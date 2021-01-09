package UI.Panels;

import CityComponents.City;
import UI.Panels.ControlPanel.ControlPanel;
import UI.Panels.ManualMode.ManualModePanel;
import UI.Panels.ManualMode.StationsCheckBoxesPanel;
import UI.Panels.Scenarios.ScenariosPanel;

import javax.swing.*;
import java.awt.*;

//Create and update all the components in the right part of the frame. The panel comprises 3 sections:
//1. Control Panel (which reflect the state of each bus and each line)
//2. Scenarios buttons
//3. Manual mode panel

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

    //Update the information displayed in all the sub-panels according to the new state of the city
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