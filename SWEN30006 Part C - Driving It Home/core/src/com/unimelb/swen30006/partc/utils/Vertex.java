package com.unimelb.swen30006.partc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * vertex of Graph
 */
public class Vertex<Item> implements IVertex<Item>{

	private Item item;                          // Items in the world
	private ArrayList<Edge> edgeList;           // list of edges
	private boolean visited;                    // flag is used to BFS
	IVertex<Item> previousVertex;               // the former Vertex of this vertex
	
	public Vertex(Item ii)
	{
		item = ii;
		edgeList = new ArrayList<Edge>();
		visited = false;
		previousVertex = null;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public boolean isVisited()
	{
		return visited;
	}
	
	public void visit()
	{
		visited = true;
	}
	
	public void noVisit()
	{
		visited = false;
	}
	
//	public boolean hasPreviousVertex() 
//	{
//		return this.previousVertex != null;     
//	}
//	
//	public void setPreviousVertex(IVertex<Item> tmpVertex) 
//	{
//	    this.previousVertex = tmpVertex;
//  }
//	
//	public IVertex<Item> getPreviousVertex() 
//	{
//	    return this.previousVertex;
//  }
	
	public Iterator<IVertex<Item>>  getNeighborInterator() 
	{
		return new NeighborIterator();
	}
	
	public boolean hasNeighbor() 
	{
		return !(edgeList.isEmpty());  
	}
	
	//get first no visited vertex
	public IVertex<Item> getNovisitedNeighbor() 
	{
		IVertex<Item> result = null;
		Iterator<IVertex<Item>> neighbors = getNeighborInterator();
		while(neighbors.hasNext() && result == null)
		{           
			IVertex<Item> nextNeighbor = neighbors.next();
			if(!nextNeighbor.isVisited())
				result = nextNeighbor;
		}
		return result;
	}
	
	//connect adjoining vertex
	public boolean connectVertex(IVertex<Item> endVertex)
	{
		boolean result = false;
		
		if(!this.equals(endVertex))
		{
			Iterator<IVertex<Item>> neighbors = this.getNeighborInterator();
			
			boolean flagAdd = false;
			while(neighbors.hasNext())
			{
				IVertex<Item> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
				{
					flagAdd = true;
					break;
				}
		    }
			if(!flagAdd)
			{
				edgeList.add(new Edge(endVertex));
				result = true;
			}
		}
		return result;
	}
	
	/*
	 * edge to connect two vertexes
	 */
	protected class Edge {
		
		private IVertex<Item> endVertex;   //adjoining vertex
		
		protected Edge(IVertex<Item> ev)
		{
			endVertex = ev;
		}
		
		protected IVertex<Item> getEndVertex()
		{
			return endVertex;
		}
	}
	
	/*
	 * this class is to get endVertex from arrayList<Edge>
	 */
	private class NeighborIterator implements Iterator<IVertex<Item>>
	{
		Iterator<Edge> edgesIterator;
		
		//get iterator form list of edges
		private NeighborIterator() 
		{
			edgesIterator = edgeList.iterator();        
		}
		
		@Override
		public boolean hasNext() 
		{
			return edgesIterator.hasNext();
		}
		
		@Override
		//link list of IVertex
		public IVertex<Item> next() 
		{
			IVertex<Item> nextNeighbor = null;
			
			if(edgesIterator.hasNext())
			{
				Edge edgeToNextNeighbor = edgesIterator.next();               
				nextNeighbor = edgeToNextNeighbor.getEndVertex();
			}
			else 
				throw new NoSuchElementException();
			
			return nextNeighbor;
		}
		
		@Override
		public void remove() 
		{
			//
		}
	}
	
	
}
