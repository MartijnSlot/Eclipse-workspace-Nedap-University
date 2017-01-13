package week4.math;

import week4.Function;

public class Product implements Function {

	private Function arg1;
	private Function arg2;

	public Product(Function f1, Function f2) {
		this.arg1 = f1;
		this.arg2 = f2;
	}
	
	public double apply(double arg) {
		return arg1.apply(arg) * arg2.apply(arg);
	}

	public Function derivative() {
		return new Sum(arg1.derivative(), arg2.derivative());
	}
	
	public String toString(){
		return String.valueOf(apply(0));
	}

		

}
