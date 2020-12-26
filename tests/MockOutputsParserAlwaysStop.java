import CityComponents.Bus;
import CityComponents.City;
import Lines.BusMover;

public class MockOutputsParserAlwaysStop extends MockOutputsParserNoStops {
    public MockOutputsParserAlwaysStop(City city) {
        super(city);
    }

    @Override
    public void updateStopAtNextStation() {
        for(int i=0; i<city.getNumBusses(); i++) {
            Bus bus = city.getBusses().get(i);
            BusMover busMover = city.getBusMover();
//            if(busMover.isAtDestinationStation(bus)){
//                bus.setStopAtNextStation(false);
//            }
//
//            else{
//                bus.setStopAtNextStation(true);
//            }
            bus.setStopAtNextStation(true);

        }
    }
}
