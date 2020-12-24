import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class City {
    final int x = 16;
    final int y = 20;
    final int NUM_BUSSES = 4;
    final int NUM_RESERVE_BUSSES = 2;

    private boolean isRaining;
    private CentralStation centralStation;
    private GasStation gasStation;
    private HashMap<String, Station > busStations;
    private HashMap<Integer, IBus> busses;
    private Road roadBetweenNeighborhoodAndmMainStation;
    private Road roadBetweenNeighborhoodAndGasStation;
    private HashMap<String, int[]> stationsLocationsForTheBus;
    private HashMap<String, HashMap<String, Route>> originRoutes;
    private HashMap<String, FullRoute> fullRoutes;
    
    public City(){
        this.busStations = new HashMap<>();
        this.busses = new HashMap<>();
        this.stationsLocationsForTheBus = new HashMap<>();
        this.originRoutes = new HashMap<>();
        this.fullRoutes = new HashMap<>();
        createLineABusStations();
        createLineBBusStations();
        createCentralStation();
        createGasStation();
        createBusses();
        createRoadBetweenCityAndMainStation();
        createRoadBetweenCityAndGasStation();
        createOriginRoutes();
        createFullRoutes();
        //this.isRaining = false;// TODO: make this initial guarantee

    }
    
    
    public void createFullRoutes(){
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


    public HashMap<String, Station > getBusStations() {
        return busStations;
    }
    
    public HashMap<Integer, IBus> getBusses(){
    	return busses;
    }
    

    public int[] top_left(){
        int[] result = {x/8, y/10};
        return result;
    }

    public int[] top_right(){
        int[] result = {x/8, 5*y/8};
        return result;
    }

    public int[] bottom_left(){
        int[] result = {7*x/8, y/10};
        return result;
    }

    public int[] bottom_right(){
        int[] result = {7*x/8,5*y/8};
        return result;
    }
    
    private void createBusses() {
    	for(int i=0; i<NUM_BUSSES; i++) {
    		busses.put(i, new Bus((Integer)i, centralStation.getLocation()));
    	}
    }

    private void createGasStation(){
        //gas station
        int[] top_left_gs = {7*x/8-2, 8*y/10};
        int [] top_right_gs = {7*x/8-2, 9*y/10};
        int [] bottom_left_gs = {7*x/8-1, 8*y/10};
        int [] bottom_right_gs = {7*x/8-1, 9*y/10};

        int[] gs_loc = new int[2];
        gs_loc[0] = bottom_left_gs[0] - 1;
        gs_loc[1] = bottom_left_gs[1] -1 + (bottom_right_gs[1] - bottom_left_gs[1])/2;
        this.gasStation = new GasStation(top_left_gs, top_right_gs, bottom_left_gs, bottom_right_gs, gs_loc);

        this.stationsLocationsForTheBus.put("gas_station", gs_loc);
    }

    private void createCentralStation(){
        // main station
        int[] top_left_cs = {x/8+1, 8*y/10};
        int [] top_right_cs = {x/8+1, 9*y/10};
        int [] bottom_left_cs = {x/8+2, 8*y/10};
        int [] bottom_right_cs = {x/8+2,9*y/10};

        int[] ms_loc = new int[2];
        ms_loc[0] = bottom_left_cs[0] - 1;
        ms_loc[1] = bottom_left_cs[1] -1 + (bottom_right_cs[1] - bottom_left_cs[1])/2;
        this.centralStation = new CentralStation(top_left_cs, top_right_cs, bottom_left_cs, bottom_right_cs, ms_loc );

        this.stationsLocationsForTheBus.put("main_station", ms_loc);
    }

    private void createLineABusStations(){
        int height = ((bottom_left()[0]-1) - (top_left()[0]+1))/2;
        int width = ((top_right()[1]-1) - (top_left()[1]+1))/2;

        int[] station1Loc = new int[2];
        int[] station2Loc = new int[2];

        // left station
        station1Loc[0] = top_left()[0] + 1 + height;
        station1Loc[1] = top_left()[1];

        // right station
        station2Loc[0] = top_left()[0] + 1 + height;
        station2Loc[1] = top_right()[1];

        Station a1 = new Station(station1Loc);
        Station a2 = new Station(station2Loc);

        busStations.put("a1", a1);
        busStations.put("a2", a2);

        int[] station1LocForBus = new int[2];
        int[] station2LocForBus = new int[2];

        // left station
        station1LocForBus[0] = station1Loc[0];
        station1LocForBus[1] = station1Loc[1] + 1;

        // right station
        station2LocForBus[0] = station2Loc[0];
        station2LocForBus[1] = station2Loc[1] - 1;

        this.stationsLocationsForTheBus.put("a1", station1LocForBus);
        this.stationsLocationsForTheBus.put("a2", station2LocForBus);

    }

    public void createLineBBusStations(){
        int height = ((bottom_left()[0]-1) - (top_left()[0]+1))/2;
        int width = ((top_right()[1]-1) - (top_left()[1]+1))/2;
        int[] station1Loc = new int[2];
        int[] station2Loc = new int[2];

        // draw top west general station
        station1Loc[0] = top_left()[0] + 3;
        station1Loc[1] = top_left()[1] + 1 + width;

        // draw bottom west general station
        station2Loc[0] = bottom_left()[0] - 3;
        station2Loc[1] = bottom_left()[1] + 1  + width;

        Station b1 = new Station(station1Loc);
        Station b2 = new Station(station2Loc);

        busStations.put("b1", b1);
        busStations.put("b2", b2);

        int[] station1LocForBus = new int[2];
        int[] station2LocForBus = new int[2];

        // left station
        station1LocForBus[0] = station1Loc[0] - 1;
        station1LocForBus[1] = station1Loc[1];

        // right station
        station2LocForBus[0] = station2Loc[0] + 1;
        station2LocForBus[1] = station2Loc[1];

        this.stationsLocationsForTheBus.put("b1", station1LocForBus);
        this.stationsLocationsForTheBus.put("b2", station2LocForBus);

    }

    public void createRoadBetweenCityAndMainStation(){
    	int[] start = {top_right()[0]+1, top_right()[1]};
    	int[] end = {x/8+1, 6*y/8};

    	this.roadBetweenNeighborhoodAndmMainStation = new Road(start, end);
    }

    public void createRoadBetweenCityAndGasStation(){
    	int[] start = {bottom_right()[0]-2, bottom_right()[1]};
    	int[] end = {7*x/8-1, 6*y/8};

    	this.roadBetweenNeighborhoodAndGasStation = new Road(start, end);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public CentralStation getCentralStation() {
        return centralStation;
    }

    public void setCentralStation(CentralStation centralStation) {
        this.centralStation = centralStation;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public Road getRoadBetweenCityAndMainStation() {
        return this.roadBetweenNeighborhoodAndmMainStation;
    }

    public Road getRoadBetweenCityAndGasStation() {
        return this.roadBetweenNeighborhoodAndGasStation;
    }

    public HashMap<String, int[]> getStationsLocationsForTheBus(){
    	return this.stationsLocationsForTheBus;
    }
    
    public HashMap<String, HashMap<String, Route>> getOriginRoutes(){
    	return this.originRoutes;
    }
    
    public HashMap<String, FullRoute> getFullRoutes(){
    	return this.fullRoutes;
    }
}
