package Lines;

import CityComponents.Bus;
import java.util.HashMap;

public class ReserveBusUpdateCoordinate implements BusUpdateCoordinate{
    BusMover busMover;

    public ReserveBusUpdateCoordinate(BusMover busMover) {
        this.busMover = busMover;
    }

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
                leaveParkingReserveBus(bus, allBusses);
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

    public void leaveParkingReserveBus(Bus bus, HashMap<Integer, Bus> allBusses) {
        int[] nextCoor;
        if (!busMover.isAtMainStation(bus)) {
            nextCoor = busMover.moveFromParkingToMain(bus);
        } else {//at red square
            nextCoor = busMover.getNextCoorBetweenStations(bus);
        }
        busMover.moveOrAvoidCollision(bus, nextCoor, allBusses);

    }
}
