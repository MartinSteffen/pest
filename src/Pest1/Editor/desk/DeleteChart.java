

package editor.desk;
import absyn.*;

/**
 * @author Michael Mai / Martin Bestaendig
 * @version V1.2</dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Loeschen von States funktioniert
 * <li> Loeschen von Connectoren funktioniert
 * <li> Loeschen von Transitionen funktioniert
 * </ul>
 */
public class DeleteChart {

  private Statechart Tree;
  private State father = null;
  private Or_State Transstate = null; // Or_State einer gewählten Transition
  private Or_State Constate = null; // Or_State eines gewählten Connectors

  public DeleteChart(Statechart sc) {
    Tree = sc;
  }

  public void delete_State ( State x ) {

  if (x instanceof Basic_State) { delete_bs ( (Basic_State)x); }
  if (x instanceof Or_State) { delete_os ( (Or_State)x); }
  if (x instanceof And_State) { delete_as ( (And_State)x); }

  }

  public void delete_Trans ( Tr x ) {

    Or_State s = find_trans( x );
    s.trs = remove_Trans ( s.trs, x );

  return;

  }

  public void delete_Con ( Connector x ) {

    Or_State s = find_con( x );
    s.connectors = remove_cons ( s.connectors, x );

    return;

  }


  private void delete_bs (Basic_State x) {


    State father = get_father(Tree.state, x);


    // Fall, wenn Vater = null:

    if (father == null) {
      Tree.state = null;
      return;
    }


    // Fälle, wenn Vater Or_State:
    if (father instanceof Or_State) {

        Or_State os = ((Or_State)father);
        os.substates = remove_State(os.substates, x);
        os.trs = update_trans (os.trs, x);

    // Wenn Vater Or_State ohne Rectangle (imaginärer Or_State)
    // und Vater hat keine Substates mehr, dann l”sche komplette Chart

        if(os.rect == null && os.substates == null) {

          Tree.state = null;
          return;

        }

    // Wenn Vater Or_State, dann schauen, ob x das letzte Element war
    // wenn ja: Vater zum Basic State machen

        if (os.substates == null) {
          make_Basic (father, Tree);
          return;
        }

    // Wenn Vater imaginärer Or_State und nur noch ein Substate vorhanden
    // ist, dann mache diesen Substate zum Vater

        if ( count_subs(os.substates) == 1 && os.rect == null) {
          Tree.state = os.substates.head;
          return;
        }


    } // Vater ist Or_State


    // Fälle, wenn Vater And_State:
    if (father instanceof And_State) {

        // Vater des And-State suchen
        State andfather = get_father( Tree.state, father);

        And_State as = ((And_State)father);
        as.substates = remove_State(as.substates, x);

    // Wenn Vater And_State keine Substates mehr enthält, dann entferne
    // auch den Vater aus dem Baum.

      if (as.substates == null) {
        if (andfather != null) {

          StateList stl = null;
          if (andfather instanceof Or_State) {stl=((Or_State)andfather).substates;}
          if (andfather instanceof And_State){stl=((And_State)andfather).substates;}

         stl = remove_State(stl, father);

        }
        else { Tree.state = null; }
        return;
      }

    // Wenn And_State nur noch einen Substate enthält, dann ersetze den
    // And_State mit diesem:

       if ( count_subs(as.substates) >= 1) {

        if (andfather != null) {

          StateList stl = null;

          if (andfather instanceof Or_State) {
            stl = ((Or_State)andfather).substates;
          }
          if (andfather instanceof And_State) {
            stl = ((And_State)andfather).substates;
          }
          move_coord (as);
          stl = replace_State(stl, father, as.substates.head);
        }
        else { // and_state steht oben, also gleich ersetzen
          move_coord (as);
          Tree.state = as.substates.head;
        }
        return;
       } // count == 1


    } // father = And_State


  } // delete bs

  private void delete_os (Or_State x) {

    State father = get_father(Tree.state, x);


  // Wenn Or_State nur einen sub enthält und sonst nix
  // dann ersetze diesen mit Or_State

  if (father == null && count_subs(x.substates) == 1 &&
    x.trs == null && x.connectors == null) {
    move_coord(x);
    Tree.state = x.substates.head;
    return;
  }

  // Wenn Or_State ganz oben ist und nicht leer, dann mache imaginären Or_State
  // daraus.

  if (father == null && !(is_empty(x)) ) {
    move_coord(x);
    move_trans(x);
    move_cons(x);
    x.rect = null;
    return;
  }

  // Wenn Or_State leer und ganz oben, dann l”sche kompletten Baum

  if (father == null && is_empty(x) ) {
    Tree.state = null;
    return;
  }

  if (father instanceof Or_State) {

    Or_State os = ((Or_State)father);

    // Verschieben der relativen Koordinaten

    move_coord(x);
    move_trans(x);
    move_cons(x);
    
    // x aus der Stateliste des Vaters entfernen
    os.substates = remove_State(os.substates, x);

    // subs von x an Vater anhängen    
    os.substates = append_List(x.substates, os.substates);

    // trans von Todelete an Vater anhängen
    os.trs = append_List(x.trs, os.trs);

    // connectoren von Todelete an Vater anhängen
    os.connectors = append_List(x.connectors, os.connectors);

    return;

  }

   // Fälle, wenn Vater And_State:
  if (father instanceof And_State) {

       // Vater des And-State suchen
       State andfather = get_father( Tree.state, father);

       And_State as = ((And_State)father);
       as.substates = remove_State(as.substates, x);

   // Wenn Vater And_State keine Substates mehr enthält, dann entferne
   // auch den Vater aus dem Baum.

      if (as.substates == null) {
        if (andfather != null) {

          StateList stl = null;
          if (andfather instanceof Or_State) {stl=((Or_State)andfather).substates;}
          if (andfather instanceof And_State){stl=((And_State)andfather).substates;}

         stl = remove_State(stl, father);

        }
        else { Tree.state = null; }
        return;
      }

    // Wenn And_State nur noch einen Substate enthält, dann ersetze den
    // And_State mit diesem:

       if ( count_subs(as.substates) >= 1) {

        if (andfather != null) {

          StateList stl = null;

          if (andfather instanceof Or_State) {
            stl = ((Or_State)andfather).substates;
          }
          if (andfather instanceof And_State) {
            stl = ((And_State)andfather).substates;
          }
          move_coord (as);
          stl = replace_State(stl, father, as.substates.head);
        }
        else { // and_state steht oben, also gleich ersetzen
          move_coord (as);
          Tree.state = as.substates.head;
        }
        return;
       } // count == 1

    } // father = And_State




  }

  private void delete_as (And_State x) {
  }






  // ------------------------------ Tools -------------------------------

  public StateList remove_State(StateList stl, State x) {

    // hängt State aus Liste stl aus und gibt diese zurück

    StateList dummy = stl;
    StateList dummy2 = stl;

    // Fall: Liste hat nur ein Element. Dann Liste komplett l”schen
    if (stl!= null) {
      if (stl.head == x && stl.tail == null) { return null; }
    }


    while (stl != null) {
      if (stl.head == x) {
        if (stl.tail != null) {
          stl.head = stl.tail.head;
          stl.tail = stl.tail.tail;
          return dummy2;
        } else {
          dummy.tail = null;
          return dummy2;
        }
      }
      dummy = stl;
      stl = stl.tail;
    }
    return dummy2;
  } // remove_State

  public StateList replace_State(StateList stl, State x, State s) {

    // Ersetzt State x mit State s
    // Ist x nicht in der Liste passiert nichts.
    // Falls aber die Liste leer ist, wird s angehängt.

   // Zunächst State s aus der Liste entfernen

    stl = remove_State ( stl, s );

    StateList dummy = stl;
    StateList dummy2 = stl;

    // Fall: Liste leer ist, dann s anhängen:
    if (stl == null) {
      stl.head = s;
      stl.tail = null;
    }

    while (stl != null) {
      if (stl.head == x) {
        stl.head = s;
        return dummy2;
      }
      stl = stl.tail;
    }
    return dummy2;
  } // remove_State





    public TrList remove_Trans(TrList trl, Tr x) {

    // hängt State aus Liste stl aus und gibt diese zurück

    TrList dummy = trl;
    TrList dummy2 = trl;

    // Fall: Liste hat nur ein Element. Dann Liste komplett l”schen
    if (trl!= null) {
      if (trl.head == x && trl.tail == null) { return null; }
    }
    while (trl != null) {
      if (trl.head == x) {
        if (trl.tail != null) {
          trl.head = trl.tail.head;
          trl.tail = trl.tail.tail;
          return dummy2;
        } else {
          dummy.tail = null;
          return dummy2;
        }
      }
      dummy = trl;
      trl = trl.tail;
    }
    return dummy2;
  } // remove_Trans


  public ConnectorList remove_cons(ConnectorList cons, Connector x) {

    // hängt Connector aus Liste cons aus und gibt diese zurück

    ConnectorList dummy = cons;
    ConnectorList dummy2 = cons;

    // Fall: Liste hat nur ein Element. Dann Liste komplett l”schen
    if (cons!= null) {
      if (cons.head == x && cons.tail == null) { return null; }
    }
    while (cons != null) {
      if (cons.head == x) {
        if (cons.tail != null) {
          cons.head = cons.tail.head;
          cons.tail = cons.tail.tail;
          return dummy2;
        } else {
          dummy.tail = null;
          return dummy2;
        }
      }
      dummy = cons;
      cons = cons.tail;
    }
    return dummy2;
  } // remove_cons


  public State get_father (State start, State son) {
    this.father = null;
    set_father(start, son);
    return this.father;
  }


  private void set_father(State start, State son) {

 // Setzt Member father


  StateList stl = null;

  if (start instanceof Or_State) { stl = ((Or_State)start).substates; }
  if (start instanceof And_State) { stl = ((And_State)start).substates; }

  while (stl != null) {
    if (stl.head == son) {
      this.father = start;
    }
    else { set_father ( stl.head, son); }
    stl = stl.tail;
  }
  } // set_father


  public void make_Basic ( State x, Statechart sc ) {

    // Wandelt einen (Or oder And) State in einen Basic_State um

    Basic_State bs = new Basic_State (x.name, x.rect);
    StateList stl = null;

    // State father = get_father (sc.state, x, null);

    State father = get_father(Tree.state, x);

    if (father == null) {
      sc.state = bs;
    } else {
      if (father instanceof Or_State) { stl = ((Or_State)father).substates; }
      if (father instanceof And_State) { stl = ((And_State)father).substates; }
      while (stl != null) {
        if (stl.head == x) {
          stl.head = bs;
        }
        stl = stl.tail;
      }
    }
  }  // make_Basic


  public int count_subs ( StateList stl ) {

  // zählt die Anzahl der substates in der Statlist

    int i = 0;

    while (stl != null) {
      i++;
      stl = stl.tail;
    }
    return i;
  }


  private static void move_coord ( State s ) {

    // verschiebt alle Koordinaten der Substates von son
    // um dessen x und y Werte.

    StateList dummylist = null;

    int dx = s.rect == null ? 0 : s.rect.x;
    int dy = s.rect == null ? 0 : s.rect.y;


    if (s instanceof Or_State) { dummylist = ((Or_State)s).substates; };
    if (s instanceof And_State) { dummylist = ((And_State)s).substates; };
    if (s instanceof Basic_State) { return; }

    while (dummylist != null) {

    if (dummylist.head.rect != null) {

      dummylist.head.rect.x = dummylist.head.rect.x + dx;
      dummylist.head.rect.y = dummylist.head.rect.y + dy;

    }

      dummylist = dummylist.tail;

    }

      return;

    } // move coord

    private StateList append_List ( StateList source, StateList target ) {

      // hängt source an target an und gibt die angehängte Liste zurück

      // System.out.println("target: " + target);
      // System.out.println("source: " + source);

      if (target == null) { return source; }

      StateList dummy = target;
      while ( target != null) { target = target.tail; }
      target = source;
      return dummy;

    } // append_List (States)

    private TrList append_List ( TrList source, TrList target ) {

      // hängt source an target an und gibt die angehängte Liste zurück

      if (target == null) { return source; }

      TrList dummy = target;
      while ( target != null) { target = target.tail; }
      target = source;
      return dummy;

    } // append_List

    private ConnectorList append_List ( ConnectorList source, ConnectorList target ) {

      // hängt source an target an und gibt die angehängte Liste zurück

      if (target == null) { return source; }

      ConnectorList dummy = target;
      while ( target != null) { target = target.tail; }
      target = source;
      return dummy;

    } // append_List


  private static void move_trans ( State s ) {

  // Verschiebt alle Transitionen, die sich unter s befinden
  // um dessen Koordinaten

  TrList dummylist = null;

    int dx = s.rect == null ? 0 : s.rect.x;
    int dy = s.rect == null ? 0 : s.rect.y;

    if (s instanceof Or_State) { dummylist = ((Or_State)s).trs; }
    if (s instanceof And_State) { return; }
    if (s instanceof Basic_State) { return; }

    while (dummylist != null) {

    if (dummylist.head != null) {

      for (int i = 0; i < dummylist.head.points.length; i++) {

        dummylist.head.points[i].x = dummylist.head.points[i].x + dx;
        dummylist.head.points[i].y = dummylist.head.points[i].y + dy;

      } // for

    } // if

    dummylist = dummylist.tail;

    } // while

  } // move_trans

  private static void move_cons ( State s ) {

  // Verschiebt alle Connectoren, die sich unter s befinden
  // um dessen Koordinaten

  ConnectorList dummylist = null;

    int dx = s.rect == null ? 0 : s.rect.x;
    int dy = s.rect == null ? 0 : s.rect.y;

    if (s instanceof Or_State) { dummylist = ((Or_State)s).connectors; }
    if (s instanceof And_State) { return; }
    if (s instanceof Basic_State) { return; }

    while (dummylist != null) {

    if (dummylist.head != null) {

        dummylist.head.position.x = dummylist.head.position.x + dx;
        dummylist.head.position.y = dummylist.head.position.y + dy;

    } // if

    dummylist = dummylist.tail;

    } // while

  } // move_cons



  private TrList update_trans(TrList trans, State x) {

  // Entfernt alle Transitionen, die etwas mit x zu tun haben aus
  // der Liste trans und gibt diese zurück

  String name = x.name.name;

  String src = "";
  String tgt = "";

  if (trans == null) { return null; }

  boolean gefunden = true;
  TrList dummy2 = trans;
  TrList dummy = trans;

  while (gefunden) {

  // check soll eventuelle endlosschleife verhindern.
  // kann zwar nicht passieren, aber man weiss ja nie
  int check = 0;

  // Transitionsliste mit über 200 000 Elementen sollte es nicht geben.
  if (check > 200000) { return null; }

  trans = dummy2;
  gefunden = false;

  // Sonderfall: Nur ein Element:
  if (trans.tail == null) {
    src = ((Statename)trans.head.source).name;
    tgt = ((Statename)trans.head.target).name;
    if ((src.equals(name)) || (tgt.equals(name))) {
      return null;
    }
  }

    while(trans != null) {

      check++;

      src = ((Statename)trans.head.source).name;
      tgt = ((Statename)trans.head.target).name;

      if ((src.equals(name)) || (tgt.equals(name))) {
        gefunden = true;
        if (trans.tail != null) {
          trans.head = trans.tail.head;
          trans.tail = trans.tail.tail;
          break;
        }
        else {
          dummy.tail = null;
          break;
        }
      }
      dummy = trans;
      trans = trans.tail;
    } // while trans != null
  } // while gefunden


  return dummy2;

  }

  private boolean is_empty ( Or_State s ) {
  // liefert true, wenn Or_State keine Transitionen
  // States oder Connectoren enthält, false sonst.

  boolean flag = true;

  if (s.substates != null) { flag = false; }
  if (s.trs != null) { flag = false; }
  if (s.connectors != null) { flag = false; }

  return flag;

  } // is_empty


  public Or_State find_trans ( Tr x ) {
    Transstate = null;
    set_transstate( Tree.state, x);
    return Transstate;
  }

  public Or_State find_con ( Connector x ) {
    Constate = null;
    set_constate( Tree.state, x);
    return Constate;
  }

  private void set_transstate ( State s, Tr x ) {

    if (s instanceof Or_State) {
      Or_State os = (Or_State)s;
      TrList trs = os.trs;
      while (trs != null) {
        if (trs.head == x) { Transstate = os; return; }
        trs = trs.tail;
      }
      StateList stl = os.substates;
      while (stl != null) {
        set_transstate ( stl.head, x );
        stl = stl.tail;
      }
    }
    if (s instanceof And_State) {
      And_State as = (And_State)s;
      StateList stl = as.substates;
      while (stl != null) {
        set_transstate ( stl.head, x );
        stl = stl.tail;
      }
    }
    return;
  }

  private void set_constate ( State s, Connector x ) {

    if (s instanceof Or_State) {
      Or_State os = (Or_State)s;
      ConnectorList cons = os.connectors;
      while (cons != null) {
        if (cons.head == x) { Constate = os; return; }
        cons = cons.tail;
      }
      StateList stl = os.substates;
      while (stl != null) {
        set_constate ( stl.head, x );
        stl = stl.tail;
      }
    }

    if (s instanceof And_State) {
      And_State as = (And_State)s;
      StateList stl = as.substates;
      while (stl != null) {
        set_constate ( stl.head, x );
        stl = stl.tail;
      }
    }
    return;
  }


}

