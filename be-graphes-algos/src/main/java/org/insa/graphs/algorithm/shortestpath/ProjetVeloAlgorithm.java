package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class ProjetVeloAlgorithm extends DijkstraAlgorithm {

    public ProjetVeloAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
    protected Label create_Label(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
    	return new LabelVelo(node, mark, cost, father, data);
    }
}
