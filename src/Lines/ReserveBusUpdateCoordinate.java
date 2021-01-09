package Lines;

import CityComponents.Bus;
import java.util.HashMap;

//This class implements the logic of how to update to next coordinate of a reserve bus
public class ReserveBusUpdateCoordinate extends BusUpdateCoordinate{

    public ReserveBusUpdateCoordinate(BusMover busMover) {
        super(busMover);
    }

    //A reserve bus leaves its parking every time it is sent as an extra bus to help one of the lines, in which case
    //it is currently marked as isUse=false.
    // Once it it set to be inUse=true it should behave like a normal bus (keeps moving on the route
    //of its current line OR go to the gas station and than back to the main station
    @Override
    public void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining) {
        int[] nextCoor;

        if(bus.isShouldGoToGasStation()){
            bus.setInUse(true);
            busMover.moveInsideCity(bus, allBusses);
            return;
        }

        //turn off inUse when comming back
        if(busMover.isAtMainStationEntrance(bus)){
            bus.setInUse(false);
        }

        //going out of parking OR in the middle of a round
        if(bus.isInUse()){
            if(busMover.isParkingOrOnWayToMain(bus)){
                leaveParking(bus, allBusses);
                return;
            }
            busMover.moveInsideCity(bus, allBusses);
        }

        //!inUse (currently parking OR going back to park)
        else{
            if(busMover.isAtPrivateParking(bus)){
                bus.setCurrCoordinate(bus.getCurrCoordinate());
                return;
            }

            else{ //going back to park
                nextCoor = busMover.moveFromMainEntranceToParking(bus);
                busMover.moveOrAvoidCollision(bus, nextCoor, allBusses);
            }
        }
    }

    @Override
    public void leaveParking(Bus bus, HashMap<Integer, Bus> allBusses) {
        int[] nextCoor;
        if (!busMover.isAtMainStation(bus)) {
            nextCoor = busMover.moveFromParkingToMain(bus);
        } else {//at red square
            nextCoor = busMover.getNextCoorBetweenStations(bus);
        }
        busMover.moveOrAvoidCollision(bus, nextCoor, allBusses);

    }
}
