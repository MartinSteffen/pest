package simu;


import java.util.Enumeration;
import java.util.Hashtable;
import absyn.*;
import util.*;

class TransitionTabelle extends Object{
  Hashtable data=null;

  public TransitionTabelle(){
    data=new Hashtable();
  }

  public void debug(){
    PrettyPrint out=new PrettyPrint();
    Enumeration keys=data.keys();
    while (keys.hasMoreElements()){
      out.start((Tr)(keys.nextElement()));
    }
  }


  public boolean isActive(Tr t){
    Tr transition=(Tr)data.get(t);
    return (transition!=null);
  }

  public void setActive(Tr k,Tr t){
    data.put(k,t);
  }

  public Tr getTransition(Tr t){
    return ((Tr)data.get(t));
  }

   public TransitionTabelle verbinde(TransitionTabelle tab){
    TransitionTabelle result=new TransitionTabelle();
    Tr temp=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle aktiven aus tab nach result...*/
    while (keys.hasMoreElements()){
      temp=(Tr)keys.nextElement();
      result.setActive(temp,tab.getTransition(temp));
    }
    keys=data.keys();      /* und dann alle aktiven der Instanz */
    while (keys.hasMoreElements()){
      temp=(Tr)keys.nextElement();
      result.setActive(temp,getTransition(temp));
    }
    return result;
  }

}


