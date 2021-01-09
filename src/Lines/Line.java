package Lines;

//An instance of this class representes the state of a bus line (A or B), including the counter for the number
//of times a bus serving this line couldnt pick up more passengers beacuse it was full, and whether there was
//an alert sent to get more help for this line (isWaiting).
public class Line {
    private LineName lineName;
    private boolean isWaiting;
    private FullRoute fullRoute;
    private int numUnstopped;

    public Line(LineName lineName, FullRoute fullRoute) {
        this.lineName = lineName;
        this.fullRoute = fullRoute;

    }


    public int getNumUnstopped() {
        return numUnstopped;
    }

    public void setNumUnstopped(int numUnstopped) {
        this.numUnstopped = numUnstopped;
    }

    public FullRoute getFullRoute(){
        return this.fullRoute;
    }

    public LineName getLineName() {
        return lineName;
    }

    public void setLineName(LineName lineName) {
        this.lineName = lineName;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }








}
