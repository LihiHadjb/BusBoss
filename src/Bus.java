//import GUI.IBus;

public class Bus implements IBus{
    private int id;
    private int[] currCoordinate;
    private Line line;
    private boolean isInService;
    private boolean shouldGoToGasStation;
    private int numOfStopsPassed;
    private int numUnstoppedStations;
    private boolean isStopPressed;
    private boolean isFull;
    private boolean shouldStop;
    private Station destination;
    private Station origin;

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
        this.currCoordinate = currCoordinate;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public boolean isInService() {
        return isInService;
    }

    public void setInService(boolean inService) {
        isInService = inService;
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

    public boolean shouldStop() {
        return shouldStop;
    }

    public void setShouldStop(boolean shouldStop) {
        shouldStop = shouldStop;
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



	@Override
	public boolean isAtMainStation() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isAtGasStation() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isAtDestinationStation() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void updateNextDesitinationAndOriginStations() {
		// TODO Auto-generated method stub
		
	}







}
