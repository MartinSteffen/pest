package codegen;

public class util {
  /**
   * Indentiation string.
   */
  private static final String indentLevel = "  ";

  /**
   * This method will write a line to a file and pretty-print
   * it.
   */
  static String printlnPP(int lvl, String s)
  {
    int i;
    String t = new String();

    for (i = lvl; i > 0; --i) {
      t += indentLevel;
    }
    return (t + s + "\n");
  }
}
