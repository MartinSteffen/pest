package simu;

import absyn.*;
import java.util.Vector;
import gui.*;


class Nachfolgermaschine extends Object{
   Statechart chart=null;
   StateTabelle act_states=null;
   TransitionTabelle act_transitions=null;
   EventTabelle act_events=null;
   BooleanTabelle act_booleans=null;
   StateTabelle pre_states=null;
   TransitionTabelle pre_transitions=null;
   EventTabelle pre_events=null;
   BooleanTabelle pre_booleans=null;

   GUIInterface gui=null;
   Communicator comm=null;
   
   
   public Nachfolgermaschine(Statechart sc, Communicator co, GUIInterface g){
     comm=co;
     chart=sc;
     gui=g;
   };




  /*debug fuer Pfade*/
  public void debug(Path path){
    String head=path.head;
    Path   tail=path.tail;
    while (tail!=null){
      System.out.print(head+" ");
      head=tail.head;
      tail=tail.tail;
    }
    System.out.print(head);
    System.out.print("\n");
  }
      


  /*step fuer And-States*/
   Status step(Path path, And_State as){
     if (gui.isDebug()){
       System.out.print("AND:");
       debug(path);
     }
     Vector statusse=new Vector(); 
     Status result=new Status();
     (result.states).setActive(path,as); /*as aktiv setzen*/
     StateList substates=as.substates;
     State temp=substates.head;
     StateList templist=substates.tail;
     statusse.addElement(step(path.append((temp.name).name),temp));
     while(templist!=null){
       temp=templist.head;                                            
       templist=templist.tail;
       statusse.addElement(step(path.append((temp.name).name),temp));/* fuer jeden Parallelstate */
     }                                                                /* step ausfuehren           */ 
     try{
       result=result.verbinde(statusse);
       /* results verschmelzen     */ 
     }
     catch (RacingException e){
       result=comm.solveBVarRacing(e);
     }
     return result;
   };

  /*step fuer OR-States:*/
   Status step(Path path, Or_State os){
     if (gui.isDebug()){
       System.out.print("OR:");
       debug(path);
     }
     Status result=new Status();
     if (act_states.isActive(path)){ /*Ist os schon aktiv? Wenn ja, dann...*/
       if (gui.isDebug()){
	 System.out.println("OS ist schon aktiv - suche Transitionen");
       }
       Vector transitionen=new Vector();
       if (os.trs!=null){
	 Tr temp=(os.trs).head;
	 TrList templist=(os.trs).tail;
	 if (enabled(path,temp)){
	   transitionen.addElement(temp); /*...wenn enabled, dann in einem Vector merken*/
	 }
	 while(templist!=null){       /*...pr’fen, welche der Transitionen enabled sind */ 
	   temp=templist.head;
	   templist=templist.tail;
	   if (enabled(path,temp)){
	     transitionen.addElement(temp); /*...wenn enabled, dann in einem Vector merken*/
	   }
	 }
       }
       if (gui.isDebug()){
	 System.out.println("Gefunden: "+transitionen.size());
       }
       if (transitionen.size()>1){                  /* Gibt es mehr als eine moegliche Transition */
	  transitionen=comm.solveNonDeterminism(transitionen);
	}
	/* BEGIN HACK: Damit das folgende Code-Stueck funktioniert, muss in result     */
	/* der momentan aktive Substate und os aktiv gesetzt werden.                          */
	StateList substates=os.substates;
	State tempstate=substates.head;
	StateList tempstatelist=substates.tail;
	if (act_states.isActive(path.append(tempstate.name.name))){
	  if (gui.isDebug()){	  
	    System.out.println("es ist einer aktiv...");
	  }
	  result.states.setActive(path.append((tempstate.name).name),tempstate);
	}
	while (tempstatelist!=null){
	  tempstate=tempstatelist.head;
	  tempstatelist=tempstatelist.tail;
	   if (act_states.isActive(path.append(tempstate.name.name))){
	     if (gui.isDebug()){	    
	       System.out.println("es ist einer aktiv...");
	     }
	    result.states.setActive(path.append((tempstate.name).name),tempstate);
	  }
	}
	result.states.setActive(path,os);
	/* ENDE HACK....                                                               */
	if (transitionen.size()>0){                  /* Gibt es denn mindestens eine?              */
	  Tr transit=(Tr)transitionen.firstElement();
	  try{
	    result=progress(path,transit,os,result);    /* Führe die Transition aus. */
	    result.transitions.setActive(transit,transit);
	  }
	  catch (ConnectorException e){
	  }
	}
	/* Dann fuer den aktiven State step ausfuehren                        */
	/* Aktiven State bestimmen: (1)                                       */
	 substates=os.substates;
	 tempstate=substates.head;
	 tempstatelist=substates.tail;
	 if (gui.isDebug()){	 
	   System.out.println("Suche-----");
	   debug(path.append((tempstate.name).name));
	   System.out.println("Result-bisher aktiv:");
	   result.states.debug();
	 }
	 while (!((result.states).isActive(path.append((tempstate.name).name)))){
	   if (gui.isDebug()){	
	     debug(path.append((tempstate.name).name));
	   }
	   tempstate=tempstatelist.head;
	   tempstatelist=tempstatelist.tail;
	 }
	 /* In tempstate steht jetzt der aktive State                    */
	 /* Jetzt rekursiven Abstieg in tempstate ausfuehren: step(tempstate) */
	 try{
	   result=result.verbinde(step(path.append((tempstate.name).name),tempstate));
	 }
	 catch (RacingException e){
	   result=comm.solveBVarRacing(e);
	 }
     }
     else{
       (result.states).setActive(path,os); /*os ist also nicht aktiv, d.h. man muž die Defaulttrans.*/
       StatenameList stlist=os.defaults;   /*ausfuehren und rekursiv absteigen und os aktiv setzen  */
       Statename defaultname=stlist.head;
       State defaultstate=null;
       if (stlist.tail!=null){
         /* Eigener Check, sollte eigentlich vom Syntaxchecker abgefangen werden */
	 if (gui.isDebug()){
	   System.out.println("Nichtdeterminismus: Mehrere Defaults in:"+path);
	 }
       }
       else{
	 StateList substates=os.substates;
	 State temp=substates.head;
	 StateList templist=substates.tail;
	 while (!((temp.name).name.equals(defaultname.name))){
	   temp=templist.head;
	   templist=templist.tail;
	 }
	 defaultstate=temp; /*Defaulttransition gefunden*/
	 try{
	   if (gui.isDebug()){   
	     System.out.println("Default ist: "+defaultstate.name.name);
	   }
	   result=result.verbinde(step(path.append((defaultstate.name).name),defaultstate)); /* rekursiver Abstieg */
	 }
	 catch (RacingException e){
	   result=comm.solveBVarRacing(e);
	 }
       }
     }
     return result;
   };

  /*step fuer den Basicstate, nichts zu tun, ausser Rueckliefern*/
  /*eines Status, in dem bs aktiv gesetzt ist.                 */
   Status step(Path path, Basic_State bs){
     if (gui.isDebug()){
       System.out.print("BASIC:");
       debug(path);
     }
     Status result=new Status();
     (result.states).setActive(path,bs);
     return result;
   };

  /*step liefert einen Status ausgehend vom aktuellen Pfad         */
  /*Der Pfad muss direkt auf den mit uebergebenen Status zeigen!!!!*/
   Status step(Path path,State s){
     /*Aufsplitten, je nach Statetyp wird dann das passende step aufgerufen*/
     Status result=new Status();
     if (s!=null){
       Class klasse=s.getClass();
       String klassenname=klasse.getName();
       if (klassenname.equals("absyn.Basic_State")){
	 result=step(path,(Basic_State)s);
       }
       if (klassenname.equals("absyn.Or_State")){
	 result=step(path,(Or_State)s);
       }
       if (klassenname.equals("absyn.And_State")){
	 result=step(path,(And_State)s);
       }
     }
     return result;
   }

   Status liefereNachfolger(Status pre_status, Status act_status){
     if (gui.isDebug()){
       System.out.println("LiefereNachfolger");
     }
     /*Vereinfachter Zugriff auf Statusse*/
     act_states=act_status.states;
     act_transitions=act_status.transitions;
     act_events=act_status.events;
     act_booleans=act_status.booleans;
     pre_states=pre_status.states;
     pre_transitions=pre_status.transitions;
     pre_events=pre_status.events;
     pre_booleans=pre_status.booleans;
     /*wende step-Relation auf rootstate an*/
     if (gui.isDebug()){ 
       System.out.println("Ausgangssituation:");
       act_states.debug();
     }
     Status result=new Status();
     State rootstate=chart.state;
     if (rootstate!=null){
       result=step((new Path((rootstate.name).name,null)),rootstate);
     }
     result=result.filterConditions(act_status);
     return result;
   };

   boolean isSatisfied(GuardEvent ge){
      SEvent event=ge.event;
      if (gui.isDebug()){   
	System.out.println("Testing Event: "+event.name);
      }
      if (act_events.isSet(event.name)){
	if (gui.isDebug()){
	  System.out.println("Event set: "+event.name);
	}
      }
      return act_events.isSet(event.name);
   }

   boolean isSatisfied(GuardBVar gb){
     boolean result=false;
     Bvar bvar=gb.bvar;
     result=act_booleans.isTrue(bvar);
     if (gui.isDebug()){
       System.out.println("Bvar "+bvar.var+" ist "+result+" .");
     }
     return act_booleans.isTrue(bvar);
   }
  
   boolean isSatisfied(GuardNeg gn){
      Guard g=gn.guard;
      return (!(isSatisfied(g)));
   }

   boolean isSatisfied(GuardCompp gcp){
      Comppath p=gcp.cpath;
      return isSatisfied(p);
   }

   boolean isSatisfied(Comppath p){
      boolean result=false;
      switch (p.pathop){
         case Comppath.IN      : {result=(act_states.isActive(p.path));break;};
         case Comppath.ENTERED : {result=((!(pre_states.isActive(p.path)))&&(act_states.isActive(p.path)));break;};
         case Comppath.EXITED  : {result=((pre_states.isActive(p.path))&&(!(act_states.isActive(p.path))));break;};
         default               : {result=false;};
      }
      return result;
   }

   boolean isSatisfied(GuardCompg gcg){
      Compguard g=gcg.cguard;
      return isSatisfied(g);
   }

   boolean isSatisfied(Compguard cg){
     boolean result=false;
     Guard left=cg.elhs;
     Guard right=cg.erhs;
     switch (cg.eop){
         case Compguard.AND     : {result=((isSatisfied(left))&&(isSatisfied(right)));break;};
         case Compguard.OR      : {result=((isSatisfied(left))||(isSatisfied(right)));break;};
         case Compguard.EQUIV   : {result=((isSatisfied(left))==(isSatisfied(right)));break;};
         case Compguard.IMPLIES : {result=((!(isSatisfied(left))||(isSatisfied(right))));break;};
         default                : {result=false;};
      }
      return result;
   }

   boolean isSatisfied(GuardEmpty ge){
      return true;
   }

   boolean isSatisfied(GuardUndet gu){
      return false;
   }

  public boolean isSatisfied(Guard g){
    boolean result=false;
    if (g!=null){
      Class klasse=g.getClass();
	String klassenname=klasse.getName();
	if (klassenname.equals("absyn.GuardUndet")){
	  result=isSatisfied((GuardUndet)g);
	}
	if (klassenname.equals("absyn.GuardEmpty")){
	  result=isSatisfied((GuardEmpty)g);
	}
	if (klassenname.equals("absyn.GuardCompg")){
	  result=isSatisfied((GuardCompg)g);
	}
	if (klassenname.equals("absyn.GuardCompp")){
	  result=isSatisfied((GuardCompp)g);
	}
	if (klassenname.equals("absyn.GuardNeg")){
	  result=isSatisfied((GuardNeg)g);
	}
	if (klassenname.equals("absyn.GuardBVar")){
        result=isSatisfied((GuardBVar)g);
	}
	if (klassenname.equals("absyn.GuardEvent")){
	  result=isSatisfied((GuardEvent)g);
	}
      }
      return result;
   }

  /* enabled prueft, ob eine Transition anwendbar ist */
  /* Bei Transitionen, die einen Connector als Ausgangspunkt haben, wird grundsaetzlich */
  /* false geliefert, das wir nicht ohne weiteres wissen, ob der Connector "aktiv ist */
  boolean enabled(Path path,Tr t){
    TrAnchor ta=t.source;
    TLabel   tl=t.label;
    Guard    tg=tl.guard;
    String sname=null;
    boolean result=false;
    
    if (ta instanceof Conname){
      result=false;
    }
    if (ta instanceof Statename){
      Statename statename=(Statename)ta;
      sname=statename.name;
      /* Ist der betr. State aktiv und der Guard erfuellt? */
      if (((act_states.isActive(path.append(sname)))) && (isSatisfied(tg))){
	result=true;
      }
      else{
	result=false;
      }
    }
    return result;
  }

  /* Zwei Hilfsfunktionen, die durch State- oder Transitionslisten iterieren */
  State getStateByName(String name, StateList states){
    State result=null;
    State head=states.head;
    String headname=(head.name).name;
    StateList tail=states.tail;
    if (gui.isDebug()){
      System.out.println("getStatebyName: "+name);
    }
    if (headname.equals(name)){
      result=head;
    }
    while (result==null){
      head=tail.head;
      headname=(head.name).name;
      tail=tail.tail;
      if (gui.isDebug()){  
	System.out.println(headname);
      }
      if (headname.equals(name)){
	result=head;
      }
    }
    return result;
  }

  TrList getTrsFromConnectorname(String name, TrList list){
    if (gui.isDebug()){
      System.out.println("getTrsFromConnectorname: "+name);
    }
    TrList result=null;
    Tr head=list.head;
    TrAnchor headsource=head.source;
    TrList tail=list.tail;
    String headname=null;
    if (headsource instanceof Conname){
      headname=((Conname)headsource).name;
      if (headname.equals(name)){
	result=new TrList(head,result);
      }
    }
    while (tail!=null){
      if (gui.isDebug()){
	System.out.println("getTrsFromConnectorname: Schleife");
      }
      head=tail.head;
      tail=tail.tail;
      headsource=head.source;
      if (headsource instanceof Conname){
	headname=((Conname)headsource).name;
	if (headname.equals(name)){
	  result=new TrList(head,result);
	}
      }
    }
    return result;
  }
      
    
  /* Progress fuer Transitionen von einem State zu einem State */ 
  Status progress (Path path, Or_State os, Tr t, Statename from, Statename to, Status status){
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      result=comm.solveBVarRacing(e);
    }
    TLabel tl=t.label;
    result.states.setInActive(path.append(from.name));
    result.states.setActive(path.append(to.name),getStateByName(to.name,os.substates));
    Action action=tl.action;
    result=progress(action,result);
    return result;
  }
  
  /* Progress fuer Transitionen von einem State zu einem Connector */ 
  Status progress (Path path, Or_State os, Tr t, Statename from, Conname to, Status status) throws ConnectorException{
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      result=comm.solveBVarRacing(e);
    }
    TLabel tl=t.label;
    Action action=tl.action;
    result=progress(action,result);
    TrList possibles=getTrsFromConnectorname(to.name,os.trs);
    Tr head=possibles.head;
    TrList tail=possibles.tail;
    Vector transitionen=new Vector();
    Guard headguard=head.label.guard;
    if (isSatisfied(headguard)){
      transitionen.addElement(head);
    }
    while (tail!=null){
      head=tail.head;
      headguard=head.label.guard;
      tail=tail.tail;
      if (isSatisfied(headguard)){
	transitionen.addElement(head);
      }
    }
    if (transitionen.size()>1){
      transitionen=comm.solveNonDeterminism(transitionen);
    }
    if (transitionen.size()>0){
      Tr transit=(Tr)transitionen.firstElement();
      result=progress(path,transit,os,result);
      result.transitions.setActive(transit,transit);
    }
    if (transitionen.size()==0){
      throw (new ConnectorException());
    }
    result.states.setInActive(path.append(from.name));
    return result;
  }

  /* Progress fuer Transitionen von einem Connector zu einem State */
   Status progress (Path path, Or_State os, Tr t, Conname from, Statename to, Status status){
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      result=comm.solveBVarRacing(e);
    }
    TLabel tl=t.label;
    result.states.setActive(path.append(to.name),getStateByName(to.name,os.substates));
    Action action=tl.action;
    result=progress(action,result);
    return result;
  }

   /* Progress fuer Transitionen von einem Connector zu einem Connector */ 
  Status progress (Path path, Or_State os, Tr t, Conname from, Conname to, Status status) throws ConnectorException{
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      result=comm.solveBVarRacing(e);
    }
    TLabel tl=t.label;
    Action action=tl.action;
    result=progress(action,result);
    TrList possibles=getTrsFromConnectorname(to.name,os.trs);
    Tr head=possibles.head;
    TrList tail=possibles.tail;
    Vector transitionen=new Vector();
    Guard headguard=head.label.guard;
    if (isSatisfied(headguard)){
      transitionen.addElement(head);
    }
    while (tail!=null){
      head=tail.head;
      headguard=head.label.guard;
      tail=tail.tail;
      if (isSatisfied(headguard)){
	transitionen.addElement(head);
      }
    }
    if (transitionen.size()>1){
      transitionen=comm.solveNonDeterminism(transitionen);
    }
    if (transitionen.size()>0){
      Tr transit=(Tr)transitionen.firstElement();
      result=progress(path,transit,os,result);
    }
    if (transitionen.size()==0){
      throw (new ConnectorException());
    }
    return result;
  }
  
    
      
      

  /* Progress führt die Transition t auf status aus, indem rekursiv die action aufgeloest wird, */
  /* und liefert einen lokal veränderten Status zurück */
  Status progress(Path path, Tr t, Or_State os, Status status) throws ConnectorException{
    if (gui.isDebug()){
      System.out.println("Progress....");
    }
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      result=comm.solveBVarRacing(e);
    }
    TrAnchor from=t.source;
    TrAnchor to=t.target;
    
    if (from instanceof Statename){
      if (to instanceof Statename){
	result=progress(path,os,t,(Statename)from,(Statename)to,status);
      }
      if (to instanceof Conname){
	try{
	  result=progress(path,os,t,(Statename)from,(Conname)to,status);
	}
	catch (ConnectorException e){
	}
      }
    }
    if (from instanceof Conname){
      if (to instanceof Statename){
	result=progress(path,os,t,(Conname)from,(Statename)to,status);
      }
      if (to instanceof Conname){
	result=progress(path,os,t,(Conname)from,(Conname)to,status);
      }
    }
    return result;
  }
      
  Status progress(ActionBlock ab, Status status){
    Status result=status;
    Aseq aseq=ab.aseq;
    Action head=aseq.head;
    Aseq tail=aseq.tail;
    result=progress(head,result);
    while(tail!=null){
      head=tail.head;
      tail=tail.tail;
      result=progress(head,result);
    }
    return result;
  }

  Status progress(ActionEmpty ae, Status status){
    /* Hier muss nichts getan werden */
    return status;
  }

  Status progress(ActionEvt aevt, Status status){
    Status result=status;
    SEvent event=aevt.event;
    result.events.set(event.name,event);
    return result;
  }

  Status progress(ActionStmt as, Status status){
    if (gui.isDebug()){
      System.out.println("progress fuer ActionStmt");
    }
    Status result=status;
    Boolstmt stmt=as.stmt;
    Class klasse=stmt.getClass();
    String klassenname=klasse.getName();
    if (klassenname.equals("absyn.BAss")){
      result=progress((BAss)stmt,status);
    }
    if (klassenname.equals("absyn.MFalse")){
      result=progress((MFalse)stmt,status);
    }
    if (klassenname.equals("absyn.MTrue")){
      result=progress((MTrue)stmt,status);
    }
    return result;
  }

  Status progress(BAss bass, Status status){
    if (gui.isDebug()){
      System.out.println("progress fuer BAss");
    }
    Status result=status;
    Bassign assign=bass.ass;
    Bvar lhs=assign.blhs;
    Guard rhs=assign.brhs;
    if (isSatisfied(rhs)){
      result.booleans.setTrue(lhs);
    }
    else{
      result.booleans.setFalse(lhs);
    }
    return result;
  }
  
  Status progress(MFalse fass, Status status){
    if (gui.isDebug()){
      System.out.println("progress fuer MFalse");
    }
    Status result=status;
    Bvar var=fass.var;
    result.booleans.setFalse(var);
    return result;
  }
  
  Status progress(MTrue tass, Status status){
    if (gui.isDebug()){
      System.out.println("progress fuer MTrue");
    }
    Status result=status;
    Bvar var=tass.var;

    result.booleans.setTrue(var);
    if (gui.isDebug()){
      System.out.println(var.var+" wurde auf True gesetzt");
      System.out.println(var.var+" ist "+result.booleans.isTrue(var));
    }
    return result;
  }
  

  Status progress(Action a, Status status){
    Status result=new Status();
    Class klasse=a.getClass();
    String klassenname=klasse.getName();
    if (klassenname.equals("absyn.ActionBlock")){
      result=progress((ActionBlock)a,status);
    }
    if (klassenname.equals("absyn.ActionEmpty")){
      result=progress((ActionEmpty)a,status);
    }
    if (klassenname.equals("absyn.ActionEvt")){
      result=progress((ActionEvt)a,status);
    }
    if (klassenname.equals("absyn.ActionStmt")){
      result=progress((ActionStmt)a,status);
    }
    return result;
  }
} 
  
  











