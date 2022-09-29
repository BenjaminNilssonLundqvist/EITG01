import java.util.*;

class State extends GlobalSimulation{
	
	public int nbrQA = 0, nbrQB = 0, accumulated = 0, noMeasurements = 0, nbrJobA = 0, nbrJobB = 0;
	
	private EventList myEventList;

	Random slump = new Random();
	
	State(EventList x){
		myEventList = x;
	}
	
	private void InsertEvent(int event, double timeOfEvent){
		myEventList.InsertEvent(event, timeOfEvent);
	}
	
	
	public void TreatEvent(Event x){
		switch (x.eventType){
			case ARRIVALA:
				arrivalA();
				break;
			case ARRIVALB:
				arrivalB();
				
				break;
			case READYA:
				readyA();
				break;
			case READYB:
				readyB();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private double generateMean(double mean){
		return (2*mean*slump.nextDouble());
	}
	
	private void arrivalA(){ 
		if (nbrQA + nbrQB == 0){
			InsertEvent(READYA, time + 0.002 ); // READY är betjäningsstate, för A: alltid 2ms
		}
			nbrQA++;
		InsertEvent(ARRIVALA, time + generateMean(1.0/150)); // ARRIVAL är ankomststate, Lambda är 1/150
	}

	private void arrivalB(){ 
		if (nbrQA + nbrQB == 0){
			InsertEvent(READYB, time + 0.004 ); // för B: Alltid 4 ms
		}
			nbrQB++;
	}
	
	private void readyA(){
		nbrQA--;
		nbrJobA++;
		if (nbrQB > 0)
			InsertEvent(READYB, time + 0.004);
		else if(nbrQA > 0 ){
			InsertEvent(READYA, time + 0.002);
		}

		InsertEvent(ARRIVALB, time + 1/*generateMean(2)*/);
	}

	private void readyB(){
		nbrQB--;
		nbrJobB++;
		if (nbrQB > 0)
			InsertEvent(READYB, time + 0.004);
		else if(nbrQA > 0 ){
			InsertEvent(READYA, time + 0.002);
		}
	}
	
	private void measure(){
		if (noMeasurements < 1000){
			accumulated = accumulated + nbrQA + nbrQB;
			noMeasurements++;
			
		}
		InsertEvent(MEASURE, time + 0.1);
		//konstant 0.1s mellan mätningarna
	}
}