package simu;



import absyn.*;
import java.util.Vector;
import java.util.Enumeration;
import java.io.BufferedReader;

class Status extends Object{
  StateTabelle states=null;
  TransitionTabelle transitions=null;
  EventTabelle events=null;
  BooleanTabelle booleans=null;

  public Status(){
    states=new StateTabelle();
    transitions=new TransitionTabelle();
    events=new EventTabelle();
    booleans=new BooleanTabelle();
  }
  

  public Status filterConditions(Status s){
    Status result=new Status();
    result.states=states;
    result.transitions=transitions;
    result.events=events;
    result.booleans=booleans.filterConditions(s.booleans);
    return result;
  }

  public Status verbinde(Status s) throws RacingException{
    Status result=new Status();
    result.states=states.verbinde(s.states);
    result.transitions=transitions.verbinde(s.transitions);
    result.events=events.verbinde(s.events);
    result.booleans=booleans.verbinde(s.booleans);
    return result;
  }

  public Status verbinde(Vector v) throws RacingException{
    Status result=new Status();
    result=result.verbinde(this);
    Status temp=null;
    Enumeration enum=v.elements();
    while (enum.hasMoreElements()){
      temp=(Status)enum.nextElement();
      result=result.verbinde(temp);
    }
    return result;
  }
   
  public void debug(){
    states.debug();
    transitions.debug();
  }

  public String toString(){
    String result="";
    result+="(";
    result+=states.toString();
    result+=events.toString();
    result+=booleans.toString();
    result+=")";
    return result;
  }


}





