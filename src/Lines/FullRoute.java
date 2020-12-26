package Lines;

import CityComponents.Bus;
import CityComponents.Station;

import java.util.HashMap;
import java.util.List;

public class FullRoute {
	private String name;
	List<String> routeStations;
	private HashMap<String, HashMap<String, Route>> originRoutes;
	private int length;
	private HashMap<String, Station> name2station;
	
	public FullRoute(String name,List<String> routeStations, HashMap<String, HashMap<String, Route>> originRoutes, int length, HashMap<String, Station> name2station) {
		this.name = name;
		this.routeStations = routeStations;
		this.originRoutes = originRoutes;
		this.length = length;
		this.name2station = name2station;
	}
	
	public String getNextDestination(String currDestination){
		int curr_index = this.routeStations.indexOf(currDestination);
		int next_index = (curr_index + 1)%this.length;
		return this.routeStations.get(next_index);
	}

	public HashMap<String, Station> getName2Station(){
		return name2station;
	}
	
	public Route getCurrRouteOfBus(Bus bus) {
		String currOrigin = bus.getOrigin().getName();
		String currDestination = bus.getDestination().getName();

		HashMap<String, Route> busOriginRoute = originRoutes.get(currOrigin);
		Route result = busOriginRoute.get(currDestination);
		return result;
	}

	public String getStationAtIndex(int i){
		return this.routeStations.get(i);
	}
	
}
