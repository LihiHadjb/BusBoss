import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import CityComponents.City;
import SpecificationVars.InputsCreator;
import SpecificationVars.OutputsParser;
import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

public class Runner{
    City city;
    Frame frame;

    ControllerExecutor executor;
    Map<String,String> inputs;
    Map<String, String> sysValues;
    InputsCreator inputsCreator;
    OutputsParser outputsParser;

    public Runner (Frame frame, City city) throws IOException{
        this.city = city;
        this.frame = frame;
        this.inputs = new HashMap<>();

    	executor = new ControllerExecutor(new BasicJitController(), "out");
    	inputsCreator = new InputsCreator(inputs, city);
    	outputsParser = new OutputsParser(city);
    }


	private void parseAndUpdateCity(Map<String, String> sysValues) {
		outputsParser.parseSysValues(sysValues);
		city.updateCity();

	}

    public void run(){
    	inputsCreator.createEnvVars(true);
        executor.initState(inputs);

        sysValues = executor.getCurrOutputs();
        parseAndUpdateCity(sysValues);
        this.frame.repaint();

        while (true) {
        	inputsCreator.createEnvVars(false);
        	executor.updateState(inputs);

            sysValues = executor.getCurrOutputs();
            parseAndUpdateCity(sysValues);
            this.frame.updateBussesPanel();
            //update panels here!!

            this.frame.repaint();
        }
    }


    public static void main(String args[]) throws IOException {
        City city = new City();
        Frame frame = new Frame(city);
        Runner runner = new Runner(frame, city);
        runner.run();

    }

}
