package Lines;

import CityComponents.Bus;
import CityComponents.GasStation;
import CityComponents.MainStation;
import CityComponents.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BusMover {
	private GasStation gasStation;
    private MainStation mainStation;
	private HashMap<String, Station> busStations;
	private HashMap<String, HashMap<String, Route>> originRoutes;
	private HashMap<Integer, Route> routesFromParkingsToMainStation;
	private HashMap<Integer, Route> routesFromMainStationEntranceToResrvesParkings;
	private RoutesCreation routesCreation;


	HashMap<Integer, int[]> parkingsLocations;

	HashMap<String, Station> name2station;

    public BusMover(GasStation gasStation, MainStation mainStation, HashMap<String, Station> busStations, HashMap<Integer, int[]> parkingsLocations) {
		this.gasStation = gasStation;
		this.busStations = busStations;
		this.mainStation = mainStation;
		this.parkingsLocations = parkingsLocations;

		name2station = new HashMap<>();
		name2station.putAll(busStations);
		name2station.put("main_station", mainStation);
		name2station.put("gas_station", gasStation);
		initRoutes();

	}

	private void initRoutes(){
		routesCreation = new RoutesCreation(gasStation, mainStation, busStations, name2station, parkingsLocations);
		originRoutes = routesCreation.getOriginRoutes();
		routesFromParkingsToMainStation = routesCreation.getRoutesFromParkingsToMainStation();
		routesFromMainStationEntranceToResrvesParkings = routesCreation.getRoutesFromMainStationEntranceToResrvesParkings();
	}





	//if should go to gas station, set it as the destination.
	//else, check if atDestinationStation, and if true, set the next destination and origin according to the current line route
	public void updateNextDesitinationAndOriginStations(Bus bus) {
			String nextDestinationName;
			bus.setOrigin(bus.getDestination());

			if(bus.isShouldGoToGasStation()) {
				FullRoute fullRouteToGasStation = routesCreation.createFullRoute(LineName.valueOf("main_station_to_gas_station"));
				nextDestinationName = fullRouteToGasStation.getNextDestination(bus.getOrigin().getName());
			}

			else {
				FullRoute currLineRoute = bus.getLine().getFullRoute();
				nextDestinationName = currLineRoute.getNextDestination(bus.getOrigin().getName());
			}


			if(nextDestinationName.equals("gas_station")) {
				bus.setDestination(gasStation);
			}

			else if(nextDestinationName.equals("main_station")) {
				bus.setDestination(mainStation);
			}
			else {
				Station nextDestinationStation = busStations.get(nextDestinationName);
				bus.setDestination(nextDestinationStation);
			}
	}

//	public void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining) {
//    	if(bus.isInUse() || bus.getId()==0 || bus.getId()==1){
//			int [] oldEntrance = new int[]{mainStation.getLocationForTheBus()[0]+1, mainStation.getLocationForTheBus()[1]+1};
//			boolean atOldEntrance = Arrays.equals(oldEntrance, bus.getCurrCoordinate());
////			if ((bus.getId()==2 || bus.getId() == 3) && atOldEntrance && bus.isShouldGoToGasStation()){
////				System.out.println("bus " + bus.getId() + "inside if");
////			}
////			else{
////				System.out.println("bus " + bus.getId() + "nottttttt inside if");
////			}
//
//			if ((bus.getId()==2 || bus.getId() == 3) && atOldEntrance){
//				System.out.println("bus " + bus.getId() + " coordinates are: " + Arrays.toString(bus.getCurrCoordinate()));
//				moveFromMainStationEntranceToMainStation(bus, allBusses);
//			}
//
//    		//reserve bus going back to parking
//			if((bus.getId()==2 || bus.getId() == 3) && isAtMainStationEntrance(bus) && !bus.isShouldGoToGasStation()){
//				bus.setInUse(false);
//				Route routeFromEntranceToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
//				int[] nextCoor = routeFromEntranceToParking.getNextCoordinate(bus.getCurrCoordinate());
//				moveOrAvoidCollision(bus, nextCoor, allBusses);
//			}
//
//			//bus going out of parking
//			else if(isAtParkingArea(bus) && !isAtMainStation(bus)){
//				Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());
//				int[] nextCoor = routeFromParkingToMain.getNextCoordinate(bus.getCurrCoordinate());
//				System.out.println("bus "+ bus.getId() + "goint to town!");
//
//				moveOrAvoidCollision(bus, nextCoor, allBusses);
//			}
//
//			//bus stopping at station
//			else if(isAtDestinationStation(bus) && bus.isStopAtNextStation()){
//				bus.getDestination().setArePassengersWaiting(false);
//				//if(isAtDestinationStation(bus) && bus.isStopAtNextStation()){
//				if(isRaining){
//					bus.setShouldStopAgainInRain(true);
//				}
//				else{
//					updateNextDesitinationAndOriginStations(bus);
//				}
//				moveOrAvoidCollision(bus, bus.getCurrCoordinate(), allBusses);
//			}
//
//			//bus in station in second step in rain
//			else if(isAtDestinationStation(bus) && isRaining && !bus.isShouldStopAgainInRain()){
//				bus.setShouldStopAgainInRain(false);
//				updateNextDesitinationAndOriginStations(bus);
//			}
//
//
//			else {
//				//in station but shouldnt stop
//				if(isAtDestinationStation(bus)) {
//					updateNextDesitinationAndOriginStations(bus);
//					System.out.println("updated next origin and destination");
//				}
//
//				//between stations
//				Station origin = bus.getOrigin();
//				Station dest = bus.getDestination();
//				Route route = originRoutes.get(origin.getName()).get(dest.getName());
//
//				if(route == null){
//					System.out.println("bus: " + bus.getId());
//					System.out.println("should go to gas: " + bus.isShouldGoToGasStation());
//					System.out.println("line: " + bus.getLine().getLineName().name());
//					System.out.println("curr coord: " + Arrays.toString(bus.getCurrCoordinate()));
//					System.out.println("no root from " + origin.getName() + "to " + dest.getName());
//				}
//
//				int[] nextCoor = route.getNextCoordinate(bus.getCurrCoordinate());
//				moveOrAvoidCollision(bus, nextCoor, allBusses);
//			}
//		}
//
//    	//reserve bus staying parking
//		else if((bus.getId()==2 || bus.getId() == 3) && !bus.isInUse() && !isAtPrivateParking(bus)){
//			Route routeFromEntranceToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
//			int[] nextCoor = routeFromEntranceToParking.getNextCoordinate(bus.getCurrCoordinate());
//			moveOrAvoidCollision(bus, nextCoor, allBusses);
//		}
//	}



	private int[] moveFromParkingToMain(Bus bus, HashMap<Integer, Bus> allBusses){
		Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());
		int[] nextCoor = routeFromParkingToMain.getNextCoordinate(bus.getCurrCoordinate());
		//System.out.println("bus "+ bus.getId() + "goint to town!");

		return nextCoor;

	}

	private int[] getNextCoorBetweenStations(Bus bus){
		int[] nextCoor;
		Station origin = bus.getOrigin();
		Station dest = bus.getDestination();
		Route route = originRoutes.get(origin.getName()).get(dest.getName());
		nextCoor = route.getNextCoordinate(bus.getCurrCoordinate());
		return nextCoor;

	}

	public void moveNormalBus(Bus bus, HashMap<Integer, Bus> allBusses){
		int[] nextCoor;
		//going out of private parking to red
		if(isAtParkingArea(bus) && !isAtMainStation(bus)){
			nextCoor = moveFromParkingToMain(bus, allBusses);
		}

		//should stop at the current station
		else if(isAtDestinationStation(bus)){
			//at station for the first time
			if(bus.isStopAtNextStation()) {
				bus.getDestination().setArePassengersWaiting(false);
				nextCoor = bus.getCurrCoordinate();
				updateNextDesitinationAndOriginStations(bus);
			}

			else {
				updateNextDesitinationAndOriginStations(bus);
				nextCoor = getNextCoorBetweenStations(bus);
			}
		}
		else {
			nextCoor = getNextCoorBetweenStations(bus);
		}

		moveOrAvoidCollision(bus, nextCoor, allBusses);


	}



	private void moveFromMainStationEntranceToResrvesParkings(Bus bus, HashMap<Integer, Bus> allBusses){
		Route routeFromEntranceToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
		int[] nextCoor = routeFromEntranceToParking.getNextCoordinate(bus.getCurrCoordinate());
		moveOrAvoidCollision(bus, nextCoor, allBusses);
	}


	public void moveReserveBus(Bus bus, HashMap<Integer, Bus> allBusses) {
		if (bus.isInUse() || bus.isShouldGoToGasStation()) {
			moveNormalBus(bus, allBusses);
		} else if (!isAtPrivateParking(bus)) {
			bus.setInUse(false);
			moveFromMainStationEntranceToResrvesParkings(bus, allBusses);
		}
	}


	public void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining){
    	if(isRaining && !bus.isShouldGoToGasStation() && !bus.isStopAtNextStation()){
    		System.out.println("should stop in rain!!!!");
		}
    	if(bus.getId() == 0 || bus.getId() == 1){
    		moveNormalBus(bus, allBusses);
		}
    	else{
    		moveReserveBus(bus, allBusses);
		}
	}


	public RoutesCreation getRoutesCreation(){
    	return routesCreation;
	}
	private void moveFromMainStationEntranceToMainStation(Bus bus, HashMap<Integer, Bus> allBusses){
		int[] nextCoor = mainStation.getLocationForTheBus();
		moveOrAvoidCollision(bus, nextCoor, allBusses);
	}

	//go through all the busses with smaller indices (which means that their coordiante was already updated!), and see if this bus is about to go to the same coordiante.
	//if it does, than curr bus should not move
	public void moveOrAvoidCollision(Bus bus, int[] nextCoor, HashMap<Integer, Bus> allBusses){
    	for(int i=0; i<bus.getId(); i++){
			Bus prevBus = allBusses.get(i);
			if(Arrays.equals((nextCoor), prevBus.getCurrCoordinate())){
				return;
			}
		}
		bus.setCurrCoordinate(nextCoor);

	}




	public boolean isAtMainStation(Bus bus) {
    	return Arrays.equals(bus.getCurrCoordinate(), mainStation.getLocationForTheBus());
	}

	public boolean isAtMainStationEntrance(Bus bus) {
    	int[] entrance = new int[]{mainStation.getLocationForTheBus()[0]+1, mainStation.getLocationForTheBus()[1]-1};
		return Arrays.equals(bus.getCurrCoordinate(), entrance);
	}


	public boolean isAtGasStation(Bus bus) {
    	return  Arrays.equals(bus.getCurrCoordinate(), gasStation.getLocationForTheBus());
	}


	public boolean isAtDestinationStation(Bus bus) {
		return Arrays.equals(bus.getCurrCoordinate(), (bus.getDestination().getLocationForTheBus()));
	}


	public boolean isAtParkingArea(Bus bus){
        Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());

        //TODO: Stopped here, added next line:
        //Route routeFromEntranceToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
		return routeFromParkingToMain.isOnRoute(bus.getCurrCoordinate()) || isAtPrivateParking(bus);

		//return routeFromParkingToMain.isOnRoute(bus.getCurrCoordinate()) ||routeFromEntranceToParking.isOnRoute(bus.getCurrCoordinate())|| isAtPrivateParking(bus);

	}

	public boolean isAtPrivateParking(Bus bus){
    	int[] currLocation = bus.getCurrCoordinate();
    	int[] privateParking = parkingsLocations.get(bus.getId());
    	return Arrays.equals(currLocation, privateParking);
	}

}
