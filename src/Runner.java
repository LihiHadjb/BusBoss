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

//    private void updateCity2() {
//    	CityComponents.Bus bus0 = city.getBusses().get(0);
//    	bus0.setCurrCoordinate(city.top_left());
//    }
//    private void updateCity1() {
//    	CityComponents.Bus bus0 = city.getBusses().get(0);
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
        System.out.println(inputs);

        sysValues = executor.getCurrOutputs();
        //System.out.println(sysValues.toString());
        parseAndupdateCity(sysValues);
        this.board.paint();

        while (true) {
            createInputs(false);
            System.out.println("______" + city.isManualMode() + "________");
        	System.out.println(inputs);
            printValuesForBus();
        	executor.updateState(inputs);
            sysValues = executor.getCurrOutputs();
            //printSysForBus();
            //System.out.println(sysValues.toString());
            parseAndupdateCity(sysValues);
            this.board.paint();
        }
    }

    private void printValuesForBus(){

        System.out.println("________________");
        System.out.println("raining" + Boolean.toString(city.isRaining()));

        for(int i=0; i<city.getNumBusses(); i++){
//            if(i==0 || i==2){

                Bus bus = city.getBusses().get(i);
                String destName = String.format("atDestinationStation[%d]", i);
                String arePassengers = String.format("arePassengersWaitingInNextStation[%d]", i);
                System.out.println(String.format("bus %d: atDest: %s, full: %b, STOP: %b, passengers: %s",i, inputs.get(destName), bus.isFull(), bus.isStopPressed(), inputs.get(arePassengers)) );
            //}
        }
    }

    private void printSysForBus(){
        System.out.println("________________");
        for(int i=0; i<city.getNumBusses(); i++){
//            if(i==0 || i==2){

                Bus bus = city.getBusses().get(i);
                String shouldStopName = String.format("stopAtNextStation[%d]", i);
                String shouldGas = String.format("shouldGoToGasStation[%d]", i);
                System.out.println(String.format("bus %d:, shouldStop: %s, GAS: %s",i, sysValues.get(shouldStopName), sysValues.get(shouldGas) ));
            //}
        }
    }

    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
