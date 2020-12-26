package Lines;

public class Line {
    private LineName lineName;
    private boolean isExtraBusNeeded;
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

    public boolean isExtraBusNeeded() {
        return isExtraBusNeeded;
    }

    public void setExtraBusNeeded(boolean extraBusNeeded) {
        isExtraBusNeeded = extraBusNeeded;
    }
    
    public void setNeedExtraBusForLine(boolean needExtraBusForLine) {
    	this.isExtraBusNeeded = needExtraBusForLine;
    }








}
