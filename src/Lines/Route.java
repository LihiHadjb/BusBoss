package Lines;

import java.util.Arrays;
import java.util.List;

public class Route {
	public List<int[]> getCoordinates() {
		return coordinates;
	}

	private List<int[]> coordinates;
	int length;
	
	public Route(List<int[]> coordinates, int length) {
		this.coordinates = coordinates;
		this.length = length;
	}
	
	public int[] getNextCoordinate(int[] currCoordinate) {
		int index = this.coordinates.indexOf(currCoordinate);
		if (index < this.length) {
			return coordinates.get(index+1);
		}
		
		return null; //in case we're at the end of the route
	}
	
	
	public boolean isAtDestination(int[] currCoordinate){
		int index = this.coordinates.indexOf(currCoordinate);
		
		if (index == this.length-1) {
			return true;
		}
		
		return false; //in case we're at the end of the route
	}

	public boolean isOnRoute(int[] coor){
		for(int[] coorOnRoute : this.coordinates){
			if(Arrays.equals(coor, coorOnRoute)){
				return true;
			}
		}
		return false;
	}

}