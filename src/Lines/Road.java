package Lines;


//This class representes a road inside the city, including its location
public class Road {
	private int[] start;
	private int[] end;
	
	public Road(int[] start, int[] end) {
		this.start = start;
		this.end = end;
	}
	
	public int[] getStart(){
		return this.start;
	}
	
	public int[] getEnd(){
		return this.end;
	}
	
}
