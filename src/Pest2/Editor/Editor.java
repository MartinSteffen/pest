/** 
	package editor
*/

package editor;
import absyn.*;
import gui.*;
import java.awt.*;
import java.awt.event.*;


public class Editor extends Frame implements ActionListener {

    public Statechart statechart;
    private String label;
    public Point startPoint = null;
    public Point endPoint = null;
    public CRectangle newRect = null;
    public boolean actionOk = false;
    public StateList stateList = null;
    private String status = "";
    private Scrollbar vert,horiz;
    public int scrollX=0,scrollY=0;

    final Editor editor = this;

/**
	public Editor(Statechart st, String l)

    Erzeugt ein Editorobjekt und öffnet ein Editorfenster

	@param st Eine Statechart-Instanz, die angezeigt werden soll und dann bearbeitet werden kann. 
	@param l Ein Bezeichner fuer den Statechart
*/

    public Editor(Statechart st, String l, int x, int y, int width, int height,GUIInterface gui) {
        statechart = st;
        if (statechart.state == null)  {
            statechart.state = new Or_State(null, null, null, null, null);
            statechart.state.name = new Statename("root");
            statechart.state.rect = new CRectangle(0,0, 4000, 3000);
        }
        stateList = EditorUtils.getSubStateList(statechart.state);
        label = l;
        setBounds(x,y,400,300);
        mainproc();
    }


    private void mainproc() {

        show();
        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu datei = new Menu("File");
        mb.add(datei);
        Menu editieren = new Menu("Edit");
        mb.add(editieren);
     
	MenuItem mi = new MenuItem("PrettyPrint");
        mi.addActionListener(this);
        mi.setActionCommand("prettyPrint");
        datei.add(mi);

        mi = new MenuItem("Exit");
        mi.addActionListener(this);
        mi.setActionCommand("exit");
        datei.add(mi);

        mi = new MenuItem("Add State");
        mi.addActionListener(this);
        mi.setActionCommand("addState");
        editieren.add(mi);

        mi = new MenuItem("Add Transition");
        mi.addActionListener(this);
        mi.setActionCommand("add Transition");
        editieren.add(mi);

        mi = new MenuItem("Pointed Line");
        mi.addActionListener(this);
        mi.setActionCommand("pointedLine");
        editieren.add(mi);

        add("East",vert = new Scrollbar(Scrollbar.VERTICAL));
        add("South",horiz = new Scrollbar(Scrollbar.HORIZONTAL));
        horiz.setBlockIncrement(400);
        vert.setBlockIncrement(400);

        horiz.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollX = e.getValue();
                horiz.setValues(scrollX,30,0,1000);
                repaint();
            }
        });
        vert.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollY = e.getValue();
                vert.setValues(scrollY,30,0,1000);
                repaint();
            }
        });


        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (status.equals("addState"))
                    EditorUtils.createStateMousePressed(e, editor);
            }

            public void mouseReleased(MouseEvent e) {
                if (status.equals("addState"))
                    EditorUtils.createStateMouseReleased(e, editor);
                if (status.equals("pointedLine"))
                    EditorUtils.andStateMouseReleased(e, editor);
            }
            public void mouseClicked(MouseEvent e)
            {
                if (status.equals("add Transition"))
                {
                    Methoden.transitionMouseClicked(e,e.getX()+scrollX,e.getY()+scrollY,editor);
                }
	    }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (status.equals("pointedLine"))
                    EditorUtils.andStateMouseMoved(e, editor);
                if (status.equals("add Transition"))
                {
                    Methoden.transitionMouseMoved(statechart.state,e.getX()+scrollX,e.getY()+scrollY,editor);
                }
            }
            public void mouseDragged(MouseEvent e) {
                if (status.equals("addState"))
                    EditorUtils.createStateMouseDragged(e, editor);
            }
        });
  

        // EditorUtils.printStatechart(statechart.state, new Point(0,0), this);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Window window = e.getWindow();
                window.dispose();
            }
        });
        addComponentListener(new ComponentAdapter ()
        {
            public void componentResized(ComponentEvent e)
            {
                Dimension d = getSize();
                horiz.setBlockIncrement(d.width-100);
                vert.setBlockIncrement(d.height-100);
            }
        });
    }

    /**
	Liefert den aktuellen Statechart zurück.
	@return den aktuellen Statechart
     */

    public Statechart getStatechart() {
        Statechart st = null;
        return st;
    }

    /**
	Ueberpruefung, ob der dem Editor uebergebene Statechart im Editorfenster geaendert wurde.
	@return true, falls der Statechart geaendert wurde.
	        false, falls der Statechart nicht geaendert wurde.
    */


    /**
    */   
    public boolean listenEditor() {
        return false;
    }

    /**
       schliesst das Editorfenster
    */   
    public static void Dispose() {
    }

    /**
       @param boolean b -
    */
    public static boolean work(boolean b) {
	return b;
    }


    /**
     */
    public static void work() {
       
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("addState")) status = "addState";
        if (command.equals("exit")) System.exit(0);
        if (command.equals("pointedLine")) status = "pointedLine";
        if (command.equals("add Transition")) status = "add Transition";
        if (command.equals("prettyPrint")) {
            util.PrettyPrint p = new util.PrettyPrint();
            p.start(statechart);
        }
	    
    }

    public void paint(Graphics g) {
        EditorUtils.showStates(this);
        Methoden.drawAllTransition(editor);
    }
}




