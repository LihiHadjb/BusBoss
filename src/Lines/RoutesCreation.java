package Lines;

import CityComponents.GasStation;
import CityComponents.MainStation;
import CityComponents.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//This class provides methods to build and create the Route objects between pairs of stations and between
// the parking locations and the main station, which are used to navigate the bus on the UI.Board
public class RoutesCreation {

    //This map is used to find a Route between a pair of stations.
    //The String in the "first level" of this map is the name of the desired origin station, and using it
    //we can retrieve a "second level" map, in which the key is the name of the desired destination station, and
    //the value is the Route between this origin station and destination station.
    private HashMap<String, HashMap<String, Route>> originRoutes;

    //Maps the id's of busses to the Route between this bus's parking spot to the main station
    private HashMap<Integer, Route> routesFromParkingsToMainStation;

    //Maps the id's of busses to the Route between the main staion entrance (when coming back
    // from a round) to this bus's parking spot
    private HashMap<Integer, Route> routesFromMainStationEntranceToResrvesParkings;

    GasStation gasStation;
    MainStation mainStation;
    HashMap<String, Station> busStations;
    HashMap<String, Station> name2station;
    HashMap<Integer, int[]> parkingsLocations;

    public RoutesCreation(GasStation gasStation, MainStation mainStation, HashMap<String, Station> busStations, HashMap<String, Station> name2station, HashMap<Integer, int[]> parkingsLocations){
        this.gasStation = gasStation;
        this.mainStation = mainStation;
        this.busStations = busStations;
        this.name2station = name2station;
        this.parkingsLocations = parkingsLocations;

        createOriginRoutes();
        createRoutesFromParkingsToMainStation();
        createRoutesFromMainStationEntranceToResrvesParkings();
    }

    public FullRoute createFullRoute(LineName lineName) {
        switch (lineName.toString()) {
            case ("A"):
                // Lines.Line A route
                List<String> line_A_route = Arrays.asList("main_station", "a1", "a2");
                FullRoute Line_A = new FullRoute("line_A", line_A_route, this.originRoutes, 3, name2station);
                return Line_A;
            case ("B"):
                // Lines.Line B route
                List<String> Line_B_route = Arrays.asList("main_station", "b2", "b1");
                FullRoute Line_B = new FullRoute("line_B", Line_B_route, this.originRoutes, 3, name2station);
                return Line_B;
            case ("main_station_to_gas_station"):
                // Main station to gas station route
                List<String> main_station_to_gas_station_route = Arrays.asList("main_station", "a1", "gas_station");
                FullRoute main_station_to_gas_station = new FullRoute("main_station_to_gas_station", main_station_to_gas_station_route, this.originRoutes, 3, name2station);
                return main_station_to_gas_station;
        }
        return null;
    }

    public void createOriginRoutes(){
        originRoutes = new HashMap<>();

        // main_station -> a1
        List<int[]> main_station_to_a1 = new ArrayList();

        main_station_to_a1.add(mainStation.getLocationForTheBus());
        int[] main_station_to_a1_last = new int[]{mainStation.getLocationForTheBus()[0], mainStation.getLocationForTheBus()[1]};
        for (int i=1; i<=13; i++) {
            int x = main_station_to_a1_last[0];
            int y = main_station_to_a1_last[1] - 1;
            main_station_to_a1.add(new int[]{x,y});
            main_station_to_a1_last[0] = x;
            main_station_to_a1_last[1] = y;
        }
        for (int i=1; i<=5; i++) {
            int x = main_station_to_a1_last[0] + 1;
            int y = main_station_to_a1_last[1];
            main_station_to_a1.add(new int[]{x,y});
            main_station_to_a1_last[0] = x;
            main_station_to_a1_last[1] = y;
        }

        // main_station -> b2
        List<int[]> main_station_to_b2 = new ArrayList();
        main_station_to_b2.add(mainStation.getLocationForTheBus());
        int[] main_station_to_b2_last = new int[]{mainStation.getLocationForTheBus()[0], mainStation.getLocationForTheBus()[1]};
        for (int i=1; i<=6; i++) {
            int x = main_station_to_b2_last[0];
            int y = main_station_to_b2_last[1] - 1;
            main_station_to_b2.add(new int[]{x,y});
            main_station_to_b2_last[0] = x;
            main_station_to_b2_last[1] = y;
        }
        for (int i=1; i<=9; i++) {
            int x = main_station_to_b2_last[0] + 1;
            int y = main_station_to_b2_last[1];
            main_station_to_b2.add(new int[]{x,y});
            main_station_to_b2_last[0] = x;
            main_station_to_b2_last[1] = y;
        }
        for (int i=1; i<=3; i++) {
            int x = main_station_to_b2_last[0];
            int y = main_station_to_b2_last[1] - 1;
            main_station_to_b2.add(new int[]{x,y});
            main_station_to_b2_last[0] = x;
            main_station_to_b2_last[1] = y;
        }
        main_station_to_a1.remove(0);
        main_station_to_b2.remove(0);
        Route main_station_to_a1_route = new Route(main_station_to_a1, 13+5);
        Route main_station_to_b2_route = new Route(main_station_to_b2, 6+9+3);
        HashMap<String, Route> routes_from_main_stations = new HashMap<>();
        routes_from_main_stations.put("a1", main_station_to_a1_route);
        routes_from_main_stations.put("b2", main_station_to_b2_route);
        originRoutes.put("main_station", routes_from_main_stations);


        // a1 -> a2
        List<int[]> a1_to_a2 = new ArrayList();

        a1_to_a2.add(busStations.get("a1").getLocationForTheBus());
        int[] a1_to_a2_last = new int[]{busStations.get("a1").getLocationForTheBus()[0], busStations.get("a1").getLocationForTheBus()[1]};
        for (int i=1; i<=5; i++) {
            int x = a1_to_a2_last[0] + 1;
            int y = a1_to_a2_last[1];
            a1_to_a2.add(new int[]{x,y});
            a1_to_a2_last[0] = x;
            a1_to_a2_last[1] = y;
        }
        for (int i=1; i<=8; i++) {
            int x = a1_to_a2_last[0];
            int y = a1_to_a2_last[1] + 1;
            a1_to_a2.add(new int[]{x,y});
            a1_to_a2_last[0] = x;
            a1_to_a2_last[1] = y;
        }
        for (int i=1; i<=5; i++) {
            int x = a1_to_a2_last[0] - 1;
            int y = a1_to_a2_last[1];
            a1_to_a2.add(new int[]{x,y});
            a1_to_a2_last[0] = x;
            a1_to_a2_last[1] = y;
        }
        a1_to_a2.remove(0);
        Route a1_to_a2_route = new Route(a1_to_a2, 5+8+5);


        //a1 -> gas station
        List<int[]> a1_to_gas_station = new ArrayList();

        a1_to_gas_station.add(busStations.get("a1").getLocationForTheBus());
        int[] a1_to_gas_station_last = new int[]{busStations.get("a1").getLocationForTheBus()[0], busStations.get("a1").getLocationForTheBus()[1]};
        for (int i=1; i<=5; i++) {
            int x = a1_to_gas_station_last[0] + 1;
            int y = a1_to_gas_station_last[1];
            a1_to_gas_station.add(new int[]{x,y});
            a1_to_gas_station_last[0] = x;
            a1_to_gas_station_last[1] = y;
        }
        for (int i=1; i<=13; i++) {
            int x = a1_to_gas_station_last[0];
            int y = a1_to_gas_station_last[1] + 1;
            a1_to_gas_station.add(new int[]{x,y});
            a1_to_gas_station_last[0] = x;
            a1_to_gas_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = a1_to_gas_station_last[0] - 1;
            int y = a1_to_gas_station_last[1];
            a1_to_gas_station.add(new int[]{x,y});
            a1_to_gas_station_last[0] = x;
            a1_to_gas_station_last[1] = y;
        }
        a1_to_gas_station.remove(0);
        Route a1_to_gas_station_route = new Route(a1_to_gas_station, 5+13+1);


        HashMap<String, Route> routes_from_a1 = new HashMap<>();
        routes_from_a1.put("a2", a1_to_a2_route);
        routes_from_a1.put("gas_station", a1_to_gas_station_route);
        originRoutes.put("a1", routes_from_a1);


        // a2 -> main_station
        List<int[]> a2_to_main_station = new ArrayList();
        a2_to_main_station.add(busStations.get("a2").getLocationForTheBus());
        int[] a2_to_main_station_last = new int[]{busStations.get("a2").getLocationForTheBus()[0], busStations.get("a2").getLocationForTheBus()[1]};
        for (int i=1; i<=4; i++) {
            int x = a2_to_main_station_last[0] - 1;
            int y = a2_to_main_station_last[1];
            a2_to_main_station.add(new int[]{x,y});
            a2_to_main_station_last[0] = x;
            a2_to_main_station_last[1] = y;
        }
        for (int i=1; i<=6; i++) {
            int x = a2_to_main_station_last[0];
            int y = a2_to_main_station_last[1] + 1;
            a2_to_main_station.add(new int[]{x,y});
            a2_to_main_station_last[0] = x;
            a2_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = a2_to_main_station_last[0] - 1;
            int y = a2_to_main_station_last[1];
            a2_to_main_station.add(new int[]{x,y});
            a2_to_main_station_last[0] = x;
            a2_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = a2_to_main_station_last[0];
            int y = a2_to_main_station_last[1] - 1;
            a2_to_main_station.add(new int[]{x,y});
            a2_to_main_station_last[0] = x;
            a2_to_main_station_last[1] = y;
        }
        a2_to_main_station.remove(0);
        Route a2_to_main_station_route = new Route(a2_to_main_station, 4+6+1+1);
        HashMap<String, Route> routes_from_a2 = new HashMap<>();
        routes_from_a2.put("main_station", a2_to_main_station_route);
        originRoutes.put("a2", routes_from_a2);


        // b1 -> main_station
        List<int[]> b1_to_main_station = new ArrayList();
        b1_to_main_station.add(busStations.get("b1").getLocationForTheBus());
        int[] b1_to_main_station_last = new int[]{busStations.get("b1").getLocationForTheBus()[0], busStations.get("b1").getLocationForTheBus()[1]};
        for (int i=1; i<=10; i++) {
            int x = b1_to_main_station_last[0];
            int y = b1_to_main_station_last[1] + 1;
            b1_to_main_station.add(new int[]{x,y});
            b1_to_main_station_last[0] = x;
            b1_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = b1_to_main_station_last[0] - 1;
            int y = b1_to_main_station_last[1];
            b1_to_main_station.add(new int[]{x,y});
            b1_to_main_station_last[0] = x;
            b1_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = b1_to_main_station_last[0];
            int y = b1_to_main_station_last[1] - 1;
            b1_to_main_station.add(new int[]{x,y});
            b1_to_main_station_last[0] = x;
            b1_to_main_station_last[1] = y;
        }
        b1_to_main_station.remove(0);
        Route b1_to_main_station_route = new Route(b1_to_main_station, 10+1+1);
        HashMap<String, Route> routes_from_b1 = new HashMap<>();
        routes_from_b1.put("main_station", b1_to_main_station_route);
        originRoutes.put("b1", routes_from_b1);

        // b2 -> b1
        List<int[]> b2_to_b1 = new ArrayList();
        b2_to_b1.add(busStations.get("b2").getLocationForTheBus());
        int[] b2_to_b1_last = new int[]{busStations.get("b2").getLocationForTheBus()[0], busStations.get("b2").getLocationForTheBus()[1]};
        for (int i=1; i<=3; i++) {
            int x = b2_to_b1_last[0];
            int y = b2_to_b1_last[1] - 1;
            b2_to_b1.add(new int[]{x,y});
            b2_to_b1_last[0] = x;
            b2_to_b1_last[1] = y;
        }
        for (int i=1; i<=8; i++) {
            int x = b2_to_b1_last[0] - 1;
            int y = b2_to_b1_last[1];
            b2_to_b1.add(new int[]{x,y});
            b2_to_b1_last[0] = x;
            b2_to_b1_last[1] = y;
        }
        for (int i=1; i<=3; i++) {
            int x = b2_to_b1_last[0];
            int y = b2_to_b1_last[1] + 1;
            b2_to_b1.add(new int[]{x,y});
            b2_to_b1_last[0] = x;
            b2_to_b1_last[1] = y;
        }
        b2_to_b1.remove(0);
        Route b2_to_b1_route = new Route(b2_to_b1, 3+8+3);
        HashMap<String, Route> routes_from_b2 = new HashMap<>();
        routes_from_b2.put("b1", b2_to_b1_route);
        originRoutes.put("b2", routes_from_b2);

        // gas_station -> main_station
        List<int[]> gas_station_to_main_station = new ArrayList();
        gas_station_to_main_station.add(gasStation.getLocationForTheBus());
        int[] gas_station_to_main_station_last = new int[]{gasStation.getLocationForTheBus()[0], gasStation.getLocationForTheBus()[1]};
        for (int i=1; i<=5; i++) {
            int x = gas_station_to_main_station_last[0];
            int y = gas_station_to_main_station_last[1] - 1;
            gas_station_to_main_station.add(new int[]{x,y});
            gas_station_to_main_station_last[0] = x;
            gas_station_to_main_station_last[1] = y;
        }
        for (int i=1; i<=8; i++) {
            int x = gas_station_to_main_station_last[0] - 1;
            int y = gas_station_to_main_station_last[1];
            gas_station_to_main_station.add(new int[]{x,y});
            gas_station_to_main_station_last[0] = x;
            gas_station_to_main_station_last[1] = y;
        }
        for (int i=1; i<=6; i++) {
            int x = gas_station_to_main_station_last[0];
            int y = gas_station_to_main_station_last[1] + 1;
            gas_station_to_main_station.add(new int[]{x,y});
            gas_station_to_main_station_last[0] = x;
            gas_station_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = gas_station_to_main_station_last[0] - 1;
            int y = gas_station_to_main_station_last[1];
            gas_station_to_main_station.add(new int[]{x,y});
            gas_station_to_main_station_last[0] = x;
            gas_station_to_main_station_last[1] = y;
        }
        for (int i=1; i<=1; i++) {
            int x = gas_station_to_main_station_last[0];
            int y = gas_station_to_main_station_last[1] - 1;
            gas_station_to_main_station.add(new int[]{x,y});
            gas_station_to_main_station_last[0] = x;
            gas_station_to_main_station_last[1] = y;
        }

        Route gas_station_to_main_station_route = new Route(gas_station_to_main_station, 5+8+6+1+1);
        HashMap<String, Route> routes_from_gas_station = new HashMap<>();
        routes_from_gas_station.put("main_station", gas_station_to_main_station_route);
        originRoutes.put("gas_station", routes_from_gas_station);
    }

    public void createRoutesFromParkingsToMainStation(){
        routesFromParkingsToMainStation = new HashMap<>();
        //0
        List<int[]> parking0_to_main_route = new ArrayList<>();
        parking0_to_main_route.add(new int[] {mainStation.getLocationForTheBus()[0], mainStation.getLocationForTheBus()[1]+1});
        parking0_to_main_route.add(mainStation.getLocationForTheBus());
        Route parking0_to_main = new Route(parking0_to_main_route, 2);
        routesFromParkingsToMainStation.put(0, parking0_to_main);

        //1
        List<int[]> parking1_to_main_route = new ArrayList<>();
        parking1_to_main_route.add(parkingsLocations.get(0));
        parking1_to_main_route.addAll(parking0_to_main_route);
        Route parking1_to_main = new Route(parking1_to_main_route, 3);
        routesFromParkingsToMainStation.put(1, parking1_to_main);

        //2
        List<int[]> parking2_to_main_route = new ArrayList<>();
        parking2_to_main_route.add(parkingsLocations.get(1));
        parking2_to_main_route.addAll(parking1_to_main_route);
        Route parking2_to_main = new Route(parking2_to_main_route, 4);
        routesFromParkingsToMainStation.put(2, parking2_to_main);

        //3
        List<int[]> parking3_to_main_route = new ArrayList<>();
        int[] parkingLoc3 = parkingsLocations.get(3);
        int[] first = new int[]{parkingLoc3[0], parkingLoc3[1]-1};
        int[] second = new int[]{first[0]-1, first[1]};
        int[] third = new int[]{second[0]-1, second[1]};
        int[] forth = new int[]{third[0]-1, third[1]};

        parking3_to_main_route.add(first);
        parking3_to_main_route.add(second);
        parking3_to_main_route.add(third);
        parking3_to_main_route.add(forth);
        parking3_to_main_route.add(mainStation.getLocationForTheBus());

        Route parking3_to_main = new Route(parking3_to_main_route, 5);
        routesFromParkingsToMainStation.put(3, parking3_to_main);

    }

    public HashMap<String, HashMap<String, Route>> getOriginRoutes() {
        return originRoutes;
    }

    public HashMap<Integer, Route> getRoutesFromParkingsToMainStation() {
        return routesFromParkingsToMainStation;
    }

    public HashMap<Integer, Route> getRoutesFromMainStationEntranceToResrvesParkings() {
        return routesFromMainStationEntranceToResrvesParkings;
    }

    public void createRoutesFromMainStationEntranceToResrvesParkings(){
        routesFromMainStationEntranceToResrvesParkings = new HashMap<>();
        int[] entrance = new int[]{mainStation.getLocationForTheBus()[0]+1, mainStation.getLocationForTheBus()[1]-1};


        //bus 2
        List <int[]> entrance_to_parking2 = new ArrayList<>();
        int[] second2 = new int[]{entrance[0], entrance[1]+1};
        int[] third2 = new int[]{second2[0], second2[1]+1};
        int[] forth2 = new int[]{third2[0], third2[1]+1};
        int[] fifth2 = new int[]{forth2[0] + 1 , forth2[1]};

        entrance_to_parking2.add(second2);
        entrance_to_parking2.add(third2);
        entrance_to_parking2.add(forth2);
        entrance_to_parking2.add(fifth2);
        Route entrance_to_parking2_route = new Route(entrance_to_parking2, 4);
        routesFromMainStationEntranceToResrvesParkings.put(2, entrance_to_parking2_route);


        //bus 3
        List <int[]> entrance_to_parking3 = new ArrayList<>();
        int[] second3 = new int[]{entrance[0], entrance[1]+1};
        int[] third3 = new int[]{second3[0], second3[1]+1};
        int[] forth3 = new int[]{third3[0] + 1, third3[1]};
        int[] fifth3 = new int[]{forth3[0] + 1 , forth3[1]};
        int[] sixth3 = new int[]{fifth3[0], fifth3[1]+1};

        entrance_to_parking3.add(second3);
        entrance_to_parking3.add(third3);
        entrance_to_parking3.add(forth3);
        entrance_to_parking3.add(fifth3);
        entrance_to_parking3.add(sixth3);
        Route entrance_to_parking3_route = new Route(entrance_to_parking3, 5);
        routesFromMainStationEntranceToResrvesParkings.put(3, entrance_to_parking3_route);
    }
}
