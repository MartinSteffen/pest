package simu;

import java.io.*;
import java.util.*;

/**
 * New type, created from HH on INTREPID.
 */
public class TraceData implements Serializable {
	Vector data = new Vector();
/**
 * TraceData constructor comment.
 * Die Uebergebenen Vectoren sind:
 * 1. InList
 * 2. seList
 * 3. bvList
 * 4. Entered-List
 * 5. Exited-List
 */
public TraceData(Vector arg1, Vector arg2, Vector arg3) {
	data.addElement(arg1);
	data.addElement(arg2);
	data.addElement(arg3);
	//data.addElement(arg4);
	//data.addElement(arg5);
}
}