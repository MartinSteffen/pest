package simu;


import java.util.*;
import absyn.*;
import util.*;

class StateTabelle extends Object{
  Hashtable data=null;

  public StateTabelle(){
    data=new Hashtable();
  }

 /*debug fuer Pfade*/
  public void debug(Path path){
    String head=path.head;
    Path   tail=path.tail;
    while (tail!=null){
      System.out.print(head+"-");
      head=tail.head;
      tail=tail.tail;
    }
    System.out.print(head);
  }

  public String pathToString(Path path){
    String result="";
    String head=path.head;
    Path   tail=path.tail;
    while (tail!=null){
      result=head+".";
      head=tail.head;
      tail=tail.tail;
    }
    result+=head;
    return result;
  }

  Path stringToPath(String input){
    Path result=null;
    String inline=input.trim();
    String temp=null;
    if (inline.length()!=0){
      StringTokenizer strtok=new StringTokenizer(inline,".");
      result=new Path(strtok.nextToken(),null);
      while (strtok.hasMoreTokens()){
	temp=strtok.nextToken();
	result=result.append(temp);
      }
    }
    return result;
  }

  State stateByPath(Path p, Basic_State s){
    State result=s;
    if (p!=null){
      result=null;
    }
    return result;
  }

  State stateByPath(Path p, And_State s){
    State result=s;
    if (p!=null){
      StateList list=s.substates;
      State listhead=null;
      String head=p.head;
      while (list!=null){
	listhead=list.head;
	if (head.equals(listhead.name.name)){
	  result=stateByPath(p.tail,listhead);
	  break;
	}
	else{
	  result=null;
	}
	list=list.tail;
      }
    }
    return result;
  }
    
  State stateByPath(Path p, Or_State s){
    State result=s;
    if (p!=null){
      StateList list=s.substates;
      State listhead=null;
      String head=p.head;
      while (list!=null){
	listhead=list.head;
	if (head.equals(listhead.name.name)){
	  result=stateByPath(p.tail,listhead);
	  break;
	}
	else{
	  result=null;
	}
	list=list.tail;
      }
    }
    return result;
  }
    

  State stateByPath(Path p, State s){
    State result=null;
    if (s instanceof Basic_State){
      result=stateByPath(p,(Basic_State)s);
    }
    if (s instanceof And_State){
      result=stateByPath(p,(And_State)s);
    }
    if (s instanceof Or_State){
      result=stateByPath(p,(Or_State)s);
    }
    return result;
  }

  State stateByPath(Path p,Statechart s){
    State result=s.state;
    if (p!=null){
      String head=p.head;
      if (head.equals(result.name.name)){
	result=stateByPath(p.tail,result);
      }
    }
    return result;
  }
    


  public void insert(String elements,Statechart s) throws TraceFormatException{
    String inline=elements.trim();
    String temp=null;
    State tempstate=null;
    State defaultstate=s.state;
    Path temppath=null;
    if (inline.length()!=0){
      StringTokenizer strtok=new StringTokenizer(inline," ");
      while (strtok.hasMoreTokens()){
	temp=strtok.nextToken();
	temppath=stringToPath(temp);
	tempstate=stateByPath(temppath,s);
	if (tempstate!=null){
	  setActive(temppath,tempstate);
	}
	else{
	  throw(new TraceFormatException());
	}
      }
    }
  }

  public void debug(){
    System.out.println("Active States:");
    PrettyPrint out=new PrettyPrint();
    Enumeration keys=data.keys();
    while (keys.hasMoreElements()){
      out.start((Path)(keys.nextElement()));
      System.out.print("\n");
    }
  }

  private boolean isEqual(Path p1, Path p2){
    if ((p1==null)||(p2==null)){
      return ((p1==null)&&(p2==null));
    }
    else{
      return (((p1.head).equals(p2.head)) && isEqual(p1.tail,p2.tail));
    }
  }

  public boolean isActive(Path p){
    boolean result=false;
    Enumeration keys=data.keys();
    Path temp=null;
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      if (isEqual(temp,p)){
	result=true;
      }
    }
    return result;
  }

  public void setActive(Path p,State s){
    if (!(isActive(p))){
      data.put(p,s);
    }
  }

  public void setInActive(Path p){
    Enumeration keys=data.keys();
    Path temp=null;
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      if (isEqual(temp,p)){
	data.remove(temp);
      }
    }
  }

  public State getState(Path p){
    State result=null;
    Enumeration keys=data.keys();
    Path temp=null;
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      if (isEqual(temp,p)){
	result=(State)data.get(temp);
      }
    }
    return result;
  }

  /* "Verschmilzt" zwei StateTabellen in "oder"-Verknüpfung */
  public StateTabelle verbinde(StateTabelle tab){
    StateTabelle result=new StateTabelle();
    Path temp=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle aktiven aus tab nach result...*/
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      result.setActive(temp,tab.getState(temp));
    }
    keys=data.keys();      /* und dann alle aktiven der Instanz */
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      result.setActive(temp,getState(temp));
    }
    return result;
  }

  public String toString(){
    String result="";
    Enumeration enum=data.keys();
    result+="( ";
    while (enum.hasMoreElements()){
      result+=pathToString((Path)enum.nextElement());
      result+=" ";
    }
    result+=")";
    return result;
  }

}










