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

    public Runner (Board board, City city){
        this.board = board;
        this.city = city;
        this.inputs = new HashMap<>();
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
    


    public void run() throws Exception {
    	executor = new ControllerExecutor(new BasicJitController(), "out");
    	InputsCreator inputsCreator = new InputsCreator(inputs, city);
    	//OutputsParser outputsParser = new OutputsParser();
    	
    	inputsCreator.createEnvVars(true);
        executor.initState(inputs);     
        sysValues = executor.getCurrOutputs();
        //outputsParser.updateCity(sysValues);
        this.board.paint();
        Thread.sleep(1000);            

        while (true) {
        	//inputsCreator.createEnvVars(false);
        	inputsCreator.createEnvVars(true);

        	executor.updateState(inputs);
            sysValues = executor.getCurrOutputs();
            //outputsParser.updateCity(sysValues);
            this.board.paint();
            Thread.sleep(1000);
            System.out.println(sysValues);
        }
    }


    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
