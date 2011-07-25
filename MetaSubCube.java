/* View license at project root. */

package com.gen.cube;
import java.awt.Color;

/* Sub cube for meta cube */
public class MetaSubCube{

    	private Color[] side;		// colors of the sub cube faces

	/* Standard color is black */
    	public MetaSubCube(){
        	side = new Color[6];
        	for(int i=0;i<6;i++)
        		side[i] = Color.black;
    	}

	/* Set side */
    	public Color side(int i){
        	return side[i];
    	}

	/* Set the small cube side based on where it is on the large cube */    
    	public void set(int bigCubeSide,Color color){
		
		int smallSide = 0;
		switch(bigCubeSide){
        		case Cube.bottom: 	smallSide = Cube.top;		break;
			case Cube.top:		smallSide = Cube.bottom;	break;
			case Cube.front:	smallSide = Cube.back;		break;
			case Cube.back:		smallSide = Cube.front;		break;
			case Cube.left:		smallSide = Cube.right;		break;
			case Cube.right:	smallSide = Cube.left;		break;
		}
		side[smallSide] = color; 
    	}
    
	/* Rotate */
    	public void rotateRightLeft(){
        	Color temp = side[Cube.back];
        	side[Cube.back] = side[Cube.top];     	// back => top
        	side[Cube.top]  = side[Cube.front];   	// top => front
        
        	side[Cube.front] = side[Cube.bottom]; 	// front => bottom
        	side[Cube.bottom] = temp;        	// bottom => back
    	}

	/* Rotate */    
    	public void rotateBottomTop(){

        	Color temp = side[Cube.left];
        	side[Cube.left] = side[Cube.front];  	// left => front
        	side[Cube.front] = side[Cube.right]; 	// front => right
        
        	side[Cube.right] = side[Cube.back];  	// right => back
        	side[Cube.back] = temp;         	// back => left
    	}

	/* Rotate */    
    	public void rotateBackFront(){

        	Color temp = side[Cube.bottom];
        	side[Cube.bottom] = side[Cube.left];  	// bottom => left
        	side[Cube.left]   = side[Cube.top];   	// left => top
        
        	side[Cube.top]    = side[Cube.right]; 	// top => right
        	side[Cube.right]  = temp;        	// right => bottom
    	}

	/* Get the colors */    
    	public Color[] colors(){
        	return side;
    	}
}
