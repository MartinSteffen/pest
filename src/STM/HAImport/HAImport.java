package PEST1.STM.HAImport;

import Absyn.*;
import java.io.*;
import java.util.*;
import com.oroinc.text.perl.*;

/**
 * Die Klasse HAImport dient zur Konvertierung des HA-Formates in einen
 * PEST-Statechart. Zum Importieren wird ein Filename benutzt, der dem
 * Konstruktor der Klasse uebergeben wird. Mit dem Aufruf der Methode import()
 * wird ein PEST-Statechart erzeugt.
 * Beispiel:
 * <pre>
 *    Statechart st = null;
 *    String filename = new String("ha-format.txt");
 *    HAImport imp = new HAImport(filename);
 *
 *    st = imp.import();
 * </pre>
 *
 * @author  Sven Jorga, Werner Lehmann
 * @version $Id: HAImport.java,v 1.1 1998-12-07 15:02:44 swtech18 Exp $
 */
public class HAImport implements Patterns {
  Perl5Util perl = new Perl5Util();

  private String mkhaString = null;
  private String eventsString = null;
  private String statesString = null;
  private String rootString = null;
  private String bvarsString = null;
  private String ivarsString = null;
  private String iconfString = null;
  private String hiString = null;
  private String tyString = null;
  private String trmapString = null;
  private String flathString = null;
  private String depthString = null;
  private String initString = null;

  Hashtable tyHash = null;
  Hashtable hiHash = null;
  Hashtable trHash = null;

  public HAImport(String filename) throws Exception {
    Reader file = null;
    char[] buf = null;
    File f = new File(filename);
    try {
      file = new BufferedReader(new FileReader(f));
      buf = new char[(int)f.length()];
      file.read(buf, 0, (int) f.length()); }
    catch(Exception e) {
      throw new Exception("Fehler beim Importieren von " + filename + ". (" + e.getMessage() + ")");}
    String str = new String(buf);
    str = perl.substitute("s/\\s//g",str);
    String pattern = new String(MK_SYSTEM);
    if (!perl.match("/"+pattern+"/",str))
      throw new Exception("Fehler beim Importieren von " + filename + ".");
    str = perl.group(1);

    pattern = STRINGSET+",(.*)";
    for (int i=1; i<13; i++) {
      if (!perl.match("/"+pattern+"/",str))
        throw new Exception("Fehler beim Importieren von " + filename + ".");
      switch(i) {
        case( 1): eventsString = perl.group(1); pattern = STRINGSET; break;
        case( 2): statesString = perl.group(1); pattern = STRING; break;
        case( 3): rootString = perl.group(1); pattern = STRINGSET; break;
        case( 4): bvarsString = perl.group(1); pattern = STRINGSET; break;
        case( 5): ivarsString = perl.group(1); pattern = STRINGSET; break;
        case( 6): iconfString = perl.group(1); pattern = MAPSET; break;
        case( 7): hiString = perl.group(1); pattern = MAPSET; break;
        case( 8): tyString = perl.group(1); pattern = MAPSET; break;
        case( 9): trmapString = perl.group(1); pattern = MAPSET; break;
        case(10): initString = perl.group(1); pattern = STRINGSET; break;
        case(11): flathString = perl.group(1); pattern = STRINGSET; break;
        case(12): depthString = perl.group(1); pattern = null; break;
      }
      if (i < 11)
        pattern += ",(.*)";
      str = perl.group(2);
    }

    tyHash = makeTyHash(tyString);
    hiHash = makeHiHash(hiString);
    trHash = makeTrHash(trmapString);
    Or_State stTest = (Or_State)getState();
  }

  // Pattern darf nicht in Stringset enthalten sein

  private Vector splitStringset(String stringset, String pattern) {
    Vector strVec = perl.split(pattern,stringset);
    for (int i=0; i < strVec.size(); i++)
      strVec.setElementAt(perl.substitute("s/\"//g",(String)strVec.elementAt(i)),i);
    return strVec;
  }

  private Hashtable makeHiHash(String hiString) {
    Vector hiVec = perl.split("/},\"/",hiString);
    Vector hiVec2 = null;
    String str = null;
    Hashtable hash = new Hashtable();
    for (int i=0; i < hiVec.size(); i++) {
      str = perl.substitute("s/[\"{}]//g",(String)hiVec.elementAt(i));
      hiVec2 = perl.split("/:->/",str);
      hash.put(hiVec2.elementAt(0),perl.split("/,/",(String) hiVec2.elementAt(1)));
    }
    return hash;
  }

  private Hashtable makeTyHash(String tyString) {
    Vector tyVector = splitStringset(tyString,"/,\"/");
    Vector vec = null;
    Hashtable hash = new Hashtable();
    for (int i=0; i < tyVector.size(); i++) {
      vec = perl.split("/:->/", (String) tyVector.elementAt(i));
      hash.put(vec.elementAt(0),vec.elementAt(1));
    }
    return hash;
  }

  private Hashtable makeTrHash(String trmapString) throws Exception {
    Vector trVector = null, vec = null, mktrVec = null, trVec = null;
    Hashtable hash = new Hashtable();

    trVector = deliSplit(trmapString,',');
    // trVector: Liste der Zuordnungen Zustand :-> Menge von Transitionen
    for (int i=0; i < trVector.size(); i++) {
      // vec: 0. Element stateName, 1. Element Menge (als String) von Transitionen
      vec = perl.split("/:->/", (String) trVector.elementAt(i));
      // mktrVec: einzelne Transitionen (noch als String) der Menge
      String mktrString = (String)vec.elementAt(1);
      // die Transitionsmenge ohne Mengenklammern {}
      mktrVec = deliSplit(mktrString.substring(1,mktrString.length()-1),',');
      for (int j=0; j<mktrVec.size(); j++) {
        // "mk_tr(" und ")" entfernen
        perl.match("/^mk_tr\\((.*)\\)/",(String)mktrVec.elementAt(j));
        // trVec: 6 Parameter von mk_tr
        trVec = deliSplit(perl.group(1),',');
        // Transitionsstring durch Vector mit 6 Elementen (Parameter von mk_tr) ersetzen
        mktrVec.setElementAt(trVec,j);
      }
      // in Hashtable einf’gen: stateName = Liste von Transitionen, jeweils als Liste von 6 Parametern
      // vorher abschliežendes " des Statenames entfernen (erstes " ist beim Splitting draufgegangen
      hash.put(perl.substitute("s/\"//g",(String)vec.elementAt(0)),mktrVec);
    }
    return hash;
  }

  public static final void main(String args[]) {
    try {
      HAImport imp = new HAImport("..\\TEST\\ha-format.txt"); }
    catch (Exception e) {
      e.printStackTrace();
      System.err.println("schiefgegangen");
    }
  }

  private SEventList getEventList() throws Exception {
    SEventList list = null;
    Vector eventsVector = splitStringset(eventsString,"/\",\"/");
    //Vector eventsVector = deliSplit(eventsString,',');
    for (int i=eventsVector.size(); i > 0; i--)
      list = new SEventList(new SEvent(new String((String) eventsVector.elementAt(i-1))),list);
    System.out.println("eventsVector: "+eventsVector);
    System.exit(-1);
    return list;
  }

  // einmal aufzurufen f’r jeden Import eines HA-Formates

  private State getState() {
    try {
      return createState(rootString); }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("schiefgegangen: "+e.getMessage());
      return null;
    }
  }

  private State createState(String stateName) throws Exception {
    String stateType = null;
    Vector hiVec = null, trVec = null;
    Or_State os = null;
    StateList sl = null;
    // TrList not public! Constructor not public!
    TrList tl = null;

    stateName = perl.substitute("s/\"//g",stateName);
    stateType = (String)tyHash.get(stateName);
    if (stateType.equalsIgnoreCase("BASIC")) // Wo sind die Transitionen ?
      return new Basic_State(new Statename(stateName));
    else if (stateType.equalsIgnoreCase("OR")) {
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("or-State muž Substates enthalten");
      for (int i=hiVec.size(); i>0; i--) {
        sl = new StateList(createState((String)hiVec.elementAt(i-1)),sl);
        if ((trVec = (Vector)trHash.get((String)hiVec.elementAt(i-1))) != null) {
          for (int k=trVec.size(); k>0; k--) {
            Vector currentTrVec = (Vector)trVec.elementAt(k-1);
            tl = new TrList(new Tr(new Statename((String)hiVec.elementAt(i-1)),
                                   new Statename((String)currentTrVec.elementAt(0)),
                                   new Label(createGuard((String)currentTrVec.elementAt(3),(String)currentTrVec.elementAt(4)),
                                             createAction((String)currentTrVec.elementAt(5)))),
                            tl); }
          }
      }
      // ConnectorList erzeugen

      // Or-State erzeugen
      return new Or_State(new Statename(stateName),sl,tl,null,null);
      }
    else if (stateType.equalsIgnoreCase("AND")) {
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("And-State muž Substates enthalten");
      for (int i=hiVec.size(); i>0; i--)
        sl = new StateList(createState((String)hiVec.elementAt(i-1)),sl);
      return new And_State(new Statename(stateName),sl);  }
    else
      throw new Exception("falscher Typ");

  }

  // Action not public!
  private Action createAction(String actionString) throws Exception {
    String temp = null;
    if (perl.match("/^mk_egen\\((.*)\\)/",actionString)) {
      temp = perl.substitute("s/\"//g",perl.group(1));
      return new ActionEvt(new SEvent(new String(temp))); }
    else if (perl.match("/^mk_block\\(<(.*)>\\)/",actionString)) {
      // Constructor ActionBlock not public!
      return new ActionBlock(createAseq(perl.group(1))); }
    else if (perl.match("/^mk_bstmt\\((.*)\\)/",actionString)) {
      return new ActionStmt(createBoolstmt(perl.group(1))); }
    else if (perl.match("/^mk_noop\\((.*)\\)/",actionString)) {
      return new ActionEmpty(new Dummy()); }
    else if (perl.match("/mk_istmt\\((.*)\\)/",actionString)) {
      throw new Exception("Integer Statements ("+actionString+") als Action sind nicht in PEST unterst’tzt!"); }
      //return null;
    else {
      throw new Exception("Action ("+actionString+") kann nicht erzeugt werden."); }
      //return null; }
  }

  private Aseq createAseq(String aseqString) throws Exception {
    // Aufbau: mk_block(<mk_block(...),mk_bstmt(..),...,mk_egen(..)>)
    // aseqString        ==========================================
    Aseq result = null;

    if (aseqString.equals("") || (aseqString == null))
      return null;

    Vector dsVec = deliSplit(aseqString,',');
    for (int i=dsVec.size(); i>0; i--)
      result = new Aseq(createAction((String)dsVec.elementAt(i-1)),result);

    return result;
  }

  private Boolstmt createBoolstmt(String boolstmtString) throws Exception {
    String temp = null;
    if (perl.match("/^mk_mtrue\\((.*)\\)/",boolstmtString)) {
      temp = perl.substitute("s/\"//g",perl.group(1));
      return new MTrue( new Bvar(new String(temp))); }
    else if (perl.match("/^mk_mfalse\\((.*)\\)/",boolstmtString)) {
      temp = perl.substitute("s/\"//g",perl.group(1));
      return new MFalse( new Bvar(new String(temp))); }
    else if (perl.match("/^mk_bass\\((.*)\\)/",boolstmtString)) {
      // Aufbau: mk_bass("X",...
      Vector vec = perl.split("/,/",perl.group(1));
      temp = perl.substitute("s/\"//g",(String) vec.elementAt(0));
      return new BAss(new Bassign(new Bvar(new String(temp)),
                                     createGuard("",(String) vec.elementAt(1)))); }
    else {
      throw new Exception("Boolstmt ("+boolstmtString+") kann nicht erzeugt werden."); }
      //return null; }
  }

  private Path createPath(String statename) {
    return new Path(statename);
  }

  private Guard createGuard(String exprStr, String condStr) throws Exception {
    Vector tempVec = null;
    if (perl.match("/^mk_emptyexpr\\(.*\\)/",exprStr))
      return new GuardEmpty(new Dummy());
    else if (perl.match("/^mk_basicexpr\\((.*)\\)/",exprStr))
      return new GuardEvent(new SEvent(perl.group(1)));
    else if (perl.match("/^mk_negexpr\\((.*)\\)/",exprStr))
      return new GuardNeg(createGuard(perl.group(1),""));
    else if (perl.match("/^mk_compe\\((.*)\\)/",exprStr)) {
      tempVec = deliSplit(perl.group(1),',');
      String op = (String)tempVec.elementAt(0);
      return new GuardCompg(new Compguard(op.equals("ANDOP")? Compguard.AND : Compguard.OR,
                                          createGuard((String)tempVec.elementAt(1),""),
                                          createGuard((String)tempVec.elementAt(2),""))); }

    if (perl.match("/^mk_emptycond\\(.*\\)/",condStr))
      return new GuardEmpty(new Dummy());
    else if (perl.match("/^mk_istrue\\((.*)\\)/",condStr)) // ??? ?????????????????
      return new GuardBVar(new Bvar(new String(perl.group(1))));
    else if (perl.match("/^mk_negcond\\((.*)\\)/",condStr))
      return new GuardNeg(createGuard("",perl.group(1)));
    else if (perl.match("/^mk_instate\\((.*)\\)/",condStr))
      return new GuardCompp(new Comppath(Comppath.IN,createPath(perl.group(1))));
    else if (perl.match("/^mk_compc\\((.*)\\)/",condStr)) {
      tempVec = deliSplit(perl.group(1),',');
      String op = (String)tempVec.elementAt(0);
      return new GuardCompg(new Compguard(op.equals("ANDOP")? Compguard.AND : Compguard.OR,
                                          createGuard("",(String)tempVec.elementAt(1)),
                                          createGuard("",(String)tempVec.elementAt(2)))); }
    else if (perl.match("/^mk_rel\\((.*)\\)/",condStr))
      return new GuardUndet(condStr);
    else
      return new GuardUndet(exprStr+", "+condStr);
  }

  private BvarList getBvarList() throws Exception {
    BvarList list = null;
    Vector bvarsVector = splitStringset(bvarsString,"/\",\"/");
    // Vector bvarsVector = deliSplit(bvarsString,',');
    for (int i=bvarsVector.size(); i > 0; i--)
      list = new BvarList(new Bvar(new String((String) bvarsVector.elementAt(i-1))),list);
    return list;
  }

  private PathList getPathList(String root) {
   /* Vector trVec = null;
    PathList list  = null;
    //String temp = perl.substitute("s/\"//g",rootString);
    System.out.println(root);
    Vector trVec = (Vector)trHash.get(root);
    for (int i=trVec.size(); i>0; i--) {
            Vector currentTrVec = (Vector)trVec.elementAt(i-1);
            list = new PathList(new Path(root),getPathList((String)currentTrVec.elementAt(0)));
            list = new PathList(new Path(root),list);
   */
    return null;
  }

  private Vector deliSplit(String input, char deli) throws Exception {
    boolean count1 = false;
    int count2 = 0, count3 = 0, count4 = 0, count5 = 0, pos = 0, lastpos = 0;

    Vector result = new Vector();
    while (pos < input.length()) {
      if (input.charAt(pos) == deli) {
        if (!count1 && (count2==0) && (count3==0) && (count4==0) && (count5==0)) {
          result.addElement(input.substring(lastpos,pos));
          lastpos = pos+1;
        }
      }
      else
        switch (input.charAt(pos)) {
          case '"': count1 = !count1; break;
          case '{': count2++; break;
          //case '<': count3++; break;
          case '[': count4++; break;
          case '(': count5++; break;
          case '}': count2--; break;
          //case '>': count3--; break;
          case ']': count4--; break;
          case ')': count5--; break;
        }
      if ((count2<0) || (count3<0) || (count4<0) || (count5<0))
        throw new Exception("deliSplit: Klammerfehler an Position "+pos+"\nEingabestring: "+input+"\nBisher gesplittet: "+result.size()+" Elemente: "+result+"\nKontext: "+input.substring(lastpos,pos));
      pos++;
    }
    if (pos>lastpos-1)
      result.addElement(input.substring(lastpos,pos));

    return result;
  }

}
