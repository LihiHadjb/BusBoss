package GUI;

public class Station {
    private boolean arePassengersWaiting;
    private int[] location;


    public Station(boolean arePassengersWaiting, int[] location) {
        this.arePassengersWaiting = arePassengersWaiting;
        this.location = location;
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








}
