package gui;

import java.io.*;
import java.awt.*;

class pestConfig
    extends Object
    implements Serializable{
    
    Dimension GUIDim, EditorDim;
    Point GUILoc, EditorLoc;
    Rectangle bounds;
    boolean isEditor;
    String Dateiname;
    String Pfad;
    int stateColorIndex,transColorIndex,conColorIndex;
    int BgColorIndex,ActColorIndex,InactColorIndex;

    boolean CheckedSC;
    boolean ResultSC;
    boolean isDirty;
    boolean ctrlWin;
    boolean debug;

    int stmXSize;
    int stmYSize;
    boolean stmKoord;

    check.CheckConfig checkConfig;
}
