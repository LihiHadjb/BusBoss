package GUI;

import java.util.List;

public class CentralNeighbourhood extends Neighbourhood{
    private Mall mall;

    public CentralNeighbourhood(List<Station> localStations, List<Station> generalLineStations, int[] top_left, int[] top_right, int[] bottom_left, int[] bottom_right) {
        super(localStations, generalLineStations, top_left, top_right, bottom_left, bottom_right);
        //this.mall =  mall;
    }
}
