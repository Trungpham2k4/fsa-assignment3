package fa.training.entities;

public abstract class Airplane {
    protected String id;
    protected String model;
    protected double cruiseSpeed;
    protected double emptyWeight;
    protected double maxTakeoffWeight;

    public Airplane(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight) {
        this.id = id;
        this.model = model;
        this.cruiseSpeed = cruiseSpeed;
        this.emptyWeight = emptyWeight;
        this.maxTakeoffWeight = maxTakeoffWeight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(double cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public double getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(double emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public double getMaxTakeoffWeight() {
        return maxTakeoffWeight;
    }

    public void setMaxTakeoffWeight(double maxTakeoffWeight) {
        this.maxTakeoffWeight = maxTakeoffWeight;
    }

    public abstract String flyMethod();

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",")
                .append(model).append(",")
                .append(cruiseSpeed).append(",")
                .append(emptyWeight).append(",")
                .append(maxTakeoffWeight);
        return stringBuilder.toString();
    }
}
