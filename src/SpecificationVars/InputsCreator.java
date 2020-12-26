package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;
import Lines.BusMover;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import GUI.IBus;

public class InputsCreator {
	private Map<String, String> inputs;
	private City city;
	private int NUM_BUSSES;
	private Map<String, Boolean> envVarToInitValue;
	
	
	public InputsCreator(Map<String, String> inputs, City city) {
		this.inputs = inputs;
		this.city = city;
		this.NUM_BUSSES = city.getNumBusses();
		
		this.envVarToInitValue = new HashMap<>();
		envVarToInitValue.put("isBusFull", false);
		envVarToInitValue.put("isStopPressed", false);
		envVarToInitValue.put("arePassengersWaitingInStation", false);
		envVarToInitValue.put("isBusFull", false);
		envVarToInitValue.put("atDestinationStation", false);
		envVarToInitValue.put("atGasStation", false);
		envVarToInitValue.put("atMainStation", false);
		
	}



	//TODO: maybe these should be with probability
	private void randomizeBooleanForEachBus(String envVarName, boolean isInit) {
		Random rand = new Random();
		boolean result;
		for(int i=0; i<NUM_BUSSES; i++) {
			String name = String.format("%s[%d]", envVarName, i);
			if(isInit) {
				result = envVarToInitValue.get(envVarName);
			}
			else {
				result = rand.nextBoolean();
			}

			inputs.put(name, Boolean.toString(result));
		}
	}

	private boolean existsBusThatStopsAtStation(Station station){
		BusMover busMover = city.getBusMover();
		for(Bus bus : city.getBusses().values()){
			if(bus.getId() == 0 || bus.getId() == 1 || bus.isInUse()){
				if(busMover.isAtDestinationStation(bus) && bus.getDestination()==station && bus.isStopAtNextStation()){
					return true;
				}
			}

		}
		return false;
	}


	private boolean[] randomizeBooleanForEachStation(boolean isInit){
		Random rand = new Random();
		boolean[] result = new boolean[city.getBusStations().size()+1];
		for(int i=0; i<city.getBusStations().size()+1; i++) {
			result[i] = rand.nextBoolean();
			Station station = city.getIndex2station().get(i);
			if(!existsBusThatStopsAtStation(station)){
				if(!station.isArePassengersWaiting()){
					station.setArePassengersWaiting(result[i]);
				}

			}
			else{
				result[i] = station.isArePassengersWaiting();
			}
		}
		return result;
	}
	
    //TODO: maybe these should be with probability
    private void arePassengersWaitingInNextStationForEachBus(String envVarName, boolean isInit) {
		boolean[] valuesForStations = randomizeBooleanForEachStation(isInit);
    	for(int i=0; i<NUM_BUSSES; i++) {
			String name = String.format("%s[%d]", envVarName, i);
			if(isInit) {
				inputs.put(name, Boolean.toString(envVarToInitValue.get("arePassengersWaitingInStation")));
			}
			else{
//				BusMover busMover = city.getBusMover();
				Bus bus = city.getBusses().get(i);
				Station nextStation = bus.getDestination();
				int stationId = nextStation.getId();
				if(stationId != 5){
					inputs.put(name, Boolean.toString(valuesForStations[stationId]));
				}
//				if(!busMover.isAtDestinationStation(bus)){
//					inputs.put(name, Boolean.toString(nextStation.isArePassengersWaiting()));
//				}
//				else{
//					int stationId = nextStation.getId();
//					inputs.put(name, Boolean.toString(valuesForStations[stationId]));
//				}



			}
    	}
    }
    
    private void putAtDestinationStationForEachBus(boolean isInit) {
    	String envVarName = "atDestinationStation";
    	HashMap<Integer, Bus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		Bus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
    			BusMover busMover = city.getBusMover();
    			result = busMover.isAtDestinationStation(bus);

    		}
    		inputs.put(name, Boolean.toString(result));

    	}	
    	
    }
    
    private void putAtGasStationForEachBus(boolean isInit) {
    	String envVarName = "atGasStation";
    	HashMap<Integer, Bus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		Bus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
				BusMover busMover = city.getBusMover();
				result = busMover.isAtGasStation(bus);
    		}
    		inputs.put(name, Boolean.toString(result));
    	}	
    	
    }
    
    private void putAtMainStationForEachBus(boolean isInit) {
    	String envVarName = "atMainStation";
    	HashMap<Integer, Bus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		Bus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
				BusMover busMover = city.getBusMover();
				result = busMover.isAtMainStationEntrance(bus);
    		
    		}
    		inputs.put(name, Boolean.toString(result));
    	}	
    	
    }
    
    private void putIsRaining() {
    	//TODO: this should come from GUI or something
    	inputs.put("isRaining", Boolean.toString(false));
    }
    
    public void createEnvVars(boolean isInit) {
    	
    	randomizeBooleanForEachBus("isBusFull", isInit);
    	randomizeBooleanForEachBus("isStopPressed", isInit);
    	//randomizeBooleanForEachBus("arePassengersWaitingInNextStation", isInit);
		arePassengersWaitingInNextStationForEachBus("arePassengersWaitingInNextStation", isInit);
    	
    	putAtDestinationStationForEachBus(isInit);
    	putAtGasStationForEachBus(isInit);
    	putAtMainStationForEachBus(isInit);
    	putIsRaining();   	
    }

}
