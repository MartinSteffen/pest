


package editor.desk;

import absyn.*;
import util.*;


public class Delete {

  public State Todelete;
  public State Father;
  public Statechart Tree;


  public Delete( State t, Statechart sc) {

      Todelete = t;
      Father = null;
      Tree = sc;
      this.findfather( Tree.state );

  }

  private void findfather (State akt) {

  StateList stlist = null;

  // Diese Routine findet den direkten Vorg„nger von Todelete und weisst ihm dem Feld
  // Father zu.
  // Danach wird Todelete aus dem Statechart entfernt.

  // Hat akt Kinder?

  if (akt instanceof Or_State) { stlist = ((Or_State)akt).substates; }
  if (akt instanceof And_State) { stlist = ((And_State)akt).substates; }

  // nein -> Routine abbrechen
  if (stlist == null) { return; }

  while (stlist != null) {

    // Ist head der gesuchte Kandidat?

    if (stlist.head == Todelete) {

	    Father = akt;

    	// Umhaengen von Substates falls kein Basic_State

    	if (!(stlist.head instanceof Basic_State)) {
        appendsubs( Todelete, Father );
      }

	    // Kandidat wird aus Baum entfernt:

	    stlist = cuthead(stlist );

      // Name aus Pathlist entfernen:

      updatepathlist ( Todelete.name, Tree.cnames );

      // Zugehoerige Transitionen entfernen:

      updatetranslist ( Todelete.name, Tree.state );

      return;
    }
    // Wenn head nicht der Kandidat ist: Ist head dann Vater vom Kandidat?
    else {
          findfather( stlist.head );

    } // else head == Todelete

    stlist = stlist.tail;
  } // while

  return;

  } // findfather



  private void appendsubs ( State source, State dest ) {

    if ((source instanceof Or_State) && (dest instanceof Or_State)) {
      appendlist ( ((Or_State)source).substates, ((Or_State)dest).substates );
    }
    if ((source instanceof Or_State) && (dest instanceof And_State)) {
      appendlist ( ((Or_State)source).substates, ((And_State)dest).substates );
    }
    if ((source instanceof And_State) && (dest instanceof Or_State)) {
      appendlist ( ((And_State)source).substates, ((Or_State)dest).substates );
    }
    if ((source instanceof And_State) && (dest instanceof And_State)) {
      appendlist ( ((And_State)source).substates, ((And_State)dest).substates );
    }

  }


  private void appendlist (StateList source, StateList dest) {

    if (dest.tail == null) {
      dest.tail = source;

    }
    else {
      appendlist (source, dest.tail);
    }
  }



  private StateList cuthead(StateList sl) {

	// entfernt head aus einer Statelist und haengt den Rest um
	if (sl.tail != null) {

    sl.head = sl.tail.head;
    sl.tail = sl.tail.tail;

  }
	  return sl;


  }

  private void updatepathlist ( Statename n, PathList pl ) {

    if ( pl != null ) {


      removepathname ( n , pl.head );
      updatepathlist ( n, pl.tail );

    }

    if (existsemptypath ( pl )) {
      pl = cuthead ( pl );
    }


  }


  private void removepathname ( Statename n, Path p ) {

    if (p != null) {

         if (p.head == n.name ) {
          p = cuthead ( p );
         }
         else {
          removepathname ( n , p.tail );
         }
    }
  }


  private Path cuthead ( Path p ) {

  // entfernt den head aus einem Path und haengt den Rest auf dessen Posititon
  // Wenn es keinen Rest gibt, m˜te head auf NULL und somit der
  // gesamte Pfad auf NULL gesetzt werden

    if (p.tail != null) {

      p.head = p.tail.head;
      p.tail = p.tail.tail;

    }
    else { p.head = null; }

    return p;

  }

  private void updatetranslist ( Statename n, State s ) {

  // Wenn es Transitionen gibt, die etwas mit n zu tun haben, werden
  // sie aus dem Baum entfernt

  if (s instanceof Or_State) {

  removetrans ( n, ((Or_State)s).trs) ;

  }
  else
  if ( s instanceof And_State) {

    while ( ((And_State)s).substates != null ) {

      updatetranslist ( n , ((And_State)s).substates.head );

      ((And_State)s).substates = ((And_State)s).substates.tail;

    }

  }

  return;

  }


  private void removetrans ( Statename n, TrList trlist ) {

  String src;
  String trg;
  String cmp;

    if ( trlist != null) {


      src = ((Statename)trlist.head.source).name;
      trg = ((Statename)trlist.head.target).name;
      cmp = n.name;

      if ((src == cmp) || (trg == cmp)) {

        trlist = cuthead ( trlist );

        removetrans ( n, trlist );

      }
      else { removetrans ( n, trlist.tail ); }

    }

  }


  private TrList cuthead ( TrList tr ) {

  if (tr.tail != null) {
    tr.head = tr.tail.head;
    tr.tail = tr.tail.tail;
  }
  else { tr = null; }
   return tr;
  }


  private PathList cuthead(PathList pl) {

	// entfernt head aus einer Pathlist und haengt den Rest um
	if (pl.tail != null) {

    pl.head = pl.tail.head;
    pl.tail = pl.tail.tail;

  }
  // else { pl = null; }

	  return pl;

  }

  private boolean existsemptypath ( PathList pl ) {

    // prueft ob es in der aktuellen Pfadliste einen Pfad gibt
    // bei dem sowohl head als auch Tail null ist. Diese Pfadliste
    // wird entfernt.

    PathList dummylist = pl;



    while ( dummylist != null ) {

      Path dummy = dummylist.head;

      while ( dummy != null ) {

        if ((dummy.head == null) && (dummy.tail == null)) { return true; }
        dummy = dummy.tail;
      }

      dummylist = dummylist.tail;

    }


    return false;


  }




} // class

