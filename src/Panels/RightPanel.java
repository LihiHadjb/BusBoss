package Panels;

import CityComponents.City;
import Panels.ControlPanel.ControlPanel;
import Panels.ManualMode.ManualModePanel;
import Panels.ManualMode.StationsCheckBoxesPanel;
import Panels.Scenarios.ScenariosPanel;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    ScenariosPanel scenariosPanel;
    ControlPanel controlPanel;
    ManualModePanel manualModePanel;
    City city;

    public RightPanel(City city) {
        this.city = city;

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);


        this.controlPanel = new ControlPanel(city);
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(this.controlPanel);

        scenariosPanel = new ScenariosPanel(city);
        scenariosPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(this.scenariosPanel);

        manualModePanel = new ManualModePanel(city);
        manualModePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        manualModePanel.setEnabled(false);
        this.add(manualModePanel);

        this.setSize(this.getPreferredSize());
    }

    public StationsCheckBoxesPanel getStationCheckBoxesPanel(){
        return manualModePanel.getStationsCheckBoxesPanel();
    }

    public void updatePanels() {
        controlPanel.updatePanel();
        if(city.isManualMode()){
            manualModePanel.setEnabled(true);
            scenariosPanel.setEnabled(false);
            manualModePanel.updatePanel();

        }
        else{
            manualModePanel.setEnabled(false);
            scenariosPanel.setEnabled(true);
        }
    }

    public JTable getManualModeTable() {
        return manualModePanel.getTable();
    }
}