/* View license at project root. */

package com.gen.cube;
import java.awt.Graphics;
import java.awt.Color;

/* This is for sotring face side info */
public class MetaCube{

    	private MetaSubCube[][][] cube;		// the sub cubes

	/* Set solved */
    	public MetaCube(){
        	cube = new MetaSubCube[3][3][3];
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			for(int z=0;z<3;z++)
        				cube[x][y][z] = new MetaSubCube();
        	setSolved();
    	}

	/* Set as solved rubiks cube */
    	private void setSolved(){   
        
		// bottom
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			cube[x][y][0].set(Cube.bottom,Color.yellow);

        	// top
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			cube[x][y][2].set(Cube.top,Color.cyan);
        
        	// back
        	for(int x=0;x<3;x++)
        		for(int z=0;z<3;z++)
        			cube[x][0][z].set(Cube.back,Color.red);
        
        	// front
        	for(int x=0;x<3;x++)
        		for(int z=0;z<3;z++)
        			cube[x][2][z].set(Cube.front,Color.orange);
        
        	// right
        	for(int y=0;y<3;y++)
        		for(int z=0;z<3;z++)
        			cube[2][y][z].set(Cube.right,Color.green);
        
        	// left
        	for(int y=0;y<3;y++)
        		for(int z=0;z<3;z++)
        			cube[0][y][z].set(Cube.left,Color.blue);
    	}

	/* Rotate a side by a direction, rotation in one direction is 3 in the other */    
    	public void rotate(int side,int dir){

        	if(side==Cube.right){
            		if(dir==Cube.clock)   
				rotateRightLeft(0);
            		if(dir==Cube.counter) 
				for(int i=0;i<3;i++)
        				rotateRightLeft(0);
        	}

        	if(side==Cube.left){
            		if(dir==Cube.clock)   
				rotateRightLeft(2);
            		if(dir==Cube.counter) 
				for(int i=0;i<3;i++)
        				rotateRightLeft(2);
        	}

        	if(side==Cube.top){
            		if(dir==Cube.clock)   
				rotateBottomTop(2);
            		if(dir==Cube.counter)
				for(int i=0;i<3;i++)
        				rotateBottomTop(2);
        	}

        	if(side==Cube.bottom){
            		if(dir==Cube.clock)   
				rotateBottomTop(0);
            		if(dir==Cube.counter)
				for(int i=0;i<3;i++)
        				rotateBottomTop(0);
        	}

        	if(side==Cube.back){
            		if(dir==Cube.clock)   
				rotateBackFront(0);
            		if(dir==Cube.counter)
				for(int i=0;i<3;i++)
        				rotateBackFront(0);
        	}

        	if(side==Cube.front){
            		if(dir==Cube.clock)   
				rotateBackFront(2);
            		if(dir==Cube.counter) 
				for(int i=0;i<3;i++)
        				rotateBackFront(2);
        	}
    	}

	/* Rotate */
    	private void rotateBackFront(int val){

        	MetaSubCube temp  = cube[0][val][0];
        	cube[0][val][0] = cube[0][val][2];	// 00 - 02
        	cube[0][val][2] = cube[2][val][2];	// 02 - 22
        	cube[2][val][2] = cube[2][val][0];	// 22 - 20
        	cube[2][val][0] = temp;             	// 02 - 22
        
        	temp            = cube[0][val][1];
        	cube[0][val][1] = cube[1][val][2];  	// 01 - 12
        	cube[1][val][2] = cube[2][val][1];  	// 12 - 21
        	cube[2][val][1] = cube[1][val][0];  	// 21 - 10
        	cube[1][val][0] = temp;             	// 10 - 01
        
        	for(int x=0;x<3;x++)
        		for(int z=0;z<3;z++)
        			cube[x][val][z].rotateBackFront();
    	}

	/* Rotate */
    	private void rotateBottomTop(int val){

        	MetaSubCube temp  = cube[0][0][val];
        	cube[0][0][val] = cube[0][2][val];  	// 00 - 02
        	cube[0][2][val] = cube[2][2][val];  	// 02 - 22
      		cube[2][2][val] = cube[2][0][val];  	// 22 - 20
        	cube[2][0][val] = temp;             	// 02 - 22
        
        	temp            = cube[0][1][val];
        	cube[0][1][val] = cube[1][2][val];  	// 01 - 12
        	cube[1][2][val] = cube[2][1][val];  	// 12 - 21
        	cube[2][1][val] = cube[1][0][val]; 	// 21 - 10
        	cube[1][0][val] = temp;            	// 10 - 01
        
        	for(int x=0;x<3;x++)
        		for(int y=0;y<3;y++)
        			cube[x][y][val].rotateBottomTop();
    	}

	/* Rotate */    
    	private void rotateRightLeft(int val){

        	MetaSubCube temp  = cube[val][0][0];
        	cube[val][0][0] = cube[val][0][2];  	// 00 - 02
        	cube[val][0][2] = cube[val][2][2];  	// 02 - 22
        	cube[val][2][2] = cube[val][2][0];  	// 22 - 20
        	cube[val][2][0] = temp;             	// 02 - 22
        
        	temp            = cube[val][0][1];
        	cube[val][0][1] = cube[val][1][2];  	// 01 - 12
        	cube[val][1][2] = cube[val][2][1];  	// 12 - 21
        	cube[val][2][1] = cube[val][1][0];  	// 21 - 10
        	cube[val][1][0] = temp;             	// 10 - 01
        
        	for(int y=0;y<3;y++)
        	for(int z=0;z<3;z++)
        	cube[val][y][z].rotateRightLeft();
    	}
 
	/* Return the cube's colors */   
    	public Color[] colors(int x,int y,int z){   
        	return cube[x][y][z].colors();
    	}
}
