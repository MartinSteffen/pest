/**
 * 
 */
package Util;

/**
 * Zufallszahlgenerator nach Donald E. Knuth mit Bereichsanpassung.
 * @version $Id: RandomIntGenerator.java,v 1.1 1998-12-07 14:37:22 swtech25 Exp $
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 */
public class RandomIntGenerator {
        private final int BUFFER_SIZE = 101;
        private double[] buffer = new double[BUFFER_SIZE];
        private int min;
        private int max;

	/**
	 * RandomIntGenerator wird mit zwei ganzahligen Werten
	 * min und max konstruiert.  Zufallszahlen werden dann
	 * im Intervall [min,max] erzeugt.
	 */
        public RandomIntGenerator(int n, int x) {
        	for (int i = 0; i < BUFFER_SIZE; i++)
        		buffer[i] = Math.random();
                min = n;
                max = x;
       }

	/**
	 * Bereichsanpassung
	 */
        public int nextInt() {
        	int r = min + (int) ((max - min + 1) * nextRandom());
        	if (r > max) r = max;
        	return r;
        }

	/**
	 * D.E. Knuth-Variante, Array-Austauschelement.
	 */
        private double nextRandom() {
        	int pos =
        		(int) (java.lang.Math.random() * BUFFER_SIZE);
        	if (pos == BUFFER_SIZE) pos = BUFFER_SIZE - 1;
        	double r = buffer[pos];
        	buffer[pos] = java.lang.Math.random();
        	return r;
        }


	/*
	 * Beispielanwendung
	public static void main(String[] args) {
		n = getkey();
		x = getkey();

		RandomIntGenerator rg = new RandomIntGenerator(n,x);
		for (int i = 1; i <= 100; i++)
			System.out.println(rg.nextInt());
	}
	 */
}
