package Panels.ManualMode;

import CityComponents.City;
import CityComponents.Station;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;

public class StationsCheckBoxesPanel extends JPanel {
    JCheckBox northStation;
    JCheckBox southStation;
    JCheckBox eastStation;
    JCheckBox westStation;
    JCheckBox mainStation;
    City city;
    HashMap<JCheckBox, String> checkbox2StationName;

    String TITLE = "Passengers";
    public StationsCheckBoxesPanel(City city){
        this.city = city;
        this.checkbox2StationName = new HashMap<>();
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), TITLE, TitledBorder.CENTER, TitledBorder.TOP));

//TODO: north and south wrong!!!
        northStation = new JCheckBox("north");
        southStation = new JCheckBox("south");
        eastStation = new JCheckBox("east");
        westStation = new JCheckBox("west");
        mainStation = new JCheckBox("central station");

        checkbox2StationName.put(southStation, "b2");
        checkbox2StationName.put(northStation, "b1");
        checkbox2StationName.put(westStation, "a1");
        checkbox2StationName.put(eastStation, "a2");
        checkbox2StationName.put(mainStation, "main_station");

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);

        setEnabled(false);

        for(JCheckBox checkBox : checkbox2StationName.keySet()){
            this.add(checkBox);
        }
    }

    public void setEnabledAllStationsWithPeople(){
        Station station;
        for(JCheckBox checkBox : checkbox2StationName.keySet()){
            String stationName = checkbox2StationName.get(checkBox);
            if(stationName.equals("main_station")){
                station = city.getMainStation();
            }
            else{
                station = city.getBusStations().get(stationName);
            }

            if(checkBox.isSelected() && !city.existsBusThatStopsAtStation(station)){
                checkBox.setEnabled(false);
            }
            else if(city.existsBusThatStopsAtStation(station)){
                checkBox.setEnabled(true);
                checkBox.setSelected(false);
            }
        }




    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        for(JCheckBox checkBox : checkbox2StationName.keySet()){
            checkBox.setEnabled(enabled);
        }

    }

    public boolean isChecked(String stationName){
        switch (stationName){
            case("a1"):
                return westStation.isSelected();
            case("a2"):
               return eastStation.isSelected();
            case("b1"):
                return northStation.isSelected();
            case("b2"):
                return southStation.isSelected();
            case("main_station"):
                return mainStation.isSelected();
        }
        return false;
    }
}
