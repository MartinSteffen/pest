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

import java.util.*;

/** das "Erweitern" der JAVA Vector Klasse wird es moeglich machen ohne
* Typecasting auf die in der Vector Klasse gespeicherten Objekte vom
* Typ "itemSyntaxError" bzw. "itemSyntaxWarning" zuzugreifen
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
public class SyntaxWarning extends Vector {

// ****************************************************************************

  // fuegt eine Warnung der Warnungs-Liste hinzu
  protected void addWarning(ItemWarning warning) {
    addElement((Object)warning);
  }

// ****************************************************************************

  // Methode ohne ueber typecasting auf ein Warnungs-Element zugreifen zu
  // koennen
  public ItemWarning warningAt(int index) {
    return (ItemWarning)elementAt(index);
  }

  // Liefert die Anzahl der gefundenen Warnungen zurueck.
  public int count() {
    return elementCount;
  }

}
