package Lines;

public class Line {
    private LineName lineName;
    private boolean isWaiting;
    private FullRoute fullRoute;


    public Line(LineName lineName, FullRoute fullRoute) {
        this.lineName = lineName;
        this.fullRoute = fullRoute;

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
