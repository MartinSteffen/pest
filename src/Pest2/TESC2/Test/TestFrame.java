
/**
 * TestFrame.java
 *
 *
 * Created: Tue Jan  5 20:30:22 1999
 *
 * @author Achim Abeling
 * @version
 */

import java.awt.*;
import java.awt.event.*;
import tesc2.*;
import util.*;
import absyn.*;

/**
 * Dieses Programm dient zum Testen des GraphOptimizer.
 * Es wird ein Zufalls-Statechart erzeugt und dieses in einem Frame 
 * angezeigt. 
 */

public class TestFrame extends Frame {
    
    static Statechart drawableStatechart = null;

    public static void main(String[] args) {
	new TestFrame();
    }

    public TestFrame() {
	super("GraphOptimizerTestFrame");

	/* Erzeugen eines Zufalls-Statechart */
	System.out.println("Creating Random-Statechart");      
	State rootstate = 
	    generateState("0",3);
	Statechart statechart = 
	    new Statechart(new SEventList(new SEvent("fuck"),null),
			   null,
			   new PathList(new Path("dumichauch",null),null),
			   rootstate);

	/* Erzeugen des Frame */
	System.out.println("Creating Frame");	

	MenuBar mb = new MenuBar();
	Menu exitMenu = new Menu("Exit");
	MenuItem exitMenuItem = new MenuItem("Exit");
	exitMenu.add(exitMenuItem);
	exitMenuItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {		
		System.exit(0);
	    }});
	mb.add(exitMenu);
	this.setMenuBar(mb);

	this.setSize(500,500);
	this.show();

	MyScrollPane scrollpane = new MyScrollPane();
	this.add(scrollpane,"Center");

	FontMetrics fontMetrics = this.getFontMetrics(this.getFont());

	/* Layout durchführen */
	System.out.println("Doing layout");
	
	tesc2.GraphOptimizer graphOptimizer = 
	    new tesc2.GraphOptimizer(statechart,fontMetrics);
	try {
	    drawableStatechart = graphOptimizer.start();
	} catch (tesc2.AlgorithmException e) {
	    System.out.println("Error in layout");
	}


	
	scrollpane.repaint();
	this.validate();
    }
    
    public void update(Graphics g) {
	paint(g);
    }

    static State generateState(String name,int depth) {

	System.out.println("Generating state "+name+" at depth "+depth);
	
	/* Falls maximale Tiefe erreicht wurde, erzeuge einen Basic_State */
	if (depth == 0) {
	    return new Basic_State(new Statename(name));
	} else {
	    /* ansonsten erzeuge andere States */
	    RandomIntGenerator r1 = new RandomIntGenerator(1,3);
	    int stateType = r1.nextInt();
	    if (stateType==1) { /* erzeuge einen Basic_State */
		return new Basic_State(new Statename(name));
	    } else if (stateType==2) { /* erzeuge einen And_State */
		/* berechne Anzahl der Substates */
		RandomIntGenerator r2 = new RandomIntGenerator(2,5);
		int countSubstates = r2.nextInt();
		StateList stateList = null;
		/* berechne Substates */
		for (int i=0;i<countSubstates;i++) {
		    String substateName = name+"."+Integer.toString(i);
		    stateList = 
			new StateList(generateState(substateName,depth-1),
				      stateList);
		}
		
		return new And_State(new Statename(name),stateList);
	    } else {/* erzeuge einen Or_State */
		/* berechne Anzahl der Substates*/
		RandomIntGenerator r2 = new RandomIntGenerator(2,10);
	        int countSubstates = r2.nextInt();
		Statename[] statenameArray = new Statename[countSubstates];
		StateList stateList = null;
		/* berechne Namen der Substates und diese selber */
		for (int i=0;i<countSubstates;i++) {
		    String substateName = name+"."+Integer.toString(i);
		    statenameArray[i] = 
			new Statename(substateName);
		    stateList = 
			new StateList(generateState(substateName,depth-1),
				      stateList);
		}
		
		/* berechne Connectors */
		RandomIntGenerator r3 = new RandomIntGenerator(0,3);

		int countConnectors = r3.nextInt();
		/* int countConnectors = 0; */
		Conname[] connameArray = new Conname[countConnectors];
		ConnectorList connectorList = null;
		for (int i=0;i<countConnectors;i++) {
		    String connectorName = "c"+Integer.toString(i);
		    connameArray[i] = new Conname(connectorName);
		    connectorList = 
			new ConnectorList(new Connector(connameArray[i]),
					  connectorList);
		}

		/* berechne Transitions */
		RandomIntGenerator r4 = 
		    new RandomIntGenerator(countSubstates,
					   countSubstates+countConnectors+5);
		int countTransitions = r4.nextInt();
		RandomIntGenerator r5 =
		    new RandomIntGenerator(0,countSubstates+countConnectors);		
		TrList transitionList = null;
		TrAnchor sourceAnchor;
		TrAnchor targetAnchor;
		for (int i=0;i<countTransitions;i++) {
		    int sourceIndex = r5.nextInt();
		    int targetIndex = r5.nextInt();
		    if (sourceIndex==0) {
			sourceAnchor = new UNDEFINED();
		    } else if (sourceIndex <= countSubstates) {
			sourceAnchor = statenameArray[sourceIndex-1];
		    } else {
			sourceAnchor = connameArray[sourceIndex-countSubstates-1];
		    }
		    if (targetIndex==0) {
			targetAnchor = new UNDEFINED();
		    } else if (targetIndex <= countSubstates) {
			targetAnchor = statenameArray[targetIndex-1];
		    } else {
			targetAnchor = connameArray[targetIndex-countSubstates-1];
		    }
		    transitionList =
			new TrList(new Tr(sourceAnchor,targetAnchor,
					  new TLabel(new GuardEmpty(new Dummy()),
						     new ActionEmpty(new Dummy()))),
				   transitionList);
		}
							       
		return new Or_State(new Statename(name),
				    stateList,
				    transitionList,
				    new StatenameList(new Statename("fuck"),null),
				    connectorList);
	    }
	    
	}
    }

    static class MyScrollPane extends ScrollPane {
	MyScrollPane() {
	    super(ScrollPane.SCROLLBARS_ALWAYS);
	    MyComponent myc = new MyComponent();
	    this.add(myc);
	}
    }

    static class MyComponent extends Component {
	
	public Dimension getPreferredSize() {return new Dimension(2000,2000);};

	public void paint(Graphics g) {
	    if (drawableStatechart != null) {
		drawState(g,drawableStatechart.state,10,10);
	    }
	}

	void drawState(Graphics g,State state,int x,int y) {
	    
	    String stateType = state.getClass().getName();
	    if (stateType.equals("absyn.Basic_State")) {

		g.drawRect(x+state.rect.x,y+state.rect.y,
			   state.rect.width,state.rect.height);
		g.drawString(state.name.name,x+state.rect.x+2,y+state.rect.y+10);
	    } else if (stateType.equals("absyn.And_State")) {

		g.drawRect(x+state.rect.x,y+state.rect.y,
			   state.rect.width,state.rect.height);

		StateList substates = ((And_State) state).substates;
		while (substates != null) {
		    drawState(g,substates.head,x+state.rect.x,
			      y+state.rect.y);
		    substates = substates.tail;
		}
	    } else if (stateType.equals("absyn.Or_State")) {

		/* Rahmen zeichnen */
		g.drawRect(x+state.rect.x,y+state.rect.y,
			   state.rect.width,state.rect.height);
		g.drawString(state.name.name,x+state.rect.x+2,y+state.rect.y+10);


		/* substates zeichnen */
		StateList substates = ((Or_State) state).substates;
		while (substates != null) {
		    drawState(g,substates.head,x+state.rect.x,
			      y+state.rect.y);
		    substates = substates.tail;
		}

		/* Connectors zeichnen */
		ConnectorList connectors = ((Or_State) state).connectors;
		while (connectors != null) {
		    g.drawOval(x+state.rect.x+connectors.head.position.x-2,
			       y+state.rect.y+connectors.head.position.y-2,
			       5,5);
		    connectors = connectors.tail;
		}

		/* Transitions zeichnen */
		TrList trs = ((Or_State) state).trs;
		int xoff = x+state.rect.x;
		int yoff = y+state.rect.y;
		while (trs != null) {
		    Tr tr = trs.head;
		    CPoint[] trPoints = tr.points;
		    if (trPoints == null) {
			System.out.println("Warnung: trPoints == null");
		    }
		    
		    for (int i=1;i<trPoints.length;i++) {
			g.drawLine(xoff+trPoints[i-1].x,
				   yoff+trPoints[i-1].y,
				   xoff+trPoints[i].x,
				   yoff+trPoints[i].y);
		    }
		    trs = trs.tail;
		}
	    }
	}
    }

} // TestFrame



