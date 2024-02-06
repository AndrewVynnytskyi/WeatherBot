package org.example.dtos;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GeocodeDto {

    private ArrayList<Feature> features;



    public ArrayList<ArrayList<String>> toArray2()
    {
        return (ArrayList<ArrayList<String>>) features.stream().map(s ->
                {
                    ArrayList<String> buff = new ArrayList<>();
                    buff.add(s.place_name);
                    buff.add(s.geometry.coordinates.getFirst() + " " + s.geometry.coordinates.getLast());
                    return new ArrayList<>(buff);
                }
        ).collect(Collectors.toList());
    }
}
class Feature{
    public Geometry geometry;
    public String place_name;


}

class Geometry{
    public ArrayList<Double> coordinates;
}



