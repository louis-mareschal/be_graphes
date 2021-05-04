package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
    protected Label create_Label(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
    	return new LabelStar(node, mark, cost, father, data);
    }
}
