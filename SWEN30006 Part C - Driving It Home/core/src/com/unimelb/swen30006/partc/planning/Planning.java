package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.utils.Graph;
import com.unimelb.swen30006.partc.utils.GraphStructure;
import com.unimelb.swen30006.partc.utils.Route;

public class Planning implements IPlanning {
	private Car car;
	private Point2D.Double currentPos;
	private World world = new World();
	private Intersection[] intersections;
	private GraphStructure graph;
	
	private Route route;
	
	//generator
	public Planning(Car car){
		this.car = car;
		this.currentPos = car.getPosition();
		this.intersections = world.getIntersections();
	} 
	
	
	@Override
	public boolean planRoute(Double destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(PerceptionResponse[] results, int visibility, float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public float eta() {
		// TODO Auto-generated method stub
		return 0;
	}

}
