package codegen;

/**
 * This class defines an auxilluary class for creating
 * code for transitions.
 *
 * @author Marcel Kyas
 * @version $Id: CodeGenTrans.java,v 1.3 1999-02-28 17:34:09 swtech25 Exp $
 */
public class CodeGenTrans {
  /**
   * Number of transition.
   */
  int number;

  /**
   * Name of the source state.
   */
  String source;

  /**
   * Code of the guard.
   */
  String guard;

  /**
   * Code for the action.
   */
  String action;

  /**
   * Name of the target.
   */
  String target;

  public CodeGenTrans() {
    number = 0;
    source = null;
    target = null;
    guard = null;
    action = null;
  }

  public CodeGenTrans(int n, String s, String t, String g, String a)
  {
    number = n;
    source = s;
    target = t;
    guard = g;
    action = a;
  }
}

