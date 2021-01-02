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
	double isBusFullProb;
	double arePassengersWaitingProb;
	double isStopPressedProb;

	public InputsCreator(Map<String, String> inputs, City city) {
		this.inputs = inputs;
		this.city = city;
		this.NUM_BUSSES = city.getNumBusses();
		
		this.envVarToInitValue = new HashMap<>();
		envVarToInitValue.put("isBusFull", false);
		envVarToInitValue.put("isStopPressed", false);
		envVarToInitValue.put("arePassengersWaitingInStation", false);
		envVarToInitValue.put("atDestinationStation", false);
		envVarToInitValue.put("atGasStation", false);
		envVarToInitValue.put("atMainStation", false);

		initProbabilities();
	}

	public void initProbabilities(){
		this.isBusFullProb = 0.005;
		this.isStopPressedProb = 0.015;
		this.arePassengersWaitingProb = 0.02;
	}

	public void updateProbabilities(){
		// Rush hour scenario
		if (city.isRushHour()){
			this.isBusFullProb = 0.15;
			this.arePassengersWaitingProb = 0.15;
			this.isStopPressedProb = 0.15;
		}
		else{
			this.isBusFullProb = 0.005;
			this.isStopPressedProb = 0.015;
			this.arePassengersWaitingProb = 0.02;
		}
	}

	//TODO: maybe these should be with probability
	private void randomizeBooleanForEachBus(String envVarName, boolean isInit, double prob) {
		boolean result;
		for(int i=0; i<NUM_BUSSES; i++) {
			String name = String.format("%s[%d]", envVarName, i);
			if(isInit) {
				result = envVarToInitValue.get(envVarName);
			}
			else {
				result = Math.random() < prob;
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


	private boolean[] randomizeBooleanForEachStation(boolean isInit, double prob){
		boolean[] result = new boolean[city.getBusStations().size()+1];

		for(int i=0; i<city.getBusStations().size()+1; i++) {
			result[i] = Math.random() < prob;
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
    private void arePassengersWaitingInNextStationForEachBus(String envVarName, boolean isInit, double prob) {
		boolean[] valuesForStations = randomizeBooleanForEachStation(isInit, prob);
		Bus bus;
		Station nextStation;
		int stationId;
		BusMover busMover = city.getBusMover();
    	for(int i=0; i<NUM_BUSSES; i++) {
			bus = city.getBusses().get(i);
			String name = String.format("%s[%d]", envVarName, i);
			if (isInit) {
				inputs.put(name, Boolean.toString(envVarToInitValue.get("arePassengersWaitingInStation")));
			}

			else if (bus.isInUse() || bus.getId()==0 || bus.getId()==1){
				nextStation = bus.getDestination();
				stationId = nextStation.getId();
				if (stationId != 5) {//not gas station
					if (!busMover.isAtDestinationStation(bus) & !nextStation.isArePassengersWaiting()) {
						inputs.put(name, Boolean.toString(valuesForStations[stationId]));
						nextStation.setArePassengersWaiting(valuesForStations[stationId]);
					} else {
						inputs.put(name, Boolean.toString(nextStation.isArePassengersWaiting()));
					}
				}
			}
			else{
				inputs.put(name, Boolean.toString(false));
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

	private void putIsParking() {
		String envVarName = "isParking";
		HashMap<Integer, Bus> busses = city.getBusses();
		for(int i=0; i<city.getNumReserveBusses(); i++) {
			Bus bus = busses.get(i + city.getNumReserveBusses());
			String name = String.format("%s[%d]", envVarName, i);
			boolean result;
			BusMover busMover = city.getBusMover();
			result = busMover.isAtPrivateParking(bus);
			inputs.put(name, Boolean.toString(result));
		}

	}





    private void putIsRaining() {
    	//TODO: this should come from GUI or something
    	inputs.put("isRaining", Boolean.toString(false));
    }
    
    public void createEnvVars(boolean isInit) {
		updateProbabilities();
		randomizeBooleanForEachBus("isBusFull", isInit, isBusFullProb);
		randomizeBooleanForEachBus("isStopPressed", isInit, isStopPressedProb);
		//randomizeBooleanForEachBus("arePassengersWaitingInNextStation", isInit);
		arePassengersWaitingInNextStationForEachBus("arePassengersWaitingInNextStation", isInit, arePassengersWaitingProb);
    	
    	putAtDestinationStationForEachBus(isInit);
    	putAtGasStationForEachBus(isInit);
    	putAtMainStationForEachBus(isInit);
    	putIsRaining();
    	putIsParking();
    }

}
