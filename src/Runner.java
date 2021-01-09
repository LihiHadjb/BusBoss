import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import CityComponents.Bus;
import CityComponents.City;
import SpecificationVars.AutomaticModeInputsCreator;
import SpecificationVars.InputsCreator;
import SpecificationVars.ManualModeInputCreator;
import SpecificationVars.OutputsParser;
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
    
    private void createInputs(boolean isInit){
        if(city.isManualMode()){
            manualModeInputCreator.createEnvVars(isInit);
        }

        else{
            automaticModeInputsCreator.createEnvVars(isInit);
        }
    }


    public void run() throws Exception {
        createInputs(true);
        executor.initState(inputs);
        //System.out.println(inputs);

        sysValues = executor.getCurrOutputs();
        //System.out.println(sysValues.toString());
        parseAndupdateCity(sysValues);
        this.board.paint();

        while (true) {
            createInputs(false);
            //System.out.println("______" + city.isManualMode() + "________");
        	//System.out.println(inputs);
            //printValuesForBus();
        	executor.updateState(inputs);
            sysValues = executor.getCurrOutputs();
            //printSysForBus();
            //System.out.println(sysValues.toString());
            parseAndupdateCity(sysValues);
            this.board.paint();
        }
    }

    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
