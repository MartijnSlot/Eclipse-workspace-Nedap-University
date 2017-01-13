package week4.math;

import week4.Function;

public class Constant implements Function, Integrandable {
	
	private double constant;
	
	public Constant(double constant){
		this.constant = constant;
	}
	
	@Override
	public double apply(double arg) {
		return this.constant;
	}

	@Override
	public Function derivative() {
		return new Constant(0);
	}
	

	public String toString(){
		return "(" + String.valueOf(apply(0)) + ") ";
	}

	@Override
	public Function integrand() {
		return new LinearProduct(new Constant(constant), new Exponent(1));
	}
		

}
