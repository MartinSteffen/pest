/**
 * This class is a very simple environment.
 *
 * @author
 * @version $Id: SimpleEnvironment.java,v 1.2 1999-02-12 15:13:07 swtech25 Exp $
 */
import java.io.*;

public class SimpleEnvironment {

	/**
	 * Argument one is expected to be the number of steps to
	 * run.
	 */
	public static void main (String[] args) {

		int steps;

		Automaton a = new Automaton();
		SymbolTable s = new SymbolTable();
		OutputStreamWriter f = new OutputStreamWriter(System.out);

		a.init(s);
		if (args.length == 1) {
			try {
				steps = Integer.parseInt(args[1]);
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
			     s.trace(f);
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
