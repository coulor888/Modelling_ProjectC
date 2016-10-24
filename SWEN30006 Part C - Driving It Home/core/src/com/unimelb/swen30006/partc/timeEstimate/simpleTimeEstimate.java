package com.unimelb.swen30006.partc.timeEstimate;

import java.util.ArrayList;
import java.lang.Math;

import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

public class simpleTimeEstimate extends estimateTime{
	private ArrayList<Intersection> intersections;
	private ArrayList<Road> roads;
	private int numIntersections;
	private float longRoads = 0;
	
	public simpleTimeEstimate(ArrayList<Intersection> intersections,ArrayList<Road> roads){
		this.intersections = intersections;
		this.roads = roads;
		this.numIntersections = intersections.size();
	}
	
	@Override
	public float estimateTime() {
		return (30*numIntersections + longRoads/Car.MAX_VELOCITY);
	}

	@Override
	public void roadLongCal() {
		for(Road road: roads){
			longRoads = longRoads + 
					Math.abs((float)(road.getEndPos().getX()-road.getStartPos().getX()))
					+ Math.abs((float)(road.getEndPos().getY()-road.getStartPos().getY()));
		}
		
	}

}
