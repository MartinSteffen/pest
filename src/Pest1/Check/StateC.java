package check;

import absyn.*;

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



