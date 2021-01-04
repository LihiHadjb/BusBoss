package Lines;

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
