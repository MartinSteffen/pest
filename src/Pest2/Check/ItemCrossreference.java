// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                ItemCrossreference
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          30.01.1999
//
// ****************************************************************************

package check;

/*
* Objekt, welches fuer die gefundenen Objekte innerhalb der "CrossReference"
* benoetigt wird.
*
*/
class ItemCrossreference {

  Object object             = null;
  Object highlighted_object = null;
  String path               = null;

  ItemCrossreference() {
  }

  ItemCrossreference(Object object,
                            Object highlighted_object,
                            String path) {
    this.path = path;
    this.highlighted_object = highlighted_object;
    this.object = object;
  }


}