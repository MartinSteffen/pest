/** 
	package editor
*/


import absyn.*;
import java.awt.*;
import java.awt.event.*;


public class Editor extends Frame implements ActionListener {

    public Statechart statechart;
    private String label;
    public Point startPoint = null;
    public Point endPoint = null;
    public Rectangle newRect = null;
    public boolean actionOk = false;
    public StateList stateList = null;
    private String status = "";

/**
	public Editor(Statechart st, String l)

    Erzeugt ein Editorobjekt und öffnet ein Editorfenster

	@param st Eine Statechart-Instanz, die angezeigt werden soll und dann bearbeitet werden kann. 
	@param l Ein Bezeichner fuer den Statechart
*/

    public Editor(Statechart st, String l, int x, int y, int width, int height) {
        statechart = st;
        if (statechart.state == null)  {
            statechart.state = new Or_State(null, null, null, null, null);
            statechart.state.name = new Statename("root");
            statechart.state.rect = new Rectangle(0,0, 400, 300);
        }
        stateList = EditorUtils.getSubStateList(statechart.state);
        label = l;
        mainproc();
    }


    private void mainproc() {

        setSize(400,300);
        show();
        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu datei = new Menu("File");
        mb.add(datei);
        Menu editieren = new Menu("Edit");
        mb.add(editieren);
     
        MenuItem mi = new MenuItem("Exit");
        mi.addActionListener(this);
        mi.setActionCommand("exit");
        datei.add(mi);

        mi = new MenuItem("Add State");
        mi.addActionListener(this);
        mi.setActionCommand("addState");
        editieren.add(mi);

        mi = new MenuItem("Pointed Line");
        mi.addActionListener(this);
        mi.setActionCommand("pointedLine");
        editieren.add(mi);

        final Editor editor = this;

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (status.equals("addState"))
                    EditorUtils.createStateMousePressed(e, editor);
            }

            public void mouseReleased(MouseEvent e) {
                if (status.equals("addState"))
                    EditorUtils.createStateMouseReleased(e, editor);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

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
                System.exit(0);
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

    public boolean listenEditor() {
        return false;
    }

    public static void Dispose() {
    }

    public static void work(boolean b) {
    }

    public static boolean work() {
	return b;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("addState")) status = "addState";
        if (command.equals("exit")) System.exit(0);
        if (command.equals("pointedLine")) status = "pointedLine";
    }

    public void paint(Graphics g) {
        EditorUtils.showStates(stateList, g);
    }
}




