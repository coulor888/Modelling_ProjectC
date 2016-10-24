package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public class simpleRouteStrategy extends Route {
	
	private ArrayList<Road> roads = new ArrayList<Road>() ;
	private ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	
	private static simpleRouteStrategy SRS;
	private IGraph graph;
	private final static World world = new World();
	
	
	private simpleRouteStrategy(Intersection[] intersections) throws Exception{
		this.graph = new NoDirectGraph(world.getIntersections());		
	}
	
	public void routePlan(Point2D.Double currentPos, Point2D.Double destination) throws Exception{
		if(world.intersectionAtPoint(currentPos) !=null)
		{
			intersections = (ArrayList<Intersection>) graph.getBreadthFirstTraversal(
					world.intersectionAtPoint(currentPos));
			findRoads();
		}		
		else
		{
			intersections = (ArrayList<Intersection>) graph.getBreadthFirstTraversal(
					findStartIntersection(currentPos));
			findRoads();
		}
		
	}
	
	public static void getInstance() throws Exception{
		SRS = new simpleRouteStrategy(world.getIntersections());
	}
	/*
	 * find a starting intersection when current position is not on the intersections
	 */
	public Intersection findStartIntersection(Point2D.Double currentPos)
	{	
		Road road = null;
		if(null == world.roadAtPoint(currentPos))
		{
			road = world.closestRoad(currentPos);
		}
		else
		{
			road = world.roadAtPoint(currentPos);
		}	

		if(null != world.intersectionAtPoint(road.getStartPos()))
			return world.intersectionAtPoint(road.getStartPos());
		else
			return world.intersectionAtPoint(road.getEndPos());

	}
	
	/*
	 * set road list based on intersections
	 */
	public boolean findRoads()
	{
		boolean result = false; 
		Point2D.Double pos = null;
		
		Iterator<Intersection> iter = intersections.iterator();
		Intersection i1;
		for(Intersection i: intersections)
		{
			if(iter.hasNext())
			{
				i1 = iter.next();
				pos = new Point2D.Double((i.pos.x + i1.pos.x)/2
						,(i.pos.y + i1.pos.y)/2);
				roads.add(this.world.roadAtPoint(pos));
			}
		}
		
		if(pos != null)
			result = true;
		
		return result;
	}

	@Override
	public boolean routePointHasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Double routePointGetNext(Double currentPos) {
		// TODO Auto-generated method stub
		return null;
	}
}
