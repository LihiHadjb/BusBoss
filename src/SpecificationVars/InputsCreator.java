package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;
import Lines.BusMover;

import java.util.HashMap;
import java.util.Map;

public abstract  class InputsCreator {
    Map<String, String> inputs;
    City city;
    int NUM_BUSSES;
    Map<String, Boolean> envVarToInitValue;

    public InputsCreator(Map<String, String> inputs, City city){
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

    }

    public abstract void putArePassengersWaitingInNextStation(boolean isInit);
    public abstract void putIsStopPressed(boolean isInit);
    public abstract void putIsBusFull(boolean isInit);





    public void createEnvVars(boolean isInit) {
        putIsBusFull(isInit);
        putIsStopPressed(isInit);
        putArePassengersWaitingInNextStation(isInit);
        putAtDestinationStationForEachBus(isInit);
        putAtGasStationForEachBus(isInit);
        putAtMainStationForEachBus(isInit);
        putIsRaining();
        putIsParking();
    }


    public void putIsRaining() {
        inputs.put("isRaining", Boolean.toString(city.isRaining()));
    }


    public void putAtDestinationStationForEachBus(boolean isInit) {
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


    public void putAtGasStationForEachBus(boolean isInit) {
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



    public void putIsParking() {
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

    public void putAtMainStationForEachBus(boolean isInit) {
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





}
