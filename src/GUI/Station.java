package GUI;

public class Station {
    private boolean are
        PassengersWaiting;
    private int[] location;
    private StationType stationType;


    public Station(boolean arePassengersWaiting, int[] location, StationType stationType) {
        this.arePassengersWaiting = arePassengersWaiting;
        this.location = location;
        this.stationType = stationType;
    }

    public boolean isArePassengersWaiting() {
        return arePassengersWaiting;
    }

    public void setArePassengersWaiting(boolean arePassengersWaiting) {
        this.arePassengersWaiting = arePassengersWaiting;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public StationType getStationType() {
        return stationType;
    }

    public void setStationType(StationType stationType) {
        this.stationType = stationType;
    }








}
