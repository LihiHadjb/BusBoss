package game;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//import tau.smlab.syntech.controller.executor.ControllerExecutor;
//import tau.smlab.syntech.controller.jit.BasicJitController;

@SuppressWarnings("serial")
public class Board extends JFrame {
	final int x = 80;
	final int y = 50;
	final int dim = 10;
	int[] monkey = new int[] { 0, 0 };
	int[] banana = new int[] { -1, -1 };
	int[] bed = new int[] { 0, 0 };
	int[] tv = new int[] { 2, 2 };
	int[] shower = new int[] { 2, 0 };
	BufferedImage m;

//	ControllerExecutor executor;
//	Map<String,String> inputs = new HashMap<String, String>();

	public void run() throws Exception {

		//executor = new ControllerExecutor(new BasicJitController(), "out");
		m = ImageIO.read(new File("img/monkey.jpg"));

		Random rand = new Random();
		banana[0] = rand.nextInt(8);
		banana[1] = rand.nextInt(8);

		//inputs.put("banana[0]", Integer.toString(banana[0]));
		//inputs.put("banana[1]", Integer.toString(banana[1]));
		//executor.initState(inputs);

		//Map<String, String> sysValues = executor.getCurrOutputs();

		//monkey[0] = Integer.parseInt(sysValues.get("monkey[0]"));
		//monkey[1] = Integer.parseInt(sysValues.get("monkey[1]"));

		paint(this.getGraphics());
		Thread.sleep(1000);

		while (true) {

			if (monkey[0] == banana[0] & monkey[1] == banana[1]) {
				banana[0] = rand.nextInt(8);
				banana[1] = rand.nextInt(8);
			}

			//inputs.put("banana[0]", Integer.toString(banana[0]));
			//inputs.put("banana[1]", Integer.toString(banana[1]));

			//executor.updateState(inputs);

			//sysValues = executor.getCurrOutputs();

			//monkey[0] = Integer.parseInt(sysValues.get("monkey[0]"));
			//monkey[1] = Integer.parseInt(sysValues.get("monkey[1]"));

			paint(this.getGraphics());
			Thread.sleep(1000);
		}
	}

	private void draw_borders(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right, String type) {
		int row, col;
		switch (type){
			case "neighbourhood":
				g.setColor(Color.GREEN);
				break;
			case "central_station":
				g.setColor(Color.ORANGE);
				break;
			case "gas_station":
				g.setColor(Color.BLUE);
		}

		row = top_left[0];
		for(col=top_left[1]; col<top_right[1]; col++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);
		}

		for(row=top_right[0]; row<bottom_right[0]; row++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);

		}

		for(col=bottom_right[1]; col>bottom_left[1]; col--) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);

		}

		for(row=bottom_left[0]; row>top_left[0]; row--) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);
		}
	}

	private void draw_vertical_road(Graphics g, int[] start,int [] end) {
		int row, col;

		col = start[1];
		g.setColor(Color.GRAY);

		// Draw the road
		for(row=start[0]; row<=end[0]; row++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);
			g.fillRect((col+1) * dim, row * dim, dim, dim);
		}

		// Draw while lined between lanes
		g.setColor(Color.WHITE);
		for(row=start[0]; row<=end[0]; row++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect((col * dim)+dim-1, (row * dim)+(dim/4), 1, dim/2);
		}
	}

	private void draw_horizontal_road(Graphics g, int[] start,int [] end) {
		int row, col;

		row = start[0];
		g.setColor(Color.GRAY);

		// Draw the road
		for(col=start[1]; col<=end[1]; col++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect(col * dim, row * dim, dim, dim);
			g.fillRect(col * dim, (row+1) * dim, dim, dim);
		}

		// Draw while lined between lanes
		g.setColor(Color.WHITE);
		for(col=start[1]; col<=end[1]; col++) {
			System.out.println("row: " + row + " col: " + col);
			g.fillRect((col * dim)+(dim/4), (row * dim)+dim-1, dim/2, 1);
		}
	}

	private void draw_local_stations(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right) {
		int height = ((bottom_left[0]-1) - (top_left[0]+1))/2;
		int width = ((top_right[1]-1) - (top_left[1]+1))/2;
		int[] station1 = new int[2];
		station1[0] = top_left[0] + 1 + height;
		station1[1] = top_left[1] + 1;

		g.setColor(Color.PINK);
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);


		dict local_stations_locations = {a1: [0,7]};
		dict general_stations_locations = {g1: [0,7]};


	}

	private void draw_neighbourhood(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right) {
		// neighbourhood borders
		draw_borders(g, top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		int[] start = new int[2];
		int[] end = new int[2];

		//left vertical road
		start[0] = top_left[0]+2;
		start[1] = top_left[1]+2;
		end[0] = bottom_left[0]-2;
		end[1] = bottom_left[1]+2;
		draw_vertical_road(g, start, end);

		// top horizontal road
		end[0] = top_right[0]+2;
		end[1] = top_right[1]-2;
		draw_horizontal_road(g, start, end);

		// right vertical road
		start[0] = end[0];
		start[1] = end[1]-1;
		end[0] = bottom_right[0]-2;
		end[1] = bottom_right[1]-2-1;
		draw_vertical_road(g, start, end);

		// bottom horizontal road
		start[0] = bottom_left[0]-2-1;
		start[1] = bottom_left[1]+2;
		end[0] = bottom_right[0]-2 -1;
		draw_horizontal_road(g, start, end);


		// draw local stations
		draw_local_stations(g, top_left, top_right, bottom_left, bottom_right);

		// draw general line stations

	}

//	neighbourhood1 = init (); //TODO: create city, neighbourhood, station,  classes.


	@Override
	public void paint(Graphics g) {
		int row;
		int col;

		g.setColor(Color.WHITE);
		for (row = 0; row < y; row++) {
			for (col = 0; col < x; col++) {
				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}

		int[] top_left = {6, 20};
		int [] top_right = {6, 32};
		int [] bottom_right = {27,32};
		int [] bottom_left = {27, 20};

		draw_neighbourhood(g, top_left, top_right, bottom_left, bottom_right);

		g.setColor(Color.BLACK);
		g.drawString("BED", bed[0] * dim + 40, bed[1] * dim + 50);
		g.drawString("SHOWER", shower[0] * dim + 20, shower[1] * dim + 50);
		g.drawString("TV", tv[0] * dim + 40, tv[1] * dim + 50);



		if (banana[0] != -1) {
			g.setColor(Color.YELLOW);
			g.fillRect(banana[0] * dim, banana[1] * dim, dim, dim);
			g.setColor(Color.BLACK);
			g.drawString("BANANA", banana[0] * dim + 30, banana[1] * dim + 50);
		}
		if (m != null) {
			g.drawImage(m, monkey[0] * dim, monkey[1] * dim, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(monkey[0] * dim, monkey[1] * dim, dim, dim);
			g.setColor(Color.WHITE);
			g.drawString("MONKEY", monkey[0] * dim + 20, monkey[1] * dim + 50);
		}
	}

	public static void main(String args[]) throws Exception {
		Board check = new Board();
		check.setTitle("monkey");
		check.setSize(check.x * check.dim, check.y * check.dim);
		check.setDefaultCloseOperation(EXIT_ON_CLOSE);
		check.setVisible(true);
		check.run();
	}
}