package week4.math;

import week4.Function;

public class LinearProduct extends Product implements Function, Integrandable {

	private Function arg1;
	private Function arg2;
	
	public LinearProduct(Function f1, Function f2) {
		super(f1, f2);
		this.arg1 = f1;
		this.arg2 = f2;
	}

	public double apply(double arg) {
		return arg1.apply(arg) * arg2.apply(arg);
	}

	@Override
	public Function derivative() {
		if (arg1 != null && arg2 != null && arg1 instanceof Constant) {
			return new LinearProduct((Constant) arg1, arg2.derivative());		
		}
		return null;
	}

	@Override
	public Function integrand() {
		if (arg1 != null && arg2 != null && arg2 instanceof Integrandable && arg1 instanceof Constant) {
			return new LinearProduct((Constant) arg1, ((Integrandable) arg2).integrand());
		}
		return null;
	}
	
    public String toString() {
        return arg1.toString() + "*" + arg2.toString();
}

}
