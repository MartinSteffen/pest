package simu;

import java.awt.*;
/**
 * Diese Klasse stellt das Grafikfenster auf dem Monitor zur Verfügung.
 * Die anzuzeigenden Daten werden dabei vom Monitor selbst in das
 * Array "coords" geladen; alsdann wird ein repaint ausgeführt, welches
 * zuerst den Grafikbereich löscht und dann die Methode paint(Graphics)
 * aufruft, die die Daten auf den Grafikbereich bringt.
 * 
 */
class DrawFrame extends Canvas {
	Monitor hMon;
	int debugingCounter = 0;
	int coords[] [] = new int[20][21];	// y-Pos, x-Pos, [21] = -1
	int beamPos;									// Position des Anzeigebalkens
	int beamPosLeftOffset = 0;			// Korrektur für den linken 
	int beamPosRightOffset = 0;		// und  rechten Rand
	boolean back = true;					// zeigt an,ob ein ausgefüllter oder ein
															// leerer Balken gezeichnet werden soll
															// ( 2 Fall : bei Veränderungen im bv-Vektor oder se-Vektor
															//	die noch nicht im Monitor angezeigt werden).
/**
 * DrawFrame constructor comment.
 */
public DrawFrame(Monitor arg1) {
	super();
	initCoords();
	hMon = arg1;
	repaint();
}
/**
 * Diese Methode dient zum initialisieren des Koordinatenarrays.
 *
 */
void initCoords() {
	for (int i = 0; i<20; i++){
		for (int j = 0; j < 21; j++){
			coords[i][j] = -1;
		}
	}
	//System.out.println("DrawFrame : initCoords() : Erfolgreich beendet !");
}
/**
 *	beamPos;								 Position des Anzeigebalkens
 *	ibeamPosLeftOffset = 0;		 Korrektur für den linken 
 * ibeamPosRightOffset = 0;	 und  rechten Rand
 *
 *
 * a) Der Anzeigebalken wird gemalt. Die Position ermittelt paint(Graphics) selbst !
 * b) Die Linien werden gezeichnet.
 * c) Zuerst wird ein Rahmen um den Graphikbereich gezeichnet.
 *
 * @param g java.awt.Graphics
 */
public void paint(Graphics g) {

	int yOffset1 = 0;
	int yOffset2 = 0;
	int xCoord1 = 0;
	int xCoord2 = 15;
	int yCoord = 22;
	boolean visible = true;
	int defaultOffset = -16;

	//System.out.println("DrawFrame : paint() wurde aufgerufen !");
		
	// a) Anzeigebalken malen
	
	// Position und Korrektur ermitteln:
	beamPos = hMon.getScrollbar3().getValue();
	beamPos = beamPos/2;
	if (beamPos == 0){
		beamPosLeftOffset = 1;
		beamPosRightOffset = 0;
	}
	if (beamPos == 29){
		beamPosLeftOffset = 0;
		beamPosRightOffset = -1;
	}
	// den Anzeigebalken selbst malen
	g.setColor(java.awt.Color.yellow);
		
	if (back == true){	
		g.fillRect((15*beamPos)+beamPosLeftOffset,1,14+beamPosRightOffset,554);
	}
	else{
		g.drawRect((15*beamPos)+beamPosLeftOffset,1,14+beamPosRightOffset,554);
		back = true;
	}



	// b)
	g.setColor(java.awt.Color.black);

	for (int i = 0; i < 20; i++){
	
		// y-Koordinate des ersten Punktes einer Linie berechnen
		if (coords[i][0] == 0){
			yOffset1 = 0;
			visible = true;
		}
		else{
			if (coords[i][0] == 1){
				yOffset1 = defaultOffset;
				visible = true;
			}
			else{
				// es darf keine Linie gezogen werden
				//System.out.println("DrawFrame : paint : '// es darf keine Linie gezogen werden' Nr 1. ");
				visible = false;
			}
		}	
	
		for (int j = 1; j < 21; j++){
			// Ein Teilstück der i-ten Linie zeichnen

			// erste Linie ( das ist die waagerechte ) zeichnen ( oder auch nicht :-)
			if (visible){
				g.drawLine(xCoord1,yCoord+yOffset1,xCoord2,yCoord+yOffset1);
			}

			// y-Koordinate des nächsten Punktes berechnen
			if (coords[i][j] == 0){
				yOffset2 = 0;
				visible = true;
			}
			else{
				if (coords[i][j] == 1){
					yOffset2 = defaultOffset;
					visible = true;
				}
				else{
					// es darf keine Linie gezogen werden
					visible = false;
					//System.out.println("DrawFrame : paint : '// es darf keine Linie gezogen werden' Nr 2. ");
				}
			}
			
			// zweite Linie ( das ist die senkrechte ) zeichnen ( oder auch nicht :-)
			if ((visible==true)&(yOffset1 != yOffset2)){
				g.drawLine(xCoord2,yCoord+yOffset1,xCoord2,yCoord+yOffset2);
			}

			// Ergebnisse dieser Berechnung in den nächsten Schleifendurchgang übernehmen:
			yOffset1 = yOffset2;
			
			// update auf die x-Koordinaten durchführen :
			xCoord1 = xCoord1 + 15;
			xCoord2 = xCoord2 + 15;
		}

		// die j-te Linie ist gezeichnet, deshalb ein update auf die y-Koordinate und die x-Koordinaten :
		yCoord = yCoord + 28;
		xCoord1 = 0;
		xCoord2 = 15;
	}


	// c) Rahmen um das Fenster legen !
	g.setColor(java.awt.Color.black);
	g.drawRect(0,0,299,555);

	//System.out.println("DrawFrame : paint() : Koorekt beendet !");
}
}