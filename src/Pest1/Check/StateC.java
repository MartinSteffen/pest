package check;

import absyn.*;

/**
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: StateC.java,v 1.3 1999-02-14 20:56:30 swtech11 Exp $
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



