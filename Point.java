/* View license at project root. */

package com.gen.cube;
import java.awt.Color;

/* Point in space */
public class Point implements Comparable{

    	private VirtualVect vect;	// the virtual 3d axis this point is associated with
    	private double[] virtPos;	// the 3 coordinates in virtual space

	/* Define point */    
    	public Point(VirtualVect vv,double len1,double len2,double len3){
		
        	vect = vv;
        	virtPos = new double[3];
        	virtPos[0] = len1;
        	virtPos[1] = len2;
        	virtPos[2] = len3;
    	}

	/* Translate virtual point to point on real (pixel) axis */	
	private double[] getActualPosition(){

	    	double[] point = vect.getProj(virtPos[0],virtPos[1],virtPos[2]);
	    	return point;
	}

	/* Real x */	
	public double getX(){
	    	double[] point = getActualPosition();
	    	return point[0];
	}

	/* Real y */	
	public double getY(){
	    	double[] point = getActualPosition();
	    	return point[1];
	}

	/* Real Z */
	public double getZ(){
	    	double[] point = getActualPosition();
	    	return point[2];
	}

	/* Virtual point */	
	public double[] getVirt(){
	    	return virtPos;
	}

	/* Points closer further forward will be ranked higher */
	public int compareTo(Object oth){
	    	Point other = (Point) oth;
	    	if(this == other)
	    		return 0;

	    	if(getZ() > other.getZ())
	    		return 1;
	    	return -1;
	}

	/* Midpoint */	
	public Point midPoint(Point other){

		double[] otherVirt = other.getVirt();
	    
	    	double x = (otherVirt[0] + virtPos[0])/2;
	    	double y = (otherVirt[1] + virtPos[1])/2;
	    	double z = (otherVirt[2] + virtPos[2])/2;
	    	return new Point(vect,x,y,z);
	}
}
