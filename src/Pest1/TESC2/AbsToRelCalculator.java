/**
 * AbsToRelCalculator
 *
 * Created: Thu Dec 31 1998, 21:50:30
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: AbsToRelCalculator.java,v 1.2 1999-02-12 16:25:19 swtech14 Exp $
 *
 *
 * Diese Klasse rechnet die Absolutkoordinaten eines SYNTAKTISCH KORREKTEN,
 * VOLLSTAENDIG MIT KOORDINATEN AUSGESTATTETEN STATECHART-OBJEKTES rekursiv in
 * Relativ-Koordinaten um. Dabei sind anschliessend die Koordinaten aller Kom-
 * ponenten relativ zu ihrem jeweiligen umfassenden State angeordnet.
 */

package tesc2;


import absyn.*;

class AbsToRelCalculator {

  private Statechart sChart;


  /**
   * Konstruktor AbsToRelCalculator.
   *
   * Uebergabeobjekt:
   *   Referenz auf (vollstaendig mit Koordinaten ausgestattetes!) Statechart-
   *   Objekt.
   */

  AbsToRelCalculator (Statechart sc) {
    sChart = sc;
  } // constructor AbsToRelCalculator



  /**
   * Start-Methode.
   */

  void start () {
    if (sChart != null)
      calculateState (sChart.state, 0, 0);
  } // method start



  // Rechne State-Koordinaten um.
  //
  // Uebergabeobjekte/-werte:
  //   umzurechnender State ´s´,
  //   x-Koordinate des umfassenden States von ´s´,
  //   y-Koordinate des umfassenden States von ´s´.

  private void calculateState (State s, int sur_x, int sur_y) {

    StateList     sL = null;
    TrList        tL = null;
    ConnectorList cL = null;

    if ((s != null) && (s.rect != null)) {

      if (s instanceof Or_State) {
	sL = ((Or_State)s).substates;
	tL = ((Or_State)s).trs;
	cL = ((Or_State)s).connectors;
      }

      if (s instanceof And_State) {
	sL = ((And_State)s).substates;
      }

      while (sL != null) {
	calculateState (sL.head, s.rect.x, s.rect.y);
	sL = sL.tail;
      }
      while (tL != null) {
	calculateTr (tL.head, s.rect.x, s.rect.y);
	tL = tL.tail;
      }
      while (cL != null) {
	calculateConnector (cL.head, s.rect.x, s.rect.y);
	cL = cL.tail;
      }

      s.name.position.x -= s.rect.x;
      s.name.position.y -= s.rect.y;
      s.rect.x -= sur_x;
      s.rect.y -= sur_y;
    }
  } // method calculate_state



  // Rechne Tr-Koordinaten um.
  //
  // Uebergabeobjekte/-werte:
  //   umzurechnende Transition ´t´,
  //   x-Koordinate des umfassenden States von ´t´,
  //   y-Koordinate des umfassenden States von ´t´.

  private void calculateTr (Tr t, int sur_x, int sur_y) {

    if ((t != null) && (t.points != null) &&
	(t.label != null) && (t.label.position != null)) {

      for (int i=0; i < t.points.length; i++)
	if (t.points[i] != null) {
	  t.points[i].x -= sur_x;
	  t.points[i].y -= sur_y;
	}
      t.label.position.x -= sur_x;
      t.label.position.y -= sur_y;
    }
  } // method calculateTr



  // Rechne Connector-Koordinaten um.
  //
  // Uebergabeobjekte/-werte:
  //   umzurechnender Collector ´c´,
  //   x-Koordinate des umfassenden States von ´c´,
  //   y-Koordinate des umfassenden States von ´c´.

  private void calculateConnector (Connector c, int sur_x, int sur_y) {

    if ((c != null) && (c.position != null) &&
	(c.name != null) && (c.name.position != null)) {

      c.position.x -= sur_x;
      c.position.y -= sur_y;
      c.name.position.x -= sur_x;
      c.name.position.y -= sur_y;
    }
  } // method calculateConnector

} // class AbsToRelCalculator
