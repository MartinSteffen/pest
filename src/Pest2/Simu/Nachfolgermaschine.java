package simu;

import absyn.*;
import java.util.Vector;

/** Die Nachfolgermaschine: NICHT ausreichend getestet!!!*/
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

   Communicator comm=null;
   
   
   public Nachfolgermaschine(Statechart sc, Communicator co){
     comm=co;
     chart=sc;
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
     System.err.print("AND:");
     debug(path);
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
       result=result.verbinde(statusse);                              /* results verschmelzen     */ 
     }
     catch (RacingException e){
       /* Racing-Situation bei Conditions aufgetreten!*/
       /* Frage an Communicator                       */
       System.err.println("Racing bei Condition!");
     }
     return result;
   };

  /*step fuer OR-States:*/
   Status step(Path path, Or_State os){
     System.err.print("OR:");
     debug(path);
     Status result=new Status();
     if (act_states.isActive(path)){ /*Ist os schon aktiv? Wenn ja, dann...*/
       System.err.println("OS ist schon aktiv - suche Transitionen");
       Tr temp=(os.trs).head;       
       TrList templist=(os.trs).tail;
       Vector transitionen=new Vector();
       if (enabled(path,temp)){
	 transitionen.addElement(temp); /*...wenn enabled, dann in einem Vector merken*/
       }
	while(templist!=null){       /*...pr�fen, welche der Transitionen enabled sind */ 
	  temp=templist.head;
	  templist=templist.tail;
	  if (enabled(path,temp)){
	    transitionen.addElement(temp); /*...wenn enabled, dann in einem Vector merken*/
	  }
	}
	System.err.println("Gefunden: "+transitionen.size());
	if (transitionen.size()>1){                  /* Gibt es mehr als eine moegliche Transition */
	  System.err.println("Nichtdeterminismus.......");
	  /* Hier den Communicator fragen, und den Vector dann auf eine */
	  /* Transition beschneiden.                                    */
	}
	/* BEGIN HACK: Damit das folgende Code-Stueck funktioniert, muss in result     */
	/* der momentan aktive Substate und os aktiv gesetzt werden.                          */
	StateList substates=os.substates;
	State tempstate=substates.head;
	StateList tempstatelist=substates.tail;
	if (act_states.isActive(path.append(tempstate.name.name))){
	  System.err.println("es ist einer aktiv...");
	  result.states.setActive(path.append((tempstate.name).name),tempstate);
	}
	while (tempstatelist!=null){
	  tempstate=tempstatelist.head;
	  tempstatelist=tempstatelist.tail;
	   if (act_states.isActive(path.append(tempstate.name.name))){
	    System.err.println("es ist einer aktiv...");
	    result.states.setActive(path.append((tempstate.name).name),tempstate);
	  }
	}
	result.states.setActive(path,os);
	/* ENDE HACK....                                                               */
	if (transitionen.size()>0){                  /* Gibt es denn mindestens eine?              */
	  Tr transit=(Tr)transitionen.firstElement();
	  result=progress(path,transit,result);    /* F�hre die Transition aus. */
	}
	/* Dann fuer den aktiven State step ausfuehren                        */
	/* Aktiven State bestimmen: (1)                                       */
	 substates=os.substates;
	 tempstate=substates.head;
	 tempstatelist=substates.tail;
	 System.err.println("Suche-----");
	 debug(path.append((tempstate.name).name));
	 while (!((result.states).isActive(path.append((tempstate.name).name)))){
	   debug(path.append((tempstate.name).name));
	   tempstate=tempstatelist.head;
	   tempstatelist=tempstatelist.tail;
	 }
	 /* In tempstate steht jetzt der aktive State                    */
	 /* Jetzt rekursiven Abstieg in tempstate ausfuehren: step(tempstate) */
	 try{
	   result=result.verbinde(step(path.append((tempstate.name).name),tempstate));
	 }
	 catch (RacingException e){
	   /* Racing-Situation bei Conditions aufgetreten!*/
	   /* Frage an Communicator                       */
	   System.err.println("Racing bei Condition!");
	 }
     }
     else{
       (result.states).setActive(path,os); /*os ist also nicht aktiv, d.h. man mu� die Defaulttrans.*/
       StatenameList stlist=os.defaults;   /*ausfuehren und rekursiv absteigen und os aktiv setzen  */
       Statename defaultname=stlist.head;
       State defaultstate=null;
       if (stlist.tail!=null){
         /* Eigener Check, sollte eigentlich vom Syntaxchecker abgefangen werden */
	 System.err.println("Nichtdeterminismus: Mehrere Defaults in:"+path);
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
	   System.err.println("Default ist: "+defaultstate.name.name);
	   result=result.verbinde(step(path.append((defaultstate.name).name),defaultstate)); /* rekursiver Abstieg */
	 }
	 catch (RacingException e){
	   /* Racing-Situation bei Conditions aufgetreten!*/
	   /* Frage an Communicator                       */
	   System.err.println("Racing bei Condition!");
	 }
       }
     }
     return result;
   };

  /*step fuer den Basicstate, nichts zu tun, ausser Rueckliefern*/
  /*eines Status, in dem bs aktiv gesetzt ist.                 */
   Status step(Path path, Basic_State bs){
     System.err.print("BASIC:");
     debug(path);
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
     System.out.println("LiefereNachfolger");
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
     System.err.println("Ausgangssituation:");
     act_states.debug();
     State rootstate=chart.state;
     Status result=step((new Path((rootstate.name).name,null)),rootstate);
     return result;
   };

   boolean isSatisfied(GuardEvent ge){
      SEvent event=ge.event;
      System.err.println("Testing Event: "+event.name);
      if (act_events.isSet(event.name)){
	System.err.println("Event set: "+event.name);
      }
      return act_events.isSet(event.name);
   }

   boolean isSatisfied(GuardBVar gb){
      Bvar bvar=gb.bvar;
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

   boolean isSatisfied(Guard g){
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
   boolean enabled(Path path,Tr t){
      TrAnchor ta=t.source;
      TLabel   tl=t.label;
      Guard    tg=tl.guard;
      String sname=null;
      boolean result=false;

      /*Achtung! Noch keine Connectoren benutzbar*/
      Statename statename=(Statename)ta;
      sname=statename.name;
      /*Achtung! */

      /* Ist der betr. State aktiv und der Guard erfuellt? */
      if (((act_states.isActive(path.append(sname)))) && (isSatisfied(tg))){
         result=true;
      }
      else{
         result=false;
      }
      return result;
   }

  /* Progress f�hrt die Transition t auf status aus, indem rekursiv die action aufgeloest wird, */
  /* und liefert einen lokal ver�nderten Status zur�ck */
  Status progress(Path path, Tr t, Status status){
    System.err.println("Progress....");
    Status result=new Status();
    try{
      result=result.verbinde(status);
    }
    catch (RacingException e){
      /* Racing-Situation bei Conditions aufgetreten!*/
      /* Frage an Communicator                       */
      System.err.println("Racing bei Condition!");
    }
    TLabel tl=t.label;
    TrAnchor from=t.source;
    TrAnchor to=t.target;
    
    /*Achtung! Noch keine Connectoren benutzbar*/
    String fromname=((Statename)from).name;
    String toname=((Statename)to).name;
    /*Achtung! */

    result.states.setActive(path.append(toname),(new Basic_State(new Statename("Dummi"))) ); /*WICHTIG: Statt null den State uebergeben!!!*/
    result.states.setInActive(path.append(fromname)); 
    Action action=tl.action;
    result=progress(action,result);
    
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
    Status result=status;
    Boolstmt stmt=as.stmt;
    Class klasse=as.getClass();
    String klassenname=klasse.getName();
    if (klassenname.equals("absyn.BAss")){
      result=progress((BAss)stmt,status);
    }
    if (klassenname.equals("absyn.BAss")){
      result=progress((MFalse)stmt,status);
    }
    if (klassenname.equals("absyn.BAss")){
      result=progress((MTrue)stmt,status);
    }
    return result;
  }

  Status progress(BAss bass, Status status){
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
    Status result=status;
    Bvar var=fass.var;
    result.booleans.setFalse(var);
    return result;
  }
  
  Status progress(MTrue tass, Status status){
    Status result=status;
    Bvar var=tass.var;
    result.booleans.setTrue(var);
    return result;
  }
  

  Status progress(Action a, Status status){
    Status result=null;
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
  
  











