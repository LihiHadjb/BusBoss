package UI.Panels.ControlPanel;

import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

//Create and update the top section of the right panel. This panel comprises 2 sections:
//1. Busses - the state of each bus
//2. Lines - the state of each line

public class ControlPanel extends JPanel{
    final String TITLE = "Control Panel";
    City city;

    BussesTableCreator bussesTableCreator;
    LinesTableCreator linesTableCreator;

    JTable bussesTable;
    JTable linesTable;

    public ControlPanel(City city) {
        this.city = city;
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        createBussesTable();
        createLinesTable();

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY), TITLE, TitledBorder.CENTER, TitledBorder.TOP));

        this.setSize(this.getPreferredSize());


    }

    private void createBussesTable(){
        bussesTableCreator = new BussesTableCreator();
        bussesTable = bussesTableCreator.create(city);

        JScrollPane jScrollPane = new JScrollPane(bussesTable);
        this.add(jScrollPane);

    }

    private void createLinesTable(){
        linesTableCreator = new LinesTableCreator();
        linesTable = linesTableCreator.create(city);

        JScrollPane jScrollPane = new JScrollPane(linesTable);
        this.add(jScrollPane);
    }

    //Update the information displayed in all the sub-panels according to the new state of the city
    public void updatePanel(){
        linesTableCreator.updateValues(city);
        bussesTableCreator.updateValues(city);
    }
}