package check;

import java.util.*;

/**
 * Grobe Fehlereinordnung:
 *     0- 99 allgemein und fatal
 *   100-199 BVars Fehler
 *   200-299 Events Fehler
 *   300-399 State Fehler
 *   400-499 Transitions Fehler
 *
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: ErrorAndWarningCodes.java,v 1.37 1999-02-14 20:56:25 swtech11 Exp $
 */
class ErrorAndWarningCodes {

  ErrorAndWarningCodes() { }

  // Vector mit allen Warnungs Codes
  Vector codeWarnings() {
    Vector v = new Vector();
    v.addElement(new Integer(1));   v.addElement(new Integer(2));
    v.addElement(new Integer(3));   v.addElement(new Integer(4));
    v.addElement(new Integer(5));   v.addElement(new Integer(6));
    v.addElement(new Integer(102)); v.addElement(new Integer(104));
    v.addElement(new Integer(105)); v.addElement(new Integer(110));
    v.addElement(new Integer(202)); v.addElement(new Integer(204));
    v.addElement(new Integer(205)); v.addElement(new Integer(210));
    v.addElement(new Integer(304)); v.addElement(new Integer(307));
    v.addElement(new Integer(416));
    return v;
  }

  // Vector mit allen Error Codes
  Vector codeErrors() {
    Vector v = new Vector();
    v.addElement(new Integer(10));  v.addElement(new Integer(11));  v.addElement(new Integer(12));  v.addElement(new Integer(13));
    v.addElement(new Integer(14));  v.addElement(new Integer(15));  v.addElement(new Integer(16));  v.addElement(new Integer(17));
    v.addElement(new Integer(18));  v.addElement(new Integer(19));  v.addElement(new Integer(20));  v.addElement(new Integer(21));
    v.addElement(new Integer(21));  v.addElement(new Integer(23));  v.addElement(new Integer(24));  v.addElement(new Integer(99));
    v.addElement(new Integer(100)); v.addElement(new Integer(101)); v.addElement(new Integer(103)); v.addElement(new Integer(106));
    v.addElement(new Integer(200)); v.addElement(new Integer(201)); v.addElement(new Integer(203));
    v.addElement(new Integer(206)); v.addElement(new Integer(300)); v.addElement(new Integer(301)); v.addElement(new Integer(302));
    v.addElement(new Integer(303)); v.addElement(new Integer(305)); v.addElement(new Integer(306)); v.addElement(new Integer(308));
    v.addElement(new Integer(309)); v.addElement(new Integer(310)); v.addElement(new Integer(311)); v.addElement(new Integer(312));
    v.addElement(new Integer(313)); v.addElement(new Integer(314)); v.addElement(new Integer(315)); v.addElement(new Integer(316));
    v.addElement(new Integer(317)); v.addElement(new Integer(400)); v.addElement(new Integer(401)); v.addElement(new Integer(402));
    v.addElement(new Integer(403)); v.addElement(new Integer(404)); v.addElement(new Integer(405)); v.addElement(new Integer(406));
    v.addElement(new Integer(407)); v.addElement(new Integer(408)); v.addElement(new Integer(409)); v.addElement(new Integer(410));
    v.addElement(new Integer(411)); v.addElement(new Integer(412)); v.addElement(new Integer(413)); v.addElement(new Integer(414));
    v.addElement(new Integer(415)); v.addElement(new Integer(417)); v.addElement(new Integer(418)); v.addElement(new Integer(419));
    v.addElement(new Integer(420)); v.addElement(new Integer(421)); v.addElement(new Integer(422)); v.addElement(new Integer(423));
    v.addElement(new Integer(424)); v.addElement(new Integer(425)); v.addElement(new Integer(426));        
    return v;
  }


  String codeToString(int _i) {
    String _s = new String();
    switch (_i) {
      case   0: {_s = "Keine Beanstandungen"; }
      case   1: {_s = "Der Syntax Check funktioniert noch nicht einwandfrei."; break; }
      case   2: {_s = "Der Event Check funktioniert noch nicht einwandfrei."; break;  }
      case   3: {_s = "Der State Check funktioniert noch nicht einwandfrei."; break;  }
      case   4: {_s = "Der Transition Check funktioniert noch nicht einwandfrei."; break;  }
      case   5: {_s = "Der BVars Check funktioniert noch nicht einwandfrei."; break;  }
      case   6: {_s = "Der Check auf Fehler der Programmierer funktioniert noch nicht einwandfrei."; break;  }
      case  10: {_s = "Fataler Fehler: EIN Objekt des Types Or-State wird mehrfach referenziert."; break;  }
      case  11: {_s = "Fataler Fehler: EIN Objekt des Types And-State wird mehrfach referenziert."; break;  }
      case  12: {_s = "Fataler Fehler: EIN Objekt des Types Basic-State wird mehrfach referenziert."; break;  }
      case  13: {_s = "Fataler Fehler: EIN Objekt des Types Transition wird mehrfach referenziert."; break;  }
      case  14: {_s = "Fataler Fehler: EIN Objekt des Types TransitionList wird mehrfach referenziert."; break;  }
      case  15: {_s = "Fataler Fehler: EIN Objekt des Types PathList wird mehrfach referenziert."; break;  }
      case  16: {_s = "Fataler Fehler: EIN Objekt des Types Path wird in EINER PathList mehrfach referenziert."; break;  }
      case  17: {_s = "Fataler Fehler: EIN Objekt des Types EventList wird mehrfach referenziert."; break;  }
      case  18: {_s = "Fataler Fehler: EIN Objekt des Types Event wird in der EventList mehrfach referenziert."; break;  }
      case  19: {_s = "Fataler Fehler: EIN Objekt des Types BVarList wird mehrfach referenziert."; break;  }
      case  20: {_s = "Fataler Fehler: EIN Objekt des Types BVar wird in der BVarList mehrfach referenziert."; break;  }
      case  21: {_s = "Fataler Fehler: EIN Objekt des Typs Label wird in Transitionen mehrfach referenziert."; break;  }
      case  22: {_s = "Fataler Fehler: EIN Objekt des Typs Gauard wird in Transitionen mehrfach referenziert."; break;  }
      case  23: {_s = "Fataler Fehler: EIN Objekt des Typs Actions wird in Transitionen mehrfach referenziert."; break;  }
      case  24: {_s = "Fataler Fehler: EIN Objekt des Typs Aseq ist ein Nullpointer."; break;  }
      case  99: {_s = "Aufgrund eines fatalen Fehlers wird der Syntax Check abgebrochen."; break;  }

      case 100: {_s = "Doppelte Definition von BVar"; break; }
      case 101: {_s = "Keine Definition von BVar"; break; }
      case 102: {_s = "Definierte BVar wurde nicht verwendet"; break; }
      case 103: {_s = "Der Boolsche Ausdruck im Guard der Transition laesst sich nicht spezifizieren."; break;}
      case 104: {_s = "Die Boolsche Variable wird in keinem Action veraendert."; break; }
      case 105: {_s = "Kein Guard fragt den Wert der boolschen Variable ab."; break; }
      case 106: {_s = "Der Name des BVars ist ein leerer String."; break; }
      case 110: {_s = "Die Statechart enthaelt keine Liste der booleschen Variablen."; break; }
      
      case 200: {_s = "Name von Event nicht eindeutig"; break; }
      case 201: {_s = "Keine Deklaration von Event"; break; }
      case 202: {_s = "Deklarierter Event wurde nicht verwendet"; break; }
      case 203: {_s = "Der Pathname in einem GuardComppath ist nicht vorhanden."; break; }
      case 204: {_s = "Das Guardevent wird nie von einem Actionevent ausgeloest.  "; break; }
      case 205: {_s = "Das Actionevent loest kein  Guardevent aus."; break; }
      case 206: {_s = "Der Name des Events ist ein leerer String."; break; }
      case 210: {_s = "Die Statechart enthaelt keine Eventliste."; break; }

      case 300: {_s = "Mehrfache Definition von Statename"; break; }
      case 301: {_s = "Bezeichnung von Statename nicht eindeutig"; break;}
      case 302: {_s = "Deklarierter State wurde nicht verwendet"; break; }
      case 303: {_s = "Keine Deklaration von Statename"; break; }
      case 304: {_s = "Ein Or-State enthaelt nur einen inneren State"; break; }
      case 305: {_s = "Fataler Fehler: Ein Or-State enthaelt keinen inneren State."; break; }
      case 306: {_s = "Fataler Fehler: Ein And-State enthaelt keinen inneren State."; break; }
      case 307: {_s = "Ein And-State enthaelt nur einen inneren State."; break; }
      case 308: {_s = "Der Statename ist ein leerer String"; break; }
      case 310: {_s = "Die Statechart enthaelt keine Pfadliste."; break; }
      case 311: {_s = "Die Statechart enthaelt keine States."; break; }
      case 312: {_s = "Zu diesem Defaultconnector gibt es keinen State."; break; }
      case 313: {_s = "Der Defaultconnector ist mehrfach eingetragen."; break; }
      case 314: {_s = "In diesem State gibt es keinen Defaultconnector."; break; }
      case 315: {_s = "IN diesem State gibt es mehr als einen Defaultconnector."; break; }
      case 316: {_s = "Der Statename ist ein leerer String."; break; }
      case 317: {_s = "Fataler Fehler: Unbekannter Statetyp."; break; }

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
      case 415: {_s = "Die Transition hat den gleichen Start- und Ziel-Connector."; break; }
      case 416: {_s = "Der Guard der Transitionen ist syntaktisch gleich."; break;}
      case 417: {_s = "Der Guard der Transition laesst sich nicht spezifizieren."; break;}
      case 418: {_s = "Der Action der Transition laesst sich nicht spezifizieren."; break;}
      case 419: {_s = "Die Transition ist auf keinem Weg mit einem State verbunden."; break;}
      case 420: {_s = "Die Statechart enthaelt keine Transitionen."; break; }
      case 421: {_s = "Dieser Connector ist Teil eines Zyklus."; break;}
      case 422: {_s = "Die Transition ist an ihrem Startpunkt nicht durch andere Transitionen mit einem Startstate verbunden."; break;}
      case 423: {_s = "Die Transition ist an ihrem Zielpunkt nicht durch andere Transitionen mit einem Zielstate verbunden."; break;}
      case 424: {_s = "Der Guard hat den Typ GuardUndet."; break;}
      case 425: {_s = "Die Transition kann nicht gecheckt werden, da 2 oder mehr States auf einem Level nicht eindeutig sind."; break;}
      case 426: {_s = "Die Connectoren haben den gleichen Namen"; break; }
      default:   _s = "Dieser Code wurde noch nicht erfasst.";
    }
    return _s;
  }

}

