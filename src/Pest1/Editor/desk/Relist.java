
package editor.desk;

import absyn.*;
import java.awt.*;
import util.*;
import editor.*;

/**
 * @author Michael Mai / Martin Bestaendig
 * @version V1.8</dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Neuschreiben der Listen funktioniert
 * <li> Die absoluten Pfadnamen werden unterstuetzt
 * </ul>
 */
public class Relist {

  Statechart root;
   
    static Color def_state = Color.green;
    static Color def_conn = Color.red;
    static Color def_text = Color.black;
    
  public Relist () {}


    public void start (Statechart nroot,PathList npathlist, Path npath,Absyn ab) {
	Statechart root = nroot;
	PathList pathlist = npathlist;
	Path path = npath;

	if (ab instanceof Statechart) redraw(root,pathlist,path,(Statechart) ab);
	if (ab instanceof Basic_State) redraw(root,pathlist,path,(Basic_State) ab);
	if (ab instanceof Or_State) redraw(root,pathlist,path,(Or_State) ab);
	if (ab instanceof And_State) redraw(root,pathlist,path,(And_State) ab);
	if (ab instanceof Connector) redraw(root,pathlist,path,(Connector) ab);

    }

 
    private void redraw(Statechart nroot,PathList npathlist, Path npath,Statechart sc) {
	Statechart root = nroot;
	PathList pathlist = npathlist;
	Path path = npath;
	root.bvars = null;
	root.events = null;
	if (sc.state != null) { Relist re = new Relist(); re.start(root,pathlist,path,sc.state);
	//System.out.println("Statechart");
	}
       }

    private void redraw(Statechart nroot,PathList npathlist, Path npath,Basic_State bs) {
	Statechart root = nroot;
	PathList pathlist = npathlist, temppathlist = null;;
	Path path = npath,temppath = null,temppath2=null,temppath3 = null;
	
	temppathlist = pathlist;
	temppath = path;
	path = new Path(bs.name.name,temppath);

	//temppath3 = new Path(bs.name.name,null);
	while (path.tail != null)
	{
	temppath2 = new Path(path.head,temppath3);
	temppath3 = temppath2;
	path = path.tail; 
	}
	temppath2 = new Path(path.head,temppath3);

	pathlist = new PathList(temppath2,temppathlist);
	root.cnames = pathlist;
	//System.out.println("Basic_State");
	//PrettyPrint pp = new PrettyPrint();
  	//pp.start(root);
    	}


 private void redraw(Statechart nroot,PathList npathlist, Path npath,Connector co) {
	Statechart root = nroot;
	PathList pathlist = npathlist, temppathlist = null;;
	Path path = npath,temppath = null,temppath2=null,temppath3 = null;
	Absyn taction,tguard,tlauf;	

	temppathlist = pathlist;
	temppath = path;
	path = new Path(co.name.name,temppath);
	temppath = path;
	while (temppath.tail != null)
	{
	temppath2 = new Path(temppath.head,temppath3);
	temppath3 = temppath2;
	temppath = temppath.tail; 
	}
	temppath2 = new Path(temppath.head,temppath3);

	pathlist = new PathList(temppath2,temppathlist);
	root.cnames = pathlist;
	//System.out.println("Basic_State");
	//PrettyPrint pp = new PrettyPrint();
  	//pp.start(root);
    	}


      private void redraw(Statechart nroot,PathList npathlist, Path npath,Or_State os) {
	Statechart root = nroot;
	PathList pathlist= npathlist, temppathlist = null;
	Path path = npath,temppath = null,temppath2=null,temppath3 = null;
	StateList templist;
	ConnectorList colist;
	TrList trlist;	

	temppathlist = pathlist;
	temppath = npath;
	path = new Path(os.name.name,temppath);
	temppath = path;
	while (temppath.tail != null)
	{
	temppath2 = new Path(temppath.head,temppath3);
	temppath3 = temppath2;
	temppath = temppath.tail; 
	}
	temppath2 = new Path(temppath.head,temppath3);

	pathlist = new PathList(temppath2,temppathlist);
	nroot.cnames = pathlist;

	templist = os.substates;
	while (templist != null)
	{
	pathlist = nroot.cnames;
	Relist re = new Relist(); re.start(root,pathlist,path,templist.head);
	templist = templist.tail;
	}

	String temptranslabel;
	trlist = os.trs;
	while (trlist != null)
	{
	Editor.relabeltrans(trlist.head);
	// new makevarlist(root,trlist.head.label);
	trlist = trlist.tail;
	}


	//System.out.println("Or_State");
    	}


      private void redraw(Statechart nroot,PathList npathlist, Path npath,And_State as) {
	Statechart root = nroot;
	PathList pathlist= npathlist, temppathlist = null;
	Path path = npath,temppath = null,temppath2=null,temppath3 = null;
	StateList templist;	

	temppathlist = pathlist;
	temppath = npath;
	path = new Path(as.name.name,temppath);
	temppath = path;
	while (temppath.tail != null)
	{
	temppath2 = new Path(temppath.head,temppath3);
	temppath3 = temppath2;
	temppath = temppath.tail; 
	}
	temppath2 = new Path(temppath.head,temppath3);

 
	pathlist = new PathList(temppath2,temppathlist);
	nroot.cnames = pathlist;

	templist = as.substates;
	while (templist != null)
	{
	pathlist = nroot.cnames;
	 Relist re = new Relist(); re.start(root,pathlist,path,templist.head);
	templist = templist.tail;
	}

	//System.out.println("And_State");
    	}


   
}
