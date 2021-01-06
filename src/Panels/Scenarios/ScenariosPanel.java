package Panels.Scenarios;

import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScenariosPanel extends JPanel {
    String TITLE = "Scenarios";

    JButton rainBtn;
    JButton rushHourBtn;
    JButton resetScenarioBtn;

    public ScenariosPanel(City city){
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY), TITLE, TitledBorder.CENTER, TitledBorder.TOP));

        // Buttons
        rainBtn = new JButton("Rain");
        rushHourBtn = new JButton("Rush Hour");
        resetScenarioBtn = new JButton("Reset");
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
        this.add(rainBtn);
        this.add(rushHourBtn);
        this.add(resetScenarioBtn);

        this.setSize(this.getPreferredSize());


        // Set panel location top-mid window
//        int panelHalfWid = this.getWidth() / 2;
//        int windowHalfWid = width / 2;
//        int newX = windowHalfWid - panelHalfWid;
//        int newY = height - this.getHeight();
//        this.setLocation(newX, newY);

        // Register panel
        //this.getContentPane().add(this, BorderLayout.PAGE_START);

    }

    public void setEnabled(boolean isEnabled){
        super.setEnabled(isEnabled);
        rainBtn.setEnabled(isEnabled);
        resetScenarioBtn.setEnabled(isEnabled);
        rushHourBtn.setEnabled(isEnabled);
    }
  }
