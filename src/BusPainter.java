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

    BufferedImage regularBus0EastToWest;
    BufferedImage regularBus0WestToEast;
    BufferedImage regularBus0NorthToSouth;
    BufferedImage regularBus0SouthToNorth;

    BufferedImage regularBus1EastToWest;
    BufferedImage regularBus1WestToEast;
    BufferedImage regularBus1NorthToSouth;
    BufferedImage regularBus1SouthToNorth;

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
            this.regularBus0EastToWest = ImageIO.read(new File("images/Regular/regular_bus_0_east_to_west.jpg"));
            this.regularBus0WestToEast = ImageIO.read(new File("images/Regular/regular_bus_0_west_to_east.jpg"));
            this.regularBus0NorthToSouth = ImageIO.read(new File("images/Regular/regular_bus_0_north_to_south.jpg"));
            this.regularBus0SouthToNorth = ImageIO.read(new File("images/Regular/regular_bus_0_south_to_north.jpg"));

            this.regularBus1EastToWest = ImageIO.read(new File("images/Regular/regular_bus_1_east_to_west.jpg"));
            this.regularBus1WestToEast = ImageIO.read(new File("images/Regular/regular_bus_1_west_to_east.jpg"));
            this.regularBus1NorthToSouth = ImageIO.read(new File("images/Regular/regular_bus_1_north_to_south.jpg"));
            this.regularBus1SouthToNorth = ImageIO.read(new File("images/Regular/regular_bus_1_south_to_north.jpg"));


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

    //Return the direction that matches a station that is located in the coordiante provided in "location"
    public String getStationDirection(int[] location){
        if (Arrays.equals(location, ((city.getBusStations().get("a1")).getLocationForTheBus()))){
            return northToSouth;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("a2")).getLocationForTheBus()))){
            return southToNorth;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("b1")).getLocationForTheBus()))){
            return westToEast;
        }

        else if (Arrays.equals(location, ((city.getBusStations().get("b2")).getLocationForTheBus()))){
            return eastTowest;
        }

        else if (Arrays.equals(location, ((city.getMainStation()).getLocationForTheBus()))){
            return eastTowest;
        }

        else if (Arrays.equals(location, ((city.getGasStation()).getLocationForTheBus()))){
            return eastTowest;
        }

        return null;
    }

    //Return the direction that matches this bus's current coordinate, according to the current and
    //previous coordinates of this bus.
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

    //Calculate the new location where the bus should next be drawn.
    // To create the illusion that the bus moves continuously, each controller step is divided to #dim "subIteration"s,
    //so that each bus can be drawn #dim times in every controller step, in a corresponding "subCoordinate" inside
    //the square that represents a coordinate
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

    //Return the image that matches this bus's coordiante and direction
    private BufferedImage choosePicture(Bus bus, String direction){
        boolean isReserve;
        isReserve = (bus.getId()==2 || bus.getId()==3);

        switch (direction){
            case(eastTowest): {
                if (isReserve) {
                    return reserveBusEastToWest;
                }
                else{
                    if (bus.getId() == 0){
                        return this.regularBus0EastToWest;}
                    if (bus.getId() == 1){
                        return this.regularBus1EastToWest;}
                }
            }

            case (westToEast): {
                if (isReserve) {
                    return reserveBusWestToEast;
                }
                else{
                    if (bus.getId() == 0){
                        return this.regularBus0WestToEast;}
                    if (bus.getId() == 1){
                        return this.regularBus1WestToEast;}
                }
            }

            case (northToSouth): {
                if (isReserve) {
                    return reserveBusNorthToSouth;
                }
                else{
                    if (bus.getId() == 0){
                        return this.regularBus0NorthToSouth;}
                    if (bus.getId() == 1){
                        return this.regularBus1NorthToSouth;}
                }
            }


            case (southToNorth): {
                if (isReserve) {
                    return reserveBusSouthToNorth;
                }
                else{
                    if (bus.getId() == 0){
                        return this.regularBus0SouthToNorth;}
                    if (bus.getId() == 1){
                        return this.regularBus1SouthToNorth;}
                }
            }

            case(stay): {
                if (isReserve) {
                    return reserveBusSouthToNorth;
                }
                else{
                    if (bus.getId() == 0){
                        return this.regularBus0SouthToNorth;}
                    if (bus.getId() == 1){
                        return this.regularBus1SouthToNorth;}
                }
            }
        }
        return null;
    }


    //Draw each bus in the calculated subCoordinate, using a picture that matches this bus's ID and direction
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
