import java.io.IOException;
import java.util.Map;

public class MockRunner extends Runner{
    OutputsParser outputsParser;

        public MockRunner (Board board, City city, OutputsParser outputsParser) throws IOException {
            super(board, city);
            this.outputsParser = outputsParser;
        }

        //    private void updateCity2() {
        //    	Bus bus0 = city.getBusses().get(0);
        //    	bus0.setCurrCoordinate(city.top_left());
        //    }
        //    private void updateCity1() {
        //    	Bus bus0 = city.getBusses().get(0);
        //    	int[] newLoc = new int[2];
        //
        //    	newLoc[0] = city.top_left()[0] + 1;
        //    	newLoc[1] = city.top_left()[1];
        //    	bus0.setCurrCoordinate(newLoc);
        //    }

        private void parseAndupdateCity(Map<String, String> sysValues) {
            outputsParser.parseSysValues(sysValues);
            city.updateCity();

        }




        public void run() throws Exception {
            inputsCreator.createEnvVars(true);
            executor.initState(inputs);
            sysValues = executor.getCurrOutputs();
            parseAndupdateCity(sysValues);
            this.board.paint();
            Thread.sleep(DELAY);

            while (true) {
                //System.out.println("hello");
                //inputsCreator.createEnvVars(false);
                inputsCreator.createEnvVars(true);
                executor.updateState(inputs);
                sysValues = executor.getCurrOutputs();
                parseAndupdateCity(sysValues);
                this.board.paint();
                Thread.sleep(DELAY);
            }
        }



}
