package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
public class LabelVelo extends Label implements Comparable<Label> {
	
	


	
    public LabelVelo(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
        super(node, mark, cost, father); 
        
    }
    public float getTotalCost() {
 	    return this.getCost();
    }
    
}   
   