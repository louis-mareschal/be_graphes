package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
public class LabelVelo extends Label implements Comparable<Label> {
	
	private int coeff;


	
    public LabelVelo(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
        super(node, mark, cost, father); 
        this.coeff = 1;
        
    }
    public float getTotalCost() {
 	    return this.getCost() * this.coeff;
    }
    
}   
   