package Lines;

import CityComponents.Bus;
import java.util.HashMap;

public class RegularBusUpdateCoordinate implements BusUpdateCoordinate {
    BusMover busMover;

    public RegularBusUpdateCoordinate(BusMover busMover) {
        this.busMover = busMover;
    }

    @Override
    public void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining) {
        int[] nextCoor;
        //going out of private parking to red
        if(!bus.isInUse()){
            leaveParkingNormalBus(bus, allBusses);
            return;
        }
        busMover.moveInsideCity(bus, allBusses);
    }



    //assumes !inUse (i.e. didn't leave parking area yet, this is his first round).
    public void leaveParkingNormalBus(Bus bus, HashMap<Integer, Bus> allBusses){
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

