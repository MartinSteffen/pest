/**
 * This class is a very simple environment.
 *
 * @author
 * @version $Id: SimpleEnvironment.java,v 1.1 1999-01-25 15:10:17 swtech25 Exp $
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
		OutputStreamWriter f = new OutputStreamWriter(System.out);

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

		a.pre_states[0] = true;

		for(int i = 1; i < a.pre_events.length; ++i) {
			a.pre_events[i] = true;
		}

		for(; steps > 0; --steps) {
			a.step();
			try {
			     a.trace(f);
			     f.flush();
			}
			catch(IOException e) {
			     System.out.println(e);
			     return;
			}
			a.finalizeStep();
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
