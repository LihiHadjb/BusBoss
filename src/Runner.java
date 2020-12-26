import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

public class Runner  {

    Board board;
    City city;
    ControllerExecutor executor;
    Map<String,String> inputs;
    Map<String, String> sysValues;
    InputsCreator inputsCreator;
    OutputsParser outputsParser;

    public Runner (Board board, City city) throws IOException{
        this.board = board;
        this.city = city;
        this.inputs = new HashMap<>();
        
    	executor = new ControllerExecutor(new BasicJitController(), "out");
    	inputsCreator = new InputsCreator(inputs, city);
    	outputsParser = new OutputsParser(city);
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
        Thread.sleep(500);

        while (true) {
        	//inputsCreator.createEnvVars(false);
        	inputsCreator.createEnvVars(true);
        	executor.updateState(inputs);
            sysValues = executor.getCurrOutputs();
            parseAndupdateCity(sysValues);
            this.board.paint();
            Thread.sleep(1000);
        }
    }


    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
