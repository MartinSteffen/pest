package util;

import absyn.*;
import java.util.*;
import stm.HAImport;
import java.io.*;
/**
 * Die Klasse ChartTools dient zur Komposition von Statecharts und
 * zur Extrahierung von Statechart-States zu eigenst&auml;ndigen
 * Statecharts.
 * Die Hauptmethoden sind Klassenmethoden; die Konstruktion einer
 * ChartTools-Klasse ist also nicht erforderlich.
 * <DL COMPACT>
 * <DT><STRONG>STATUS.</STRONG>
 * compChart und insertChart implementiert.
 * <DT><STRONG>TODO.</STRONG>
 * Verbesserungen und Implementierung von extractChart!
 * <DT><STRONG>BEKANNTE FEHLER.</STRONG>
 * Keine.
 * <DT><STRONG>FORDERUNGEN.</STRONG>
 * Statechart-Parameter m&uuml;ssen korrekte PEST-Statecharts sein, die vom
 * Syntaxcheck akzeptiert werden.
 * <DT><STRONG>HINWEIS.</STRONG>
 * Die Methode main dient uns intern zum Testen ohne GUI.
 * </DL COMPACT>
 *
 * @author  Sven Jorga, Werner Lehmann
 * @version $Id: ChartTools.java,v 1.1 1999-02-10 23:14:22 swtech18 Exp $
 */

public class ChartTools {

  private static final String CHANGESYM = "_1";
  /** Main: Nur zum internen Testen; nicht benutzen. */

  public static void main(String[] args) throws Exception{
    PrettyPrint pp = new PrettyPrint ();
    Statechart a = null;
    Statechart b = null;
    Statechart sc = null;

    if (args.length < 1) {
      System.out.println("Bitte Parameter angeben:");
      System.out.println("ChartTools <Action> <Dest-Statechart> <Src-Statechart> <Dateiname> [<New-Statechart>|<Path-String>]");
      System.out.println(" Action: c | i - compCahrt oder insertCahrt");
      System.exit(0);
    }
    if (args[0].equals("i") && args.length >= 5) {
      a = load(args[1]);
      b = load(args[2]);
      Path p = makePath(args[4]);
      sc = insertChart(a,b,p,true,true);
      pp.start(sc);
      save(sc,args[3]);
    }else if (args[0].equals("c") && args.length >= 5) {
      //pp.start (compChart(a,b,"CompState", false, false));
      //Statechart sc = compChart(a,b,"CompState", false, false);
    }else {
      System.out.println("ChartTools: unbekannte Action und/oder zuwenig Parameter!");
      System.exit(0);
    }
  }

  /** Komposition von a und b als Substates eines AND-States.
   *  newAndStatename wird der neue Statechart-Namen des Root-And-States.
   *  Die Parameter useSameEvents und useSameBvars geben an, ob beide
   *  vereinigte Charts die gleichen Events und/oder Bvars benutzen
   */

  public static Statechart compChart(Statechart a, Statechart b, String newAndStateName, boolean useSameEvents, boolean useSameBvars) throws Exception{
    Statechart tmpA = (Statechart)a.clone();
    Statechart tmpB = (Statechart)b.clone();
    String newRoot = null;
    // Wenn Root-Statename von a gleich Root-Statename von b -> Name von b aendern
    if (tmpA.state.name.name.equals(tmpB.state.name.name)) {
      newRoot = tmpA.state.name.name+CHANGESYM;
      tmpB.state.name.name = newRoot;
    }
    tmpA = changePathnames(tmpA,newAndStateName,null);
    tmpB = changePathnames(tmpB,newAndStateName,newRoot);
    // useSameEvents == false -> umbenennen der doppelten SEvents
    if (tmpA.events != null)
      tmpB = removeDuplicateSEvent(tmpA.events,tmpB,useSameEvents);
    // useSameBvars == false -> umbenennen der doppelten Bvars
    if (tmpA.bvars != null)
      tmpB = removeDuplicateBvar(tmpA.bvars,tmpB,useSameBvars);
    // Koordinaten ändern
    if (tmpB.state.rect != null && tmpA.state.rect != null)
      tmpB.state.rect.x = tmpA.state.rect.width + 25;

    return new Statechart(mergeSEventList(tmpA.events,tmpB.events),
                          mergeBvarList(tmpA.bvars, tmpB.bvars),
                          mergePathList(tmpA.cnames, tmpB.cnames),
                          new And_State(new Statename(newAndStateName),
                                        new StateList(tmpA.state, new StateList(tmpB.state, null)),
                                        tmpA.state.rect!=null?(CRectangle) tmpA.state.rect.union(tmpB.state.rect)
                                                          :(CRectangle)tmpB.state.rect
                                        )
                          );
  }

  /** Einf&uuml;gen der Statechart src in dest an Stelle des States mit Path pos(muß aus dest sein).
   *  Die Parameter useSameEvents und useSameBvars geben an, ob beide
   *  vereinigte Charts die gleichen Events und/oder Bvars benutzen
   */

  public static Statechart insertChart(Statechart dest, Statechart src, Path pos, boolean useSameEvents, boolean useSameBvars) throws Exception{
    Statechart tmpDest = (Statechart)dest.clone();
    Statechart tmpSrc = (Statechart)src.clone();

    // Pathnames anpassen (in PathList und in TLabels aller States des Charts)
    tmpSrc = changePathnames(tmpSrc,pos);
    tmpDest.cnames = removePathFrom(tmpDest.cnames,pos);
    // Wenn !useSameBvars -> Vorkommen doppelter Bvars checken
    if (tmpDest.bvars != null || tmpSrc.bvars != null)
      tmpSrc = removeDuplicateBvar(tmpDest.bvars,tmpSrc,useSameBvars);
    // Wenn !useSameSEvents -> Vorkommen doppelter SEvents checken
    if (tmpDest.events != null || tmpSrc.events != null)
      tmpSrc = removeDuplicateSEvent(tmpDest.events,tmpSrc,useSameEvents);
    // PathLists der Statecharts zusammenführen
    tmpDest.cnames = mergePathList(tmpDest.cnames, tmpSrc.cnames);
    // BvarLists der Statecharts zusammenführen
    tmpDest.bvars = mergeBvarList(tmpDest.bvars, tmpSrc.bvars);
    // SEventsLists der Statecharts zusammenführen
    tmpDest.events = mergeSEventList(tmpDest.events,tmpSrc.events);
    // insertStateAt(Statechart dest, State src, Path pos);
    tmpDest.state = insertStateAt(tmpDest.state,tmpSrc.state,pos);
    return tmpDest;
  }

  /** State pos aus src als eigenst&auml;ndigen Statechart liefern. */

  public static Statechart extractChart(Statechart src, Path pos) throws Exception{
    return null;
  }

  private static Path makePath(String str) {
    String tmpStr = "";
    Path p = null;
    char c;
    for(int i=str.length()-1; i>=0; i--) {
      if ((c = str.charAt(i)) == '.' || i == 0) {
        if (i==0)
          tmpStr = c + tmpStr;
        p = new Path(tmpStr,p);
        tmpStr = "";
      }else
        tmpStr = c + tmpStr;
    }
    return p;
  }
  
  private static Statechart load(String filename) throws Exception{
      FileInputStream fis = new FileInputStream(filename);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    Statechart sc = (Statechart) ois.readObject();
	    ois.close();
	    return sc;
  }

  private static void save(Statechart sc, String filename) throws Exception{
    FileOutputStream fos = new FileOutputStream(filename);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(sc);
    oos.flush();
    oos.close();
  }

  private static State insertStateAt(State dest, State src, Path pos) throws Exception{

    if(dest.name.name.equals(pos.head)) {
      if(dest instanceof Basic_State && pos.tail == null) {
        CRectangle cr = (CRectangle)dest.rect.clone();
        dest = src;
        dest.rect = cr;
      }else if(dest instanceof Or_State)
        if (pos.tail == null) {
          CRectangle cr = (CRectangle)dest.rect.clone();
          dest = src;
          dest.rect = cr;
        }else
          ((Or_State)dest).substates = insertStateAt(((Or_State)dest).substates, src ,pos.tail);
      else if(dest instanceof And_State)
        if (pos.tail == null){
          CRectangle cr = (CRectangle)dest.rect.clone();
          dest = src;
          dest.rect = cr;
        }else
          ((And_State)dest).substates = insertStateAt(((And_State)dest).substates, src ,pos.tail);
    }
    return dest;
  }

  private static StateList insertStateAt(StateList sl, State src, Path pos) throws Exception{
    StateList current = sl;
    while (current != null) {
      current.head = insertStateAt(current.head,src,pos);
      current = current.tail;
    }
    return sl;
  }

  private static void getStateWithName(StateList sl, State src, Path pos) {
    StateList current = sl;
    while (current != null) {
      getStateWithName(current.head,src,pos);
      current = current.tail;
    }
  }

  private static void getStateWithName(State s, State src, Path pos) {
    if (s instanceof Or_State) {
      Or_State tmpOrState = (Or_State)s;
      if (pos.head.equals(tmpOrState.name.name)) {
        if (pos.tail != null)
          getStateWithName(tmpOrState.substates,src,pos.tail);
        else
          s = src;
      }
    }else if (s instanceof And_State) {
      And_State tmpAndState = (And_State)s;
      if (pos.head.equals(tmpAndState.name.name)) {
        if (pos.tail != null)
          getStateWithName(tmpAndState.substates,src,pos.tail);
        else
          s = src;
      }
    }else if (s instanceof Basic_State || s instanceof Ref_State) {
      Basic_State tmpBasicState = (Basic_State)s;
      if (pos.head.equals(tmpBasicState.name.name) && pos.tail == null)
        s = src;
    }
  }

  private static Statechart changePathnames(Statechart sc, Path pos) throws Exception{
    sc.cnames = changePathList(sc.cnames, pos);
    // Alle State-TrList-TLabels auf das Vorkommen von Pathnames untersuchen und erweitern mit pos
    sc.state = changeState(sc.state,pos);
    return sc;
  }

  private static PathList removePathFrom(PathList pl, Path pos) throws Exception{
    Path currentPosPath = pos;
    Path currentPathListPath = pl.head;
    while (currentPosPath != null) {
      if (currentPathListPath != null && currentPathListPath.head.equals(currentPosPath.head)) {
        currentPathListPath = currentPathListPath.tail;
        currentPosPath = currentPosPath.tail;
      }else {
        pl.tail = removePathFrom(pl.tail,pos);
        return pl;
      }
    }
    pl = pl.tail;

    return pl;
  }

  private static PathList changePathList(PathList list, Path pos) throws Exception{
    PathList current = list;
    Path start = null, last = null;
    Path appendPos = null;
    while (current != null) {
      start = (Path)pos.clone();
      last = start;
      // Ende von pos suchen
      while (last != null) {
        appendPos = last ;
        last = last.tail;
      }
      // last zeigt jetzt auf das letzte Element der Liste start
      // An appendPos.tail den Path current.head.tail anfügen
      appendPos.tail = current.head.tail;
      // Aktueller Path wird neue zusammengefuegte Liste
      current.head = start;
      // Naechster Path der PathList bearbeiten
      current = current.tail;
    }
    return list;
  }

  private static StateList changeStateList(StateList sl, Path pos) {
    StateList current = sl;
    while (current != null) {
      current.head = changeState(current.head,pos);
      current = current.tail;
    }
    return sl;
  }

  private static State changeState(State s, Path pos) {
    TrList current = null;
    if (s instanceof Or_State) {
      current = ((Or_State)s).trs;
      while (current != null) {
        current.head.label = changePathOfTLabels(current.head.label,pos);
        current = current.tail;
      }
      ((Or_State)s).substates = changeStateList(((Or_State)s).substates,pos);
    }else if (s instanceof And_State) {
      ((And_State)s).substates = changeStateList(((And_State)s).substates,pos);
    }
    return s;
  }

  private static TLabel changePathOfTLabels(TLabel tl, Path pos) {
    tl.guard = changeGuard(tl.guard, pos);
    return tl;
  }

  private static Guard changeGuard(Guard g, Path pos) {
    Path currentPath = null;
    if (g instanceof GuardCompp) {
      GuardCompp tmpGuardCompp = (GuardCompp)g;
      currentPath = tmpGuardCompp.cpath.path.tail;
      while (currentPath != null) {
        pos = pos.append(currentPath.head);
        tmpGuardCompp.cpath.path = pos;
        currentPath = currentPath.tail;
      }
      return tmpGuardCompp;
    }else if (g instanceof GuardCompg) {
      GuardCompg tmpGuardCompg = (GuardCompg)g;
      tmpGuardCompg.cguard.elhs = changeGuard(tmpGuardCompg.cguard.elhs,pos);
      tmpGuardCompg.cguard.erhs = changeGuard(tmpGuardCompg.cguard.erhs,pos);
      return tmpGuardCompg;
    }else
      return g;
  }

  private static Statechart changePathnames(Statechart sc, String prefix, String newRoot) {
    sc.cnames = changePathList(sc.cnames, prefix, newRoot);
    // Alle State-TrList-TLabels auf das Vorkommen von Pathnames untersuchen und umbenennen
    sc.state = changeState(sc.state,prefix,newRoot);
    return sc;
  }

  private static PathList changePathList(PathList list, String prefix, String newRoot) {
    PathList current = list;
    while (current != null) {
      if (newRoot != null)
        current.head.head = newRoot;
      current.head = new Path(prefix,current.head);
      current = current.tail;
    }
    return list;
  }

  private static StateList changeStateList(StateList sl, String prefix, String newRoot) {
    StateList current = sl;
    while (current != null) {
      current.head = changeState(current.head,prefix,newRoot);
      current = current.tail;
    }
    return sl;
  }

  private static State changeState(State s, String prefix, String newRoot) {
    TrList current = null;
    if (s instanceof Or_State) {
      current = ((Or_State)s).trs;
      while (current != null) {
        current.head.label = changePathOfTLabels(current.head.label,prefix, newRoot);
        current = current.tail;
      }
      ((Or_State)s).substates = changeStateList(((Or_State)s).substates,prefix,newRoot);
    }else if (s instanceof And_State) {
      ((And_State)s).substates = changeStateList(((And_State)s).substates,prefix,newRoot);
    }
    return s;
  }

  private static TLabel changePathOfTLabels(TLabel tl, String prefix, String newRoot) {
    tl.guard = changeGuard(tl.guard,prefix,newRoot);
    return tl;
  }

  private static Guard changeGuard(Guard g,String prefix, String newRoot) {
    if (g instanceof GuardCompp) {
      GuardCompp tmpGuardCompp = (GuardCompp)g;
      if (newRoot != null)
        tmpGuardCompp.cpath.path.head = newRoot;
      tmpGuardCompp.cpath.path = new Path(prefix,tmpGuardCompp.cpath.path);
      return tmpGuardCompp;
    }else if (g instanceof GuardCompg) {
      GuardCompg tmpGuardCompg = (GuardCompg)g;
      tmpGuardCompg.cguard.elhs = changeGuard(tmpGuardCompg.cguard.elhs,prefix,newRoot);
      tmpGuardCompg.cguard.erhs = changeGuard(tmpGuardCompg.cguard.erhs,prefix,newRoot);
      return tmpGuardCompg;
    }else
      return g;
  }

  private static SEventList mergeSEventList(SEventList a, SEventList b) throws Exception{
    SEventList newList = null;
    SEventList current = null, last = null;
    if (a == null)
      return b;
    newList = (SEventList) a.clone();
    current = newList;
    // Ende der Liste a suchen
    while (current != null) {
      last = current;
      current = current.tail;
    }
    // last zeigt jetzt auf das letzte Element der Liste a
    // Liste b an a anhaengen
    last.tail = b;
    return newList;
  }

  private static BvarList mergeBvarList(BvarList a, BvarList b) throws Exception{
    BvarList newList = null;
    BvarList current = null, last = null;
    if (a == null)
      return b;
    newList = (BvarList)a.clone();
    current = newList;
    // Ende der Liste a suchen
    while (current != null) {
      last = current;
      current = current.tail;
    }
    // last zeigt jetzt auf das letzte Element der Liste a
    // Liste b an a anhaengen
    last.tail = b;
    return newList;
  }

  private static PathList mergePathList(PathList a, PathList b) throws Exception{
    PathList newList = null;
    PathList current = null, last = null;
    if (a == null)
      return b;
    newList = (PathList)a.clone();
    current = newList;
    // Ende der Liste a suchen
    while (current != null) {
      last = current;
      current = current.tail;
    }
    // last zeigt jetzt auf das letzte Element der Liste a
    // Liste b an a anhaengen
    last.tail = b;
    return newList;
  }

  private static Statechart removeDuplicateSEvent(SEventList from, Statechart toClean, boolean useSameEvents) {
    Vector tmpVec = new Vector();
    SEventList current = from;
    SEventList newList = null;

    // Vector mit Events aufbauen.
    while (current != null) {
      tmpVec.addElement(current.head.name);
      current = current.tail;
    }
    current = toClean.events;
    // Bereinigte SEvent-Liste aufbauen
    while (current != null) {
      if (tmpVec.contains(current.head.name)) {
        if (!useSameEvents) {
          while (tmpVec.contains(current.head.name))
            current.head.name = current.head.name+CHANGESYM;
          newList = new SEventList(current.head,newList);
        }
      }else
        newList = new SEventList(current.head,newList);
      current = current.tail;
    }
    // TLabels auf das Vorkommen von SEvents untersuchen und umbenennen
    if (!useSameEvents)
      toClean.state = changeSEventsOfState(toClean.state,tmpVec);
    toClean.events = newList;
    return toClean;
  }

  private static StateList changeSEventsOfStateList(StateList sl, Vector sEventsVec) {
    StateList current = sl;
    while (current != null) {
      current.head = changeSEventsOfState(current.head,sEventsVec);
      current = current.tail;
    }
    return sl;
  }

  private static State changeSEventsOfState(State s, Vector sEventsVec) {
    TrList current = null;
    if (s instanceof Or_State) {
      current = ((Or_State)s).trs;
      while (current != null) {
        current.head.label = changeSEventsOfTLabel(current.head.label,sEventsVec);
        current = current.tail;
      }
      ((Or_State)s).substates = changeSEventsOfStateList(((Or_State)s).substates,sEventsVec);
    }else if (s instanceof And_State) {
      ((And_State)s).substates = changeSEventsOfStateList(((And_State)s).substates,sEventsVec);
    }
    return s;
  }

  private static TLabel changeSEventsOfTLabel(TLabel tl, Vector sEventsVec) {
    tl.guard = changeSEventsOfGuard(tl.guard,sEventsVec);
    tl.action = changeSEventsOfAction(tl.action,sEventsVec);
    return tl;
  }

  private static Guard changeSEventsOfGuard(Guard g, Vector sEventsVec) {
    if (g instanceof GuardEvent){
      GuardEvent tmpGuard = (GuardEvent)g;
      while (sEventsVec.contains(tmpGuard.event.name))
        tmpGuard.event.name = tmpGuard.event.name+CHANGESYM;
      return tmpGuard;
    }else if (g instanceof GuardCompg) {
      GuardCompg tmpGuardCompg = (GuardCompg)g;
      tmpGuardCompg.cguard.elhs = changeSEventsOfGuard(tmpGuardCompg.cguard.elhs,sEventsVec);
      tmpGuardCompg.cguard.erhs = changeSEventsOfGuard(tmpGuardCompg.cguard.erhs,sEventsVec);
      return tmpGuardCompg;
    }
    return g;
  }

  private static Action changeSEventsOfAction(Action a, Vector sEventsVec) {
    if (a instanceof ActionStmt){
      ActionStmt tmpActionStmt = (ActionStmt)a;
      tmpActionStmt.stmt = changeSEventsOfBoolstmt(tmpActionStmt.stmt,sEventsVec);
    }else if (a instanceof ActionEvt){
      ActionEvt tmpAction = (ActionEvt)a;
      while (sEventsVec.contains(tmpAction.event.name))
        tmpAction.event.name = tmpAction.event.name+CHANGESYM;
      return tmpAction;
    }else if (a instanceof ActionBlock) {
      ActionBlock tmpActionBlock = (ActionBlock)a;
      Aseq currentAseq = tmpActionBlock.aseq;
      while (currentAseq != null) {
        currentAseq.head = changeSEventsOfAction(currentAseq.head,sEventsVec);
        currentAseq = currentAseq.tail;
      }
      return tmpActionBlock;
    }
    return a;
  }

  private static State changeBvarOfState(State s, Vector bvarVec) {
    TrList current = null;
    if (s instanceof Or_State) {
      current = ((Or_State)s).trs;
      while (current != null) {
        current.head.label = changeBvarOfTLabel(current.head.label,bvarVec);
        current = current.tail;
      }
      ((Or_State)s).substates = changeBvarOfStateList(((Or_State)s).substates,bvarVec);
    }else if (s instanceof And_State) {
      ((And_State)s).substates = changeBvarOfStateList(((And_State)s).substates,bvarVec);
    }
    return s;
  }

  private static StateList changeBvarOfStateList(StateList sl, Vector bvarVec) {
    StateList current = sl;
    while (current != null) {
      current.head = changeBvarOfState(current.head,bvarVec);
      current = current.tail;
    }
    return sl;
  }

  private static TLabel changeBvarOfTLabel(TLabel tl, Vector bvarVec) {
    tl.guard = changeBvarOfGuard(tl.guard,bvarVec);
    tl.action = changeBvarOfAction(tl.action,bvarVec);
    return tl;
  }

  private static Guard changeBvarOfGuard(Guard g, Vector bvarVec) {
    if (g instanceof GuardBVar){
      GuardBVar tmpGuard = (GuardBVar)g;
      while (bvarVec.contains(tmpGuard.bvar.var))
        tmpGuard.bvar.var = tmpGuard.bvar.var+CHANGESYM;
      return tmpGuard;
    }else if (g instanceof GuardCompg) {
      GuardCompg tmpGuardCompg = (GuardCompg)g;
      tmpGuardCompg.cguard.elhs = changeBvarOfGuard(tmpGuardCompg.cguard.elhs,bvarVec);
      tmpGuardCompg.cguard.erhs = changeBvarOfGuard(tmpGuardCompg.cguard.erhs,bvarVec);
      return tmpGuardCompg;
    }
    return g;
  }

  private static Boolstmt changeBvarOfBoolstmt(Boolstmt b, Vector bvarVec) {
    if (b instanceof MFalse){
      MFalse tmpMFalse = (MFalse) b;
      while (bvarVec.contains(tmpMFalse.var.var))
        tmpMFalse.var.var = tmpMFalse.var.var+CHANGESYM;
    }else if (b instanceof MTrue){
      MTrue tmpMTrue = (MTrue) b;
      while (bvarVec.contains(tmpMTrue.var.var))
        tmpMTrue.var.var = tmpMTrue.var.var+CHANGESYM;
    }else if (b instanceof BAss){
      BAss tmpBAss = (BAss) b;
      tmpBAss.ass.blhs.var = tmpBAss.ass.blhs.var+CHANGESYM;
      tmpBAss.ass.brhs = changeBvarOfGuard(tmpBAss.ass.brhs,bvarVec);
    }
    return b;
  }

  private static Boolstmt changeSEventsOfBoolstmt(Boolstmt b, Vector sEventVec) {
    if (b instanceof BAss){
      BAss tmpBAss = (BAss) b;
      tmpBAss.ass.brhs = changeSEventsOfGuard(tmpBAss.ass.brhs,sEventVec);
    }
    return b;
  }

  private static Action changeBvarOfAction(Action a, Vector bvarVec) {
    if (a instanceof ActionStmt){
      ActionStmt tmpActionStmt = (ActionStmt)a;
      tmpActionStmt.stmt = changeBvarOfBoolstmt(tmpActionStmt.stmt,bvarVec);
    }else if (a instanceof ActionBlock) {
      ActionBlock tmpActionBlock = (ActionBlock)a;
      Aseq currentAseq = tmpActionBlock.aseq;
      while (currentAseq != null) {
        currentAseq.head = changeBvarOfAction(currentAseq.head,bvarVec);
        currentAseq = currentAseq.tail;
      }
    }
    return a;
  }

  private static Statechart removeDuplicateBvar(BvarList from, Statechart toClean, boolean useSameBvar) {
    Vector tmpVec = new Vector();
    BvarList current = from;
    BvarList newList = null;

    // Vector mit Bvars aufbauen.
    while (current != null) {
      tmpVec.addElement(current.head.var);
      current = current.tail;
    }
    current = toClean.bvars;
    // Bereinigte Bvar-Liste aufbauen
    while (current != null) {
      if (tmpVec.contains(current.head.var)) {
        if (!useSameBvar) {
          while (tmpVec.contains(current.head.var))
            current.head.var = current.head.var+CHANGESYM;
          newList = new BvarList(current.head,newList);
        }
      }else
        newList = new BvarList(current.head,newList);
      current = current.tail;
    }
    // TLabels auf das Vorkommen von SEvents untersuchen und umbenennen
    if (!useSameBvar)
      toClean.state = changeBvarOfState(toClean.state,tmpVec);
    toClean.bvars = newList;
    return toClean;
  }

  /*private static Absyn mergeAbsynList(Absyn a, Absyn b) throws Exception{
    Absyn newList = null;
    Absyn current = null;
    if (a instanceof SEventList && b instanceof SEventList) {
      newList = (SEventList) a.clone();
      current = newList;
    }else if (a instanceof PathList && b instanceof PathList) {
      newList = (PathList)a.clone();
      current = newList;
    }else if (a instanceof BvarList && b instanceof BvarList) {
      newList = (BvarList)a.clone();
      current = newList;
    } else throw new Exception("mergeAbsynList kann nur mit Listen von Absyn"+
                               "genutzt werden!");
    // Ende der Liste a suchen
    while (current != null)
      current = ((SEventList)current).tail;
    // last zeigt jetzt auf das letzte Element der Liste a
    // Liste b an a anhaengen
    current = b;
    return newList;
  } */
}