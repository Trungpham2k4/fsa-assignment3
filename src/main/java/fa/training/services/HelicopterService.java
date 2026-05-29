package fa.training.services;

import fa.training.entities.Airport;
import fa.training.entities.Helicopter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelicopterService {
    private final List<Helicopter> helicopters;
    public HelicopterService() {
        helicopters = new ArrayList<>();
    }
    public void add(Helicopter helicopter) {
        helicopters.add(helicopter);
    }
    public List<String> findAllIds(){
        return helicopters.stream().map(Helicopter::getId).collect(Collectors.toList());
    }
    public List<Helicopter> findAll(){
        return helicopters;
    }
    public Helicopter findById(String id) {
        for (Helicopter helicopter : helicopters) {
            if(helicopter.getId().equals(id)) {
                return helicopter;
            }
        }
        return null;
    }
    public void delete(String id) {
        helicopters.removeIf(helicopter -> helicopter.getId().equals(id));
    }
    public void update(String id, double range) {
        Helicopter helicopter = findById(id);
        if(helicopter != null) {
            helicopter.setRange(range);
        }
    }
    public boolean isExists(String fixedWingId) {
        return helicopters.stream().anyMatch(helicopter -> helicopter.getId().equals(fixedWingId));
    }

    public void parse(String line, AirportService airportService) {
        String[] parts = line.split(",");
        if(parts.length == 7) {
            Airport airport = airportService.findById(parts[6]);
            if(airport != null) {
                Helicopter helicopter = new Helicopter(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]));
                airport.getHelicopterIds().add(helicopter.getId());
                helicopters.add(helicopter);
            }
        }
    }
}
