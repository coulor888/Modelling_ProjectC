package com.unimelb.swen30006.partc.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.unimelb.swen30006.partc.roads.Intersection;

public class NoDirectGraph implements IGraph{

	private final int numIntersections;                            //quantities of edges
	private HashMap<Intersection, IVertex<Intersection>> mapGraph; //one intersection has one relevant Vertex
	
//	public NoDirectGraph(Intersection[] intersections) throws Exception
//	{
//		numIntersections = intersections.length;
//		
//		if(this.numIntersections > 0)
//		{
//			for(int i = 0; i < numIntersections; i++)
//				mapGraph.put(intersections[i], new Vertex(intersections[i]));	
//		}
//		else
//		{
//			throw new Exception("no intersection");
//		}
//	}

	public NoDirectGraph(Intersection[] intersections) throws Exception {
		// TODO Auto-generated constructor stub
		numIntersections = intersections.length;
		
		if(this.numIntersections > 0)
		{
			for(int i = 0; i < numIntersections; i++)
				mapGraph.put(intersections[i], new Vertex(intersections[i]));	
		}
		else
		{
			throw new Exception("no intersection");
		}
	}

	public Queue<Intersection> getBreadthFirstTraversal(Intersection start) throws Exception
	{
		graphGenerator();
        Queue<IVertex<Intersection>> vertexQueue = new LinkedList<IVertex<Intersection>>();
        Queue<Intersection> traversalOrder = new LinkedList<Intersection>();
        IVertex<Intersection> originVertex = mapGraph.get(start);
        originVertex.visit();
        traversalOrder.offer(originVertex.getItem());
        vertexQueue.offer(originVertex);
        
        while(!vertexQueue.isEmpty()){
        	IVertex<Intersection> frontVertex = vertexQueue.poll();
            Iterator<IVertex<Intersection>> neighbors = frontVertex.getNeighborInterator();
            while(neighbors.hasNext())
            {
            	IVertex<Intersection> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited()){
                    nextNeighbor.visit();
                    traversalOrder.offer(nextNeighbor.getItem());
                    vertexQueue.offer(nextNeighbor);
                }
            }
        }
        return traversalOrder;
    }
	
	//initialize vertexes
	@Override
	public void graphGenerator() throws Exception 
	{
		if(!mapGraph.isEmpty())
		{
			Iterator iter1 = mapGraph.entrySet().iterator();
			Iterator iter2 = mapGraph.entrySet().iterator();
			HashMap.Entry entry1 = (HashMap.Entry) iter1;
			HashMap.Entry entry2 = (HashMap.Entry) iter2;
			while (iter1.hasNext()) 
			{
				Intersection key1 = (Intersection)entry1.getKey();
				IVertex<Intersection> val1 = (IVertex<Intersection>)entry1.getValue();
				
				iter2 = mapGraph.entrySet().iterator();
				entry2 = (HashMap.Entry) iter2;
				while (iter2.hasNext()) 
				{
					Intersection key2 = (Intersection)entry2.getKey();
					if(key2.isConnected(key1))
					{
						IVertex<Intersection> val2 = (IVertex<Intersection>)entry2.getValue();
						val1.connectVertex(val2);
					}
				}
				
				entry1 = (HashMap.Entry) iter1.next();
			}
		}
		else
			throw new Exception("no intersection");
	}
}
