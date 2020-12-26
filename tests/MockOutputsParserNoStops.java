import java.util.HashMap;
import java.util.Map;

public class MockOutputsParserNoStops extends OutputsParser{

        public MockOutputsParserNoStops(City city) {
            super(city);
        }



        private void updateLineOfReserveBus() {
            for(int i=0; i<NUM_RESERVE_BUSSES; i++) {
                Bus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
                bus.setLine(city.getLines().get(1));
            }
        }

        private void updateShouldGoToGasStation() {
            for(int i=0; i<NUM_BUSSES; i++) {
                Bus bus = city.getBusses().get(i);
                bus.setShouldGoToGasStation(false);
            }
        }

        private void updateNeedExtraBusForLine() {
            city.getLines().get(0).setNeedExtraBusForLine(false);
            city.getLines().get(1).setNeedExtraBusForLine(false);
        }



        //	private void updateExtraBusSentLine() {
        //		boolean[] values = parseToArrayOfBooleans("extraBusSentLine");
        //		city.getLines.get(0).setExtraBusSentLine(values[0]);
        //		city.getLines.get(1).setExtraBusSentLine(values[1]);
        //	}

        public void updateStopAtNextStation() {
            for(int i=0; i<NUM_BUSSES; i++) {
                Bus bus = city.getBusses().get(i);
                bus.setStopAtNextStation(false);
            }
        }

        private void updateInUse() {
            for(int i=0; i<NUM_RESERVE_BUSSES; i++) {
                Bus bus = city.getBusses().get(i + NUM_RESERVE_BUSSES);
                bus.setInUse(false);
            }
        }

        public void parseSysValues(Map<String, String> sysValues) {
            this.sysValues = sysValues;
            updateLineOfReserveBus();
            updateShouldGoToGasStation();
            updateNeedExtraBusForLine();
            updateStopAtNextStation();
            updateInUse();
            //updateExtraBusSentLine();
            //updateUnstoppedStationsLine();
            //updateNumOfStopsPassedBus();
        }



        //				lineOfReserveBus
        //				shouldGoToGasStation
        //				needExtraBusForLine[0]=false, //only need to visualize this
        //				extraBusSentLine[1]=true, //do nothing with this
        //				stopAtNextStation[0]=false, //visualize + gui should stop when arriving destinationStation
        //				inUse[1]=true, lineOfReserveBus[1]=1, // if not in main station, go there. otherwise do nothing
        //				unstoppedStationsLineA=0, // only need to visualize this
        //				numOfStopsPassedBus2=1, // only need to visualize this





}
