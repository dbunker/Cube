/* View license at project root. */

package com.gen.cube;

/* This is for translating points between the real axis and virtual axis */
public class VirtualVect{

    	private double[] v1;				// first axis direction
    	private double[] v2;				// second axis direction
    	private double[] v3;				// third axis direction
							// all these are perpendicular 
							// sqrt( v1^2 + v2^2 + v3^2 ) = 1.0
	public static final int moveSens = 1000;	// move sensitivity

	/* These values create a cube with a corner pointing forward */
    	public VirtualVect(){

        	v1 = new double[3];
        	v2 = new double[3];
        	v3 = new double[3];
   
        	v1[0] =  0.49874749330202717; v1[1] =  0.46463139916614854; 
		v1[2] =  0.73168886887382090; 

		v2[0] = -0.76746668800466360; v2[1] = -0.15557319957714863; 
		v2[2] =  0.62192592997597460;

        	v3[0] =  0.40279749344013026; v3[1] = -0.87173083143951930; 
		v3[2] =  0.27899737774693795;
    	}

	/* Define values specifically */    
    	public VirtualVect(double[] ve1,double[] ve2,double[] ve3){
        	v1=ve1;
        	v2=ve2;
        	v3=ve3;
    	}

	/* Moves the axisToMove around the perpendicular axisPerpend
	   Here there be dragons */    
    	private double[] move(double change,double[] axisToMove,double[] axisPerpend){

        	double[] N = axisPerpend;
        	double[] M = axisToMove;
        	double[] P = new double[3];
        	double[] L = new double[3];
        
		// keep z same
        	P[2] = M[2];
        
		// change x in relation to z
        	P[0] = P[2] * 	( N[1]*M[2] - N[2]*M[1] ) / 
                      		( N[0]*M[1] - N[1]*M[0] );
 
		// change y in relation to x and z
        	P[1] = - ( P[0]*M[0] + P[2]*M[2] ) / M[1];
        
        	P = normalize(P);
        
		double dir = N[0]*M[1] - N[1]*M[0];
        	if(dir>0)
        		change = -change;
        
        	if(P[2]>0)
        		change = -change;
        
        	L[0] = P[0]*change + M[0];
        	L[1] = P[1]*change + M[1];
        	L[2] = P[2]*change + M[2];
        
		double[] retMovedAxis = normalize(L);
        	return retMovedAxis;
    	}

	/* Normalize axis */    
    	private double[] normalize(double[] v){
        	double[] vr = new double[3];
        	double absolute = Math.sqrt( v[0]*v[0] + v[1]*v[1] + v[2]*v[2] );
        	vr[0] = v[0]/absolute;
        	vr[1] = v[1]/absolute;
        	vr[2] = v[2]/absolute;
        	return vr;
    	}

	/* Only move v2 v3 axis */    
    	public void v1Still(double change){
       		v2 = move(change,v2,v1); 
        	v3 = move(change,v3,v1);
    	}
    
	/* Only move v1 v3 axis */
    	public void v2Still(double change){
        	v1 = move(change,v1,v2);
        	v3 = move(change,v3,v2);
    	}

	/* Only move v1 v2 axis */    
    	public void v3Still(double change){
        	v1 = move(change,v1,v3);
        	v2 = move(change,v2,v3);
    	}

	/* Get v1 */    
    	public double[] getV1(){
        	double[] vr = new double[3];
        	vr[0] = v1[0];
        	vr[1] = v1[1];
        	vr[2] = v1[2];
        	return vr;
    	}
    
	/* Get v2 */
	public double[] getV2(){
        	double[] vr = new double[3];
        	vr[0] = v2[0];
        	vr[1] = v2[1];
        	vr[2] = v2[2];
        	return vr;
    	}

	/* Get v3 */
    	public double[] getV3(){
        	double[] vr = new double[3];
        	vr[0] = v3[0];
        	vr[1] = v3[1];
        	vr[2] = v3[2];
        	return vr;
    	}

	/* Do the projection from virtual to real point */    
    	public double[] getProj(double LenAlonV1,double LenAlonV2,double LenAlonV3){

        	double[] ver = new double[3];
        	ver[0] = LenAlonV1*v1[0] + LenAlonV2*v2[0] + LenAlonV3*v3[0];
        	ver[1] = LenAlonV1*v1[1] + LenAlonV2*v2[1] + LenAlonV3*v3[1];
        	ver[2] = LenAlonV1*v1[2] + LenAlonV2*v2[2] + LenAlonV3*v3[2];
        	return ver;
    	}
    
	/* move horizontal */
    	public void horiz(double direct){

        	direct = direct/moveSens;
        	for(int i=0;i<moveSens;i++)
        		rightLeft(direct);
    	}

	/* Move right left */    
    	private void rightLeft(double angle){

        	double tanAng = Math.tan(angle);
        
        	double[] right = new double[3];
        	right[0] = 0;
        	right[1] = 1;
        	right[2] = 0;
        
        	double v1Dist = Math.sqrt(v1[0]*v1[0] + v1[2]*v1[2])*tanAng;
        	double v2Dist = Math.sqrt(v2[0]*v2[0] + v2[2]*v2[2])*tanAng;
        	double v3Dist = Math.sqrt(v3[0]*v3[0] + v3[2]*v3[2])*tanAng;
        
        	v1 = move(v1Dist,v1,right);
        	v2 = move(v2Dist,v2,right);
        	v3 = move(v3Dist,v3,right);
    	}

	/* Move vertical */    
    	public void vert(double direct){

        	direct = direct/moveSens;
        	for(int i=0;i<moveSens;i++)
        		upDown(direct);
    	}
 
	/* Move up down */   
    	private void upDown(double angle){

        	double tanAng = Math.tan(angle);
        
        	double[] up = new double[3];
        	up[0] = 1;
       		up[1] = 0;
        	up[2] = 0;
        
        	double v1Dist = Math.sqrt(v1[1]*v1[1] + v1[2]*v1[2])*tanAng;
        	double v2Dist = Math.sqrt(v2[1]*v2[1] + v2[2]*v2[2])*tanAng;
        	double v3Dist = Math.sqrt(v3[1]*v3[1] + v3[2]*v3[2])*tanAng;
        
        	v1 = move(v1Dist,v1,up);
        	v2 = move(v2Dist,v2,up);
        	v3 = move(v3Dist,v3,up);
    	}
}
