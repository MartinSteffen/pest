package Check;

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
      case   100: {_s = "Doppelte Definition von BVar"; break; }
      case   101: {_s = "Keine Deklaration von BVar"; break; }
      case   102: {_s = "Deklarierte BVar wurde nicht verwendet"; break; }
      
      case   200: {_s = "Doppelte Definition von Event"; break; }
      case   201: {_s = "Keine Deklaration von Event"; break; }
      case   202: {_s = "Deklarierter Event wurde nicht verwendet"; break; }
      case   203: {_s = "Der Pathname in einem GuardComppath ist nicht vorhanden."; break; }
      case   204: {_s = "Der Pathname in einem GuardComppath ist mehrfach vorhanden."; break; }
 
     
      case   400: {_s = "Der Startanker der Transition ist nicht definiert."; break; }
      case   401: {_s = "Der Zielanker der Transition ist nicht definiert."; break; }
      case   402: {_s = "Beide Anker der Transition sind nicht definiert."; break; }
      default:  _s = "Dieser Code wurde noch nicht erfasst.";
    }
    return _s;
  }

}

/* 100-199 BVars Fehler
   200-299 Events Fehler
   300-399 State Fehler
   400-499 Transitions Fehler */
