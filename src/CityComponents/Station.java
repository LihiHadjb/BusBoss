package CityComponents;

//This class creates and updates the state of a bus stations, whether or not passengers are currently
// waiting in it for a bus to pick them up)

public class Station {
    boolean arePassengersWaiting;
    int[] location;//location of the actual station
    int[] locationForTheBus;//location on the road, where the bus actually stops
    String name;
    int id;

    public Station(int[] location, String name, int[] locationForTheBus, int id) {
        this.arePassengersWaiting = false;
        this.location = location;
        this.name = name;
        this.locationForTheBus = locationForTheBus;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int[] getLocationForTheBus(){
        return locationForTheBus;
    }

    
    public String getName() {
    	return name;
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







}
