package Absyn;


/**
 * Boolean variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Bvar.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
 */
public class Bvar  extends Absyn  implements Cloneable {
/**
 * Contents of the variable.
 */
    public String var;
/**
 * Constructor.
 */
    public Bvar (String v) {
	var = v;
    };

    public Object clone() throws CloneNotSupportedException {
	return new Bvar(var);
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bvar.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:05:31  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:12  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
