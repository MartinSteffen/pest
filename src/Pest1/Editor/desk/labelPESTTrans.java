/**
 * labelPESTTrans.java
 *
 *
 * Created: Thu Nov  5 10:43:39 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

package editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                 
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;    
import absyn.*;
import editor.desk.*;
import editor.*;

public class labelPESTTrans 
{
Statechart root;

   boolean drawtest = true;  
   static int laufname = 0;
   static String temptranslabel;
    
    public labelPESTTrans(Statechart nroot, int cx1, int cy1,Tr akttrans) 
    {
	root = nroot;
	akttrans.label.position = new CPoint(cx1,cy1);
	temptranslabel = Editor.labelObject(akttrans);
	// System.out.println("Neues Label :"+temptranslabel);

}
    
} // drawPESTTrans
