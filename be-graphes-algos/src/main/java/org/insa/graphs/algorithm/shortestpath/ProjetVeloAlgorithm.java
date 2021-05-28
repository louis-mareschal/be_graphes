package org.insa.graphs.algorithm.shortestpath;

import java.util.EnumSet;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.AccessRestrictions.AccessMode;
import org.insa.graphs.model.AccessRestrictions.AccessRestriction;
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
    	float vitesse = (float)(15 * 1000 / 3600); // On prend 15 km/h de moyenne
    	int coeff = 5;
    	float cost = 0;
    	float vitesse_arc = (float)arc.getRoadInformation().getMaximumSpeed();
    	if (arc.getRoadInformation().getType() == RoadType.MOTORWAY) {
    		if (vitesse_arc >= 110) {
    			coeff = 50;
    		} else if (vitesse_arc >= 80.0) {
    			coeff = 5;
    		} else if (vitesse_arc >= 50.0) {
    			coeff = 3;
    		} else if (vitesse_arc >= 30.0) {
    			coeff = 2;
    		}else {
    			coeff = 1;
    		}
        }else if (arc.getRoadInformation().getType() == RoadType.CYCLEWAY || arc.getRoadInformation()
        		.getAccessRestrictions().isAllowedForAny(AccessMode.FOOT, EnumSet.complementOf(EnumSet
                .of(AccessRestriction.FORBIDDEN, AccessRestriction.PRIVATE)))) {
        	coeff = 1;
        }
        if (this.data.getMode() != AbstractInputData.Mode.LENGTH) {
        	coeff /= vitesse;
        	cost = arc.getLength();
        } else {
        	cost = arc.getLength()/vitesse;
        }
    	return cost * (float)coeff;
    }
}
