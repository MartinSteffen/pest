/**
 * Listhandler
 *
 * Created: Fri Dec 25 1998, 09:10:00
 *
 * @author Developed by Eike Schulz for swtech14
 * @version $Id: Listhandler.java,v 1.1 1999-01-08 23:10:22 swtech14 Exp $
 *
 *
 * Diese Klasse enthaelt Routinen zur Behandlung/zum Aufbau spezieller Listen,
 * die fuer "SpreadAlgorithm" benoetigt werden.
 * Diese Listen sind im einzelnen:
 *
 *   StateInfoList    (ist im wesentlichen auf die And-/Or-States eines State-
 *                     chart-Objektes bezogen)
 *
 *   StateposInfoList (dient im wesentlichen der Speicherung von "Zwischenkoor-
 *                     dinatenwerten" bei der Berechnung der State-Koordinaten)
 *
 * Ferner enthaelt diese Klasse noch weitere nuetzliche Such-Methoden, die sich
 * auf die Listenklassen der Abstrakten Syntax beziehen (also State-Listen,
 * Connector-Listen, etc.).
 */

package tesc2;


import absyn.*;
import java.util.Vector;

class Listhandler extends GraphOptimizer {

  // Referenzvariable fuer uebergebenes Statechart-Objekt.
  private Statechart sChart;

  /**
   * Liste, die Informationen ueber die And-/Or-States aus ´sChart´ enthaelt.
   */
  StateInfoList siList;

  /**
   * Liste, die Positions-Informationen ueber ALLE States enthaelt.
   */
  StateposInfoList spiList;

  /**
   * Liste, die Positions-Informationen ueber alle Connectoren enthaelt.
   */
  ConnectorposInfoList cpiList;


  /**
   * Konstruktor fuer Listhandler.
   *
   * Uebergabeobjekte:
   *   Statechart-Objekt,
   */

  Listhandler (Statechart sc) {
    sChart  = sc;
    siList = null;
    spiList = null;
  } // constructor Listhandler



  // ---- (private) Methoden, die den Plazierungsalgorithmus ´vorbereiten´ ----



  // Baue StateInfo-Liste auf.
  // Jedes Element dieser Liste enthaelt anschliessend:
  //   Referenz auf einen ´surrounding_state´(NUR And-/Or-States aus ´sChart´),
  //   Markierungsvariable (zunaechst "false"),
  //   Schachtelungstiefe des ´surrounding_state´-Objekts,
  //   Substates des ´surrounding_state´-Objekts.
  // Die Liste ist nach Schachtelungstiefen geordnet, an erster Stelle stehen
  // (sofern im Statechart vorhanden) die schachtelungstiefsten And-/Or-States,
  // die nur Basic-States als Substates besitzen. Am Ende der Liste steht (der
  // Schachtelungstiefenordnung entsprechend) der Rootstate mit Tiefe "0".

  private void buildStateInfoList () {

    // Zu erstellende StateInfo-Liste zunaechst auf "null" setzen.
    siList = null;

    // Erzeuge Liste, die nur den Rootstate des Statecharts enthaelt, rufe
    // anschliessend rekursive ´addStatesInfos´-Methode auf, um die Liste wei-
    // ter aufzubauen (Uebergabeparameter "0" entspricht der Schachtelungstiefe
    // des Rootstates).

    StateList rootstateList = new StateList (sChart.state, null);
    addStatesInfos (rootstateList, 0);

  } // method buildStateInfoList



  // Rekursive Methode zur Erzeugung der StateInfo-Liste.
  //
  // Uebergabeobjekte:
  //   State-Liste,
  //   Schachtelungstiefe der States in uebergebener State-Liste.

  private void addStatesInfos (StateList actualStateList, int actualDepth) {

    // Substates-Liste der aktuellen States zunaechst auf "null" setzen.
    StateList newSubstateList = null;

    // Referenzvariable fuer betrachteten State.
    State actualState;

    // Referenzvariable fuer die aus aktuellem State zu erstellenden Infos.
    StateInfo si;


    while (actualStateList != null) {
      actualState = actualStateList.head;

      // Falls aktuell betrachteter State ein And-State ist, fuege Informatio-
      // nen ueber ihn der StateInfoList hinzu. Fuege ferner seine Substates
      // der (neuen) Substates-Liste hinzu.

      if (actualState instanceof And_State) {
	si = new StateInfo
	  (actualState, actualDepth, ((And_State)actualState).substates);

	siList = new StateInfoList (si, siList);

	newSubstateList = concatStateLists
	  (newSubstateList, ((And_State)actualState).substates);
      }

      // Falls aktuell betrachteter State ein Or-State ist, fuege Informatio-
      // nen ueber ihn der StateInfoList hinzu. Fuege ferner seine Substates
      // der (neuen) Substates-Liste hinzu.

      if (actualState instanceof Or_State) {
	si = new StateInfo
	  (actualState, actualDepth, ((Or_State)actualState).substates);

	siList = new StateInfoList (si, siList);

	newSubstateList = concatStateLists
	  (newSubstateList, ((Or_State)actualState).substates);
      }

      actualStateList = actualStateList.tail;

    } // while

    // Betrachte nun die auf Schachtelungstiefe x gesammelten Substates mit
    // Tiefe x+1 :

    if (newSubstateList != null)
      addStatesInfos (newSubstateList, actualDepth + 1);

  } // method addStatesinfos



  // Fuege zwei State-Listen zusammen.
  //
  // Uebergabeobjekte:
  //   State-Liste1,
  //   State-Liste2.
  //
  // Rueckgabeobjekt:
  //   Zusammengefuegte State-Liste.

  private StateList concatStateLists (StateList sL1, StateList sL2) {
    StateList sL = null;

    while (sL1 != null) {
      sL = new StateList (sL1.head, sL);
      sL1 = sL1.tail;
    }

    while (sL2 != null) {
      sL = new StateList (sL2.head, sL);
      sL2 = sL2.tail;
    }

    return sL;
  } // method concatStateLists



  // Baue StateposInfo-Liste aus StateInfo-Liste auf.
  // Jedes Element dieser Liste enthaelt anschliessend:
  //   Referenz auf State-Objekt (ALLE States aus ´sChart´),
  //   Counter x (zur Speicherung der "Zischenkoordinaten-Werte" x),
  //   Counter y (zur Speicherung der "Zischenkoordinaten-Werte" y),
  //   Anzahl direkter Transitionen (Transition von State zu anderem State),
  //   Anzahl indirekter Transitionen (Transitionen von State zu Connector).

  private void buildStateposInfoList () {

    // Zu erstellende StateposInfo-Liste zunaechst auf "null" setzen.
    spiList = null;

    // Referenzvariable fuer StateInfo-Liste, lasse ´siList´ unveraendert.
    StateInfoList siL = siList;

    // Referenzvariable fuer aktuell betrachtete Statepos-Informationen.
    StateposInfo spInfo = null;


    while (siL != null) {

      // Hole die Liste ´sL´ der Substates des aktuellen ´siL´-Listenelements
      // aus der StateInfo-Liste.

      StateList sL = siL.head.substates;


      // Fuege der Liste ´spiList´ jeden Substate des jeweiligen umfassenden
      // States hinzu, erhalte dadurch eine bis auf den Rootstate vollstaendige
      // Liste.

      while (sL != null) {

	// Erzeuge StateposInfo-Objekt mit Default-Werten f. Substate aus ´sL´.
	spInfo = new StateposInfo (sL.head);

	// Ermittle Anzahl direkter Transitionen des aktuellen Substates.

	if ((siL.head.surrounding_state) instanceof Or_State) {
	  spInfo.directTrs = getNumberOfDirectTrs
	    (sL.head, ((Or_State)siL.head.surrounding_state).trs);
	  spInfo.indirectTrs = getNumberOfIndirectTrs
	    (sL.head, ((Or_State)siL.head.surrounding_state).trs);
	} else;

	// Initialisiere Counter-Werte
	// (dienen als "Zwischenkoordinaten" fuer zu bestimmende Transitionen-
	//  koordinaten; "wandern" auf dem jeweiligen State entlang, um "An-
	//  schluesse" der Transitionen an einem State gleichmaessig zu vertei-
	//  len).
	// Bei Basic-State kleine Stepsize, bei And-/Or-States mittlere.

	if (spInfo.state instanceof Basic_State)
	  spInfo.counter_x = STEPSIZE_SMALL;
	else
	  spInfo.counter_x = STEPSIZE_MEDIUM;

	spiList = new StateposInfoList (spInfo, spiList);
	sL = sL.tail;
      }
      siL = siL.tail;
    }

    // Fuege der Liste den Rootstate hinzu (bei korrektem Statechart-Objekt
    // immer vorhanden!).
    // Mit dem Rootstate sind keine Transitionen verbunden, daher entfaellt
    // hier eine Ermittlung der (in)direkten Transitionen.

    spInfo = new StateposInfo (sChart.state);
    spiList = new StateposInfoList (spInfo, spiList);
  } // method buildStateposInfoList



  // Liefere Anzahl der direkten Transitionen des Uebergabe-States.
  //
  // Uebergabeobjekte:
  //   State-Objekt,
  //   Transitionen-Liste.
  //
  // Rueckgabewert:
  //   int-Wert mit Anzahl der direkten Transitionen.

  private int getNumberOfDirectTrs (State s, TrList trL) {

    // Speichervariable fuer die Anzahl der direkten Transitionen.
    int x = 0;


    // Pruefe, ob der Statename (nicht) null ist.

    if (s != null)
      if (s.name != null)

	// Pruefe im folgenden, ob die mit ´s´ verbundenen Transitionen mit
	// einem anderem State verbunden sind.

	while (trL != null) {

	  // Pruefe, ob Transitionsquelle/-ziel vorhanden ist (eigentlich Auf-
	  // gabe des Syntaxchecks, hier nur sicherheitshalber hinzugefuegt).

	  if (trL.head != null) {
	    if ((trL.head.target != null) && (trL.head.source != null))

	      // Pruefe, ob Trsquelle und -ziel jeweils States sind.

	      if ((trL.head.source instanceof Statename) &&
		  (trL.head.target instanceof Statename))

		// Pruefe, ob Trsquelle oder -ziel ´s´ ist.
		// Wenn ja, addiere Anzahl der direkten Trs um 1 auf.
		// HIER WIRD DIE EINDEUTIGKEIT DER STATENAMES INNERHALB EINES
		// UMFASSENDEN STATES AUSGENUTZT (-> Syntaxcheck!).

		if ((((Statename)(trL.head.source)).name.equals (s.name.name))
		    ||
		    (((Statename)(trL.head.target)).name.equals (s.name.name)))

		  x += 1;

	    trL = trL.tail;
	  }
	}

    return x;
  } // method getNumberOfDirectTrs



  // Liefere Anzahl der indirekten Transitionen des Uebergabe-States.
  //
  // Uebergabeobjekte:
  //   State-Objekt,
  //   Transitionen-Liste.
  //
  // Rueckgabewert:
  //   int-Wert mit Anzahl der indirekten Transitionen.

  private int getNumberOfIndirectTrs (State s, TrList trL) {

    // Speichervariable fuer die Anzahl der indirekten Transitionen.
    int x = 0;


    // Pruefe, ob der Statename (nicht) null ist.

    if (s != null)
      if (s.name != null)

	// Pruefe im folgenden, ob die mit ´s´ verbundenen Transitionen mit
	// einem Connector verbunden sind.

	while (trL != null) {

	  // Pruefe, ob Transitionsquelle/-ziel vorhanden ist (eigentlich Auf-
	  // gabe des Syntaxchecks, hier nur sicherheitshalber hinzugefuegt).

	  if (trL.head != null) {
	    if ((trL.head.target != null) && (trL.head.source != null))

	      // Pruefe, ob [Trsquelle == ´s´ und Trsziel Connector] oder
	      //            [Trsquelle Connector und Trsziel == ´s´] ist.
	      // Wenn ja, addiere Anzahl der indirekten Trs um 1 auf.
              // HIER WIRD DIE EINDEUTIGKEIT DER STATENAMES INNERHALB EINES
              // UMFASSENDEN STATES AUSGENUTZT (-> Syntaxcheck!).

	      if (((trL.head.source instanceof Statename) &&
		   (trL.head.target instanceof Conname) &&
		   (((Statename)(trL.head.source)).name.equals (s.name.name)))
		  ||
		  ((trL.head.source instanceof Conname) &&
		   (trL.head.target instanceof Statename) &&
		   (((Statename)(trL.head.target)).name.equals (s.name.name))))

		x += 1;

	    trL = trL.tail;
	  }
	}

    return x;
  } // method getNumberOfIndirectTrs



  // Teste, ob State-Liste nur Basic-States enthaelt.
  //
  // Uebergabeobjekt:
  //   State-Liste.
  //
  // Rueckgabewert:
  //   "true", falls Eigenschaft zutrifft; "false" sonst.

  private boolean states_onlyBasic (StateList sL) {
    boolean onlyBasic = true;

    while (sL != null) {
      if (!(sL.head instanceof Basic_State)) {
	onlyBasic = false;
	break;
      }
      sL = sL.tail;
    }
    return onlyBasic;
  } // method substates_onlyBasic



  // Baue ConnectorposInfo-Liste auf.
  // Jedes Element dieser Liste enthaelt anschliessend:
  //   Referenz auf einen Connector (fuer alle Connectoren aus ´sChart´),
  //   int-Wert zur Speicherung einer x-Position zwecks Trs-Verschiebung,
  //   int-Wert zur Speicherung einer y-Position zwecks Trs-Verschiebung,

  private void buildConnectorposInfoList () {
    if (sChart != null)
      analyseState (sChart.state);
  } // method buildConnectorposInfoList



  // Teste, ob es sich bei State ´s´ um einen Or-State handelt.
  // Falls ja, untersuche seine ´connectors´.
  // Falls nein, mache nichts.
  // Ferner untersuche rekursiv, falls ´s´ And-/Or-State ist, die ´substates´.
  //
  // Uebergabeobjekt:
  //   State.

  private void analyseState (State s) {

    // Referenzvariable zur Zwischenspeicherung einer Connector-Liste.
    ConnectorList cL;

    // Referenzvariable zur Zwischenspeicherung einer (Sub)state-Liste.
    StateList subs = null;


    if (s != null) {
      if (s instanceof Or_State) {

	// Untersuche ´connectors´, baue Posinfo-Liste auf.

	cL = ((Or_State)s).connectors;
	while (cL != null) {
	  cpiList = new ConnectorposInfoList (new ConnectorposInfo (cL.head),
					      cpiList);
	  cL = cL.tail;
	}

	// Betrachte ´substates´ des Or-States.

	subs = ((Or_State)s).substates;
      }

      if (s instanceof And_State) {

	// Betrachte ´substates´ des And-States.

	subs = ((And_State)s).substates;
      }

      // Durch Rekursion werden die Substates der Substates untersucht, usw.

      while (subs != null) {
	analyseState (subs.head);
	subs = subs.tail;
      }
    }
  } // method analyseState



  // -------------- Fuer die Paketklassen zugaengliche Methoden ---------------



  /**
   * Baue Listen auf.
   */

  void buildLists () {
    buildStateInfoList();
    buildStateposInfoList();
    buildConnectorposInfoList();
  } // method buildLists



  /**
   * Suche Surrounding-State fuer ´s´ (also State, der ´s´ als Substate ent-
   * haelt).
   *
   * Uebergabeobjekt:
   *   (Sub)state-Objekt.
   *
   * Rueckgabewert:
   *   (Surrounding)state-Objekt.
   */

  State getSurroundingState (State s) {

    // Referenzvariable fuer Rueckgabe-State-Objekt.
    State surroundingState = null;

    // Referenzvariable auf StateInfo-Liste.
    StateInfoList siL = siList;


    // Suche Objekt mit Substate ´s´.

    while ((siL != null) && (surroundingState == null)) {
      StateList sL = siL.head.substates;

      while (sL != null) {
	if (sL.head == s) {
	  surroundingState = siL.head.surrounding_state;
	  break;
	}
	sL = sL.tail;
      }

      siL = siL.tail;
    }
    return surroundingState;
  } // method getSurroundingState



  /**
   * Liefere StateInfo ueber State ´s´.
   *
   * Uebergabeobjekt:
   *   State-Objekt.
   *
   * Rueckgabewert:
   *   StateInfo-Objekt.
   */

  StateInfo getStateInfo (State s) {

    // Referenzvariable fuer Rueckgabe-StateInfo-Objekt.
    StateInfo si = null;

    // Referenzvariable auf StateInfo-Liste.
    StateInfoList siL = siList;


    // Suche Objekt mit Referenz auf ´s´.

    while (siL != null) {
      if (siL.head.surrounding_state == s) {
	si = siL.head;
	break;
      }
      siL = siL.tail;
    }
    return si;
  } // method getStateInfo



  /**
   * Liefere StateposInfo ueber State ´s´.
   *
   * Uebergabeobjekt:
   *   State-Objekt.
   *
   * Rueckgabeobjekt:
   *   StateposInfo-Objekt.
   */

  StateposInfo getStateposInfo (State s) {

    // Referenzvariable fuer Rueckgabe-StateposInfo-Objekt.
    StateposInfo spi = null;

    // Referenzvariable auf StateposInfo-Liste.
    StateposInfoList spiL = spiList;


    // Suche Objekt mit Referenz auf ´s´.

    while (spiL != null) {
      if (spiL.head.state == s) {
	spi = spiL.head;
	break;
      }
      spiL = spiL.tail;
    }
    return spi;
  } // method getStateposInfo



  /**
   * Liefere ConnectorposInfo ueber Connector ´c´.
   *
   * Uebergabeobjekt:
   *   Connector-Objekt.
   *
   * Rueckgabeobjekt:
   *   ConnectorposInfo-Objekt.
   */

  ConnectorposInfo getConnectorposInfo (Connector c) {

    // Referenzvariable fuer Rueckgabe-ConnectorposInfo-Objekt.
    ConnectorposInfo cpi = null;

    // Referenzvariable auf ConnectorposInfo-Liste.
    ConnectorposInfoList cpiL = cpiList;


    // Suche Objekt mit Referenz auf ´c´.

    while (cpiL != null) {
      if (cpiL.head.con == c) {
	cpi = cpiL.head;
	break;
      }
      cpiL = cpiL.tail;
    }
    return cpi;
  } // method getConnectorposInfo



  /**
   * Pruefe, ob alle And-/Or-States in ´sL´ markiert sind.
   *
   * Uebergabeobjekt:
   *   State-Liste, fuer die diese Eigenschaft geprueft werden soll.
   *
   * Rueckgabewert:
   *   "true", falls Eigenschaft erfuellt; "false" sonst.
   */

  boolean all_andOrStates_marked (StateList sL) {
    if (getFirstUnmarked_andOrState (sL) == null)
      return true;
    else
      return false;
  } // method all_andOrStates_marked



  /**
   * Finde ersten unmarkierten And-/Or-State in ´sL´.
   *
   * Uebergabeobjekt:
   *   State-Liste, in der gesucht werden soll.
   *
   * Rueckgabewert:
   *   Gefundener State; "null", falls kein State gefunden wurde.
   */

  State getFirstUnmarked_andOrState (StateList sL) {
    State s = null;

    while (sL != null) {
      if ((sL.head instanceof And_State) || (sL.head instanceof Or_State))
	if (getStateInfo (sL.head).marked == false) {
	  s = sL.head;
	  break;
	}
      sL = sL.tail;
    }
    return s;
  } // method getFirstUnmarked_andOrState



  /**
   * Finde einen (evtl. um einige Stufen tiefer geschachtelten) ersten unmar-
   * kierten Substate von ´s´,der nur noch Basic-States als Substates enthaelt.
   * Liefere State-Informationen ueber diesen State.
   *
   * Uebergabeobjekt:
   *   And-/Or-State.
   *
   * Rueckgabeobjekt:
   *   StateInfo-Objekt fuer State mit obiger Eigenschaft.
   */

  StateInfo get_stateWithSubstatesOnlyBasic_info (State s) {
    StateInfo si = null;

    StateList sL = (s instanceof And_State) ?
      ((And_State)s).substates :
      ((Or_State)s).substates;

    if (states_onlyBasic (sL))
      si = getStateInfo (s);
    else {
      s = getFirstUnmarked_andOrState (sL);
      si = get_stateWithSubstatesOnlyBasic_info (s);
    }
    return si;
  } // method get_stateWithSubstatesOnlyBasic_info



  /**
   * Ermittle das y-Maximum der in ´sL´ enthaltenen And-/Or-States.
   *
   * Uebergabeobjekt:
   *   State-Liste.
   *
   * Rueckgabewert:
   *   Maximum.
   */

  int maxline_of_andOrStates (StateList sL) {
    int max = 0;

    while (sL != null) {
      if (!(sL.head instanceof Basic_State))
	max = Math.max (max, getStateposInfo (sL.head).max_y);
      sL = sL.tail;
    }
    return max;
  } // method maxline_of_andOrStates



  /**
   * Setze Counter-Werte in der StateposInfo-Liste auf default-Werte zurueck.
   */

  void resetCounters () {

    // Referenzvariable fuer StateposInfo-Liste, lasse ´spiList´ unveraendert.
    StateposInfoList spiL = spiList;


    while (spiL != null) {
      spiL.head = spiL.head;

      // Reset der Counter-Werte

      if (spiL.head.state instanceof Basic_State)
	spiL.head.counter_x = STEPSIZE_SMALL;
      else
	spiL.head.counter_x = STEPSIZE_MEDIUM;

      spiL.head.counter_y = 0;
      spiL = spiL.tail;
    }

  } // method resetCounters



  // ---------- Sonstige fuer die Paketklassen zugaengliche Methoden ----------



  /**
   * Pruefe, ob ein Connector mit Conname ´cn´ in Liste ´cL´ vorhanden ist.
   *
   * Uebergabeobjekte:
   *   Conname-Objekt des zu suchenden Namens;
   *   ConnectorList-Objekt der Liste, in der gesucht werden soll.
   *
   * Rueckgabeobjekt:
   *   Connector-Objekt, falls ´cn´ in ´cL´ gefunden wurde; "null" sonst.
   */

  Connector findConnectorWithName (Conname cn, ConnectorList cL) {
    Connector found = null;

    while (cL != null) {
      if (cL.head != null)
	if(cL.head.name != null)
	  if (cn.name.equals (cL.head.name.name)) {
	    found = cL.head;
	    break;
	  }
      cL = cL.tail;
    }
    return found;

  } // method findConnectorWithName



  /**
   * Pruefe, ob ein State mit Statename ´sn´ in Liste ´sL´ vorhanden ist.
   *
   * Uebergabeobjekte:
   *   Statename-Objekt des zu suchenden Namens;
   *   StateList-Objekt der Liste, in der gesucht werden soll.
   *
   * Rueckgabeobjekt:
   *   State-Objekt, falls ´sn´ in ´sL´ gefunden wurde; "null" sonst.
   */

  State findStateWithName (Statename sn, StateList sL) {
    State found = null;

    while (sL != null) {
      if (sL.head != null)
	if(sL.head.name != null)
	  if (sn.name.equals (sL.head.name.name)) {
	    found = sL.head;
	    break;
	  }
      sL = sL.tail;
    }
    return found;

  } // method findStateWithName



  /**
   * Gib Vektor zurueck, der die Liste mit Basic-States aus ´sL´, sowie
   * die Anzahl der Basic-States enthaelt.
   *
   * Uebergabeobjekt:
   *   State-Liste, aus der Informationen bestimmt werden sollen.
   *
   * Rueckgabeobjekt:
   *   Vektor, der folgende Komponenten enthaelt:
   *     0) Liste mit Basic-States aus ´sL´,
   *     1) int-Wert mit Anzahl der Basic-States in ´sL´.
   */

  Vector getListAndNumberOf_Basicstates (StateList sL) {

    // Referenzvariable auf zurueckzugebende State-Liste (mit Basic-States).
    StateList bList = null;

    // Speichervariable fuer die Anzahl der Basic-States.
    int bNumber = 0;


    // Suche nach Basic-States in ´sL´, erstelle Liste, und bestimme Anzahl.

    while (sL != null) {
      if (sL.head instanceof Basic_State) {
	bList = new StateList (sL.head, bList);
	bNumber += 1;
      }
      sL = sL.tail;
    }

    // Konstruiere Rueckgabewert.

    Vector v = new Vector ();
    v.addElement (bList);
    v.addElement (new Integer (bNumber));
    return v;

  } // method getListAndNumberOf_Basicstates



  /**
   * Gib die Anzahl der Connectoren aus ´cL´ zurueck.
   *
   * Uebergabeobjekt:
   *   Connector-Liste ´cL´.
   *
   * Rueckgabeobjekt:
   *   int-Wert, der die Anzahl der Connectoren in ´cL´ beschreibt.
   */

  int getNumberOf_Connectors (ConnectorList cL) {
    int cNumber = 0;

    // Suche nach Connectors in ´cL´, bestimme Anzahl.

    while (cL != null) {
      cNumber += 1;
      cL = cL.tail;
    }
    return cNumber;
  } // method getNumberOf_Connectors


} // class Listhandler
