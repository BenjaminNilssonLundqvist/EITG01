import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	EventList myEventList = new EventList();
    	State actState = new State(myEventList);
        myEventList.InsertEvent(ARRIVALA, 0);
        myEventList.InsertEvent(MEASURE, 500);
    	while (time < 1000){
    		actEvent = myEventList.FetchEvent();
    		time = actEvent.eventTime;
    		actState.TreatEvent(actEvent);
    	}
    	System.out.println("Mean number of customers: " + 1.0*actState.accumulated/actState.noMeasurements);
		System.out.println("Mean number of customers in B: " + actState.nbrJobB/actState.noMeasurements );
    	System.out.println("Number of measurements done: " + actState.noMeasurements);
		System.out.println("Mean number of customers in A: " + actState.accumulated/actState.noMeasurements);
	}
}
