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
    public GUIInterface gui = null;

    private MenuItem copyOneCon,copyOneTr,insertOne,moveOne;
    private Menu copy = new Menu("Kopieren");

    final Editor editor = this;

/**
	public Editor(Statechart st, String l)

    Erzeugt ein Editorobjekt und öffnet ein Editorfenster

	@param st Eine Statechart-Instanz, die angezeigt werden soll und dann bearbeitet werden kann.
	@param l Ein Bezeichner fuer den Statechart
*/

    public Editor(Statechart st, String l, int x, int y, int width, int height,GUIInterface gui_interface) {
        statechart = st;
        if (statechart.state == null)  {
            statechart.state = new Or_State(null, null, null, null, null);
            statechart.state.name = new Statename("root");
            statechart.state.rect = new CRectangle(0,0, 4000, 3000);
        }
        stateList = EditorUtils.getSubStateList(statechart.state);
        setTitle(l);
        setBounds(x,y,width,height);
        gui = gui_interface;
        mainproc();
    }


    private void mainproc() {

        show();
        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu datei = new Menu("Datei");
        mb.add(datei);

        Menu bearbeiten = new Menu("Bearbeiten");
        mb.add(bearbeiten);

        Menu Extras = new Menu("Extras");
        mb.add(Extras);

        Menu Zoom = new Menu("Zoom");
        mb.add(Zoom);

	    MenuItem mi = new MenuItem("PrettyPrint");
        mi.addActionListener(this);
        mi.setActionCommand("PrettyPrint");
        datei.add(mi);

        mi = new MenuItem("Beenden");
        mi.addActionListener(this);
        mi.setActionCommand("Beenden");
        datei.add(mi);

        mi = new MenuItem("Zustand hinzufuegen");
        mi.addActionListener(this);
        mi.setActionCommand("Zustand hinzufuegen");
        bearbeiten.add(mi);

        mi = new MenuItem("Andstate erzeugen");
        mi.addActionListener(this);
        mi.setActionCommand("Andstate erzeugen");
        bearbeiten.add(mi);

        mi = new MenuItem("Transition hinzufuegen");
        mi.addActionListener(this);
        mi.setActionCommand("Transition hinzufuegen");
        bearbeiten.add(mi);

        mi = new MenuItem("Connector hinzufuegen");
        mi.addActionListener(this);
        mi.setActionCommand("Connector hinzufuegen");
        bearbeiten.add(mi);

        bearbeiten.addSeparator();

        mi = new MenuItem("Zustand benennen");
        mi.addActionListener(this);
        mi.setActionCommand("Zustand benennen");
        bearbeiten.add(mi);

        mi = new MenuItem("And_Zustand benennen");
        mi.addActionListener(this);
        mi.setActionCommand("And_Zustand benennen");
        bearbeiten.add(mi);

        mi = new MenuItem("Transition benennen");
        mi.addActionListener(this);
        mi.setActionCommand("Transition benennen");
        bearbeiten.add(mi);

        mi = new MenuItem("Connector benennen");
        mi.addActionListener(this);
        mi.setActionCommand("Connector benennen");
        bearbeiten.add(mi);

    	mi = new MenuItem("10%");
        mi.addActionListener(this);
        mi.setActionCommand("10%");
        Zoom.add(mi);

    	mi = new MenuItem("Markieren");
        mi.addActionListener(this);
        mi.setActionCommand("Markieren");
        Extras.add(mi);

        Extras.add(copy);
        copy.setEnabled(false);

    	copyOneCon = new MenuItem("Connector");
        copyOneCon.addActionListener(this);
        copyOneCon.setActionCommand("Connector");
        copyOneCon.setEnabled(false);
        copy.add(copyOneCon);

    	copyOneTr = new MenuItem("Transition");
        copyOneTr.addActionListener(this);
        copyOneTr.setActionCommand("Transition");
        copyOneTr.setEnabled(false);
        copy.add(copyOneTr);

    	insertOne = new MenuItem("Einfuegen");
        insertOne.addActionListener(this);
        insertOne.setActionCommand("Einfuegen");
        insertOne.setEnabled(false);
        Extras.add(insertOne);

    	moveOne = new MenuItem("Verschieben");
        moveOne.addActionListener(this);
        moveOne.setActionCommand("Verschieben");
        moveOne.setEnabled(false);
        Extras.add(moveOne);

    	mi = new MenuItem("25%");
        mi.addActionListener(this);
        mi.setActionCommand("25%");
        Zoom.add(mi);

    	mi = new MenuItem("50%");
        mi.addActionListener(this);
        mi.setActionCommand("50%");
        Zoom.add(mi);

    	mi = new MenuItem("100%");
        mi.addActionListener(this);
        mi.setActionCommand("100%");
        Zoom.add(mi);

    	mi = new MenuItem("200%");
        mi.addActionListener(this);
        mi.setActionCommand("200%");
        Zoom.add(mi);

    	mi = new MenuItem("300%");
        mi.addActionListener(this);
        mi.setActionCommand("300%");
        Zoom.add(mi);

    	mi = new MenuItem("400%");
        mi.addActionListener(this);
        mi.setActionCommand("400%");
        Zoom.add(mi);

        add("East",vert = new Scrollbar(Scrollbar.VERTICAL));
        add("South",horiz = new Scrollbar(Scrollbar.HORIZONTAL));
        horiz.setBlockIncrement(400);
        vert.setBlockIncrement(400);

        horiz.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollX = e.getValue();
                horiz.setValues(scrollX,40,0,1000);
                horiz.setUnitIncrement(20);
                repaint();
            }
        });
        vert.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollY = e.getValue();
                vert.setValues(scrollY,40,0,1000);
                vert.setUnitIncrement(20);
                repaint();
            }
        });


        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMousePressed(e, editor);
                Methoden_0.updateAll(editor);
            }

            public void mouseReleased(MouseEvent e) {
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMouseReleased(e, editor);
                if (status.equals("Andstate erzeugen"))
                    EditorUtils.andStateMouseReleased(e, editor);
                Methoden_0.updateAll(editor);
            }
            public void mouseClicked(MouseEvent e)
            {
                if (status.equals("Transition hinzufuegen"))
                {
                    Methoden_0.transitionMouseClicked(e,e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Zustand benennen"))
                {
                    Methoden_1.addStatenameMouseClicked(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("And_Zustand benennen"))
                {
                    Methoden_1.addAndNameMouseClicked(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Transition benennen"))
                {
                    Methoden_1.addTransNameMouseClicked(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Connector hinzufuegen"))
                {
                    Methoden_0.addConnectorMouseClicked(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Connector benennen"))
                {
                    Methoden_1.addConNameMouseClicked(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Markieren"))
                {
                    Methoden_1.selectOneConnector(e.getX()+scrollX,e.getY()+scrollY,editor);
                    if (Methoden_1.selectOneConnector != null)
                    {initMarkCon();}
                    Methoden_1.selectOneTr(e.getX()+scrollX,e.getY()+scrollY,editor);
                    if (Methoden_1.selectOneTr != null)
                    {initMarkTr();}
                }
                if (status.equals("Einfuegen"))
                {
                    Methoden_1.insertOneConnector(e.getX()+scrollX,e.getY()+scrollY,editor);
                    Methoden_1.insertOneTr(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                Methoden_0.updateAll(editor);
	    }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (status.equals("Andstate erzeugen"))
                    EditorUtils.andStateMouseMoved(e, editor);
                if (status.equals("Transition hinzufuegen"))
                {
                    Methoden_0.transitionMouseMoved(statechart.state,e.getX()+scrollX,e.getY()+scrollY,editor);
                    Methoden_0.markConMouseMoved(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
                if (status.equals("Connector benennen") | status.equals("Markieren"))
                {
                    Methoden_0.markConMouseMoved(e.getX()+scrollX,e.getY()+scrollY,editor);
                }
            }
            public void mouseDragged(MouseEvent e)
            {
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMouseDragged(e, editor);
                if (status.equals("Markieren"))
                {
                    EditorUtils.createStateMouseDragged(e,editor);
                    Methoden_1.selectMouseDragged(editor);
                }
            }
        });

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
//bearbeiten
        if (command.equals("Zustand hinzufuegen")) status = "Zustand hinzufuegen";
        if (command.equals("Beenden")) this.dispose();
        if (command.equals("Andstate erzeugen")) status = "Andstate erzeugen";
        if (command.equals("Transition hinzufuegen")) status = "Transition hinzufuegen";
//benennen
        if (command.equals("Zustand benennen")) status = "Zustand benennen";
        if (command.equals("And_Zustand benennen")) status = "And_Zustand benennen";
        if (command.equals("Transition benennen")) status = "Transition benennen";
        if (command.equals("Connector hinzufuegen")) status = "Connector hinzufuegen";
        if (command.equals("Connector benennen")) status = "Connector benennen";
//extras
        if (command.equals("Markieren")) {status = "Markieren";}
        if (command.equals("Connector")) {status = "Connector";initCopyCon();}
        if (command.equals("Transition")) {status = "Transition";initCopyTr();}
        if (command.equals("Einfuegen")) {status = "Einfuegen";}
        if (command.equals("Verschieben")) {status = "Verschieben";}

//Zoomen
        if (command.equals("10%")) {Methoden_1.setFactor(10);repaint();}
        if (command.equals("25%")) {Methoden_1.setFactor(25);repaint();}
        if (command.equals("50%")) {Methoden_1.setFactor(50);repaint();}
        if (command.equals("100%")) {Methoden_1.setFactor(100);repaint();}
        if (command.equals("200%")) {Methoden_1.setFactor(200);repaint();}
        if (command.equals("300%")) {Methoden_1.setFactor(300);repaint();}
        if (command.equals("400%")) {Methoden_1.setFactor(400);repaint();}
        if (command.equals("PrettyPrint")) {
            util.PrettyPrint p = new util.PrettyPrint();
            p.start(statechart);
        }
    }

    private void initMarkCon()
    {
        copy.setEnabled(true);
        copyOneCon.setEnabled(true);
    }
    private void initMarkTr()
    {
        copy.setEnabled(true);
        copyOneTr.setEnabled(true);
    }
    private void initCopyCon()
    {
        Methoden_1.copyOneConnector(this);insertOne.setEnabled(true);
    }
    private void initCopyTr()
    {
        Methoden_1.copyOneTr(this);insertOne.setEnabled(true);
    }


    public void paint(Graphics g) {
        try {
            EditorUtils.showStates(this);
            Methoden_0.updateAll(this);
        }
        catch (Exception e) {}
    }
}
