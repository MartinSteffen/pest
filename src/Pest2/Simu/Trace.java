package simu;


import java.io.*;
import java.util.*;


public class Trace extends Object{

  Vector statusse=null;
  int pos=0;

  public Trace(){
    statusse=new Vector();
    pos=0;
  }

  public int size(){
    return statusse.size();
  }

  public void add(Status status){
    statusse.addElement(status);
  }

  public Status prev(){
    Status result=new Status();
    if (pos>0){
      pos--;
      result=(Status)statusse.elementAt(pos);
    }
    return result;
  }
  

  public Status next(){
    Status result=new Status();
    if (pos<statusse.size()){
      pos++;
      result=(Status)statusse.elementAt(pos);
    }
    return result;
  }


  public Status go(int position){
    Status result=new Status();
    pos=position;
    if ((pos<=0)||(pos<statusse.size())){
      result=(Status)statusse.elementAt(pos);
    }
    return result;
  }

  public void read(BufferedReader r) throws IOException{
    String status=null;
    String states=null;
    String booleans=null;
    String events=null;
    int pos=0;
    int brace_level=0;
    String inline=new String();
    /* Lies alles in einen String - nicht schoen, aber einfach */
    while (r.ready()){
      inline=inline+r.readLine();
      System.out.println("Lese Zeile...");
    }
    /* Aeussere Klammern entfernen */
    String trace=(inline.substring(inline.indexOf("(")+1,inline.lastIndexOf(")"))).trim();
    
    System.out.println("Trace:" +trace);
    pos=0;
    brace_level=0;
    char c;
    do{
      c=trace.charAt(pos);
      if (c=='('){
	brace_level++;
      }
      if (c==')'){
	brace_level--;
      }
      pos++;
    }
    while(brace_level!=0);
    status=trace.substring(0,pos);
    status=(status.substring(status.indexOf("(")+1,status.lastIndexOf(")"))).trim();
    
    
    pos=0;
    brace_level=0;
    do{
      c=status.charAt(pos);
      if (c=='('){
	brace_level++;
      };
      if (c==')'){
	brace_level--;
      }
      pos++;
    }
    while(brace_level!=0);
    states=status.substring(0,pos);
    states=(states.substring(states.indexOf("(")+1,states.lastIndexOf(")"))).trim();

    pos=status.indexOf('(',pos);
    status=status.substring(pos);
    pos=0;
    brace_level=0;
    do{
      c=status.charAt(pos);
      if (c=='('){
	brace_level++;
      };
      if (c==')'){
	brace_level--;
      }
      pos++;
    }
    while(brace_level!=0);
    events=status.substring(0,pos);
    events=(events.substring(events.indexOf("(")+1,events.lastIndexOf(")"))).trim();
    pos=status.indexOf('(',pos);
    status=status.substring(pos);
    pos=0;
    brace_level=0;
    do{
      c=status.charAt(pos);
      if (c=='('){
	brace_level++;
      };
      if (c==')'){
	brace_level--;
      }
      pos++;
    }
    while(brace_level!=0);
    booleans=status.substring(0,pos);
    booleans=(booleans.substring(booleans.indexOf("(")+1,booleans.lastIndexOf(")"))).trim();
    
    Status stat=new Status();
    //stat.states=stat.states.insert(states);
    //stat.events=stat.events.insert(events);
    //stat.booleans=stat.booleans.insert(booleans);
    
    StringTokenizer strtok=new StringTokenizer(inline,"() \n\t");
    while (strtok.hasMoreTokens()){
      System.out.println("Token: "+strtok.nextToken());
    }

  }
    

  public String toString(){
    String result="";
    Status temp=null;
    Enumeration enum=statusse.elements();
    while (enum.hasMoreElements()){
      temp=(Status)enum.nextElement();
      result+=temp.toString()+"\n";
    }
    return result;
  }
      
}    








