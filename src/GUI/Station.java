package GUI;

public class Station {
    private boolean isPeopleWaiting;
    private int[] location;
    private StationType stationType;


    public Station(boolean isPeopleWaiting, int[] location, StationType stationType) {
        this.isPeopleWaiting = isPeopleWaiting;
        this.location = location;
        this.stationType = stationType;
    }

    public boolean isPeopleWaiting() {
        return isPeopleWaiting;
    }

    public void setPeopleWaiting(boolean peopleWaiting) {
        isPeopleWaiting = peopleWaiting;
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
