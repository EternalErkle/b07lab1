class Polynomial {
	private double [] coefficients;

	public Polynomial(){
		coefficients =  new double[]{0};
		
	}
	public Polynomial(double [] c ){
		coefficients = c.clone();//gotta clone
	}
	
	public Polynomial add(Polynomial c){
		double [] newCoef = new double [Math.max(c.coefficients.length, coefficients.length)];
		int minLength = Math.min(c.coefficients.length, coefficients.length);
		for(int i = 0; i < minLength; i++){			
			newCoef[i] = c.coefficients[i] + coefficients[i];
		}
		if(c.coefficients.length <= coefficients.length){
			for(int i = minLength ; i < coefficients.length; i++){
				newCoef[i] = coefficients[i];
			}
		}
		else{
			for(int i = minLength; i < c.coefficients.length; i++){
				newCoef[i] = c.coefficients[i];
			}
		}
		Polynomial p = new Polynomial(newCoef);
		return p;
	}

	public double evaluate(double x){
		double count = 0;
		for(int i = 0; i < coefficients.length; i++){
			count = count + coefficients [i] * Math.pow(x, i);
		}
		return count;
	}

	public boolean hasRoot(double x){
		double count = 0;
		for(int i = 0; i < coefficients.length; i++){
			count = count + coefficients[i] * Math.pow(x, i);
		}
		if(count == 0){
			return true;
		}
		else{
			return false;
		}

	}
	
}