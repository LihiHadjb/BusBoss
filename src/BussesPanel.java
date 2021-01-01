import CityComponents.Bus;
import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BussesPanel extends JPanel{
    final String TITLE = "Control Panel";
    City city;
    Object[][] values;
    String [] headers;
    JTable bussesTable;
    DefaultTableModel dtm;


    final int ID_INDEX = 0;
    final int LINE_INDEX = 1;
    final int FULL_INDEX = 2;
    final int STOP_PRESSES_INDEX = 3;
    final int ROUNDS_TO_GAS_STATION_INDEX = 4;

    final String ID_HEADER = "Bus Number";
    final String LINE_HEADER = "Line";
    final String FULL_HEADER = "Full";
    final String STOP_PRESSED_HEADER = "STOP";
    final String ROUNDS_TO_GAS_STATION_HEADER = "Rounds Until Gas Station";

    final String NONE = "-";

    public BussesPanel(City city) {
        this.city = city;

        headers = createHeaders();
        values = createValues(0);


        dtm = new DefaultTableModel(values, headers);
        bussesTable = new JTable(dtm);
        bussesTable.setGridColor(Color.magenta);
        bussesTable.setRowSelectionAllowed(true);
        bussesTable.setShowGrid(true);

        this.add(new JScrollPane(bussesTable));

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), TITLE, TitledBorder.CENTER, TitledBorder.TOP));
    }


    private Object[][] createValues(int n){
        String[][] result = new String[city.getNumBusses()][5];
        if(n==1){
            result[0] = new String[]{"1", "2", "3 ", "4",};
            result[1] = new String[]{"1", "2", "3 ", "4",};
            result[2] = new String[]{"1", "2", "3 ", "4",};
            result[3] = new String[]{"1", "2", "3 ", "4",};


        }
        for(Bus bus : city.getBusses().values()){
            String[] row = new String[5];
            row[ID_INDEX] = String.valueOf(bus.getId() + 1);
            if(bus.isInUse()){
                row[LINE_INDEX] = bus.getLine().getLineName().name();
                row[FULL_INDEX] = String.valueOf(bus.isFull());
                row[STOP_PRESSES_INDEX] = String.valueOf(bus.isStopPressed());
                row[ROUNDS_TO_GAS_STATION_INDEX] = String.valueOf(city.getMAX_ROUNDS_TO_GAS_STATION() - bus.getNumOfStopsPassed());
            }
            else{
                row[LINE_INDEX] = NONE;
                row[FULL_INDEX] = NONE;
                row[STOP_PRESSES_INDEX] = NONE;
                row[ROUNDS_TO_GAS_STATION_INDEX] = String.valueOf(city.getMAX_ROUNDS_TO_GAS_STATION());

            }
            result[bus.getId()] = row;

        }
        return result;
    }

    private String[] createHeaders(){
        String[] result = new String[5];
        result[ID_INDEX] = ID_HEADER;
        result[LINE_INDEX] = LINE_HEADER;
        result[FULL_INDEX] = FULL_HEADER;
        result[STOP_PRESSES_INDEX] = STOP_PRESSED_HEADER;
        result [ROUNDS_TO_GAS_STATION_INDEX] = ROUNDS_TO_GAS_STATION_HEADER;
        return result;

    }

    public void update(int n){
        this.values = createValues(n);
        dtm.fireTableDataChanged();


    }


}
