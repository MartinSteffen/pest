package simu;


import java.util.Enumeration;
import java.util.Hashtable;
import absyn.*;
import util.*;

public class StateTabelle extends Object{
  Hashtable data=null;

  public StateTabelle(){
    data=new Hashtable();
  }

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
      System.err.print("verbindeTabtab:");
      debug(temp);
      result.setActive(temp,tab.getState(temp));
    }
    keys=data.keys();      /* und dann alle aktiven der Instanz */
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      System.err.print("verbindeTabinstance:");
      debug(temp);
      result.setActive(temp,getState(temp));
    }
    return result;
  }
}


