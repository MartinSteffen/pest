/**
 * PESTeditData.java
 *
 *
 * Created: Thu Nov  5 11:52:16 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

package Editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;     

public class PESTeditData implements Serializable{
	public String ID;
	public int x1;
	public int y1;
	public int x2;
	public int y2;
                public int s1;
                public int s2;
	public String Txt1;
	public String Txt2;
	public String Txt3;
                public int Father;
                public int Son;
	public int NR;

 	public PESTeditData(String tID, int tx1, int ty1, int tx2, int ty2, int ts1, int ts2, String tTxt1, String tTxt2, String tTxt3, int tFather, int tSon, int tNR) {
	{
 	  this.ID = tID;
	  this.x1 = tx1;
	  this.y1 = ty1;
	  this.x2 = tx2;
	  this.y2 = ty2;
	  this.s1 = ts1;
	  this.s2 = ts2;
	  this.Txt1 = tTxt1;
	  this.Txt2 = tTxt2;
	  this.Txt3 = tTxt3;
	  this.Father = tFather;
	  this.Son = tSon;
	  this.NR = tNR;
	}
          }
} // PESTeditData
