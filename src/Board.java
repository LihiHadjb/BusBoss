import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Thread.sleep;


@SuppressWarnings("serial")
public class Board extends JFrame {
	final int dim = 40;
	
	int x;
	int y;
	
	BufferedImage busImage;
	BufferedImage stationWithPeopleImage;
	BufferedImage gasStationImage;
	BufferedImage emptyStationImage;
	
	int[] top_left;
	int[] top_right;
	int[] bottom_left;
	int[] bottom_right;
	
	City city;

	public Board(City city){
		this.city = city;
		this.x = city.getX();
		this.y = city.getY();

		top_left = city.top_left();
		top_right = city.top_right();
		bottom_left = city.bottom_left();
		bottom_right = city.bottom_right();
		
		initImages();

		this.setTitle("BusBoss");
		this.setSize(this.y * this.dim, this.x * this.dim);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.paint(this.getGraphics());

	}

	
	private void initImages() {
		try{
			this.busImage = ImageIO.read(new File("images/bus.jpeg"));
			this.stationWithPeopleImage =  ImageIO.read(new File("images/station_with_people.jpeg"));
			this.gasStationImage =  ImageIO.read(new File("images/gas_station.jpeg"));
			this.emptyStationImage =  ImageIO.read(new File("images/empty_station.jpeg"));
		}
		catch (IOException e){
			System.out.println("error in reading images!");
			e.printStackTrace();
		}	
	}
	
	private void draw_borders(String type) {
		int row, col;
		Graphics g = this.getGraphics();
		int[] top_left = new int[2];
		int[] top_right = new int[2];
		int[] bottom_left = new int[2];
		int[] bottom_right = new int[2];
		switch (type){
			case "neighbourhood":
				g.setColor(Color.WHITE);
				break;
			case "central_station":
				g.setColor(Color.ORANGE);
				top_left = city.getCentralStation().getTop_left();
				top_right = city.getCentralStation().getTop_right();
				bottom_left = city.getCentralStation().getBottom_left();
				bottom_right = city.getCentralStation().getBottom_right();
				break;

			case "gas_station":
				g.setColor(Color.BLUE);
				top_left = city.getGasStation().getTop_left();
				top_right = city.getGasStation().getTop_right();
				bottom_left = city.getGasStation().getBottom_left();
				bottom_right = city.getGasStation().getBottom_right();
		}

		row = top_left[0];
		for(col=top_left[1]; col<top_right[1]; col++) {
			g.fillRect(col * dim, row * dim, dim, dim);
		}

		for(row=top_right[0]; row<bottom_right[0]; row++) {
			g.fillRect(col * dim, row * dim, dim, dim);

		}

		for(col=bottom_right[1]; col>bottom_left[1]; col--) {
			g.fillRect(col * dim, row * dim, dim, dim);

		}

		for(row=bottom_left[0]; row>top_left[0]; row--) {
			g.fillRect(col * dim, row * dim, dim, dim);
		}
	}

	private void draw_vertical_road(int[] start,int [] end) {
		Graphics g = this.getGraphics();
		int row, col;

		col = start[1];
		g.setColor(Color.GRAY);

		// Draw the road
		for(row=start[0]; row<=end[0]; row++) {
			g.fillRect(col * dim, row * dim, dim, dim);
			g.fillRect((col+1) * dim, row * dim, dim, dim);
		}

		// Draw while lined between lanes
		g.setColor(Color.WHITE);
		for(row=start[0]; row<=end[0]; row++) {
			g.fillRect((col * dim)+dim-1, (row * dim)+(dim/4), 1, dim/2);
		}
	}

	private void draw_horizontal_road(int[] start,int [] end) {
		int row, col;
		Graphics g = this.getGraphics();

		row = start[0];
		g.setColor(Color.GRAY);

		// Draw the road
		for(col=start[1]; col<=end[1]; col++) {
			g.fillRect(col * dim, row * dim, dim, dim);
			g.fillRect(col * dim, (row+1) * dim, dim, dim);
		}

		// Draw while lined between lanes
		g.setColor(Color.WHITE);
		for(col=start[1]; col<=end[1]; col++) {
			g.fillRect((col * dim)+(dim/4), (row * dim)+dim-1, dim/2, 1);
		}
	}

	private void draw_A_stations() {
		Station station1 = city.getBusStations().get("a1");
		Station station2 = city.getBusStations().get("a2");
		Graphics g = this.getGraphics();
		//g.setColor(Color.PINK);
		g.drawImage(emptyStationImage, station1.getLocation()[1] * dim - dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		g.drawImage(stationWithPeopleImage, station2.getLocation()[1] * dim, station2.getLocation()[0] * dim, dim*2, dim*2, null);

	}

	private void draw_B_stations() {
		Station station1 = city.getBusStations().get("b1");
		Station station2 = city.getBusStations().get("b2");
		Graphics g = this.getGraphics();
		//g.setColor(Color.MAGENTA);
		g.drawImage(emptyStationImage, station1.getLocation()[1] * dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		g.drawImage(stationWithPeopleImage, station2.getLocation()[1] * dim - dim, station2.getLocation()[0] * dim - dim, dim*2, dim*2, null);


	}

	//TODO: make the roads a field of the city??
	private void draw_roads(){
		int[] start = new int[2];
		int[] end = new int[2];

		//left vertical road
		start[0] = top_left[0]+1;
		start[1] = top_left[1]+1;
		end[0] = bottom_left[0]-1;
		end[1] = bottom_left[1]+1;
		draw_vertical_road(start, end);

		// top horizontal road
		end[0] = top_right[0]+1;
		end[1] = top_right[1]-1;
		draw_horizontal_road(start, end);

		// right vertical road
		start[0] = end[0];
		start[1] = end[1]-1;
		end[0] = bottom_right[0]-1;
		end[1] = bottom_right[1]-1-1;
		draw_vertical_road(start, end);

		// bottom horizontal road
		start[0] = bottom_left[0]-1-1;
		start[1] = bottom_left[1]+1;
		end[0] = bottom_right[0]-1 -1;
		draw_horizontal_road(start, end);
	}

	private void draw_neighbourhood() {
//		draw_borders(top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		draw_roads();
		draw_A_stations();
		draw_B_stations();

	}

	public void drawBusses(){
		Graphics g = this.getGraphics();
		for (IBus bus : city.getBusses().values()){
			g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
		}
	}

	public void drawPassengerInStations(){
		Graphics g = this.getGraphics();
		for (Station station : city.getBusStations().values()){
			if(station.arePassengersWaiting){
				g.drawImage(stationWithPeopleImage, station.getLocation()[0] * dim * 2, station.getLocation()[1] * dim * 2, null);
			}
		}
	}
	
	public void drawRoadsOutsideTheNeighborhoood(){
		// draw the road between neighborhood and main station
		draw_horizontal_road(city.getRoadBetweenCityAndMainStation().getStart(), city.getRoadBetweenCityAndMainStation().getEnd());
		
		// draw the road between neighborhood and gas station
		draw_horizontal_road(city.getRoadBetweenCityAndGasStation().getStart(), city.getRoadBetweenCityAndGasStation().getEnd());

	}

	private void draw_background(){
		Graphics g = this.getGraphics();
		int row;
		int col;
		g.setColor(Color.WHITE);
		for (row = 0; row < x; row++) {
			for (col = 0; col < y; col++) {
				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}
	}
	
	public void checkStationsLocations(){
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		int row;
		int col;
		for (int[] loc : this.city.getStationsLocationsForTheBus().values()) {
			col = loc[1];
			row = loc[0];
			g.fillRect(col * dim, row * dim, dim, dim);
		}
	}
	
	public void checkRoutes(){
		Graphics g = this.getGraphics();
		IBus bus = city.getBusses().get(1);
		bus.setCurrCoordinate((this.city.getStationsLocationsForTheBus()).get(new int[]{2,3}));
		g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
		bus.setCurrCoordinate((this.city.getStationsLocationsForTheBus()).get(new int[]{2,4}));	
		g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
		bus.setCurrCoordinate((this.city.getStationsLocationsForTheBus()).get(new int[]{2,5}));	
		g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
		bus.setCurrCoordinate((this.city.getStationsLocationsForTheBus()).get(new int[]{2,6}));	
		g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
		
		
//		for (String origin : this.city.getOriginRoutes().keySet()){
//			
//			bus.setCurrCoordinate((this.city.getStationsLocationsForTheBus()).get(origin));				
//			
//			for (String destination : this.city.getOriginRoutes().get(origin).keySet()){
//				g.drawImage(busImage, bus.getCurrCoordinate()[0] * dim, bus.getCurrCoordinate()[1] * dim, dim, dim, null);
//				Route curr_route_between_stations = this.city.getOriginRoutes().get(origin).get(destination);
//				int[] next_coordinates = curr_route_between_stations.getNextCoordinate(bus.getCurrCoordinate());
//				bus.setCurrCoordinate(next_coordinates);	
//			}
//		}
	}
		
	public void paint() {
		draw_background();
		draw_neighbourhood();
		draw_borders("central_station");
		draw_borders("gas_station");
		drawBusses();
		drawPassengerInStations();
		drawRoadsOutsideTheNeighborhoood();
		checkStationsLocations(); //TODO: Remove after!!!
		checkRoutes(); //TODO: Remove after!!!
	}

}