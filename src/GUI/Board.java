package GUI;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@SuppressWarnings("serial")
public class Board extends JFrame {
	final int x = 16;
	final int y = 20;
	final int dim = 50;
	BufferedImage busImage;
	BufferedImage stationWithPeopleImage;
	BufferedImage gasStationImage;
	BufferedImage emptyStationImage;
	HashMap<String, int[]> stationLoactions;


	public Board(){
		this.setTitle("BusBoss");
		this.setSize(this.y * this.dim, this.x * this.dim);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.paint(this.getGraphics());

		try{
			this.busImage = ImageIO.read(new File("BusBoss/images/bus.jpeg"));
			this.stationWithPeopleImage =  ImageIO.read(new File("BusBoss/images/station_with_people.jpeg"));
			this.gasStationImage =  ImageIO.read(new File("BusBoss/images/gas_station.jpeg"));
			this.emptyStationImage =  ImageIO.read(new File("BusBoss/images/empty_station.jpeg"));
		}
		catch (IOException e){
			System.out.println("error in reading images!");
			e.printStackTrace();
		}
		this.stationLoactions = new HashMap<>();


	}

	private void draw_borders(int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right, String type) {
		int row, col;
		Graphics g = this.getGraphics();
		switch (type){
			case "neighbourhood":
				g.setColor(Color.WHITE);
				break;
			case "central_station":
				g.setColor(Color.ORANGE);
				break;
			case "gas_station":
				g.setColor(Color.BLUE);
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

	private void draw_A_stations(int[] top_left, int [] top_right, int[] bottom_left, int[] bottom_right) {
		Graphics g = this.getGraphics();
		g.setColor(Color.PINK);
		int height = ((bottom_left[0]-1) - (top_left[0]+1))/2;
		int width = ((top_right[1]-1) - (top_left[1]+1))/2;
		int[] station1 = new int[2];
		int[] station2 = new int[2];

		// draw left local station
		station1[0] = top_left[0] + 1 + height;
		station1[1] = top_left[1];
		//g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);
		g.drawImage(emptyStationImage, station1[1] * dim, station1[0] * dim, dim, dim, null);
		this.stationLoactions.put("a1", station1);

		// draw right local station
		station2[0] = top_left[0] + 1 + height;
		station2[1] = top_right[1];
		//g.fillRect(station2[1] * dim, station2[0] * dim, dim, dim);
		g.drawImage(stationWithPeopleImage, station2[1] * dim, station2[0] * dim, dim, dim, null);

		this.stationLoactions.put("a2", station2);

	}

	private void draw_B_stations(int[] top_left, int [] top_right, int[] bottom_left, int[] bottom_right) {
		Graphics g = this.getGraphics();
		g.setColor(Color.MAGENTA);
		int height = ((bottom_left[0]-1) - (top_left[0]+1))/2;
		int width = ((top_right[1]-1) - (top_left[1]+1))/2;
		int[] station = new int[2];
		int[] station2 = new int[2];

		// draw top west general station
		station[0] = top_left[0] + 3;
		station[1] = top_left[1] + 1 + width;
		g.fillRect(station[1] * dim, station[0] * dim, dim, dim);
		this.stationLoactions.put("b1", station);

		// draw bottom west general station
		station2[0] = bottom_left[0] - 3;
		station2[1] = bottom_left[1] + 1  + width;
		g.fillRect(station2[1] * dim, station2[0] * dim, dim, dim);
		this.stationLoactions.put("b2", station2);

	}

	private void draw_roads(int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right){
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

	private void draw_neighbourhood(int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right) {
		Graphics g = this.getGraphics();
//		draw_borders(top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		draw_roads(top_left, top_right, bottom_left, bottom_right);
		draw_A_stations(top_left, top_right, bottom_left, bottom_right);
		draw_B_stations(top_left, top_right, bottom_left, bottom_right);

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
				g.drawImage(stationWithPeopleImage, stationLoc[0] * dim, stationLoc[1] * dim, null);
			}
		}
	}


	public void paint(HashMap<String, int[]> bussesLocations, HashMap<String, Boolean> arePassengersWaiting) {
		int row;
		int col;

		Graphics g = this.getGraphics();

		g.setColor(Color.WHITE);
		for (row = 0; row < x; row++) {
			for (col = 0; col < y; col++) {
				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}

		//neighbourhood A
		int[] top_left = {x/8, 2*y/8};
		int [] top_right = {x/8, 6*y/8};
		int [] bottom_left = {7*x/8, 2*y/8};
		int [] bottom_right = {7*x/8,6*y/8};
		draw_neighbourhood(top_left, top_right, bottom_left, bottom_right);

		// main station
		int[] top_left_cs = {1+x/7, 3*y/8};
		int [] top_right_cs = {1+x/7, 5*y/8};
		int [] bottom_left_cs = {1+x/7, 3*y/8};
		int [] bottom_right_cs = {1+x/7,5*y/8};
		draw_borders(top_left_cs, top_right_cs, bottom_left_cs, bottom_right_cs, "central_station");
		int[] ms_loc = new int[2];
		ms_loc[0] = bottom_left_cs[0] - 1;
		ms_loc[1] = bottom_left_cs[1] + 1 + (bottom_right_cs[1] - bottom_left_cs[1])/2; //TODO: VERIFY IT'S OK - HERE!
		this.stationLoactions.put("main_station", ms_loc);

		// gas station
		int[] top_left_gs = {x-x/7-2, 3*y/8};
		int [] top_right_gs = {x-x/7-2, 5*y/8};
		int [] bottom_left_gs = {x-x/7-2, 3*y/8};
		int [] bottom_right_gs = {x-x/7-2, 5*y/8};
		draw_borders(top_left_gs, top_right_gs, bottom_left_gs, bottom_right_gs, "gas_station");
		int[] gs_loc = new int[2];
		gs_loc[0] = bottom_left_gs[0] - 1;
		gs_loc[1] = bottom_left_gs[1] + 1 + (bottom_right_gs[1] - bottom_left_gs[1])/2; //TODO: VERIFY IT'S OK - HERE!
		this.stationLoactions.put("gas_station", gs_loc);


		drawBusses(bussesLocations);
		drawPassengerInStations(arePassengersWaiting);


	}

}