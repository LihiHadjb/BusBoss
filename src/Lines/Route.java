package Lines;

import java.util.Arrays;
import java.util.List;

//An instance of this class represents the sequence of coordinates that create the route between 2 stations.
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

	//Return the coordinate that comes after the current coordinate in the Route
	public int[] getNextCoordinate(int[] currCoordinate) {
		int index=-1;
		for(int i=0; i<coordinates.size(); i++){
			if(Arrays.equals(coordinates.get(i), currCoordinate)){
				index = i;
				break;
			}
		}

		if (index < this.length) {
			return coordinates.get(index+1);
		}
		
		return null; //in case we're at the end of the route
	}

	//Return true iff the coordinate coor is part of this Route
	public boolean isOnRoute(int[] coor){
		for(int[] coorOnRoute : this.coordinates){
			if(Arrays.equals(coor, coorOnRoute)){
				return true;
			}
		}
		return false;
	}

}
