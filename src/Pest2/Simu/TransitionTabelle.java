package simu;


import java.util.Enumeration;
import java.util.Hashtable;
import absyn.*;
import util.*;

public class TransitionTabelle extends Object{
  Hashtable data=null;

  public TransitionTabelle(){
    data=new Hashtable();
  }

  public void debug(){
    PrettyPrint out=new PrettyPrint();
    Enumeration keys=data.keys();
    while (keys.hasMoreElements()){
      out.start((Path)(keys.nextElement()));
    }
  }


  public boolean isActive(Path p){
    Tr transition=(Tr)data.get(p);
    return (transition!=null);
  }

  public void setActive(Path p,Tr t){
    data.put(p,t);
  }

  public Tr getTransition(Path p){
    return ((Tr)data.get(p));
  }

   public TransitionTabelle verbinde(TransitionTabelle tab){
    TransitionTabelle result=new TransitionTabelle();
    Path temp=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle aktiven aus tab nach result...*/
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      result.setActive(temp,tab.getTransition(temp));
    }
    keys=data.keys();      /* und dann alle aktiven der Instanz */
    while (keys.hasMoreElements()){
      temp=(Path)keys.nextElement();
      result.setActive(temp,tab.getTransition(temp));
    }
    return result;
  }

}


