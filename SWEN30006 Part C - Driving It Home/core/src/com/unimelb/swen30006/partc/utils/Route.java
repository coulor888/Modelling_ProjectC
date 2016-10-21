package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public abstract class Route {
	private ArrayList<Road> roads = new ArrayList<Road>() ;
	private ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	
	public boolean routePlan(Point2D.Double destination){
		
		return true;
	}
	
}
