package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OutputsParser {
	//TODO: fields should not be public! its just for the test!!!

	public Map<String, String> sysValues;
	public City city;
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
			Bus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
			if(values[i] == 0) {
				bus.setLine(city.getLines().get(0));
			}
				
			else {
				bus.setLine(city.getLines().get(1));
			}
		}
	}
	
	private void updateShouldGoToGasStation() {
		boolean[] values = parseToArrayOfBooleans("shouldGoToGasStation", NUM_BUSSES);
		for(int i=0; i<NUM_BUSSES; i++) {
			Bus bus = city.getBusses().get(i);
			bus.setShouldGoToGasStation(values[i]);
		}
	}
	
	private void updateNeedExtraBusForLine() {
		boolean[] values = parseToArrayOfBooleans("needExtraBusForLine", NUM_LINES);
		city.getLines().get(0).setNeedExtraBusForLine(values[0]);
		city.getLines().get(1).setNeedExtraBusForLine(values[1]);
	}
	
	
	
//	private void updateExtraBusSentLine() {
//		boolean[] values = parseToArrayOfBooleans("extraBusSentLine");
//		city.getLines.get(0).setExtraBusSentLine(values[0]);
//		city.getLines.get(1).setExtraBusSentLine(values[1]);
//	}
	
	public void updateStopAtNextStation() {
		boolean[] values = parseToArrayOfBooleans("stopAtNextStation", NUM_BUSSES);
		for(int i=0; i<NUM_BUSSES; i++) {
			Bus bus = city.getBusses().get(i);
			bus.setStopAtNextStation(values[i]);
		}
	}
	
	private void updateInUse() {
		boolean[] values = parseToArrayOfBooleans("inUse", NUM_RESERVE_BUSSES);
		for(int i=0; i<NUM_RESERVE_BUSSES; i++) {
			Bus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
			bus.setInUse(values[i]);
			if(values[i]){
				System.out.println("bus "+ bus.getId() + "in use!");
			}

		}
	}
	
	public void parseSysValues(Map<String, String> sysValues) {
		this.sysValues = sysValues;
		updateLineOfReserveBus();
		updateShouldGoToGasStation();
		updateNeedExtraBusForLine();	
		updateStopAtNextStation();
		updateInUse();
		//updateExtraBusSentLine();
		//updateUnstoppedStationsLine();
		//updateNumOfStopsPassedBus();
	}
		
	

//				lineOfReserveBus
//				shouldGoToGasStation
//				needExtraBusForLine[0]=false, //only need to visualize this
//				extraBusSentLine[1]=true, //do nothing with this
//				stopAtNextStation[0]=false, //visualize + gui should stop when arriving destinationStation
//				inUse[1]=true, lineOfReserveBus[1]=1, // if not in main station, go there. otherwise do nothing
//				unstoppedStationsLineA=0, // only need to visualize this
//				numOfStopsPassedBus2=1, // only need to visualize this


		
	
}