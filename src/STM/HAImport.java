package stm;

import absyn.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.Point;
import java.awt.Rectangle;
import com.oroinc.text.perl.*;
import util.PrettyPrint;
import util.Keyword;
import gui.GUIInterface;
import tesc1.TESCSaver;

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
 *    st = imp.getStatechart(); // Default mit Koordinaten, falls Statechart welche hat,
 *                              // sonst ohne Koordinaten und Auflösung 640x480.
 *    st = imp.getStatechart(true); // Koordinaten werden übernommen,
 *                                  // falls Statechart welche hat.
 *    st = imp.getStatechart(false); // Koordinaten werden nicht übernommen.
 *    st = imp.getStatechart(800,600); // Neue Aufloesung 800x600.
 *    st = imp.getStatechart(false,800,600); // Keine Koordinaten und
 *                                           // neue Aufloesung 800x600.
 *
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
 * @version $Id: HAImport.java,v 1.22 1999-02-11 18:38:54 swtech18 Exp $
 */
public class HAImport implements Patterns {
  Perl5Util perl = new Perl5Util();

  private static final String DEFAULTMESSAGE = "STM: Statename {0} ist reserviertes Schlüsselwort und wurde {1} umbenannt.";
  private double rFaktor = 1;
  private boolean parseCoords = true;
  private boolean rename = false;
  private boolean parseInitString = true;
  private boolean parseTrmap = true;
  private int xSize = 640;
  private int ySize = 480;
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
  private Hashtable pathHash = new Hashtable();
  private Hashtable rectHash = new Hashtable();
  private TESCSaver tesc = null;
  private boolean first = true;
  private Vector used = null;

  /** Der Konstruktor dient zum Importieren einer StateChart
   * im HA-Format. Es wird erwartet, da&szlig; reader ein
   * Stream auf eine entsprechende Eingabedatei ist.
   */

  public HAImport(BufferedReader reader) throws Exception {
    this.tesc = new TESCSaver(null);
    first = true;
    used = new Vector();
    initImport(reader);
  }

  /** Konstruktor mit GUI-Unterst&uuml;tzung.
   * Dieser Konstruktor erwartet zus&auml;tzlich ein GUIInterface
   * und sollte nun anstelle des bisherigen verwendet werden, damit
   * Fehlermeldungen im Logfenster des GUIs erscheinen und nicht als
   * Dialoge.
   */

  public HAImport(BufferedReader reader, GUIInterface gui ) throws Exception {
    this.gui = gui;
    this.tesc = new TESCSaver(gui);
    first = true;
    used = new Vector();
    try {
      initImport(reader);}
    catch ( Exception e) {
      if (gui == null)
        throw e;
      else {
        gui.userMessage("STM: Fehler beim Importieren. (" + e.getMessage() + ")");}
        // e.printStackTrace();
      }
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
    if (tyString == null || tyString.equals(""))
      throw new Exception("HA-Format-Fehler: Kein Typing vorhanden!");
    if (tyString == null || hiString.equals(""))
      throw new Exception("HA-Format-Fehler: Keine \"hierarchy function\" (Hi) vorhanden!");
    if (flathString != null && !flathString.equals(""))
      showWarning("STM: flache Histories sind nicht abgebildet worden!");
    if (depthString != null && !depthString.equals(""))
      showWarning("STM: tiefe Histories sind nicht abgebildet worden!");
    if (trmapString == null || trmapString.equals("") )
      parseTrmap = false;
      // throw new Exception("HA-Format-Fehler: Keine Trmap vorhanden!");
    if (rootString == null || rootString.equals(""))
      throw new Exception("HA-Format-Fehler: Keine Root-State definiert!");
    if (initString == null || initString.equals("") || initString.equals(" "))
      parseInitString = false;

    tyHash = makeTyHash(tyString);
    hiHash = makeHiHash(hiString);
    if (parseTrmap)
      trHash = makeTrHash(trmapString);
    rootString = removeQuotes(rootString);

    if (perl.match("/.*mk_coord.*/",statesString) && (!parseTrmap || perl.match("/.*mk_coord.*/",trmapString)))
      parseCoords = true;
    else
      parseCoords = false;

    if (parseCoords)
      coordHash = makeCoordHash(statesString);

    if (parseInitString)
      initHash = makeInitHash(initString);
    initSuccess = true;
  }

  private double calcResize(int maxWidth, int maxHeight, int rootWidth, int rootHeight) {
    double rFaktorX = 1.0, rFaktorY = 1.0;
    double resize = 1.0;

    rFaktorX = (double)maxWidth / rootWidth;
    rFaktorY = (double)maxHeight / rootHeight;
    if (rFaktorX > rFaktorY)
      resize = rFaktorY;
    else
      resize = rFaktorX;
    return resize;
  }

  /** Diese Methode liefert die StateChart, die aus dem dem Konstruktor
   * &uuml;bergebenen Stream importiert wird. Wenn ein Fehler auftritt,
   * wird dieser ggf. im GUI-Logfenster ausgegeben (falls ein entsprechendes
   * Objekt bei der Konstruktion der Klasse angegeben wurde). Der
   * R&uuml;ckgabewert ist in diesem Fall null.
   * getStatechart() darf nur einmal aufgerufen werden.
   */

  public Statechart getStatechart() throws Exception {
    if (!first)
      throw new Exception("getStatechart() darf nur einmal aufgerufen werden!");
    first = false;

    Statechart st = null;
    CRectangle cRect = new CRectangle(0,0,0,0);
    if (!initSuccess)
      gui.userMessage("STM: getStatechart - Init fehlgeschlagen.");
    else
      try {
        if (parseCoords) {
          cRect = calcRect((Vector)coordHash.get(rootString));
          rFaktor = calcResize(xSize, ySize, cRect.width, cRect.height);
        }
        st = new Statechart(getEventList(),getBvarList(),getPathList(),getState()); }
      catch(Exception e) {
        if (gui == null)
          throw e;
        else {
          gui.userMessage("STM: Fehler beim Importieren. (" + e.getMessage() + ")");
          //e.printStackTrace();
        }
      }
    return st;
  }

  /** Diese Methode liefert die StateChart genau wie getStatechart(),
   * jedoch kann noch zus&auml;tzlich ein boolean-Parameter &uuml;bergeben
   * werden, der festlegt, ob die Koordinaten mit &uuml;bernommen werden sollen.
   */

  public Statechart getStatechart(boolean parseCoords) throws Exception {
    this.parseCoords = this.parseCoords && parseCoords;
    return getStatechart();
  }

  /** Diese Methode liefert die StateChart genau wie getStatechart(),
   * jedoch können noch zus&auml;tzlich zwei Integer-Parameter,
   * die die gewünschte Skalierungs-Aufl&ouml;sung angeben, &uuml;bergeben werden.
   */

  public Statechart getStatechart(int xSize, int ySize) throws Exception {
    this.xSize = xSize;
    this.ySize = ySize;
    return getStatechart();
  }

  /** Diese Methode liefert die StateChart genau wie getStatechart(),
   * jedoch kann noch zus&auml;tzlich ein boolean-Parameter &uuml;bergeben
   * werden, der festlegt, ob die Koordinaten mit &uuml;bernommen werden sollen.
   * Und zwei Integer-Parameter, die die gewünschte Skalierungs-Aufl&ouml;sung
   * angeben.
   */

  public Statechart getStatechart(boolean parseCoords, int xSize, int ySize) throws Exception {
    this.parseCoords = this.parseCoords && parseCoords;
    this.xSize = xSize;
    this.ySize = ySize;
    return getStatechart();
  }

  /** Diese Methode liefert die StateChart genau wie getStatechart(),
   * jedoch kann noch zus&auml;tzlich zwei boolean-Parameter &uuml;bergeben
   * werden, die festlegen, ob im StateChart vorkommende PEST-Keywords umbenannt
   * werden sollen und ob die Koordinaten mit &uuml;bernommen werden sollen.
   * Und zwei Integer-Parameter, die die gewünschte Skalierungs-Aufl&ouml;sung
   * angeben.
   */
  public Statechart getStatechart(boolean rename, boolean parseCoords, int xSize, int ySize) throws Exception {
    this.parseCoords = this.parseCoords && parseCoords;
    this.rename = rename;
    this.xSize = xSize;
    this.ySize = ySize;
    return getStatechart();
  }

  // Pattern darf nicht in Stringset enthalten sein
  private Vector splitStringset(String stringset, String pattern) {
    if (stringset.equals(""))
      return new Vector();
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
    String stateName = null;
    Double xCoord = null, yCoord = null;

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
        xCoord = new Double(Integer.parseInt((String)xyVec.elementAt(0)));
        yCoord = new Double(Integer.parseInt((String)xyVec.elementAt(1)));
        pointVec.addElement(new CPoint(xCoord.intValue(),yCoord.intValue()));
      }
      stateName = removeQuotes((String)currentVec.elementAt(0));
      tempHash.put(stateName,pointVec);
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
        // trVec: 8 Parameter von mk_tr
        trVec = deliSplit(perl.group(1),',');
        trVec.setElementAt(removeQuotes((String)trVec.firstElement()),0);
        // td nicht leer? Interleveltransitionen sind nicht zulässig
        if (!((String)trVec.elementAt(1)).equals("{}"))
          throw new Exception("Interleveltransitionen nicht zulässig");
        // Transitionsstring durch Vector mit 6 Elementen (Parameter von mk_tr) ersetzen
        mktrVec.setElementAt(trVec,j);
      }
      // in Hashtable einfÆgen: stateName = Liste von Transitionen, jeweils als Liste von 6 Parametern
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
      list = new SEventList(new SEvent(new String(checkKeyword((String) eventsVector.elementAt(i-1),"STM: SEvent {0} ist reserviertes Schlüsselwort und wurde {1} umbenannt."))),
                            list);
    return list;
  }

  // einmal aufzurufen fÆr jeden Import eines HA-Formates

  private State getState() throws Exception {
    State st = createState(rootString, new CRectangle(0,0,0,0));
    if (parseCoords) {
      fixAllTrans(st);
      fixAllStateRects(st);
    }
    return st;
  }

  private void fixAllTrans(State s) throws Exception {
    StateList currState = null;
    if (s instanceof Or_State) {
      TrList current = ((Or_State)s).trs;
      while ( current != null) {
        fixTrans(current.head.points,(CRectangle) rectHash.get(((Statename)current.head.source).name),
                (CRectangle) rectHash.get(((Statename)current.head.target).name));
        current = current.tail;
      }
      currState = ((Or_State)s).substates;
      while (currState != null) {
        fixAllTrans(currState.head);
        currState = currState.tail;
      }
    }else if (s instanceof And_State) {
      currState = ((And_State)s).substates;
      while (currState != null) {
        fixAllTrans(currState.head);
        currState = currState.tail;
      }
    }
  }

  private void fixAllStateRects(State s) throws Exception {
    StateList currStateList = null;
    if (s instanceof And_State) {
      currStateList = ((And_State)s).substates;
      while (currStateList != null) {
        fixRect(currStateList.head, ((And_State)s).substates);
        fixAllStateRects(currStateList.head);
        currStateList = currStateList.tail;
      }
    }else if (s instanceof Or_State) {
      currStateList = ((Or_State)s).substates;
      while (currStateList != null) {
        fixAllStateRects(currStateList.head);
        currStateList = currStateList.tail;
      }
    }else if (s instanceof Basic_State) {
      
    }
  }

  private void fixRect(State s, StateList sl) {
    StateList currStateList = sl;
    while (currStateList != null) {
      if (inXRange(s,currStateList.head)){
        s.rect.width = currStateList.head.rect.x - s.rect.x;
      }
      if (inYRange(s,currStateList.head)) {
        s.rect.height = currStateList.head.rect.y - s.rect.y;
      }
      currStateList = currStateList.tail;
    }
  }

  private boolean inXRange(State s1, State s2) {
    int size = 25;
    int pos = s1.rect.x + s1.rect.width;
    if(pos-size < s2.rect.x && s2.rect.x < pos+size &&
       s1.rect.y-size < s2.rect.y && s2.rect.y < s1.rect.y+size)
      return true;
    else
      return false;
  }

  private boolean inYRange(State s1, State s2) {
    int pos = s1.rect.y + s1.rect.height;
    if(pos-25 < s2.rect.y && s2.rect.y < pos+25 &&
       s1.rect.x-25 < s2.rect.x && s2.rect.x < s1.rect.x+25)
      return true;
    else
      return false;
  }

  private CRectangle calcRect(Vector pointVec) {
    CPoint pt = null;
    Rectangle rect = null;
    CRectangle cRect = null;

    for (int l=0; l < pointVec.size(); l++) {
      pt = (CPoint)pointVec.elementAt(l);
      if (cRect == null)
        cRect = new CRectangle(pt);
      else {
        rect = cRect.union(new CRectangle(pt));
        cRect = new CRectangle(rect.x,rect.y,rect.width,rect.height);
        //cRect = new CRectangle((CRectangle)cRect.union(new CRectangle(pt)));
      }
    }
    return cRect;
  }

  private State createState(String stateName, Rectangle baseRect) throws Exception {
    String stateType = null, exprStr = null, condStr = null;
    Vector hiVec = null, trVec = null, pointVec = null, tmpVec = null;
    Or_State os = null;
    StateList sl = null;
    StatenameList snl = null;
    TrList tl = null;
    CRectangle rt = null, rtNew = null;
    CPoint pointArray[] = null;
    Double xCoord = null, yCoord = null;
    Double resizedX = null, resizedY = null, resizedWidth = null, resizedHeight = null;
    Guard newGuard = null;
    TLabel tLabel = null;

    stateName = perl.substitute("s/\"//g",stateName);
    //stateName = checkKeyword(stateName,DEFAULTMESSAGE);
    stateType = (String)tyHash.get(stateName);
    if (parseCoords) {
      // Rectangle erzeugen
      pointVec = (Vector)coordHash.get(stateName);
      rt = calcRect(pointVec);
      resizedX = new Double((rt.x-baseRect.x) * rFaktor);
      resizedY = new Double((rt.y-baseRect.y) * rFaktor);
      resizedWidth = new Double(rt.width * rFaktor);
      resizedHeight = new Double(rt.height * rFaktor);
      rtNew = new CRectangle(resizedX.intValue(),
                             resizedY.intValue(),
                             resizedWidth.intValue(),
                             resizedHeight.intValue());
      rectHash.put(checkKeyword(stateName,null),rtNew);
    }
    if (stateType.equalsIgnoreCase("BASIC"))
      return new Basic_State(new Statename(checkKeyword(stateName,DEFAULTMESSAGE)),rtNew);
    else if (stateType.equalsIgnoreCase("OR")) {
      // Default-SatenameList erzeugen
      if (parseInitString)
        snl = new StatenameList(new Statename(checkKeyword((String)initHash.get(stateName),null)),snl);
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("or-State muss Substates enthalten");
      for (int i=hiVec.size(); i>0; i--) {
        // StateList erzeugen
        sl = new StateList(createState((String)hiVec.elementAt(i-1),rt),sl);
        if (parseTrmap && ((trVec = (Vector)trHash.get((String)hiVec.elementAt(i-1))) != null)) {
          for (int k=trVec.size(); k>0; k--) {
            Vector currentTrVec = (Vector)trVec.elementAt(k-1);
            if (parseCoords) {
              pointVec.removeAllElements(); // pointVec soll jetzt die Koordinaten der Transitionen aufnehmen
              tmpVec = null;
              for (int m=6; m<currentTrVec.size(); m++) {
                String tmpStr = (String)currentTrVec.elementAt(m);
                perl.match("/<?mk_coord\\((\\d+),(\\d+)\\)>?/",tmpStr);
                xCoord = new Double((Integer.parseInt(perl.group(1))-rt.x)*rFaktor);
                yCoord = new Double((Integer.parseInt(perl.group(2))-rt.y)*rFaktor);
                if (tmpVec == null)
                  pointVec.addElement(new CPoint(xCoord.intValue(),yCoord.intValue()));
                else
                  tmpVec.addElement(new CPoint(xCoord.intValue(),yCoord.intValue()));
                if (tmpVec == null && tmpStr.endsWith(">") && (m+1 < currentTrVec.size()) )
                  tmpVec = new Vector();
              }
              pointVec = removeDuplicates(pointVec);
              pointArray = new CPoint[pointVec.size()];
              pointVec.copyInto(pointArray);
            }
            exprStr = (String)currentTrVec.elementAt(3);
            condStr = (String)currentTrVec.elementAt(4);
            if (!exprStr.equals("") && !condStr.equals(""))  {
              // System.out.println("exprStr: "+exprStr);
              // System.out.println("condStr: "+condStr);
              newGuard = new GuardCompg(new Compguard(Compguard.AND,
                                          createGuard(exprStr,""),
                                          createGuard("",condStr)));
            }else
              newGuard = createGuard(exprStr, condStr);
            tLabel =  new TLabel(newGuard,
                                 createAction((String)currentTrVec.elementAt(5)),
                                 getUnusedPoint(tmpVec,pointVec), // CPoint
                                 null, // Location
                                 "");
            tesc.setCaption(tLabel);
            tl = new TrList(new Tr(new Statename(checkKeyword((String)hiVec.elementAt(i-1),null)),
                                   new Statename(checkKeyword((String)currentTrVec.elementAt(0),null)),
                                   tLabel,
                                   pointArray),
                            tl);
          }
        }
      }
      // ConnectorList entfaellt
      // Or-State erzeugen
      return new Or_State(new Statename(checkKeyword(stateName,DEFAULTMESSAGE)),sl,tl,snl,null,rtNew);
      }
    else if (stateType.equalsIgnoreCase("AND")) {
      // Liste der Substates
      hiVec = (Vector)hiHash.get(stateName);
      if (hiVec == null)
        throw new Exception("And-State muss Substates enthalten");
      for (int i=hiVec.size(); i>0; i--)
        sl = new StateList(createState((String)hiVec.elementAt(i-1),rt),sl);
      return new And_State(new Statename(checkKeyword(stateName,DEFAULTMESSAGE)),sl,rtNew);  }
    else
      throw new Exception("State "+stateName+" hat unbekannten Typ "+stateType);

  }

  private String checkKeyword(String s, String m) {
    String tmp = null;
    Object[] args = new Object[2];
    if ( Keyword.isReserved(s) ) {
      if (rename)
        tmp = "in "+checkKeyword(s+"_1",m);
      else
        tmp = "nicht";
      args[0] = s;
      args[1] = tmp;
      if (m != null)
        showWarning(MessageFormat.format(m,args));
      return tmp;
    }
    return s;
  }

  private void fixTrans(CPoint pointVec[], CRectangle src, CRectangle dest) throws Exception{
    if (src == null || dest == null)
      throw new Exception("fixTrans: src oder dest ist null.");
    if (!src.contains(pointVec[0])) {
      pointVec[0] = nearestPoint(pointVec[0],src);
    }
    if (!dest.contains(pointVec[pointVec.length-1])) {
      pointVec[pointVec.length-1] = nearestPoint(pointVec[pointVec.length-1],dest);
    }
  }

  private CPoint nearestPoint(CPoint p, CRectangle r) throws Exception{
    //if (p.x >= r.x && p.y >= r.y && p.x < r.x+r.width && p.y < r.y+r.height)
    //  throw new Exception("Böse Falle");
    if (p.x >= r.x && p.x < r.x+r.width)
      if (p.y < r.y) return new CPoint(p.x,r.y);
      else return new CPoint(p.x, r.y+r.height-1);
    else if (p.y >= r.y && p.y < r.y+r.height)
      if (p.x < r.x) return new CPoint(r.x,p.y);
      else return new CPoint(r.x+r.width-1, p.y);
    else if (p.x < r.x && p.y < r.y) return new CPoint(r.x,r.y);
    else if (p.x >= r.x + r.width && p.y < r.y) return new CPoint(r.x+r.width-1, r.y);
    else if (p.x < r.x && p.y >= r.y + r.height) return new CPoint(r.x,r.y+r.height-1);
    else if (p.x >= r.x + r.width && p.y >= r.y + r.height) return new CPoint(r.x+r.width-1, r.y+r.height-1);
    else throw new Exception("nearestPoint: Shit happens");
  }

  private CPoint getUnusedPoint(Vector labelCoords, Vector trCoords) throws Exception {
    int i = 0;
    CPoint pt = null;
    if (labelCoords == null || labelCoords.size() == 0)
      return null;
    for (i=0; (i<labelCoords.size()) && pointInVec(used,(CPoint)labelCoords.elementAt(i)); i++);
    if (i >= labelCoords.size()) {
      //throw new Exception("Nicht genug Labelkoordinaten verfuegbar.");
      pt = calcPoint(trCoords);
    } else {
      used.addElement((CPoint)labelCoords.elementAt(i));
      pt = (CPoint)used.lastElement();
    }
    return pt;
  }

  private CPoint calcPoint(Vector trCoords) {
    int size = trCoords.size();
    CPoint pt = null, pt1 = null, pt2 = null;

    if (size % 2 > 0)
      pt = (CPoint) trCoords.elementAt(size / 2);
    else {
      pt1 = (CPoint) trCoords.elementAt(size / 2 - 1);
      pt2 = (CPoint) trCoords.elementAt(size / 2 );
      pt = new CPoint((pt1.x+pt2.x)/2,(pt1.y+pt2.y)/2);
    }
    return pt;
  }

  private boolean pointInVec(Vector vec, CPoint p) {
    boolean result = false;
    for (int i=0; !result && i<vec.size();i++) {
      result = pointEquals(p,(CPoint)vec.elementAt(i));
    }
    // for (int i=0; !result && i<vec.size() && (result |= p.equals((Point)vec.elementAt(i))) ;i++);
    return result;
  }

  private boolean pointEquals(CPoint a, CPoint b) {
    return (a.x == b.x) && (a.y == b.y);
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
      temp = checkKeyword(temp, "STM: ActionEvt-String {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
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
      temp = checkKeyword(temp, "STM: Bvar {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
      return new MTrue( new Bvar(new String(temp))); }
    else if (perl.match("/^mk_mfalse\\((.*)\\)/",boolstmtString)) {
      temp = perl.substitute("s/\"//g",perl.group(1));
      temp = checkKeyword(temp, "STM: Bvar {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
      return new MFalse( new Bvar(new String(temp))); }
    else if (perl.match("/^mk_bass\\((.*)\\)/",boolstmtString)) {
      // Aufbau: mk_bass("X",...
      Vector vec = perl.split("/,/",perl.group(1));
      temp = perl.substitute("s/\"//g",(String) vec.elementAt(0));
      temp = checkKeyword(temp, "STM: Bvar {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
      return new BAss(new Bassign(new Bvar(new String(temp)),
                                     createGuard("",(String) vec.elementAt(1)))); }
    else {
      throw new Exception("Boolstmt ("+boolstmtString+") kann nicht erzeugt werden."); }
  }

  private Path createPath(String statename) {
    statename = removeQuotes(statename);
    return (Path) pathHash.get(statename);
  }

  private Guard createGuard(String exprStr, String condStr) throws Exception {
    Vector tempVec = null;
    String temp = null;

    if (perl.match("/^mk_emptyexpr\\(.*\\)/",exprStr))
      return new GuardEmpty(new Dummy());
    else if (perl.match("/^mk_basicexpr\\((.*)\\)/",exprStr)) {
       temp = checkKeyword(removeQuotes(perl.group(1)), "STM: SEvent {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
      return new GuardEvent(new SEvent(temp)); }
    else if (perl.match("/^mk_negexpr\\((.*)\\)/",exprStr))
      return new GuardNeg(createGuard(perl.group(1),""));
    else if (perl.match("/^mk_compe\\(mk_compexpr\\((.*)\\)\\)/",exprStr)) {
      tempVec = deliSplit(perl.group(1),',');
      String op = (String)tempVec.elementAt(0);
      return new GuardCompg(new Compguard(op.equals("ANDOP")? Compguard.AND : Compguard.OR,
                                          createGuard((String)tempVec.elementAt(1),""),
                                          createGuard((String)tempVec.elementAt(2),""))); }

    if (perl.match("/^mk_emptycond\\(.*\\)/",condStr))
      return new GuardEmpty(new Dummy());
    else if (perl.match("/^mk_istrue\\((.*)\\)/",condStr)) { // ??? ?????????????????
      temp = checkKeyword(removeQuotes(perl.group(1)), "STM: Bvar {0} enthält reserviertes Schlüsselwort und wurde {1} umbenannt.");
      return new GuardBVar(new Bvar(temp)); }
    else if (perl.match("/^mk_negcond\\((.*)\\)/",condStr))
      return new GuardNeg(createGuard("",perl.group(1)));
    else if (perl.match("/^mk_instate\\((.*)\\)/",condStr))
      return new GuardCompp(new Comppath(Comppath.IN,createPath(perl.group(1))));
    else if (perl.match("/^mk_compc\\(mk_compcond\\((.*)\\)\\)/",condStr)) {
      tempVec = deliSplit(perl.group(1),',');
      // System.out.println("tempVec: "+tempVec);
      String op = (String)tempVec.elementAt(0);
      return new GuardCompg(new Compguard(op.equals("ANDOP")? Compguard.AND : Compguard.OR,
                                          createGuard("",(String)tempVec.elementAt(1)),
                                          createGuard("",(String)tempVec.elementAt(2)))); }
    else if (perl.match("/^mk_cinstate\\(mk_compinstate\\((.*)\\)\\)/",condStr))
      return new GuardCompp(new Comppath(Comppath.IN,createExternPath(perl.group(1))));
    else if (perl.match("/^mk_rel\\((.*)\\)/",condStr)) {
      showWarning("STM: GuardUndet erzeugt! "+condStr);
      return new GuardUndet(condStr);
    }else {
      showWarning("STM: GuardUndet erzeugt! "+condStr);
      return new GuardUndet(exprStr+", "+condStr);
    }
  }

  private BvarList getBvarList() throws Exception {
    BvarList list = null;
    Vector bvarsVector = splitStringset(bvarsString,"/\",\"/");
    // Vector bvarsVector = deliSplit(bvarsString,',');
    for (int i=bvarsVector.size(); i > 0; i--)
      list = new BvarList(new Bvar(new String(checkKeyword((String) bvarsVector.elementAt(i-1),null))),
                          list);
    return list;
  }

  private void showWarning(String tmpStr) {
    if (gui != null)
      gui.userMessage(tmpStr);
    else
      if (gui.isDebug())
        System.out.println(tmpStr);
  }

  private Path createExternPath(String str) throws Exception{
    Path newPath = null;
    Vector tempVec = null;
    str = perl.substitute("s/<//g",str);
    str = perl.substitute("s/>//g",str);
    str = perl.substitute("s/\"//g",str);
    tempVec = deliSplit(str,',');
    showWarning("STM: Referenz auf externen StateChartname ("+(String)tempVec.elementAt(0)+") kann in PEST nicht abgebildet werden!");
    for (int i=tempVec.size()-1; i>0; i--)
      newPath = new Path((String)tempVec.elementAt(i), newPath);
    return newPath;
  }

  private PathList getPathList() { return getPathList(rootString,null,null); }

  private PathList getPathList(String statename, Path currentPath, PathList pathList) {
    Vector hiVec = null;
    statename = perl.substitute("s/\"//g",statename);
    //System.out.println(statename);
    if (currentPath == null)
      currentPath = new Path(checkKeyword(statename,null),null);
    else
      currentPath = currentPath.append(checkKeyword(statename,null));
    pathList = new PathList(currentPath,pathList);
    pathHash.put(statename,currentPath);
    hiVec = (Vector)hiHash.get(statename);
    if (hiVec != null)
      for (int i=hiVec.size(); i>0; i--)
        pathList = getPathList((String)hiVec.elementAt(i-1),
                               currentPath,pathList);
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
