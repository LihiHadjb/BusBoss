package CityComponents;

public class Station {
    boolean arePassengersWaiting;
    int[] location;
    String name;
    int[] locationForTheBus;
    int id;

    public Station(int[] location, String name, int[] locationForTheBus, int id) {
        this.arePassengersWaiting = false; //TODO: do we want to make this initial guarantee?
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

    public void setLocation(int[] location) {
        this.location = location;
    }








}
