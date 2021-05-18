package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
public class LabelVelo extends Label implements Comparable<Label> {
	
	private int coeff;
	
    public LabelVelo(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
        super(node, mark, cost, father); 
        this.coeff = 1;
    }
    public float getTotalCost() {
 	    return this.getCost() * this.coeff;
    }
    @Override
    public void setFather(Arc father) {
	    this.father = father;
	    if (father.getRoadInformation().getType() != RoadType.MOTORWAY) {
        	this.coeff = 10000;	
        }else if (this.getFather().getRoadInformation().getType() != RoadType.CYCLEWAY) {
        	this.coeff = 1;	
        }
	    System.out.println(this.coeff);
	    this.setCost(this.getCost()*this.coeff); 
   }
    
}   
   