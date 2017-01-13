package week4.math;

import week4.Function;

public class Polynomial implements Function, Integrandable {
	
	LinearProduct[] parts;
	
	public Polynomial(LinearProduct[] parts){
		this.parts = parts;
	}

	@Override
	public double apply(double arg) {
		double sum = 0;
		for (int i = 0; i < parts.length; i++) {
			sum = parts[i].apply(arg);
		}
		return sum;
	}

	@Override
	public Function derivative() {
		LinearProduct[] derivative = new LinearProduct[parts.length];
		for (int i = 0; i < parts.length; i++) {
			derivative[i] = (LinearProduct) parts[i].derivative();
		}
		return new Polynomial(derivative);
	}

	@Override
	public Function integrand() {
		LinearProduct[] integrand = new LinearProduct[parts.length];
		for (int i = 0; i < parts.length; i++) {
			integrand[i] = (LinearProduct) parts[i].integrand();
		}
		return new Polynomial(integrand);
	}
	
    public String toString() {
        String result = "";
        for (int i = 0; i < parts.length; i++) {
            result += parts[i].toString() + " + ";
            }
        return result.substring(0, result.length()-1);
        }

}
