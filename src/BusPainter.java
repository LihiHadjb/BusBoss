import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class BusPainter {
    Graphics g;
    City city;
    int dim;
//    BufferedImage busImage;
    BufferedImage busImageEastToWest;
    BufferedImage busImageWestToEast;
    BufferedImage busImageNorthToSouth;
    BufferedImage busImageSouthToNorth;
    int DELAY;
    int[][] tempLocs;
    private HashMap<int[], String> busStationsDirections;

    //Remove after debugging!!!! (Tslil)
    BufferedImage reserveImage = null;

    final String eastTowest = "eastToWest";
    final String westToEast = "westToEast";
    final String northToSouth = "northToSouth";
    final String southToNorth = "southToNorth";
    final String stay = "stay";

    public BusPainter(Graphics g, City city, int dim, BufferedImage busImageEastToWest, BufferedImage busImageWestToEast, BufferedImage busImageNorthToSouth, BufferedImage busImageSouthToNorth, int DELAY){
        this.g = g;
        this.city = city;
        this.dim = dim;
        this.busImageEastToWest = busImageEastToWest;
        this.busImageWestToEast = busImageWestToEast;
        this.busImageNorthToSouth = busImageNorthToSouth;
        this.busImageSouthToNorth = busImageSouthToNorth;
        this.DELAY = DELAY;
        this.busStationsDirections = initBusStationsDirections();


        //Remove after debugging!!!! (Tslil)
        try{
            reserveImage = ImageIO.read(new File("images/bus.jpeg"));
        }
        catch (IOException e){

        }
    }

    public HashMap<int[], String> initBusStationsDirections(){
        HashMap<int[], String> result = new HashMap<>();

        result.put((city.getBusStations().get("a1")).getLocation(), northToSouth);
        result.put((city.getBusStations().get("a2")).getLocation(), southToNorth);
        result.put((city.getBusStations().get("b1")).getLocation(), eastTowest);
        result.put((city.getBusStations().get("b2")).getLocation(), westToEast);
        result.put((city.getMainStation()).getLocation(), eastTowest);
        result.put((city.getGasStation()).getLocation(), eastTowest);

        return result;
    }

    private String chooseDirection(Bus bus){
        int[] prev = bus.getPrevCoordinate();
        int[] curr = bus.getCurrCoordinate();

        if(prev[0] > curr[0]){
            return southToNorth;
        }

        if(prev[0] < curr[0]){
            return northToSouth;
        }

        if(prev[1] > curr[1]){
            return eastTowest;
        }
        if(prev[1] < curr[1]){
            return westToEast;
        }

        if (busStationsDirections.containsKey(curr)){
            return busStationsDirections.get(curr);
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

        newY = bus.getPrevCoordinate()[1] * dim  + y_offset;
        newX = bus.getPrevCoordinate()[0] * dim + x_offset;
        return new int[] {newX, newY};

    }

    private BufferedImage choosePicture(Bus bus, String direction){
        //TODO: choose also according to background in current coordinate
        if(bus.getId() == 2 | bus.getId() == 3){
            return reserveImage;
        }

        //TODO: return picture according to current coordiante
        switch (direction){
            case(eastTowest):
                return this.busImageEastToWest;

            case (westToEast):
                return this.busImageWestToEast;


            case (northToSouth):
                return this.busImageNorthToSouth;


            case (southToNorth):
                return this.busImageSouthToNorth;


            case(stay):
                return this.busImageSouthToNorth;

        }

        return null;
    }


    public void drawBusses(int subIterationNumber){
        String direction;
        BufferedImage busImage;
        for (Bus bus : city.getBusses().values()){
            direction = chooseDirection(bus);
            busImage = choosePicture(bus, direction);
            int[] subCoor = getSubCoordinate(bus, direction, subIterationNumber);
            g.drawImage(busImage, subCoor[1], subCoor[0] , dim-3, dim-3, null);
//            g.drawImage(busImage, bus.getCurrCoordinate()[1], bus.getCurrCoordinate()[0] , dim-3, dim-3, null);
        }
    }
}
