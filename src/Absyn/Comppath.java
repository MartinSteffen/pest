package Absyn;

import java.io.Serializable;

/**
 * Operation on path of statenames.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Comppath.java,v 1.8 1998-12-15 07:11:07 swtech01 Exp $
 */
public class Comppath extends Absyn implements Serializable, Cloneable {
/**
 * Path operation.
 */
    public int  pathop;
/**
 * Path.
 */
    public Path   path;
/**
 * Constants
 */
    public final static int IN = 0;
    public final static int ENTERED  = 1;
    public final static int EXITED   = 2;
/**
 * Constructor.
 */
    public Comppath(int op, Path p) {
	pathop  = op;
	path    = p;
    };

    public Object clone() throws CloneNotSupportedException {
	return new Comppath (pathop, (Path)path.clone());
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Comppath.java,v 1.8 1998-12-15 07:11:07 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/11 17:42:56  swtech00
//	Cloneable
//
//	Revision 1.6  1998/12/01 17:43:07  swtech00
//	Die operatoren als Konstante Felder mit aufgenommen
//
//	Revision 1.5  1998/12/01 10:48:04  swtech00
//	Die Selektor-Felder umbenannt ("s_" entfernt)
//
//	Revision 1.4  1998/11/27 16:07:39  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
