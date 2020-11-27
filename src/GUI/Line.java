package GUI;

public class Line {

    public Line(LineName lineName, boolean isExtraBusNeeded) {
        this.lineName = lineName;
        this.isExtraBusNeeded = isExtraBusNeeded;
    }

    private LineName lineName;
    private boolean isExtraBusNeeded;

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


}
