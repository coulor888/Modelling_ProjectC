package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public abstract class Route {
	private ArrayList<Road> roads = new ArrayList<Road>() ;
	private ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	private ArrayList<Point2D.Double> routePoint = new ArrayList<Point2D.Double>();
	
	public boolean routePlan(Point2D.Double destination)
	{		
		return true;
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
		return routePoint;
	}
	/*
	 * determine whether there is a latter point in the points list
	 */
	public abstract boolean routePointHasNext();
	
	/*
	 * get a latter point in the points list 
	 * return null when there is not a latter point
	 */
	public abstract Point2D.Double routePointGetNext(Point2D.Double currentPos);
	
}
