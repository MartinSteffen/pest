package Absyn;

public class Or_State extends State{
    public Statename     s_oname;
    public StateList     s_osubstates;
    public TrList        s_otrs;
    public StatenameList s_odefaults;
    public ConnectorList s_oconnectors;
    public Or_State(Statename osn,
		    StateList osl,
		    TrList otl,
		    StatenameList osnl,
		    ConnectorList ocl) {
      s_oname = osn;
      s_osubstates = osl;
      s_otrs = otl;
      s_odefaults = osnl;
      s_oconnectors = ocl;
    }
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Or_State.java,v 1.3 1998-11-27 10:04:07 swtech20 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:20  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------





