/**
 * This class is a very simple environment.
 *
 * @author
 * @version $Id: SimpleEnvironment.java,v 1.2 1999-03-11 16:58:12 swtech25 Exp $
 */
import java.io.*;
import codegen.EnvUtil;

public class SimpleEnvironment {

	/**
	 * Argument one is expected to be the number of steps to
	 * run.
	 */
	public static void main (String[] args) {

		int steps;

		Automaton1 a = new Automaton1();
		SymbolTable s = new SymbolTable();
		OutputStreamWriter f = new OutputStreamWriter(System.out);

		a.init(s);
		if (args.length == 1) {
			try {
				steps = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.out.println(e);
				return;
			}
		} else {
			steps = 10;
		}

		for(int i = 1; i < s.pre_events.length; ++i) {
			s.pre_events[i] = true;
		}

		for(; steps > 0; --steps) {
			a.step(s);
			try {
			     EnvUtil.traceSimple(f, s);
			     f.flush();
			}
			catch(IOException e) {
			     System.out.println(e);
			     return;
			}
			s.finalizeStep();
		}
		try {
		     f.close();
		}
		catch (IOException e) {
		     System.out.println(e);
		     return;
		}
	}
}
