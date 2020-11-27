package GUI;

public class Bus {
    private int id;
    private int[] currCoordinate;
    private Line line;
    private boolean isInService;
    private boolean shouldGoToGasStation;
    private int numOfStopsPassed;
    private int numUnstoppedStations;
    private boolean isStopPressed;
    private boolean isFull;

    public Bus(int id, int[] currCoordinate, Line line, boolean isInService, boolean isStopPressed, boolean isFull, boolean shouldGoToGasStation, int numOfStopsPassed, int numUnstoppedStations) {
        this.id = id;
        this.currCoordinate = currCoordinate;
        this.line = line;
        this.isInService = isInService;
        this.isStopPressed = isStopPressed;
        this.isFull = isFull;
        this.shouldGoToGasStation = shouldGoToGasStation;
        this.numOfStopsPassed = numOfStopsPassed;
        this.numUnstoppedStations = numUnstoppedStations;
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







}
