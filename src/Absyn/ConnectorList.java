package Absyn;

public class ConnectorList extends Absyn {
    public Connector head;
    public ConnectorList  tail;
    public ConnectorList(Connector h, ConnectorList tl) {
	head = h;
	tail = tl;
    }
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ConnectorList.java,v 1.3 1998-11-26 16:32:15 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
