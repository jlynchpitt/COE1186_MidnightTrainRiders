package MTR.NorthShoreExtension.ProofOfConcept;

import MTR.NorthShoreExtension.Backend.TrainSrc.Train;

public class TrainModelTest {
	public static void main(String[] args){
		Train[] trains = new Train[5];
		
		for(int i=0;i<5;i++) {
			trains[i] = new Train("Train "+Integer.toString(i));
		}
		TrainModelUIDemo testUI = new TrainModelUIDemo();
	}
	
}
