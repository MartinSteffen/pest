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
      System.err.print(tab_element);
      if (isIn(tab_element)){
	if (isTrue(tab_element)){
	  result.setTrue(tab_element);
	  System.err.println(" auf true!!!");
	}
	else{
	  System.err.println(" auf false!!!");
	}
      }
      else{
	result.setTrue(tab_element);
	System.err.println(" auf true!! (sowieso...)");
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
   
  public boolean isTrue(String name){
    String value=(String)data.get(name);
    if (value!=null){
      return (value.equals("true"));
    }
    else{
      return false;
    }
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
    data.put(name,(new String("true")));
  }

  public void setFalse(String name){
    data.put(name,(new String("false")));
  }


  public BooleanTabelle verbinde(BooleanTabelle tab) throws RacingException{
    BooleanTabelle result=new BooleanTabelle();
    boolean racing_occured=false;;
    Bvar racing_var=null;
    String tempkey=null;
    String tempvalue=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle "Wahren" aus tab nach result...*/
    while (keys.hasMoreElements()){
      tempkey=(String)keys.nextElement();
      if (isTrue(tempkey)){
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
	if (valueAusTab.equals(tempvalue)){
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
	(result.data).put(tempkey,tempvalue);
      }
    }
    if (racing_occured){
      throw (new RacingException(result,racing_var));
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
