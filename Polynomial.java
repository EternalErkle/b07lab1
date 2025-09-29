import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.File;

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
		int tempExp = 0;
		int tempCoef = 0;

		int len = 1;//exclude the first term in case its a -, as thatd be part of the number.
		for(int i = 1; i < line.length(); i++){
			if(line.charAt(i) == '+' || line.charAt(i) == '-') len++;
		}
		double [] newCoef = new double [len];
		int [] newExponents = new int [len];

		//split by +, -. Tracker will keep track of what we got so far.
		String tracker = "";
		String[] powers = new String[len];
		int powersCur = 0;
		//This will split it into each component.
		for(int i = 0; i < line.length(); i++){
			if(i == 0 || line.charAt(i) == '-'){
				tracker = tracker + line.charAt(i);
				
			}

			while(i < line.length() && line.charAt(i) != '+' && line.charAt(i) != '-'){
				tracker = tracker + line.charAt(i);
				i++;
			}
			powers[powersCur] = tracker;
			tracker = "";
			powersCur++;
		}
		int iterator = 0;
		//Now we iterate through every element in powers.
		String[] coefExp;
		for(String power : powers){
			if(power.charAt(0) == 'x' || power.charAt(0) == '-' && power.charAt(1) == 'x'){
				power = "1" + power;
			}


			//0 means false, 1 means just x, 2 means xY;
			int hasX = 0;
			for(int i = 0; i < power.length(); i++){
				if(power.charAt(i) == 'x'){
					if(i !=  power.length() - 1){
						hasX = 2;
						//using the split function we assign before AND after x to coef and exponent respectively.
						coefExp = power.split("x");
						newCoef[iterator] = Double.parseDouble(coefExp[0]);
					newExponents[iterator] = Integer.parseInt(coefExp[1]);
						iterator++;
					}
					else {
						hasX = 1;
						power = power + "1";
						coefExp = power.split("x");
						newCoef[iterator] = Double.parseDouble(coefExp[0]);
					newExponents[iterator] = Integer.parseInt(coefExp[1]);
						iterator++;
					}
					break;
				}
			}
			if(hasX == 0){
				power = power + "x0";
				coefExp = power.split("x");
				newCoef[iterator] = Double.parseDouble(coefExp[0]);
				newExponents[iterator] = Integer.parseInt(coefExp[1]);
				iterator++;
			}
		}
		sc.close();
		this.coef = newCoef;
		this.exponents = newExponents;
	}

	// need to fix this function.
	public Polynomial add(Polynomial e){

		//You'll need to probably do this:
			//for each exp in A, check if its in B. If they are add their coeff into the newCoef and the exp into newExp at the
			//earliest available spot.
			//If they arent just add one.
			//Then after that for B, if any exp are not in newExp, you add them along with the coefficient. There.
		int usedSlot = 0;
		double [] newCoef = new double [e.exponents.length + this.exponents.length];
		int [] newExponents = new int [e.exponents.length + this.exponents.length];
		//fill newCoef with and newExponents with 
		for(int i = 0; i < newCoef.length; i++){
			newExponents[i] = -1;
			newCoef[i] = 0;
		}

		// A is the this. . B is the c., e. .
		for(int i = 0; i < this.exponents.length; i++){
			boolean inIt = false;
			for(int j = 0; j < e.exponents.length; j++){
				if(this.exponents[i] == e.exponents[j]){
					//adds the thing to newCoef and newExponents if it is in both.
					newExponents[usedSlot] = this.exponents[i];
					newCoef[usedSlot] = this.coef[i] + e.coef[j];
					usedSlot++;	
					inIt = true;
				}
			}
			if(inIt == false){
				newExponents[usedSlot] = this.exponents[i];
				newCoef[usedSlot] = this.coef[i];
				usedSlot++;
			}
		}
		//now we add all the ones in B not in the newCoef already.
		for(int i = 0; i< e.exponents.length; i++){
			boolean inIt = false;
			for(int j = 0; j < newExponents.length; j++){
				if(newExponents[j] == e.exponents[i]){
					inIt = true;
				}
			}
			if(inIt == false){
				newExponents[usedSlot] = e.exponents[i];
				newCoef[usedSlot] = e.coef[i];
				usedSlot++;
			}
		}

		int negativeCounter =0;
		for(int i = 0; i < newCoef.length; i++){
			if (newExponents[i] == -1) negativeCounter++;
		}
		int [] newExpF = new int[newCoef.length - negativeCounter];
		double [] newCoefF = new double[newCoef.length - negativeCounter];

		int entryCounter = 0;
		for(int i = 0; i < newCoef.length; i++){
			if (newExponents[i] != -1){
				newExpF[entryCounter] = newExponents[i];
				newCoefF[entryCounter] = newCoef[i];
				entryCounter++;
			}
		}
		Polynomial p = new Polynomial(newCoefF, newExpF);
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
	public Polynomial multiply(Polynomial p){
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

	public void saveToFile(String fileName){
		try{
			PrintStream output = new PrintStream(fileName); // creates file 
			if(this.coef.length == 0) return;
			//Now we format the polynomial into the text format from earlier
			String line = "";
			line = line + Double.toString(coef[0]) + "x" + Integer.toString(exponents[0]);
			for(int i = 0; i < this.coef.length; i++){
				//we bundle; if the coef is not negative, add a plus. if it is, just add it without the plus.
				if(coef[i] < 0){
					if( coef[i] == 0){
						continue;
					}
					//if exp = 0, just write coef
					else if(exponents[i] == 0){
						line = line + Double.toString(coef[i]);
					}
					
					else if (exponents[i] == 1){ // exponent is not zero;
						line = line + Double.toString(coef[i]) + "x";
					}
					else{// Exponent is not zero or one;
						line = line + Double.toString(coef[i]) + "x" + Integer.toString(exponents[i]);
					}
					
				}
				else{
					if( coef[i] == 0){
						continue;
					}
					//if exp = 0, just write coef
					else if(exponents[i] == 0){
						line = line + "+" + Double.toString(coef[i]);
					}
					else if (exponents[i] == 1){ // exponent is not zero;
						line = line + "+" + Double.toString(coef[i]) + "x";
					}
					else{// Exponent is not zero or one;
						line = line + "+" + Double.toString(coef[i]) + "x" + Integer.toString(exponents[i]);
					}
				}
			}
			output.println(line);
			output.close(); 
		} catch( FileNotFoundException e){
			System.out.println("Error while trying to write:" + e.getMessage());
		}
	}
	
}
