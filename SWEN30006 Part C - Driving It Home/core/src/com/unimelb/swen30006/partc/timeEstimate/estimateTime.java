package com.unimelb.swen30006.partc.timeEstimate;

import java.util.ArrayList;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;
import com.unimelb.swen30006.partc.utils.Route;

public abstract class estimateTime {
	private ArrayList<Intersection> intersections;
	private ArrayList<Road> road;
	private int numIntersections;
	private float longRoads;
	
	public abstract float estimateTime();
	
	public abstract void roadLongCal();
}
