package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestStates.java,v 1.7 1999-01-08 17:14:41 swtech11 Exp $
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
    pruefeState(sc.state, null, "");
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
<br>Wenn States aus dem Vector Pfad2 nicht verwendet werden, wird ein Fehler ausgegeben.*/
 
    void vergleiche_Pfade(){
    Vector p2=(Vector)Pfad2.clone();
    for (int i=0; i<Pfad1.size(); i++){
      p2.removeElement(Pfad1.elementAt(i)); };
  
    for(int i=0; i<p2.size(); i++) { msg.addError(302,"State: "+(String)p2.elementAt(i));};
    };
 
    
    /** Es wird ueberprueft, ob ein Or- bzw. And-State andere States enthaelt.
   <br> Wenn kein innere State vorhanden ist, wird ein Fehler ausgegeben. Bei einem inneren State wird gewarnt.
    */
    
    void pruefeState(State s, State _s, String p){
	//System.out.println("pS "+p);
    if (s instanceof Or_State) {
	   if (((Or_State)s).substates==null) {msg.addError(305,"State: "+s.name.name);}
	   else {
                p=getAddPathPart(p, s.name.name);
	        if (Anzahl_States(((Or_State)s).substates)==1) {msg.addWarning(304,"State: "+p);};
                Vector dc=defaultcon((Or_State)s,p);
                navStateInStateList(((Or_State)s).substates, s, p, dc); };};
    if (s instanceof And_State) {
           if (((And_State)s).substates==null) {msg.addError(306,"State: "+s.name.name);}
	   else {
            p=getAddPathPart(p, s.name.name);
            if (Anzahl_States(((And_State)s).substates)==1) {msg.addWarning(307,"State: "+p);};
            navStateInStateList(((And_State)s).substates, s, p, new Vector()); };};
    }

  void navStateInStateList(StateList sl, State _s, String p, Vector dc) {
      boolean b=true;
     
      //System.out.println("nav State "+sl.head.name.name);
      for (int i=0; ((i<dc.size()) && b); i++){
	  //System.out.println("nav vergleiche "+(String)dc.elementAt(i));
	  if (((String)dc.elementAt(i)).equals(sl.head.name.name)) {
	      //System.out.println("nav entfernen "+(String)dc.elementAt(i));
dc.removeElementAt(i);};};


      
    if (sl.head instanceof Or_State) {pruefeState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {pruefeState ((And_State)sl.head, _s, p); }
    if (sl.tail != null) { navStateInStateList(sl.tail, _s, p, dc); }
    if (sl.tail ==null) {if (dc.size()>0) {
               for (;dc.size()!=0; dc.removeElementAt(0)) {
                  msg.addError(312,"Defaultconnector "+(String)dc.elementAt(0)+" in State "+p);};};
  };}

    /** Berechnet die Anzahl der States die in StateList sl vorhandne sind. */
    int Anzahl_States(StateList sl){
      int i=1; 
	 for (; sl.tail!=null; sl=sl.tail) {i++;};

      return i;};

    Vector defaultcon(Or_State _s, String p) {
     Vector v=new Vector();
     StatenameList sl=_s.defaults;
     if (sl!=null) { 
//System.out.println("DFC Pfad "+p);

         for (;sl.tail!=null; sl=sl.tail ) {
	     //System.out.println("DFC State 2 "+sl.head.name);
            for(int i=0; i<v.size();i++){
              if (((String)v.elementAt(i)).equals(sl.head.name)) {msg.addError(313,"Defaultcon: "+sl.head.name+" im State "+p);}
	      else {v.addElement(sl.head.name);};};
	    if (v.size()==0) {v.addElement(sl.head.name);}
              };
    int s=v.size();
    for(int i=0; i<s; i++){
	//System.out.println("drin "+(String)v.elementAt(i)+" draussen "+sl.head.name);
     if (((String)v.elementAt(i)).equals(sl.head.name)) {msg.addError(313,"Defaultcon: "+sl.head.name+" im State "+p);}
	      else {v.addElement(sl.head.name);
	      //System.out.println("DFC State 1 "+sl.head.name);
};
};
    if (s==0) {v.addElement(sl.head.name);};


     };
     if (sl==null) {msg.addError(314,"State "+p);};
     if (v.size()>1)  {msg.addError(315,"State "+p);};
     //System.out.println("DFV size "+v.size());
     return v;
    }



    /**   gibt den kompletten Pfad des States _n zurueck, d.h. _p + "." +_n
	  <br> falls eine Statename aus "" besteht wird gewarnt.*/
  String getAddPathPart(String _p, String _n) {   // _p Pfad, _n Name des States
    String _np = new String();
    if (_p.equals("")) { _np = _n; } else { _np = _p + ts + _n; }
    if (_n=="") {msg.addWarning(308,"State: "+_p);};
    return _np;
  }
}



