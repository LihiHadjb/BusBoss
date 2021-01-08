package Panels.ControlPanel;

import CityComponents.City;

import javax.swing.*;
import javax.swing.table.*;
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

        table = new JTable(dtm) {
            public Component prepareRenderer(TableCellRenderer renderer,int row,int column)
            {
                Component comp=super.prepareRenderer(renderer,row, column);
                if((column==4 && "0".equals(dtm.getValueAt(row, 4).toString())) || (column==1 && "true".equals(dtm.getValueAt(row, 1).toString()))){
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
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER);
        for(int i=0; i<headers.length; i++){
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }

        return table;
    }

    public void updateValues(City city){
        Object[][] newValues = createValues(city);
        for(int i=0; i<newValues.length; i++){
            for(int j=0; j<newValues[0].length; j++){
                dtm.setValueAt(newValues[i][j],i, j);
            }
        }
        dtm.fireTableDataChanged();
    }
}