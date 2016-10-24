package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public abstract class Route {
	protected ArrayList<Road> roads = new ArrayList<Road>() ;
	protected ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	protected ArrayList<Point2D.Double> routePoints = new ArrayList<Point2D.Double>();
	
	public void routePlan(Point2D.Double currentPos , Point2D.Double destination) throws Exception
	{		
		
	}
	
	public ArrayList<Intersection> retrieveIntersections()
	{
		return intersections;
	}
	
	public ArrayList<Road> retrieveRoads()
	{
		return roads;
	}
	
	public ArrayList<Point2D.Double> routePoint()
	{
		return routePoints;
	}
	/*
	 * determine whether there is a latter point in the points list
	 */
	public abstract boolean routePointHasNext(Point2D.Double currentPos);
	
	/*
	 * get a latter point in the points list 
	 * return null when there is not a latter point
	 */
	public abstract Point2D.Double routePointGetNext(Point2D.Double currentPos) throws Exception;
	
	public abstract Double findPointInIntersection(Road road, Intersection intersection);
	
	public abstract Double findPointInRoadBegin(Road road, Intersection intersection);
	
	public abstract Double findPointInRoadEnd(Road road, Intersection intersection);
	
	public void addPoint(Double point) {
		routePoints.add(point);
		
	}
}
