/* View license at project root. */

package com.gen.cube;
import javax.swing.JApplet;
import javax.swing.JRootPane;

/* This is the applets starting point */
public class Start extends JApplet{

    	private Display display;	// main display
    
	/* Applet entry point */
    	public void init(){

        	JRootPane rootPane = this.getRootPane();    
        	rootPane.putClientProperty("defeatSystemEventQueueCheck",Boolean.TRUE);
        
        	display = new Display();
        	setContentPane(display);
    	}
}
