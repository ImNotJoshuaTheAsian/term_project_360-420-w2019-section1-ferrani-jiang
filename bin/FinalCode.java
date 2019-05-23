//importa libraries
import java.io.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Scanner;        
import java.text.DecimalFormat;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;  
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;


public class FinalCode
{
	//all the constant coefficient that will be used. All units are in SI
	public static final double angleAlpha = 0.0375;
	public static final double dt = 0.001;
	public static final double tMax = 50;
	public static final double densityAir = 0.00122;
	public static final double densityStone = 1;
	public static final double dragCoefficient = 0.5;
	public static final double stoneThickness = 1;
	public static final double gravity = 980;
	public static final double radius = 4;

	public static void main(String[] args)
	{

		double counter = 0;						//counter of number of skips
		double xInitial, yInitial;					//initial position of x and y
		Boolean previousState = false;					//records the state of the stone at y[i-1]; false means in the air, true means in water
		Boolean currentState = false;					//records the state of the stone at y[i]; false means in the air, true means in water
		
		//initial values of everything
		xInitial = 0;
		yInitial = 50;

		int imax = (int) (tMax/dt);

		//declaring arrays for speed, time, velocity, angle, etc...
		double[] t = new double[imax];	

		double[] x1 = new double[imax];  						
		double[] vx1 = new double[imax];					
		double[] ax1 = new double[imax];	

		double[] y1 = new double[imax];							
		double[] vy1 = new double[imax];						
		double[] ay1 = new double[imax];

		double[] speed = new double[imax];						
		double[] angleBeta = new double[imax];  	
		
		//initial conditions of the arrays
		t[0] = 0;
		x1[0] = xInitial;
		y1[0] = yInitial;

		vx1[0] = 50000;
		vy1[0] = 0;

		speed[0] = Math.sqrt(vx1[0] * vx1[0] + vy1[0] * vy1[0]);
		angleBeta[0] = Math.atan(vy1[0]/vx1[0]);

		ax1[0] = accelerationx1(angleBeta[0],speed[0]);
		ay1[0] = accelerationy1(angleBeta[0],speed[0]);
		
		//start for loop
		for (int i = 1; i < imax; i++)
		{
			//when the velocity in x is below 0, the rock stops moving
			//end code such as print
			if(vx1[i-1] < 0)
			{
				// This is the code for the end of rock skipping simulation
				System.out.println("Rock has stopped skipping");

				counter = counter ;
				System.out.println("Rock has stopped moving");
				System.out.println("Number of skips: " + counter);
				System.out.println("Time that rock has been alive: " + i * dt);
				double[] xRealPosition = new double[i];
				double[] yRealPosition = new double[i];

				xRealPosition = Arrays.copyOfRange(x1, 0, i);
				yRealPosition = Arrays.copyOfRange(y1, 0, i);

				Plot2DPanel myPlot = new Plot2DPanel();
				myPlot.addLinePlot("trajectory", Color.RED, xRealPosition, yRealPosition);
				new FrameView(myPlot).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				while(true)
				{

				}

			}
			
			//when the rock is moving and its velocity in x is above 0
			else
			{
				// main skipping loop
				previousState = currentState;
				
				//when the rok is in the air
				if (y1[i-1] > 0)
				{
					// The rock is moving/accelerating normally in the air
					currentState = false;
					
					//formula for new conditions like speed, angle, acceleration
					vx1[i] = vx1[i-1] + ax1[i-1] * dt;
					//System.out.println("vx1: " + vx1[i]);
					vy1[i] = vy1[i-1] + ay1[i-1] * dt;
					//System.out.println("vy1: " + vy1[i]);

					x1[i] = x1[i-1] + vx1[i-1] * dt;
					y1[i] = y1[i-1] + vy1[i-1] * dt;

					speed[i] = Math.sqrt(vx1[i] * vx1[i] + vy1[i] * vy1[i]);
					//System.out.println("speed: " + speed[i]);
					angleBeta[i] = Math.atan(vy1[i]/vx1[i]);

					ax1[i] = accelerationx1(angleBeta[i],speed[i]);
					//System.out.println("ax1: " + ax1[i]);
					ay1[i] = accelerationy1(angleBeta[i],speed[i]);
					//System.out.println();

				}
				
				//when the rock is under water ( a different formula to calculate speed angle and acceleration
				else
				{
					currentState = true;
					System.out.println("Rock under water");
					// The rock is under the water

					if (previousState != currentState)
					{
						counter++;
					}
					
					//new formual for calculation
					vx1[i] = vx1[i-1] * Math.cos(angleAlpha - angleBeta[i-1]) * Math.cos(angleAlpha);
					double stop  = 2 * gravity * radius * Math.sin(angleAlpha)/(vy1[i-1]*vy1[i-1]);
					System.out.println("Stopping criteria " + stop);
					
					//condition for the rock to stop moving
					if (stop > 1)
					{
						// Rock isn't coming out of water because not enough vertical speed
						System.out.println("Rock has stopped skipping");

						counter = counter;
						System.out.println("Rock has stopped moving");
						System.out.println("Number of skips: " + counter);
						System.out.println("Time that rock has been alive: " + i * dt);
						double[] xRealPosition = new double[i];
						double[] yRealPosition = new double[i];

						xRealPosition = Arrays.copyOfRange(x1, 0, i);
						yRealPosition = Arrays.copyOfRange(y1, 0, i);

						Plot2DPanel myPlot = new Plot2DPanel();
						myPlot.addLinePlot("trajectory", Color.RED, xRealPosition, yRealPosition);
						myPlot.setFixedBounds(1, 0, 150);
				        BaseLabel title = new BaseLabel("Skipping Stone trajectory", Color.BLACK, 0.5, 1.1);
      				 	title.setFont(new Font("Courier", Font.BOLD, 14));
        				myPlot.addPlotable(title);
						new FrameView(myPlot).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						while(true)
						{

						}

					}
					//update new variables of speed, etc...
					vy1[i] = Math.abs(vy1[i-1] * (Math.sqrt(1 - stop)));
					System.out.println("vy1[i-1]: " + vy1[i-1]);

					System.out.println("vy1: " + vy1[i]);

					x1[i] = x1[i-1] + vx1[i-1] * dt;
					y1[i] = y1[i-1] + vy1[i-1] * dt;

					speed[i] = Math.sqrt(vx1[i] * vx1[i] + vy1[i] * vy1[i]);
					angleBeta[i] = Math.atan(vy1[i]/vx1[i]);

					ax1[i] = accelerationx1(angleBeta[i],speed[i]);
					System.out.println("ax1: " + ax1[i]);
					ay1[i] = accelerationy1(angleBeta[i],speed[i]);
					System.out.println("ay1: " + ay1[i]);

				}

			}

		}

	}
	
	//method for the acceleration in x in the air
	public static double accelerationx1(double angle, double velocity)
	{

		double accx;
		//System.out.println("speed" + velocity);

		accx = -1 * densityAir * dragCoefficient * velocity * velocity * Math.cos(angle)/(2 * densityStone * stoneThickness);
		//System.out.println("acceleration" + accx);

		return accx;

	}
	
	//method for the acceleration in y in the air
	public static double accelerationy1(double angle, double velocity)
	{

		double accy;
		double accyFinal;

		accy = -1 * densityAir * dragCoefficient * velocity * velocity * Math.sin(angle)/(2 * densityStone * stoneThickness);
		accyFinal = -1 * gravity + accy;

		return accyFinal;

	}

}

	
    
