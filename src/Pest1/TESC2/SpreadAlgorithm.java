/**
 * SpreadAlgorithm.
 *
 * Created: Thu Dec 31 1998, 18:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: SpreadAlgorithm.java,v 1.2 1999-01-22 21:58:47 swtech14 Exp $
 *
 *
 * Durch diese Klasse wird ein (syntaktisch korrektes!) Statechart-Objekt mit
 * Koordinaten ausgestattet. Folgende Absyn-Klassen sind fuer die Koordinaten-
 * berechnung relevant:
 *
 * State.java          : Statename name, CRectangle rect,
 * |                     Object clone()
 * |_ Basic_State.java
 * |_ And_State.java   : StateList substates
 * |_ Or_State.java    : StateList substates, TrList trs,
 *                       StatenameList defaults, ConnectorList connectors
 *
 * Tr.java             : TrAnchor source, TrAnchor target,
 *                       TLabel label, CPoint[] points
 *                       Object clone()
 *
 * TLabel.java         : CPoint Position, Guard guard, Action action
 *                       Object clone()
 *
 * Connector.java      : Conname name, CPoint position
 *                       Object clone()
 */

package tesc2;


import absyn.*;
import java.awt.FontMetrics;
import java.util.Vector;

class SpreadAlgorithm extends GraphOptimizer {

  // Speichervariablen fuer uebergebene Objekte/Werte.
  private Statechart sChart;
  private FontMetrics fMetrics;
  private boolean relCoord;

  // Fehlerwert.
  private int errorcode;

  // Referenzvariable fuer ListenElemente aus StateInfo-Liste.
  private StateInfo sInfo;

  // Referenzvariable f. Informationen ueber zuletzt berechneten And-/Or-State
  // (benoetigt zur Berechnung der Basicstate-x-Positionen).
  private StateInfo last_calculated_andOrState;

  // Speichervariablen fuer maximale Spalten-/Zeilenkoordinaten.
  private int maxCoord_x;
  private int maxCoord_y;

  // Speichervariablen fuer die Font-Hoehe und fuer den Abstand der Schrift von
  // den einzelnen Komponenten.
  private int fontHeight;
  private int stepsize_tiny;

  // Referenzvariable auf Listen-Handler (verwaltet StateInfo-Liste, Statepos-
  // Info-Liste, ConnectorposInfo-Liste, und bietet einige andere wichtige
  // Listenmethoden zum Suchen, etc.).
  private Listhandler lh;

  // Referenzvariable auf eine Liste der And-/Or-States, dessen Substates noch
  // nicht alle berechnet wurden.
  private StateInfoList unmarked_sInfos;


  /**
   * Konstruktor fuer Spread-Algorithmus.
   *
   * Uebergabeobjekte/-wert:
   *   Statechart, der mit Koordinaten ausgestattet werden soll;
   *   Fontmetrics (vom Editor uebergeben);
   *   Boolescher Wert: Absolutkoordinaten ja/nein (von ´GraphOptimizer´).
   */

  SpreadAlgorithm (Statechart sc, FontMetrics fm, boolean r) {
    sChart = sc;
    fMetrics = fm;
    relCoord = r;
  } // construktor SpreadAlgorithm


  /**
   * Start-Methode.
   *
   * Rueckgabewert:
   *   Fehlercode (0, falls Algorithmus erfolgreich durchlaufen wurde).
   */

  int calculate_coordinates () {

    // Setze zunaechst alle "dynamischen" Werte zurueck.

    errorcode = 0;
    last_calculated_andOrState = null;
    maxCoord_x = 0;
    maxCoord_y = 0;
    unmarked_sInfos = null;

    // Lies zunaechst die Fonthoehe aus der FontMetrics.
    // Addiere einen gewissen Abstand hinzu, um die Schrift nicht zwischen die
    // einzelnen Komponenten zu "quetschen".

    stepsize_tiny = (int)((double)(fMetrics.getHeight())/4 + 0.5);
    fontHeight = fMetrics.getHeight() + stepsize_tiny * 2;

    // Initialisiere Listen-Handler, baue die State(pos)Info-Listen auf.

    lh = new Listhandler (sChart);
    lh.buildLists();

    // Berechne mit Hilfe der State(pos)Info-Listen die Koordinaten der State-
    // chart-Komponenten.
    // Dabei werden die Koordinaten zunaechst ABSOLUT bestimmt.

    setCoordinates();

    // Falls Relativkoordinaten gewuenscht sind, berechne diese mittels dem
    // ´AbsToRelCalculator´.

    if (relCoord == true) {
      AbsToRelCalculator atrCalc = new AbsToRelCalculator (sChart);
      atrCalc.start();
    }

    // Gib evtl. Fehlerwert an ´GraphOptimizer´ zurueck.

    return errorcode;
  } // method calculate_coordinates



  // ------------- Methoden, die den Algorithmusablauf beschreiben ------------



  // Berechne Koordinaten mit Hilfe der State(pos)Info-Listen.
  // Anschliessend werden die Substates der And-States "aufgeblaeht".

  private void setCoordinates() {
    step_1a();
    resize_andSubstates(sChart.state);
  } // method setCoordinates



  // Schritt 1a :
  //   Waehle erstes (unmarkiertes) Listenelement in der StateInfo-Liste
  //   (diese enthaelt mindestens den Rootstate!).
  // -->> Uebergang zu Schritt 1b.

  private void step_1a () {
    sInfo = lh.siList.head;
    step_1b();
  } // method step_1a



  // Schritt 1b :
  //   Markiere sInfo-Objekt;
  //   Berechne Sub-Basicstates und Connectoren (inkl. State-/Connames);
  //   Berechne Transitionen (inkl. TLabels)
  //   Berechne umfassenden State.
  // -->> Uebergang zu Schritt 2.

  private void step_1b () {
    sInfo.marked = true;
    set_basicCoord_and_conCoord();
    set_trsCoord();
    set_surStateCoord();
    step_2();
  } // method step_1b



  // Schritt 2 :
  // - Betrachte Schachtelungstiefe t von ´sInfo´:
  //      Falls t == 0,
  // -->> fertig.
  //      Falls t > 0 , Betrachte umfassenden State u des ´surrounding_state´:
  //         Falls noch nicht alle And-/Or-Substates von u markiert sind,
  //    -->> Uebergang zu Schritt 3.
  //         Falls alle And-/Or-Substates von u markiert sind,
  //    -->> Uebergang zu Schritt 4.

  private void step_2 () {
    if (sInfo.depth > 0) {
      sInfo =
	lh.getStateInfo (lh.getSurroundingState (sInfo.surrounding_state));

      // Pruefe, ob alle Substates des neuen aktuellen And-/Or-States schon
      // alle berechnet wurden. Falls nicht, fuege ´sInfo´ an die Liste mit
      // "noch nicht fertigen" States hinzu.

      if (!lh.all_andOrStates_marked (sInfo.substates)) {

	// Registriere aktuelle State-Info als "noch nicht alle Substates be-
	// rechnet", falls ´sInfo´ noch nicht am Anfang der Liste steht.

	if (unmarked_sInfos == null || unmarked_sInfos.head != sInfo)
	  unmarked_sInfos = new StateInfoList (sInfo, unmarked_sInfos);
	step_3();

      } else
	step_1b();
    }
  } // method step_2



  // Schritt 3 :
  // - Waehle ersten And-/Or-State u aus der Liste der Substates des ersten
  //   unmarkierten States, der ebenfalls noch nicht markiert ist.
  // - Suche erstes unmarkiertes StateInfo-Element, dessen ´surrounding_state´
  //   ein (evtl. "tieferer") Substate von u ist und dessen Substates nur
  //   Basic-States sind.
  // -->> Uebergang zu Schritt 1b.

  private void step_3 () {
    State s = lh.getFirstUnmarked_andOrState (sInfo.substates);
    sInfo = lh.get_stateWithSubstatesOnlyBasic_info (s);
    step_1b();
  } // method step_3



  // ---------- (private) Methoden, die dem Algorithmusablauf dienen ----------



  //           ------- (private) Methoden fuer And-/Or-States -------



  // Setze die Koordinaten des umfassenden And-/Or-States (´surrounding_state´
  // aus ´sInfo´).

  private void set_surStateCoord () {

    // Tiefe des Surrounding-States -> d.
    int d = sInfo.depth;

    // Speichervariable fuer Statename-Laenge.
    int snL;

    // Referenzvariable fuer Statepos-Informationen.
    StateposInfo spi;

    // Speichervariablen fuer zu bestimmende Koordinaten.
    int x, y, height, width;


    // Bestimme die x-Koordinate fuer den zu berechnenden Surrounding-State.

    // Falls keine State-Infos ueber States mit unmarkierten Substates in ´un-
    // marked_sInfos´ existieren, bestimme ´x´ ausschliesslich abhaengig von
    // der Tiefe des States.

    if (unmarked_sInfos == null) {
      x = (STEPSIZE_LARGE * d);
    }

    // Falls State-Infos ueber States mit unmarkierten Substates in ´unmarked_
    // sInfos´ existieren, pruefe ob ´sInfo´ an erster Stelle der Liste steht.
    // Falls ja, bestimme ´x´ fuer den ´surrounding_state´ abhaengig von dem
    // Minimum der x-Koordinaten aller Substates des ´surrounding_state´. Ent-
    // ferne zudem ´sInfo´ aus ´unmarked_sInfos´.

    else
      if (unmarked_sInfos.head == sInfo) {
	x = getMinAndOr_x (sInfo) - STEPSIZE_LARGE;

	unmarked_sInfos = unmarked_sInfos.tail;

      } else {

	// Falls ´sInfo´ nicht an erster Stelle von ´unmarked_sInfos´ steht,
	// handelt es sich um eine State-Info ueber einen Sub-And-/Or-State des
	// ´surrounding_state´ aus der State-Info, die an erster Stelle der
	// ´unmarked_sInfos´-Liste steht.

	x = getMaxAndOr_x
	  (lh.getStateInfo (lh.getSurroundingState (sInfo.surrounding_state)));
	x = (x != -1) ?
	  (x + STEPSIZE_LARGE) :
	  (getMinAndOr_x (sInfo) - STEPSIZE_LARGE);
      }


    // Die y-Koordinate ergibt sich aus dem And-/Or-Offset des ´surrounding-
    // state´.

    y = get_andOrOffset_y (sInfo.surrounding_state);


    // Die Breite ergibt sich aus dem Maximum der folgenden Groessen:
    //   - aktuelles x-Maximum minus x-Koordinate;
    //   - Laenge des Statenames plus (Anzahl der direkten Transitionen + 1),
    //     multipliziert mit dem Standardabstand zwischen Transitionen (´STEP-
    //     SIZE_MEDIUM´));
    //   - Laenge des Statenames plus (Anzahl der INdirekten Transitionen + 1),
    //     multipliziert mit dem Standardabstand zwischen Transitionen (´STEP-
    //     SIZE_MEDIUM´)) plus "Bonusabstand" (´STEPSIZE_SMALL´);
    // (Bei Or-States wird die Statenamelaenge NICHT mit der Anzahl der Tran-
    //  sitionen addiert, da der Statename innerhalb des States steht!)

    snL = getStatenameLength (sInfo.surrounding_state.name);
    spi = lh.getStateposInfo (sInfo.surrounding_state);

    width = (sInfo.surrounding_state instanceof And_State) ?
      Math.max
      ((maxCoord_x + STEPSIZE_LARGE) - x, Math.max
       (snL + (spi.directTrs + 1) * STEPSIZE_MEDIUM + STEPSIZE_SMALL,
	snL + (spi.indirectTrs + 1) * STEPSIZE_MEDIUM * 2 + STEPSIZE_SMALL)) :
      Math.max
      ((maxCoord_x + STEPSIZE_LARGE) - x, Math.max
       (snL, Math.max
	((spi.directTrs + 1) * STEPSIZE_MEDIUM + STEPSIZE_SMALL,
	 ((spi.indirectTrs + 1) * STEPSIZE_MEDIUM * 2 + STEPSIZE_SMALL))));


    // Die Hoehe ergibt sich aus dem Maximum der Maximalzeilen der im State
    // enthaltenen And-/Or-States und der Maximalzeile der Basic-States und
    // Connectoren inkl. deren Transitionen (´max_y´), plus dem Maximum aus dem
    // Standardabstand zwischen State-Linien und Font-Hoehe, minus der y-Koor-
    // dinate dieses States.

    maxCoord_y = lh.getStateposInfo (sInfo.surrounding_state).max_y;
    maxCoord_y = Math.max (maxCoord_y,
			   lh.maxline_of_andOrStates (sInfo.substates));

    height = (maxCoord_y + Math.max (STEPSIZE_LARGE, fontHeight)) - y;


    // Speichere Koordinaten, setze Koordinaten der Statenames.

    sInfo.surrounding_state.rect = new CRectangle (x, y, width, height);

    if (sInfo.surrounding_state.name != null)
      sInfo.surrounding_state.name.position =
	(sInfo.surrounding_state instanceof And_State) ?
	(new CPoint (x + stepsize_tiny, y - stepsize_tiny))
	: (new CPoint (x + stepsize_tiny, y + fontHeight - stepsize_tiny));


    // Pruefe und speichere evtl. neue Maximalwerte.

    maxCoord_x = Math.max (maxCoord_x, (x + width));
    maxCoord_y = Math.max (maxCoord_y, (y + height));
    lh.getStateposInfo (sInfo.surrounding_state).max_y = maxCoord_y;

    // Die Informationen ueber den berechneten And-/Or-State werden als
    // Informationen ueber den zuletzt berechneten State gespeichert.

    last_calculated_andOrState = sInfo;

    // Setze Zeilenmaximum wieder auf 0, um ein neues y-Maximum fuer naechsten
    // And-/Or-State zu berechnen.

    maxCoord_y = 0;

  } // method set_surStateCoord



  // Berechne Maximal-x-Koordinate der ´substates´ aus einer State-Info.
  //
  // Uebergabeobjekt:
  //   StateInfo, aus deren ´substates´ das x-Maximum bestimmt werden soll.
  //
  // Rueckgabewert:
  //   int-Wert mit x-Maximal-Koordinate; -1, falls ´substates´ von ´si´ null.

  private int getMaxAndOr_x (StateInfo si) {
    int n = -1;
    StateList sL = si.substates;

    while (sL != null) {
      if ((sL.head != null) && (sL.head.rect != null))
	n = Math.max (n, sL.head.rect.x + sL.head.rect.width);
      sL = sL.tail;
    }
    return n;
  } // method getMaxAndOr_x



  // Berechne Minimal-x-Koordinate der ´substates´ aus einer State-Info.
  //
  // Uebergabeobjekt:
  //   StateInfo, aus deren ´substates´ das x-Minimum bestimmt werden soll.
  //
  // Rueckgabewert:
  //   int-Wert mit x-Minimal-Koordinate; ´Integer.MAX_VALUE´,falls ´substates´
  //                                      von ´si´ null ist.

  private int getMinAndOr_x (StateInfo si) {
    int n = Integer.MAX_VALUE;
    StateList sL = si.substates;

    while (sL != null) {
      if ((sL.head != null) && (sL.head.rect != null))
	n = Math.min (n, sL.head.rect.x);
      sL = sL.tail;
    }
    return n;
  } // method getMinAndOr_x



  //           -------- (private) Methoden fuer Transitionen --------



  // Setze die Koordinaten der Transitionen des ´surrounding_state´ aus
  // ´sInfo´ (d.h. die Transitionen zwischen den Substates des ´surrounding-
  // state´).

  private void set_trsCoord () {

    // Speichervariablen fuer jeweils aktuelle Startspalte/-zeile.
    int distance_x = 0, distance_y = 0;

    // Distanz zwischen Transitionen.
    int d_y = Math.max (fontHeight, STEPSIZE_MEDIUM);

    // Referenzvariablen fuer Quell-/Ziel-States einer Transition.
    State srcState, trgtState;

    // Referenzvariablen fuer Quell-/Ziel-Connectoren einer Transition.
    Connector srcCon, trgtCon;

    // CPoint-Array zum Zwischenspeichern der Transitionspositionen.
    CPoint[] pos;


    // Pruefe, ob Transitionen-Liste zu bekommen ist und ob Substates vorhanden
    // sind.

    if (sInfo.surrounding_state instanceof Or_State &&
	sInfo.substates != null) {

      TrList trL1 = ((Or_State)(sInfo.surrounding_state)).trs;
      TrList trL2 = trL1;
      TrList trL3 = trL1;
      TrList trL4 = trL1;
      TrList trL5 = trL1;

      // Berechne y-Offset fuer Transitionen
      // (d.h. ermittle And-/Or-Offset fuer umfassenden State und addiere 2 *
      //  Maximum aus dem Standardabstand zu State-Linien (´STEPSIZE_LARGE´)
      //  und Font-Hoehe hinzu).

      distance_y = get_andOrOffset_y (sInfo.surrounding_state) +
	2 * Math.max (STEPSIZE_LARGE, fontHeight);


      // Statte zunaechst die Transitionen, die mit And- oder Or-States ver-
      // bunden sind, mit Koordinaten aus.

      while (trL1 != null) {
	if (trL1.head != null) {

	  // Teste, ob es sich um eine direkte Transition handelt.

	  if (trL1.head.source instanceof Statename &&
	      trL1.head.target instanceof Statename ) {

	    // Ermittle die zu den Statenames gehoerigen States.

	    srcState  = lh.findStateWithName ((Statename)(trL1.head.source),
					      sInfo.substates);
	    trgtState = lh.findStateWithName ((Statename)(trL1.head.target),
					      sInfo.substates);

	    // Pruefe, ob es sich um eine Transition handelt, die mit And- oder
	    // Or-States verbunden ist.
	    // Falls ja, erstelle CPoint-Array, und speichere dieses in der
	    // Transition ab. Erhoehe anschliessend y-Offset.

	    if (srcState instanceof And_State  ||
		srcState instanceof Or_State   ||
		trgtState instanceof And_State ||
		trgtState instanceof Or_State) {

	      pos = calculate_trPos (srcState, trgtState, distance_y);
	      trL1.head.points = pos;
	      trL1.head.label.position =
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[1].y - stepsize_tiny);

	      // Berechne nun die neue Zeile.

	      distance_y += d_y;
	    }
	  } // if
	}
	trL1 = trL1.tail;

      } // while TrL1 != null


      // Statte nun die Transitionen, die von Basic-State zu Basic-State ver-
      // laufen, mit Koordinaten aus.

      while (trL2 != null) {
	if (trL2.head != null) {

	  // Teste, ob es sich um eine direkte Transition handelt.

	  if (trL2.head.source instanceof Statename &&
	      trL2.head.target instanceof Statename ) {

	    // Ermittle die zu den Statenames gehoerigen States.

	    srcState  = lh.findStateWithName ((Statename)(trL2.head.source),
					      sInfo.substates);
	    trgtState = lh.findStateWithName ((Statename)(trL2.head.target),
					      sInfo.substates);

	    // Pruefe, ob es sich um eine Transition handelt, von Basic-State
	    // zu Basic-State verlaeuft.
	    // Falls ja, erstelle CPoint-Array, und speichere dieses in der
	    // Transition ab. Erhoehe anschliessend y-Offset.

	    if ((srcState instanceof Basic_State) &&
		(trgtState instanceof Basic_State)) {

	      pos = calculate_trPos (srcState, trgtState, distance_y);
	      trL2.head.points = pos;
	      trL2.head.label.position =
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[1].y - stepsize_tiny);

	      // Berechne nun die neue Zeile.

	      distance_y += d_y;
	    }
	  } // if
	}
	trL2 = trL2.tail;

      } // while TrL2 != null


      // Statte nun die Transitionen, die mit State und Connector verbunden
      // sind, mit Koordinaten aus. Fuehre zunaechst einen Reset der Counter-
      // werte in der ´StateposList´ durch.

      lh.resetCounters();

      // Berechne y-Offset fuer Transitionen unterhalb der And-/Or-States.
      // (d.h. ermittle Maximum aller Zeilenmaxima der And-/Or-(Sub)states des
      //  ´surrounding_state´, und addiere den Standardabstand zwischen State-
      //  Linien (´STEPSIZE_LARGE´) hinzu).

      distance_x = STEPSIZE_LARGE;
      distance_y = lh.maxline_of_andOrStates (sInfo.substates)+ STEPSIZE_LARGE;

      while (trL3 != null) {
	if (trL3.head != null) {

	  // Teste, ob es sich um eine indirekte Transition handelt
	  // (State -> Connector).

	  if (trL3.head.source instanceof Statename &&
	      trL3.head.target instanceof Conname ) {

	    // Ermittle den zum State-/Conname gehoerigen State/Connector.
	    // Es handelt sich beim ´surrounding_state´ von ´sInfo´ um einen
	    // Or-State, daher ist eine ´ConnectorList´ auf jeden Fall vorhan-
	    // den (evtl. "null").

	    srcState = lh.findStateWithName ((Statename)(trL3.head.source),
					      sInfo.substates);
	    trgtCon = lh.findConnectorWithName
	      ((Conname)(trL3.head.target),
	       ((Or_State)(sInfo.surrounding_state)).connectors);

	    // Erstelle CPoint-Array, und speichere dieses in der Transition ab.
	    // Berechne zudem die zugehoerige Label-Position.

	    pos = calculate_trPos (srcState, trgtCon, distance_x, distance_y);
	    trL3.head.points = pos;
	    if (pos.length == 4)
	      trL3.head.label.position =
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[1].y + fontHeight - stepsize_tiny * 2);
	    else
	      // Betrachte den Sonderfall der Transition mit Laenge 5. Dann
	      // handelt es sich um eine Transition, die mit einem And-Or-State
	      // verbunden ist. Um festzustellen, in welche Richtung diese
	      // Transition laeuft, werden der zweite und der vierte Punkt der
	      // Transition betrachtet.

	      trL3.head.label.position = (pos[1].y > pos[3].y) ?
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[2].y + fontHeight - stepsize_tiny * 2)
		: new CPoint (Math.min (pos[2].x, pos[3].x) + stepsize_tiny,
			     pos[2].y + fontHeight - stepsize_tiny * 2);

	    // Erhoehe im Falle eines And-/Or-States ´distance_y´, speichere
	    // neue Maximal-y-Koordinate.

	    if (srcState instanceof And_State ||
		srcState instanceof Or_State) {
	      distance_y += d_y;
	      lh.getStateposInfo (sInfo.surrounding_state).max_y =
		distance_y - d_y;
	    }
	  } // if State -> Connector

	  // Teste, ob es sich um eine indirekte Transition handelt
	  // (Connector -> State).

	  if (trL3.head.source instanceof Conname &&
	      trL3.head.target instanceof Statename ) {

	    // Ermittle den zum Con-/Statename gehoerigen Connector/State.

	    srcCon = lh.findConnectorWithName
	      ((Conname)(trL3.head.source),
	       ((Or_State)(sInfo.surrounding_state)).connectors);

	    trgtState = lh.findStateWithName ((Statename)(trL3.head.target),
					      sInfo.substates);

	    // Erstelle CPoint-Array, und speichere dieses in der Transition ab.
	    // Berechne zudem die zugehoerige Label-Position.

	    pos = calculate_trPos (srcCon, trgtState, distance_x, distance_y);
	    trL3.head.points = pos;
	    if (pos.length == 4)
	      trL3.head.label.position =
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[1].y + fontHeight - stepsize_tiny * 2);
	    else
	      // Betrachte den Sonderfall der Transition mit Laenge 5. Dann
	      // handelt es sich um eine Transition, die mit einem And-Or-State
	      // verbunden ist. Um festzustellen, in welche Richtung diese
	      // Transition laeuft, werden der zweite und der vierte Punkt der
	      // Transition betrachtet.

	      trL3.head.label.position = (pos[1].y > pos[3].y) ?
		new CPoint (Math.min (pos[1].x, pos[2].x) + stepsize_tiny,
			   pos[2].y + fontHeight - stepsize_tiny * 2)
		: new CPoint (Math.min (pos[2].x, pos[3].x) + stepsize_tiny,
			     pos[2].y + fontHeight - stepsize_tiny * 2);

	    // Erhoehe im Falle eines And-/Or-States ´distance_x´ und ´dis-
	    // tance_y´, speichere evtl. neue Maximal-Koordinaten.

	    if (trgtState instanceof And_State ||
		trgtState instanceof Or_State) {
	      distance_x += STEPSIZE_MEDIUM;
	      distance_y += d_y;
	      lh.getStateposInfo (sInfo.surrounding_state).max_y =
		distance_y - d_y;
	    }

	  } // if Connector -> State
	}
	trL3 = trL3.tail;

      } // while TrL3 != null


      // Statte nun die Transitionen, die Connectoren verbinden , mit Koordina-
      // ten aus.

      while (trL4 != null) {
	if (trL4.head != null) {

	  // Teste, ob es sich um eine Transition von Connector zu Con. handelt
	  // (Connector -> Connector).

	  if (trL4.head.source instanceof Conname &&
	      trL4.head.target instanceof Conname ) {

	    // Ermittle die zu den Connames gehoerigen Connectoren.

	    srcCon  = lh.findConnectorWithName
	      ((Conname)(trL4.head.source),
	       ((Or_State)(sInfo.surrounding_state)).connectors);
	    trgtCon = lh.findConnectorWithName
	      ((Conname)(trL4.head.target),
	       ((Or_State)(sInfo.surrounding_state)).connectors);

	    // Erstelle CPoint-Array, und speichere dieses in der Transition ab.
	    // Berechne zudem die zugehoerige Label-Position.

	    pos = calculate_trPos (srcCon, trgtCon, distance_x);
	    trL4.head.points = pos;

	    trL4.head.label.position =
	      new CPoint (pos[1].x + stepsize_tiny,
			 Math.min (pos[1].y, pos[2].y) +
			 fontHeight - stepsize_tiny * 2);

	  } // if Connector -> Connector
	}
	trL4 = trL4.tail;

      } // while TrL4 != null


      // Berechne neuen Maximalwert unter Beachtung der Transitionenlabel:

      while (trL5 != null) {
	if (trL5.head != null && trL5.head.label != null)
	  maxCoord_x = Math.max (maxCoord_x,
				 trL5.head.label.position.x +
				 getTLabelLength (trL5.head.label));
	trL5 = trL5.tail;
      }

    } // if Or_State

  } // method set_trsCoord



  // Berechne CPoint-Array mit Transitions-Koordinaten fuer eine DIREKTE Transi-
  // tion, und erhoehe Counterwerte fuer "State-Transitionsanschluesse".
  //
  // Uebergabeobjekte/-wert:
  //   Quell-State,
  //   Ziel-State,
  //   Abstand zum oberen Rand der Zeichenflaeche.
  //
  // Rueckgabeobjekt:
  //   CPoint-Array mit Koordinaten.

  private CPoint[] calculate_trPos (State src, State trgt, int dist_y) {

    // Speichervariablen fuer Koordinaten-Komponenten.
    int x1, y1, x2, y2, x3, y3, x4, y4;


    // Berechne die Komponenten der vier Punkte einer direkten Transition.
    // Beachte dabei die in der StateposInfo-Liste gespeicherten Counterwerte,
    // die die Verschiebung der Transitionen ermoeglichen.

    x1 = src.rect.x + (lh.getStateposInfo (src)).counter_x;
    y1 = src.rect.y - 1;

    x2 = x1;
    y2 = dist_y;

    // Erhoehe Counterwerte je nach Basic- oder And-/Or-State.

    if (src instanceof Basic_State)
      lh.getStateposInfo (src).counter_x += STEPSIZE_SMALL;
    else
      lh.getStateposInfo (src).counter_x += STEPSIZE_MEDIUM;

    x3 = trgt.rect.x + (lh.getStateposInfo (trgt)).counter_x;
    y3 = y2;

    x4 = x3;
    y4 = trgt.rect.y - 1;

    // Erhoehe Counterwerte je nach Basic- oder And-/Or-State.

    if (trgt instanceof Basic_State)
      lh.getStateposInfo (trgt).counter_x += STEPSIZE_SMALL;
    else
      lh.getStateposInfo (trgt).counter_x += STEPSIZE_MEDIUM;

    // Addiere nun noch bei den And-State-Transitionen die Statename-laengen
    // zu den x-Koordinaten hinzu, um Ueberschneidungen zwischen Transitionen
    // und State-Namen zu vermeiden.

    if (src instanceof And_State) {
      x1 += getStatenameLength (src.name);
      x2 += getStatenameLength (src.name);
    }
    if (trgt instanceof And_State) {
      x3 += getStatenameLength (trgt.name);
      x4 += getStatenameLength (trgt.name);
    }

    // Konstruiere Rueckgabe-Array.

    CPoint[] p = { new CPoint (x1, y1), new CPoint (x2, y2),
		  new CPoint (x3, y3), new CPoint (x4, y4) };
    return p;
  } // method calculate_trPos (dir. Trs.)



  // Berechne CPoint-Array mit Transitions-Koordinaten fuer eine INDIREKTE Tran-
  // sition, und erhoehe Counterwerte fuer "Transitionsanschluesse".
  //
  // Uebergabeobjekte/-wert:
  //   Quell-State,
  //   Ziel-Connector,
  //   Verschiebungs-Spalte,
  //   Verschiebungs-Zeile.
  //
  // Rueckgabeobjekt:
  //   CPoint-Array mit Koordinaten.

  private CPoint[] calculate_trPos (State src, Connector trgt,
				   int dist_x, int dist_y) {

    // Speichervariablen fuer Koordinaten-Komponenten.
    int x1, y1, x2, y2, x3, y3, x4, y4, x5, y5;

    // Referenzvariable auf Rueckgabe-Array.
    CPoint[] p = null;


    // Berechne die Komponenten der vier/fuenf Punkte einer indirekten Transi-
    // tion. Beachte dabei die in der StateposInfo-Liste gespeicherten Counter-
    // werte, sowie die in der ConnectorposInfo-Liste gespeicherten Counterwer-
    // te, die die Verschiebung der Transitionen ermoeglichen.

    if (src instanceof Basic_State) {

      x1 = src.rect.x + lh.getStateposInfo (src).counter_x;
      y1 = src.rect.y + Math.max (BASICSTATE_MINHEIGHT, fontHeight) + 1;

      x2 = x1;
      y2 = trgt.position.y + lh.getConnectorposInfo (trgt).counter_ya +
	CONNECTOR_SIZE/2;

      x3 = lh.getConnectorposInfo (trgt).counter_xa;
      y3 = y2;

      x4 = trgt.position.x;
      y4 = trgt.position.y + CONNECTOR_SIZE/2;

      // Erhoehe ´counter_x´ fuer den Quell-State, bzw. ´counter_ya´ fuer den
      // Ziel-Connector, um eine horizentale, bzw. vertikale Transitionenver-
      // schiebung bzgl. And-/Or-States zu erzeugen.

      lh.getStateposInfo (src).counter_x += STEPSIZE_SMALL;
      lh.getConnectorposInfo (trgt).counter_ya += Math.max (STEPSIZE_MEDIUM,
							    fontHeight);
      // Konstruiere Rueckgabe-Array der Laenge 5.

      p = new CPoint[4];
      p[0] = new CPoint (x1, y1);
      p[1] = new CPoint (x2, y2);
      p[2] = new CPoint (x3, y3);
      p[3] = new CPoint (x4, y4);

    } else {

      x1 = src.rect.x + lh.getStateposInfo (src).counter_x;
      y1 = lh.getStateposInfo (src).max_y + 1;

      x2 = x1;
      y2 = dist_y;

      x3 = trgt.position.x + CONNECTOR_SIZE + dist_x;
      y3 = y2;

      x4 = x3;
      y4 = trgt.position.y + CONNECTOR_SIZE/2;

      x5 = trgt.position.x + CONNECTOR_SIZE;
      y5 = trgt.position.y + CONNECTOR_SIZE/2 +
	lh.getConnectorposInfo (trgt).counter_yb;

      // Erhoehe ´counter_yb´ fuer Connector, um eine vertikale Transitionen-
      // verschiebung bzgl. And-/Or-States zu erzeugen.

      // Erhoehe ´counter_x´ fuer den Quell-State, bzw. ´counter_ya´ fuer den
      // Ziel-Connector, um eine horizentale, bzw. vertikale Transitionenver-
      // schiebung bzgl. And-/Or-States zu erzeugen.

      lh.getStateposInfo (src).counter_x += STEPSIZE_MEDIUM;
      lh.getConnectorposInfo (trgt).counter_yb += Math.max (STEPSIZE_MEDIUM,
							    fontHeight);
      // Konstruiere Rueckgabe-Array der Laenge 5.

      p = new CPoint[5];
      p[0] = new CPoint (x1, y1);
      p[1] = new CPoint (x2, y2);
      p[2] = new CPoint (x3, y3);
      p[3] = new CPoint (x4, y4);
      p[4] = new CPoint (x5, y5);
    }
    return p;
  } // method calculate_trPos (ind. Trs.)



  // Berechne CPoint-Array mit Transitions-Koordinaten fuer eine INDIREKTE Tran-
  // sition, und erhoehe Counterwerte fuer "Transitionsanschluesse".
  //
  // Uebergabeobjekte/-wert:
  //   Quell-Connector,
  //   Ziel-State,
  //   Verschiebungs-Spalte,
  //   Verschiebungs-Zeile.
  //
  // Rueckgabeobjekt:
  //   CPoint-Array mit Koordinaten.

  private CPoint[] calculate_trPos (Connector src, State trgt,
				   int dist_x, int dist_y) {

    // CPoint-Array zum Speichern der Transitionskoordinaten.
    CPoint pos[] = calculate_trPos (trgt, src, dist_x, dist_y);

    // CPoint-Array zum Speichern der Umkehrung der Trskoordinaten aus ´pos´.
    CPoint pos_negation[];


    // "Negiere" CPoint-Array, um die Umkehr-Transition zu erhalten.

    pos_negation = new CPoint[pos.length];
    for (int i=0; i < pos.length; i++)
      pos_negation[i] = pos[pos.length-1-i];

    return pos_negation;
  } // method calculate_trPos (ind. Trs.)



  // Berechne CPoint-Array mit Transitions-Koordinaten fuer eine INDIREKTE Tran-
  // sition, und erhoehe Counterwerte fuer "Connector-Transitionsanschluesse".
  //
  // Uebergabeobjekte/-wert:
  //   Quell-Connector,
  //   Ziel-Connector,
  //   Verschiebungs-Wert.
  //
  // Rueckgabeobjekt:
  //   CPoint-Array mit Koordinaten.

  private CPoint[] calculate_trPos (Connector src, Connector trgt, int dist_x) {

    // Speichervariablen fuer Koordinaten-Komponenten.
    int x1, y1, x2, y2, x3, y3, x4, y4;


    // Berechne die Komponenten der vier Punkte einer indirekten Transition.
    // (Connector -> Connector).

    x1 = src.position.x + CONNECTOR_SIZE;
    y1 = src.position.y + CONNECTOR_SIZE/2;

    x2 = src.position.x + CONNECTOR_SIZE + dist_x;
    y2 = src.position.y + CONNECTOR_SIZE/2 +
      lh.getConnectorposInfo (src).counter_yb;

    x3 = x2;
    y3 = trgt.position.y + CONNECTOR_SIZE/2 +
      lh.getConnectorposInfo (trgt).counter_yb;

    x4 = x1;
    y4 = trgt.position.y + CONNECTOR_SIZE/2;

    /*
    // Erhoehe Counterwerte je nach Basic- oder And-/Or-State.

    if (src instanceof Basic_State)
      lh.getStateposInfo (src).counter_x += STEPSIZE_SMALL;
    else
      lh.getStateposInfo (src).counter_x += STEPSIZE_MEDIUM;

    if (trgt instanceof Basic_State)
      lh.getStateposInfo (trgt).counter_x += STEPSIZE_SMALL;
    else
      lh.getStateposInfo (trgt).counter_x += STEPSIZE_MEDIUM;
      */

    // Konstruiere Rueckgabe-Array.

    CPoint[] p = { new CPoint (x1, y1), new CPoint (x2, y2),
		  new CPoint (x3, y3), new CPoint (x4, y4) };
    return p;
  } // method calculate_trPos (dir. Trs.)



  // Setze Koordinaten der Basic-States des ´surrounding_state´ aus ´sInfo´.

  private void set_basicCoord_and_conCoord () {

    // Erstelle Vektor mit:
    //   0) Liste der in der ´substates´-Liste von ´sInfo´ vorhandenen Basic-
    //      States,
    //   1) Anzahl dieser Basic-States.
    Vector v = lh.getListAndNumberOf_Basicstates (sInfo.substates);

    // Liste der Basic-States in ´substates´ von ´sInfo´ -> bList.
    StateList bList = (StateList)(v.elementAt (0));

    // Anzahl der Basic-States -> bn.
    int bn = ((Integer)v.elementAt (1)).intValue();

    // Anzahl der Connectoren -> cn.
    int cn = (sInfo.surrounding_state instanceof Or_State) ?
      lh.getNumberOf_Connectors
      (((Or_State)(sInfo.surrounding_state)).connectors) : 0;

    // Tiefe des Surrounding-States -> d.
    int d = sInfo.depth;

    // Koordinaten-Variablen, Breitenvariablen fuer Basic-States.
    int x, y, width, height;

    // Referenzvariable fuer Stateposition-Informationen.
    StateposInfo spi;


    // Pruefe, ob Basic-States ueberhaupt vorhanden sind:

    if (bn > 0) {

      // Berechne x-Offset fuer States in ´bList´.

      StateInfo si = last_calculated_andOrState;

      x = (si == null) ?
	(STEPSIZE_LARGE * (d + 1)) :
	(STEPSIZE_LARGE * (d + 2 - si.depth) +
	 si.surrounding_state.rect.x + si.surrounding_state.rect.width);

      // Berechne y-Offset fuer States in ´bList´.

      y = get_basicOffset_y (bList);

      // Statte nun die Basic-States mit Koordinaten aus.
      // Dabei werden die States in einer Reihe auf gleicher Hoehe angeordnet.

      while (bList != null) {
	if (bList.head != null) {
	  spi = lh.getStateposInfo(bList.head);

	  // Die Breite eines Basic-States ist das Maximum aus:
	  //   Laenge des Labels (unter gegebenem Font),
	  //   (Anzahl der direkten Transitionen + 1) * STEPSIZE,
	  //   (Anzahl der indirekten Transitionen + 1) * STEPSIZE,
	  //   Mindestbreite fuer Basic-States.

	  width =
	    Math.max (getStatenameLength (bList.head.name),
		      Math.max ( (spi.directTrs + 1) * STEPSIZE_SMALL,
				 Math.max ((spi.indirectTrs+1)* STEPSIZE_SMALL,
					   BASICSTATE_MINWIDTH)));

	  // Die Hoehe eines Basic-States ist das Maximum aus:
	  //   Hoehe des Fonts
	  //   Mindesthoehe fuer Basic-States.

	  height = Math.max (fontHeight, BASICSTATE_MINHEIGHT);

	  // Speichere Koordinaten im jeweiligen State(-name).

	  bList.head.rect = new CRectangle (x, y, width, height);
	  if (bList.head.name != null)
	    bList.head.name.position = new CPoint
	      (x + stepsize_tiny, y + fontHeight - stepsize_tiny);

	  // Pruefe und speichere evtl. neue Maximalwerte.

	  maxCoord_x = Math.max (maxCoord_x, (x + width));

	  maxCoord_y = lh.getStateposInfo (sInfo.surrounding_state).max_y;
	  maxCoord_y = Math.max (maxCoord_y, (y + height));
	  lh.getStateposInfo (sInfo.surrounding_state).max_y = maxCoord_y;

	  // Berechne x-Offset-Position fuer naechsten Basic-State.
	  x += (width + STEPSIZE_LARGE);
	}
	bList = bList.tail;

      } // while

    } else
      y = get_andOrOffset_y(sInfo.surrounding_state);

    // Berechne Connectoren.
    // Addiere zunaechst einen Mindestabstand von den Basic-States zum ersten
    // Connector hinzu.

    x = maxCoord_x + STEPSIZE_LARGE;
    y += (Math.max (BASICSTATE_MINHEIGHT, fontHeight) +
	  Math.max (STEPSIZE_LARGE, fontHeight));

    set_conCoord (x, y);

  } // method set_basicCoord_and_conCoord



  // Setze Koordinaten der Connectoren des ´surrounding_state´ aus ´sInfo´.

  private void set_conCoord (int x, int y) {

    ConnectorList cL;
    int[] conTrs_analysis;
    int max_a = 0, max_b = 0;
    ConnectorposInfo cpi;

    if (sInfo.surrounding_state instanceof Or_State) {
      Or_State surSt = (Or_State)(sInfo.surrounding_state);

      cL = surSt.connectors;
      while (cL != null) {
	conTrs_analysis = analyse_connectorTrs(cL.head, surSt.trs);
	max_a = Math.max (conTrs_analysis[0], max_a);
	max_b = Math.max (conTrs_analysis[1], max_b);
	cL = cL.tail;
      }
      x += (max_a * STEPSIZE_MEDIUM);

      cL = surSt.connectors;
      while (cL != null) {
	if (cL.head != null) {
	  cL.head.position = new CPoint (x, y);
	  if (cL.head.name != null)

	    cL.head.name.position =
	      new CPoint (Math.max (lh.getConnectorposInfo (cL.head).counter_xa,
				   x - (getConnameLength (cL.head.name) -
					stepsize_tiny*2)/2 + CONNECTOR_SIZE/2),
			 y - stepsize_tiny);

	  conTrs_analysis = analyse_connectorTrs(cL.head, surSt.trs);

	  cpi = lh.getConnectorposInfo (cL.head);
	  cpi.counter_xa = x - conTrs_analysis[0] * STEPSIZE_MEDIUM;
	  cpi.counter_xb = x + CONNECTOR_SIZE + max_b * STEPSIZE_MEDIUM;

	  y += (Math.max (STEPSIZE_LARGE, fontHeight) +
		Math.max (conTrs_analysis[0], conTrs_analysis[1]) *
		Math.max (STEPSIZE_MEDIUM, fontHeight));

	  // Pruefe und speichere evtl. neue Maximalwerte.

	  maxCoord_x = Math.max(Math.max (maxCoord_x, cL.head.name.position.x +
					  getConnameLength (cL.head.name)),
				x + CONNECTOR_SIZE);

	  maxCoord_y = Math.max (maxCoord_y, y + CONNECTOR_SIZE);
	}
	cL = cL.tail;
      }

    } // if Or_State

  } // method set_conCoord



  private int [] analyse_connectorTrs (Connector c, TrList tL) {
    int x=0, y=0;
    Or_State surSt = (Or_State)(sInfo.surrounding_state);
    Connector con = null;
    State sta = null;

    while (tL != null) {
      if (tL.head != null)
	if ((tL.head.source != null) && (tL.head.source != null)) {

	  if (((tL.head.source instanceof Conname) &&
	       (tL.head.target instanceof Statename))) {

	    con = lh.findConnectorWithName ((Conname)(tL.head.source),
					    surSt.connectors);
	    sta = lh.findStateWithName ((Statename)(tL.head.target),
					sInfo.substates);
	  }
	  if (((tL.head.source instanceof Statename) &&
	       (tL.head.target instanceof Conname))) {

	    con = lh.findConnectorWithName ((Conname)(tL.head.target),
					    surSt.connectors);
	    sta = lh.findStateWithName ((Statename)(tL.head.source),
					sInfo.substates);
	  }

	  if (con == c)
	    if (sta instanceof Basic_State)
	      x += 1;
	    else
	      y += 1;
	}
      tL = tL.tail;
    }

    int[] analysis_result = {x, y};
    return analysis_result;
  } // method analyse_connectorTrs



  // Berechne And-/Or-Offset-Zeile.
  // Hier wird der y-Abstand ´distance_y´ berechnet, der vom oberen Rand der
  // Zeichenflaeche bis zum And-/Or-State benoetigt wird.
  //
  // Uebergabeobjekt/-wert:
  //   State-Objekt ´s´, fuer das die Offset-Zeile bestimmt werden soll
  //                     (dieses muss ein And-/Or-State sein!).
  // Rueckgabewert:
  //   And-/Or-Offset-Zeile.

  private int get_andOrOffset_y (State s) {

    // Speichervariablen zur Berechnung der Summe aller Abstaende zwischen
    // State-to-State-Linien uebergeordneter And-/Or-States von ´s´ und ´s´
    // selbst.
    int dy, distance_y;

    // Referenzvariable fuer ´s´ umfassenden State (sofern ´s´ nicht Root-
    // state ist).
    State surSt;

    // Speichervariable fuer die Anzahl der direkten Trs, die mit And-/Or-
    // States verbunden sind.
    int andOrTrs = 0;


    // Berechne die Summe aller Abstaende zwischen den ´s´ uebergeordneten
    // And-/Or-State-Linien und ´s´ selbst -> ´distance_y´.

    distance_y = 0;
    while (s != (sChart.state)) {

      // Betrachte zunaechst den umfassenden State von ´s´.

      surSt = lh.getSurroundingState (s);

      // Falls der umfassende State ein Or-State ist,betrachte seine Trs-Liste.

      if (surSt instanceof Or_State) {
	TrList trL = ((Or_State)surSt).trs;

	// Bestimme nun die Anzahl aller direkten Transitionen, die mit And-
	// oder Or-States verbunden sind.

	andOrTrs = 0;
	while (trL != null) {
	  if (trL.head.source != null && trL.head.target != null)

	    // Teste, ob es sich um eine direkte Transition handelt.

	    if ((trL.head.source instanceof Statename &&
		 trL.head.target instanceof Statename)) {

	      // Bestimme die durch Trsquelle und Trsziel beschriebenen States
	      // in der Substate-Liste von ´surSt´.
	      // HIER WIRD DIE EINDEUTIGKEIT DER STATENAMES INNERHALB EINES
	      // STATES AUSGENUTZT (-> Syntaxcheck!).

	      State src = lh.findStateWithName ((Statename)(trL.head.source),
						((Or_State)surSt).substates);
	      State trgt = lh.findStateWithName ((Statename)(trL.head.target),
						 ((Or_State)surSt).substates);

	      // Pruefe, ob Transition mit And- oder Or-States verbunden ist.

	      if ((src instanceof Or_State) || (src instanceof And_State) ||
		  (trgt instanceof Or_State) || (trgt instanceof And_State))

		andOrTrs += 1;
	    }
	  trL = trL.tail;

	} // while
      }
      // Berechne den Abstand ´dy´.
      //
      // ´dy´ = 2 * Maximum aus Standardabstand zwischen State-Linien (´STEP-
      //        SIZE_LARGE´) und Font-Hoehe
      //        +
      //        Maximum aus Standardabstand zwischen Transitionen (´STEPSIZE_
      //        MEDIUM´) und Font-Hoehe, multipliziert mit der Anzahl der Trs.
      // (Falls ´surSt´ ein And-State ist, wird das Maximum aus Standardabstand
      //  zwischen State-Linien und Font-Hoehe nur 1 mal addiert, da kein
      //  Extraplatz fuer Statenames reserviert werden muss!).

      if (surSt instanceof Or_State) {
	dy = 2 * Math.max (STEPSIZE_LARGE, fontHeight);
	dy += Math.max (STEPSIZE_MEDIUM, fontHeight) * andOrTrs;
      } else {
	dy = Math.max (STEPSIZE_LARGE, fontHeight);
	dy += Math.max (STEPSIZE_MEDIUM, fontHeight) * andOrTrs;
      }

      // Addiere ´dy´ jeweils zur Summe der bereits berechneten Abstaende.

      distance_y += dy;

      // Gehe eine Schachtelungstiefe hoeher, ´surSt´ wird zu ´s´.

      s = surSt;
    }
    return distance_y;

  } // method get_andOrOffset_y



  // Berechne Basic-Offset-Zeile.
  // Hier wird der y-Abstand ´distance_y´ berechnet, der vom oberen Rand der
  // Zeichenflaeche bis zu den in Reihe angeordneten Basic-States aus ´bL´
  // benoetigt wird.
  //
  // Uebergabeobjekt/-wert:
  //   StateList-Objekt ´bL´, mit den Basic-States, fuer die die Offset-Zeile
  //                          bestimmt werden soll.
  // Rueckgabewert:
  //   Basic-Offset-Zeile.

  private int get_basicOffset_y (StateList bL) {

    // Speichervariable zur Berechnung der Summe aller State-to-State-Trs.
    int t = 0;

    // Speichervariable fuer ´t´, multipliziert mit Einzelabstand zwischen den
    // Transitionen.
    int distance_y = 0;

    // Ermittle zunaechst den State, der die States aus der Basicstates-Liste
    // umschliesst.

    State surSt = sInfo.surrounding_state;

    // Berechne, falls ´surSt´ ein Or-State ist, aus seiner Transitionen-Liste
    // die Anzahl der direkten Trs, die von einem State zu einem anderen State
    // fuehren.

    if (surSt instanceof Or_State) {
      TrList trL = ((Or_State)surSt).trs;

      while (trL != null) {

	// Pruefe, ob Transitionsquelle/-ziel vorhanden ist (eigentlich Auf-
	// gabe des Syntaxchecks, hier nur sicherheitshalber hinzugefuegt).

	if (trL.head != null) {
	  if ((trL.head.source != null) && (trL.head.target != null))

	    // Pruefe, ob Trsquelle und Trsziel States sind.

	    if (trL.head.source instanceof Statename &&
		trL.head.target instanceof Statename)

	      t += 1;
	}
	trL = trL.tail;

      } // while

    } // if

    // Ermittle Abstand der in Reihe angeordneten Basic-States aus ´bL´ zum
    // umfassenden State.
    //
    // ´distance_y´ = 2 * Maximum aus Standardabstand zwischen State-Linien
    //                (´STEPSIZE_LARGE´) und Font-Hoehe
    //                +
    //                Maximum aus Standardabstand zwischen Transitionen (´STEP-
    //                SIZE_MEDIUM´) und Font-Hoehe, multipliziert mit der An-
    //                zahl der Transitionen.

    distance_y = 2 * Math.max (STEPSIZE_LARGE, fontHeight);
    distance_y += Math.max (STEPSIZE_MEDIUM, fontHeight) * t;

    // Ermittle AndOr-Offset.

    int andOrOffset_y = get_andOrOffset_y (sInfo.surrounding_state);

    return (andOrOffset_y + distance_y);
  } // method get_basicOffset_y



  // Berechne Laenge des Statenames ´sn´.

  private int getStatenameLength (Statename sn) {
    return ((sn.name != null) ?
	    (fMetrics.stringWidth (sn.name) + stepsize_tiny*2) : 0);
  } // method getStatenameLength



  // Berechne Laenge des Connames ´cn´.

  private int getConnameLength (Conname cn) {
    return ((cn.name != null) ?
	    (fMetrics.stringWidth (cn.name) + stepsize_tiny*2) : 0);
  } // method getConnameLength



  // Berechne Laenge des TLabels ´l´.

  private int getTLabelLength(TLabel l) {
    //    return ((l != null && l.caption != null) ?
    //	    (fMetrics.stringWidth (l.caption) + stepsize_tiny*2) : 0);
    return ((l != null && l.caption != null) ?
	    (fMetrics.stringWidth ("Testlabel") + stepsize_tiny*2) : 0);
  } // method getTLabelLength



  private void resize_andSubstates (State s) {
    StateList sL = null;

    // Pruefe, ob es sich bei ´s´ um einen And-State handelt; falls ja, passe
    // die Groesse der Substates der Groesse des And-States an.

    if (s instanceof And_State || s instanceof Or_State) {
      if (s instanceof And_State) {
	resizeSubstates ((And_State)s);
	sL = ((And_State)s).substates;
      } else {
	sL = ((Or_State)s).substates;
      }
      while (sL != null) {
	resize_andSubstates (sL.head);
	sL = sL.tail;
      }
    }
  } // method resize_andSubstates



  private void resizeSubstates (And_State s) {
    StateList sL = s.substates;
    State leftSubstate = null;
    State rightSubstate = null;


    while (sL != null) {

      if (sL.head != null) {
	if (leftSubstate == null)
	  leftSubstate = sL.head;
	else
	  if (sL.head.rect.x < leftSubstate.rect.x)
	    leftSubstate = sL.head;

	if (rightSubstate == null)
	  rightSubstate = sL.head;
	else
	  if (sL.head.rect.x + sL.head.rect.width >
	      leftSubstate.rect.x + leftSubstate.rect.width)
	    rightSubstate = sL.head;
      }
      sL.head.rect.x -= STEPSIZE_LARGE/2;
      sL.head.rect.width += STEPSIZE_LARGE;
      sL.head.rect.y = s.rect.y;
      sL.head.rect.height = s.rect.height;
      sL = sL.tail;

    } // while sL != null

    if (leftSubstate != null && rightSubstate != null) {
      int dist = leftSubstate.rect.x - s.rect.x;
      leftSubstate.rect.x -= dist;
      leftSubstate.rect.width += dist;
      rightSubstate.rect.width += dist;
    }

    sL = s.substates;
    while (sL != null) {
      if (sL.head != null)
	if (sL.head.name != null) {
	  sL.head.name.position.x = sL.head.rect.x + stepsize_tiny;
	  sL.head.name.position.y = sL.head.rect.y + fontHeight -stepsize_tiny;
	}
      sL = sL.tail;
    }

  } // method resizeSubstates


} // class SpreadAlgorithm
