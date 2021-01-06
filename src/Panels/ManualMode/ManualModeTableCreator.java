package Panels.ManualMode;

import CityComponents.Bus;
import CityComponents.City;
import CityComponents.Station;
import Lines.BusMover;
import Panels.ControlPanel.BussesTableCreator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManualModeTableCreator {
    BussesTableCreator bussesTableCreator;
    Object[] headers;
    Object[][] values;
    DefaultTableModel model;
    JTable table;
    City city;

    public ManualModeTableCreator(City city){
        this.city = city;
        this.bussesTableCreator = new BussesTableCreator();
        this.headers = bussesTableCreator.createHeaders();
        this.values = bussesTableCreator.createValues(city);

    }


    public JTable overrideValuesWithCheckBoxes(){
        for(int i=0; i<values.length; i++) {
            values[i][BussesTableCreator.FULL_INDEX] = Boolean.FALSE;
            values[i][BussesTableCreator.STOP_PRESSED_INDEX] = Boolean.FALSE;
        }

        model = new DefaultTableModel(values, headers);
        table = new JTable(model) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case BussesTableCreator.ID_INDEX:
                        return String.class;
                    case BussesTableCreator.LINE_INDEX:
                        return String.class;
                    case BussesTableCreator.FULL_INDEX:
                        return Boolean.class;
                    case BussesTableCreator.STOP_PRESSED_INDEX:
                        return Boolean.class;
                    default:
                        return Integer.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column)
            {
                Boolean isSelected;
                Bus bus = city.getBusses().get(row);
                BusMover busMover = city.getBusMover();

                if(!city.isManualMode()){
                    return false;
                }

                if(column == BussesTableCreator.ID_INDEX || column == BussesTableCreator.LINE_INDEX || column == BussesTableCreator.ROUNDS_TO_GAS_STATION_INDEX){
                    return false;
                }

                if(bus.isReserve() && !bus.isInUse()){
                    return false;
                }

                isSelected = (Boolean)table.getValueAt(row, column);
                if(isSelected && !busMover.isAtDestinationStation(bus)) {
                    return false;
                }
                return true;

            }
        };


        table.setGridColor(Color.magenta);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(true);

        return table;

    }

}
