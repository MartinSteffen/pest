package simu;

import absyn.*;
import java.awt.*;


class TrView extends Checkbox{

   Tr transition=null;

   public TrView(Tr t){
     super("",false);
     transition=t;
     String label=null;
     TrAnchor von=t.source;
     TrAnchor nach=t.target;
     String vontext=null;
     String nachtext=null;
     if (von instanceof absyn.Statename){
       vontext=((Statename)von).name;
     }
     if (von instanceof absyn.Conname){
       vontext=((Conname)von).name;
     }
     if (nach instanceof absyn.Statename){
       nachtext=((Statename)nach).name;
     }
     if (nach instanceof absyn.Conname){
       nachtext=((Conname)nach).name;
     }
     label=vontext+"->"+nachtext; /*+TLabel?*/
     setLabel(label);
   } 
}    
