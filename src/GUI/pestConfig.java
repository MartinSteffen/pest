package gui;

import java.io.*;
import java.awt.*;

class pestConfig
    extends Object
    implements Serializable{
    
    Dimension GUIDim, EditorDim;
    Point GUILoc, EditorLoc;
    boolean isEditor;
    String Dateiname;
    String Pfad;
    int stateColorIndex,transColorIndex,conColorIndex;
    int BgColorIndex,ActColorIndex,InactColorIndex;

    boolean CheckedSC;
    boolean ResultSC;
    boolean isDirty;
    boolean ctrlWin;

    int stmXSize;
    int stmYSize;
    boolean stmKoord;

    check.CheckConfig checkConfig;
}
