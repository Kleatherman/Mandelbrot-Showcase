package edu.ycp.cs201.mandelbrot;

public class MandelbrotTask implements Runnable {
    private double x1, y1, x2, y2;
    private int startCol, endCol, startRow, endRow;
    private int[][] iterCounts;
    private int ix1, iy1, ix2, iy2;

    public MandelbrotTask(double x1, double y1, double x2, double y2,
                          int startCol, int endCol, int startRow, int endRow,
                          int[][] iterCounts) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.startCol = startCol;
        this.endCol = endCol;
        this.startRow = startRow;
        this.endRow = endRow;
        this.iterCounts = iterCounts;
        
    }

    public void run() {
    for (int i = startRow; i < endRow; i++) {
        for (int j = startCol; j < endCol; j++) {
                Complex c = getComplex(i, j);
                int iterCount = computeIterCount(c);
                iterCounts[i][j] = iterCount;
            }
        }
       
    }

    //i is subtracted by 300 to center the image- the other math is the iteration by 1/number of pixels through the users range
    public Complex getComplex(int i,int j) {
    	return new Complex((i-300)*(x2-x1)/(endRow-startRow), (j-300)*(y2-y1)/(endCol-startCol));
    	
    }
    //honestly nothing special here
    public int computeIterCount(Complex c) {
    	int count=0;
    	Complex z = new Complex(0,0);
    	while(z.getMagnitude()<=2&& count<=600) {
    		z = z.multiply(z).add(c);
    		count ++;
    		
    	}
    	if(count>=600) {
    		return -1;
    	}
    	else {
    		return count;
    	}
    	
    	
    }
    
    
}

