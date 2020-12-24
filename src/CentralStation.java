

import java.util.List;

public class CentralStation extends Station {
    private int[] top_left;
    private int[] top_right;
    private int[] bottom_left;
    private int[] bottom_right;

    public int[] getTop_left() {
        return top_left;
    }

    public void setTop_left(int[] top_left) {
        this.top_left = top_left;
    }

    public int[] getTop_right() {
        return top_right;
    }

    public void setTop_right(int[] top_right) {
        this.top_right = top_right;
    }

    public int[] getBottom_left() {
        return bottom_left;
    }

    public void setBottom_left(int[] bottom_left) {
        this.bottom_left = bottom_left;
    }

    public int[] getBottom_right() {
        return bottom_right;
    }

    public void setBottom_right(int[] bottom_right) {
        this.bottom_right = bottom_right;
    }

    public CentralStation(int[] top_left, int[] top_right, int[] bottom_left, int[] bottom_right,int[] loc) {
        super(loc);
        this.top_left = top_left;
        this.top_right = top_right;
        this.bottom_left = bottom_left;
        this.bottom_right = bottom_right;
        this.arePassengersWaiting = false;

    }
}
