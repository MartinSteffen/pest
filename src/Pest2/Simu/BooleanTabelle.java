package simu;


import java.util.Enumeration;
import java.util.Hashtable;

import java.util.StringTokenizer; 

import absyn.*;

public class BooleanTabelle extends Object{
  Hashtable data=null;

  public BooleanTabelle(){
    data=new Hashtable();
  }


  public BooleanTabelle insert(String s){
    Bvar b=null;
    BooleanTabelle result=new BooleanTabelle();
    StringTokenizer strtok=new StringTokenizer(s," ");
    while (strtok.hasMoreTokens()){
      /* hier BVars einfuegen */
    }
    return result;
  }
    
      

  public BooleanTabelle filterConditions(BooleanTabelle tab){
    BooleanTabelle result=new BooleanTabelle();
    Enumeration keys=null;
    keys=(data).keys();
    Bvar tempvar=null;
    while (keys.hasMoreElements()){
      tempvar=(Bvar)keys.nextElement();
      if (isTrue(tempvar)){
	result.setTrue(tempvar);
      }
    }
    Enumeration tab_keys=(tab.data).keys();
    Bvar tab_element=null;
    while (tab_keys.hasMoreElements()){
      tab_element=(Bvar)tab_keys.nextElement();
      if (data.containsKey(tab_element)){
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
      

  public boolean isTrue(Bvar b){
    String name=b.var;
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
    Bvar tempkey=null;
    String tempvalue=null;
    Enumeration keys=null;
    keys=(tab.data).keys(); /* Zunaechst alle "Wahren" aus tab nach result...*/
    while (keys.hasMoreElements()){
      tempkey=(Bvar)keys.nextElement();
      tempvalue=(String)(tab.data.get(tempkey));
      (result.data).put(tempkey,tempvalue);
    }
    keys=data.keys();                   /* und dann alle aktiven der Instanz, aber nur, wenn */
    while (keys.hasMoreElements()){     /* wenn nicht schon vorher gesetzt*/
      tempkey=(Bvar)keys.nextElement();
      tempvalue=(String)(data.get(tempkey));
      /* Wurde tempkey schon vorher von tab nach result gesetzt? */
      if ((result.data).containsKey(tempkey)){
	/*Wenn ja, dann pruefen, ob der angestrebte Wert bei beiden das gleiche ergibt*/
        String valueAusTab=(String)result.data.get(tempkey);
	if (valueAusTab.equals(tempvalue)){
	  /* hier muss nichts gemacht werden, der Wert ist schon gesetzt...*/
	}
	else{
	  /* Racing-Condition: Zwei unterschiedliche Aktionen wollten quasi gleichzeitig*/
	  /* eine boolesche Variable unterschiedlich setzen.... Finden wir nicht schoen..*/
	  racing_occured=true;
	  racing_var=tempkey;
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
