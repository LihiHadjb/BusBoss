package GUI;

import GUI.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
//import tau.smlab.syntech.controller.executor.ControllerExecutor;
//import tau.smlab.syntech.controller.jit.BasicJitController;

public class Runner  {

    Board board;
//    ControllerExecutor executor;
    Map<String,String> inputs = new HashMap<String, String>();

    public Runner (Board board){
        this.board = board;
    }




    public void run() throws Exception {
//        executor = new ControllerExecutor(new BasicJitController(), "out");
//        BufferedImage m = ImageIO.read(new File("img/monkey.jpg"));
//
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
        HashMap<String, int[]> bussesLocations = new HashMap<>();
        HashMap<String, Boolean> arePassengersWaiting = new HashMap<>();
        this.board.paint(bussesLocations, arePassengersWaiting);
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
        Board board = new Board(city.getX(), city.getY());
        Runner runner = new Runner(board);
        runner.run();

    }

}
