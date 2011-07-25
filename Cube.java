/* View license at project root. */

package com.gen.cube;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

/* The main cube used for display */
public class Cube{

    	private SubCube[][][] cube;			// the set of 3*3*3=27 small cubes
    	private VirtualVect vect;			// associated virtual axis
    	private ArrayList<SubCube> displayOrder;	// display order of the sub cubes
    	private MetaCube meta;				// the meta cube to store side colors
    	private VirtualVect spinVirt;			// virtual axis used when rotating a side
    	private int numSpin;				// spins into rotation
    	private int rotateDir;				// direction or rotation
    	private int rotateSide;				// side of rotation

	public static final double drawFreq = 1.0;			// draw frequency
	public static final double rotateMove = 0.04 / drawFreq;	// step size
	public static final int maxNumSpin = (int) (40 * drawFreq);	// max number of spins

	// related to cube place and rotation
    	public static final int top=0, bottom=1, front=2, back=3, left=4, right=5;
    	public static final int counter=-1, clock=1;

	public static final double third = 1.0/3.0;
	public static final double half  = 1.0/2.0;

	/* Cube */
    	public Cube(){

        	meta = new MetaCube();
        	vect = new VirtualVect();
		buildCube();
	}

	/* Set sub cube info */
	public void buildCube() {

		spinVirt = new VirtualVect();
        	numSpin = maxNumSpin;
        	
        	cube = new SubCube[3][3][3];
        
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			for(int z=0;z<3;z++)
        				cube[x][y][z] = new SubCube(meta.colors(x,y,z),vect,
								x*third-half,
								y*third-half,
								z*third-half);
        
        	generate();
    	}
    
    	/* Create array list of subcubes */
	private void generate(){
        
		displayOrder = new ArrayList<SubCube>();
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			for(int z=0;z<3;z++)
        				displayOrder.add(cube[x][y][z]);
    	}
    
    	/* Paint subcubes */
	public void paint(Graphics g){
        
		order();
        	for(int i=0;i<displayOrder.size();i++)
        		displayOrder.get(i).paint(g);
    	}
    
    	/* Order subcubes */
	private void order(){   
        	Collections.sort(displayOrder);
    	}
    
    	/* Rotate a side */
	public void rotate(int side,int direct){
        	rotateDir = direct;
        	rotateSide = side;

		switch(rotateSide){
			case front: 	rotateBackFront(2);	break;
    			case back:	rotateBackFront(0);	break;
    			case top:	rotateBottomTop(2);	break;
	    		case bottom:	rotateBottomTop(0);	break;
			case left:	rotateRightLeft(2);	break;
    			case right:	rotateRightLeft(0);	break;
		}
    	}

    	/* Rotate */
	private void rotateBackFront(int val){

        	spinVirt = new VirtualVect(vect.getV1(),vect.getV2(),vect.getV3());
        	numSpin = 0;
        
        	for(int x=0;x<3;x++)
        		for(int z=0;z<3;z++)
        			cube[x][val][z] = new SubCube(meta.colors(x,val,z),spinVirt,
							x  *third-half,
							val*third-half,
							z  *third-half);
               
        	for(int x=0;x<3;x++)
        		for(int z=0;z<3;z++)
        			cube[x][val][z].setRot(rotateSide);
        
        	generate();
    	}
    
    	/* Rotate */
	private void rotateBottomTop(int val){

        	spinVirt = new VirtualVect(vect.getV1(),vect.getV2(),vect.getV3());
        	numSpin = 0;
        
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			cube[x][y][val] = new SubCube(meta.colors(x,y,val),spinVirt,  
							x  *third-half,
							y  *third-half,
							val*third-half);
               
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			cube[x][y][val].setRot(rotateSide);
        
        	generate();
    	}
    
    	/* Rotate */
	private void rotateRightLeft(int val){

        	spinVirt = new VirtualVect(vect.getV1(),vect.getV2(),vect.getV3());
        	numSpin = 0;
        
        	for(int y=0;y<3;y++)
        		for(int z=0;z<3;z++)
        			cube[val][y][z] = new SubCube(meta.colors(val,y,z),spinVirt,
							val*third-half,
                  					y  *third-half,
                   					z  *third-half);
        
        	for(int y=0;y<3;y++)
        		for(int z=0;z<3;z++)
        			cube[val][y][z].setRot(rotateSide);
        
        	generate();
    	}
    
    	/* Rotate meta side */
	public void rotateMetaSide(){
        	meta.rotate(rotateSide,rotateDir);
    	}
    
    	/* Mid rotation action */
	public void continueRotate(){
        	if(rotateSide==right || rotateSide==left)   
			spinVirt.v1Still( rotateDir * rotateMove);
        	if(rotateSide==top   || rotateSide==bottom) 
			spinVirt.v3Still( rotateDir * rotateMove);
        	if(rotateSide==back  || rotateSide==front)  
			spinVirt.v2Still(-rotateDir * rotateMove);
        	numSpin++;
    	}
    
	/* Indicates if still rotating a side */
    	public boolean stillRotate(){
        	if(numSpin<maxNumSpin)
        		return true;
        	return false;
    	}
    
    	/* Indicates if a face is clicked on
	   x=0:left x=2:right y=0:bottom y=2:top z=0:back z=2:front */
    	public boolean mouse(int side,int x,int y){
        
		if(side == left  ) return inFace(cube[2][1][1],x,y,side);
        	if(side == right ) return inFace(cube[0][1][1],x,y,side);
        	if(side == bottom) return inFace(cube[1][1][0],x,y,side);
        	if(side == top   ) return inFace(cube[1][1][2],x,y,side);
        	if(side == back  ) return inFace(cube[1][0][1],x,y,side);
        	if(side == front ) return inFace(cube[1][2][1],x,y,side);
        	return false;
    	}
    
	/* True if (x,y) are in the side */
    	private boolean inFace(SubCube cube,int x,int y,int side){
        	if(cube.getCent().getZ() > 0)
        		return cube.getFace(side).contains(x,y);
        	return false;
    	}
    
    	/* Horizontal movement */
	public void horiz(double x){
        	vect.horiz(x);
        	spinVirt.horiz(x);
    	}
    
   	/* Vertical movement */
	public void vert(double y){
        	vect.vert(y);
        	spinVirt.vert(y);
    	}
}
