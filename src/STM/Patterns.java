package stm;

interface Patterns {

  // static final String STRING = "(\"[a-zA-Z_\\-,\\s0-9\\*]+\")";
  // static final String STRINGSET = "\\{([\"a-zA-Z_\\-,\\s0-9\\.]*)\\}";
  // static final String MK_HA = "\\s*mk_ha\\((.*)\\)\\s*";
  // static final String MK_SYSTEM = "mk_system\\(" + MK_HA + "," + MK_HA + ",.*\\)";

  static final String STRING = "(\"[^\"]*\")";
  static final String MAPSET = "\\[([^\\]]*)\\]";
  static final String STRINGSET = "\\{([^\\}]*)\\}";
  static final String MK_HA = "\\s*mk_ha\\((.*)\\)\\s*";
  static final String MK_SYSTEM = "mk_system\\(" + MK_HA + "," + MK_HA + ",.*\\)";

}
