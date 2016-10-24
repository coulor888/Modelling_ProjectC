package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.utils.Graph;
import com.unimelb.swen30006.partc.utils.GraphStructure;
import com.unimelb.swen30006.partc.utils.Route;
import com.unimelb.swen30006.partc.utils.angleCal;

public class Planning implements IPlanning {
	private Car car;
	private Point2D.Double currentPos;
	private World world;
	private Intersection[] intersections;
	private GraphStructure graph;
	
	private Route route;
	
	private boolean tempXY4Test = false;
	
	//generator
	public Planning(Car car, World world){
		this.car = car;
		this.currentPos = car.getPosition();
		this.intersections = world.getIntersections();
		this.world =  world;
	} 
	
	
	@Override
	public boolean planRoute(Double destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(PerceptionResponse[] results, int visibility, float delta) {
		// TODO Auto-generated method stub
		updateCarwithPerception(results, visibility, delta);
	}

	private void updateCarwithPerception(PerceptionResponse[] results, int visibility, float delta) {
		// TODO Auto-generated method stub
		Point2D.Double targetPoint = new Point2D.Double(car.getPosition().getX(),car.getPosition().getY());
		//test case
		if (tempXY4Test) {
			targetPoint.x += 5;
			tempXY4Test = !tempXY4Test;
//			car.accelerate();
		}
		else {
			targetPoint.y += 5;
			tempXY4Test = !tempXY4Test;
//			car.brake();
		}

		car.turn(calDeviationAngle(car.getPosition(),targetPoint,car.getVelocity()));
		car.accelerate();
		car.update(delta);
	}


	private float calDeviationAngle(Double startPoint, Double targetPoint, Vector2 currentAngle) {
		// TODO Auto-generated method stub
		Vector2 targetAngle = new Vector2();
		targetAngle.x = (float) (targetPoint.getX() - startPoint.getX());
		targetAngle.y = (float) (targetPoint.getY() - startPoint.getY());
		return (float) angleCal.AngleBetween(currentAngle, targetAngle);
	}


	@Override
	public float eta() {
		// TODO Auto-generated method stub
		return 0;
	}

}
