package com.unimelb.swen30006.partc.utils;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class angleCal {

	public static double AngleBetween(Vector2 vector1, Vector2 vector2)
	{
	    double sin = vector1.x * vector2.y - vector2.x * vector1.y;  
	    double cos = vector1.x * vector2.x + vector1.y * vector2.y;
	    double angle =Math.atan2(sin, cos) * (180 / Math.PI);
	    return angle;
	}
}
