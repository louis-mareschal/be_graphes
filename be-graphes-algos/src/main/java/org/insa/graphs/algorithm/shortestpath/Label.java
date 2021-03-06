package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;

public class Label implements Comparable<Label> {
	
	private Node node;
	private boolean mark;
	private float cost;
	public Arc father;
	
    public Label(Node node, boolean mark, float cost, Arc father) {
        this.node = node;
        this.mark = mark;
        this.cost = cost;
        this.father = father;
    }
    
    
   public float getCost() {
	   return this.cost;
   }
   public float getTotalCost() {
	   return this.cost;
   }
   public void setCost(float cost) {
	   this.cost = cost;
   }
   public void setMarked() {
	   this.mark = true;
   }
   public Node getNode() {
	   return this.node;
   }
   public boolean isMarked() {
	   return this.mark;
   }
   public void setFather(Arc father) {
	    this.father = father;
   }
   public Arc getFather() {
	    return this.father;
  }
   
   public int compareTo(Label autre) {
	   int resultat;
	   if(this.getTotalCost() < autre.getTotalCost()) {
		   resultat = -1;
	   }else if (this.getTotalCost() == autre.getTotalCost()) {
		   resultat = 0;
	   } else {
		   resultat = 1;
	   }
	   return resultat;
   }
}