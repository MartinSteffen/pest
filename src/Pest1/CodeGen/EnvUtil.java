/**
* This file serves as a library for frequently used functions.
*/
package codegen;

public class EnvUtil {

  /**
   * This method is generating traces.
   * @exception IOException self-explanatory.
   */
  public void tracePretty(OutputStreamWriter f, SymbolTable s)
       throws IOException
  {
    int i;

    f.write("(\n  (\n    (\n");
    for (i = 0; i < s.statenames.length; ++i) {
      if(s.post_states[i]) {
	f.write("      " + s.statenames[i] + "\n");
      }
    }
    f.write("    )\n  (\n");
    for (i = 0; i < eventnames.length; ++i) {
      if(s.post_events[i]) {
	f.write("      " + s.eventnames[i] + "\n");
      }
    }
    f.write("    )\n  (\n");
    for (i = 0		; i < s.condition_names.length; ++i) {
      if(s.post_cond[i]) {
	f.write("      " + s.condition_names[i] + "\n");
      }
    }
    f.write("    )\n  )\n)");
  }


  /**
   * This method is generating traces.
   * @exception IOException self-explanatory.
   */
  public void traceSimple(OutputStreamWriter f, SymbolTable s)
       throws IOException
  {
    int i;

    f.write("( (");
    for (i = 0; i < s.statenames.length; ++i) {
      if(s.post_states[i]) {
	f.write(" " + s.statenames[i] + " ");
      }
    }
    f.write(" )  ( ");
    for (i = 0; i < s.eventnames.length; ++i) {
      if(s.post_events[i]) {
	f.write(" " + s.eventnames[i] + " ");
      }
    }
    f.write(" ) ( ");
    for (i = 0; i < s.condition_names.length; ++i) {
      if(s.post_cond[i]) {
	f.write(" " + s.condition_names[i] + " ");
      }
    }
    f.write(" ) )\n");
  }

}
