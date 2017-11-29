package MTR.NorthShoreExtension.Tests;

import java.awt.EventQueue;

import MTR.NorthShoreExtension.Backend.TrainSrc.Train;

public class TrainModelTest {
	public static void main(String[] args) throws InterruptedException{
		Train[] trains = new Train[5];
		
		for(int i=0;i<5;i++) {
			trains[i] = new Train(i);
		}
		
		TrainModelTestUI frame = new TrainModelTestUI(trains);
		frame.setVisible(true);
		double temp =0;
		do {
			long millis = System.currentTimeMillis();
		    //code to run
			for(int i=0;i<5;i++) {
				trains[i].TrainModel_setPower(temp); 
			}
			frame.updateGUI();
		    Thread.sleep(1000 - millis % 1000);
		}while(true);
	}
	
}
