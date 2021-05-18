package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;


public class DijkstraTest {

    
    private static Graph g1, g2;
    
    @BeforeClass
    public static void initAll() throws IOException {
    	
    	String mapDirectory = "/home/marescha/Cours/be_graphes/Maps/";
    	
    	String mapCarreDense = mapDirectory + "carre-dense.mapgr";
    	GraphReader readerCarreDense = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarreDense))));
    	g1 = readerCarreDense.read();
    	
    	String mapToulouse = mapDirectory + "toulouse.mapgr";
    	GraphReader readerToulouse = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapToulouse))));
    	g2 = readerToulouse.read();
    }
    
    public ArrayList<ShortestPathSolution> test(Graph graph, int origine, int destination, int typetravel, int comparer) {
    	ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(typetravel);
    	ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspector);
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionD = D.run();
		ArrayList<ShortestPathSolution> list = new ArrayList<ShortestPathSolution>();
		list.add(solutionD);
		if (comparer == 1) {
			BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionB = B.run();
			list.add(solutionB);
		}else if (comparer == 2) {
			AStarAlgorithm A = new AStarAlgorithm(data);
			ShortestPathSolution solutionA = A.run();
			list.add(solutionA);
		}
		return list;
    }


    @Test
    public void testIsValid() {
    	// Test 1
    	ShortestPathSolution solution = test(g1, 0, 0, 1, 0).get(0);
    	assertTrue(solution.getPath().isValid());
    	// Test 2
    	boolean error = false;
    	try {
    		solution = test(g1, -1, 300, 3, 0).get(0);
    	} catch (Exception e) {
    		error = true;
    	}finally {
    		assertTrue(error);
    	}
    	// Test 3
    	error = false;
    	try {
    		solution = test(g1, 0, 300000000, 3, 0).get(0);
    	} catch (Exception e) {
    		error = true;
    	}finally {
    		assertTrue(error);
    	}
    	// Test 4
    	solution = test(g1, 450, 8900, 4, 0).get(0);
    	assertTrue(solution.getPath().isValid());
    }
    
    @Test
    public void testIsFeasible() {
    	// Test 1
    	assertEquals(Status.OPTIMAL, test(g1, 0, 300, 1, 0).get(0).getStatus());
    	// Test 2
    	assertEquals(Status.OPTIMAL, test(g1, 9800, 30030, 1, 0).get(0).getStatus());
    }
    @Test
    public void testCost() {
    	// Test 1
    	ShortestPathSolution solution = test(g1, 0, 100, 0, 0).get(0);
    	assertEquals((int)solution.getPath().getLength(), solution.getCost());
    	// Test 2
    	solution = test(g1, 0, 100, 2, 0).get(0);
    	assertEquals((int)solution.getPath().getMinimumTravelTime(), solution.getCost());
    }
    @Test
    public void testComparaison() {
    	ArrayList<ShortestPathSolution> list = new ArrayList<ShortestPathSolution>();
    	// Test 1 Comparaison BellmanFord Dijktra Distance
    	list = test(g2, 342, 10020, 1, 1);
    	if (list.get(1).isFeasible()) {
    		assertEquals((int)list.get(0).getPath().getLength(), (int)list.get(1).getPath().getLength());
    	}
    	// Test 2 Comparaison BellmanFord Dijktra Temps
    	list = test(g2, 342, 10020, 3, 1);
    	if (list.get(1).isFeasible()) {
    		assertEquals((int)list.get(0).getPath().getLength(), (int)list.get(1).getPath().getLength());
    	}
    }
    

}
