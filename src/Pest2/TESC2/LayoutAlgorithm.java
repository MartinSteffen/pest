/**
 * LayoutAlgorithm.java
 *
 *
 * Created: Mon Dec 14 12:55:29 1998
 *
 * @author Software Technologie 19
 * @version
 */

package TESC2;

import Absyn.*;

interface LayoutAlgorithm {
    
    void layoutBasicState(Basic_State s);
    void layoutORState(Or_State s);
    void layoutANDState(And_State s);
    void layoutStatechart(Statechart sc);
    
} // LayoutAlgorithm