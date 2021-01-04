package Panels;

import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class TableCreator {
    public DefaultTableModel dtm;

    public Object[][] values;
    public Object [] headers;
    public JTable table;

    public abstract Object[][] createValues(City city);
    public abstract Object[] createHeaders();
    public JTable create(City city){

        values = createValues(city);
        headers = createHeaders();

        dtm = new DefaultTableModel(values, headers);
        table = new JTable(dtm);
        table.setGridColor(Color.magenta);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(true);
        return table;
    }

    public void updateValues(City city){
        Object[][] newValues = createValues(city);
        for(int i=0; i<newValues.length; i++){
            for(int j=0; j<newValues[0].length; j++)
            dtm.setValueAt(newValues[i][j],i, j );
        }
        dtm.fireTableDataChanged();

    }
}

