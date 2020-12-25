

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BusMover {
	private GasStation gasStation;
    private CentralStation centralStation;
	private HashMap<String, Station > busStations;

    private HashMap<String, HashMap<String, Route>> originRoutes;
    private HashMap<String, FullRoute> fullRoutes;
    private HashMap<String, int[]> stationsLocationsForTheBus;

    
    public BusMover(HashMap<String, int[]> stationsLocationsForTheBus, GasStation gasStation, CentralStation centralStation, HashMap<String, Station > busStations) {
    	this.stationsLocationsForTheBus = stationsLocationsForTheBus;
		this.gasStation = gasStation;
		this.busStations = busStations;
		this.centralStation = centralStation;
		createOriginRoutes();
		createFullRoutes();
    }
    
    	
    
    
    
    public void createFullRoutes(){
    	fullRoutes = new HashMap<>();
    	
    	// Line A route
    	List<String> line_A_route = Arrays.asList("main_station", "a1", "a2");
    	FullRoute Line_A = new FullRoute("line_A", line_A_route, this.originRoutes, 3);
    	fullRoutes.put("line_A", Line_A);

    	// Line B route
    	List<String> Line_B_route = Arrays.asList("main_station", "b1", "b2");
    	FullRoute Line_B = new FullRoute("line_B", Line_B_route, this.originRoutes, 3);
    	fullRoutes.put("line_B", Line_B);


    	// Main station to gas station route
    	List<String> main_station_to_gas_station_route = Arrays.asList("main_station", "a1", "gas_station");
    	FullRoute main_station_to_gas_station = new FullRoute("main_station_to_gas_station", main_station_to_gas_station_route, this.originRoutes, 3);
    	fullRoutes.put("main_station_to_gas_station", main_station_to_gas_station);
    }


    public void createOriginRoutes(){
    	originRoutes = new HashMap<>();
    	// stations are: a1, a2, b1, b2, main_station, gas_station

    	// main_station -> a1
    	List<int[]> main_station_to_a1 = new ArrayList();
    	main_station_to_a1.add(stationsLocationsForTheBus.get("main_station"));
    	int[] main_station_to_a1_last = stationsLocationsForTheBus.get("main_station");
    	for (int i=1; i<=13; i++) {
    		int x = main_station_to_a1_last[0];
    		int y = main_station_to_a1_last[1] - 1;
    		main_station_to_a1.add(new int[]{x,y});
    		main_station_to_a1_last[0] = x;
    		main_station_to_a1_last[1] = y;
    	}
    	for (int i=1; i<=5; i++) {
    		int x = main_station_to_a1_last[0] + 1;
    		int y = main_station_to_a1_last[1];
    		main_station_to_a1.add(new int[]{x,y});
    		main_station_to_a1_last[0] = x;
    		main_station_to_a1_last[1] = y;
    	}

    	// main_station -> b2
    	List<int[]> main_station_to_b2 = new ArrayList();
    	main_station_to_b2.add(stationsLocationsForTheBus.get("main_station"));
    	int[] main_station_to_b2_last = stationsLocationsForTheBus.get("main_station");
    	for (int i=1; i<=6; i++) {
    		int x = main_station_to_b2_last[0];
    		int y = main_station_to_b2_last[1] - 1;
    		main_station_to_b2.add(new int[]{x,y});
    		main_station_to_b2_last[0] = x;
    		main_station_to_b2_last[1] = y;
    	}
    	for (int i=1; i<=9; i++) {
    		int x = main_station_to_b2_last[0] + 1;
    		int y = main_station_to_b2_last[1];
    		main_station_to_b2.add(new int[]{x,y});
    		main_station_to_b2_last[0] = x;
    		main_station_to_b2_last[1] = y;
    	}
    	for (int i=1; i<=3; i++) {
    		int x = main_station_to_b2_last[0];
    		int y = main_station_to_b2_last[1] - i;
    		main_station_to_b2.add(new int[]{x,y});
    		main_station_to_b2_last[0] = x;
    		main_station_to_b2_last[1] = y;
    	}

    	Route main_station_to_a1_route = new Route(main_station_to_a1, 13+5);
    	Route main_station_to_b2_route = new Route(main_station_to_b2, 6+9+3);
    	HashMap<String, Route> routes_from_main_stations = new HashMap<>();
    	routes_from_main_stations.put("a1", main_station_to_a1_route);
    	routes_from_main_stations.put("b2", main_station_to_b2_route);
    	originRoutes.put("main_station", routes_from_main_stations);


    	// a1 -> a2
    	List<int[]> a1_to_a2 = new ArrayList();
    	a1_to_a2.add(stationsLocationsForTheBus.get("a1"));
    	int[] a1_to_a2_last = stationsLocationsForTheBus.get("a1");
    	for (int i=1; i<=5; i++) {
    		int x = a1_to_a2_last[0] + 1;
    		int y = a1_to_a2_last[1];
    		a1_to_a2.add(new int[]{x,y});
    		a1_to_a2_last[0] = x;
    		a1_to_a2_last[1] = y;
    	}
    	for (int i=1; i<=8; i++) {
    		int x = a1_to_a2_last[0];
    		int y = a1_to_a2_last[1] + 1;
    		a1_to_a2.add(new int[]{x,y});
    		a1_to_a2_last[0] = x;
    		a1_to_a2_last[1] = y;
    	}
    	for (int i=1; i<=5; i++) {
    		int x = a1_to_a2_last[0] - 1;
    		int y = a1_to_a2_last[1];
    		a1_to_a2.add(new int[]{x,y});
    		a1_to_a2_last[0] = x;
    		a1_to_a2_last[1] = y;
    	}

    	Route a1_to_a2_route = new Route(a1_to_a2, 5+8+5);
    	HashMap<String, Route> routes_from_a1 = new HashMap<>();
    	routes_from_a1.put("a2", a1_to_a2_route);
    	originRoutes.put("a1", routes_from_a1);


    	// a2 -> main_station
    	List<int[]> a2_to_main_station = new ArrayList();
    	a2_to_main_station.add(stationsLocationsForTheBus.get("a2"));
    	int[] a2_to_main_station_last = stationsLocationsForTheBus.get("a2");
    	for (int i=1; i<=4; i++) {
    		int x = a2_to_main_station_last[0] - 1;
    		int y = a2_to_main_station_last[1];
    		a2_to_main_station.add(new int[]{x,y});
    		a2_to_main_station_last[0] = x;
    		a2_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=6; i++) {
    		int x = a2_to_main_station_last[0];
    		int y = a2_to_main_station_last[1] + 1;
    		a2_to_main_station.add(new int[]{x,y});
    		a2_to_main_station_last[0] = x;
    		a2_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = a2_to_main_station_last[0] - 1;
    		int y = a2_to_main_station_last[1];
    		a2_to_main_station.add(new int[]{x,y});
    		a2_to_main_station_last[0] = x;
    		a2_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = a2_to_main_station_last[0];
    		int y = a2_to_main_station_last[1] - 1;
    		a2_to_main_station.add(new int[]{x,y});
    		a2_to_main_station_last[0] = x;
    		a2_to_main_station_last[1] = y;
    	}

    	Route a2_to_main_station_route = new Route(a2_to_main_station, 4+6+1+1);
    	HashMap<String, Route> routes_from_a2 = new HashMap<>();
    	routes_from_a2.put("main_station", a2_to_main_station_route);
    	originRoutes.put("a2", routes_from_a2);


    	// b1 -> main_station
    	List<int[]> b1_to_main_station = new ArrayList();
    	b1_to_main_station.add(stationsLocationsForTheBus.get("b1"));
    	int[] b1_to_main_station_last = stationsLocationsForTheBus.get("b1");
    	for (int i=1; i<=10; i++) {
    		int x = b1_to_main_station_last[0];
    		int y = b1_to_main_station_last[1] + 1;
    		b1_to_main_station.add(new int[]{x,y});
    		b1_to_main_station_last[0] = x;
    		b1_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = b1_to_main_station_last[0] - 1;
    		int y = b1_to_main_station_last[1];
    		b1_to_main_station.add(new int[]{x,y});
    		b1_to_main_station_last[0] = x;
    		b1_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = b1_to_main_station_last[0];
    		int y = b1_to_main_station_last[1] - 1;
    		b1_to_main_station.add(new int[]{x,y});
    		b1_to_main_station_last[0] = x;
    		b1_to_main_station_last[1] = y;
    	}

    	Route b1_to_main_station_route = new Route(b1_to_main_station, 10+1+1);
    	HashMap<String, Route> routes_from_b1 = new HashMap<>();
    	routes_from_b1.put("main_station", b1_to_main_station_route);
    	originRoutes.put("b1", routes_from_b1);

    	// b2 -> b1
    	List<int[]> b2_to_b1 = new ArrayList();
    	b2_to_b1.add(stationsLocationsForTheBus.get("b2"));
    	int[] b2_to_b1_last = stationsLocationsForTheBus.get("b2");
    	for (int i=1; i<=3; i++) {
    		int x = b2_to_b1_last[0];
    		int y = b2_to_b1_last[1] - 1;
    		b2_to_b1.add(new int[]{x,y});
    		b2_to_b1_last[0] = x;
    		b2_to_b1_last[1] = y;
    	}
    	for (int i=1; i<=8; i++) {
    		int x = b2_to_b1_last[0] - 1;
    		int y = b2_to_b1_last[1];
    		b2_to_b1.add(new int[]{x,y});
    		b2_to_b1_last[0] = x;
    		b2_to_b1_last[1] = y;
    	}
    	for (int i=1; i<=3; i++) {
    		int x = b2_to_b1_last[0];
    		int y = b2_to_b1_last[1] + 1;
    		b2_to_b1.add(new int[]{x,y});
    		b2_to_b1_last[0] = x;
    		b2_to_b1_last[1] = y;
    	}

    	Route b2_to_b1_route = new Route(b2_to_b1, 3+8+3);
    	HashMap<String, Route> routes_from_b2 = new HashMap<>();
    	routes_from_b2.put("b1", b2_to_b1_route);
    	originRoutes.put("b2", routes_from_b2);

    	// gas_station -> main_station //TODO - Tslil: think if this one is necessary, maybe from gas_station to a2 is enough?
    	List<int[]> gas_station_to_main_station = new ArrayList();
    	gas_station_to_main_station.add(stationsLocationsForTheBus.get("gas_station"));
    	int[] gas_station_to_main_station_last = stationsLocationsForTheBus.get("gas_station");
    	for (int i=1; i<=5; i++) {
    		int x = gas_station_to_main_station_last[0];
    		int y = gas_station_to_main_station_last[1] - 1;
    		gas_station_to_main_station.add(new int[]{x,y});
    		gas_station_to_main_station_last[0] = x;
    		gas_station_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=8; i++) {
    		int x = gas_station_to_main_station_last[0] - 1;
    		int y = gas_station_to_main_station_last[1];
    		gas_station_to_main_station.add(new int[]{x,y});
    		gas_station_to_main_station_last[0] = x;
    		gas_station_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=6; i++) {
    		int x = gas_station_to_main_station_last[0];
    		int y = gas_station_to_main_station_last[1] + 1;
    		gas_station_to_main_station.add(new int[]{x,y});
    		gas_station_to_main_station_last[0] = x;
    		gas_station_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = gas_station_to_main_station_last[0] - 1;
    		int y = gas_station_to_main_station_last[1];
    		gas_station_to_main_station.add(new int[]{x,y});
    		gas_station_to_main_station_last[0] = x;
    		gas_station_to_main_station_last[1] = y;
    	}
    	for (int i=1; i<=1; i++) {
    		int x = gas_station_to_main_station_last[0];
    		int y = gas_station_to_main_station_last[1] - 1;
    		gas_station_to_main_station.add(new int[]{x,y});
    		gas_station_to_main_station_last[0] = x;
    		gas_station_to_main_station_last[1] = y;
    	}

    	Route gas_station_to_main_station_route = new Route(gas_station_to_main_station, 5+8+6+1+1);
    	HashMap<String, Route> routes_from_gas_station = new HashMap<>();
    	routes_from_gas_station.put("main_station", gas_station_to_main_station_route);
    	originRoutes.put("gas_station", routes_from_gas_station);
    }
    
    

	//if should go to gas station, set it as the destination.
	//else, check if atDestinationStation, and if true, set the next destination and origin according to the current line route
	public void updateNextDesitinationAndOriginStations(Bus bus) {
		if(bus.isShouldGoToGasStation()) {
			bus.setOrigin(bus.getDestination());
			bus.setDestination(this.gasStation);
		}

		else {
			if(bus.isAtDestinationStation()) {
				bus.setOrigin(bus.getDestination());
				FullRoute busFullRoute = fullRoutes.get(bus.getOrigin().getName());
				String nextDestinationName = busFullRoute.getNextDestination(bus.getOrigin().getName());
				
				if(nextDestinationName.equals("main_station")) {
					bus.setDestination(centralStation);
				}
				else {		
					Station nextDestinationStation = busStations.get(nextDestinationName);
					bus.setDestination(nextDestinationStation);
				}
			}

		}

	}
	
	public void updateCoordinates(Bus bus) {	
		HashMap<String, Route> busOriginRoute = originRoutes.get(bus.getOrigin().getName());
		System.out.println(busOriginRoute);
		System.out.println(bus.getDestination().getName()==null);
		Route currRoute = busOriginRoute.get(bus.getDestination().getName());
		int[] nextCoor = currRoute.getNextCoordinate(bus.getCurrCoordinate());
		bus.setCurrCoordinate(nextCoor);
		
	}
	
}
