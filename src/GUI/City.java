package GUI;

import java.util.List;

public class City {
    //TODO: maybe do this per neighbourhood
    private boolean isRaining;
    private List<Neighbourhood> neighbourhoods;
    private CentralStation centralStation;
    private GasStation gasStation;
    //private List<Route> routes;

    public City(boolean isRaining, List<Neighbourhood> neighbourhoods, CentralStation centralStation, GasStation gasStation) {
        this.isRaining = isRaining;
        this.neighbourhoods = neighbourhoods;
        this.centralStation = centralStation;
        this.gasStation = gasStation;
    }



    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public List<Neighbourhood> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void setNeighbourhoods(List<Neighbourhood> neighbourhoods) {
        this.neighbourhoods = neighbourhoods;
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
