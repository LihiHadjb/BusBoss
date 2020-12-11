package GUI;

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
	final int x = 50;
	final int y = 80;
	final int dim = 10;

	public void run() throws Exception {
		paint(this.getGraphics());
		//TODO: init city here and move all the draw methods into the respective classes, so that each object will draw itself
		//city = new city (g, neighbourhoods_borders)
		Thread.sleep(1000);

//		while (true) {
//			paint(this.getGraphics());
//			Thread.sleep(1000);
//		}
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
		g.setColor(Color.PINK);
		int height = ((bottom_left[0]-1) - (top_left[0]+1))/2;
		int width = ((top_right[1]-1) - (top_left[1]+1))/2;
		int[] station1 = new int[2];

		// draw left local station
		station1[0] = top_left[0] + 1 + height;
		station1[1] = top_left[1] + 1;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw right local station
		station1[0] = top_left[0] + 1 + height;
		station1[1] = top_right[1] - 1;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw top local station
		station1[0] = top_left[0] + 1;
		station1[1] = top_left[1] + 1 + width;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw bottom local station
		station1[0] = bottom_left[0] - 1;
		station1[1] = bottom_left[1] + 1 + width;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);
	}

	private void draw_general_stations(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right) {
		g.setColor(Color.MAGENTA);
		int height = ((bottom_left[0]-1) - (top_left[0]+1))/2;
		int width = ((top_right[1]-1) - (top_left[1]+1))/2;
		int[] station1 = new int[2];

		// draw left east general station
		station1[0] = top_left[0] + 1 + height -1;
		station1[1] = top_left[1] + 1;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw left west general station
		station1[0] = top_left[0] + 1 + height -1;
		station1[1] = top_left[1] + 1 + 3;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw right east general station
		station1[0] = top_left[0] + 1 + height -1;
		station1[1] = top_right[1] - 1;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);

		// draw right west general station
		station1[0] = top_left[0] + 1 + height -1;
		station1[1] = top_right[1] - 1 -3;
		g.fillRect(station1[1] * dim, station1[0] * dim, dim, dim);
	}

	private void draw_roads(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right){
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
	}

	private void draw_neighbourhood(Graphics g, int[] top_left,int [] top_right, int[] bottom_left, int[] bottom_right) {
		draw_borders(g, top_left, top_right, bottom_left, bottom_right, "neighbourhood");
		draw_roads(g, top_left, top_right, bottom_left, bottom_right);
		draw_local_stations(g, top_left, top_right, bottom_left, bottom_right);
		draw_general_stations(g, top_left, top_right, bottom_left, bottom_right);

	}




	@Override
	public void paint(Graphics g) {
		int row;
		int col;

		g.setColor(Color.WHITE);
		for (row = 0; row < x; row++) {
			for (col = 0; col < y; col++) {
				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}

		//neighbourhood A
		int[] top_left = {5, 2};
		int [] top_right = {5, 18};
		int [] bottom_left = {35, 2};
		int [] bottom_right = {35,18};
		draw_neighbourhood(g, top_left, top_right, bottom_left, bottom_right);

		//neighbourhood c
		int[] top_left_c = {5, 62};
		int [] top_right_c = {5, 78};
		int [] bottom_left_c = {35, 62};
		int [] bottom_right_c = {35,78};
		draw_neighbourhood(g, top_left_c, top_right_c, bottom_left_c, bottom_right_c);

		//neighbourhood b
		int[] top_left_b = {15, 22};
		int [] top_right_b = {15, 58};
		int [] bottom_left_b = {35, 22};
		int [] bottom_right_b = {35,58};
		draw_neighbourhood(g, top_left_b, top_right_b, bottom_left_b, bottom_right_b);

		// central station
		int[] top_left_cs = {5, 30};
		int [] top_right_cs = {5, 50};
		int [] bottom_left_cs = {10, 30};
		int [] bottom_right_cs = {10,50};
		draw_borders(g, top_left_cs, top_right_cs, bottom_left_cs, bottom_right_cs, "central_station");

		// gas station
		int[] top_left_gs = {40, 30};
		int [] top_right_gs = {40, 50};
		int [] bottom_left_gs = {45, 30};
		int [] bottom_right_gs = {45,50};
		draw_borders(g, top_left_gs, top_right_gs, bottom_left_gs, bottom_right_gs, "gas_station");
	}

	public static void main(String args[]) throws Exception {
		Board check = new Board();
		check.setTitle("BusBoss");
		check.setSize(check.y * check.dim, check.x * check.dim);
		check.setDefaultCloseOperation(EXIT_ON_CLOSE);
		check.setVisible(true);
		check.run();
	}
}