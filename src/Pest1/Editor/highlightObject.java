/**
 * highlightObject.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 * @author Software Technologie 24
 * @version
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;
public class highlightObject {


  static Graphics h;
       
  public highlightObject (Graphics g) {h = g;};
   
    
  public highlightObject () { h.drawRect(10,10,100,100);}

    
/**
 * Generates a change to default color of object 
 *<ul>
 * <li>g    : graphic frame   
 * <li>obj  :  
 * </ul>
 */


    public highlightObject(Basic_State obj) { }
    public highlightObject(Basic_State obj, Color col) { }

    public highlightObject(And_State obj) { }
    public highlightObject(And_State obj, Color col) { }
   
    public highlightObject(Or_State obj) { }
    public highlightObject(Or_State obj, Color col) { }

    public highlightObject(Tr obj) { }
    public highlightObject(Tr obj, Color col) { }

    public highlightObject(Connector obj) { }
    public highlightObject(Connector obj, Color col) { }


} // highlightObject
