package simu;


import java.util.Enumeration;
import java.util.Hashtable;
import absyn.*;

public class BooleanTabelle extends Object{
  Hashtable data=null;

  public BooleanTabelle(){
    data=new Hashtable();
  }

  public boolean isTrue(Bvar b){
    String value=(String)data.get(b);
    if (value!=null){
      return (value.equals("true"));
    }
    else{
      return false;
    }
  }
    
  public void setTrue(Bvar b){
    data.put(b,(new String("true")));
  }

  public void setFalse(Bvar b){
    data.put(b,(new String("false")));
  }

   public BooleanTabelle verbinde(BooleanTabelle tab) throws RacingException{
    BooleanTabelle result=new BooleanTabelle();
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
	  throw (new RacingException());
	}
      }
      else{
	/* tempkey gibt es noch nicht, also einfach ´reinkopieren ...*/
	(result.data).put(tempkey,tempvalue);
      }
    }
    return result;
  }
}
