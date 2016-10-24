package com.unimelb.swen30006.partc.utils;

import com.unimelb.swen30006.partc.roads.Intersection;

public class Graph implements GraphStructure {
	private final int V;
	private Intersection[] intersections;
	// adjacency lists
	private Bag<Intersection>[] adj;
	
	public Graph(Intersection[] intersections) throws Exception{
		this.V = intersections.length;
		
		if(this.V > 0)
		{
			adj = (Bag<Intersection>[]) new Bag[V];
		}
		else
		{
			throw new Exception("no intersection");
		}
		
		for (int i = 0; i< V; i++){
			adj[i] = new Bag<Intersection>();
		}
	}
	
	public Iterable<Intersection> adj(int i){
		return adj[i];
	}
	
	@Override
	public void generateGraph() {
		for(int i = 0; i < V; i++){
			for(Intersection intersection2: intersections){
				if(intersections[i].isConnected(intersection2)){
					adj[i].add(intersection2);
				}
				if(adj[i].size()>=4){
					break;
				}
			}
		}
	}


	
}


