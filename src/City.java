import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class City {
    final int x = 16;
    final int y = 20;
    final int NUM_BUSSES = 4;
    final int NUM_RESERVE_BUSSES = 2;
    final int NUM_LINES = 2;

    private boolean isRaining;
    private MainStation mainStation;
    private GasStation gasStation;
    private HashMap<String, Station > busStations;
    private HashMap<Integer, Bus> busses;
    private Road roadBetweenNeighborhoodAndmMainStation;
    private Road roadBetweenNeighborhoodAndGasStation;
    private List<Line> lines;
    private BusMover busMover;


    public City(){
        this.busStations = new HashMap<>();
        this.busses = new HashMap<>();
        this.lines = new ArrayList<>();
        createLineABusStations();
        createLineBBusStations();
        createCentralStation();
        createGasStation();

        createRoadBetweenCityAndMainStation();
        createRoadBetweenCityAndGasStation();

        createBusMover();
        createLines();

        createBusses();

        //this.isRaining = false;// TODO: make this initial guarantee




    }
    
    public void createBusMover() {
    	this.busMover = new BusMover(gasStation, mainStation, busStations);
    }

    public void createLines() {
        this.lines = new ArrayList<>();
        Line lineA = new Line(LineName.valueOf("A"), busMover.createFullRoute(LineName.valueOf("A")));
        Line lineB = new Line(LineName.valueOf("B"), busMover.createFullRoute(LineName.valueOf("B")));
        lines.add(lineA);
        lines.add(lineB);


    	
    }


    public HashMap<String, Station > getBusStations() {
        return busStations;
    }

    public HashMap<Integer, Bus> getBusses(){
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
    
    public void createBusses() {
    	for(int i=0; i<NUM_BUSSES; i++) {
    		Bus bus = new Bus((Integer)i, mainStation.getLocationForTheBus());
    		bus.setOrigin(mainStation);
    		busses.put(i, bus);
    	}

    	Bus lineABus = busses.get(0);
    	Bus lineBBus = busses.get(1);

    	lineABus.setLine(lines.get(0));
        lineBBus.setLine(lines.get(1));


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
        this.gasStation = new GasStation(top_left_gs, top_right_gs, bottom_left_gs, bottom_right_gs, gs_loc, gs_loc);

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
        this.mainStation = new MainStation(top_left_cs, top_right_cs, bottom_left_cs, bottom_right_cs, ms_loc, ms_loc);

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

        int[] station1LocForBus = new int[2];
        int[] station2LocForBus = new int[2];

        // left station
        station1LocForBus[0] = station1Loc[0];
        station1LocForBus[1] = station1Loc[1] + 1;

        // right station
        station2LocForBus[0] = station2Loc[0];
        station2LocForBus[1] = station2Loc[1] - 1;

        Station a1 = new Station(station1Loc, "a1", station1LocForBus);
        Station a2 = new Station(station2Loc, "a2", station2LocForBus);

        busStations.put("a1", a1);
        busStations.put("a2", a2);



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

        int[] station1LocForBus = new int[2];
        int[] station2LocForBus = new int[2];

        // left station
        station1LocForBus[0] = station1Loc[0] - 1;
        station1LocForBus[1] = station1Loc[1];

        // right station
        station2LocForBus[0] = station2Loc[0] + 1;
        station2LocForBus[1] = station2Loc[1];


        Station b1 = new Station(station1Loc, "b1", station1LocForBus);
        Station b2 = new Station(station2Loc, "b2", station2LocForBus);

        busStations.put("b1", b1);
        busStations.put("b2", b2);

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

    public List<Line> getLines(){
    	return this.lines;
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

    public MainStation getMainStation() {
        return mainStation;
    }

    public void setMainStation(MainStation mainStation) {
        this.mainStation = mainStation;
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
    
    public BusMover getBusMover() {
    	return this.busMover;
    }
    
    public void updateCity() {
		for(Bus bus : busses.values()) {
            busMover.updateCoordinates(bus, busses);
		}
		//do any other updates needed for the dashboard or whatever...(isExtraNeeded etc...) 
    }



}
