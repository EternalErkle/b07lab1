import java.util.Scanner;

class Polynomial {
	private double [] coef;
	private int [] exponents;

	public Polynomial(){
		coef =  new double[]{0};
		exponents = new int[]{0};
	}
	public Polynomial(double [] c, int [] e){
		coef =  c.clone();//gotta clone
		exponents = e.clone();
	}
	
	public Polynomial(File f) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		String line = sc.nextLine();
		
		String[] nonAlpha = {"(", ")", "?", "=", "[", "]", "+", "-"};
		int tempExp = 0;
		int tempCoef = 0;

		int len = 1;//first term wouldn't have a + or -
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) == '+' || line.charAt(i) == '-') len++;
		}

		int [] newExp = new int[len];
		double [] newCoef = new double[len];

		int ind = 0;
		int cur = 0;
		for(int i = )
		



		sc.close();
	}

	// need to fix this function.
	public Polynomial add(Polynomial c, Polynomial e){

		//You'll need to probably do this:
			//for each exp in A, check if its in B. If they are add their coeff into the newCoef and the exp into newExp at the
			//earliest available spot.
			//If they arent just add one.
			//Then after that for B, if any exp are not in newExp, you add them along with the coefficient. There.
		double [] newcoef = new double [Math.max(c.exponents.length, this.exponents.length)];
		int [] newExponents = new int [Math.max(e.exponents.length, this.exponents.length)];
		
		int minLength = Math.min(c.exponents.length, this.exponents.length);
		for(int i = 0; i < minLength; i++){			
			newcoef[i] = c.coef[i] + this.coef[i];
			newExponents[i] = c.exponents[i] + this.exponents[i];
		}
		if(c.coef.length <= this.coef.length){
			for(int i = minLength ; i < this.coef.length; i++){
				newcoef[i] =  this.coef[i];
				newExponents[i] =  this.exponents[i];
			}
		}
		else{
			for(int i = minLength; i < c.coef.length; i++){
				newcoef[i] = c.coef[i];
				newExponents[i] = c.exponents[i];
			}
		}
		Polynomial p = new Polynomial(newCoef, newExponents);
		return p;
	}

	public double evaluate(double x){
		double count = 0;
		for(int i = 0; i < coef.length; i++){
			count = count + this.coef[i] * Math.pow(x, this.exponents[i]);
			
		}
		return count;
	}

	public boolean hasRoot(double x){
		double count = 0;
		for(int i = 0; i < coef.length; i++){
			count = count + this.coef[i] * Math.pow(x, this.exponents[i]);
		}
		if(count == 0){
			return true;
		}
		else{
			return false;
		}

	}
//	____________
	public Polynomial multiple(Polynomial p){
		int newLength = this.exponents.length * p.exponents.length ;
		int [] newExp = new int[newLength];
		double [] newCoef = new double[newLength];
		for(int i = 0; i < newLength; i++){
			newExp[i] = -1;
			newCoef[i] = 0;
		}

		int expTrack = 0;
		double coefTrack = 0;
		for(int i = 0; i < this.exponents.length; i++){
			for(int j = 0; j < p.exponents.length; j++){
				expTrack = this.exponents[i] + p.exponents[j];
				coefTrack = this.coef[i] * p.coef[j];
				//Loop to see if expTrack in newExp
				boolean T = false;
				for(int k = 0; k < newLength; k++){
					if(expTrack == newExp[k]){
						newCoef[k] = newCoef[k] + coefTrack;
						T = true;
							break;
					}
				
				}
				if(T == false){
					for(int k = 0; k < newLength; k++){
						if(newExp[k] == -1){
							newExp[k] = expTrack;
							newCoef[k] = coefTrack;
							break;
						}

					}
				}
			}
		}
		//Now the newExp and newCoef have the right values in them, or they should.
		//We update newExp and newCoef to only have non- -1 entries.
			//count the amount of -1's in newExp
		int negativeCounter =0;
		for(int i = 0; i < newLength; i++){
			if (newExp[i] == -1) negativeCounter++;
		}
		int [] newExpF = new int[newLength - negativeCounter];
		double [] newCoefF = new double[newLength - negativeCounter];

		int entryCounter = 0;
		for(int i = 0; i < newLength; i++){
			if (newExp[i] != -1){
				newExpF[entryCounter] = newExp[i];
				newCoefF[entryCounter] = newCoef[i];
				entryCounter++;
			}
		}
		Polynomial q = new Polynomial(newCoefF, newExpF);
		return q;
	}


	
}


// class Polynomial {
// 	private double [] coefficients;

// 	public Polynomial(){
// 		coefficients =  new double[]{0};
		
// 	}
// 	public Polynomial(double [] c ){
// 		coefficients = c.clone();//gotta clone
// 	}
	
// 	public Polynomial add(Polynomial c){
// 		double [] newCoef = new double [Math.max(c.coefficients.length, coefficients.length)];
// 		int minLength = Math.min(c.coefficients.length, coefficients.length);
// 		for(int i = 0; i < minLength; i++){			
// 			newCoef[i] = c.coefficients[i] + coefficients[i];
// 		}
// 		if(c.coefficients.length <= coefficients.length){
// 			for(int i = minLength ; i < coefficients.length; i++){
// 				newCoef[i] = coefficients[i];
// 			}
// 		}
// 		else{
// 			for(int i = minLength; i < c.coefficients.length; i++){
// 				newCoef[i] = c.coefficients[i];
// 			}
// 		}
// 		Polynomial p = new Polynomial(newCoef);
// 		return p;
// 	}

// 	public double evaluate(double x){
// 		double count = 0;
// 		for(int i = 0; i < coefficients.length; i++){
// 			count = count + coefficients [i] * Math.pow(x, i);
// 		}
// 		return count;
// 	}

// 	public boolean hasRoot(double x){
// 		double count = 0;
// 		for(int i = 0; i < coefficients.length; i++){
// 			count = count + coefficients[i] * Math.pow(x, i);
// 		}
// 		if(count == 0){
// 			return true;
// 		}
// 		else{
// 			return false;
// 		}

// 	}
	
// }