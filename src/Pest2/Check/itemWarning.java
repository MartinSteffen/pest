// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                itemWarning
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          07.12.1998
//
// ****************************************************************************

package Check;

public class itemWarning {

  // private Eigenschaften der Klasse
  private int     code        = 0;
  private String  description = "";
  private String  place       = "";

// ****************************************************************************

  // Konstruktor
  public itemWarning() {
  }

  // Konstruktor
  public itemWarning(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

// ****************************************************************************

	// liefert einen eindeutigen Warnungscode
	public int getCode() {
           return 0;
	}

	// liefert eine Fehlerbeschreibung
	public String getDescription() {
	   return "";
        }

	// liefert erweiterte Informationen - etwa den Zustand, in dem die Warnung
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