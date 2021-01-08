package CityComponents;//import GUI.IBus;

import Lines.FullRoute;
import Lines.Line;

//This class keeps the state of a bus in every step, including its location on the board, whether it should go to the gas station,
//the line it currently serves, whether it should stop in the next station etc.
//Also keeps the destination and origin station of the bus, so that it can know how to update its next coordinate.

public class Bus{
    private int id;
    private int[] currCoordinate;
    private int[] prevCoordinate;
    private Line line;
    private boolean shouldGoToGasStation;
    private int numOfStopsPassed;
    private boolean isStopPressed;
    private boolean isFull;
    private boolean shouldStopAtNextStation;
    private Station destination;
    private Station origin;
    private boolean inUse;

    public Bus(int id, int[] initialCoordinate) {
        this.id = id;
        this.currCoordinate = initialCoordinate;
        this.prevCoordinate = initialCoordinate;
        this.isStopPressed = false;
        this.isFull = false;
    }

    public int[] getPrevCoordinate() {
        return prevCoordinate;
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

    public boolean isReserve(){
        return id == 2 || id == 3;
    }

    public boolean isRegular(){
        return id == 0 || id == 1;
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

	public void setInUse(boolean inUse) {
		this.inUse = inUse;

	}

	//auxiliary field to indicate if this bus is currently in service of some line, or if its currently parking or
    //on its way in/out of parking.
	public boolean isInUse() {
		return this.inUse;
	}





	







}
