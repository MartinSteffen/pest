package Check;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: ErrorAndWarningCodes.java,v 1.8 1998-12-08 14:24:59 swtech11 Exp $
 */
class ErrorAndWarningCodes {
  ErrorAndWarningCodes() {
  }
  
  String codeToString(int _i) {
    String _s = new String();
    switch (_i) {
      case   0: { _s = "Keine Beanstandungen"; }
      case   1: { _s = "Der Syntax Check funktioniert noch nicht einwandfrei."; break; }
      case   2: { _s = "Der Event Check funktioniert noch nicht einwandfrei."; break;  }
      case   3: { _s = "Der State Check funktioniert noch nicht einwandfrei."; break;  }
      case   4: { _s = "Der Transition Check funktioniert noch nicht einwandfrei."; break;  }
      case   5: { _s = "Der BVars Check funktioniert noch nicht einwandfrei."; break;  }
      case   6: { _s = "Der Check auf Kreiheitfreiheit funktioniert noch nicht einwandfrei."; break;  }

      case  10: { _s = "Fataler Fehler: Kreise in den Listen der States"; break;  }
      case  11: { _s = "Fataler Fehler: Kreise in den Listen der Transitionen"; break;  }

      case 100: {_s = "Doppelte Definition von BVar"; break; }
      case 101: {_s = "Keine Deklaration von BVar"; break; }
      case 102: {_s = "Deklarierte BVar wurde nicht verwendet"; break; }
      
      case 200: {_s = "Doppelte Definition von Event"; break; }
      case 201: {_s = "Keine Deklaration von Event"; break; }
      case 202: {_s = "Deklarierter Event wurde nicht verwendet"; break; }
      case 203: {_s = "Der Pathname in einem GuardComppath ist nicht vorhanden."; break; }
      case 204: {_s = "Der Pathname in einem GuardComppath ist mehrfach vorhanden."; break; }

      case 300: {_s = "Mehrfache Definition von Statename"; break; }
      case 301: {_s = "Bezeichnung von Statename nicht eindeutig"; break;}
      case 302: {_s = "Deklarierter State wurde nicht verwendet"; break; }
      case 303: {_s = "Keine Deklaration von Statename"; break; }
      case 304: {_s = "Ein Or-State enthaelt nur einen inneren State"; break; }
      case 305: {_s = "Ein Or-State enthaelt keinen inneren State"; break; }
      case 307: {_s = "Ein And-State enthaelt nur einen inneren State"; break; }
      case 306: {_s = "Ein And-State enthaelt keinen inneren State"; break; }
     
      case 400: {_s = "Der Start-State der Transition ist nicht definiert."; break; }
      case 401: {_s = "Der Ziel-State der Transition ist nicht definiert."; break; }
      case 402: {_s = "Beide States der Transition sind nicht definiert."; break; }
      case 403: {_s = "Der Start-State der Transition ist unbekannt."; break; }
      case 404: {_s = "Der Ziel-State der Transition ist unbekannt."; break; }
      case 405: {_s = "Beide States der Transition sind unbekannt."; break; }
      case 406: {_s = "Interlevel-Transition: Der Start-State liegt falsch."; break; }
      case 407: {_s = "Interlevel-Transition: Der Ziel-State liegt falsch."; break; }
      case 408: {_s = "Interlevel-Transition: Beide States liegten falsch."; break; }
      case 409: {_s = "Der Start-Connenctor der Transition ist unbekannt."; break; }
      case 410: {_s = "Der Ziel-Connenctor der Transition ist unbekannt."; break; }
      case 411: {_s = "Beide Connenctoren der Transition sind unbekannt."; break; }
      case 412: {_s = "Interlevel-Transition: Der Start-Connenctor liegt falsch."; break; }
      case 413: {_s = "Interlevel-Transition: Der Ziel-Connenctor liegt falsch."; break; }
      case 414: {_s = "Interlevel-Transition: Beide Connenctoren liegten falsch."; break; }

      default:  _s = "Dieser Code wurde noch nicht erfasst.";
    }
    return _s;
  }

}

/* 100-199 BVars Fehler
   200-299 Events Fehler
   300-399 State Fehler
   400-499 Transitions Fehler */
