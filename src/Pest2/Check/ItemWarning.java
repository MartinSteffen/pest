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

package check;


/**
* Diese Klasse repraesentiert eine gefundene Warnung
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
public class ItemWarning {

  // private Eigenschaften der Klasse
  private int     code        = 0;
  private String  description = "";
  private String  place       = "";

// ****************************************************************************

  // Konstruktor
  public ItemWarning() {
  }

  // Konstruktor
  public ItemWarning(int code, String description, String place) {
    this.code        = code;
    this.description = description;
    this.place       = place;
  }

// ****************************************************************************

	/** liefert einen eindeutigen Warnungscode */
	public int getCode() {
           return 0;
	}

	/** liefert eine Fehlerbeschreibung */
	public String getDescription() {
	   return "";
        }

	/** liefert erweiterte Informationen -
      etwa den Zustand, in dem die Warnung
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