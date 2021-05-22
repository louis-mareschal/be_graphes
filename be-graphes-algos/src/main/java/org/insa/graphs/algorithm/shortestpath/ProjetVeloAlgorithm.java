package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation.RoadType;

public class ProjetVeloAlgorithm extends DijkstraAlgorithm {

    public ProjetVeloAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
    protected Label create_Label(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
    	return new LabelVelo(node, mark, cost, father, data);
    }
    @Override
    protected float getCostArc(Arc arc) {
    	float vitesse = (float)arc.getMinimumTravelTime() * 1000 / 3600;
    	int coeff = 100;
    	if (arc.getRoadInformation().getType() != RoadType.MOTORWAY) {
        	coeff = 1000;	
        }else if (arc.getRoadInformation().getType() != RoadType.CYCLEWAY) {
        	coeff = 1;
        }
        if (this.data.getMode() != AbstractInputData.Mode.LENGTH) {
        	coeff /= vitesse;
        }
    	return (float) data.getCost(arc) * coeff;
    }
}
