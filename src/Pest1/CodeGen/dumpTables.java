/**
 * This class will dump the tables of the automaton.
 */
package codegen;

import absyn.*;
import java.io.*;
import java.util.*;

/**
 * This class will dump the tables of our code.  Internallz
 * we will work with numerical values.  This is faster and
 * more efficient.  But we want to provide the environment
 * an interface to the original textual representations of
 * states, events and conditions.
 *
 * @version $Id: dumpTables.java,v 1.10 1999-01-25 14:43:58 swtech25 Exp $
 * @author Marcel Kyas
 */
public class dumpTables
{
	/**
	 * This is our reserved event keyword.  If it occures,
	 * the generated code will clean up and exit.
	 * Obviously, EVENT_$$stop$$ is a valid identifier in
	 * Java.  You may want to change this, if your
	 * Compiler chokes on this one.
	 */
	public static final String endEvent = "$$stop$$";


	/*
	 * These are the generic code fragments we are going to create.
	 */
	private final String[] state_head = {
		"\tstatic final String[] statenames = {"
	};

	private final String[] state_tail = {
		"\t};",
		""
	};

	private final String[] event_head = {
		"\tstatic final String[] eventnames = {"
	};

	private final String[] event_tail = {
		"\t};",
		""
	};

	private final String[] cond_head = {
		"\tstatic final String[] condition_names = {"
	};

	private final String[] cond_tail = {
		"\t};",
		""
	};

	private final String[] head = {
		"/*",
		" * This code was automatically generated by codegen",
		" */",
		"",
		"import java.io.*;",
		"",
		"/**",
		" * This class maintains the lists of symbols and generates",
		" * traces.  It also contains the finalizeStep method.",
		" */",
		"public class SymbolTable {"
	};

	private final String[] tail = {
		"\tstatic boolean[] his_states = new boolean[statenames.length];",
		"\tstatic boolean[] pre_states = new boolean[statenames.length];",
		"\tstatic boolean[] post_states = new boolean[statenames.length];",
		"\tstatic boolean[] pre_events = new boolean[eventnames.length];",
		"\tstatic boolean[] post_events = new boolean[eventnames.length];",
		"\tstatic boolean[] pre_cond = new boolean[condition_names.length];",
		"\tstatic boolean[] post_cond = new boolean[condition_names.length];",
		"",
		"\t/**",
		"\t * This method is generating traces.",
		"\t * @exception IOException self-explanatory.",
		"\t */",
		"\tpublic static void trace(OutputStreamWriter f) throws IOException {",
		"\t\tint i;",
		"",
		"\t\tf.write(\"(\\n (\");",
		"\t\tfor (i = 0; i < statenames.length; ++i) {",
		"\t\t\tif(post_states[i]) {",
		"\t\t\t\tf.write(statenames[i] + \" \");",
		"\t\t\t}",
		"\t\t}",
		"\t\tf.write(\")\\n (\");",
		"\t\tfor (i = 0; i < eventnames.length; ++i) {",
		"\t\t\tif(post_events[i]) {",
		"\t\t\t\tf.write(eventnames[i] + \" \");",
		"\t\t\t}",
		"\t\t}",
		"\t\tf.write(\")\\n (\");",
		"\t\tfor (i = 0; i < condition_names.length; ++i) {",
		"\t\t\tif(post_cond[i]) {",
		"\t\t\t\tf.write(condition_names[i] + \" \");",
		"\t\t\t}",
		"\t\t}",
		"\t\tf.write(\")\\n\");",
		"\t}",
		"",
		"\t/**",
		"\t * This method updates the different fields after",
		"\t * the completion of a step of the automaton",
		"\t */",
		"\tpublic static void finalizeStep() {",
		"\t\tpre_cond = post_cond;",
		"\t\tpost_cond = new boolean[condition_names.length];",
		"\t\tpre_events = post_events;",
		"\t\tpost_events = new boolean[eventnames.length];",
		"\t\this_states = pre_states;",
		"\t\tpre_states = post_states;",
		"\t\tpost_states = new boolean[statenames.length];",
		"\t}",
		"}"
	};


	private final float loadFactor = (float) 0.7;

	/*
	 * The private members.
	 */
	private final static SEvent SEndEvent = new SEvent(endEvent);
	private Statechart S;
	private int state_curr_key = 0;
	private int event_curr_key = 0;
	private int cond_curr_key = 0;
	Hashtable states_sym = new Hashtable(1024, loadFactor);
	Hashtable events_sym = new Hashtable(1024, loadFactor);
	Hashtable cond_sym   = new Hashtable(1024, loadFactor);
	Path[]   states;
	SEvent[] events;
	Bvar[] cond;

	/**
	 * This is the only constructor for this class.  Do not use any
	 * other automatically generated constructors.
	 */
	public dumpTables(Statechart s)
	{
		S = s;
	}


	/**
	 * This method returns a string from path seperated by
	 * ch.
	 */
	private static String generateName(Path path, String ch)
	{
		String symbol = new String(path.head);
		Path name = path.tail;
		while (name != null) {
			symbol += (ch + name.head);
			name = name.tail;
		}
		return symbol;
	}


	/**
	 * This method returns a string from an event
	 * for representation as an integer.
	 */
	public static String generateSymEvent(SEvent e)
	{
		return "EVENT_" + e.name;
	}


	/**
	 * This method returns a string from an event
	 * for representation as an integer.
	 */
	public static String generateSymBVar(Bvar b)
	{
		return "BVAR_" + b.var;
	}


	/**
	 * Creates symbol names PathList and hashes them in Table.
	 */
	private void generateStateTable()
	{
		PathList current;
		Integer key;

		current = S.cnames;
		while (current != null) {
			// put them into Hash table, but check first.
			if (!states_sym.containsKey(current.head)) {
				key = new Integer(state_curr_key);
				states_sym.put(current.head, key);
				state_curr_key++;
			}
			current = current.tail;
		}
	}


	/**
	 * Creates symbol names PathList and hashes them in Table.
	 */
	private void generateEventTable()
	{
		SEventList current = S.events;
		Integer key;

		// Hash the stop event first:
		key = new Integer(event_curr_key);
		events_sym.put(SEndEvent, key);
		event_curr_key++;
		while (current != null) {
			if (!events_sym.containsKey(current.head)) {
				key = new Integer(event_curr_key);
				events_sym.put(current.head, key);
				event_curr_key++;
			}
			current = current.tail;
		}
	}


	/**
	 * Creates symbol names PathList and hashes them in Table.
	 */
	private void generateCondTable()
	{
		BvarList current = S.bvars;
		Integer key;

		while (current != null) {
			if (!cond_sym.containsKey(current.head)) {
				key = new Integer(cond_curr_key);
				cond_sym.put(current.head, key);
				cond_curr_key++;
			}
			current = current.tail;
		}
	}


	/**
	 * Now compute the inverse of the state hash table
	 */
	private void generateStateArray()
	{
		Enumeration e;
		Path p;
		Integer i;

		states = new Path[states_sym.size()];
		e = states_sym.keys();
		while (e.hasMoreElements()) {
			p = (Path) e.nextElement();
			i = (Integer) states_sym.get(p);
			states[i.intValue()] = p;
		}
	}


	/**
	 * Create event array.
	 */
	private void generateEventArray()
	{
		Enumeration e;
		SEvent p;
		Integer i;

		events = new SEvent[events_sym.size()];
		e = events_sym.keys();
		while (e.hasMoreElements()) {
			p = (SEvent) e.nextElement();
			i = (Integer) events_sym.get(p);
			events[i.intValue()] = p;
		}
	}


	/**
	 * Create condition array.
	 */
	private void generateCondArray()
	{
		Enumeration e;
		Bvar p;
		Integer i;

		cond = new Bvar[cond_sym.size()];
		e = cond_sym.keys();
		while (e.hasMoreElements()) {
			p = (Bvar) e.nextElement();
			i = (Integer) cond_sym.get(p);
			cond[i.intValue()] = p;
		}
	}


	/**
	 * Dump the state symbol table.
	 * @exceptions IOException self-explanatory.
	 */
	private void dumpStateSymbolTable(OutputStreamWriter f)
		throws IOException
	{
		int i;

		f.write("\t/**\n");
		f.write("\t * The numbers belonging to the textual represen" +
			"tations\n");
		f.write("\t * of states.\n\t */\n");
		for(i = 0; i < states.length; ++i) {
			f.write("\tpublic static final int " +
				generateSymState(states[i]) +
				" = " + i + ";\n");
		}
		f.write("\n");
	}


	/**
	 * Dump the state name table.
	 * @exceptions IOException self-explanatory.
	 */
	private void dumpStateNames(OutputStreamWriter f)
		throws IOException
	{
		int i;

		for(i = 0; i < state_head.length; ++i) {
			f.write(state_head[i] + "\n");
		}
		for(i = 0; i < states.length - 1; ++i) {
			f.write("\t\t\"" + generateTextState(states[i]) +
				"\",\n");
		}
		if (states.length > 0) {
			f.write("\t\t\"" +
				 generateTextState(states[states.length - 1]) +
				 "\"\n");
		}
		for(i = 0; i < state_tail.length; ++i) {
			f.write(state_tail[i] + "\n");
		}
	}


	/**
	 * Dump the event Symbol table.
	 * @exception IOException self-explanatory.
	 */
	private void dumpEventSymbolTable(OutputStreamWriter f)
		throws IOException
	{
		int i;

		f.write("\t/**\n");
		f.write("\t * The numbers belonging to the textual represen" +
			"tations\n");
		f.write("\t * of events.\n\t */\n");
		for(i = 0; i < events.length; ++i) {
			f.write("\tpublic static final int " +
				generateSymEvent(events[i]) +
				" = " + i + ";\n");
		}
		f.write("\n");
	}

		
	/**
	 * Dump the event name table.
	 * @exceptions IOException self-explanatory.
	 */
	private void dumpEventNames(OutputStreamWriter f)
		throws IOException
	{
		int i;

		for(i = 0; i < event_head.length; ++i) {
			f.write(event_head[i] + "\n");
		}
		for(i = 0; i < events.length - 1; ++i) {
			f.write("\t\t\"" + events[i].name + "\",\n");
		}
		if (events.length > 0) {
			f.write("\t\t\"" + events[events.length - 1].name +
				"\"\n");
		}
		for(i = 0; i < event_tail.length; ++i) {
			f.write(event_tail[i] + "\n");
		}
	}


	/**
	 * Dump the symbol table for boolean variables.
	 * @exception IOException self-explanatory.
	 */
	private void dumpCondSymbolTable(OutputStreamWriter f)
		throws IOException
	{
		int i;

		f.write("\t/**\n");
		f.write("\t * The numbers belonging to the textual represen" +
			"tations\n");
		f.write("\t * of boolean variables.\n\t */\n");
		for(i = 0; i < cond.length; ++i) {
			f.write("\tpublic static final int " +
				generateSymBVar(cond[i]) +
				" = " + i + ";\n");
		}
		f.write("\n");
	}

		
	/**
	 * Dump the condition name table.
	 * @exceptions IOException self-explanatory.
	 */
	private void dumpCondNames(OutputStreamWriter f)
		throws IOException
	{
		int i;

		for(i = 0; i < cond_head.length; ++i) {
			f.write(cond_head[i] + "\n");
		}
		for(i = 0; i < cond.length - 1; ++i) {
			f.write("\t\t\"" + cond[i].var + "\",\n");
		}
		if (cond.length > 0) {
			f.write("\t\t\"" + cond[cond.length - 1].var + "\"\n");
		}
		for(i = 0; i < cond_tail.length; ++i) {
			f.write(cond_tail[i] + "\n");
		}
	}


	/**
	 * Generate the textual state name as a String.
	 */
	public static String generateTextState(Path path)
	{
		return generateName(path, ".");
	}


	/**
	 * Generate the symbolic state name as a String.
	 */
	public static String generateSymState(Path path)
	{
		return generateName(path, "_");
	}


	/**
	 * In this section we will generate a symbol table.
	 *
	 * @exception IOException What shall we say?
	 */
	private void dumpSymbolTable(OutputStreamWriter f)
		throws IOException
	{
		int i;

		for(i = 0; i < head.length; ++i) {
			f.write(head[i] + "\n");
		}
		dumpStateSymbolTable(f);
		dumpStateNames(f);
		dumpEventSymbolTable(f);
		dumpEventNames(f);
		dumpCondSymbolTable(f);
		dumpCondNames(f);
		for(i = 0; i < tail.length; ++i) {
			f.write(tail[i] + "\n");
		}
	}


	/**
	 * This method will create the necessary tables in one
	 * run and write them to disc.
	 *
	 * @exception IOException What shall we say?
	 */
	public void dump (OutputStreamWriter f) throws IOException
	{
		generateStateTable();
		generateStateArray();
		generateEventTable();
		generateEventArray();
		generateCondTable();
		generateCondArray();
		dumpSymbolTable(f);
	}
}
