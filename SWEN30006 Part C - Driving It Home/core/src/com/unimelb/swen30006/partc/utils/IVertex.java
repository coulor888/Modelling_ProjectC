package com.unimelb.swen30006.partc.utils;

import java.util.Iterator;

public interface IVertex<Item>{

	public Item getItem();
	
	public boolean isVisited();
	
	public void visit();
	
	public void noVisit();
	
	public Iterator<IVertex<Item>>  getNeighborInterator() ;
	
	public boolean connectVertex(IVertex<Item> endVertex);
}
