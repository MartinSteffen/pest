
/**
 * CircleLayoutAlgorithm.java
 *
 *
 * Created: Thu Jan 21 14:29:23 1999
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;
import java.awt.*;

public class CircleLayoutAlgorithm extends GeneralLayoutAlgorithm {
    
    /* Radius eines Kreises für einen Connector */
    static final double CONNECTOR_RADIUS = 10.0;
    static final double RADIUS_BONUS = 10.0;

    /**
     * Konstruktor
     */
    public CircleLayoutAlgorithm(GraphOptimizer _go) {
	super(_go);
    }

    /**
     * Führt das Layout für einen Or_State durch 
     */
    public void layoutORState(Or_State s) {


	/* Die substates werden der Bequemlichkeit halber erstmal in ein
	   Array umgeschaufelt */

	StateList substates;
	int substateCount=0;
	substates = s.substates;
	while (substates!=null) {
	    substateCount++;
	    substates = substates.tail;
	}
	State[] substateArray = new State[substateCount];
	substateCount=0;
	substates = s.substates;
	while (substates!=null) {
	    substateArray[substateCount]=substates.head;
	    substateCount++;
	    substates = substates.tail;
	}

	/* Die connectors werden auch umgeschaufelt */

	ConnectorList connectorList = s.connectors;
	int connectorCount = 0;
	while (connectorList!=null) {
	    connectorCount++;
	    connectorList = connectorList.tail;
	}
	Connector[] connectorArray = new Connector[connectorCount];
	connectorList = s.connectors;
	connectorCount = 0;
	while (connectorList!=null) {
	    connectorArray[connectorCount] = connectorList.head;
	    connectorCount++;
	    connectorList = connectorList.tail;
	}	

	/* Das Layout der substates wird durchgeführt */
	for (int i=0;i<substateCount;i++) {
	    layoutState(substateArray[i]);
	}

	/* Es werden die Radien der substates berechnet */

	double[] substateRadius = new double[substateCount];
	double width,height;

	for (int i=0;i<substateCount;i++) {
	    width = substateArray[i].rect.width;
	    height = substateArray[i].rect.height;
	    substateRadius[i] = RADIUS_BONUS+
		0.5*Math.sqrt(width*width+height*height);
	}

	/* Die substates und connectors werden in eine Reihenfolge
	   gebracht, in der sie um den Transitionskreis angeordnet 
	   werden */

	int layoutElementCount = substateCount+connectorCount;
	LayoutElement[] layoutElement = new LayoutElement[layoutElementCount];
	for (int i=0;i<substateCount;i++) 
	    layoutElement[i] = 
		new LayoutElementState(substateArray[i],substateRadius[i]);
	for (int i=0;i<connectorCount;i++)
	    layoutElement[substateCount+i] =
		new LayoutElementConnector(connectorArray[i]);

	/** !!!!!!!!!!!!!!!!!
	 * An dieser Stelle wäre es sinnvoll, die Reihenfolge so zu ändern,
	 * daß möglichst wenig Kantenüberschneidungen entstehen
	 */

	/* Fallunterscheidung nach Anzahl der layoutElements */
	double trCircleRadius=0;
	double trCircleX = 0;
	double trCircleY = 0;

	if (layoutElementCount==1) {

	    layoutElement[0].x = 0;
	    layoutElement[0].y = 0;
	    layoutElement[0].angle_to_transitionCircle = 0;
	    
	} else {

	    if (layoutElementCount==2) {
		/* In diesem Fall wird der Transitionskreisradius
		   fest gewählt. */

		trCircleRadius = 20;

	    } else {

		/* Der Radius des Transitionskreises wird berechnet */
		double[] radius = new double[layoutElementCount];
		for (int i=0;i<layoutElementCount;i++)
		    radius[i] = layoutElement[i].radius;
		trCircleRadius = transitionCircleRadius(radius);
	    }

	    /** !!!!!!!!!
	     * An dieser Stelle einbauen, daß der Radius des 
	     * Transitionskreises nicht zu klein wird
	     */
	    
	    trCircleRadius = Math.max(trCircleRadius,20.0);

	    /* Die Mittelpunkte der layoutElement-Kreise werden berechnet. */
	    
	    double phi = 0;
	    double h1;

	    if (layoutElementCount==2) {
		h1 =trCircleRadius+layoutElement[0].radius;
		phi = 0.5*Math.PI;
		layoutElement[0].x = Math.cos(phi)*h1;
		layoutElement[0].y = Math.sin(phi)*h1;
		layoutElement[0].angle_to_transitionCircle = 
		    (phi+Math.PI)%(2*Math.PI);
		h1 =trCircleRadius+layoutElement[1].radius;
		phi = 1.5*Math.PI;
		layoutElement[1].x = Math.cos(phi)*h1;
		layoutElement[1].y = Math.sin(phi)*h1;
		layoutElement[1].angle_to_transitionCircle = 
		    (phi+Math.PI)%(2*Math.PI);
	    } else {
		for (int i=0;i<layoutElementCount;i++) {
		    h1 = trCircleRadius+layoutElement[i].radius;
		    layoutElement[i].x = Math.cos(phi)*h1;
		    layoutElement[i].y = Math.sin(phi)*h1;
		    layoutElement[i].angle_to_transitionCircle = 
			(phi+Math.PI)%(2*Math.PI);
		    if (i!=layoutElementCount-1) {
			phi = phi + gamma(trCircleRadius,
					  layoutElement[i].radius,
					  layoutElement[i+1].radius);
		    }
		}
	    }	    
	} /* Ende der Fallunterscheidung nach layoutElementCount */
    
	/* An dieser Stelle besitzen alle layoutElement-Kreise einen
	   Radius und Mittelpunkt */

	/* Berechnung der Eckkoordinaten der layoutElements.
	   Gleichzeitig werden die minimalen und maximalen x- und y-Werte
	   berechnet, um die Breite und Höhe des State s zu berechnen. */

	double xmin=0,ymin=0,xmax=0,ymax=0;
	for (int i=0;i<layoutElementCount;i++) {
	    if (layoutElement[i] instanceof LayoutElementState) {
		LayoutElementState layoutElementState =
		    (LayoutElementState) layoutElement[i];
		layoutElementState.state.rect.x = (int)
		    layoutElementState.x - 
		    layoutElementState.state.rect.width/2;
		layoutElementState.state.rect.y = (int)
		    layoutElementState.y - 
		    layoutElementState.state.rect.height/2;
		xmin = Math.min(xmin,
				layoutElementState.x-
				layoutElementState.radius-
				WIDTH_BONUS);
		ymin = Math.min(ymin,
				layoutElementState.y-
				layoutElementState.radius-
				HEIGHT_BONUS);
		xmax = Math.max(xmax,
				layoutElementState.x+
				layoutElementState.radius+
				WIDTH_BONUS);
		ymax = Math.max(ymax,
				layoutElementState.y+
				layoutElementState.radius+
				HEIGHT_BONUS);				
	    } else {
		LayoutElementConnector layoutElementConnector =
		    (LayoutElementConnector) layoutElement[i];
		layoutElementConnector.connector.position =
		    new CPoint((int) layoutElementConnector.x,
			       (int) layoutElementConnector.y);
		xmin = Math.min(xmin,
				layoutElementConnector.x-
				layoutElementConnector.radius-
				WIDTH_BONUS);
		ymin = Math.min(ymin,
				layoutElementConnector.y-
				layoutElementConnector.radius-
				HEIGHT_BONUS);
		xmax = Math.max(xmax,
				layoutElementConnector.x+
				layoutElementConnector.radius+
				WIDTH_BONUS);
		ymax = Math.max(ymax,
				layoutElementConnector.y+
				layoutElementConnector.radius+
				HEIGHT_BONUS);
	    }	
	}

	/* Damit ergibt sich schon mal die Breite des state s */

	s.rect.width = (int) (xmax-xmin);
	s.rect.height = (int) (ymax-ymin);

	/* Hier wird die Position des Namens des state relativ zu seiner
	   eigenen linken oberen Ecke angegeben. Später (beim Layout des
	   umgebenden state) wird diese Position
	   dann umgewandelt in eine relative bzgl. des umgebenden
	   state */

	s.name.position = new CPoint(5,go.fm.getHeight());

	/* Die Eckkoordinaten der layoutElements werden um xmin nach rechts
	   und ymin nach unten verschoben */
	trCircleX-=xmin;
	trCircleY-=ymin;
	for (int i=0;i<layoutElementCount;i++) {
	    layoutElement[i].x -= xmin;
	    layoutElement[i].y -= ymin;
	    if (layoutElement[i] instanceof LayoutElementState) {
		((LayoutElementState) layoutElement[i]).state.rect.x-=
		    (int) xmin;
		((LayoutElementState) layoutElement[i]).state.rect.y-=
		    (int) ymin;
		
	    } else {
		((LayoutElementConnector) layoutElement[i]).
		    connector.position.x -=
		    (int) xmin;
		((LayoutElementConnector) layoutElement[i]).
		    connector.position.y -=
		    (int) ymin;
	    }
	}

	/* Die Positionen der Namen der substates werden gesetzt:
	   Beim Layout von substate wurde die Position schon relativ
	   zur eigenen linken oberen Ecke gesetzt, also muß dieser
	   Wert hier nur noch verschoben werden, sodaß sie relativ zur
	   Ecke des state s ist */

	for (int i=0;i<layoutElementCount;i++) {
	    if (layoutElement[i] instanceof LayoutElementState) {
		LayoutElementState layoutElementState = 
		    (LayoutElementState) layoutElement[i];
		layoutElementState.state.name.position.
		    translate(layoutElementState.state.rect.x,
			      layoutElementState.state.rect.y);
	    }
	}

	/**
	 * Jetzt wirds Zeit, die TRANSITIONEN zu plazieren 
	 */

	/* Zuerst werden für jedes layoutElement die Sektoren bestimmt, 
	   in denen Transitionszwischenpunkte liegen dürfen bzw. in
	   denen Loopzwischenpunkte liegen dürfen.
	   Transitionszwischenpunkte liegen auf dem Transitionskreis,
	   Loopzwischenpunkte liegen auf dem layoutElement-Kreis. */
	double[] transitionSectorAngle = new double[layoutElementCount];
	for (int i=0;i<layoutElementCount-1;i++) {
	    transitionSectorAngle[i] = 
		evalTransitionSectorAngle(layoutElement[i].angle_to_transitionCircle,
					  layoutElement[i+1].angle_to_transitionCircle);					  
	}
	transitionSectorAngle[layoutElementCount-1] = 
	    evalTransitionSectorAngle(layoutElement[layoutElementCount-1].angle_to_transitionCircle,
				      layoutElement[0].angle_to_transitionCircle);

	layoutElement[0].transitionSectorStart = 
	    transitionSectorAngle[layoutElementCount-1];
	layoutElement[0].transitionSectorEnd =
	    transitionSectorAngle[0];
	for (int i=1;i<layoutElementCount;i++) {
	    layoutElement[i].transitionSectorStart = 
		transitionSectorAngle[i-1];
	    layoutElement[i].transitionSectorEnd = 
		transitionSectorAngle[i];

	    layoutElement[i].loopSectorStart =
		(layoutElement[i].angle_to_transitionCircle+Math.PI/2)%
		(2*Math.PI);
	    layoutElement[i].loopSectorEnd =
		(layoutElement[i].angle_to_transitionCircle-Math.PI/2)%
		(2*Math.PI);
	}
	layoutElement[0].loopSectorStart =
	    (layoutElement[0].angle_to_transitionCircle+Math.PI/2)%
	    (2*Math.PI);
	layoutElement[0].loopSectorEnd =
	    (layoutElement[0].angle_to_transitionCircle-Math.PI/2)%
	    (2*Math.PI);

	/* Zur besseren Bearbeitung der transitions werden diese erstmal
	   in ein Array geschaufelt */
	int transitionCount=0;
	TrList transitionList = s.trs;
	while (transitionList!=null) {
	    transitionCount++;
	    transitionList=transitionList.tail;
	}
	Tr[] transitionArray = new Tr[transitionCount];
	transitionCount=0;
	transitionList = s.trs;
	while (transitionList!=null) {
	    transitionArray[transitionCount] = transitionList.head;
	    transitionCount++;
	    transitionList=transitionList.tail;
	}
	
	/* Jetzt werden die für jedes layoutElement nötigen 
	   Transitionszwischenpunkte und Loopzwischenpunkte gezählt */
	for (int i=0;i<transitionCount;i++) {
	    TrAnchor source = transitionArray[i].source;
	    TrAnchor target = transitionArray[i].target;

	    int layoutElementIndexToSource =
		findLayoutElementIndexToAnchor(source,layoutElement);
	    int layoutElementIndexToTarget =
		findLayoutElementIndexToAnchor(target,layoutElement);
	    
	    if ((layoutElementIndexToSource==-1)&&
		(layoutElementIndexToTarget==-1)) {
		//System.out.println("Bekloppter Fall von blöder Transition");
	    } else if (layoutElementIndexToSource==
		       layoutElementIndexToTarget) {
		layoutElement[layoutElementIndexToSource].
		    loopPointCount+=2;				
	    } else {
		if (layoutElementIndexToSource!=-1)
		    layoutElement[layoutElementIndexToSource].
			transitionPointCount++;
		if (layoutElementIndexToTarget!=-1)
		    layoutElement[layoutElementIndexToTarget].
			transitionPointCount++;
	    }
	    
	}

	/* Die LoopPoints werden berechnet */
	for (int i=0;i<layoutElementCount;i++) {
	    LayoutElement le = layoutElement[i];
	    le.loopPoint = 
		new CPoint[le.loopPointCount];
	    le.nextLoopPointIndex=0;
	    double delta_angle = (le.loopSectorEnd+2*Math.PI-
				  le.loopSectorStart)%(2*Math.PI);
	    /* Winkel auf 90 Grad beschränken */
	    delta_angle = Math.min(delta_angle,1.57);
	    
	    for (int j=0;j<le.loopPointCount;j++) {		
		double angle = le.loopSectorStart+(j+1)*
		    delta_angle/(le.loopPointCount+2);
		
		le.loopPoint[j] =
		    new CPoint((int) (le.x+le.radius*Math.cos(angle)),
			       (int) (le.y+le.radius*Math.sin(angle)));
		
	    }
	}

	if (layoutElementCount>1) {
	    /* Die TransitionPoints werden berechnet */
	    for (int i=0;i<layoutElementCount;i++) {
		LayoutElement le = layoutElement[i];
		le.transitionPoint = new CPoint[le.transitionPointCount];
		le.nextTransitionPointIndexFromLeft=0;
		le.nextTransitionPointIndexFromRight=le.transitionPointCount-1;
		double delta_angle = (le.transitionSectorEnd+2*Math.PI-
				      le.transitionSectorStart)%(2*Math.PI);
		for (int j=0;j<le.transitionPointCount;j++) {
		    double angle = le.transitionSectorStart+(j+1)*
			(delta_angle)/(le.transitionPointCount+2);
		    le.transitionPoint[j] =
			new CPoint((int) (trCircleX+trCircleRadius*
					  Math.cos(angle)),
				   (int) (trCircleY+trCircleRadius*
					  Math.sin(angle)));
		}
	    }
	}

	/* Jetzt werden die Koordinaten der Transitionen gesetzt */

	for (int i=0;i<transitionCount;i++) {
	    TrAnchor source = transitionArray[i].source;
	    TrAnchor target = transitionArray[i].target;

	    int layoutElementIndexToSource =
		findLayoutElementIndexToAnchor(source,layoutElement);
	    int layoutElementIndexToTarget =
		findLayoutElementIndexToAnchor(target,layoutElement);
	    
	    /* Fall 1:von UNDEFINED nach UNDEFINED */
	    if ((layoutElementIndexToSource==-1)&&
		(layoutElementIndexToTarget==-1)) {
		transitionArray[i].points = new CPoint[2];
		transitionArray[i].points[0] = new CPoint(10,10);
		transitionArray[i].points[1] = new CPoint(50,10);
	    } else {
		/* Fall 2:von x nach x (Loop) */
		if (layoutElementIndexToSource==
		    layoutElementIndexToTarget) {
		    transitionArray[i].points = new CPoint[4];
		    if (layoutElement[layoutElementIndexToSource] instanceof
			LayoutElementState) {
			LayoutElementState les = (LayoutElementState)
			    layoutElement[layoutElementIndexToSource];
			transitionArray[i].points[0] = 
			    findPointNextToRectangle(les.loopPoint[les.nextLoopPointIndex],
						     les.state.rect);
			transitionArray[i].points[1] =
			    les.loopPoint[les.nextLoopPointIndex];
			les.nextLoopPointIndex++;
			transitionArray[i].points[2] =
			    les.loopPoint[les.nextLoopPointIndex];
			transitionArray[i].points[3] = 
			    findPointNextToRectangle(les.loopPoint[les.nextLoopPointIndex],
						     les.state.rect); 
			les.nextLoopPointIndex++;
		    } else {
			LayoutElementConnector lec = (LayoutElementConnector)
			    layoutElement[layoutElementIndexToSource];
			transitionArray[i].points[0] =
			    new CPoint((int)lec.x,(int)lec.y);
			transitionArray[i].points[1] =
			    lec.loopPoint[lec.nextLoopPointIndex];
			lec.nextLoopPointIndex++;
			transitionArray[i].points[2] =
			    lec.loopPoint[lec.nextLoopPointIndex];
			lec.nextLoopPointIndex++;
			transitionArray[i].points[3] =
			    new CPoint((int)lec.x,(int)lec.y);
		    }
		} /* Ende von Fall 2 */
		else if ((layoutElementIndexToSource!=-1)&&
			 (layoutElementIndexToTarget!=-1)) {
		    /* Fall 3: von x nach y */

		    transitionArray[i].points = new CPoint[4];
		    double right_distance,left_distance;
		    int d = layoutElementIndexToTarget-
			layoutElementIndexToSource;
		    if (d>0) {
			right_distance = d;
			left_distance = layoutElementCount-d;
		    } else {
			right_distance = layoutElementCount+d;
			left_distance = -d;
		    }

		    LayoutElement le_source = 
			layoutElement[layoutElementIndexToSource];
		    LayoutElement le_target =
			layoutElement[layoutElementIndexToTarget];
		    if (right_distance>=left_distance) {
			transitionArray[i].points[1] = 
			    le_source.
			    transitionPoint[le_source.
					   nextTransitionPointIndexFromLeft];
			le_source.nextTransitionPointIndexFromLeft++;
			transitionArray[i].points[2] = 
			    le_target.
			    transitionPoint[le_target.
					   nextTransitionPointIndexFromRight];
			le_target.nextTransitionPointIndexFromRight--;
		    } else {
			transitionArray[i].points[1] = 
			    le_source.
			    transitionPoint[le_source.
					   nextTransitionPointIndexFromRight];
			le_source.nextTransitionPointIndexFromRight--;
			transitionArray[i].points[2] = 
			    le_target.
			    transitionPoint[le_target.
					   nextTransitionPointIndexFromLeft];
			le_target.nextTransitionPointIndexFromLeft++;
		    }
		    if (le_source instanceof LayoutElementState) {
			transitionArray[i].points[0] =
			    findPointNextToRectangle(transitionArray[i].points[1],
						     ((LayoutElementState) le_source).state.rect);
		    } else {
			transitionArray[i].points[0] =
			    new CPoint((int) le_source.x,
				       (int) le_source.y);
		    }
		    if (le_target instanceof LayoutElementState) {
			transitionArray[i].points[3] =
			    findPointNextToRectangle(transitionArray[i].points[2],
						     ((LayoutElementState) le_target).state.rect);
		    } else {
			transitionArray[i].points[3] =
			    new CPoint((int) le_target.x,
				       (int) le_target.y);
		    }
		} /* Ende von Fall 3 */
		else if (layoutElementIndexToSource!=-1) {
		    /* Fall 4: von x nach UNDEFINED */

		    transitionArray[i].points = new CPoint[2];

		    LayoutElement le_source = 
			layoutElement[layoutElementIndexToSource];

		    transitionArray[i].points[1] = 
			    le_source.
			    transitionPoint[le_source.
					   nextTransitionPointIndexFromRight];
		    le_source.nextTransitionPointIndexFromRight--;
		    
		    if (le_source instanceof LayoutElementState) {
			transitionArray[i].points[0] =
			    findPointNextToRectangle(transitionArray[i].points[1],
						     ((LayoutElementState) le_source).state.rect);
		    } else {
			transitionArray[i].points[0] =
			    new CPoint((int) le_source.x,
				       (int) le_source.y);
		    }
		} else if (layoutElementIndexToTarget!=-1) {
		    /* Fall 5: von UNDEFINED nach x */

		    transitionArray[i].points = new CPoint[2];

		    LayoutElement le_target = 
			layoutElement[layoutElementIndexToTarget];

		    transitionArray[i].points[0] = 
			    le_target.
			    transitionPoint[le_target.
					   nextTransitionPointIndexFromRight];
		    le_target.nextTransitionPointIndexFromRight--;
		    
		    if (le_target instanceof LayoutElementState) {
			transitionArray[i].points[1] =
			    findPointNextToRectangle(transitionArray[i].points[0],
						     ((LayoutElementState) le_target).state.rect);
		    } else {
			transitionArray[i].points[1] =
			    new CPoint((int) le_target.x,
				       (int) le_target.y);
		    }

		} /* Ende aller Fallunterscheidungen */
		
	    }

	    /* Die Position der Transitionsbeschriftung wird gesetzt */
	    transitionArray[i].label.position=
		new CPoint(transitionArray[i].points[1]);
	}

    }; 
    /**
     *=====================================================================
     * Ende von layoutORState 
     *=====================================================================
     */


    private double evalTransitionSectorAngle(double a1,double a2) {
	double delta,res;
	a1 = (a1+Math.PI)%(2*Math.PI);
	a2 = (a2+Math.PI)%(2*Math.PI);
	
	delta =
	    (a2-a1+2*Math.PI)%(2*Math.PI);
	res =
	    (a1+delta/2)%
	    (2*Math.PI);
	return res;
    }

	
    /**
     * Berechnet den Radius des Transitionskreises.
     * Das Array r beinhaltet die Radien der substates.
     */
    private double transitionCircleRadius(double[] r) {

	/*
	System.out.print("Radien=");
	for (int i=0;i<r.length;i++)
	    System.out.print(r[i]+" ");
	System.out.println();
	*/
	
	
	final double eps = 1.e-5; /* Genauigkeit der Näherung */
	final int maxIterations = 100; /* maximale Zahl von Newton-Schritten */

	double delta_R,R;
	double phi,phi_;
	int n = r.length;
	boolean convergence=false;
	int iteration=0;
	double x,h1,h2,h3;

	/* Startwert für R ist Mittelwert aller Kreisradien */
	R = 0;
	for (int k=0;k<n;k++) R = R + r[k];
	R = R/n;

	while (!convergence && (iteration<maxIterations)) {
	    /* Berechnung von phi(R) und phi'(R) */
	    phi = -2*Math.PI;
	    phi_ = 0;
	    for (int k=0;k<=n-2;k++) {
		h1 = R+r[k];
		h2 = R+r[k+1];
		h3 = r[k]+r[k+1];
		x = -(h3*h3-h1*h1-h2*h2)/(2*h1*h2);
		//System.out.println("x="+x+" acos(x)="+Math.acos(x));
		
		phi = phi+Math.acos(x);		
		phi_=phi_-1/Math.sqrt(1-x*x)*
		    ((4*R+2*h3)*h1*h2+(h3*h3-h1*h1-h2*h2)*(2*R+h3))/
		    (2*h1*h1*h2*h2);
	    }
	    h1 = R+r[n-1];
	    h2 = R+r[0];
	    h3 = r[n-1]+r[0];
	    x = -(h3*h3-h1*h1-h2*h2)/(2*h1*h2);
	    //System.out.println("x="+x+" acos(x)="+Math.acos(x));

	    phi = phi+Math.acos(x);
	    
	    phi_=phi_-1/Math.sqrt(1-x*x)*
		((4*R+2*h3)*h1*h2+(h3*h3-h1*h1-h2*h2)*(2*R+h3))/
		(2*h1*h1*h2*h2);
	    
	    /* Newton-Näherung */
	    delta_R = -phi/phi_;

	    //System.out.println("iter="+iteration+":phi="+phi+" phi_="+phi_+" R="+R);
	    
	    if (R+delta_R > 0) {
		R = R+delta_R;
	    } else {
		double lambda = 0.5;
		while (R+lambda*delta_R<0) lambda=lambda*0.5;
		R = R+lambda*delta_R;
	    }

	    if (Math.abs(delta_R)<=eps) {
		convergence = true;
	    } else {
		iteration++;
	    }
	}
	//System.out.println("convergence="+convergence);
	
	return R;
    }

    private double gamma(double R,double r1,double r2) {
	double h1,h2,h3,x;

	h1 = R+r1;
	h2 = R+r2;
	h3 = r1+r2;
	x = -(h3*h3-h1*h1-h2*h2)/(2*h1*h2);
	return Math.acos(x);
    }

    private CPoint findPointNextToRectangle(CPoint p,CRectangle r) {
	CPoint res = new CPoint();
	if (p.x<=r.x) {
	    res.x = r.x;
	} else if (r.x+r.width<p.x) {
	    res.x = r.x+r.width;
	} else {
	    res.x = p.x;
	}
	if (p.y<=r.y) {
	    res.y = r.y;
	} else if (r.y+r.height<p.y) {
	    res.y = r.y+r.height;
	} else {
	    res.y = p.y;
	}
	return res;
    }

    /**
     * Zur Berechnung des Schnittpunktes einer Linie mit einem Rechteck.
     * Der Punkt p1 muß innerhalb des Rechtecks liegen, der Punkt p2
     * außerhalb.
     */

    private CPoint cutLineRectangle(CPoint p1,CPoint p2,CRectangle r) {
	CPoint cut1 = cutLineLine(p1,p2,new CPoint(r.x,r.y),
				  new CPoint(r.x+r.width,r.y));
	CPoint cut2 = cutLineLine(p1,p2,new CPoint(r.x+r.width,r.y),
				  new CPoint(r.x+r.width,r.y+r.height));
	CPoint cut3 = cutLineLine(p1,p2,new CPoint(r.x+r.width,r.y+r.height),
				  new CPoint(r.x,r.y+r.height));
	CPoint cut4 = cutLineLine(p1,p2,new CPoint(r.x,r.y+r.height),
				  new CPoint(r.x,r.y));
	CPoint res=null;

	int minx,miny,maxx,maxy;
	minx = Math.min(p1.x,p2.x);
	miny = Math.min(p1.y,p2.y);
	maxx = Math.max(p1.x,p2.x);
	maxy = Math.max(p1.y,p2.y);

	CRectangle rp1p2 = new CRectangle(minx,miny,maxx-minx+1,maxy-miny+1);

	if ((cut1!=null) &&
	    rp1p2.contains(cut1)) res = cut1;
	if ((cut2!=null) &&
	    rp1p2.contains(cut2)) res = cut2;
	if ((cut3!=null) &&
	    rp1p2.contains(cut3)) res = cut3;
	if ((cut4!=null) &&
	    rp1p2.contains(cut4)) res = cut4;	
	
	if (res==null) {
	    System.out.println("Warnung! Kein Schnittpunkt:"+p1+","+p2+","+r);
	    System.out.println(cut1+","+cut2+","+cut3+","+cut4);
	    if (cut1!=null)
		System.out.println("cut1:"+r.contains(cut1)+","+rp1p2.contains(cut1));
	    if (cut2!=null)
		System.out.println("cut2:"+r.contains(cut2)+","+rp1p2.contains(cut2));
	    if (cut3!=null)
		System.out.println("cut3:"+r.contains(cut3)+","+rp1p2.contains(cut3));
	    if (cut4!=null)
		System.out.println("cut4:"+r.contains(cut4)+","+rp1p2.contains(cut4));
	    
	}
	    
	return res;
    }

    /**
     * Berechnung des Schnittpunktes zweier Geraden
     */

    private CPoint cutLineLine(CPoint p1,CPoint p2,CPoint p3,CPoint p4) {
	double a=p2.x-p1.x;
	double b=p3.x-p4.x;
	double c=p2.y-p1.y;
	double d=p3.y-p4.y;
	double e=p3.x-p1.x;
	double f=p3.y-p1.y;
	double det=a*d-b*c;

	if (det==0) {
	    return null;
	} else {
	    double t=1/det*(d*e-b*f);
	    return new CPoint((int) (p1.x+t*(p2.x-p1.x)),
			      (int) (p1.y+t*(p2.y-p1.y)));
	}
    }

    /**
     *
     */

    private abstract class LayoutElement {
	double radius;
	double x,y;
	double angle_to_transitionCircle;
	double transitionSectorStart;
	double transitionSectorEnd;
	double loopSectorStart;
	double loopSectorEnd;
	int transitionPointCount;
	int loopPointCount;
	CPoint[] loopPoint;
	CPoint[] transitionPoint;
	int nextLoopPointIndex;
	int nextTransitionPointIndexFromLeft;
	int nextTransitionPointIndexFromRight;

	LayoutElement() {
	    angle_to_transitionCircle = 0;
	    transitionSectorStart = 0;
	    transitionSectorEnd = 0;
	    loopSectorStart = 0;
	    loopSectorEnd = 0;
	    transitionPointCount = 0;
	    loopPointCount = 0;
	}
    }
    
    private class LayoutElementState extends LayoutElement {
	State state;
	LayoutElementState(State s,double r) {
	    super();
	    state = s;
	    radius = r;
	    x = 0;
	    y = 0;	    
	}
    }

    private class LayoutElementConnector extends LayoutElement {
	Connector connector;
	LayoutElementConnector(Connector c) {
	    super();
	    connector = c;
	    radius = CONNECTOR_RADIUS;
	    x = 0;
	    y = 0;
	}
    }

    private int findLayoutElementIndexToAnchor(TrAnchor anchor,
					       LayoutElement[] layoutElement) {
	int res = -1;
	if (anchor instanceof UNDEFINED) {
	    res = -1;
	} else if (anchor instanceof Statename) {
	    for (int i=0;i<layoutElement.length;i++) {
		if (layoutElement[i] instanceof LayoutElementState) {
		    if (((LayoutElementState) layoutElement[i]).state.
			name.name.equals(((Statename) anchor).name)) {
			res = i;
		    }
		}
	    }
	} else {
	    for (int i=0;i<layoutElement.length;i++) {
		if (layoutElement[i] instanceof LayoutElementConnector) {
		    if (((LayoutElementConnector) layoutElement[i]).connector.
			name.name.equals(((Conname) anchor).name)) {
			res = i;
		    }
		}
	    }
	}

	return res;
    }

} // CircleLayoutAlgorithm
