package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testStates.java,v 1.3 1998-12-07 11:42:38 swtech11 Exp $
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
    msg.addWarning(3,"bei allen States");
    return true;
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
    for(PathList p=sc.cnames; p!=null; p=p.tail){
       if (Pfad2.contains(p.head.name)) { msg.addError(300,"State: "+p.head.name);}
                                else {Pfad2.addElement(p.head.name);};
        }; 
    };

    void vergleiche_Pfade(){
    Vector p2=(Vector)Pfad2.clone();
    for (int i=0; i<Pfad1.size(); i++){
      p2.removeElement(Pfad1.elementAt(i)); };
  
    for(int i=0; i<p2.size(); i++) { msg.addWarning(302,"State: "+(String)p2.elementAt(i));};
    };



}
