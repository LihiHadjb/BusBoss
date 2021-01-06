package Panels.ControlPanel;

import CityComponents.City;
import Panels.ManualMode.ManualModePanel;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static javax.swing.JTable.AUTO_RESIZE_OFF;

public class ControlPanel extends JPanel{
    final String TITLE = "Control Panel";
    City city;

    BussesTableCreator bussesTableCreator;
    LinesTableCreator linesTableCreator;

    JTable bussesTable;
    JTable linesTable;
    ManualModePanel manualModePanel;



    public ControlPanel(City city) {
        this.city = city;
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        createLinesTable();
        createBussesTable();

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY), TITLE, TitledBorder.CENTER, TitledBorder.TOP));

        this.setSize(this.getPreferredSize());


    }



    private void createBussesTable(){
        bussesTableCreator = new BussesTableCreator();
        bussesTable = bussesTableCreator.create(city);

        JScrollPane jScrollPane = new JScrollPane(bussesTable);
        bussesTable.setPreferredScrollableViewportSize(
                new Dimension(
                        bussesTable.getPreferredSize().width,
                        bussesTable.getRowHeight() * 4));
        this.add(jScrollPane);

    }

    private void createLinesTable(){
        linesTableCreator = new LinesTableCreator();
        linesTable = linesTableCreator.create(city);
        //linesTable.setFillsViewportHeight(true);

        JScrollPane jScrollPane = new JScrollPane(linesTable);
        this.add(jScrollPane);
        //jScrollPane.setSize(linesTable.getSize());

    }




    public void updatePanel(){
        linesTableCreator.updateValues(city);
        bussesTableCreator.updateValues(city);
    }


}