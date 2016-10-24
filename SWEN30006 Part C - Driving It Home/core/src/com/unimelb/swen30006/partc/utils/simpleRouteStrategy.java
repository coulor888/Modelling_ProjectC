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
	private ArrayList<Point2D.Double> routePoints = new ArrayList<Point2D.Double>();
	
	private static simpleRouteStrategy SRS;
	private IGraph graph;
	private World world;
	
	
	public simpleRouteStrategy(Intersection[] intersections, World w) throws Exception{
		this.graph = new NoDirectGraph(world.getIntersections());	
		world = w;
	}
	
	public void routePlan(Point2D.Double currentPos, Point2D.Double destination) throws Exception{
	
			intersections = (ArrayList<Intersection>) graph.getBreadthFirstTraversal(
					findIntersection(currentPos), findIntersection(destination));
			findRoads();
	}
	
	/*
	 * find a starting or ending intersection 
	 * return the close intersection when position is not on the intersections
	 */
	public Intersection findIntersection(Point2D.Double currentPos)
	{
		if(null != world.intersectionAtPoint(currentPos))
			return world.intersectionAtPoint(currentPos);
		
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
	
	public void initRoutePoints(Point2D.Double currentPos, Point2D.Double destination)
	{	
		routePoints.clear();
		if(null != world.intersectionAtPoint(currentPos))
		{
			routePoints.add(currentPos);
		}		
		else if(null == world.roadAtPoint(currentPos))
		{
			Point2D.Double pos = null;		
			routePoints.add(currentPos);
			Road road = null;
			road = world.closestRoad(currentPos);
			if(road.getStartPos().x<currentPos.x && road.getEndPos().x<currentPos.x)
			{
				pos = new Point2D.Double(road.getEndPos().x + road.getWidth()/4 , currentPos.y);
				routePoints.add(pos);
			}
			else if(road.getStartPos().x>currentPos.x && road.getEndPos().x>currentPos.x)
			{
				pos = new Point2D.Double(road.getEndPos().x - road.getWidth()/4 , currentPos.y);
				routePoints.add(pos);
			}
			else if(road.getStartPos().y<currentPos.y && road.getEndPos().y<currentPos.y)
			{
				pos = new Point2D.Double( currentPos.x, road.getEndPos().y + road.getWidth()/4);
				routePoints.add(pos);
			}
			else
			{
				pos = new Point2D.Double(currentPos.x, road.getEndPos().y - road.getWidth()/4);
				routePoints.add(pos);
			}
			routePoints.add(findPointInRoadEnd(road, intersections.get(0)));
			routePoints.add(findPointInIntersection(road, intersections.get(0)));
		}
		else
		{
			routePoints.add(currentPos);
			Road road = world.roadAtPoint(currentPos);
			routePoints.add(findPointInRoadEnd(road, intersections.get(0)));
			routePoints.add(findPointInIntersection(road, intersections.get(0)));
		}	
		
		for(int i = 0; i < intersections.size() + 1; i++)
		{
			routePoints.add(findPointInRoadBegin(roads.get(i), intersections.get(i)));
			routePoints.add(findPointInRoadEnd(roads.get(i), intersections.get(i + 1)));
			routePoints.add(findPointInIntersection(roads.get(i), intersections.get(i + 1)));
		}
		
		if(null != world.intersectionAtPoint(destination))
		{
			routePoints.add(destination);
			return;
		}		
		else if(null == world.roadAtPoint(destination))
		{
			Point2D.Double pos = null;		
			Road road = null;
			road = world.closestRoad(currentPos);
			routePoints.add(findPointInRoadBegin(road, intersections.get(intersections.size() - 1)));
			if(road.getStartPos().x<currentPos.x && road.getEndPos().x<currentPos.x)
			{
				pos = new Point2D.Double(road.getEndPos().x + road.getWidth()/4 , currentPos.y);
				routePoints.add(pos);
			}
			else if(road.getStartPos().x>currentPos.x && road.getEndPos().x>currentPos.x)
			{
				pos = new Point2D.Double(road.getEndPos().x - road.getWidth()/4 , currentPos.y);
				routePoints.add(pos);
			}
			else if(road.getStartPos().y<currentPos.y && road.getEndPos().y<currentPos.y)
			{
				pos = new Point2D.Double( currentPos.x, road.getEndPos().y + road.getWidth()/4);
				routePoints.add(pos);
			}
			else
			{
				pos = new Point2D.Double(currentPos.x, road.getEndPos().y - road.getWidth()/4);
				routePoints.add(pos);
			}
			routePoints.add(destination);
		}
		else
		{			
			Road road = world.roadAtPoint(currentPos);
			routePoints.add(findPointInRoadBegin(road, intersections.get(intersections.size() - 1)));
			routePoints.add(destination);
		}	
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
	public Double findPointInIntersection(Road road, Intersection intersection) {
		return intersection.getShiftPoint(road);
	}
	
	public Double findPointInIntersection(Road road, Road road1,Intersection intersection) {
		
		Point2D.Double point = null;
		String Dir1 = intersection.getDirectionByRoad(road);
		String Dir2 = intersection.getDirectionByRoad(road1);
		if(Dir1 == "North"){
			if(Dir2 == "West"){
				point.x = intersection.pos.x + intersection.width/4;
				point.y = intersection.pos.y + intersection.length/4;
			}else{
				point.x = intersection.pos.x + intersection.width/4;
				point.y = intersection.pos.y - intersection.length/4;
			}
			
		}else if(Dir1 == "South"){
			if(Dir2=="East"){
				point.x = intersection.pos.x - intersection.width/4;
				point.y = intersection.pos.y + intersection.length/4;
			}else{
				point.x = intersection.pos.x - intersection.width/4;
				point.y = intersection.pos.y - intersection.length/4;
			}

		}else if(Dir1 == "East"){
			if(Dir2=="North"){
				point.x = intersection.pos.x - intersection.width/4;
				point.y = intersection.pos.y - intersection.length/4;
			}else{
				point.x = intersection.pos.x + intersection.width/4;
				point.y = intersection.pos.y - intersection.length/4;
			}
		}else if(Dir1 == "West"){
			if(Dir2=="South"){
				point.x = intersection.pos.x + intersection.width/4;
				point.y = intersection.pos.y + intersection.length/4;
			}else{
				point.x = intersection.pos.x - intersection.width/4;
				point.y = intersection.pos.y + intersection.length/4;
			}
		}
		return point;
	}
	
	@Override
	public Double findPointInRoadBegin(Road road, Intersection intersection) {
		String Dir = intersection.getDirectionByRoad(road);
		Point2D.Double point = intersection.pos;
		float shift = road.getLength();
		if(Dir == "North"){
			point.x = intersection.pos.x - intersection.width/4;
			point.y = intersection.pos.y + intersection.length/2 + shift/8;
		}else if(Dir == "South"){
			point.x = intersection.pos.x + intersection.width/4;
			point.y = intersection.pos.y - intersection.length/2 - shift/8;
		}else if(Dir == "East"){
			point.x = intersection.pos.x + intersection.width/2 + shift/8;
			point.y = intersection.pos.y + intersection.length/4 ;
		}else if(Dir == "West"){
			point.x = intersection.pos.x - intersection.width/2 - shift/8;
			point.y = intersection.pos.y - intersection.length/4 ;
		}
		return point;
	}

	@Override
	public Double findPointInRoadEnd(Road road, Intersection intersection) {
		String Dir = intersection.getDirectionByRoad(road);
		Point2D.Double point = intersection.pos;
		float shift = road.getLength();
		if(Dir == "North"){
			point.x = intersection.pos.x + intersection.width/4;
			point.y = intersection.pos.y + intersection.length/2 + shift/8;
		}else if(Dir == "South"){
			point.x = intersection.pos.x - intersection.width/4;
			point.y = intersection.pos.y - intersection.length/2 - shift/8;
		}else if(Dir == "East"){
			point.x = intersection.pos.x + intersection.width/2 + shift/8;
			point.y = intersection.pos.y - intersection.length/4 ;
		}else if(Dir == "West"){
			point.x = intersection.pos.x - intersection.width/2 - shift/8;
			point.y = intersection.pos.y + intersection.length/4 ;
		}
		return point;
	}

	
	@Override
	public boolean routePointHasNext(Point2D.Double pos) {
		Iterator<Point2D.Double> iter = routePoints.iterator();	
		
		while(iter.hasNext())
		{
			if(iter.next() == pos)
				return true;
		}
		
		return false;
	}

	@Override
	public Double routePointGetNext(Point2D.Double routePos) throws Exception {
		Iterator<Point2D.Double> iter = routePoints.iterator();	
		
		boolean flagExistence = false;
		
		while(iter.hasNext())
		{
			if(iter.next() == routePos)
			{
				flagExistence = true;
			}
			if(flagExistence)
			{				
				if(!iter.hasNext())
					throw new Exception("no such points");
				else
					return iter.next();
			}
		}
		
		return null;
	}
}
