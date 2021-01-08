package Panels.ManualMode;

import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManualModePanel extends JPanel {
    final String TITLE="Manual Mode";

    //ManualModeTableCreator manualModeTableCreator;
    ManualBussesPanel manualBussesPanel;
    StationsCheckBoxesPanel stationsCheckboxes;
    JButton manualModeButton;
    JButton rainBtn;
    City city;

    public ManualModePanel(City city){
        this.city = city;
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY), TITLE, TitledBorder.CENTER, TitledBorder.TOP));

        addMModeButton();
        addRainButton();
        addBussesTable();
        addStationsCheckBoxes();
        this.setSize(this.getPreferredSize());

    }

    private void addStationsCheckBoxes(){
        stationsCheckboxes = new StationsCheckBoxesPanel(city);
        stationsCheckboxes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(stationsCheckboxes);
    }

    private void addBussesTable(){
        manualBussesPanel = new ManualBussesPanel(city);
        manualBussesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(manualBussesPanel);

    }

    private void addMModeButton(){
        manualModeButton = new JButton("Manual");
        Color defaultBgColor = new JButton().getBackground();
        manualModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(manualModeButton);

        ActionListener manualModeButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city.toggleMode();
                city.setRaining(false);
                city.setRushHour(false);
                if(city.isManualMode()) {
                    manualModeButton.setBackground(new Color(130, 216, 229));
                }
                else {
                    manualModeButton.setBackground(defaultBgColor);
                }
            }
        };
        manualModeButton.addActionListener(manualModeButtonListener);

    }

    private void addRainButton(){
        rainBtn = new JButton("Rain");
        Color defaultBgColor = new JButton().getBackground();
        rainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
//        rainBtn.setBounds(0, 0, 3*40, 40);
        this.add(rainBtn);

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
        rainBtn.addActionListener(rainBtnListener);
    }

    public JTable getTable(){
        return manualBussesPanel.getTable();
    }

    public StationsCheckBoxesPanel getStationsCheckBoxesPanel(){
        return stationsCheckboxes;
    }

    public void updatePanel(){
        this.setEnabled(city.isManualMode());
        if(city.isManualMode()){
            manualBussesPanel.uncheckForBussesStoopingInDestinationStation();
            manualBussesPanel.updatePanel();
            stationsCheckboxes.setEnabledAllStationsWithPeople();
        }
    }

    @Override
    public void setEnabled(boolean isEnabled){
        super.setEnabled(isEnabled);
        rainBtn.setEnabled(isEnabled);
        manualBussesPanel.setEnabled(isEnabled);
        stationsCheckboxes.setEnabled(isEnabled);

    }
}
