package simu;

import java.util.*; 

import absyn.*;
import util.*;

class BooleanTabelle extends Object{
  Hashtable data=null;

  public BooleanTabelle(){
    data=new Hashtable();
  }


  public void insert(String elements){
    String inline=elements.trim();
    String temp=null;
    if (inline.length()!=0){
      StringTokenizer strtok=new StringTokenizer(inline," ");
      while (strtok.hasMoreTokens()){
	temp=strtok.nextToken();
	setTrue(temp);
      }
    }
  }
      

  public BooleanTabelle filterConditions(BooleanTabelle tab){
    BooleanTabelle result=new BooleanTabelle();
    Enumeration keys=null;
    keys=(data).keys();
    String tempvar=null;
    while (keys.hasMoreElements()){
      tempvar=(String)keys.nextElement();
      if (isTrue(tempvar)){
	result.setTrue(tempvar);
      }
    }
    Enumeration tab_keys=(tab.data).keys();
    String tab_element=null;
    while (tab_keys.hasMoreElements()){
      tab_element=(String)tab_keys.nextElement();
      if (isIn(tab_element)){
	if (isTrue(tab_element)){
	  result.setTrue(tab_element);
	}
      }
      else{
	result.setTrue(tab_element);
      }
    }
    return result;
  }
      
  boolean isIn(String name){
    boolean result=false;
    Enumeration enum=data.keys();
    String temp=null;
    while ((enum.hasMoreElements())&&(result!=true)){
      temp=(String)enum.nextElement();
      result=name.equals(temp);
    }
    return result;
  }

  public boolean isTrue(Bvar b){
    String name=b.var;
    return (isTrue(name));
  }

  public boolean isFalse(Bvar b){
    return (!(isTrue(b)));
  }

  public boolean isTrue(String name){
    boolean result=false;
    Enumeration enum=data.keys();
    String tempkey=null;
    String tempvalue=null;
    while (enum.hasMoreElements()){
      tempkey=(String)enum.nextElement();
      if (tempkey.equals(name)){
	tempvalue=(String)data.get(tempkey);
	result=(tempvalue.equals("true"));
	break;
      }
    }
    return result;
  }
 
  public boolean isFalse(String name){
    return (!(isTrue(name)));
  }

  public void setTrue(Bvar b){
    String name=b.var;
    data.put(name,(new String("true")));
  }

  public void setFalse(Bvar b){
    String name=b.var;
    data.put(name,(new String("false")));
  }
  
  public void setTrue(String name){
    if (isIn(name)){
      Enumeration enum=data.keys();
      String temp=null;
      while (enum.hasMoreElements()){
	temp=(String)enum.nextElement();
	if (temp.equals(name)){
	  data.put(temp,(new String("true")));
	  break;
	}
      }
    }
    data.put(name,(new String("true")));
  }

  public void setFalse(String name){
    if (isIn(name)){
      Enumeration enum=data.keys();
      String temp=null;
      while (enum.hasMoreElements()){
	temp=(String)enum.nextElement();
	if (temp.equals(name)){
	  data.put(temp,(new String("false")));
	  break;
	}
      }
    }
    data.put(name,(new String("false")));
  }


  public BooleanTabelle verbinde(BooleanTabelle tab) throws RacingException{
    BooleanTabelle result=new BooleanTabelle();
    boolean racing_occured=false;;
    Bvar racing_var=null;
    String tempkey=null;
    String tempvalue=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); 
    while (keys.hasMoreElements()){
      tempkey=(String)keys.nextElement();
      if (tab.isTrue(tempkey)){
	result.setTrue(tempkey);
      }
      else{
	result.setFalse(tempkey);
      }
    }
    keys=data.keys();                   /* und dann alle aktiven der Instanz, aber nur, wenn */
    while (keys.hasMoreElements()){     /* wenn nicht schon vorher gesetzt*/
      tempkey=(String)keys.nextElement();
      tempvalue=(String)(data.get(tempkey));
      /* Wurde tempkey schon vorher von tab nach result gesetzt? */
      if (result.isIn(tempkey)){
	/*Wenn ja, dann pruefen, ob der angestrebte Wert bei beiden das gleiche ergibt*/
        String valueAusTab=(String)result.data.get(tempkey);
	if (((result.isTrue(tempkey))&&(isTrue(tempkey)))||((result.isFalse(tempkey))&&(isFalse(tempkey)))){
	  /* hier muss nichts gemacht werden, der Wert ist schon gesetzt...*/
	}
	else{
	  /* Racing-Condition: Zwei unterschiedliche Aktionen wollten quasi gleichzeitig*/
	  /* eine boolesche Variable unterschiedlich setzen.... Finden wir nicht schoen..*/
	  racing_occured=true;
	  racing_var=new Bvar(tempkey);
	}
      }
      else{
	/* tempkey gibt es noch nicht, also einfach ´reinkopieren ...*/
	if (isTrue(tempkey)){
	  result.setTrue(tempkey);
	}
	else{
	  result.setFalse(tempkey);
	}
      }
    }
    if (racing_occured){
      throw (new RacingException(result,racing_var));
    }
    return result;
  }

  String debug(){
    String result="";
    Enumeration enum=data.keys();
    String temp=null;
    while (enum.hasMoreElements()){
      temp=(String)enum.nextElement();
      result+=temp+"="+(String)data.get(temp);
    }
    return result;
  }

  public String toString(){
    String result="";
    result+="( ";
    Enumeration enum=data.keys();
    while (enum.hasMoreElements()){
      result+=(String)enum.nextElement();
      result+=" ";
    }
    result+=")";
    return result;
  }


}
