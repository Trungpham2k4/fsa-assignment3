package fa.training.entities;

import fa.training.enums.PlaneType;

public class FixedWing extends Airplane{
    private PlaneType planeType;
    private double minNeededRunwaySize;

    public FixedWing(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight, PlaneType planeType, double minNeededRunwaySize) {
        super(id,model,cruiseSpeed,emptyWeight,maxTakeoffWeight);
        this.planeType = planeType;
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    public void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    public void setMinNeededRunwaySize(double minNeededRunwaySize) {
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    @Override
    public String flyMethod(){
        return "fixed wing";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(",")
                .append(planeType).append(",")
                .append(minNeededRunwaySize);
        return sb.toString();
    }
}
