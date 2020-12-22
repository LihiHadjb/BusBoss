package GUI;

import java.util.List;

public class City {
    private boolean isRaining;
    private CentralStation centralStation;
    private GasStation gasStation;
    private List<Station> busStations;
    //private List<Route> routes;
    final int x = 16;
    final int y = 20;

    public List<Station> getBusStations() {
        return busStations;
    }

    public void setBusStations(List<Station> busStations) {
        this.busStations = busStations;
    }


//    public City(boolean isRaining, List<Station> busStations, CentralStation centralStation, GasStation gasStation) {
//        this.isRaining = isRaining;
//        this.centralStation = centralStation;
//        this.gasStation = gasStation;
//        this.busStations = busStations;
//    }

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
