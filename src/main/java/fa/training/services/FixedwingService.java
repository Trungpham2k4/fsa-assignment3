package fa.training.services;

import fa.training.entities.Airport;
import fa.training.entities.FixedWing;
import fa.training.enums.PlaneType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FixedwingService {
    private final List<FixedWing> fixedWings;
    public FixedwingService() {
        fixedWings = new ArrayList<>();
    }
    public void add(FixedWing fixedWing) {
        fixedWings.add(fixedWing);
    }
    public List<FixedWing> findAll() {
        return fixedWings;
    }
    public FixedWing findById(String id) {
        for (FixedWing fixedWing : fixedWings) {
            if(fixedWing.getId().equals(id)){
                return fixedWing;
            }
        }
        return null;
    }
    public void delete(String id) {
        fixedWings.removeIf(fixedWing -> fixedWing.getId().equals(id));
    }
    public void update(String id, PlaneType planeType, double minNeededRunwaySize) {
        FixedWing fixedWing = findById(id);
        fixedWing.setPlaneType(planeType);
        fixedWing.setMinNeededRunwaySize(minNeededRunwaySize);
    }
    public List<String> findAllIds() {
        return fixedWings.stream().map(FixedWing::getId).collect(Collectors.toList());
    }
    public boolean isExists(String fixedWingId) {
        return fixedWings.stream().anyMatch(fixedWing -> fixedWing.getId().equals(fixedWingId));
    }

    public void parse(String line, AirportService airportService) {
        String[] parts = line.split(",");
        if(parts.length == 8) {
            Airport airport = airportService.findById(parts[7]);
            if(airport != null) {
                FixedWing fixedWing = new FixedWing(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        PlaneType.valueOf(parts[5]),
                        Double.parseDouble(parts[6])
                );
                airport.getFixedWingIds().add(fixedWing.getId());
                fixedWings.add(fixedWing);
            }
        }
    }
}
