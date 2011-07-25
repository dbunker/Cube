/* View license at project root. */

package com.gen.cube;
import java.awt.Color;
import java.awt.Graphics;

/* Part of the larger cube */
public class SubCube implements Comparable{

    	private Face[] face;				// 6 faces of the subcube
    	private VirtualVect vect;			// the virtual space the subcube is associated with 
    	private Point[] point;				// 8 points of the subcube
    	private Point center;				// center of subcube
    	private int rotateSide;				// indeicates if subcube is rotating

	public static final double third = 1.0/3.0;
    
	/* Set points into a standard cube position */ 
    	public SubCube(Color[] colors,VirtualVect vv,double len1,double len2,double len3){

        	rotateSide = -1;
        	vect = vv;
        	point = new Point[8];
        
        	point[0] = new Point(vect,len1      ,len2      ,len3);
        	point[1] = new Point(vect,len1+third,len2      ,len3);
        	point[2] = new Point(vect,len1+third,len2+third,len3);
        	point[3] = new Point(vect,len1      ,len2+third,len3);
        
        	point[4] = new Point(vect,len1      ,len2      ,len3+third);
        	point[5] = new Point(vect,len1+third,len2      ,len3+third);
        	point[6] = new Point(vect,len1+third,len2+third,len3+third);
        	point[7] = new Point(vect,len1      ,len2+third,len3+third);
        
        	center = point[0].midPoint(point[6]);
        
        	face = new Face[6];
        
        	face[Cube.bottom] = new Face(colors[0],point[0],point[1],point[2],point[3]); 	// bottom
        	face[Cube.top]    = new Face(colors[1],point[4],point[5],point[6],point[7]); 	// top
        
        	face[Cube.back]   = new Face(colors[2],point[0],point[1],point[5],point[4]); 	// front
        	face[Cube.front]  = new Face(colors[3],point[2],point[3],point[7],point[6]); 	// back
        
        	face[Cube.left]   = new Face(colors[4],point[1],point[2],point[6],point[5]); 	// right
        	face[Cube.right]  = new Face(colors[5],point[3],point[0],point[4],point[7]); 	// left
    	}

	/* Set is rotating */ 
    	public void setRot(int rot){
		rotateSide = rot;
	}

	/* Get rotating */
	public int getRot(){
		return rotateSide;
	}
    	
	/* Sorted by how far forward subcubes are, rotation taken into account */
    	public int compareTo(Object oth){

        	SubCube other = (SubCube) oth;
        
        	if(rotateSide>-1 && other.getRot()==-1){
            		if(displaysBlack())
            			return -1;
            		return 1;
        	}
        
        	if(rotateSide==-1 && other.getRot()>-1){

            		if(other.displaysBlack())
            			return 1;
            		return -1;
        	}
        
        	if(center.compareTo(other.getCent()) > 0)
        		return 1;
        
		return -1;
    	}

	/* Checks if it shouldn't appear and should be black */
    	private boolean displaysBlack(){

        	if(rotateSide==Cube.top    && face[Cube.bottom].compareTo(face[Cube.top])>0 ) return true; 	// bottom > top
        	if(rotateSide==Cube.bottom && face[Cube.bottom].compareTo(face[Cube.top])<0 ) return true;
        
        	if(rotateSide==Cube.front && face[Cube.front].compareTo(face[Cube.back])<0 ) return true; 	// front > back
        	if(rotateSide==Cube.back  && face[Cube.front].compareTo(face[Cube.back])>0 ) return true;
        
        	if(rotateSide==Cube.right && face[Cube.right].compareTo(face[Cube.left])<0 ) return true; 	// right > left
        	if(rotateSide==Cube.left  && face[Cube.right].compareTo(face[Cube.left])>0 ) return true;
        
        	return false;
    	}
    
	/* get Center */
    	public Point getCent(){
     		return center;
    	}
    
	/* Get point */
   	public Point getPoint(int i){
       		return point[i];
    	}
    
	/* Get face */
    	public Face getFace(int i){
        	return face[i];
    	}

	/* Paint subcube */    
    	public void paint(Graphics g){

        	if(face[Cube.bottom].compareTo(face[Cube.top])>0) face[Cube.bottom].paint(g); 	// bottom > top
        	else                                              face[Cube.top].paint(g);
        	if(face[Cube.front].compareTo(face[Cube.back])>0) face[Cube.front].paint(g); 	// front > back
        	else                                              face[Cube.back].paint(g);
        	if(face[Cube.right].compareTo(face[Cube.left])>0) face[Cube.right].paint(g); 	// right > left
        	else                                              face[Cube.left].paint(g); 
    	}
}
