// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                itemError
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          09.01.1998
//
// ****************************************************************************

package check;

/**
* Diese Klasse repaesentiert einen gefundenen Fehler
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
class ItemError {
  // private Eigenschaften der Klasse
  private int     code        = 0;
  private String  description = "";
  private String  place       = "";

// ****************************************************************************

  // Konstruktor
  ItemError() {
  }

  // Konstruktor
  ItemError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

// ****************************************************************************

	/** liefert einen eindeutigen Fehlercode */
	int getCode() {
    return code;
	}

	/** liefert eine Fehlerbeschreibung */
	String getDescription() {
    return description;
	}

	/** liefert erweiterte Informationen -
      etwa den Zustand, in dem der Fehler
	    aufgetreten ist.
  */
	String getPlace() {
    return place;
	}

  /** liefert komplette Fehlermeldung als String zurueck */
  public String toString() {
    return "Fehler: "+Integer.toString(code)+" "+getDescription()+", "+getPlace();
  }

// ****************************************************************************

  // zum Setzen der Fehlereigenschaften
  void setError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

}