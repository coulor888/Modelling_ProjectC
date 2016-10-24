package com.unimelb.swen30006.partc.utils;

import java.util.Queue;

import com.unimelb.swen30006.partc.roads.Intersection;

public interface IGraph {
	
	public void graphGenerator() throws Exception;

	public Queue<Intersection> getBreadthFirstTraversal(Intersection start) throws Exception;
}
