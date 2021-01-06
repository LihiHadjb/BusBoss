package Panels.ManualMode;

import CityComponents.Bus;
import CityComponents.City;
import Lines.BusMover;
import Panels.ControlPanel.BussesTableCreator;
import SpecificationVars.ManualModeInputCreator;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ManualBussesPanel extends JPanel {
    ManualModeTableCreator manualModeTableCreator;
    JTable manualModeTable;
    JPanel bussesPanel;
    JScrollPane jScrollPane;
    City city;


    public ManualBussesPanel(City city){
        this.city = city;
        manualModeTableCreator = new ManualModeTableCreator(city);
        manualModeTable = manualModeTableCreator.overrideValuesWithCheckBoxes();
        manualModeTable.setFillsViewportHeight(true);

        bussesPanel = new JPanel();

        jScrollPane = new JScrollPane(manualModeTable);
        jScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        jScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));


        bussesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Busses", TitledBorder.CENTER, TitledBorder.TOP));

        bussesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bussesPanel.add(jScrollPane);
        this.add(bussesPanel);
    }

    public JTable getTable(){
        return manualModeTable;
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        bussesPanel.setEnabled(enabled);
        jScrollPane.setEnabled(enabled);
        manualModeTable.setEnabled(enabled);

//        if (enabled) {
//            manualModeTable.setRowSelectionAllowed(true);
//            manualModeTable.setForeground(Color.black);
//            manualModeTable.setBackground(Color.white);
//        } else {
//            manualModeTable.setRowSelectionAllowed(false);
//            manualModeTable.setForeground(Color.gray);
//            manualModeTable.setBackground(Color.lightGray);
//        }

    }

    public void uncheckForBussesStoopingInDestinationStation(){
        BusMover busMover = city.getBusMover();
        int row;
        for(Bus bus : city.getBusses().values()){
            if(busMover.isAtDestinationStation(bus) && bus.isStopAtNextStation()){
                row = bus.getId();
                manualModeTable.getModel().setValueAt(false, row, BussesTableCreator.FULL_INDEX);
                manualModeTable.getModel().setValueAt(false, row, BussesTableCreator.STOP_PRESSED_INDEX);

            }
        }
    }



}
