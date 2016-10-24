package com.unimelb.swen30006.partc.roads;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.*;

/**
 * This class provides functionality for use within the simulation system. It is NOT intended to be
 * read or understood for SWEN30006 Part C. Comments have been intentionally removed to reinforce
 * this. We take no responsibility if you use all your time trying to understand this code.
 * @author Mathew Blair <mathew.blair@unimelb.edu.au>
 * @version 1.0
 */
public final class Intersection {

	private static final Color INTERSECTION_COLOUR = Color.DARK_GRAY;
	private static final Color INTERSECTION_LINE = Color.LIGHT_GRAY;
	private static final float INTERSECTION_LINE_WIDTH = 1.0f;

	public enum Direction {
		North, South, East, West
	}

	// Final values for the roads and size of intersection
	public final float width;
	public final float length;

	public final HashMap<Direction, Road> roads;
	public final Point2D.Double pos;
	
	// Information for determining if we are in an intersection
	private Rectangle2D.Double shape;
	
	public Intersection(Point2D.Double point, float width, float length) {
		// Initialize the variables
		this.width = width;
		this.length = length;
		this.roads = new HashMap<Direction, Road>();
		this.pos = point;
		this.shape = new Rectangle2D.Double((float)point.x, (float)point.y, width, length);	}
	
	public void registerRoad(Direction d, Road r){
		this.roads.put(d, r);
	}
	
	public boolean containsPoint(Point2D.Double pos){
		System.out.println(pos);
		System.out.println(this.shape);
		return this.shape.contains(pos);
	}
	
	public void render(ShapeRenderer r){
		Color old = r.getColor();
		r.setColor(INTERSECTION_COLOUR);
		r.rect((float)(this.pos.x), (float)(this.pos.y), width, length);
		// Draw the lines around the intersection
		r.setColor(INTERSECTION_LINE);
		r.rectLine((float)this.pos.x, (float)this.pos.y + INTERSECTION_LINE_WIDTH/2, 
				   (float)this.pos.x + width, (float)this.pos.y + INTERSECTION_LINE_WIDTH/2, 
				   INTERSECTION_LINE_WIDTH);
		r.rectLine((float)this.pos.x + width - INTERSECTION_LINE_WIDTH/2, (float)this.pos.y, 
				   (float)this.pos.x + width -INTERSECTION_LINE_WIDTH/2, (float)this.pos.y+length,
				   INTERSECTION_LINE_WIDTH);
		r.rectLine((float)this.pos.x + INTERSECTION_LINE_WIDTH/2, (float)this.pos.y,
				   (float)this.pos.x + INTERSECTION_LINE_WIDTH/2, (float)this.pos.y + length, 
				   INTERSECTION_LINE_WIDTH);
		r.rectLine((float)this.pos.x, (float)this.pos.y+length - INTERSECTION_LINE_WIDTH/2, 
				   (float)this.pos.x + width, (float)this.pos.y + length - INTERSECTION_LINE_WIDTH/2, 
				   INTERSECTION_LINE_WIDTH);
		// Set the old colours back
		r.setColor(old);
	}
	
	public boolean isConnected(Intersection intersection){
		boolean result = false;
		//exclude this intersection itself;
		if(this.pos == intersection.pos){
			return result;
		}
		
		Iterator<Road> iter1 = roads.values().iterator();
		Iterator<Road> iter2 = intersection.roads.values().iterator();
		while(iter1.hasNext()){
			Road road = iter1.next();
			while(iter2.hasNext()){
				result = iter2.next().equals(road);
			}
		}
		return result;
	}

	public String getDirectionByRoad(Road road){
		String Dir = null;
		Direction dir = null;
		Iterator<HashMap.Entry<Direction, Road>> iter = roads.entrySet().iterator();
		while(iter.hasNext()){
			HashMap.Entry<Direction, Road> nextentry = iter.next();
			if(nextentry.getValue().equals(road)){
				dir = nextentry.getKey();
			}
		}
		if(dir == Direction.North){
			Dir = "North";
		}else if(dir == Direction.South){
			Dir = "South";
		}else if(dir == Direction.East){
			Dir = "East";
		}else if(dir == Direction.West){
			Dir = "West";
		}else{
			System.out.println("Not connected!");
		}
		return Dir;
	}
	
	// get the point in the intersection where the car can turn based on the entry road
	public Point2D.Double getShiftPoint(Road road){
		Point2D.Double point = pos;
		String Dir = getDirectionByRoad(road);
		if(Dir == "North"){
			point.x = pos.x + width/4;
			point.y = pos.y + length/4;
		}else if(Dir == "South"){
			point.x = pos.x - width/4;
			point.y = pos.y - length/4;
		}else if(Dir == "East"){
			point.x = pos.x + width/4;
			point.y = pos.y - length/4;
		}else if(Dir == "West"){
			point.x = pos.x - width/4;
			point.y = pos.y + length/4;
		}
		return point;
	}
}
