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
    private Statechart old;
    private MenuItem undo = null;
    public Point startPoint = null;
    public Point endPoint = null;
    public CRectangle newRect = null;
    public boolean actionOk = false;
    public StateList stateList = null;
    private String status = "";
    private Scrollbar vert,horiz;
    public int scrollX=0,scrollY=0;
    public GUIInterface gui = null;
    private boolean changedStatechart = false; //fuer listenEditor()
    private String statechartName = "";

    private MenuItem copyOneCon,copyOneTr,insertOne,moveOne,removeOne;
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
        statechartName = l;
        setTitle(statechartName);
        setBounds(x,y,width,height);
        gui = gui_interface;
        mainproc();
    }


    private void mainproc() {

        EditorUtils.setPathList(statechart, this);
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

        MenuItem mi = new MenuItem("Neu");
        mi.addActionListener(this);
        mi.setActionCommand("Neu");
        datei.add(mi);
        datei.addSeparator();

        mi = new MenuItem("PrettyPrinter");
        mi.addActionListener(this);
        mi.setActionCommand("PrettyPrinter");
        datei.add(mi);
        datei.addSeparator();

        mi = new MenuItem("Beenden");
        mi.addActionListener(this);
        mi.setActionCommand("Beenden");
        datei.add(mi);

        undo = new MenuItem("Rueckgaengig");
        undo.addActionListener(this);
        undo.setActionCommand("Rueckgaengig");
        undo.setEnabled(false);
        bearbeiten.add(undo);
        bearbeiten.addSeparator();

        mi = new MenuItem("Zustand hinzufuegen");
        mi.addActionListener(this);
        mi.setActionCommand("Zustand hinzufuegen");
        bearbeiten.add(mi);

        mi = new MenuItem("And_Zustand erzeugen");
        mi.addActionListener(this);
        mi.setActionCommand("And_Zustand erzeugen");
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

        mi = new MenuItem("Zustand loeschen");
        mi.addActionListener(this);
        mi.setActionCommand("Zustand loeschen");
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

        removeOne = new MenuItem("Loeschen");
        removeOne.addActionListener(this);
        removeOne.setActionCommand("Loeschen");
        removeOne.setEnabled(false);
        Extras.add(removeOne);

    	final CheckboxMenuItem cb25,cb50,cb100,cb200,cb300,cb400;

    	cb25 = new CheckboxMenuItem("25%");
        Zoom.add(cb25);

    	cb50 = new CheckboxMenuItem("50%");
        Zoom.add(cb50);

    	cb100 = new CheckboxMenuItem("100%",true);
        Zoom.add(cb100);

    	cb200 = new CheckboxMenuItem("200%");
        Zoom.add(cb200);

    	cb300 = new CheckboxMenuItem("300%");
        Zoom.add(cb300);

    	cb400 = new CheckboxMenuItem("400%");
        Zoom.add(cb400);

        add("East",vert = new Scrollbar(Scrollbar.VERTICAL));
        add("South",horiz = new Scrollbar(Scrollbar.HORIZONTAL));
        horiz.setBlockIncrement(400);
        vert.setBlockIncrement(400);
        horiz.setUnitIncrement(20);
        vert.setUnitIncrement(20);
        horiz.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollX = e.getValue();
                horiz.setValues(scrollX,40,0,10000);
                repaint();
            }
        });
        vert.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollY = e.getValue();
                vert.setValues(scrollY,40,0,10000);
                repaint();
            }
        });

        cb25.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb25.getState()){
                    Methoden_1.setFactor(25);
                    setAllDeselected(cb50,cb100,cb200,cb300,cb400);
                    repaint();
                }
                else cb25.setState(true);
            }
        });

        cb50.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(cb50.getState())
                {
                    Methoden_1.setFactor(50);
                    setAllDeselected(cb25,cb100,cb200,cb300,cb400);
                    repaint();
                }
                else cb50.setState(true);
            }
        });

        cb100.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb100.getState()){
                    Methoden_1.setFactor(100);
                    setAllDeselected(cb25,cb50,cb200,cb300,cb400);
                    repaint();
                }
                else cb100.setState(true);
            }
        });

        cb200.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e)
            {
                if (cb200.getState()){
                    Methoden_1.setFactor(200);
                    setAllDeselected(cb25,cb50,cb100,cb300,cb400);
                    repaint();
                }
                else cb200.setState(true);
            }
        });


        cb300.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e)
            {
                if (cb300.getState()){
                    Methoden_1.setFactor(300);
                    setAllDeselected(cb25,cb50,cb100,cb200,cb400);
                    repaint();
                }
                else cb300.setState(true);
            }
        });

        cb400.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb400.getState()){
                    Methoden_1.setFactor(400);
                    setAllDeselected(cb25,cb50,cb100,cb200,cb300);
                    repaint();
                }
                else cb400.setState(true);
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
                if (status.equals("And_Zustand erzeugen"))
                    EditorUtils.andStateMouseReleased(e, editor);
                if (status.equals("Zustand loeschen"))
                    EditorUtils.deleteStateMouseReleased(e, editor);
                if (status.equals("Verschieben"))
                    Methoden_1.moveOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                initRemove();
                initMove();
                Methoden_0.updateAll(editor);
            }
            public void mouseClicked(MouseEvent e)
            {
                if (status.equals("Transition hinzufuegen"))
                {
                    Methoden_0.transitionMouseClicked(e,(int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Zustand benennen"))
                {
                    Methoden_1.addStatenameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor())+scrollY,editor);
                }
                if (status.equals("And_Zustand benennen"))
                {
                    Methoden_1.addAndNameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor())+scrollY,editor);
                }
                if (status.equals("Transition benennen"))
                {
                    Methoden_1.addTransNameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector hinzufuegen"))
                {
                    Methoden_0.addConnectorMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector benennen"))
                {
                    Methoden_1.addConNameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Markieren"))
                {
                    Methoden_1.selectOneConnector((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_1.selectOneTr((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    initCopyTr();
                    initCopyCon();
                    initMove();
                    initRemove();
                    return;
                }
                if (status.equals("Einfuegen"))
                {
                    Methoden_1.insertOneConnector((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_1.insertOneTr((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                Methoden_0.updateAll(editor);
	    }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (status.equals("And_Zustand erzeugen"))
                    EditorUtils.andStateMouseMoved(e, editor);
                if (status.equals("Zustand loeschen"))
                    EditorUtils.deleteStateMouseMoved(e, editor);
                if (status.equals("Transition hinzufuegen"))
                {
                    Methoden_0.transitionMouseMoved(statechart.state,(int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector benennen") | status.equals("Markieren"))
                {
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
            }
            public void mouseDragged(MouseEvent e)
            {
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMouseDragged(e, editor);
                if (status.equals("Markieren"))
                {
//                    EditorUtils.createStateMouseDragged(e,editor);
                    Methoden_1.selectMouseDragged(editor);
                }
                if (status.equals("Verschieben"))
                {
//                    Methoden_1.drawMarkMouseDragged((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
            }
        });

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
//                if ((gui.YesNoDialog(editor,statechartName,"Aktuelle Statechart wird geloescht")) == 2) return;
                Window window = e.getWindow();
                window.dispose();
            }
        });
        addComponentListener(new ComponentAdapter ()
        {
            public void componentResized(ComponentEvent e)
            {
                Dimension dimension = getSize();
                horiz.setBlockIncrement(dimension.width-100);
                vert.setBlockIncrement(dimension.height-100);
            }
        });
    }

    /**
	Liefert den aktuellen Statechart zurück.
	@return den aktuellen Statechart
     */

    public Statechart getStatechart() {
        return statechart;
    }

    public void setChangedStatechart(boolean b)
    {
        changedStatechart = b;
    }
    public boolean getChangedStatechart()
    {
        return changedStatechart;
    }
    /**
    ueberpruefung, ob der dem Editor uebergebene Statechart im Editorfenster geaendert wurde.
    @return true, falls der Statechart geaendert wurde.
            false, falls der Statechart nicht geaendert wurde.
    */


    /**
    */
    public boolean listenEditor() {
        boolean changed = getChangedStatechart();
        setChangedStatechart(false);
        return changed;
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

    private void setAllDeselected(CheckboxMenuItem c1,CheckboxMenuItem c2,CheckboxMenuItem c3,CheckboxMenuItem c4,CheckboxMenuItem c5)
    {
        c1.setState(false);
        c2.setState(false);
        c3.setState(false);
        c4.setState(false);
        c5.setState(false);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Rueckgaengig")) {
            try {
                Statechart help = (Statechart) statechart.clone();
                if (old.events != null) statechart.events = (SEventList) old.events.clone();
                if (old.bvars  != null) statechart.bvars  = (BvarList) old.bvars.clone(); 
                if (old.cnames != null) statechart.cnames = (PathList) old.cnames.clone(); 
                                        statechart.state  = (State) old.state.clone();
                old = help;
            }
            catch (Exception ex) {System.out.println(ex);}
            undo.setLabel("Rueckgaengig: Rueckgaengig");
            stateList = EditorUtils.getSubStateList(statechart.state);
            EditorUtils.setPathList(statechart, this);
            this.repaint();
        }
        else if (!command.equals("PrettyPrinter")) {
            try { old = (Statechart) statechart.clone(); }
            catch (Exception ex) {System.out.println(ex);}
            undo.setLabel("Rueckgaengig:"+command);
            undo.setEnabled(true);
        }
        if (command.equals("Neu")) {initStatechart();}
        if (command.equals("Beenden")) this.dispose();
        if (command.equals("PrettyPrinter")) {
            util.PrettyPrint p = new util.PrettyPrint();
            p.start(statechart);
        }

//bearbeiten
        if (command.equals("Zustand hinzufuegen")) status = "Zustand hinzufuegen";
        if (command.equals("And_Zustand erzeugen")) status = "And_Zustand erzeugen";
        if (command.equals("Transition hinzufuegen")) status = "Transition hinzufuegen";
        if (command.equals("Zustand loeschen")) status = "Zustand loeschen";
//benennen
        if (command.equals("Zustand benennen")) status = "Zustand benennen";
        if (command.equals("And_Zustand benennen")) status = "And_Zustand benennen";
        if (command.equals("Transition benennen")) status = "Transition benennen";
        if (command.equals("Connector hinzufuegen")) status = "Connector hinzufuegen";
        if (command.equals("Connector benennen")) status = "Connector benennen";
//extras
        if (command.equals("Markieren")) {status = "Markieren";}
        if (command.equals("Connector")) {status = "Connector";initInsertCon();}
        if (command.equals("Transition")) {status = "Transition";initInsertTr();}
        if (command.equals("Einfuegen")) {status = "Einfuegen";}
        if (command.equals("Verschieben")) {status = "Verschieben";}
        if (command.equals("Loeschen")) {status = "Loeschen";Methoden_1.removeOne(editor);initRemove();}

//Zoomen
        if (command.equals("10%")) {Methoden_1.setFactor(10);repaint();}
        if (command.equals("25%")) {Methoden_1.setFactor(25);repaint();}
        if (command.equals("50%")) {Methoden_1.setFactor(50);repaint();}
        if (command.equals("100%")) {Methoden_1.setFactor(100);repaint();}
        if (command.equals("200%")) {Methoden_1.setFactor(200);repaint();}
        if (command.equals("300%")) {Methoden_1.setFactor(300);repaint();}
        if (command.equals("400%")) {Methoden_1.setFactor(400);repaint();}
    }

    private void initStatechart()
    {
        if ((gui.YesNoDialog(editor,statechartName,"Aktuelle Statechart wird geloescht")) == 2) return;
        statechart = new Statechart("UNBENANNT");
        if (statechart.state == null)  {
            statechart.state = new Or_State(null, null, null, null, null);
            statechart.state.name = new Statename("root");
            statechart.state.rect = new CRectangle(0,0, 4000, 3000);
        }
        stateList = EditorUtils.getSubStateList(statechart.state);
        repaint();
    }

    private void initCopyCon()
    {
        if (Methoden_1.selectOneConnector != null){
            copy.setEnabled(true);
            copyOneCon.setEnabled(true);
        }
        else
            copyOneCon.setEnabled(false);
    }
    private void initCopyTr()
    {
        if (Methoden_1.selectOneTr != null){
            copy.setEnabled(true);
            copyOneTr.setEnabled(true);
        }
        else
            copyOneTr.setEnabled(false);
    }
    private void initInsertCon()
    {
        Methoden_1.copyOneConnector(this);insertOne.setEnabled(true);
    }
    private void initInsertTr()
    {
        Methoden_1.copyOneTr(this);insertOne.setEnabled(true);
    }
    private void initRemove()
    {
        if (Methoden_1.markLast != null)
            removeOne.setEnabled(true);
        else
            removeOne.setEnabled(false);
    }
    private void initMove()
    {
        if (Methoden_1.markLast != null)
            moveOne.setEnabled(true);
        else
            moveOne.setEnabled(false);
    }

    public void paint(Graphics g) {
        try {
            EditorUtils.showStates(this);
            Methoden_0.updateAll(this);
        }
        catch (Exception e) {}
//        new highlightObject(g);
//        highlightObject hlo = new highlightObject();
    }
}
