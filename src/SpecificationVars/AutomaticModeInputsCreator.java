package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;
import Lines.BusMover;

import java.util.Map;

//Create the map of value passed to the controller as the new environment variables, using random values, which are
//drawn with a predefined probability according to the mode (Rush-Hour or regular mode)
public class AutomaticModeInputsCreator extends InputsCreator{

	double isBusFullProb;
	double arePassengersWaitingProb;
	double isStopPressedProb;

	public AutomaticModeInputsCreator(Map<String, String> inputs, City city) {
		super(inputs, city);
		initProbabilities();
	}

	public void initProbabilities(){
		this.isBusFullProb = 0.005;
		this.isStopPressedProb = 0.015;
		this.arePassengersWaitingProb = 0.02;
	}

	public void updateProbabilities(){
		if (city.isRushHour()){
			this.isBusFullProb = 0.7;
			this.isStopPressedProb = 0.015;
			this.arePassengersWaitingProb = 0.8;
		}
		else{
			this.isBusFullProb = 0.005;
			this.isStopPressedProb = 0.015;
			this.arePassengersWaitingProb = 0.02;
		}
	}

	//If the value for a certain bus is already true, should remain true until reaching the station
	private void randomizeBooleanForEachBus(String envVarName, boolean isInit, double prob) {
		boolean result;
		for(int i=0; i<NUM_BUSSES; i++) {
			String name = String.format("%s[%d]", envVarName, i);
			if(isInit) {
				result = this.envVarToInitValue.get(envVarName);
			}
			else {
				result = Math.random() < prob;
			}

			//update the bus object so that control panel can be updated correctly, and dont put the random value if its already
			//true and the bus did not arrive to the station yet
			Bus bus = city.getBusses().get(i);
			BusMover busMover = city.getBusMover();
			switch (envVarName){
				case "isBusFull":
					if(bus.isFull() && !busMover.isAtDestinationStation(bus) && !isInit){
						result = bus.isFull();
					}
					bus.setFull(result);
				case "isStopPressed":
					if(bus.isStopPressed() && !busMover.isAtDestinationStation(bus) && !isInit){
						result = bus.isStopPressed();
					}
					bus.setStopPressed(result);
			}
			inputs.put(name, Boolean.toString(result));
		}
	}


	private boolean[] randomizeBooleanForEachStation(boolean isInit, double prob){
		boolean[] result = new boolean[city.getBusStations().size()+1];

		for(int i=0; i<city.getBusStations().size()+1; i++) {
			result[i] = Math.random() < prob;
			Station station = city.getIndex2station().get(i);
			if(!city.existsBusThatStopsAtStation(station)){
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

	//If the value for a certain station is already true, should remain true until some bus stops at this station
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
					}
					else {
						inputs.put(name, Boolean.toString(nextStation.isArePassengersWaiting()));
					}
				}
			}
			else{
				inputs.put(name, Boolean.toString(false));
			}
		}

    }

	@Override
	public void putArePassengersWaitingInNextStation(boolean isInit) {
		arePassengersWaitingInNextStationForEachBus("arePassengersWaitingInNextStation", isInit, arePassengersWaitingProb);
	}

	@Override
	public void putIsStopPressed(boolean isInit) {
		randomizeBooleanForEachBus("isStopPressed", isInit, isStopPressedProb);

	}

	@Override
	public void putIsBusFull(boolean isInit) {
		randomizeBooleanForEachBus("isBusFull", isInit, isBusFullProb);

	}

	@Override
	public void createEnvVars(boolean isInit) {
		updateProbabilities();
		super.createEnvVars(isInit);
	}

}
