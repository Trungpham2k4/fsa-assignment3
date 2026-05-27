package fa.training.entities;

public class Helicopter extends Airplane{
    double range;

    public Helicopter(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight, double range) {
        super(id,model,cruiseSpeed,emptyWeight,maxTakeoffWeight);
        this.range = range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public String flyMethod(){
        return "rotated wing";
    }

    @Override
    public String toString() {
        return "Helicopter{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", cruiseSpeed=" + cruiseSpeed +
                ", emptyWeight=" + emptyWeight +
                ", maxTakeoffWeight=" + maxTakeoffWeight +
                ", range=" + range +
                ", flyMethod='" + flyMethod() + '\'' +
                '}';
    }
}
