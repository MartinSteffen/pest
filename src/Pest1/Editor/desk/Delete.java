


package editor.desk;

import absyn.*;
import util.*;


public class Delete {

  public static State Todelete;
  public static State Father;
  public static Statechart Tree;

  public Delete( State t, Statechart sc) {

      Todelete = t;
      Father = null;
      Tree = sc;
      this.findfather( Tree.state );

  }

  private void findfather (State akt) {

  StateList stlist = null;
  State head = null;

  // Diese Routine findet den direkten Vorgänger von Todelete und weisst ihm dem Feld
  // Father zu.

  // Hat akt Kinder?

  if (akt instanceof Or_State) { stlist = ((Or_State)akt).substates; }
  if (akt instanceof And_State) { stlist = ((And_State)akt).substates; }

  // nein -> Routine abbrechen
  if (stlist == null) { return; }

  while (stlist != null) {

    head = stlist.head;

    // Ist head der gesuchte Kandidat?
    if (head == Todelete) {


	Father = akt;
	
	// Umhaengen von Substates falls vorhanden:

	appendlist (stlist.head, akt);

	// Kandidat wird aus Baum entfernt
	stlist = cuthead(stlist );
	

        
	
      return;
    }
    // Sonst untersuche head auf Vaterschaft:
    else {
          findfather( head );

    } // else head == Todelete

    stlist = stlist.tail;
  } // while

  return;

  } // findfather


 

  private void appendlist ( State source, State target ) {

    // hängt alle Substates von Source an die von Target an
    

    StateList s = null;
    StateList t = null;


    // Sowohl Source als auch Target dürfen keine Basic_States sein

    if (source instanceof Basic_State) { return; } 
    if (target instanceof Basic_State) { return; } 

    if (source instanceof Or_State) { s = ((Or_State)source).substates; }
    if (source instanceof And_State) { s = ((And_State)source).substates; }
    if (target instanceof Or_State) { t = ((Or_State)source).substates; }
    if (target instanceof And_State) { t = ((And_State)source).substates; }



    while ( t != null ) {

      t = t.tail;

    }

    t = s;

  }

    private StateList cuthead(StateList sl) {

	// entfernt head aus einer Statelist und haengt den Rest um
	if (sl.tail != null) { sl.head = sl.tail.head; sl.tail = sl.tail.tail; }
	return sl;


    }

} // class

