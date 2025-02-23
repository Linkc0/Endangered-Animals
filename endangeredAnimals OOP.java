import java.util.Scanner;

class EndangeredAnimals {
    final int MAX_ANIMALS = 500;
    private String[] commonNames = new String[MAX_ANIMALS];
    private String[] latinNames = new String[MAX_ANIMALS];
    private int[] status = new int[MAX_ANIMALS];
    private int animalCounter = 0;

    public int getMaxAnimals() {
        return MAX_ANIMALS;
    }

    public String getCommonName(int index) {
        return commonNames[index];
    }

    public String getLatinName(int index) {
        return latinNames[index];
    }

    public int getStatus(int index) {
        return status[index];
    }

    public int getAnimalCounter() {
        return animalCounter;
    }

    public void setCommonName(int index, String name) {
        commonNames[index] = name;
    }

    public void setLatinName(int index, String latinName) {
        latinNames[index] = latinName;
    }

    public void setStatus(int index, int statusValue) {
        status[index] = statusValue;
    }

    public void setAnimalCounter(int counter) {
        animalCounter = counter;
    }
}//END EndangeredAnimals

class EndangeredAnimalsManager {
    public static void main(String[] args) {
        EndangeredAnimals endangeredAnimals = new EndangeredAnimals();
        inputAnimalDetails(endangeredAnimals);
        menu(endangeredAnimals);
    }//END main

    public static String inputs(String message) {
        String userInput;
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        userInput = scanner.nextLine();
        return userInput;
    }//END inputs

    public static void print(String message) {
        System.out.println(message);
    }//END print

    public static void addAnimal(EndangeredAnimals endangeredAnimals, String commonName, String latinName, int status) {
        int counter = endangeredAnimals.getAnimalCounter();
        if (counter < endangeredAnimals.getMaxAnimals()) {
            endangeredAnimals.setCommonName(counter, commonName);
            endangeredAnimals.setLatinName(counter, latinName);
            endangeredAnimals.setStatus(counter, status);
            endangeredAnimals.setAnimalCounter(counter + 1);
        } else {
            print("Maximum number of animals recorded.");
        }
    }//END addAnimal

    public static void updateAnimalStatus(EndangeredAnimals endangeredAnimals) {
        int numOfRecordedAnimals = endangeredAnimals.getAnimalCounter();
        String commonName = inputs("Enter the common name of the animal to update: ");
        boolean isFound = false;

        for (int animalIndex = 0; animalIndex < numOfRecordedAnimals && !isFound; animalIndex++) {
            if (endangeredAnimals.getCommonName(animalIndex).equalsIgnoreCase(commonName)) {
                String status = inputs("Enter the status for this animal (0 for Critically Endangered, 1 for Endangered, 2 for Vulnerable, 3 for Least Concern): ");
                while (!isValidStatus(status)) {
                    status = inputs("Invalid input. Please enter 0, 1, 2, or 3: ");
                }
                endangeredAnimals.setStatus(animalIndex, Integer.parseInt(status));
                print("Updated " + commonName + " status to: " + getStatusClassification(Integer.parseInt(status)));
                isFound = true;
            }
        }

        if (!isFound) {
            print("Animal not found: " + commonName);
        }
    }//END updateAnimalStatus

    public static boolean isValidStatus(String status) {
        return status.matches("[0-3]");
    }//END isValidStatus

    public static void printAllAnimals(EndangeredAnimals endangeredAnimals) {
        int counter = endangeredAnimals.getAnimalCounter();

        if (counter == 0) {
            print("No animals recorded.");
            return;
        }

        print("Animal details:");
        for (int animalIndex = 0; animalIndex < counter; animalIndex++) {
            String classification = getStatusClassification(endangeredAnimals.getStatus(animalIndex));
            print((animalIndex + 1) + ". " + endangeredAnimals.getCommonName(animalIndex) + " (" + endangeredAnimals.getLatinName(animalIndex) + ") : " + classification);
        }
    }//END printAllAnimals

    public static String getStatusClassification(int status) {
        switch (status) {
            case 0: return "Critically Endangered";
            case 1: return "Endangered";
            case 2: return "Vulnerable";
            case 3: return "Least Concern";
            default: return "Unknown";
        }
    }//END getStatusClassification

    public static void inputAnimalDetails(EndangeredAnimals endangeredAnimals) {
        final String END = "XXX";
        int count = 0;
        String commonName = inputs("Enter the common name of the animal (or enter 'XXX' to stop): ");

        while (!(commonName.equals(END)) && (count < endangeredAnimals.getMaxAnimals())) {
            String latinName = inputs("Enter the Latin name of " + commonName + ": ");
            String status = inputs("Enter the endangered status for " + commonName + " (0 for Critically Endangered, 1 for Endangered, 2 for Vulnerable, 3 for Least Concern): ");

            while (!isValidStatus(status)) {
                status = inputs("Invalid input. Please enter 0, 1, 2, or 3: ");
            }
            addAnimal(endangeredAnimals, commonName, latinName, Integer.parseInt(status));

            commonName = inputs("Enter next animal common name (or enter 'XXX' to finish): ");
        }
    }//END inputAnimalDetails

    public static void findAnimal(EndangeredAnimals endangeredAnimals) {
        int numOfRecordedAnimals = endangeredAnimals.getAnimalCounter();
        String commonName = inputs("Enter the common name of the animal to find: ");
        boolean isFound = false;

        for (int animalIndex = 0; animalIndex < numOfRecordedAnimals && !isFound; animalIndex++) {
            if (endangeredAnimals.getCommonName(animalIndex).equalsIgnoreCase(commonName)) {
                String classification = getStatusClassification(endangeredAnimals.getStatus(animalIndex));
                print("Found " + commonName + " (" + endangeredAnimals.getLatinName(animalIndex) + "): " + classification);
                isFound = true;
            }
        }

        if (!isFound) {
            print("Animal not found: " + commonName);
        }
    }//END findAnimal

    public static void menu(EndangeredAnimals endangeredAnimals) {
        boolean exit = false;

        while (!exit) {
            String input = inputs("Enter: (E)xit, (U)pdate animal status, (F)ind animal, (P)rint all animals").toUpperCase();

            if (input.equals("E")) {
                exit = true;
            } else if (input.equals("U")) {
                updateAnimalStatus(endangeredAnimals);
            } else if (input.equals("F")) {
                findAnimal(endangeredAnimals);
            } else if (input.equals("P")) {
                printAllAnimals(endangeredAnimals);
            } else {
                print("Invalid input. Please enter 'E', 'U', 'F', or 'P'.");
            }
        }
    }//END menu
}//END EndangeredAnimalsManager
