package edu.ycp.cs201.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Mandelbrot {
	public static final int HEIGHT = 600;

	public static final int WIDTH = 600;

	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter coordinates of region to render:");
		System.out.print("  x1: ");
		double x1 = keyboard.nextDouble();
		System.out.print("  y1: ");
		double y1 = keyboard.nextDouble();
		System.out.print("  x2: ");
		double x2 = keyboard.nextDouble();
		System.out.print("  y2: ");
		double y2 = keyboard.nextDouble();

		System.out.print("Output filename: ");
		String fileName = keyboard.next();
		int[][] iterCounts = new int[HEIGHT][WIDTH];
		
		//single thread code
		/*
		MandelbrotTask task= new MandelbrotTask(x1, y1, x2, y2, 0, WIDTH, 0, HEIGHT, iterCounts);
		task.run();
		*/
		
		
		// dual thread code
		MandelbrotTask tasks[]= new MandelbrotTask[2];
		for(int i=0; i<2; i++) {
			tasks[i] = new MandelbrotTask(x1, y1+(y2/64)*i, x2, (y2/ 64)*(i+1), 0+(WIDTH/2)*i, (WIDTH/2)*(i+1), 0, HEIGHT, iterCounts);
			tasks[i].run();
		}
		Thread[] threads = new Thread[2];
		for (int i = 0; i < 2; i++) {
		    threads[i] = new Thread(tasks[i]);
		    threads[i].start();
		}
		try {
		    for (int i = 0; i < 2; i++) {
		        threads[i].join();
		    }
		} catch (InterruptedException e) {
		    // this should not happen
		    System.err.println("A thread was interrupted");
		}
		//Drawing the image
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.getGraphics();
		int zc = 0, oc=0, tc=0, thc=0, fc=0, fic= 0, sc=0;
		
		//Color selection- =the range selection is based off of what experimentally looks the best 
		for (int i =0; i <WIDTH; i++) {
            for (int j =0; j < HEIGHT; j++) {
            	if(iterCounts[i][j]==-1) {
            		g.setColor(Color.BLACK);
            		g.fillRect(i, j, 1, 1);
            		zc++;
            	}
            	else if(iterCounts[i][j]>-1&& iterCounts[i][j]<4) {
            		g.setColor(Color.CYAN);
            		g.fillRect(i, j, 1, 1);
            		oc++;
            	}
            	else if(iterCounts[i][j]<5) {
            		g.setColor(Color.BLUE);
            		g.fillRect(i, j, 1, 1);
            		tc++;
            	}
            	else if(iterCounts[i][j]<8) {
            		g.setColor(Color.GREEN);
            		g.fillRect(i, j, 1, 1);
            		thc++;
            	}
            	else if(iterCounts[i][j]<13) {
            		g.setColor(Color.YELLOW);
            		g.fillRect(i, j, 1, 1);
            		fc++;
            	}
            	else if(iterCounts[i][j]<30) {
            		g.setColor(Color.ORANGE);
            		g.fillRect(i, j, 1, 1);
            		fic++;
            	}
            	else {
            		g.setColor(Color.RED);
            		g.fillRect(i, j, 1, 1);
            		sc++;
            	}
            }
            }
		//colored bit print out- very useful 
		System.out.println("zero="+zc+"one="+oc+"two="+tc+"three="+thc+"four="+fc+"five="+fic+"six="+sc);
		
		g.dispose();
		
		
		//making image
		OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
		try {
		    ImageIO.write(bufferedImage, "PNG", os);
		}
		finally {
		    os.close();
		}
	}
}
