package Lines;

import CityComponents.Bus;
import CityComponents.Station;

import java.util.Arrays;
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


	public FullRoute createFullRoute(LineName lineName) {
		FullRoute result = null;
		switch (lineName.toString()) {
			case ("A"):
				// Lines.Line A route
				List<String> line_A_route = Arrays.asList("main_station", "a1", "a2");
				FullRoute Line_A = new FullRoute("line_A", line_A_route, this.originRoutes, 3, name2station);
				return Line_A;
			case ("B"):
				// Lines.Line B route
				List<String> Line_B_route = Arrays.asList("main_station", "b2", "b1");
				FullRoute Line_B = new FullRoute("line_B", Line_B_route, this.originRoutes, 3, name2station);
				return Line_B;
			case ("main_station_to_gas_station"):
				// Main station to gas station route
				List<String> main_station_to_gas_station_route = Arrays.asList("main_station", "a1", "gas_station");
				FullRoute main_station_to_gas_station = new FullRoute("main_station_to_gas_station", main_station_to_gas_station_route, this.originRoutes, 3, name2station);
				return main_station_to_gas_station;
//		case("parking0_to_main_station"):
//			List<String> parking0_to_main_station_route = Arrays.asList("parking0", "main_station");
//			FullRoute parking0_to_main_station = new FullRoute("parking0_to_main_station", parking0_to_main_station_route, this.originRoutes, 3, name2station);
//			return parking0_to_main_station;
//		case("parking1_to_main_station"):
//			List<String> parking1_to_main_station_route = Arrays.asList("parking1", "main_station");
//			FullRoute parking1_to_main_station = new FullRoute("parking1_to_main_station", parking1_to_main_station_route, this.originRoutes, 3, name2station);
//			return parking1_to_main_station;
//		case("parking2_to_main_station"):
//			List<String> parking2_to_main_station_route = Arrays.asList("parking2", "main_station");
//			FullRoute parking2_to_main_station = new FullRoute("parking2_to_main_station", parking2_to_main_station_route, this.originRoutes, 3, name2station);
//			return parking2_to_main_station;
//		case("parking3_to_main_station"):
//			List<String> parking3_to_main_station_route = Arrays.asList("parking3", "main_station");
//			FullRoute parking3_to_main_station = new FullRoute("parking3_to_main_station", parking3_to_main_station_route, this.originRoutes, 3, name2station);
//			return parking3_to_main_station;

		}
		return null;
	}


	
}
