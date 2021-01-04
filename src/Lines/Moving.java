//package Lines;
//
//import CityComponents.Bus;
//import CityComponents.Station;
//
//import java.util.Arrays;
//import java.util.HashMap;
//
//public class Moving {
//
//    private int[] moveFromParkingToMain(Bus bus, HashMap<Integer, Bus> allBusses){
//        Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());
//        int[] nextCoor = routeFromParkingToMain.getNextCoordinate(bus.getCurrCoordinate());
//        System.out.println("bus "+ bus.getId() + "goint to town!");
//
//        return nextCoor;
//
//    }
//
//    private int[] getNextCoorBetweenStations(Bus bus){
//        int[] nextCoor;
//        Station origin = bus.getOrigin();
//        Station dest = bus.getDestination();
//        Route route = originRoutes.get(origin.getName()).get(dest.getName());
//        nextCoor = route.getNextCoordinate(bus.getCurrCoordinate());
//        return nextCoor;
//
//    }
//
//    public void moveNormalBus(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining){
//        int[] nextCoor;
//        //going out of private parking to red
//        if(isAtParkingArea(bus) && !isAtMainStation(bus)){
//            nextCoor = moveFromParkingToMain(bus, allBusses);
//        }
//
//        //should stop at the current station
//        else if(isAtDestinationStation(bus)){
//            //at station for the first time
//            if(bus.isStopAtNextStation()) {
//                bus.getDestination().setArePassengersWaiting(false);
//                nextCoor = bus.getCurrCoordinate();
//                updateNextDesitinationAndOriginStations(bus);
//            }
//
//            else {
//                updateNextDesitinationAndOriginStations(bus);
//                nextCoor = getNextCoorBetweenStations();
//            }
//        }
//        else {
//            nextCoor = getNextCoorBetweenStations();
//        }
//
//        moveOrAvoidCollision(bus, nextCoor, allBusses);
//
//
//    }
//
//
//
//    private void moveFromMainStationEntranceToResrvesParkings(Bus bus, HashMap<Integer, Bus> allBusses){
//        Route routeFromEntranceToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
//        int[] nextCoor = routeFromEntranceToParking.getNextCoordinate(bus.getCurrCoordinate());
//        moveOrAvoidCollision(bus, nextCoor, allBusses);
//    }
//
//
//    public void moveReserveBus(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining) {
//        if (bus.isInUse() || bus.isShouldGoToGasStation()) {
//            moveNormalBus(bus, allBusses, isRaining);
//        } else if (!atPrivateParking(bus)) {
//            moveFromMainStationEntranceToResrvesParkings(bus, allBusses);
//        }
//    }
//
//
//
//
//
//
//}
