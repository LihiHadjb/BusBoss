package Lines;

import CityComponents.Bus;
import java.util.HashMap;

//This class implements the logic of how to update to next coordinate of a regular (not reserve) bus
public class RegularBusUpdateCoordinate extends BusUpdateCoordinate{

    public RegularBusUpdateCoordinate(BusMover busMover) {
        super(busMover);
    }

    //A regular bus leaves its parking only once in each run (in the beggining), and than keeps moving on the route
    //of one of the lines OR goes to the gas station and than back to the main station
    @Override
    public void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining) {
        //going out of private parking to red
        if(!bus.isInUse()){
            leaveParking(bus, allBusses);
            return;
        }
        busMover.moveInsideCity(bus, allBusses);
    }


    //Moves the bus to the next coordinate of the root from its parking spot to the main station
    //assumes !inUse (i.e. didn't leave parking area yet, this is his first round).
    @Override
    public void leaveParking(Bus bus, HashMap<Integer, Bus> allBusses){
        int [] nextCoor;
        if(!busMover.isAtMainStation(bus)){
            nextCoor = busMover.moveFromParkingToMain(bus);
            //return true;
        }

        else{//at red square
            bus.setInUse(true);
            nextCoor = busMover.getNextCoorBetweenStations(bus);
            //return false;
        }
        busMover.moveOrAvoidCollision(bus, nextCoor, allBusses);
    }

}

