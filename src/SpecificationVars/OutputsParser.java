package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;
import Lines.BusMover;
import Lines.LineName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Update the state of each of the city components according to the new values of system variables created
// by the controller, that were recieved in the sysValues map. This is done by calling the parseSysValues
// method with the new map as an argument
public class OutputsParser {

	Map<String, String> sysValues;
	City city;
	int NUM_BUSSES;
	int NUM_RESERVE_BUSSES;
	int NUM_LINES;
	
	public OutputsParser(City city) {
		this.sysValues = new HashMap<>();
		this.city = city;
		this.NUM_BUSSES = city.getNumBusses();
		this.NUM_RESERVE_BUSSES = city.getNumReserveBusses();
		this.NUM_LINES = city.getNumLines();
	}

	public void parseSysValues(Map<String, String> sysValues) {
		this.sysValues = sysValues;
		updateShouldGoToGasStation();
		updateWaiting();
		updateStopAtNextStation();
		updateExtraBusSentLine();
		updateUnstoppedStationsLine();
		updateNumOfStopsPassedBus();
	}

	//Return an array of booleans of size numElements, that contains the corresponding values of the system
	// variable array named sysVarName
	private boolean[] parseToArrayOfBooleans(String sysVarName, int numElements) {
		boolean[] result = new boolean[numElements];
    	for(int i=0; i<numElements; i++) {
    		String name = String.format("%s[%d]", sysVarName, i);
    		result[i] = Boolean.parseBoolean(sysValues.get(name));
    	}
    	return result;	
	}

	//Return an array of ints of size numElements, that contains the corresponding values of the system
	// variable array named sysVarName
	private int[] parseToArrayOfInts(String sysVarName, int numElements) {
		int[] result = new int[numElements];
    	for(int i=0; i<numElements; i++) {
    		String name = String.format("%s[%d]", sysVarName, i);
    		result[i] = Integer.parseInt(sysValues.get(name));
    	}
    	return result;	
	}

	private void updateShouldGoToGasStation() {
		boolean[] values = parseToArrayOfBooleans("shouldGoToGasStation", NUM_BUSSES);
		for(int i=0; i<NUM_BUSSES; i++) {
			Bus bus = city.getBusses().get(i);
			bus.setShouldGoToGasStation(values[i]);
			if(bus.getId() == 2 || bus.getId() == 3){
				if(bus.isShouldGoToGasStation()){
				}
			}
		}
	}
	
	private void updateWaiting() {
		boolean resultA = Boolean.parseBoolean(sysValues.get("waitingA"));
		boolean resultB = Boolean.parseBoolean(sysValues.get("waitingB"));
		city.getLines().get(0).setWaiting(resultA);
		city.getLines().get(1).setWaiting(resultB);
	}

	//Once an extra bus is sent, we simultaneously update also the line of this bus to the lines the controller
	//allocated to this bus
	private void updateExtraBusSentLine() {
		boolean[] values = parseToArrayOfBooleans("extraBusSentLine", city.getNumLines());
		int[] lines = parseToArrayOfInts("lineOfReserveBus", city.getNumReserveBusses());
		boolean sendForA = values[0];
		boolean sendForB = values[1];
		int newLineFor2 = lines[0];
		int newLineFor3 = lines[1];
		HashMap<Integer, Bus> busses = city.getBusses();
		BusMover busMover = city.getBusMover();

		if(sendForA){
			if(newLineFor2==0 && busMover.isAtPrivateParking(busses.get(2))){
				busses.get(2).setLine(city.getLines().get(0));
				busses.get(2).setInUse(true);

			}
			else if(newLineFor3==0 && busMover.isAtPrivateParking(busses.get(3))){
				busses.get(3).setLine(city.getLines().get(0));
				busses.get(3).setInUse(true);
			}
		}

		if(sendForB){
			if(newLineFor2==1 && busMover.isAtPrivateParking(busses.get(2))){
				busses.get(2).setLine(city.getLines().get(1));
				busses.get(2).setInUse(true);

			}
			else if(newLineFor3==1 && busMover.isAtPrivateParking(busses.get(3))){
				busses.get(3).setLine(city.getLines().get(1));
				busses.get(3).setInUse(true);
			}
		}
	}
	
	private void updateStopAtNextStation() {
		boolean[] values = parseToArrayOfBooleans("stopAtNextStation", NUM_BUSSES);
		for(int i=0; i<NUM_BUSSES; i++) {
			Bus bus = city.getBusses().get(i);
			bus.setStopAtNextStation(values[i]);
		}
	}
	
	private void updateNumOfStopsPassedBus(){
		for(Bus bus : city.getBusses().values()){
			String name = "numOfStopsPassedBus" + bus.getId();
			int value = Integer.parseInt(sysValues.get(name));
			bus.setNumOfStopsPassed(value);
		}
	}

	private void updateUnstoppedStationsLine(){
		String nameA = "unstoppedStationsLineA";
		int valueA = Integer.parseInt(sysValues.get(nameA));

		String nameB = "unstoppedStationsLineB";
		int valueB = Integer.parseInt(sysValues.get(nameB));

		city.getLines().get(0).setNumUnstopped(valueA);
		city.getLines().get(1).setNumUnstopped(valueB);
	}
}
