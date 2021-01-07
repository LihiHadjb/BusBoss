package Lines;

import CityComponents.Bus;

import java.util.HashMap;

public interface BusUpdateCoordinate {
    void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining);
}
