package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testStates.java,v 1.6 1998-12-09 14:28:53 swtech11 Exp $
 */
class testStates extends modelCheckBasics{
  private Vector Pfad1=new Vector();
  private Vector Pfad2=new Vector();

  testStates(Statechart _s, modelCheckMsg _m) {
    super(_s,_m);
  }
  boolean check() {
    erstelle_Pfad2();
    erstelle_Pfad1();
    vergleiche_Pfade();
    pruefeState(sc.state);
    msg.addWarning(3,"bei allen States");
    return (msg.getErrorNumber()==0);;
  }

    void erstelle_Pfad1(){
    Pfad1=getPathListFromState(sc.state);
    Vector p1=(Vector)Pfad1.clone();
    Vector p2=(Vector)Pfad1.clone();
	for (int i=0; i<p1.size(); i++){

           int j=0;
           if (!Pfad2.contains(p1.elementAt(i)))
               {msg.addError(303,"State: "+(String)p1.elementAt(i));};

           for (;p2.contains(p1.elementAt(i)) ;j++){
               p2.removeElement(p1.elementAt(i));};
           if (j>1) {
            msg.addError(301,"State: "+(String)p1.elementAt(i)+" Anzahl: "+j);}
	};
    }

    void erstelle_Pfad2(){
    String s;
    for(PathList p=sc.cnames; p!=null; p=p.tail){
       Path p_=p.head;
       s=p_.head;
       p_=p_.tail;
       for(;p_!=null; p_=p_.tail) {s=s+"."+p_.head;};
   
       if (Pfad2.contains(s)) { msg.addError(300,"State: "+s);}
                                else {Pfad2.addElement(s);};
        }; 
    };

    void vergleiche_Pfade(){
    Vector p2=(Vector)Pfad2.clone();
    for (int i=0; i<Pfad1.size(); i++){
      p2.removeElement(Pfad1.elementAt(i)); };
  
    for(int i=0; i<p2.size(); i++) { msg.addWarning(302,"State: "+(String)p2.elementAt(i));};
    };
 
    

    void pruefeState(State s){
    if (s instanceof Or_State) {
	   if (((Or_State)s).substates==null) {msg.addError(305,"State: "+s.name.name);}
	   else {
	        if (Anzahl_States(((Or_State)s).substates)==1) {msg.addWarning(304,"State: "+s.name.name);};
                nextStateInStateList(((Or_State)s).substates); };};
    if (s instanceof And_State) {
           if (((And_State)s).substates==null) {msg.addError(306,"State: "+s.name.name);}
	   else {
            if (Anzahl_States(((And_State)s).substates)==1) {msg.addWarning(307,"State: "+s.name.name);};
            nextStateInStateList(((And_State)s).substates); };};
    }

  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State) {pruefeState ((Or_State)sl.head); }
    if (sl.head instanceof And_State) {pruefeState ((And_State)sl.head); }
    if (sl.tail != null) { nextStateInStateList(sl.tail); }
  }

    int Anzahl_States(StateList sl){
      int i=1; 
	 for (; sl.tail!=null; sl=sl.tail) {i++;};

      return i;};
}
