//import GUI.IBus;

public class Bus{
    private int id;
    private int[] currCoordinate;
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

    public Bus(int id, int[] initialCoordinate) {
        this.id = id;
        this.currCoordinate = initialCoordinate;
        this.isStopPressed = false;//TODO: initial guarantee
        this.isFull = false;//TODO: initial guarantee

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

    public void setCurrCoordinate(int[] currCoordinate) {
        //System.out.println(String.format("updateing to %d, %d", currCoordinate[0], currCoordinate[1]));
        this.currCoordinate = currCoordinate;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
        FullRoute busFullRoute = line.getFullRoute();
        String destinationName = busFullRoute.getNextDestination(this.origin.getName());
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
