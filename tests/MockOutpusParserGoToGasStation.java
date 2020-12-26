import CityComponents.Bus;
import CityComponents.City;

public class MockOutpusParserGoToGasStation extends MockOutputsParserAlwaysStop{

    public MockOutpusParserGoToGasStation(City city) {
        super(city);
    }

    @Override
    public void updateShouldGoToGasStation() {
        for(int i=0; i<city.getNumBusses(); i++) {
            Bus bus = city.getBusses().get(i);
            bus.setShouldGoToGasStation(true);
        }
    }
}
