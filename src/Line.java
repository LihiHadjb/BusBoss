import java.util.List;

public class Line {
    private LineName lineName;
    private boolean isExtraBusNeeded;
    private List<String> route;

    public Line(LineName lineName, List<String> route) {
        this.lineName = lineName;
        this.route = route;
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
