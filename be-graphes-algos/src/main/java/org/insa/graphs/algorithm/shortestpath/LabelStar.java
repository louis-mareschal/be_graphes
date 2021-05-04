package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractInputData;
public class LabelStar extends Label implements Comparable<Label> {
	
	private float real_cost;
	
    public LabelStar(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
        super(node, mark, cost, father);
        float vitesse = data.getGraph().getGraphInformation().getMaximumSpeed() * 1000 / 3600;
        float distance_directe = (float)Point.distance(node.getPoint(), data.getDestination().getPoint());
        if (data.getMode() == AbstractInputData.Mode.LENGTH) {
        	this.real_cost = distance_directe;
        } else {
        	this.real_cost = distance_directe / vitesse;
        }
        
        
    }
    public float getTotalCost() {
 	   return this.getCost() + this.real_cost;
    }
    
}