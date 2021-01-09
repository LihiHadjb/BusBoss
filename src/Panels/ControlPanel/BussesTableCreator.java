package Panels.ControlPanel;

import CityComponents.Bus;
import CityComponents.City;

public class BussesTableCreator extends TableCreator {
    public static final int ID_INDEX = 0;
    public static final int LINE_INDEX = 1;
    public static final int FULL_INDEX = 2;
    public static final int STOP_PRESSED_INDEX = 3;
    public static final int ROUNDS_TO_GAS_STATION_INDEX = 4;

    public static final String ID_HEADER = "Bus Number";
    public static final String LINE_HEADER = "Line";
    public static final String FULL_HEADER = "Full";
    public static final String STOP_PRESSED_HEADER = "Drop Off Request";
    public static final String ROUNDS_TO_GAS_STATION_HEADER = "Rounds Until Refuel";

    public static final String NONE = "-";



    @Override
    public Object[][] createValues(City city){
        Object[][] result;
        if(values == null){
            result = new Object[city.getNumBusses()][5];
        }
        else{
            result = values;
        }
        for(Bus bus : city.getBusses().values()){
            Object[] row = new Object[5];
            row[ID_INDEX] = String.valueOf(bus.getId() + 1);
            row[ROUNDS_TO_GAS_STATION_INDEX] = String.valueOf(city.getMAX_ROUNDS_TO_GAS_STATION() - bus.getNumOfStopsPassed());

            if(bus.isInUse() || bus.isRegular()){
                row[LINE_INDEX] = bus.getLine().getLineName().name();
                row[FULL_INDEX] = String.valueOf(bus.isFull());
                row[STOP_PRESSED_INDEX] = String.valueOf(bus.isStopPressed());
            }
            else{
                row[LINE_INDEX] = NONE;
                row[FULL_INDEX] = NONE;
                row[STOP_PRESSED_INDEX] = NONE;
            }
            result[bus.getId()] = row;

        }
        return result;
    }

    @Override
    public Object[] createHeaders(){
        Object[] result = new Object[5];
        result[ID_INDEX] = ID_HEADER;
        result[LINE_INDEX] = LINE_HEADER;
        result[FULL_INDEX] = FULL_HEADER;
        result[STOP_PRESSED_INDEX] = STOP_PRESSED_HEADER;
        result [ROUNDS_TO_GAS_STATION_INDEX] = ROUNDS_TO_GAS_STATION_HEADER;
        return result;

    }



}
