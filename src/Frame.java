import CityComponents.Bus;
import CityComponents.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    final int dim = 40;
    int x;
    int y;

    BussesPanel bussesPanel;
    JPanel buttonsPanel;
    City city;

    int width;
    int height;

    public Frame(City city){
        this.city = city;

        this.x = city.getX() + 1;
        this.y = city.getY() + 12;
        this.width = this.y * dim;
        this.height = this.x * dim;

        this.setLayout(new BorderLayout());

        this.setTitle("BusBoss");
        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        initBussesPanel();
        initBtnsPanel();
        initBoardPanel();
        this.setVisible(true);


    }


    private void initBussesPanel() {
        bussesPanel = new BussesPanel(city);
        this.getContentPane().add(bussesPanel, BorderLayout.EAST);
    }

    private void initBoardPanel(){
        Board board = new Board(city, dim);
        this.getContentPane().add(board, BorderLayout.CENTER);
    }


    private void initBtnsPanel() {
        // Buttons-Container
        this.buttonsPanel = new JPanel();

        // Buttons
        JButton rainBtn = new JButton("Rain Scenario");
        JButton rushHourBtn = new JButton("Rush Hour Scenario");
        JButton resetScenarioBtn = new JButton("Reset Scenario");
        Color defaultBgColor = new JButton().getBackground();

        // Listeners
        ActionListener rainBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city.toggleRaining();
                if(city.isRaining()) {
                    rainBtn.setBackground(new Color(130, 216, 229));
                }
                else {
                    rainBtn.setBackground(defaultBgColor);
                }
            }
        };
        ActionListener rushBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city.toggleRushHour();
                if(city.isRushHour()) {
                    rushHourBtn.setBackground(new Color(130, 216, 229));
                }
                else {
                    rushHourBtn.setBackground(defaultBgColor);
                }
            }
        };
        ActionListener resetScenarioBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city.setRaining(false);
                city.setRushHour(false);
                rainBtn.setBackground(defaultBgColor);
                rushHourBtn.setBackground(defaultBgColor);
            }
        };

        // Register Listeners to buttons
        rainBtn.addActionListener(rainBtnListener);
        rushHourBtn.addActionListener(rushBtnListener);
        resetScenarioBtn.addActionListener(resetScenarioBtnListener);

        // Register buttons to panel
        this.buttonsPanel.add(rainBtn);
        this.buttonsPanel.add(rushHourBtn);
        this.buttonsPanel.add(resetScenarioBtn);

        // Register panel
        this.getContentPane().add(this.buttonsPanel, BorderLayout.PAGE_START);

    }

    public void updateBussesPanel(){
        this.bussesPanel.update(1);
    }



}
