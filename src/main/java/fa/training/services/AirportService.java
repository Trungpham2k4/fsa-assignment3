package fa.training.services;

import fa.training.entities.Airport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AirportService {

    private final List<Airport> airports;
    public AirportService() {
        airports = new ArrayList<>();
    }
    public List<Airport> findAll(){
        airports.sort(Comparator.comparing(Airport::getId));
        return airports;
    }
    public Airport findById(String id){
        for (Airport airport : airports) {
            if(airport.getId().equals(id)){
                return airport;
            }
        }
        return null;
    }
    public void add(Airport airport){
        airports.add(airport);
    }
    public void update(String id, String name, int maxFixedWingCapacity, int maxHelicopterCapacity){
        Airport airport = findById(id);
        if(airport != null){
            airport.setName(name);
            airport.setMaxFixedWingParkingPlace(maxFixedWingCapacity);
            airport.setMaxRotatedWingParkingPlace(maxHelicopterCapacity);
        }
    }
    public void remove(String id){
        airports.removeIf(airport -> airport.getId().equals(id));
    }
    public List<String> findAllId(){
        return airports.stream().map(Airport::getId).collect(Collectors.toList());
    }
    public boolean isExists(String fixedWingId) {
        return airports.stream().anyMatch(airport -> airport.getId().equals(fixedWingId));
    }

    public void parse(String airportString){
        String[] airportAttr = airportString.split(",");
        if(airportAttr.length == 5){
            Airport airport = new Airport(
                    airportAttr[0],
                    airportAttr[1],
                    Double.parseDouble(airportAttr[2]),
                    Integer.parseInt(airportAttr[3]),
                    Integer.parseInt(airportAttr[4])
            );
            airports.add(airport);
        }
    }
}
