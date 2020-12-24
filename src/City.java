import java.util.HashMap;

public class City {
    final int x = 16;
    final int y = 20;
    final int NUM_BUSSES = 4;

    private boolean isRaining;
    private CentralStation centralStation;
    private GasStation gasStation;
    private HashMap<String, Station > busStations;
    private HashMap<Integer, Bus> busses;
    //private List<Route> routes;


    public HashMap<String, Station > getBusStations() {
        return busStations;
    }
    
    public HashMap<Integer, Bus> getBusses(){
    	return busses;
    }

    public City(){
        this.busStations = new HashMap<>();
        this.busses = new HashMap<>();
        createLineABusStations();
        createLineBBusStations();
        createCentralStation();
        createGasStation();
        createBusses();
        //this.isRaining = false;// TODO: make this initail guarantee

    }
    

    public int[] top_left(){
        int[] result = {x/8, 2*y/8};
        return result;
    }

    public int[] top_right(){
        int[] result = {x/8, 6*y/8};
        return result;
    }

    public int[] bottom_left(){
        int[] result = {7*x/8, 2*y/8};
        return result;
    }

    public int[] bottom_right(){
        int[] result = {7*x/8,6*y/8};
        return result;
    }
    
    private void createBusses() {
    	for(int i=0; i<NUM_BUSSES; i++) {
    		busses.put(i, new Bus((Integer)i, centralStation.getLocation()));
    	}
    }

    private void createGasStation(){
        //gas station
        int[] top_left_gs = {x-x/7-2, 3*y/8};
        int [] top_right_gs = {x-x/7-2, 5*y/8};
        int [] bottom_left_gs = {x-x/7-2, 3*y/8};
        int [] bottom_right_gs = {x-x/7-2, 5*y/8};

        int[] gs_loc = new int[2];
        gs_loc[0] = bottom_left_gs[0] - 1;
        gs_loc[1] = bottom_left_gs[1] + 1 + (bottom_right_gs[1] - bottom_left_gs[1])/2; //TODO: VERIFY IT'S OK - HERE!
        this.gasStation = new GasStation(top_left_gs, top_right_gs, bottom_left_gs, bottom_right_gs, gs_loc);
    }

    private void createCentralStation(){
        // main station
        int[] top_left_cs = {1+x/7, 3*y/8};
        int [] top_right_cs = {1+x/7, 5*y/8};
        int [] bottom_left_cs = {1+x/7, 3*y/8};
        int [] bottom_right_cs = {1+x/7,5*y/8};

        int[] ms_loc = new int[2];
        ms_loc[0] = bottom_left_cs[0] - 1;
        ms_loc[1] = bottom_left_cs[1] + 1 + (bottom_right_cs[1] - bottom_left_cs[1])/2; //TODO: VERIFY IT'S OK - HERE!
        this.centralStation = new CentralStation(top_left_cs, top_right_cs, bottom_left_cs, bottom_right_cs, ms_loc );
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





}
