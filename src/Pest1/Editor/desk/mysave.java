/**
 * mysave.java
 *
 *
 * Created: Wed Feb  3 14:00:34 1999
 *
 * @author Software Technologie 24
 * @version
 */

package editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;
import java.awt.Graphics.*;         
import java.io.*;                
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;     
import absyn.*;
import editor.*;
import java.applet.*;
import tesc2.*;


public class mysave extends Frame{
    
    FileDialog fDialog = new FileDialog(this);
    String     SBDateiname = null;
    String     SBPfad = null;

   public mysave(Statechart myroot) {
	        
       fDialog.setMode(FileDialog.SAVE);
       fDialog.setTitle("Statechart speichern");
       fDialog.setVisible(true);
       String FileName = fDialog.getFile();
       if (FileName != null)//Ok gewählt
         {
       try {
       
           FileOutputStream outf = new FileOutputStream(fDialog.getDirectory()+FileName);
                  ObjectOutputStream oos = new ObjectOutputStream(outf);
           oos.writeObject(myroot);
           oos.flush();
          oos.close();
		    // setDirty(false);
           SBPfad = fDialog.getDirectory();
           SBDateiname = FileName;
      }catch (Exception e)
           {
      	
       	// Alarm !
           }
          }

       	fDialog.setVisible(false);
       fDialog.dispose();


    }
    
} // mysave
