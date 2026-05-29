package fa.training.main;

import fa.training.entities.Airport;
import fa.training.entities.FixedWing;
import fa.training.entities.Helicopter;
import fa.training.enums.PlaneType;
import fa.training.services.AirportService;
import fa.training.services.FixedwingService;
import fa.training.services.HelicopterService;
import fa.training.utils.Validator;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import static fa.training.utils.Constants.*;

public class AirplaneManagement {

    private static final AirportService airportService = new AirportService();
    private static final FixedwingService fixedwingService = new FixedwingService();
    private static final HelicopterService helicopterService = new HelicopterService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(){
        String menu = """
                ================= Airplane Management System ================
                Choose one of the following services:
                0.Exit
                1.Airport Management
                2.Airplane Management
                3.Save data
                """;
        System.out.print(menu);
    }

    public static void showAirportMenu(){
        String menu = """
                ================ Airport Services ================
                Choose one of the following services:
                1.Add an airport
                2.Remove an airport
                3.Update an airport
                4.Get status of an airport
                5.Get all status of an airport
                6.Display list of all fixed wings in airport
                7.Display list of all helicopters in airport
                """;
        System.out.print(menu);
    }
    public static void showAirplaneMenu(){
        String menu = """
                ================ Airplane Services ===============
                Choose one of the following services:
                1.Fixed wing
                2.Helicopter
                """;
        System.out.print(menu);
    }
    public static void showAirplaneFunction(){
        String menu = """
                ================ Airplane Functions ===============
                Choose one of the following functions:
                1.Add an airplane
                2.Remove one or more airplanes
                3.Update an airplane
                4.Get info of an airplane
                5.Get info of all airplanes
                """;
        System.out.print(menu);
    }

    public static void showYesNoMenu(){
        String menu = """
                ================ Do you want to continue?===============
                Choose one of the following options:
                0.No
                1.Yes
                """;
        System.out.print(menu);
    }

    public static void main(String[] args) {
        loadData();
        start();
    }

    public static void start(){
        int option;
        do{
            showMenu();
            option = inputValidOption(0,3);
            switch(option){
                case 1 -> handleAirport();
                case 2 -> handleAirplane();
                case 3 -> saveData();
            }
        }while(option != 0);
        saveData();
    }

    public static void handleAirport(){
        showAirportMenu();
        int option = inputValidOption(1,7);
        switch(option){
            case 1 -> createAirport();
            case 2 -> removeAirport();
            case 3 -> updateAirport();
            case 4 -> getStatus();
            case 5 -> getAllAirports();
            case 6 -> displayListOfAllFixedWings();
            case 7 -> displayListOfAllHelicopters();
        }
    }
    public static void handleAirplane(){
        showAirplaneMenu();
        int option = inputValidOption(1,2);
        switch(option){
            case 1 -> handleFixedWing();
            case 2 -> handleHelicopter();
        }
    }
    
    public static void handleFixedWing(){
        showAirplaneFunction();
        int option = inputValidOption(1,5);
        switch(option){
            case 1 -> createFixedWing();
            case 2 -> removeFixedWing();
            case 3 -> updateFixedWing();
            case 4 -> getInfoOfFixedWing();
            case 5 -> getAllFixedWings();
        }
    }
    public static void handleHelicopter(){
        showAirplaneFunction();
        int option = inputValidOption(1,5);
        switch(option){
            case 1 -> createHelicopter();
            case 2 -> removeHelicopter();
            case 3 -> updateHelicopter();
            case 4 -> getInfoOfHelicopter();
            case 5 -> getAllHelicopters();
        }
    }

    private static void saveData(){
        File airportFile = new File(DATA_PATH + AIRPORT_FILE_NAME);
        File fixedwingFile = new File(DATA_PATH + FIXEDWING_FILE_NAME);
        File helicopterFile = new File(DATA_PATH + HELICOPTER_FILE_NAME);
        try(BufferedWriter airportWriter = new BufferedWriter(new FileWriter(airportFile));
            BufferedWriter fixedWingsWriter = new BufferedWriter(new FileWriter(fixedwingFile));
            BufferedWriter helicopterWriter = new BufferedWriter(new FileWriter(helicopterFile))){
            List<Airport> airports = airportService.findAll();
            for(Airport airport : airports){
                airportWriter.write(airport.toString());
                airportWriter.newLine();
                for(String fixedWingId : airport.getFixedWingIds()){
                    FixedWing fixedWing = fixedwingService.findById(fixedWingId);
                    if(fixedWing != null){
                        fixedWingsWriter.write(fixedWing.toString());
                        fixedWingsWriter.write(",");
                        fixedWingsWriter.write(airport.getId());
                        fixedWingsWriter.newLine();
                    }
                }
                for(String helicopterId : airport.getHelicopterIds()){
                    Helicopter helicopter = helicopterService.findById(helicopterId);
                    if(helicopter != null){
                        helicopterWriter.write(helicopter.toString());
                        helicopterWriter.write(",");
                        helicopterWriter.write(airport.getId());
                        helicopterWriter.newLine();
                    }
                }
            }
        }catch (IOException e){
            System.out.println("Error store data because of: " + e.getMessage());
        }
    }

    private static void loadData(){
        File airportFile = new File(DATA_PATH + AIRPORT_FILE_NAME);
        File fixedwingFile = new File(DATA_PATH + FIXEDWING_FILE_NAME);
        File helicopterFile = new File(DATA_PATH + HELICOPTER_FILE_NAME);
        if(airportFile.exists()){
            try(BufferedReader airportReader = new BufferedReader(new FileReader(airportFile))){
                airportReader.lines().forEach(airportService::parse);
            }catch (IOException e){
                System.out.println("Error load airport data because of: " + e.getMessage());
            }
        }
        if(fixedwingFile.exists()){
            try(BufferedReader fixedWingReader = new BufferedReader(new FileReader(fixedwingFile))){
                fixedWingReader.lines().forEach(line -> {
                    fixedwingService.parse(line, airportService);
                });
            }catch (IOException e){
                System.out.println("Error load fixed wing data because of: " + e.getMessage());
            }
        }
        if(helicopterFile.exists()){
            try(BufferedReader helicopterReader = new BufferedReader(new FileReader(helicopterFile))){
                helicopterReader.lines().forEach(line -> {
                    helicopterService.parse(line, airportService);
                });
            }catch (IOException e){
                System.out.println("Error load helicopter data because of: " + e.getMessage());
            }
        }
    }

    private static void createAirport(){
        delimiter();
        System.out.println("Create an airport");
        delimiter();
        String airportId = inputNoneExistAirportId("Enter ID (start with AP), then 5 numbers: ","AP");
        String airportName = getStringInput("Enter airport name: ");
        double runwaySize = inputValidRunwaySize();
        int maxFixedWingParkingPlace = inputValidParkingPlace("Fixed Wing");
        int maxHelicopterParkingPlace = inputValidParkingPlace("Helicopter");
        Airport airport = new Airport(
                airportId,
                airportName,
                runwaySize,
                maxFixedWingParkingPlace,
                maxHelicopterParkingPlace);
        airportService.add(airport);
    }

    private static void removeAirport(){
        delimiter();
        System.out.println("Remove an airport");
        delimiter();
        String airportId = inputValidId("Enter ID (start with AP), then 5 numbers: ","AP");
        Airport airport = airportService.findById(airportId);
        if(airport != null){
            List<String> fixedWingIds = airport.getFixedWingIds();
            for(String fixedWingId : fixedWingIds){
                fixedwingService.delete(fixedWingId);
            }
            List<String> helicopterIds = airport.getHelicopterIds();
            for(String helicopterId : helicopterIds){
                helicopterService.delete(helicopterId);
            }
            airportService.remove(airportId);
        }

    }
    private static void updateAirport(){
        delimiter();
        System.out.println("Update an airport");
        delimiter();
        String airportId = inputValidId("Enter ID (start with AP), then 5 numbers: ","AP");
        if(!airportService.isExists(airportId)){
            System.out.println("Airport does not exist");
        }
        String airportName = getStringInput("Enter airport name to update: ");
        int maxFixedWingParkingPlace = inputValidParkingPlace("Fixed Wing");
        int maxHelicopterParkingPlace = inputValidParkingPlace("Helicopter");
        airportService.update(airportId,airportName,maxFixedWingParkingPlace,maxHelicopterParkingPlace);
    }
    private static void getStatus(){
        delimiter();
        System.out.println("Get status of an airport");
        delimiter();
        String airportId = inputValidId("Enter ID (start with AP), then 5 numbers: ","AP");
        Airport airport = airportService.findById(airportId);
        if(airport != null){
            System.out.println(airport);
        }else{
            System.out.println("Airport not found");
        }
    }
    private static void getAllAirports(){
        delimiter();
        System.out.println("Get all Airports");
        delimiter();
        List<Airport> airports = airportService.findAll();
        if(airports.isEmpty()){
            System.out.println("No Airports found");
            return;
        }
        for(Airport airport : airports){
            System.out.println(airport);
        }
    }

    private static void displayListOfAllFixedWings(){
        delimiter();
        System.out.println("Display list of all fixed wings in airport");
        delimiter();
        List<FixedWing> fixedWings = fixedwingService.findAll();
        for(FixedWing fixedWing : fixedWings){
            System.out.print("Airplane: " + fixedWing + " ");
            for(Airport airport : airportService.findAll()){
                if(airport.getFixedWingIds().contains(fixedWing.getId())){
                    System.out.println("Parked in airport: " + airport.getId() + "-" + airport.getName());
                }
            }
        }
    }

    private static void displayListOfAllHelicopters(){
        delimiter();
        System.out.println("Display list of all helicopters in airport");
        delimiter();
        List<Helicopter> helicopters = helicopterService.findAll();
        for(Helicopter helicopter : helicopters){
            System.out.print("Airplane: " + helicopter + " ");
            for(Airport airport : airportService.findAll()){
                if(airport.getHelicopterIds().contains(helicopter.getId())){
                    System.out.println("Parked in airport: " + airport.getId() + "-" + airport.getName());
                }
            }
        }
    }

    private static void createFixedWing(){
        delimiter();
        System.out.println("Create fixed wings");
        delimiter();
        while(true){
            String airportId = inputValidId("Enter ID of airport that you want to put this plane in: ","AP");
            if(!Validator.isExistId(airportId, airportService.findAllId())){
                System.out.println("Airport not found");
                break;
            }
            Airport airport = airportService.findById(airportId);
            if(airport.getFixedWingIds().size() == airport.getMaxFixedWingParkingPlace()){
                System.out.println("Slot for fixed wing is full");
                break;
            }
            String fixedWingId = inputNoneExistAirplaneId(
                    "Enter ID of fixed wing (start with FW), then 5 numbers: ",
                    "FW",
                    fixedwingService.findAllIds());
            String model = inputModel("Enter model: ");
            double cruiseSpeed = inputValidCruiseSpeed("Enter cruise speed: ");
            double emptyWeight = inputValidEmptyWeight("Enter empty weight: ");
            double takeoffWeight = inputValidTakeoffWeight("Enter takeoff weight: ", "Fixed wing", emptyWeight);
            PlaneType planeType = inputPlaneType("Enter plane type (CAG, LGR, PRV): ");
            double minNeededRunwaySize = inputValidMinNeededRunwaySize("Enter min needed runway size: ", airportService.findById(airportId).getRunwaySize());
            FixedWing fixedWing = new FixedWing(
                    fixedWingId,
                    model,
                    cruiseSpeed,
                    emptyWeight,
                    takeoffWeight,
                    planeType,
                    minNeededRunwaySize
            );
            fixedwingService.add(fixedWing);
            airportService.findById(airportId).getFixedWingIds().add(fixedWingId);

            showYesNoMenu();
            int continueOption = inputValidOption(0,1);
            if(continueOption == 0){
                break;
            }
        }
    }


    private static void removeFixedWing(){
        delimiter();
        System.out.println("Remove fixed wings");
        delimiter();
        while(true){
            String airportId = inputValidId("Enter ID of airport that you want to remove this plane: ","AP");
            if(!Validator.isExistId(airportId, airportService.findAllId())){
                System.out.println("Airport not found");
                break;
            }
            Airport airport = airportService.findById(airportId);
            if(airport.getFixedWingIds().isEmpty()){
                System.out.println("No fixed wing in this airport");
                break;
            }
            String fixedWingId = inputValidId(
                    "Enter ID of fixed wing you want to remove (start with FW), then 5 numbers: ",
                    "FW");
            fixedwingService.delete(fixedWingId);
            airportService.findById(airportId).getFixedWingIds().removeIf(id -> id.equals(fixedWingId));

            showYesNoMenu();
            int continueOption = inputValidOption(0,1);
            if(continueOption == 0){
                break;
            }
        }
    }

    private static void updateFixedWing(){
        delimiter();
        System.out.println("Update fixed wings");
        delimiter();
        String airportId = inputValidId("Enter ID of airport that you want to remove this plane: ","AP");
        if(!Validator.isExistId(airportId, airportService.findAllId())){
            System.out.println("Airport not found");
            return;
        }
        Airport airport = airportService.findById(airportId);
        if(airport.getFixedWingIds().isEmpty()){
            System.out.println("No fixed wing in this airport");
            return;
        }
        String fixedWingId = inputValidId(
                "Enter ID of fixed wing you want to update (start with FW), then 5 numbers: ",
                "FW");
        if(!fixedwingService.isExists(fixedWingId)){
            System.out.println("No fixed wing found");
            return;
        }
        PlaneType planeType = inputPlaneType("Enter plane type (CAG, LGR, PRV) that you want to change: ");
        double minNeededRunwaySize = inputValidMinNeededRunwaySize("Enter min needed runway size that you want to update: ", airport.getRunwaySize());
        fixedwingService.update(fixedWingId, planeType, minNeededRunwaySize);
    }

    private static void getAllFixedWings(){
        delimiter();
        System.out.println("Fixed wings list");
        delimiter();
        List<FixedWing> fixedWings = fixedwingService.findAll();
        if(fixedWings.isEmpty()){
            System.out.println("No fixed wings found");
        }else{
            for(FixedWing fixedWing : fixedWings){
                System.out.println(fixedWing);
            }
        }
    }
    private static void getInfoOfFixedWing(){
        delimiter();
        System.out.println("Info of fixed wing");
        delimiter();
        String fixedWingId = inputValidId("Enter fixed wing ID (start with FW), then 5 numbers: ","FW");
        FixedWing fixedWing = fixedwingService.findById(fixedWingId);
        if(fixedWing != null){
            System.out.println(fixedWing);
        }else{
            System.out.println("Fixed wing not found");
        }
    }

    private static void createHelicopter(){
        delimiter();
        System.out.println("Create helicopters");
        delimiter();
        while(true){
            String airportId = inputValidId("Enter ID of airport that you want to put this plane in: ","AP");
            if(!Validator.isExistId(airportId, airportService.findAllId())){
                System.out.println("Airport not found");
                break;
            }
            Airport airport = airportService.findById(airportId);
            if(airport.getHelicopterIds().size() == airport.getMaxRotatedWingParkingPlace()){
                System.out.println("Slot for helicopter is full");
                break;
            }
            String helicopterId = inputNoneExistAirplaneId(
                    "Enter ID of Helicopter (start with RW), then 5 numbers: ",
                    "RW",
                    helicopterService.findAllIds());
            String model = inputModel("Enter model: ");
            double cruiseSpeed = inputValidCruiseSpeed("Enter cruise speed: ");
            double emptyWeight = inputValidEmptyWeight("Enter empty weight: ");
            double takeoffWeight = inputValidTakeoffWeight("Enter takeoff weight: ", "Helicopter", emptyWeight);
            double range = inputValidRange("Enter range: ");
            Helicopter helicopter = new Helicopter(
                    helicopterId,
                    model,
                    cruiseSpeed,
                    emptyWeight,
                    takeoffWeight,
                    range
            );
            helicopterService.add(helicopter);
            airportService.findById(airportId).getHelicopterIds().add(helicopterId);

            showYesNoMenu();
            int continueOption = inputValidOption(0,1);
            if(continueOption == 0){
                break;
            }
        }
    }

    private static void removeHelicopter(){
        delimiter();
        System.out.println("Remove helicopters");
        delimiter();
        while(true){
            String airportId = inputValidId("Enter ID of airport that you want to remove this plane: ","AP");
            if(!Validator.isExistId(airportId, airportService.findAllId())){
                System.out.println("Airport not found");
                break;
            }
            Airport airport = airportService.findById(airportId);
            if(airport.getHelicopterIds().isEmpty()){
                System.out.println("No helicopters in this airport");
                break;
            }
            String helicopterId = inputValidId(
                    "Enter ID of helicopter you want to remove (start with RW), then 5 numbers: ",
                    "RW");
            helicopterService.delete(helicopterId);
            airportService.findById(airportId).getHelicopterIds().removeIf(id -> id.equals(helicopterId));

            showYesNoMenu();
            int continueOption = inputValidOption(0,1);
            if(continueOption == 0){
                break;
            }
        }
    }

    private static void updateHelicopter(){
        delimiter();
        System.out.println("Update helicopter");
        delimiter();
        String helicopterId = inputValidId(
                "Enter ID of helicopter you want to update (start with RW), then 5 numbers: ",
                "RW");
        if(!helicopterService.isExists(helicopterId)){
            System.out.println("Helicopter not found");
            return;
        }
        double range = inputValidRange("Enter range: ");
        helicopterService.update(helicopterId, range);
    }

    private static void getAllHelicopters(){
        delimiter();
        System.out.println("Helicopters list");
        delimiter();
        List<Helicopter> helicopters = helicopterService.findAll();
        if(helicopters.isEmpty()){
            System.out.println("No helicopters found");
        }else{
            for(Helicopter helicopter : helicopters){
                System.out.println(helicopter);
            }
        }
    }
    private static void getInfoOfHelicopter(){
        delimiter();
        System.out.println("Info of helicopter");
        delimiter();
        String helicopterId = inputValidId("Enter helicopter ID (start with RW), then 5 numbers: ","RW");
        Helicopter helicopter = helicopterService.findById(helicopterId);
        if(helicopter != null){
            System.out.println(helicopter);
        }else{
            System.out.println("Fixed wing not found");
        }
    }


    private static void delimiter(){
        System.out.println("=====================================================");
    }

    private static String inputNoneExistAirportId(String prompt, String prefix){
        while (true){
            String airportId = inputValidId(prompt,prefix);
            if(!Validator.isExistId(airportId, airportService.findAllId())){
                return airportId;
            }
            System.out.println("Airport has existed. Please enter another id");
        }
    }

    private static String inputValidId(String prompt, String prefix){
        while(true){
            String input = getStringInput(prompt);
            if(Validator.isValidId(input, prefix)){
                return input;
            }
            System.out.println("Invalid ID. Please try again.");
        }
    }
    private static double inputValidRunwaySize(){
        while (true){
            double runwaySize = getDoubleInput("Enter runway size: ");
            if(runwaySize > 0){
                return runwaySize;
            }
            System.out.println("Runway size must be positive. Please try again.");
        }
    }
    private static int inputValidParkingPlace(String airplaneType){
        while (true){
            int parkingPlace = getIntInput("Enter number of parking place for " + airplaneType + ": ");
            if(parkingPlace > 0){
                return parkingPlace;
            }
            System.out.println("Parking place must be positive. Please try again.");
        }
    }

    private static String inputNoneExistAirplaneId(String prompt, String prefix, List<String> airplaneIds){
        while (true){
            String airplaneId = inputValidId(prompt,prefix);
            if(!Validator.isExistId(airplaneId, airplaneIds)){
                return airplaneId;
            }
            System.out.println("This airplane has existed. Please enter another id");
        }
    }

    private static String inputModel(String prompt){
        while (true){
            String model = getStringInput(prompt);
            if(Validator.isValidModel(model)){
                return model;
            }
            System.out.println("Model name must between 1 and 40 characters. Please try again.");
        }
    }

    private static double inputValidCruiseSpeed(String prompt){
        while (true){
            double cruiseSpeed = getDoubleInput(prompt);
            if(Validator.isPositiveDoubleInput(cruiseSpeed)){
                return cruiseSpeed;
            }
            System.out.println("Cruise speed must be positive. Please try again.");
        }
    }

    private static double inputValidEmptyWeight(String prompt){
        while (true){
            double emptyWeight = getDoubleInput(prompt);
            if(Validator.isPositiveDoubleInput(emptyWeight)){
                return emptyWeight;
            }
            System.out.println("Empty weight must be positive. Please try again.");
        }
    }

    private static double inputValidTakeoffWeight(String prompt, String airplaneType, double emptyWeight){
        while (true){
            double takeoffWeight = getDoubleInput(prompt);
            if(Validator.isValidTakeoffWeight(takeoffWeight,emptyWeight, airplaneType)){
                return takeoffWeight;
            }
            System.out.println("Takeoff weight must greater than or equal to empty weight and must be less than 1.5 times of empty weight for helicopter. Please try again.");
        }
    }

    private static PlaneType inputPlaneType(String prompt){
        while (true){
            String planeType = getStringInput(prompt);
            if(Validator.isValidPlaneType(planeType)){
                return PlaneType.valueOf(planeType.trim().toUpperCase());
            }
            System.out.println("Invalid plane type. Please try again.");
        }
    }

    private static double inputValidMinNeededRunwaySize(String prompt, double airportRunwaySize){
        while (true){
            double minNeededRunwaySize = getDoubleInput(prompt);
            if(Validator.isPositiveDoubleInput(minNeededRunwaySize) && Validator.isValidMinNeededRunwaySize(minNeededRunwaySize, airportRunwaySize)){
                return minNeededRunwaySize;
            }
            System.out.println("Invalid min needed runway size. Please try again.");
        }
    }

    private static double inputValidRange(String prompt){
        while (true){
            double range = getDoubleInput(prompt);
            if(Validator.isPositiveDoubleInput(range)){
                return range;
            }
            System.out.println("Range must be positive. Please try again.");
        }
    }


    private static int inputValidOption(int min, int max){
        while(true){
            int option = getIntInput("Please input an option from " + min + " to " + max + ": ");
            if(option < min || option > max){
                System.out.println("Invalid option. Please provide a number between " + min + " and " + max);
            }else{
                return option;
            }
        }
    }

    private static String getStringInput(String prompt) {
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine();
            if(Validator.isBlank(input)) {
                System.out.println("Don't let the input blank");
            }else{
                return input.trim();
            }
        }
    }
    private static int getIntInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter an integer");
            }
        }
    }
    private static double getDoubleInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter a double number (Ex: 2.2)");
            }
        }
    }
}
