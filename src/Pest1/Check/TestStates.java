package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestStates.java,v 1.2 1998-12-15 17:51:40 swtech00 Exp $
 */

/** Diese Testklasse testet, ob alle Statenamen deklariert worden sind, 
    <br>ob die Deklarierten eindeutig sind und ob sie alle verwendet werden.*/

class TestStates extends ModelCheckBasics{
  private Vector Pfad1=new Vector();
  private Vector Pfad2=new Vector();

  TestStates(Statechart _s, ModelCheckMsg _m) {
    super(_s,_m);
  }

 /** Die Methode check ueberprueft die Statechart auf Fehler bzgl. States.*/

  boolean check() {
    int m=msg.getErrorNumber();
    erstelle_Pfad2();
    erstelle_Pfad1();
    vergleiche_Pfade();
    pruefeState(sc.state);
    msg.addWarning(3,"bei allen States");
    return ((msg.getErrorNumber()-m)==0);;
  }

   /** Die Methode erstellt einen Vector Pfad1 der verwendeten States, 
   <br>dabei wird ueberprueft, ob alle Namen eindeutig sind und
   <br> ob die States deklariert worden sind.*/

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

/** Die Methode erstellt einen Vector Pfad2 der deklarierten States, 
	<br>dabei wird ueberprueft, ob alle Namen eindeutig sind.*/

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

   /** Die Vectoren Pfad1 und Pfad2 werden verglichen. 
<br>Wenn States aus dem Vector Pfad2 nicht verwendet werden, wird gewarnt.*/
 
    void vergleiche_Pfade(){
    Vector p2=(Vector)Pfad2.clone();
    for (int i=0; i<Pfad1.size(); i++){
      p2.removeElement(Pfad1.elementAt(i)); };
  
    for(int i=0; i<p2.size(); i++) { msg.addError(302,"State: "+(String)p2.elementAt(i));};
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
