package Lines;

import CityComponents.Bus;
import CityComponents.GasStation;
import CityComponents.MainStation;
import CityComponents.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//This class provides methods to help update the next coordinate where a bus should be at, using the route
//of each line and the "sub-routes" between each 2 stations

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

	//create the routes between stations and the routes of each line
	private void initRoutes(){
		routesCreation = new RoutesCreation(gasStation, mainStation, busStations, name2station, parkingsLocations);
		originRoutes = routesCreation.getOriginRoutes();
		routesFromParkingsToMainStation = routesCreation.getRoutesFromParkingsToMainStation();
		routesFromMainStationEntranceToResrvesParkings = routesCreation.getRoutesFromMainStationEntranceToResrvesParkings();
	}

	//update the coordinates of a bus according to its current state
	// (in station/should stop/should go to gas station etc.)
	public void moveInsideCity(Bus bus,  HashMap<Integer, Bus> allBusses){
		int [] nextCoor;
		if(isAtDestinationStation(bus)){
			//at station for the first time
			//pick up passengers in station + stay is place for one more step + update next dest
			if(bus.isStopAtNextStation()) {
				bus.getDestination().setArePassengersWaiting(false);
				nextCoor = bus.getCurrCoordinate();
				updateNextDesitinationAndOriginStations(bus);
			}

			else {//in station but shouldnt stop
				updateNextDesitinationAndOriginStations(bus);
				nextCoor = getNextCoorBetweenStations(bus);
			}
		}

		//not in destination station yet
		else {
			nextCoor = getNextCoorBetweenStations(bus);
		}

		moveOrAvoidCollision(bus, nextCoor, allBusses);
	}


	//Update the origin and destination stations of a bus, assuming it now arrived to its destination station
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

	//Return the next coordinate of the bus, assuming it is currently leaving its parking towards the cenreal station
	public int[] moveFromParkingToMain(Bus bus){
		Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());
		int[] nextCoor = routeFromParkingToMain.getNextCoordinate(bus.getCurrCoordinate());
		return nextCoor;
	}

	//Return the next coordinate of the bus, assuming it is currently entering the central station, or moving inside
	//this area towards its parking location
	public int[] moveFromMainEntranceToParking(Bus bus){
		Route routeFromMainToParking = routesFromMainStationEntranceToResrvesParkings.get(bus.getId());
		int[] nextCoor = routeFromMainToParking.getNextCoordinate(bus.getCurrCoordinate());
		return nextCoor;
	}

	//Return the next coordiante of a bus, assuming it is currently in between 2 stations
	public int[] getNextCoorBetweenStations(Bus bus){
		int[] nextCoor;
		Station origin = bus.getOrigin();
		Station dest = bus.getDestination();
		Route route = originRoutes.get(origin.getName()).get(dest.getName());
		nextCoor = route.getNextCoordinate(bus.getCurrCoordinate());
		return nextCoor;
	}

	//Move the bus to the provided next coordiante if no other bus is already attempting to move to the
	//same coordinate, otherwise keep the bus in its current location
	//(go through all the busses with smaller indices (which means that their coordiante was already updated!), and see if this bus is about to go to the same coordiante.
	//if it does, than curr bus should not move)
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

	//Returns true iff the bus is currently in its designated parking spot.
	public boolean isAtPrivateParking(Bus bus){
		int[] currLocation = bus.getCurrCoordinate();
		int[] privateParking = parkingsLocations.get(bus.getId());
		return Arrays.equals(currLocation, privateParking);
	}

	//Returns true iff the bus is currently in its designated parking spot or leaving it towards the
	//main station
	public boolean isParkingOrOnWayToMain(Bus bus){
		Route routeFromParkingToMain = routesFromParkingsToMainStation.get(bus.getId());
		boolean isOnWay = routeFromParkingToMain.isOnRoute(bus.getCurrCoordinate());
		return isAtPrivateParking(bus) || isOnWay;
	}

	public RoutesCreation getRoutesCreation(){
		return routesCreation;
	}
}
