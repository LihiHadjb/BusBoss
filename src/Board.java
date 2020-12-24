package GUI;

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
	int x;
	int y;
	final int dim = 50;
	BufferedImage busImage;
	BufferedImage stationWithPeopleImage;
	BufferedImage gasStationImage;
	BufferedImage emptyStationImage;
	HashMap<String, int[]> stationLoactions;
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

		this.setTitle("BusBoss");
		this.setSize(this.y * this.dim, this.x * this.dim);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.paint(this.getGraphics());

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
		this.stationLoactions = new HashMap<>();


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
		Graphics g = this.getGraphics();
//		draw_borders(top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		draw_roads();
		draw_A_stations();
		draw_B_stations();

	}

	public void drawBusses(HashMap<String, int[]> bussesLocations){
		Graphics g = this.getGraphics();
		for (String bus : bussesLocations.keySet()){
			g.drawImage(busImage, bussesLocations.get(bus)[0] * dim, bussesLocations.get(bus)[1] * dim, null);
		}
	}

	public void drawPassengerInStations(HashMap<String, Boolean> arePassengersWaiting){
		Graphics g = this.getGraphics();
		for (String station : arePassengersWaiting.keySet()){
			int[] stationLoc = this.stationLoactions.get(station);
			if(arePassengersWaiting.get(station)){
				g.drawImage(stationWithPeopleImage, stationLoc[0] * dim * 2, stationLoc[1] * dim * 2, null);
			}
		}
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

	public void paint(HashMap<String, int[]> bussesLocations, HashMap<String, Boolean> arePassengersWaiting) {
		Graphics g = this.getGraphics();
		draw_background();
		draw_neighbourhood();
		draw_borders("central_station");
		draw_borders("gas_station");
		g.drawImage(busImage, 6 * dim, 5 * dim, dim, dim, null);
		drawBusses(bussesLocations);
		drawPassengerInStations(arePassengersWaiting);
	}

}