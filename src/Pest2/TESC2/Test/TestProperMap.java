
/**
 * TestProperMap.java
 *
 *
 * Created: Tue Dec  8 14:19:54 1998
 *
 * @author Achim Abeling
 * @version
 */



import Absyn.*;
import TESC2.*;
import Util.*;

public class TestProperMap {
    
    public static void main(String argv[]) {

	/*
	TrAnchor[] vertex = new TrAnchor[7];
	vertex[0] = new Statename("State1");
	vertex[1] = new Statename("State2");
	vertex[2] = new Statename("State3");
	vertex[3] = new Conname("Con1");
	vertex[4] = new Statename("State4");
	vertex[5] = new UNDEFINED();
	vertex[6] = new Statename("State5");

	Tr[] transition = new Tr[10];
	transition[0] = new Tr(vertex[0],vertex[6],null);
	transition[1] = new Tr(vertex[0],vertex[1],null);
	transition[2] = new Tr(vertex[2],vertex[1],null);
	transition[3] = new Tr(vertex[3],vertex[2],null);
	transition[4] = new Tr(vertex[4],vertex[3],null);
	transition[5] = new Tr(vertex[4],vertex[5],null);
	transition[6] = new Tr(vertex[6],vertex[3],null);
	transition[7] = new Tr(vertex[0],vertex[3],null);
	transition[8] = new Tr(vertex[2],vertex[0],null);
	transition[9] = new Tr(vertex[0],vertex[2],null);
	*/

	RandomIntGenerator rand = new RandomIntGenerator(5,10);
	RandomIntGenerator rand2 = new RandomIntGenerator(10,15);
	RandomIntGenerator rand3 = new RandomIntGenerator(1,2);

	int vertices = rand.nextInt();
	System.out.println("vertices="+vertices);
	
	RandomIntGenerator rand4 = new RandomIntGenerator(0,vertices-1);

	TrAnchor[] vertex = new TrAnchor[vertices];
	for (int i=0;i<vertices;i++) {
	    int t=rand3.nextInt();
	    if (t==1) {
		vertex[i] = new Statename("S"+i);
	    } else if (t==2) {
		vertex[i] = new Conname("C"+i);
	    } else {
		vertex[i] = new UNDEFINED();
	    }
	}

	int edges = rand2.nextInt();
	System.out.println("edges="+edges);
	
	Tr[] transition = new Tr[edges];
	for (int i=0;i<edges;i++) {
	    int si = rand4.nextInt();
	    int ti = rand4.nextInt();
	    
	    TrAnchor source = vertex[si];
	    TrAnchor target = vertex[ti];
	    transition[i] = new Tr(source,target,null);
	}
	    
	System.out.println();
	System.out.println("Ausgabe:");

	for (int i=0;i<edges;i++) 
	    new PrettyPrint().start(transition[i]);
	
	TESC2.ProperMap properMap = new TESC2.ProperMap(vertex,transition);

	for (int i=0;i<properMap.getHeight();i++) {
	    System.out.print(properMap.getWidthOfRow(i));
	    
	    for (int j=0;j<properMap.getWidthOfRow(i);j++) {
		System.out.print(properMap.getElement(i,j)+" ");
	    }
	    System.out.println();
	}

	TESC2.MapTransition[] mt = properMap.getMapTransitions();
	for (int i=0;i<mt.length;i++)
	    System.out.println(mt[i]);

    }

    
    
} // TestProperMap
