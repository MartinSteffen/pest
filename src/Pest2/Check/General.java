// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                General
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          30.01.1999
//
// ****************************************************************************

package check;

import absyn.*;     // Objekte

/*
* Basisklasse, die eine gewisse Basis-Funktionialitaet zur Verfuegung
* stellt.
*/
class General {

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  public General() {
  }

// ****************************************************************************
// Methoden
// ****************************************************************************

   // liefert den vollstaendigen Pfad zurueck
   String PathtoString(Path p) {

     String s=p.head;

     p=p.tail;

     for(;p!=null; p=p.tail)
       s=s+"."+p.head;

     return s;
   }


  String getAddPathPart(String p, String n) {
    String s = new String();

    if (p.equals("")) s = n;
                 else s = p + "." + n;

    return s;
  }

  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State)
      nextOrState ((Or_State)sl.head);
    if (sl.head instanceof And_State)
      nextAndState ((And_State)sl.head);
    if (sl.tail != null)
      nextStateInStateList(sl.tail);
  }

  // liefert Quelle einer Transisition zurueck
  String getTrSourceName(Tr t) {
    String s = new String();

    if (t.source instanceof Statename)      { s=((Statename)t.source).name;}
    else if (t.source instanceof Conname)   { s=((Conname)t.source).name;}
    else if (t.source instanceof UNDEFINED) { s="UNDEFINED";}
    return s;
  }


  // liefert Ziel einer Transition zurueck
  String getTrTargetName(Tr t) {

    String s=new String();
    if (t.target instanceof Statename)
      s=((Statename)t.target).name;
    else if (t.target instanceof Conname)
      s=((Conname)t.target).name;
    else if (t.target instanceof UNDEFINED)
      s="UNDEFINED";
    return s;
  }

  void nextOrState(Or_State os) {
    if (os.trs != null)
      nextTransInTransList(os.trs);

    if (os.substates != null)
      nextStateInStateList(os.substates);
  }


  // liefert den naechsten AND state zurueck
  void nextAndState(And_State as) {
    if (as.substates != null) { nextStateInStateList(as.substates); }
  }

  // liefert die naechste Transisation zurueck
  void nextTransInTransList(TrList tl) {
    if (tl.tail != null) { nextTransInTransList(tl.tail); }
  }

}
