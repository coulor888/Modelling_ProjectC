package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.sun.javafx.scene.paint.GradientUtils.Point;
import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.utils.Graph;
import com.unimelb.swen30006.partc.utils.GraphStructure;
import com.unimelb.swen30006.partc.utils.Route;
import com.unimelb.swen30006.partc.utils.SpeedUtils;
import com.unimelb.swen30006.partc.utils.angleCal;

public class Planning implements IPlanning {
	private Car car;
	private Point2D.Double currentPos;
	private World world;
	private Intersection[] intersections;
	private GraphStructure graph;
	
	private Route route;
	
	private boolean tempXY4Test = false;
	private Point2D.Double targetPoint;
	private double remainingDistance;
	
	//generator
	public Planning(Car car, World world){
		this.car = car;
		this.currentPos = car.getPosition();
		this.intersections = world.getIntersections();
		this.world =  world;
		targetPoint = null;
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
		
		int safetyDistance = SpeedUtils.getSafetyDistance
				(car.getVelocity().len(), 
						car.calculateAcceleration(0, car.getbreakingPower()).len(), 
						(int)car.getLength());
		if(shouldCarBreak(results, safetyDistance)){
			car.brake();
		}
		else{
			Point2D.Double tmptargetPoint;
			if (shouldTakeNextPoint()) {
				//take next point
			}
			else{
				//judge if intersection is within the safety distance
				//if so
					//judge traffic light
						//if red
							//break
						//else
							// turn and acc
				//else
					//turn and acc
				//take current target point as the targetPoint
			}
			//test code
			tmptargetPoint = new Point2D.Double(car.getPosition().getX(),car.getPosition().getY());
			if (tempXY4Test) {
				tmptargetPoint.x += 5;
				tmptargetPoint.y += 5;
				tempXY4Test = !tempXY4Test;
			}
			else {
				tmptargetPoint.x -= 5;
				tmptargetPoint.y -= 5;
				tempXY4Test = !tempXY4Test;
			}

			car.turn(calDeviationAngle(car.getPosition(),tmptargetPoint,car.getVelocity()));
			car.accelerate();
		}

		car.update(delta);
	}


	private boolean shouldTakeNextPoint() {
		// TODO Auto-generated method stub
		return false;
	}


	private float calDeviationAngle(Double startPoint, Double targetPoint, Vector2 currentAngle) {
		// TODO Auto-generated method stub
		Vector2 targetAngle = new Vector2();
		targetAngle.x = (float) (targetPoint.getX() - startPoint.getX());
		targetAngle.y = (float) (targetPoint.getY() - startPoint.getY());
		return (float) angleCal.AngleBetween(currentAngle, targetAngle);
	}
	
	private boolean shouldCarBreak(PerceptionResponse[] results, int safetyDistance){
		boolean shouldBreak = false;
		for (int i = 0; i < results.length; i++) {
			if(Math.abs(results[i].direction.len2())<safetyDistance){
				Point2D.Double SafetyPoint = 
						SpeedUtils.getSafetyPoint(
								car.getPosition(), 
								car.getVelocity(), 
								safetyDistance);				
				shouldBreak |= SpeedUtils.isPointInTrace(results[i].position, car.getPosition(), SafetyPoint);
			}
		}
		return shouldBreak;
	}


	@Override
	public float eta() {
		// TODO Auto-generated method stub
		return 0;
	}

}
