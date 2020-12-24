import java.util.HashMap;
import java.util.Map;

public class OutputsParser {
	Map<String, String> sysValues;
	City city;
	int NUM_BUSSES;
	int NUM_RESERVE_BUSSES;
	
	public OutputsParser(City city) {
		this.sysValues = new HashMap<>();
		this.city = city;
		this.NUM_BUSSES = city.NUM_BUSSES;
		this.NUM_RESERVE_BUSSES = city.NUM_RESERVE_BUSSES;
	}

	private boolean[] parseToArrayOfBooleans(String sysVarName, int numElements) {
		boolean[] result = new boolean[numElements];
    	for(int i=0; i<numElements; i++) {
    		String name = String.format("%s[%d]", sysVarName, i);
    		result[i] = Boolean.parseBoolean(sysValues.get(name));
    	}
    	return result;	
	}
	
	private int[] parseToArrayOfInts(String sysVarName, int numElements) {
		int[] result = new int[numElements];
    	for(int i=0; i<numElements; i++) {
    		String name = String.format("%s[%d]", sysVarName, i);
    		result[i] = Integer.parseInt(sysValues.get(name));
    	}
    	return result;	
	}
	
	private void updateLineOfReserveBus() {
		int[] values = parseToArrayOfInts("lineOfReserveBus", NUM_RESERVE_BUSSES);
		for(int i=0; i<NUM_RESERVE_BUSSES; i++) {
			IBus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
			if(values[i] == 0) {
				bus.setLine(LineName.valueOf("A"));
			}
				
			else {
				bus.setLine(LineName.valueOf("B"));
			}
		}
	}
	
	private void updateShouldGoToGasStation() {
		int[] values = parseToArrayOfInts("lineOfReserveBus", NUM_RESERVE_BUSSES);
		for(int i=0; i<NUM_RESERVE_BUSSES; i++) {
			IBus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
			if(values[i] == 0) {
				bus.setLine(LineName.valueOf("A"));
			}
				
			else {
				bus.setLine(LineName.valueOf("B"));
			}
		}
	}
	
	public void updateCity(Map<String, String> sysValues) {
		this.sysValues = sysValues;
		updateLineOfReserveBus();
		updateShouldGoToGasStation();
		updateNeedExtraBusForLine();	
		
		for(IBus bus : city.getBusses().values()) {
			bus.updateNextDesitinationAndOriginStations();
			//above method should do:
			//if should go to gas station, set it as the destination. 
			//else, check if atDestinationStation, and if true, set the next destination and origin according to the current line route
			bus.updateCoordinate();
			
		}
		
		
		
		
		
		
		
	}
				lineOfReserveBus
				shouldGoToGasStation
				needExtraBusForLine[0]=false, //only need to visualize this
				extraBusSentLine[1]=true, //do nothing with this
				stopAtNextStation[0]=false, //visualize + gui should stop when arriving destinationStation
				inUse[1]=true, lineOfReserveBus[1]=1, // if not in main station, go there. otherwise do nothing
				unstoppedStationsLineA=0, // only need to visualize this
				numOfStopsPassedBus2=1, // only need to visualize this


		
	}

}
