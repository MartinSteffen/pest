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
      default:  _s = "Dieser Code wurde noch nicht erfasst.";
    }
    return _s;
  }

}
