package Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import CityComponents.City;
import SpecificationVars.AutomaticModeInputsCreator;
import SpecificationVars.ManualModeInputCreator;
import SpecificationVars.OutputsParser;
import UI.Board;
import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;


public class Runner{
    Board board;
    City city;
    ControllerExecutor executor;
    Map<String,String> inputs;
    Map<String, String> sysValues;
    AutomaticModeInputsCreator automaticModeInputsCreator;
    ManualModeInputCreator manualModeInputCreator;
    OutputsParser outputsParser;

    public Runner (Board board, City city) throws IOException{
        this.board = board;
        this.city = city;
        this.inputs = new HashMap<>();
        
    	executor = new ControllerExecutor(new BasicJitController(), "out");
    	automaticModeInputsCreator = new AutomaticModeInputsCreator(inputs, city);
    	manualModeInputCreator = new ManualModeInputCreator(inputs, city, board.getManualModeTable(), board.getStationsCheckBoxesPanel());
    	outputsParser = new OutputsParser(city);
    }

	private void parseAndupdateCity(Map<String, String> sysValues) {
		outputsParser.parseSysValues(sysValues);
		city.updateCity();
	}

	//Create a new set (map) of inputs, using a creator class that matches the current mode
    private void createInputs(boolean isInit){
        if(city.isManualMode()){
            manualModeInputCreator.createEnvVars(isInit);
        }

        else{
            automaticModeInputsCreator.createEnvVars(isInit);
        }
    }


    //Start the controller, provide inputs, get its outputs and update the state of the city and the UI accordingly
    public void run() throws Exception {
        createInputs(true);
        executor.initState(inputs);

        sysValues = executor.getCurrOutputs();
        parseAndupdateCity(sysValues);
        this.board.paint();

        while (true) {
            createInputs(false);
        	executor.updateState(inputs);
            sysValues = executor.getCurrOutputs();
            parseAndupdateCity(sysValues);
            this.board.paint();
        }
    }

    //Create the and initialize the City, GUI and Controller to start the simulation
    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
