package Absyn;

public class Or_State extends State{
    public StateList     substates;
    public TrList        trs;
    public StatenameList defaults;
    public ConnectorList connectors;
    public Or_State(Statename n,
		    StateList sl,
		    TrList tl,
		    StatenameList snl,
		    ConnectorList cl) {
      name         = n;
      substates    = sl;
      trs          = tl;
      defaults     = snl;
      connectors   = cl;
    }
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Or_State.java,v 1.4 1998-11-30 17:05:10 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/27 10:04:07  swtech20
//	Neuer Konstruktor.
//
//	Revision 1.2  1998/11/26 16:32:20  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------





