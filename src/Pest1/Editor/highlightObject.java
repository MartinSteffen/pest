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

public class highlightObject {
    
/**
 * Generates a change to default color of object 
 *<ul>
 * <li>g    : graphic frame   
 * <li>obj  :  
 * </ul>
 */

    public highlightObject(Graphics g, Basic_State obj) { }
    public highlightObject(Graphics g, Basic_State obj, Color col) { }

    public highlightObject(Graphics g, And_State obj) { }
    public highlightObject(Graphics g, And_State obj, Color col) { }
   
    public highlightObject(Graphics g, Or_State obj) { }
    public highlightObject(Graphics g, Or_State obj, Color col) { }

    public highlightObject(Graphics g, Tr obj) { }
    public highlightObject(Graphics g, Tr obj, Color col) { }

    public highlightObject(Graphics g, Connector obj) { }
    public highlightObject(Graphics g, Connector obj, Color col) { }


} // highlightObject
