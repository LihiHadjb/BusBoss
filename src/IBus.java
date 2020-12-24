

public interface IBus {
	public boolean isAtMainStation();
	public boolean isAtGasStation();
	public boolean isAtDestinationStation();
	public int[] getCurrCoordinate();
	public void updateNextDesitinationAndOriginStations();

}
