import CityComponents.Bus;
import CityComponents.City;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class BusPainter {
    Graphics g;
    City city;
    int dim;
    BufferedImage busImage;
    int DELAY;
    int[][] tempLocs;

    final String eastTowest = "eastToWest";
    final String westToEast = "westToEast";
    final String northToSouth = "northToSouth";
    final String southToNorth = "southToNorth";
    final String stay = "stay";

    public BusPainter(Graphics g, City city, int dim, BufferedImage busImage, int DELAY){
        this.g = g;
        this.city = city;
        this.dim = dim;
        this.busImage = busImage;
        this.DELAY = DELAY;
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

        //TODO: return picture according to current coordiante
        switch (direction){
            case(eastTowest):
                return busImage;

            case (westToEast):
                return busImage;


            case (northToSouth):
                return busImage;


            case (southToNorth):
                System.out.println(southToNorth);
                return busImage;


            case(stay):
                return busImage;


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

        }
    }
}
