package Panels;

import CityComponents.Bus;
import CityComponents.City;

public class BussesTableCreator extends TableCreator{
    final int ID_INDEX = 0;
    final int LINE_INDEX = 1;
    final int FULL_INDEX = 2;
    final int STOP_PRESSES_INDEX = 3;
    final int ROUNDS_TO_GAS_STATION_INDEX = 4;

    final String ID_HEADER = "Bus Number";
    final String LINE_HEADER = "Line";
    final String FULL_HEADER = "Full";
    final String STOP_PRESSED_HEADER = "STOP";
    final String ROUNDS_TO_GAS_STATION_HEADER = "Rounds Until Gas Station";

    final String NONE = "-";



    @Override
    public Object[][] createValues(City city){
        Object[][] result;
        if(values == null){
            result = new String[city.getNumBusses()][5];
        }
        else{
            result = values;
        }
        for(Bus bus : city.getBusses().values()){
            String[] row = new String[5];
            row[ID_INDEX] = String.valueOf(bus.getId() + 1);
            if(bus.isInUse() || bus.isRegular()){
                row[LINE_INDEX] = bus.getLine().getLineName().name();
                row[FULL_INDEX] = String.valueOf(bus.isFull());
                row[STOP_PRESSES_INDEX] = String.valueOf(bus.isStopPressed());
                row[ROUNDS_TO_GAS_STATION_INDEX] = String.valueOf(city.getMAX_ROUNDS_TO_GAS_STATION() - bus.getNumOfStopsPassed());
            }
            else{
                row[LINE_INDEX] = NONE;
                row[FULL_INDEX] = NONE;
                row[STOP_PRESSES_INDEX] = NONE;
                row[ROUNDS_TO_GAS_STATION_INDEX] = String.valueOf(city.getMAX_ROUNDS_TO_GAS_STATION());

            }
            result[bus.getId()] = row;

        }
        return result;
    }

    @Override
    public Object[] createHeaders(){
        Object[] result = new String[5];
        result[ID_INDEX] = ID_HEADER;
        result[LINE_INDEX] = LINE_HEADER;
        result[FULL_INDEX] = FULL_HEADER;
        result[STOP_PRESSES_INDEX] = STOP_PRESSED_HEADER;
        result [ROUNDS_TO_GAS_STATION_INDEX] = ROUNDS_TO_GAS_STATION_HEADER;
        return result;

    }



}
