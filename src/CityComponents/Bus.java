package CityComponents;//import GUI.IBus;

import Lines.FullRoute;
import Lines.Line;

public class Bus{
    private int id;
    private int[] currCoordinate;
    private int[] prevCoordinate;

    private Line line;
    private boolean shouldGoToGasStation;
    private int numOfStopsPassed;
    private int numUnstoppedStations;
    private boolean isStopPressed;
    private boolean isFull;
    private boolean shouldStopAtNextStation;
    private Station destination;
    private Station origin;
    private boolean inUse;
    private boolean shouldStopAgainInRain;
    //private int[] parkingLocation;
    public Bus(int id, int[] initialCoordinate) {
        this.id = id;
        this.currCoordinate = initialCoordinate;
        this.prevCoordinate = initialCoordinate;
        //this.parkingLocation = initialCoordinate;
        this.isStopPressed = false;//TODO: initial guarantee
        this.isFull = false;//TODO: initial guarantee


    }


    //public int[] getParkingLocation() {
//        return parkingLocation;
//    }
    public boolean isShouldStopAgainInRain() {
        return shouldStopAgainInRain;
    }

    public void setShouldStopAgainInRain(boolean shouldStopAgainInRain) {
        this.shouldStopAgainInRain = shouldStopAgainInRain;
    }

    public int[] getPrevCoordinate() {
        return prevCoordinate;
    }

    public void setPrevCoordinate(int[] prevCoordinate) {
        this.prevCoordinate = prevCoordinate;
    }


    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getCurrCoordinate() {
        return currCoordinate;
    }

    public void setCurrCoordinate(int[] newCoordinate) {
        this.prevCoordinate = this.currCoordinate;
        this.currCoordinate = newCoordinate;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
        FullRoute busFullRoute = line.getFullRoute();

        String originName = busFullRoute.getStationAtIndex(0);
        Station originStation = busFullRoute.getName2Station().get(originName);
        this.setOrigin(originStation);


        String destinationName = busFullRoute.getStationAtIndex(1);
        Station destinationStation = busFullRoute.getName2Station().get(destinationName);
        this.setDestination(destinationStation);

    }


    public boolean isStopPressed() {
        return isStopPressed;
    }

    public void setStopPressed(boolean stopPressed) {
        isStopPressed = stopPressed;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean isStopAtNextStation() {
        return shouldStopAtNextStation;
    }

    public void setStopAtNextStation(boolean shouldStop) {
        this.shouldStopAtNextStation = shouldStop;
    }


    public boolean isShouldGoToGasStation() {
        return shouldGoToGasStation;
    }

    public void setShouldGoToGasStation(boolean shouldGoToGasStation) {
        this.shouldGoToGasStation = shouldGoToGasStation;
    }

    public int getNumOfStopsPassed() {
        return numOfStopsPassed;
    }

    public void setNumOfStopsPassed(int numOfStopsPassed) {
        this.numOfStopsPassed = numOfStopsPassed;
    }

    public int getNumUnstoppedStations() {
        return numUnstoppedStations;
    }

    public void setNumUnstoppedStations(int numUnstoppedStations) {
        this.numUnstoppedStations = numUnstoppedStations;
    }




	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	public boolean isInUse() {
		return this.inUse;
	}





	







}
