package stm;

import absyn.*;
import java.io.*;
import java.util.*;
import java.awt.Point;
import java.awt.Rectangle;
import com.oroinc.text.perl.*;
import util.PrettyPrint;
import gui.GUIInterface;

/**
 * Die Klasse HAImport dient zur Konvertierung des HA-Formates in eine
 * PEST-Statechart. Zum Importieren wird ein Filename benutzt, der dem
 * Konstruktor der Klasse uebergeben wird. Mit dem Aufruf der Methode import()
 * wird ein PEST-Statechart erzeugt.
 * Beispiel:
 * <pre>
 *    GUIInterface myWindow = ...;
 *    Statechart st = null;
 *    BufferedReader buf = new BufferedReader(new FileReader(new File("ha-format.txt")));
 *    HAImport imp = new HAImport(buf, myWindow);
 *
 *    st = imp.getStatechart();
 *    if (st == null) System.out.println("Import fehlgeschlagen!");
 * </pre>
 *
 * <DL COMPACT>
 * <DT><STRONG>
 * STATUS.
 * </STRONG>
 * Import funktioniert (HA-Format wird eingelesen und ein
 * StateChart in PEST-Syntax zur&uuml;ckgeliefert).
 * <DT><STRONG>TODO.</STRONG>
 * Die durch das Entfernen von Konnektoren entstandenen
 * &Uuml;berschneidungen von Transitionen m&uuml;ssen
 * noch &uuml;berarbeitet werden.
 * <DT><STRONG>
 * BEKANNTE FEHLER.
 * </STRONG>
 * Keine.
 * <DT><STRONG>
 * FORDERUNGEN.
 * </STRONG>
 * Wir erwarten ein korrektes HA-Format.
 * <DT><STRONG>GARANTIEN.</STRONG>
 * Es wird eine PEST-StateChart erzeugt, die nur NULL-Pointer
 * als Listenterminierung oder als Kennzeichnung f&uuml;r
 * leere Listen enth&auml;lt.
 * <DT><STRONG>HINWEIS.</STRONG>
 * Die Methode main dient uns intern zum Testen ohne GUI.
 * Ein Beispiel mit hardkodiertem Dateinamen wird importiert und &uuml;ber
 * den PrettyPrinter ausgegeben. 
 * </DL COMPACT>
 *
 * @author  Sven Jorga, Werner Lehmann
 * @version $Id: HAImport.java,v 1.9 1999-01-11 12:27:12 swtech18 Exp $
 */
public class HAImport implements Patterns {
  Perl5Util perl = new Perl5Util();

  private static final int SKAL = 20;
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

  private GUIInterface gui = null;
  private boolean initSuccess = false;
  private Hashtable tyHash = null;
  private Hashtable hiHash = null;
  private Hashtable trHash = null;
  private Hashtable coordHash = null;
  private Hashtable initHash = null;

/** Der Konstruktor dient zum Importieren einer StateChart
   * im HA-Format. Es wird erwartet, da&szlig; reader ein
   * Stream auf eine entsprechende Eingabedatei ist.
   */

  public HAImport(BufferedReader reader) throws Exception {
    initImport(reader);
  }

  /** Dieser Konstruktor erwartet zus&auml;tzlich ein GUIInterface
   * und sollte nun anstelle des bisherigen verwendet werden, damit
   * Fehlermeldungen im Logfenster des GUIs erscheinen und nicht als
   * Dialoge.
   */

  public HAImport(BufferedReader reader, GUIInterface gui ) throws Exception {
    this.gui = gui;
    try {
      initImport(reader);}
    catch ( Exception e) {
      if (gui == null)
        throw e;
      else
        gui.userMessage("STM: Fehler beim Importieren. (" + e.getMessage() + ")");}
  }

  private void initImport(BufferedReader reader) throws Exception {
    String str = new String();

    initSuccess = false;
    while (reader.ready())
      str += reader.readLine();
    str = perl.substitute("s/\\s//g",str);
    String pattern = new String(MK_SYSTEM);
    if (!perl.match("/"+pattern+"/",str))
      throw new Exception("Eingabedatei nicht korrektes HAFormat. Konstruktor mk_system() fehlerhaft.");
    str = perl.group(1);

    pattern = STRINGSET+",(.*)";
    for (int i=1; i<13; i++) {
      if (!perl.match("/"+pattern+"/",str))
        throw new Exception("Komponente "+i+" vom ersten mk_ha() fehlerhaft.");
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
    coordHash = makeCoordHash(statesString);
    initHash = makeInitHash(initString);
    initSuccess = true;
  }

  /** Diese Methode liefert die StateChart, die aus dem dem Konstruktor
   * &uuml;bergebenen Stream importiert wird. Wenn ein Fehler auftritt,
   * wird dieser ggf. im GUI-Logfenster ausgegeben (falls ein entsprechendes
   * Objekt bei der Konstruktion der Klasse angegeben wurde). Der
   * R&uuml;ckgabewert ist in diesem Fall null.
   */

  public Statechart getStatechart() throws Exception {
    Statechart st = null;
    if (!initSuccess)
      gui.userMessage("STM: getStatechart - Init fehlgeschlagen.");
    else
      try {
        st = new Statechart(getEventList(),getBvarList(),getPathList(),getState()); }
      catch(Exception e) {
        if (gui == null)
          throw e;
        else
          gui.userMessage("STM: Fehler beim Importieren. (" + e.getMessage() + ")");}
    return st;
  }

  // Pattern darf nicht in Stringset enthalten sein
  private Vector splitStringset(String stringset, String pattern) {
    Vector strVec = perl.split(pattern,stringset);
    for (int i=0; i < strVec.size(); i++)
      strVec.setElementAt(perl.substitute("s/\"//g",(String)strVec.elementAt(i)),i);
    return strVec;
  }

  private Hashtable makeInitHash(String initString) throws Exception {
    Hashtable tempHash = new Hashtable();
    Vector tempVec = null, currentVec = null;
    String currentStr = null;

    tempVec = deliSplit(initString,',');
    for (int i=0; i < tempVec.size(); i++) {
      currentStr = removeQuotes((String) tempVec.elementAt(i));
      currentVec = perl.split("/:->/",currentStr);
      tempHash.put(currentVec.elementAt(0), currentVec.elementAt(1));
    }
    return tempHash;
  }

  private Hashtable makeCoordHash(String statesString) throws Exception {
    Hashtable tempHash = new Hashtable();
    Vector gstatesVec = null;
    Vector currentVec = null;
    Vector xyVec = null;
    Vector pointVec = null;
    String currentStr = null;
    String currentCoord = null;

    statesString = perl.substitute("s/mk_gstate//g",statesString);
    gstatesVec = deliSplit(statesString,',');
    for (int i=0; i < gstatesVec.size(); i++) {
      currentStr = (String)gstatesVec.elementAt(i);
      // Remove ( ) from List
      currentStr = currentStr.substring(1,currentStr.length()-1);
      currentStr = perl.substitute("s/mk_coord|<|>//g",currentStr);
      // Vector with statename and coords
      currentVec = deliSplit(currentStr,',');
      pointVec = new Vector();
      for (int j=1; j < currentVec.size(); j++) {
        currentCoord = (String) currentVec.elementAt(j);
        // Remove (...)
        currentCoord = currentCoord.substring(1,currentCoord.length()-1);
        xyVec = deliSplit(currentCoord,',');
        pointVec.addElement(new CPoint(Integer.parseInt((String)xyVec.elementAt(0))*SKAL,
                                      Integer.parseInt((String)xyVec.elementAt(1))*SKAL));
      }
      tempHash.put(removeQuotes((String)currentVec.elementAt(0)),pointVec);
    }
    return tempHash;
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
        trVec.setElementAt(removeQuotes((String)trVec.firstElement()),0);
        // Transitionsstring durch Vector mit 6 Elementen (Parameter von mk_tr) ersetzen
        mktrVec.setElementAt(trVec,j);
      }
      // in Hashtable einf�gen: stateName = Liste von Transitionen, jeweils als Liste von 6 Parametern
      // vorher abschliessendes " des Statenames entfernen (erstes " ist beim Splitting draufgegangen
      hash.put(perl.substitute("s/\"//g",(String)vec.elementAt(0)),mktrVec);
    }
    return hash;
  }

  private String removeQuotes(String str) { return perl.substitute("s/\"//g",str); }

  /** main dient uns intern zum testen ohne GUI und sollte nicht
   * verwendet werden.
   */

  public static final void main(String args[]) {
    PrettyPrint pp = new PrettyPrint ();
    try {
      //HAImport imp = new HAImport(new BufferedReader(new FileReader(new File("Test/ha-format.txt")))); }
      HAImport imp = new HAImport(new BufferedReader(new FileReader(new File("Test/a1.st"))));
      pp.start(imp.getStatechart());}
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
    return list;
  }

  // einmal aufzurufen f�r jeden Import eines HA-Formates

  private State getState() {
    try {
      return createState(rootString, new CRectangle(0,0,0,0)); }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("schiefgegangen: "+e.getMessage());
      return null;
    }
  }

  private State createState(String stateName, Rectangle baseRect) throws Exception {
    String stateType = null;
    Vector hiVec = null, trVec = null, pointVec = null;
    Or_State os = null;
    StateList sl = null;
    StatenameList snl = null;
    TrList tl = null;
    CPoint pt = null;
    Rectangle rect = null;
    CRectangle rt = null;
    CPoint pointArray[];

    stateName = perl.substitute("s/\"//g",stateName);
    stateType = (String)tyHash.get(stateName);
    // Rectangle erzeugen
    pointVec = (Vector)coordHash.get(stateName);
    for (int l=0; l < pointVec.size(); l++) {
      pt = (CPoint)pointVec.elementAt(l);
      if (rt == null)
        rt = new CRectangle(pt);
      else {
        rect = rt.union(new CRectangle(pt));
        rt = new CRectangle(rect.x,rect.y,rect.width,rect.height);
        //rt = new CRectangle((CRectangle)rt.union(new CRectangle(pt)));
        }
    }
    rt = new CRectangle(rt.x-baseRect.x,rt.y-baseRect.y,rt.width,rt.height);
    if (stateType.equalsIgnoreCase("BASIC"))
      return new Basic_State(new Statename(stateName),rt);
    else if (stateType.equalsIgnoreCase("OR")) {
      // Default-SatenameList erzeugen
      snl = new StatenameList(new Statename((String)initHash.get(stateName)),snl);
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("or-State muss Substates enthalten");
      for (int i=hiVec.size(); i>0; i--) {
        // StateList erzeugen
        sl = new StateList(createState((String)hiVec.elementAt(i-1),rt),sl);
        if ((trVec = (Vector)trHash.get((String)hiVec.elementAt(i-1))) != null) {
          for (int k=trVec.size(); k>0; k--) {
            Vector currentTrVec = (Vector)trVec.elementAt(k-1);
            pointVec.removeAllElements(); // pointVec soll jetzt die Koordinaten der Transitionen aufnehmen
            for (int m=6; m<currentTrVec.size(); m++) {
              perl.match("/<?mk_coord\\((\\d+),(\\d+)\\)>?/",(String)currentTrVec.elementAt(m));
              pointVec.addElement(new CPoint((Integer.parseInt(perl.group(1))*SKAL)-rt.x,(Integer.parseInt(perl.group(2))*SKAL)-rt.y));
            }
            pointVec = removeDuplicates(pointVec);
            pointArray = new CPoint[pointVec.size()];
            pointVec.copyInto(pointArray);
            tl = new TrList(new Tr(new Statename((String)hiVec.elementAt(i-1)),
                                   new Statename((String)currentTrVec.elementAt(0)),
                                   new TLabel(createGuard((String)currentTrVec.elementAt(3),(String)currentTrVec.elementAt(4)),
                                             createAction((String)currentTrVec.elementAt(5))),
                                   pointArray),
                            tl); }
          }
      }
      // ConnectorList entfaellt
      // Or-State erzeugen
      //System.exit(-1);
      return new Or_State(new Statename(stateName),sl,tl,snl,null,rt);
      }
    else if (stateType.equalsIgnoreCase("AND")) {
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("And-State muss Substates enthalten");
      for (int i=hiVec.size(); i>0; i--)
        sl = new StateList(createState((String)hiVec.elementAt(i-1),rt),sl);
      return new And_State(new Statename(stateName),sl,rt);  }
    else
      throw new Exception("State "+stateName+" hat unbekannten Typ "+stateType);

  }

  private Vector removeDuplicates(Vector vec) {
    Vector v = new Vector();
    v = (Vector)vec.clone();

    if (v.size() == 0)
      return v;

    for (int i=0; i<v.size()-1; i++)
      if ( ((CPoint)v.elementAt(i)).equals((CPoint)v.elementAt(i+1)))
        v.removeElementAt(i);
    return v;
  }

  private Action createAction(String actionString) throws Exception {
    String temp = null;
    if (perl.match("/^mk_egen\\((.*)\\)/",actionString)) {
      temp = perl.substitute("s/\"//g",perl.group(1));
      return new ActionEvt(new SEvent(new String(temp))); }
    else if (perl.match("/^mk_block\\(<(.*)>\\)/",actionString)) {
      return new ActionBlock(createAseq(perl.group(1))); }
    else if (perl.match("/^mk_bstmt\\((.*)\\)/",actionString)) {
      return new ActionStmt(createBoolstmt(perl.group(1))); }
    else if (perl.match("/^mk_noop\\((.*)\\)/",actionString)) {
      return new ActionEmpty(new Dummy()); }
    else if (perl.match("/mk_istmt\\((.*)\\)/",actionString)) {
      throw new Exception("Integer Statements ("+actionString+") als Action sind nicht in PEST unterstuetzt!"); }
    else {
      throw new Exception("Action ("+actionString+") kann nicht erzeugt werden."); }
  }

  private Aseq createAseq(String aseqString) throws Exception {
    // Aufbau: mk_block(<mk_block(...),mk_bstmt(..),...,mk_egen(..)>)
    // aseqString        ==========================================
    Aseq result = null;

    if (aseqString.equals("") || (aseqString == null))
      return new Aseq(new ActionEmpty(new Dummy()),null);

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
  }

  private Path createPath(String statename) {
    return null; //new Path(statename);
  }

  private Guard createGuard(String exprStr, String condStr) throws Exception {
    Vector tempVec = null;
    if (perl.match("/^mk_emptyexpr\\(.*\\)/",exprStr))
      return new GuardEmpty(new Dummy());
    else if (perl.match("/^mk_basicexpr\\((.*)\\)/",exprStr))
      return new GuardEvent(new SEvent(removeQuotes(perl.group(1))));
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

  private PathList getPathList() { return getPathList(rootString,null,null); }

  private PathList getPathList(String statename, Path currentPath, PathList pathList) {
    Vector hiVec = null;
    statename = perl.substitute("s/\"//g",statename);
    //System.out.println(statename);
    if (currentPath == null)
      currentPath = new Path(statename,null);
    else
      currentPath = currentPath.append(statename);
    pathList = new PathList(currentPath,pathList);
    hiVec = (Vector)hiHash.get(statename);
    if (hiVec != null)
      for (int i=hiVec.size(); i>0; i--)
        pathList = getPathList((String)hiVec.elementAt(i-1),currentPath,pathList);
    return pathList;
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
