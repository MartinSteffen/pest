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
    Color stateColor,transitionColor,connectorColor;
}
