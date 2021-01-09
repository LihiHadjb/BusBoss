package UI.Panels.ManualMode;

import CityComponents.Bus;
import CityComponents.City;
import Lines.BusMover;
import UI.Panels.ControlPanel.BussesTableCreator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

//Define and create the content displayed in the Busses table in the Manual mode section,
// according to the state of the city
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

    //Replace the string displayes in the "full" and "stop request" column with user-controlled checkboxes
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

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component comp=super.prepareRenderer(renderer,row, column);
                if(column==4 && "0".equals(model.getValueAt(row, 4).toString())){
                    comp.setBackground(Color.PINK);
                }
                else{
                    comp.setBackground(Color.WHITE);
                }

                comp.setForeground(Color.BLACK);
                return comp;
            }
        };

        table.setGridColor(Color.black);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(true);
        table.setSize(table.getPreferredSize());

        //center the text inside cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int i=0; i<headers.length; i++){
            if ( (i != BussesTableCreator.FULL_INDEX) && (i != BussesTableCreator.STOP_PRESSED_INDEX)){
                table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            }
        }

        return table;

    }

    //Update the information displayed in the busses's table according to the new state of the city
    public void updateValues(City city){
        Object[][] newValues = bussesTableCreator.createValues(city);
        TableModel dtm = table.getModel();
        int num_rows = newValues.length;

        for(int i=0; i<num_rows; i++){
            dtm.setValueAt(newValues[i][BussesTableCreator.LINE_INDEX], i, BussesTableCreator.LINE_INDEX);
            dtm.setValueAt(newValues[i][BussesTableCreator.ROUNDS_TO_GAS_STATION_INDEX], i, BussesTableCreator.ROUNDS_TO_GAS_STATION_INDEX);

        }
    }

}
