package com.unimelb.swen30006.partc.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.maps.Map;
import com.unimelb.swen30006.partc.roads.Intersection;

public class NoDirectGraph implements IGraph{

	private final int numIntersections;                            //quantities of edges
	private HashMap<Intersection, IVertex<Intersection>> mapGraph; //one intersection has one relevant Vertex

	public NoDirectGraph(Intersection[] intersections) throws Exception {
		// TODO Auto-generated constructor stub
		numIntersections = intersections.length;
		mapGraph = new HashMap<Intersection, IVertex<Intersection>>();
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

	@Override
	public Queue<Intersection> getBreadthFirstTraversal(Intersection start, Intersection end) throws Exception
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
            while(neighbors.hasNext())//adjoining vertex 
            {
            	IVertex<Intersection> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited()){
                    nextNeighbor.visit();
                    traversalOrder.offer(nextNeighbor.getItem());
                    if(end.equals(nextNeighbor.getItem()))
                    	return traversalOrder;
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
			Iterator<HashMap.Entry<Intersection, IVertex<Intersection>>> iter1 = mapGraph.entrySet().iterator();
			Iterator<HashMap.Entry<Intersection, IVertex<Intersection>>> iter2 = mapGraph.entrySet().iterator();

			while (iter1.hasNext()) 
			{
				HashMap.Entry<Intersection, IVertex<Intersection>> entry1 = iter1.next();
				Intersection key1 = (Intersection)entry1.getKey();
				IVertex<Intersection> val1 = (IVertex<Intersection>)entry1.getValue();
				
				iter2 = mapGraph.entrySet().iterator();
				
				while (iter2.hasNext()) 
				{
					HashMap.Entry<Intersection, IVertex<Intersection>> entry2 = iter2.next();
					Intersection key2 = (Intersection)entry2.getKey();
					if(key2.isConnected(key1))
					{
						IVertex<Intersection> val2 = (IVertex<Intersection>)entry2.getValue();
						val1.connectVertex(val2);
					}
				}
			}
		}
		else
			throw new Exception("no intersection");
	}
}
