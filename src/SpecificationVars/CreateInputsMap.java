package SpecificationVars;

import CityComponents.City;

import java.util.HashMap;

public class CreateInputsMap {
    City city;
    HashMap<String, String> inputs;

    public CreateInputsMap(City city){
        this.city = city;
    }

    public HashMap<String, String> create(){
        inputs = new HashMap<>();
        return inputs;


    }

}
