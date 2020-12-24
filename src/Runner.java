import java.util.HashMap;
import java.util.Map;
//import tau.smlab.syntech.controller.executor.ControllerExecutor;
//import tau.smlab.syntech.controller.jit.BasicJitController;

public class Runner  {

    Board board;
    City city;
    //ControllerExecutor executor;
    Map<String,String> inputs = new HashMap<String, String>();

    public Runner (Board board, City city){
        this.board = board;
        this.city = city;
    }

    private void updateCity2() {
    	Bus bus0 = city.getBusses().get(0);
    	bus0.setCurrCoordinate(city.top_left());
    }
    private void updateCity1() {
    	Bus bus0 = city.getBusses().get(0);
    	int[] newLoc = new int[2];
    	
    	newLoc[0] = city.top_left()[0] + 1;
    	newLoc[1] = city.top_left()[1];
    	bus0.setCurrCoordinate(newLoc);
    }


    public void run() throws Exception {
        //executor = new ControllerExecutor(new BasicJitController(), "out");
//        createEnvVars(); //initial states and random...
//        updateEnvVars(); //put in "inputs"
////
////        inputs.put("banana[0]", Integer.toString(banana[0]));
////        inputs.put("banana[1]", Integer.toString(banana[1]));
//        executor.initState(inputs);
//
//        Map<String, String> sysValues = executor.getCurrOutputs();
////
////        monkey[0] = Integer.parseInt(sysValues.get("monkey[0]"));
////        monkey[1] = Integer.parseInt(sysValues.get("monkey[1]"));
//        updateSysVars();
        Thread.sleep(1000);
    	updateCity1();
        this.board.paint();
        Thread.sleep(1000);
        
    	updateCity2();
        this.board.paint();
        Thread.sleep(1000);
        
        
//
//        while (true) {
//
//            if (monkey[0] == banana[0] & monkey[1] == banana[1]) {
//                banana[0] = rand.nextInt(8);
//                banana[1] = rand.nextInt(8);
//            }
//
//            inputs.put("banana[0]", Integer.toString(banana[0]));
//            inputs.put("banana[1]", Integer.toString(banana[1]));
//
//            executor.updateState(inputs);
//
//            sysValues = executor.getCurrOutputs();
//
//            monkey[0] = Integer.parseInt(sysValues.get("monkey[0]"));
//            monkey[1] = Integer.parseInt(sysValues.get("monkey[1]"));
//
//            paint(this.getGraphics());
//            Thread.sleep(1000);
//        }
    }


    public static void main(String args[]) throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();

    }

}
