import CityComponents.City;
import CityComponents.Station;
import Panels.RightPanel;
import Panels.ManualMode.StationsCheckBoxesPanel;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class Board extends JFrame{
	City city;

	final int dim = 40;
	int x;
	int y;
	int frame_x;
	int frame_y;
	int width;
	int height;

	final int DELAY = 500;

	RightPanel rightPanel;

	//images
	BufferedImage busImage;
	BufferedImage station_A_empty;
	BufferedImage station_A_with_people;
	BufferedImage station_B_empty;
	BufferedImage station_B_with_people;

	BufferedImage main_station_empty;
	BufferedImage main_station_with_people;

	BufferedImage mainStationSign;
	BufferedImage parkingSign;
	BufferedImage gasStationSign;
	BufferedImage gasStationPainting;

	BufferedImage rainImage;
	BufferedImage sunImage;
	BufferedImage backgroundImage;

	int[] top_left;
	int[] top_right;
	int[] bottom_left;
	int[] bottom_right;


	public Board(City city){
		this.city = city;
		this.x = city.getX();
		this.y = city.getY();

		frame_x = x ;
		frame_y = y + 12;

		this.width = this.frame_y * dim;
		this.height = this.frame_x * dim;

		top_left = city.top_left();
		top_right = city.top_right();
		bottom_left = city.bottom_left();
		bottom_right = city.bottom_right();
		
		initImages();

		BorderLayout layout = new BorderLayout();
		this.getContentPane().setLayout(layout);
		rightPanel = new RightPanel(city);
		this.getContentPane().add(rightPanel, BorderLayout.EAST);


		this.setTitle("BusBoss");
		this.setSize(this.width, this.height);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.paint(this.getGraphics());


	}



	private void initImages() {
		try{
			this.busImage = ImageIO.read(new File("images/bus_with_background.jpg"));

			//Bus stations
			this.station_A_empty = ImageIO.read(new File("images/stations/station_A_empty.jpeg"));
			this.station_A_with_people = ImageIO.read(new File("images/stations/station_A_with_people.jpeg"));
			this.station_B_empty = ImageIO.read(new File("images/stations/station_B_empty.jpeg"));
			this.station_B_with_people = ImageIO.read(new File("images/stations/station_B_with_people.jpeg"));

			//Gas station & Main station
			this.main_station_empty = ImageIO.read(new File("images/stations/main_station_empty.jpeg"));
			this.main_station_with_people = ImageIO.read(new File("images/stations/main_station_with_people.jpeg"));
			this.mainStationSign = ImageIO.read(new File("images/main_station_sign.jpg"));
			this.parkingSign = ImageIO.read(new File("images/parking.jpg"));
			this.gasStationSign = ImageIO.read(new File("images/gas_station_sign.jpg"));
			this.gasStationPainting = ImageIO.read(new File("images/gas_station_painting.jpg"));

			//Weather
			this.rainImage = ImageIO.read(new File("images/weather/rain.jpg"));
			this.sunImage = ImageIO.read(new File("images/weather/sun.png"));
			this.backgroundImage = ImageIO.read(new File("images/weather/background.png"));

		}

		catch (IOException e){
			System.out.println("error in reading images!");
			e.printStackTrace();
		}
	}

	public JTable getManualModeTable(){
		return rightPanel.getManualModeTable();
	}
	public StationsCheckBoxesPanel getStationsCheckBoxesPanel(){
		return rightPanel.getStationCheckBoxesPanel();
	}


	private void draw_main_station() {
		int row, col;
		Graphics g = this.getGraphics();
		int[] top_left = city.getMainStation().getTop_left();
		int[] top_right = city.getMainStation().getTop_right();

		//draw main station roads
		draw_horizontal_road(top_left, top_right);

		//draw main station parking
		g.setColor(Color.GRAY);
		int[] parking_top_left = new int[]{top_left[0]+2, top_left[1]+1};
		int[] parking_top_right = new int[]{parking_top_left[0], parking_top_left[1]+1};
		int[] parking_bottom_left = new int[]{parking_top_left[0]+1, parking_top_left[1]};
		int[] parking_bottom_right = new int[]{parking_top_left[0]+1, parking_top_left[1]+1};

		g.fillRect(parking_top_left[1] * dim, parking_top_left[0] * dim, dim, dim);
		g.fillRect(parking_top_right[1] * dim, parking_top_right[0] * dim, dim, dim);
		g.fillRect(parking_bottom_left[1] * dim, parking_bottom_left[0] * dim, dim, dim);
		g.fillRect(parking_bottom_right[1] * dim, parking_bottom_right[0] * dim, dim, dim);

		//draw parking sign
		g.drawImage(parkingSign, parking_bottom_left[1] * dim + dim/6, parking_bottom_left[0] * dim + dim + dim/6, dim*2 - 2*dim/6, dim - 2*dim/6, null);


		//draw main station sign
		Station main_station = city.getMainStation();
		g.drawImage(mainStationSign, main_station.getLocation()[1] * dim + dim + 2*dim/6, main_station.getLocation()[0] * dim - 2*dim + 2*dim/6, dim*2 - 4*dim/6, dim*2 - 4*dim/6, null);

		//draw main station itself
		if(main_station.isArePassengersWaiting()){
			g.drawImage(main_station_with_people, main_station.getLocation()[1] * dim - dim, main_station.getLocation()[0] * dim - 2*dim, dim*2, dim*2, null);

		}
		else{
			g.drawImage(main_station_empty, main_station.getLocation()[1] * dim - dim, main_station.getLocation()[0] * dim - 2*dim, dim*2, dim*2, null);
		}
	}


	private void draw_gas_station() {
		Graphics g = this.getGraphics();
		int[] top_left = city.getGasStation().getTop_left();
		int[] top_right = city.getGasStation().getTop_right();

		//draw gas station roads
		draw_horizontal_road(top_left, top_right);

		//draw gas station sign & painting
		g.drawImage(gasStationPainting, top_left[1] * dim  + dim/6, top_left[0] * dim - dim + dim/6, dim-2*dim/6, dim-2*dim/6, null);
		g.drawImage(gasStationSign, top_left[1] * dim + dim, top_left[0] * dim - dim, dim*2, dim, null);
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
			g.drawImage(station_A_with_people, station1.getLocation()[1] * dim - dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}
		else{
			g.drawImage(station_A_empty, station1.getLocation()[1] * dim - dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}

		if(station2.isArePassengersWaiting()){
			g.drawImage(station_A_with_people, station2.getLocation()[1] * dim, station2.getLocation()[0] * dim, dim*2, dim*2, null);

		}
		else{
			g.drawImage(station_A_empty, station2.getLocation()[1] * dim, station2.getLocation()[0] * dim, dim*2, dim*2, null);
		}

	}

	private void draw_B_stations() {
		Station station1 = city.getBusStations().get("b1");
		Station station2 = city.getBusStations().get("b2");
		Graphics g = this.getGraphics();
		//g.setColor(Color.MAGENTA);
		if(station1.isArePassengersWaiting()){
			g.drawImage(station_B_with_people, station1.getLocation()[1] * dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}
		else{
			g.drawImage(station_B_empty, station1.getLocation()[1] * dim, station1.getLocation()[0] * dim, dim*2, dim*2, null);
		}

		if(station2.isArePassengersWaiting()){
			g.drawImage(station_B_with_people, station2.getLocation()[1] * dim - dim, station2.getLocation()[0] * dim - dim, dim*2, dim*2, null);

		}
		else{
			g.drawImage(station_B_empty, station2.getLocation()[1] * dim - dim, station2.getLocation()[0] * dim - dim, dim*2, dim*2, null);
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
		draw_roads();
		draw_A_stations();
		draw_B_stations();


	}

	public void drawRoadsOutsideTheNeighborhoood(){
		// draw the road between neighborhood and main station
		draw_horizontal_road(city.getRoadBetweenCityAndMainStation().getStart(), city.getRoadBetweenCityAndMainStation().getEnd());
		
		// draw the road between neighborhood and gas station
		draw_horizontal_road(city.getRoadBetweenCityAndGasStation().getStart(), city.getRoadBetweenCityAndGasStation().getEnd());

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

	private void drawWeather(){
		Graphics g = this.getGraphics();
		int x = dim * 2;
		int y = dim + 10;
		int size = dim * 2 - 10;

		//override previous weather
		for(int i=0; i<4; i++){
			g.drawImage(backgroundImage, x+i*3*dim, y, size, size, null);

		}

		if(city.isRaining()){
			for(int i=0; i<4; i++){
				g.drawImage(rainImage, x+i*3*dim, y, size, size, null);

			}
		}
		else{
			g.drawImage(sunImage, x, y, size, size, null);
		}




	}
	

	public void paint() throws InterruptedException {
		BusPainter busPainter = new BusPainter(this.getGraphics(), city, dim, DELAY);
		draw_neighbourhood();
		draw_main_station();
		draw_gas_station();
		checkStationsLocations();
		drawWeather();

		for(int i=0; i<dim; i++){
			busPainter.drawBusses(i);
			Thread.sleep(DELAY/dim);
		}

		rightPanel.updatePanels();

	}

}