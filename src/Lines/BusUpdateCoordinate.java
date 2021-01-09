package Lines;

import CityComponents.Bus;

import java.util.HashMap;

public abstract class BusUpdateCoordinate {
    BusMover busMover;

    public BusUpdateCoordinate(BusMover busMover) {
        this.busMover = busMover;
    }

    public abstract void updateCoordinates(Bus bus, HashMap<Integer, Bus> allBusses, boolean isRaining);
    public abstract void leaveParking(Bus bus, HashMap<Integer, Bus> allBusses);

  }
