
/**
	package editor
*/

package editor;
import absyn.*;
import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/*
Aenderungen am 01.02.99 (von long)
*/

public class Editor extends Frame implements ActionListener {

    public Statechart statechart;
    private Statechart old;
    private MenuItem undo = null;
    public Point startPoint = null;
    public Point endPoint = null;
    public State activeState = null;
    public CRectangle newRect = null;
    public boolean actionOk = false;
    public StateList stateList = null;
    private String status = "";
    private Scrollbar vert,horiz;
    public int scrollX=-20,scrollY=-80;
    public GUIInterface gui = null;
    private boolean changedStatechart = false; //fuer listenEditor()
    private String statechartName = "";

    private MenuItem copyOneCon,copyOneTr,insertOne,moveOne,removeOne,moveTransName,simulieren;
    private Menu copy = new Menu("Kopieren");
    private Dimension dimension;
    public CheckboxMenuItem cbbezier = new CheckboxMenuItem("Transitionen mit Kurven");
    public int fontsize = 0;


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
        checkHighlight();
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

        Menu simulation = new Menu("Simulation");
        mb.add(simulation);

        simulieren = new MenuItem("Simulieren");
        simulieren.addActionListener(this);
        simulieren.setActionCommand("Simulieren");
        simulieren.setEnabled(false);
        simulation.add(simulieren);

        Menu option = new Menu("Option");
        mb.add(option);

        cbbezier.setState(false);
        option.add(cbbezier);

        Menu Zoom = new Menu("Zoom");
        mb.add(Zoom);

        MenuItem mi = new MenuItem("Neu");
        mi.addActionListener(this);
        mi.setActionCommand("Neu");
        datei.add(mi);
        datei.addSeparator();

        mi = new MenuItem("Schriftgroesse");
        mi.addActionListener(this);
        mi.setActionCommand("Schriftgroesse");
        option.add(mi);

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

        mi = new MenuItem("Als Default setzen");
        mi.addActionListener(this);
        mi.setActionCommand("Als Default setzen");
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

    	moveTransName = new MenuItem("Transitionsname verschieben");
        moveTransName.addActionListener(this);
        moveTransName.setActionCommand("move Transname");
        moveTransName.setEnabled(false);
        Extras.add(moveTransName);

        mi = new MenuItem("Zustand verschieben");
        mi.addActionListener(this);
        mi.setActionCommand("Zustand verschieben");
        Extras.addSeparator();
        Extras.add(mi);

    	final CheckboxMenuItem cb10,cb25,cb50,cb100,cb200,cb300,cb400,cbper,cbanpassen;

    	cb10 = new CheckboxMenuItem("10%");
        Zoom.add(cb10);

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

        Zoom.addSeparator();

    	cbper = new CheckboxMenuItem("persoenlich ...");
        Zoom.add(cbper);

    	cbanpassen = new CheckboxMenuItem("Anpassen");
        Zoom.add(cbanpassen);

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
                scrollX = e.getValue()-20;
                horiz.setValues(e.getValue(),40,0,4000);
                repaint();
            }
        });
        vert.addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                scrollY = e.getValue()-80;
                vert.setValues(e.getValue(),40,0,3000);
                repaint();
            }
        });

        cbbezier.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cbbezier.getState()){
                    repaint();
                }
                else repaint();
            }
        });

        cb10.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb10.getState()){
                    Methoden_1.setFactor(10);
                    setAllDeselected(cb25,cb50,cb100,cb200,cb300,cb400,cbper,cbanpassen);
                    repaint();
                }
                else cb10.setState(true);
            }
        });

        cb25.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb25.getState()){
                    Methoden_1.setFactor(25);
                    setAllDeselected(cb10,cb50,cb100,cb200,cb300,cb400,cbper,cbanpassen);
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
                    setAllDeselected(cb10,cb25,cb100,cb200,cb300,cb400,cbper,cbanpassen);
                    repaint();
                }
                else cb50.setState(true);
            }
        });

        cb100.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb100.getState()){
                    Methoden_1.setFactor(100);
                    setAllDeselected(cb10,cb25,cb50,cb200,cb300,cb400,cbper,cbanpassen);
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
                    setAllDeselected(cb10,cb25,cb50,cb100,cb300,cb400,cbper,cbanpassen);
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
                    setAllDeselected(cb10,cb25,cb50,cb100,cb200,cb400,cbper,cbanpassen);
                    repaint();
                }
                else cb300.setState(true);
            }
        });

        cb400.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cb400.getState()){
                    Methoden_1.setFactor(400);
                    setAllDeselected(cb10,cb25,cb50,cb100,cb200,cb300,cbper,cbanpassen);
                    repaint();
                }
                else cb400.setState(true);
            }
        });

        cbper.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cbper.getState() | !cbper.getState()){

                    String per = gui.EingabeDialog(editor,"Zoomen","Zoom_Faktor: 1 .. 1000","500");
                    if (per == null) {cbper.setState(false);return;}
                    Double z = new Double(per);
                    double wert = z.doubleValue();
                    if (wert < 1 | wert > 1000) return;
                    Methoden_1.setFactor(wert);

                    setAllDeselected(cb10,cb25,cb50,cb100,cb200,cb300,cb400,cbanpassen);
                    repaint();
                    cbper.setState(true);
                }
            }
        });

        cbanpassen.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (cbanpassen.getState() | !cbanpassen.getState()){

                    Methoden_1.setFactor(getFitToScreen());
                    setAllDeselected(cb10,cb25,cb50,cb100,cb200,cb300,cb400,cbper);
                    repaint();
                    cbanpassen.setState(true);
                }
            }
        });

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if ((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) >= 4000 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) >= 3000) return;
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMousePressed(e, editor);
                if (status.equals("Zustand verschieben"))
                    EditorUtils.dragStateMousePressed(e, editor);
                Methoden_0.updateAll(editor);
            }

            public void mouseReleased(MouseEvent e) {
                if ((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) >= 4000 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) >= 3000) return;
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMouseReleased(e, editor);
                if (status.equals("And_Zustand erzeugen"))
                    EditorUtils.andStateMouseReleased(e, editor);
                if (status.equals("Zustand loeschen"))
                    EditorUtils.deleteStateMouseReleased(e, editor);
                if (status.equals("Zustand verschieben"))
                    EditorUtils.dragStateMouseReleased(e, editor);
                if (status.equals("Verschieben"))
                    Methoden_1.moveOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                initCopy();
                initRemove();
                initMove();
                Methoden_0.updateAll(editor);
            }
            public void mouseClicked(MouseEvent e)
            {
                if ((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) >= 4000 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) >= 3000) return;
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
                if (status.equals("Transition benennen")){
                    Methoden_0.updateAll(editor);
                    Methoden_1.selectOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_1.addTransNameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector hinzufuegen")){
                    Methoden_0.updateAll(editor);
                    Methoden_0.addConnectorMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector benennen")){
                    Methoden_0.updateAll(editor);
                    Methoden_1.addConNameMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Als Default setzen")){
                    Methoden_0.addDefaultMouseClicked((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    repaint();
                }
                if (status.equals("Markieren")){
                    Methoden_0.updateAll(editor);
                    Methoden_1.selectOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    initCopy();
                    initMove();
                    initRemove();
                    return;
                }
                if (status.equals("Einfuegen")){
                    Methoden_1.insertOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("move Transname")){
                    Methoden_1.moveTransName((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                Methoden_0.updateAll(editor);
	    }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if ((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) >= 4000 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) >= 3000) return;
                if (status.equals("And_Zustand erzeugen"))
                    EditorUtils.andStateMouseMoved(e, editor);
                if (status.equals("Zustand loeschen"))
                    EditorUtils.deleteStateMouseMoved(e, editor);
                if (status.equals("Transition hinzufuegen"))
                {
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                    if (Methoden_1.getFactor() == 1) Methoden_0.transitionMouseMoved(statechart.state,(int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Transition benennen"))
                {
                    Methoden_0.updateAll(editor);
                    Methoden_1.selectOne((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                if (status.equals("Connector benennen") | status.equals("Markieren"))
                {
                    Methoden_0.markConMouseMoved((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
                }
                Methoden_1.showFullTransName((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()),(int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()),editor);
            }
            public void mouseDragged(MouseEvent e)
            {
                if ((int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) <= 0 |
                    (int)((double)(e.getX()+scrollX)/Methoden_1.getFactor()) >= 4000 |
                    (int)((double)(e.getY()+scrollY)/Methoden_1.getFactor()) >= 3000) return;
                if (status.equals("Zustand hinzufuegen"))
                    EditorUtils.createStateMouseDragged(e, editor);
                if (status.equals("Zustand verschieben"))
                    EditorUtils.dragStateMouseDragged(e, editor);
            }
        });

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Window window = e.getWindow();
                window.dispose();
            }
            public void windowActivated(WindowEvent e){
                File file = new File("highlight.dat");
                if(file.exists()) simulieren.setEnabled(true);
                else simulieren.setEnabled(false);
            }
        });

        addComponentListener(new ComponentAdapter ()
        {
            public void componentResized(ComponentEvent e)
            {
                dimension = getSize();

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

    private void checkHighlight()
    {
        File file = new File("highlight.dat");
        if (file.exists()) file.delete();
    }

    private void setAllDeselected(CheckboxMenuItem c0,CheckboxMenuItem c1,CheckboxMenuItem c2,CheckboxMenuItem c3,CheckboxMenuItem c4,CheckboxMenuItem c5,CheckboxMenuItem c6,CheckboxMenuItem c7)
    {
        c0.setState(false);
        c1.setState(false);
        c2.setState(false);
        c3.setState(false);
        c4.setState(false);
        c5.setState(false);
        c6.setState(false);
        c7.setState(false);
    }

    private double getFitToScreen()
    {
        int max = 0, startX = 0, startY = 0;
        Rectangle r;
        StateList list = ((Or_State)(statechart.state)).substates;
        while(list != null)
        {
            r = Methoden_0.abs(editor,list.head);
            if (r.x > startX) startX = r.x;
            if (r.y > startY) startY = r.y;
            if (list.head.rect.width > max) max = list.head.rect.width;
            if (list.head.rect.height > max) max = list.head.rect.height;
            list = list.tail;
        }
        int maxScreen;
        if (dimension.width < dimension.height) maxScreen = dimension.width;
        else maxScreen = dimension.height;
        if (startX < startY) max += startX;
        else max += startY;
        if (max == 0) max = maxScreen;
        return (((double)(maxScreen)/(double)max)*100);
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
        if (command.equals("Als Default setzen")) status = "Als Default setzen";
//extras
        if (command.equals("Markieren")) {status = "Markieren";}
        if (command.equals("Connector")) {status = "Connector";initInsertCon();}
        if (command.equals("Transition")) {status = "Transition";initInsertTr();}
        if (command.equals("Einfuegen")) {status = "Einfuegen";}
        if (command.equals("Verschieben")) {status = "Verschieben";}
        if (command.equals("Loeschen")) {status = "Loeschen";Methoden_1.removeObjects(editor);initRemove();status = "Markieren";}
        if (command.equals("move Transname")) {status = "move Transname";}
        if (command.equals("Zustand verschieben")) status = "Zustand verschieben";

        if (command.equals("Schriftgroesse")) {setFontSize();repaint();}

        if (command.equals("Simulieren")) {Methoden_1.startSimulation(editor);simulieren.setEnabled(false);}

    }

    private void setFontSize()
    {
        String nr = gui.EingabeDialog(this,"Schriftgroesse","Schriftgroesse einstellen","10");
        if (nr == null) {fontsize = 0; return;}
        try
        {
            Integer i = new Integer(nr);
            fontsize = i.intValue();
            if (fontsize < 5 | fontsize > 30)
                fontsize = 10;
            return;
        }
        catch(NumberFormatException e) {fontsize = 0;}
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

    private void initCopy()
    {
        if (Methoden_1.selectOneConnector != null & Methoden_1.markLast instanceof Connector){// | Methoden_1.selectList != null){
            copy.setEnabled(true);
            copyOneCon.setEnabled(true);
        }
        else
            copyOneCon.setEnabled(false);
        if (Methoden_1.selectOneTr != null & Methoden_1.markLast instanceof Tr){
            copy.setEnabled(true);
            copyOneTr.setEnabled(true);
            if (Methoden_1.selectOneTr.label.position != null)
                moveTransName.setEnabled(true);
        }
        else
        {
            copyOneTr.setEnabled(false);
            moveTransName.setEnabled(false);
        }
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
        if (Methoden_1.markLast != null | Methoden_1.selectList != null)
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
