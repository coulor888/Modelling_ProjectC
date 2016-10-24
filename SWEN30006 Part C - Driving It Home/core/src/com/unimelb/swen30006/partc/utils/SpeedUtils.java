package com.unimelb.swen30006.partc.utils;

import java.awt.geom.Point2D;

import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.regexp.internal.recompile;

public class SpeedUtils {

	public static int getSafetyDistance(double currentV, double breakacc,int bodyLength){
		int distance = 0;
		if (0 == breakacc) {
			distance = 0;
		}
		else {
			distance = (int) (currentV*currentV / (2*breakacc)+ bodyLength);
		}
		return distance;
	}
	
	public static boolean isPointInTrace(Point2D.Double point, Point2D.Double traceStart, Point2D.Double traceEnd) {
		boolean inTrace = (point.getX()-traceStart.getX())*(traceStart.getY()-traceEnd.getY())==(traceStart.getX()-traceEnd.getX())*(point.getY()-traceStart.getY());
		return inTrace;
	}
	
	public static Point2D.Double getSafetyPoint(Point2D.Double startingPoint,Vector2 velocity,
			int safetyDistance){

		Point2D.Double SafetyPoint = new Point2D.Double(startingPoint.getX(), startingPoint.getY());
		if(0 != velocity.len()){
			SafetyPoint.x += velocity.x /velocity.len() * safetyDistance;
			SafetyPoint.y += velocity.y /velocity.len() * safetyDistance;
		}

		return SafetyPoint;
	}

	public static boolean inPointsNear(Point2D.Double pointA, Point2D.Double pointB){
		Vector2 distance = new Vector2();
		distance.x = (float) (pointB.getX() - pointA.getX());
		distance.y = (float) (pointB.getY() - pointA.getY());
		return (Math.abs(distance.len())<0.08);
		
	}
}
