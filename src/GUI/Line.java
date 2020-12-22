package GUI;

public class Line {
    private LineName lineName;
    private boolean isExtraBusNeeded;

    public Line(LineName lineName, boolean isExtraBusNeeded) {
        this.lineName = lineName;
        this.isExtraBusNeeded = isExtraBusNeeded;
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


}
