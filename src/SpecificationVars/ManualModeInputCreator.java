package SpecificationVars;

import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;
import Lines.BusMover;
import Panels.ControlPanel.BussesTableCreator;
import Panels.ManualMode.StationsCheckBoxesPanel;

import javax.swing.*;
import java.util.Map;

public class ManualModeInputCreator extends InputsCreator{
    JTable manualModeTable;
    BussesTableCreator bussesTableCreator;
    StationsCheckBoxesPanel stationsCheckBoxesPanel;

    public ManualModeInputCreator(Map<String, String> inputs, City city, JTable manualModeTable, StationsCheckBoxesPanel stationsCheckBoxesPanel){
        super(inputs, city);
        this.manualModeTable = manualModeTable;
        this.bussesTableCreator = new BussesTableCreator();
        this.stationsCheckBoxesPanel = stationsCheckBoxesPanel;

    }


    public void updatePassengersInStations() {
        for (Station station : city.getBusStations().values()) {
            station.setArePassengersWaiting(stationsCheckBoxesPanel.isChecked(station.getName()));
        }
        city.getMainStation().setArePassengersWaiting(stationsCheckBoxesPanel.isChecked(city.getMainStation().getName()));
    }

    @Override
    public void putArePassengersWaitingInNextStation(boolean isInit) {
        updatePassengersInStations();
        Bus bus;
        Station nextStation;
        int stationId;
        BusMover busMover = city.getBusMover();
        Boolean stationValue;
        for (int i = 0; i < NUM_BUSSES; i++) {
            bus = city.getBusses().get(i);
            String name = String.format("%s[%d]", "arePassengersWaitingInNextStation", i);

            if (bus.isInUse() || bus.getId() == 0 || bus.getId() == 1) {
                nextStation = bus.getDestination();
                stationId = nextStation.getId();
                stationValue = nextStation.isArePassengersWaiting();

                if (stationId != 5) {//not gas station
                    inputs.put(name, Boolean.toString(stationValue));
                }
                else{
                    inputs.put(name, Boolean.toString(false));
                }

            }
            else {
                inputs.put(name, Boolean.toString(false));
            }
        }

    }







    @Override
    public void putIsStopPressed(boolean isInit) {
        int row;
        int col = bussesTableCreator.STOP_PRESSED_INDEX;
        Boolean isChecked;
        String nameInMap;
        BusMover busMover = city.getBusMover();

        for(Bus bus : city.getBusses().values()){
            row = bus.getId();
            nameInMap = String.format("isStopPressed[%d]", row);

            if(bus.isRegular() || bus.isInUse()){
                isChecked = Boolean.valueOf(manualModeTable.getValueAt(row, col).toString());
            }
            else{
                isChecked = false;
            }
            inputs.put(nameInMap, Boolean.toString(isChecked));
            bus.setStopPressed(isChecked);
        }
    }

    @Override
    public void putIsBusFull(boolean isInit) {
        int row;
        int col = bussesTableCreator.FULL_INDEX;
        Boolean isChecked;
        String nameInMap;
        BusMover busMover = city.getBusMover();

        for(Bus bus : city.getBusses().values()){
            row = bus.getId();
            nameInMap = String.format("isBusFull[%d]", row);

            if(bus.isRegular() || bus.isInUse()){
                isChecked = Boolean.valueOf(manualModeTable.getValueAt(row, col).toString());
            }
            else{
                isChecked = false;
            }
            inputs.put(nameInMap, Boolean.toString(isChecked));
            bus.setFull(isChecked);
        }
    }
}
