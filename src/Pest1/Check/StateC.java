package check;

import absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: StateC.java,v 1.2 1999-01-28 20:56:22 swtech11 Exp $
 */
class StateC {
  State s = null;
  Statename sn = null;
  String Pfad = new String();
  String Ort = new String();

  StateC() {}

  StateC(State _s, String _ort, String _pfad) {
    s   = _s;
    Pfad = _pfad;
    Ort   = _ort;
    sn = s.name;
  }

  StateC(Statename _sn, String _ort, String _pfad) {
    sn   = _sn;
    Pfad = _pfad;
    Ort   = _ort;
    s = null;
  }
}  



