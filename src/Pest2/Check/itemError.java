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

package Check;

// Wrapper Klasse fuer einen gefundenen Fehler
public class itemError {
  // private Eigenschaften der Klasse
  private int     code        = 0;
  private String  description = "";
  private String  place       = "";

// ****************************************************************************

  // Konstruktor
  public itemError() {
  }

  // Konstruktor
  public itemError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

// ****************************************************************************

	// liefert einen eindeutigen Fehlercode
	public int getCode() {
    return 0;
	}

	// liefert eine Fehlerbeschreibung
	public String getDescription() {
    return "";
	}

	// liefert erweiterte Informationen - etwa den Zustand, in dem der Fehler
	// aufgetreten ist.
	public String getPlace() {
    return "";
	}

// ****************************************************************************

  // zum Setzen der Fehlereigenschaften
  protected void setError(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

}