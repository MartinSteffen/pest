/**
 *  Statematrix
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

public class Statematrix {
	public State akt;
	public State prev;
public Statematrix(State x, State y) {
	akt = x;
	prev = y;
	}
}
