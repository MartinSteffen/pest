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

import java.util.*;

/** das "Erweitern" der JAVA Vector Klasse wird es moeglich machen ohne
* Typecasting auf die in der Vector Klasse gespeicherten Objekte vom
* Typ "itemSyntaxError" bzw. "itemSyntaxWarning" zuzugreifen
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
public class SyntaxError extends Vector {

// ****************************************************************************

  // fuegt einen Fehler der Fehler-Liste hinzu
  protected void addError(ItemError error) {
    addElement((Object)error);
  }

// ****************************************************************************

  // Methode ohne ueber typecasting auf ein Fehler-Element zugreifen zu koennen
  public ItemError errorAt(int index) {
    return (ItemError)elementAt(index);
  }

  // Liefert die Anzahl der gefundenen Fehler zurueck.
  public int count() {
    return elementCount;
  }

}
