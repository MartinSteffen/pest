// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                itemError
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          07.12.1998
//
// ****************************************************************************

package check;

/**
* Diese Klasse repaesentiert einen gefundenen Fehler
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
public class ItemError {
  // private Eigenschaften der Klasse
  private int     code        = 0;
  private String  description = "";
  private String  place       = "";

// ****************************************************************************

  // Konstruktor
  public ItemError() {
  }

  // Konstruktor
  public ItemError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

// ****************************************************************************

	/** liefert einen eindeutigen Fehlercode */
	public int getCode() {
    return 0;
	}

	/** liefert eine Fehlerbeschreibung */
	public String getDescription() {
    return "";
	}

	/** liefert erweiterte Informationen -
      etwa den Zustand, in dem der Fehler
	    aufgetreten ist.
  */
	public String getPlace() {
    return "";
	}

  /** liefert komplette Fehlermeldung als String zurueck */
  public String toString() {
    return Integer.toString(code)+" "+getDescription()+", "+getPlace();
  }

// ****************************************************************************

  // zum Setzen der Fehlereigenschaften
  protected void setError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

}