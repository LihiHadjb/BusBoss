import CityComponents.City;
import CityComponents.Station;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;


@SuppressWarnings("serial")
public class Board extends JPanel{

	int dim;
	int x;
	int y;

	final int DELAY = 400;
	BufferedImage busImage;
	BufferedImage stationWithPeopleImage;
	BufferedImage gasStationImage;
	BufferedImage emptyStationImage;
	BufferedImage busImageNoBG;

	int width;
	int height;

	boolean initialized;

	int[] top_left;
	int[] top_right;
	int[] bottom_left;
	int[] bottom_right;
	
	City city;
	
	JPanel buttonsPanel;
	JPanel bussesPanel;

//	int width;
//	int height;

	public Board(City city, int dim){
		this.city = city;
		this.x = city.getX();
		this.y = city.getY();
		this.dim = dim;
//		this.width = this.y * dim;
//		this.height = this.x * dim;

		top_left = city.top_left();
		top_right = city.top_right();
		bottom_left = city.bottom_left();
		bottom_right = city.bottom_right();

		this.width = this.y * dim;
		this.height = this.x * dim;
		initialized = true;
		initImages();

	}


	private void initImages() {
		try{
			this.busImage = ImageIO.read(new File("images/bus_with_background.jpg"));
			this.busImageNoBG = ImageIO.read(new File("images/bus.jpeg"));
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
			case "main_station":
				g.setColor(Color.ORANGE);
				top_left = city.getMainStation().getTop_left();
				top_right = city.getMainStation().getTop_right();
				bottom_left = city.getMainStation().getBottom_left();
				bottom_right = city.getMainStation().getBottom_right();
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
		if(station1.isArePassengersWaiting()){
			g.drawImage(stationWithPeopleImage, station1.getLocation()[1] * dim - dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}
		else{
			g.drawImage(emptyStationImage, station1.getLocation()[1] * dim - dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}

		if(station2.isArePassengersWaiting()){
			g.drawImage(stationWithPeopleImage, station2.getLocation()[1] * dim, station2.getLocation()[0] * dim, dim*2, dim*2, null);

		}
		else{
			g.drawImage(emptyStationImage, station2.getLocation()[1] * dim, station2.getLocation()[0] * dim, dim*2, dim*2, null);
		}

	}

	private void draw_B_stations() {
		Station station1 = city.getBusStations().get("b1");
		Station station2 = city.getBusStations().get("b2");
		Graphics g = this.getGraphics();
		//g.setColor(Color.MAGENTA);
		if(station1.isArePassengersWaiting()){
			g.drawImage(stationWithPeopleImage, station1.getLocation()[1] * dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}
		else{
			g.drawImage(emptyStationImage, station1.getLocation()[1] * dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}

		if(station2.isArePassengersWaiting()){
			g.drawImage(stationWithPeopleImage, station2.getLocation()[1] * dim - dim, station2.getLocation()[0] * dim - dim, dim*2, dim*2, null);

		}
		else{
			g.drawImage(emptyStationImage, station2.getLocation()[1] * dim - dim, station2.getLocation()[0] * dim - dim, dim*2, dim*2, null);
		}



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

		drawRoadsOutsideTheNeighborhoood();

	}

	private void draw_neighbourhood() {
//		draw_borders(top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		draw_roads();
		draw_A_stations();
		draw_B_stations();


	}

//	public void drawBusses(){
//		Graphics g = this.getGraphics();
//		for (Bus bus : city.getBusses().values()){
//			g.drawImage(busImage, bus.getCurrCoordinate()[1] * dim + 1, bus.getCurrCoordinate()[0] * dim +2, dim-4, dim-4, null);
//
//		}
//	}

	public void drawPassengerInStations(){
		Graphics g = this.getGraphics();
		for (Station station : city.getBusStations().values()){
			if(station.isArePassengersWaiting()){
				g.drawImage(stationWithPeopleImage, station.getLocation()[0] * dim * 2, station.getLocation()[1] * dim * 2, null);
			}

			else{
				g.drawImage(emptyStationImage, station.getLocation()[0] * dim * 2, station.getLocation()[1] * dim * 2, null);
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
		for (Station station : this.city.getBusStations().values()) {
			col = station.getLocationForTheBus()[1];
			row = station.getLocationForTheBus()[0];
			g.fillRect(col * dim, row * dim, dim, dim);
		}

		col = city.getGasStation().getLocationForTheBus()[1];
		row = city.getGasStation().getLocationForTheBus()[0];
		g.fillRect(col * dim, row * dim, dim, dim);

		col = city.getMainStation().getLocationForTheBus()[1];
		row = city.getMainStation().getLocationForTheBus()[0];
		g.fillRect(col * dim, row * dim, dim, dim);
	}
	
@Override
	public void paintComponent(Graphics g) {
		System.out.println("__________in paintComponent!!");

		//draw_background(); //Tslil - no need for that
		BusPainter busPainter = new BusPainter(this.getGraphics(), city, dim, busImage, DELAY);

		draw_neighbourhood();
		draw_borders("main_station");
		draw_borders("gas_station");
		checkStationsLocations(); //TODO: Remove after!!!

		for(int i=0; i<dim; i++){
			busPainter.drawBusses(i);
			try {
				Thread.sleep(DELAY/dim);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}



	}

}