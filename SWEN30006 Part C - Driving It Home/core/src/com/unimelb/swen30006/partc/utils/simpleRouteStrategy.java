package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public class simpleRouteStrategy extends Route {
	
	private ArrayList<Road> roads = new ArrayList<Road>() ;
	private ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	
	private static simpleRouteStrategy SRS;
	private GraphStructure graph;
	private final static World world = new World();
	
	
	private simpleRouteStrategy(Intersection[] intersections){
		this.graph = new Graph(intersections);
		this.graph.generateGraph();
	}
	
	public void routePlan(Point2D.Double currentPos, Point2D.Double destination){
		if(world.intersectionAtPoint(currentPos) !=null){
			intersections.add(world.intersectionAtPoint(currentPos));
		}
		
		if(world.roadAtPoint(currentPos) != null){
			
		}
		
	}
	
	public static void getInstance(){
		SRS = new simpleRouteStrategy(world.getIntersections());
	}
}
