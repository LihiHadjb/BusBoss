import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import GUI.IBus;

public class InputsCreator {
	private Map<String, String> inputs;
	private City city;
	private int NUM_BUSSES;
	private Map<String, Boolean> envVarToInitValue;
	
	
	public InputsCreator(Map<String, String> inputs, City city) {
		this.inputs = inputs;
		this.city = city;
		this.NUM_BUSSES = city.NUM_BUSSES;
		
		this.envVarToInitValue = new HashMap<>();
		envVarToInitValue.put("isBusFull", false);
		envVarToInitValue.put("isStopPressed", false);
		envVarToInitValue.put("arePassengersWaitingInNextStation", false);
		envVarToInitValue.put("isBusFull", false);
		envVarToInitValue.put("atDestinationStation", false);
		envVarToInitValue.put("atGasStation", false);
		envVarToInitValue.put("atMainStation", true);
		
	}
	
    //TODO: maybe these should be with probability
    private void randomizeBooleanForEachBus(String envVarName, boolean isInit) {
    	Random rand = new Random();
    	boolean result;
    	for(int i=0; i<NUM_BUSSES; i++) {
    		String name = String.format("%s[%d]", envVarName, i);
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
        		result = rand.nextBoolean();
    		}

    		inputs.put(name, Boolean.toString(result));
    	}	
    }
    
    private void putAtDestinationStationForEachBus(boolean isInit) {
    	String envVarName = "atDestinationStation";
    	HashMap<Integer, IBus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		IBus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
    			//result = bus.isAtDestinationStation();
    			result = true;
    		}
    		inputs.put(name, Boolean.toString(result));

    	}	
    	
    }
    
    private void putAtGasStationForEachBus(boolean isInit) {
    	String envVarName = "atGasStation";
    	HashMap<Integer, IBus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		IBus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
    			//result = bus.isAtGasStation();
    			result = true;
    		}
    		//inputs.put(name, Boolean.toString(bus.isAtGasStation()));
    		inputs.put(name, Boolean.toString(result));
    	}	
    	
    }
    
    private void putAtMainStationForEachBus(boolean isInit) {
    	String envVarName = "atGasStation";
    	HashMap<Integer, IBus> busses = city.getBusses();
    	for(int i=0; i<NUM_BUSSES; i++) {
    		IBus bus = busses.get(i);
    		String name = String.format("%s[%d]", envVarName, i);
    		boolean result;
    		if(isInit) {
    			result = envVarToInitValue.get(envVarName);
    		}
    		else {
    			result = bus.isAtMainStation();
    			result = true;
    		
    		}
    		inputs.put(name, Boolean.toString(result));
    	}	
    	
    }
    
    private void putIsRaining() {
    	//TODO: this should come from GUI or something
    	inputs.put("isRaining", Boolean.toString(true));
    }
    
    public void createEnvVars(boolean isInit) {
    	
    	randomizeBooleanForEachBus("isBusFull", isInit);
    	randomizeBooleanForEachBus("isStopPressed", isInit);
    	randomizeBooleanForEachBus("arePassengersWaitingInNextStation", isInit);

    	
    	putAtDestinationStationForEachBus(isInit);
    	putAtGasStationForEachBus(isInit);
    	putAtMainStationForEachBus(isInit);
    	putIsRaining();   	
    }

}
