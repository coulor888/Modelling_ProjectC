package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.sun.javafx.scene.paint.GradientUtils.Point;
import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.infrastructure.TrafficLight;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.utils.Graph;
import com.unimelb.swen30006.partc.utils.GraphStructure;
import com.unimelb.swen30006.partc.utils.Route;
import com.unimelb.swen30006.partc.utils.SpeedUtils;
import com.unimelb.swen30006.partc.utils.angleCal;
import com.unimelb.swen30006.partc.utils.simpleRouteStrategy;


public class Planning implements IPlanning {
	private Car car;
	private Point2D.Double currentPos;
	private World world;
	private Intersection[] intersections;
	private GraphStructure graph;
	
	private Route route;
	
	private boolean tempXY4Test = false;
	//current target
	private Point2D.Double targetPoint;
	//To measure should change target
	private double remainingDistance;
	//Raise if it is final point
	private boolean finalPointFlag = false;
	
	//generator
	public Planning(Car car, World world){
		this.car = car;
		this.currentPos = car.getPosition();
		this.intersections = world.getIntersections();
		this.world =  world;
		targetPoint = null;
		try {
			route = new simpleRouteStrategy(this.intersections, this.world);
			Double point = new Double();
			point.x = 700;
			point.y =700;
			planRoute(point);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	@Override
	public boolean planRoute(Double destination) {
		
		// TODO Auto-generated method stub
		try {
			route.routePlan(car.getPosition(), destination);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		Point2D.Double tmptargetPoint;
		if (shouldTakeNextPoint(car.getPosition(), targetPoint, car.getVelocity())) {
			try {
				//get next point
				targetPoint = route.routePointGetNext(targetPoint);
				//
				if (route.routePointGetNext(targetPoint) == null) {
					finalPointFlag = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// make the car face the target
		tmptargetPoint = targetPoint;
		car.turn(calDeviationAngle(car.getPosition(),tmptargetPoint,car.getVelocity()));
		Point2D.Double safetyPoint = SpeedUtils.getSafetyPoint(car.getPosition(), car.getVelocity(),
				safetyDistance);
		
		if(shouldCarBreak(results, safetyDistance)){
			car.brake();
		}
		else
		{
			if (finalPointFlag) {
				// check if reaching the destination
				if (SpeedUtils.inPointsNear(targetPoint, safetyPoint)) {
					car.brake();
				}
			}
			// judge if intersection is within the safety distance
			if (null != this.world.intersectionAtPoint(safetyPoint)
					&& null != this.world.roadAtPoint(car.getPosition())) {
				// judge traffic light
				TrafficLight trafficLight = this.world.getTrafficLight(this.world.roadAtPoint(car.getPosition()),
						this.world.intersectionAtPoint(safetyPoint));
				if (TrafficLight.RED_COL == trafficLight.getColour()) {
					car.brake();
				} else {
					car.accelerate();
				}
			} else {
				car.accelerate();
			}					
			}

		car.update(delta);
	}


	private boolean shouldTakeNextPoint(Double currentPoint, Double CurrentTarget, Vector2 speed) {
		Double nextpoint = new Point2D.Double();
		nextpoint.x = currentPoint.getX() + speed.x;
		nextpoint.y = currentPoint.getY() + speed.y;
		
		Vector2 c2tPoint = new Vector2();
		c2tPoint.x = (float) (CurrentTarget.getX() - currentPoint.getX());
		c2tPoint.y = (float) (CurrentTarget.getY() - currentPoint.getY());
		Vector2 n2tPoint = new Vector2();
		n2tPoint.x = (float) (CurrentTarget.getX() - nextpoint.getX());
		n2tPoint.y = (float) (CurrentTarget.getY() - nextpoint.getY());
		// TODO Auto-generated method stub
		return (c2tPoint.len()<n2tPoint.len());
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
