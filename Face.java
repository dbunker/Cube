/* View license at project root. */

package com.gen.cube;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;

/* This is used by cube to represent a sub cube face that may be displayed
 * or clicked on */
public class Face implements Comparable{

    	private Point[] point;	// points on the real axis may change
    	private Color color;	// color of face

	/* Face is defined by four points */
    	public Face(Color col,Point p1,Point p2,Point p3,Point p4){

        	point = new Point[4];
        	point[0] = p1;
        	point[1] = p2;
        	point[2] = p3;
        	point[3] = p4;
        	color = col;
    	}

	/* Color */    
    	public Color getCol(){
        	return color;
    	}

	/* Center */
    	public Point getCent(){
        	return point[0].midPoint(point[2]);
    	}

	/* For ordering faces by which is closest to the front */
    	public int compareTo(Object oth){

        	Face other = (Face) oth;
		Point center = getCent();

        	if(center.compareTo(other.getCent())>0)
        		return 1;
        	return -1;
    	}

	/* Determine if the input (x,y) pixel is in face */    
    	public boolean contains(int x1,int y1){

		int[] x = new int[4];
        	int[] y = new int[4];
        	create(x,y);

        	Polygon poly = new Polygon(x,y,4);
        	return poly.contains(x1,y1);
    	}

	/* Create x and y arrays based on real point positions, 
	   the virtual points projected from VirtualVect */    
    	private void create(int[] x,int[] y){
        
		x[0] = Display.conv(point[0].getX());
        	x[1] = Display.conv(point[1].getX());
        	x[2] = Display.conv(point[2].getX());
        	x[3] = Display.conv(point[3].getX());
        
        	y[0] = Display.conv(point[0].getY());
        	y[1] = Display.conv(point[1].getY());
        	y[2] = Display.conv(point[2].getY());
        	y[3] = Display.conv(point[3].getY());
    	}
 
	/* Paint the face */   
    	public void paint(Graphics g){
        
		int[] x = new int[4];
        	int[] y = new int[4];
		create(x,y);

        	g.setColor(color);
        	g.fillPolygon(x,y,4);
        	g.setColor(Color.black);
        	g.drawPolygon(x,y,4);
    	}
}
