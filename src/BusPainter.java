import CityComponents.Bus;
import CityComponents.City;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class BusPainter {
    Graphics g;
    City city;
    int dim;
    int DELAY;
    private HashMap<int[], String> busStationsDirections;

    final String eastTowest = "eastToWest";
    final String westToEast = "westToEast";
    final String northToSouth = "northToSouth";
    final String southToNorth = "southToNorth";
    final String stay = "stay";

    BufferedImage regularBusEastToWest;
    BufferedImage regularBusWestToEast;
    BufferedImage regularBusNorthToSouth;
    BufferedImage regularBusSouthToNorth;

    BufferedImage reserveBusEastToWest;
    BufferedImage reserveBusWestToEast;
    BufferedImage reserveBusNorthToSouth;
    BufferedImage reserveBusSouthToNorth;

    public BusPainter(Graphics g, City city, int dim, int DELAY){
        this.g = g;
        this.city = city;
        this.dim = dim;
        this.DELAY = DELAY;

        initBussesImages();
    }

    private void initBussesImages() {
        try{
            //regular busses
            this.regularBusEastToWest = ImageIO.read(new File("images/Regular/regular_bus_east_to_west.jpg"));
            this.regularBusWestToEast = ImageIO.read(new File("images/Regular/regular_bus_west_to_east.jpg"));
            this.regularBusNorthToSouth = ImageIO.read(new File("images/Regular/regular_bus_north_to_south.jpg"));
            this.regularBusSouthToNorth = ImageIO.read(new File("images/Regular/regular_bus_south_to_north.jpg"));

            //reserve busses
            this.reserveBusEastToWest = ImageIO.read(new File("images/Reserve/reserve_bus_east_to_west.jpg"));
            this.reserveBusWestToEast = ImageIO.read(new File("images/Reserve/reserve_bus_west_to_east.jpg"));
            this.reserveBusNorthToSouth = ImageIO.read(new File("images/Reserve/reserve_bus_north_to_south.jpg"));
            this.reserveBusSouthToNorth = ImageIO.read(new File("images/Reserve/reserve_bus_south_to_north.jpg"));
        }
        catch (IOException e){
            System.out.println("error in reading images!");
            e.printStackTrace();
        }
    }

    public String getStationDirection(int[] location){
        if (Arrays.equals(location, ((city.getBusStations().get("a1")).getLocationForTheBus()))){
            System.out.println("In a1 !!!");
            return northToSouth;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("a2")).getLocationForTheBus()))){
            System.out.println("In a2 !!!");
            return southToNorth;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("b1")).getLocationForTheBus()))){
            System.out.println("In b1 !!!");
            return westToEast;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("b2")).getLocationForTheBus()))){
            System.out.println("In b2 !!!");
            return eastTowest;
        }

        else if (Arrays.equals(location, ((city.getMainStation()).getLocationForTheBus()))){
            System.out.println("In main station !!!");
            return eastTowest;
        }

        else if (Arrays.equals(location, ((city.getGasStation()).getLocationForTheBus()))){
            System.out.println("In gas station !!!");
            return eastTowest;
        }

        return null;
    }

    private String chooseDirection(Bus bus){
        int[] prev = bus.getPrevCoordinate();
        int[] curr = bus.getCurrCoordinate();

        if(prev[0] > curr[0]){
            return southToNorth;
        }

        else if(prev[0] < curr[0]){
            return northToSouth;
        }

        else if(prev[1] > curr[1]){
            return eastTowest;
        }
        else if(prev[1] < curr[1]){
            return westToEast;
        }

        String stationDirection = getStationDirection(curr);

        if (stationDirection != null){
            return stationDirection;
        }

        return stay;
    }

    private int[] getSubCoordinate(Bus bus, String direction, int subIterationNumber){
        int y_offset = 0;
        int x_offset = 0;
        int newY=0;
        int newX=0;
        switch (direction){
            case(eastTowest):
                y_offset = -subIterationNumber;
                x_offset = 0;
                break;
            case (westToEast):
                y_offset = +subIterationNumber;
                x_offset = 0;
                break;
            case (northToSouth):
                y_offset = 0;
                x_offset = +subIterationNumber;
                break;
            case (southToNorth):
                y_offset = 0;
                x_offset = -subIterationNumber;
                break;
            case(stay):
                y_offset = 0;
                x_offset = 0;
        }

        if (Arrays.equals(bus.getPrevCoordinate(), bus.getCurrCoordinate())){
            newY = bus.getCurrCoordinate()[1] * dim  + y_offset;
            newX = bus.getCurrCoordinate()[0] * dim + x_offset;
        }

        else{
            newY = bus.getPrevCoordinate()[1] * dim  + y_offset;
            newX = bus.getPrevCoordinate()[0] * dim + x_offset;
        }

        return new int[] {newX, newY};

    }

    private BufferedImage choosePicture(Bus bus, String direction){
        boolean isReserve;
        isReserve = (bus.getId()==2 || bus.getId()==3);

        switch (direction){
            case(eastTowest): {
                if (isReserve) {
                    return reserveBusEastToWest;
                }
                return this.regularBusEastToWest;
            }

            case (westToEast): {
                if (isReserve) {
                    return reserveBusWestToEast;
                }
                return this.regularBusWestToEast;
            }

            case (northToSouth): {
                if (isReserve) {
                    return reserveBusNorthToSouth;
                }
                return this.regularBusNorthToSouth;
            }


            case (southToNorth): {
                if (isReserve) {
                    return reserveBusSouthToNorth;
                }
                return this.regularBusSouthToNorth;
            }

            case(stay): {
                if (isReserve) {
                    return reserveBusSouthToNorth;
                }
                return this.regularBusSouthToNorth;
            }
        }
        return null;
    }


    public void drawBusses(int subIterationNumber){
        String direction;
        BufferedImage busImage;
        for (Bus bus : city.getBusses().values()){
            direction = chooseDirection(bus);
            busImage = choosePicture(bus, direction);

            if (Arrays.equals(bus.getPrevCoordinate(), bus.getCurrCoordinate())){
                g.drawImage(busImage, bus.getCurrCoordinate()[1]*dim, bus.getCurrCoordinate()[0]*dim, dim-3, dim-3, null);
            }
            else{
                int[] subCoor = getSubCoordinate(bus, direction, subIterationNumber);
                g.drawImage(busImage, subCoor[1], subCoor[0] , dim-3, dim-3, null);
            }
        }
    }
}
