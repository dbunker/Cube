/* View license at project root. */

package com.gen.cube;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* This class deals with managing the UI */
public class Display extends JPanel implements MouseListener, MouseMotionListener{

    	private Cube cube;				// the main cube
    	private int dragX,dragY;			// dragged pixel x,y
    	private Timer tim;				// rotation timer
	public static final int rotateTime = 20;	// miliseconds to wait for each side semi-rotation
	public static final double convMult = 200.0;	// size of cube
	public static final double convAdd = 210.0;	// (x,y) place of cube
	public static final double moveSpeed = 0.01;	// speed of spin durring mouse drag

	/* Display sets up handling mouse events and timer */
    	public Display(){

        	cube = new Cube();
        	setFocusable(true);
        	addMouseMotionListener(this);
        	addMouseListener(this);

        	tim  = new Timer(rotateTime,new MyTimer());
        	setBackground(Color.white);
    	}
    
	/* Paint */
    	public void paintComponent(Graphics g){
        	super.paintComponent(g);
		cube.paint(g);
    	}
    	
	/* For spinning */
    	public void mousePressed(MouseEvent me){	
        	dragX = me.getX();
        	dragY = me.getY();
    	}

	/* For rotating */
    	public void mouseClicked(MouseEvent me){	
        	int x = me.getX();
        	int y = me.getY();
		int button = me.getButton();

		if(cube.stillRotate())
			return;

		freeRotate(x,y,button);
    	}
    
	/* For spinning */
    	public void mouseDragged(MouseEvent me){
        
		double x = (me.getX() - dragX) * moveSpeed;
        	double y = (me.getY() - dragY) * moveSpeed;
                
        	dragX = me.getX();
        	dragY = me.getY();
                
        	cube.horiz(x);
        	cube.vert(-y);
        	repaint();
    	}
 
	/* Each possible rotation */   
    	private void freeRotate(int x,int y,int button){

        	int dir = Cube.clock;
        	if(button == MouseEvent.BUTTON1) 
        		dir = Cube.counter;

        	     if (cube.mouse(Cube.right ,x,y)) {cube.rotate(Cube.right ,dir);	tim.start();}
        	else if (cube.mouse(Cube.left  ,x,y)) {cube.rotate(Cube.left  ,dir);	tim.start();}
        	else if (cube.mouse(Cube.bottom,x,y)) {cube.rotate(Cube.bottom,dir);	tim.start();}
        	else if (cube.mouse(Cube.top   ,x,y)) {cube.rotate(Cube.top   ,dir);	tim.start();}
        	else if (cube.mouse(Cube.back  ,x,y)) {cube.rotate(Cube.back  ,dir);	tim.start();}
        	else if (cube.mouse(Cube.front ,x,y)) {cube.rotate(Cube.front ,dir);	tim.start();} 
    	}
    
	/* Rotation timer */
    	private class MyTimer implements ActionListener{

        	public MyTimer(){}
        	public void actionPerformed(ActionEvent event){
            
			cube.continueRotate();
            		if(!cube.stillRotate()){
                		tim.stop();
                		cube.rotateMetaSide();
                		cube.buildCube();
            		}
            		repaint(); 
        	}
    	}
    
	/* Allows for resizing and placement of cube */
    	public static int conv(double val){
        	return (int) (val * convMult + convAdd);
    	}

	/* Unimplemented methods from MouseEvent interface */
    	public void mouseExited(MouseEvent me){}
    	public void mouseEntered(MouseEvent me){}
    	public void mouseReleased(MouseEvent me){}
    	public void mouseMoved(MouseEvent me){}
}
