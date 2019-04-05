package edu.ycp.cs201.mandelbrot;

public class Complex {

	private double real;
	private double imaginary;
	
	public Complex(double real, double imaginary) {
		this.real= real;
		this.imaginary=imaginary;
		
	}
	// math is from wikipedia- I added getters 
	public Complex add(Complex o) {
		return new Complex(o.getReal()+real, o.getImagine()+imaginary);
	}
	public Complex multiply(Complex o) {
		return new Complex((o.getReal()*real)-(o.getImagine()*imaginary),(real*o.getImagine())+(imaginary*o.getReal()));
	}
	public double getMagnitude() {
		return Math.sqrt((real*real)+(imaginary*imaginary));
	}
	public double getReal() {
		return real;
	}
	public double getImagine() {
		return imaginary;
	}
}
