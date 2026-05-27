package fa.training.utils;

import fa.training.enums.PlaneType;

import java.util.List;

public class Validator {
    public static boolean isBlank(String str) {
        return str.trim().isEmpty();
    }
    public static boolean isValidId(String id, String prefix) {
        if(id.startsWith(prefix)){
            if(id.length() == 7){
                boolean valid = true;
                for(int i = 2; i <= 6; i++){
                    if(!Character.isDigit(id.charAt(i))){
                        valid = false;
                        break;
                    }
                }
                return valid;
            }
        }
        return false;
    }

    public static boolean isExistId(String id, List<String> ids) {
        return ids.contains(id);
    }
    public static boolean isValidModel(String model) {
        return !isBlank(model) && model.trim().length() <= 40;
    }

    public static boolean isPositiveDoubleInput(double input) {
        return input > 0;
    }

    public static boolean isValidTakeoffWeight(double takeoffWeight, double emptyWeight, String airplaneType) {
        if(isPositiveDoubleInput(takeoffWeight)){
            if(takeoffWeight >= emptyWeight){
                if(airplaneType.equals("Helicopter")){
                    return takeoffWeight <= emptyWeight * 1.5;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPlaneType(String planeType) {
        if (isBlank(planeType)) return false;
        try{
            PlaneType.valueOf(planeType.trim().toUpperCase());
        }catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public static boolean isValidMinNeededRunwaySize(double fixedWingRunwaySize, double airportRunwaySize) {
        return fixedWingRunwaySize <= airportRunwaySize;
    }

}
