package fa.training.entities;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String id;
    private String name;
    private double runwaySize;
    private int maxFixedWingParkingPlace;
    private int maxRotatedWingParkingPlace;
    private List<String> fixedWingIds;
    private List<String> helicopterIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxFixedWingParkingPlace() {
        return maxFixedWingParkingPlace;
    }

    public void setMaxFixedWingParkingPlace(int maxFixedWingParkingPlace) {
        this.maxFixedWingParkingPlace = Math.max(fixedWingIds.size(), maxFixedWingParkingPlace);
    }

    public double getRunwaySize() {
        return runwaySize;
    }

    public void setRunwaySize(double runwaySize) {
        this.runwaySize = runwaySize;
    }

    public int getMaxRotatedWingParkingPlace() {
        return maxRotatedWingParkingPlace;
    }

    public void setMaxRotatedWingParkingPlace(int maxRotatedWingParkingPlace) {
        this.maxRotatedWingParkingPlace = Math.max(helicopterIds.size(), maxRotatedWingParkingPlace);
    }

    public List<String> getFixedWingIds() {
        return fixedWingIds;
    }

    public void setFixedWingIds(List<String> fixedWingIds) {
        this.fixedWingIds = fixedWingIds;
    }

    public List<String> getHelicopterIds() {
        return helicopterIds;
    }

    public void setHelicopterIds(List<String> helicopterIds) {
        this.helicopterIds = helicopterIds;
    }
    public Airport() {}
    public Airport(String id, String name, double runwaySize, int maxFixedWingParkingPlace, int maxRotatedWingParkingPlace) {
        this.id = id;
        this.name = name;
        this.runwaySize = runwaySize;
        this.maxFixedWingParkingPlace = maxFixedWingParkingPlace;
        this.maxRotatedWingParkingPlace = maxRotatedWingParkingPlace;
        this.fixedWingIds = new ArrayList<>();
        this.helicopterIds = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", runwaySize=" + runwaySize +
                ", maxFixedWingParkingPlace=" + maxFixedWingParkingPlace +
                ", maxRotatedWingParkingPlace=" + maxRotatedWingParkingPlace +
                '}';
    }
}
