package simu;


import java.util.Hashtable;
import java.util.Enumeration;
import absyn.*;

public class EventTabelle extends Object{
  Hashtable data=null;

  public EventTabelle(){
    data=new Hashtable();
  }

  public boolean isSet(String name){
    SEvent event=(SEvent)data.get(name);
    return (event!=null);
  }

  public void set(String name, SEvent e){
    if (!(isSet(name))){
      data.put(name,e);
    }
  }

  public void remove(String name){
    Enumeration enum=data.keys();
    String temp=null;
    while (enum.hasMoreElements()){
      temp=(String)enum.nextElement();
      if (temp.equals(name)){
	data.remove(temp);
      }
    }
  }

  public SEvent getEvent(String name){
    return ((SEvent)data.get(name));
  }

  public EventTabelle verbinde(EventTabelle tab){
    EventTabelle result=new EventTabelle();
    String temp=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle aktiven aus tab nach result...*/
    while (keys.hasMoreElements()){
      temp=(String)keys.nextElement();
      result.set(temp,tab.getEvent(temp));
    }
    keys=data.keys();      /* und dann alle aktiven der Instanz */
    while (keys.hasMoreElements()){
      temp=(String)keys.nextElement();
      result.set(temp,getEvent(temp));
    }
    return result;
   }

  public String toString(){
    String result="";
    Enumeration enum=data.keys();
    result+="( ";
    while (enum.hasMoreElements()){
      result+=(String)enum.nextElement();
      result+=" ";
    }
    result+=")";
    return result;
  }

}



