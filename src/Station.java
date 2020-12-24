public class Station {
    boolean arePassengersWaiting;
    int[] location;


    public Station(int[] location) {
        this.arePassengersWaiting = false; //TODO: do we want to make this initial guarantee?
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
