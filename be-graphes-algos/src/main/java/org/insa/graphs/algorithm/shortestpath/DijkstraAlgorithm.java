package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;


import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        Node origin = data.getOrigin();
        Node destination = data.getDestination();
        int size = graph.size();
        java.util.List<Node> list_node = graph.getNodes();
        // TODO:
        if (origin == destination) {
        	ArrayList<Arc> arcs = new ArrayList<Arc>();
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        	return solution;
        }
        // Initialisation :
        
        // tableau de labels
        Label list_label[] = new Label [size];
        // tas de labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        for (Node node: list_node) {
        	list_label[node.getId()] = create_Label(node, false, Float.POSITIVE_INFINITY, null, data);
        }
        list_label[origin.getId()].setCost(0);
        tas.insert(list_label[origin.getId()]);
        notifyOriginProcessed(origin);
        boolean end = false;
        
        // Itérations
        while (!tas.isEmpty() && !end) {
        	Label label = tas.findMin();
        	
        	tas.remove(label);
        	label.setMarked();
        	notifyNodeMarked(label.getNode());
        	
        	// Si la destination est atteinte on s'arrête
        	if (label.getNode() == destination) {
        		end = true;
        		notifyDestinationReached(destination);
        		continue;
        	}
  
        	for (Arc arc: label.getNode().getSuccessors()) {

        		// Vérification si on peut vraiment prendre cet arc
        		if (!data.isAllowed(arc)) {
        			continue;
        		}
        		
        		// On récupère le label du successeur 
        		Label label_suc = list_label[arc.getDestination().getId()] ;
        		notifyNodeReached(label_suc.getNode());
        		if (!label_suc.isMarked()) {
        			float new_cost = Math.min(label_suc.getCost(), label.getCost() + (float) data.getCost(arc));
        			
        			if (new_cost < label_suc.getCost()) {
        				label_suc.setCost(new_cost);
        				tas.insert(label_suc);
        				label_suc.setFather(arc);
        			}
        		}
        	} 	
        }
        
        // on test si la destination a ou non un prédécesseur
        if (list_label[list_node.indexOf(destination)].getFather() == null) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {
        	// Si un chemin existe on en crée la liste à partir des pères
        	ArrayList<Arc> arcs = new ArrayList<Arc>();
        	Arc arc = list_label[list_node.indexOf(destination)].getFather();
        	while (arc != null) {
        		arcs.add(arc);
        		arc = list_label[list_node.indexOf(arc.getOrigin())].getFather();
        	}
        	// On inverse la liste car elle est à l'envers
        	Collections.reverse(arcs);
        	// On renvoie la solution
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }
   
    protected Label create_Label(Node node, boolean mark, float cost, Arc father, ShortestPathData data) {
    	return new Label(node, false, Float.POSITIVE_INFINITY, null);
    }

}
