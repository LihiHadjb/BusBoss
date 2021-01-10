package Panels;

import CityComponents.City;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ControlPanel extends JPanel{
    final String TITLE = "Control Panel";
    City city;

    JTable bussesTable;
    JTable linesTable;

    BussesTableCreator bussesTableCreator;
    LinesTableCreator linesTableCreator;





    public ControlPanel(City city) {
        this.city = city;
        this.setLayout(new BorderLayout());
        createBussesTable();
        createLinesTable();
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), TITLE, TitledBorder.CENTER, TitledBorder.TOP));
    }

    private void createBussesTable(){
        bussesTableCreator = new BussesTableCreator();
        bussesTable = bussesTableCreator.create(city);
        this.add(new JScrollPane(bussesTable),  BorderLayout.SOUTH);

    }

    private void createLinesTable(){
        linesTableCreator = new LinesTableCreator();
        linesTable = linesTableCreator.create(city);
        this.add(new JScrollPane(linesTable),  BorderLayout.CENTER);

    }



    public void update(){
        linesTableCreator.updateValues(city);
        bussesTableCreator.updateValues(city);


    }


}