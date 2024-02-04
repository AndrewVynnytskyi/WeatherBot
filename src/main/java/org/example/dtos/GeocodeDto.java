package org.example.dtos;

import java.util.ArrayList;

public class GeocodeDto {

    private ArrayList<Feature> features;

    public ArrayList<ArrayList<String>> toArray()
    {
        ArrayList<ArrayList<String>> locations = new ArrayList<>();
        for (Feature element : features)
        {
            ArrayList<String> loc = new ArrayList<>();
            loc.add(element.place_name);
            loc.add(element.geometry.coordinates.getFirst() + " " + element.geometry.coordinates.getLast());
            locations.add(loc);
        }
        return locations;
    }
}
class Feature{
    public Geometry geometry;
    public String place_name;


}

class Geometry{
    public ArrayList<Double> coordinates;
}



