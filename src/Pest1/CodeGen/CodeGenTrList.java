package codegen;

public class CodeGenTrList 
{
  CodeGenTrans head;
  CodeGenTrList tail;
  
  public CodeGenTrList() 
  {
    head = null;
    tail = null;
  }
  

  public CodeGenTrList(CodeGenTrans t)
  {
    head = t;
    tail = null;
  }
  

  public CodeGenTrList(CodeGenTrans t, CodeGenTrList l)
  {
    head = t;
    tail = l;
  }

  CodeGenTrList append(CodeGenTrans t)
  {
    if (tail != null) {
      return new CodeGenTrList(head, tail.append(t));
    } else {
      return new CodeGenTrList(head, new CodeGenTrList(t));
    }
  }
}
