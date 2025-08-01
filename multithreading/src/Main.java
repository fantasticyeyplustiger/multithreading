import java.util.*;

/**
    Intention:
    Demonstrate ability to make and use single and multithreaded code

    Summary:
    Given a specific amount of resources and space, calculate the maximum amount of power
    that can be achieved with fuel generators: then, have all the fuel generators burn
    their type of fuel accordingly. Then, print results.

    As a side note, a bunch of fibonacci calculations are run every time it attempts
    to burn fuel. This is just to increase work overload.

    Single threaded demo:
    One thread burns all the fuel in every single generator.

    Multithreaded demo:
    Four threads burn the fuel in each type of generator accordingly.
 */

public class Main {

    static HashMap<String, Double> resources = new HashMap<>();

    static int maxUnitsOfSpace;

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();

        Random random = new Random();

        //region Initialize resources
        /*
        OLD CODE for randomizing resources
        resources.put("Biomass", random.nextDouble(50.0, 3000000.0));
        resources.put("Water", random.nextDouble(50.0, 3000000.0));
        resources.put("Coal", random.nextDouble(0.0, 1000000.0));
        resources.put("Fuel", random.nextDouble(0.0, 1000000.0));
        resources.put("Uranium", random.nextDouble(0.0, 500000.0));

        maxUnitsOfSpace = random.nextInt(499999, 500000);
         */

        resources.put("Biomass", 3000000.0);
        resources.put("Water", 300000.0);
        resources.put("Coal", 10000.0);
        resources.put("Fuel", 10000.0);
        resources.put("Uranium", 5000.0);

        maxUnitsOfSpace = 75000;

        int overclockItems = random.nextInt(10, 50);

        // Assign static variables
        NuclearGenerator.totalFuelAvailable = resources.get("Uranium");
        NuclearGenerator.otherTotalFuelAvailable = resources.get("Water");

        FuelGenerator.totalFuelAvailable = resources.get("Fuel");

        CoalGenerator.totalFuelAvailable = resources.get("Coal");
        CoalGenerator.otherTotalFuelAvailable = resources.get("Water");

        BiomassGenerator.totalFuelAvailable = resources.get("Biomass");

        // Print all the resources in a readable format
        System.out.println("RESOURCES:\n");

        for (String resource : resources.keySet()) {
            System.out.printf("%s: %f\n", resource, resources.get(resource));
        }

        System.out.printf("Max units of space: %d\n", maxUnitsOfSpace);
        System.out.printf("Overclock items: %d\n", overclockItems);
        System.out.println();
        //endregion

        //region Initialize generators
        // Higher power generation = Higher priority
        int numOfNuclearGens = getTwoResourceGenerators(new NuclearGenerator(), resources.get("Biomass"), resources.get("Water"));
        int numOfFuelGens = getOneResourceGenerators(new FuelGenerator(), resources.get("Fuel"));
        int numOfCoalGens = getTwoResourceGenerators(new CoalGenerator(), resources.get("Coal"), resources.get("Water"));
        int numOfBiomassGens = getOneResourceGenerators(new BiomassGenerator(), resources.get("Biomass"));

        NuclearGenerator[] nuclearGenerators = NuclearGenerator.getGenerators(numOfNuclearGens);
        FuelGenerator[] fuelGenerators = FuelGenerator.getGenerators(numOfFuelGens);
        CoalGenerator[] coalGenerators = CoalGenerator.getGenerators(numOfCoalGens);
        BiomassGenerator[] biomassGenerators = BiomassGenerator.getGenerators(numOfBiomassGens);

        LinkedList<PowerGenerator> allGeneratorsList = new LinkedList<>();
        PowerGenerator[] allGenerators = new PowerGenerator[numOfBiomassGens + numOfCoalGens + numOfFuelGens + numOfNuclearGens];

        addArrayToGeneratorList(allGeneratorsList, nuclearGenerators);
        addArrayToGeneratorList(allGeneratorsList, fuelGenerators);
        addArrayToGeneratorList(allGeneratorsList, coalGenerators);
        addArrayToGeneratorList(allGeneratorsList, biomassGenerators);

        for (int i = 0; i < allGeneratorsList.size(); i++) {
            allGenerators[i] = allGeneratorsList.get(i);
        }
        //endregion

        System.out.print("Start up time: ");
        stopwatch.stop();
        stopwatch.printElapsedMilliseconds();

        System.out.println("\nMultithreaded Demo [1] or Single thread Demo [2]");

        String input = "0";

        while(!input.equals("1") && !input.equals("2")){
            System.out.println("Type 1 or 2!");
            input = scan.nextLine();
        }

        System.out.println("\nNote: This will take a few seconds");

        switch (input){
            case "1" -> {
                Thread threadOne = new Thread(new GeneratorThread(nuclearGenerators));
                Thread threadTwo = new Thread(new GeneratorThread(fuelGenerators));
                Thread threadThree = new Thread(new GeneratorThread(coalGenerators));
                Thread threadFour = new Thread(new GeneratorThread(biomassGenerators));

                threadOne.start(); threadTwo.start(); threadThree.start(); threadFour.start();
            }
            // This thread has the work of the four above combined.
            // Used to see the difference in time between single threaded and multithreaded.
            case "2" -> {
                Thread allGensThread = new Thread(new GeneratorThread(allGenerators));

                allGensThread.start();}
        }

        scan.close();
    }

    /**
     * Calculates the amount of OneResourceGenerators you can have with the given space and resources.
     * @param generatorType The type of generator, i.e. BiomassGenerator or FuelGenerator.
     * @param resource The amount of resource the generator uses.
     */
    public static int getOneResourceGenerators(OneResourceGenerator generatorType, double resource){
        int generatorSpace = generatorType.unitsOfSpace;
        int maxUsage = getMaxUsageForResource(resource, generatorType.fuelUsagePerBurnTime);

        int maxPotentialGeneratorsSpace = maxUsage * generatorSpace;

        while (maxUnitsOfSpace < maxPotentialGeneratorsSpace){
            maxUsage--;
            maxPotentialGeneratorsSpace -= generatorSpace;
        }

        maxUnitsOfSpace -= maxPotentialGeneratorsSpace;

        return maxUsage;
    }

    /**
     * Calculates the amount of TwoResourceGenerators you can have with the given space and resources.
     * @param generatorType The type of generator, i.e. CoalGenerator or NuclearGenerator.
     * @param resourceOne The first amount of resource the generator uses.
     * @param resourceTwo The second amount of resource the generator uses.
     */
    public static int getTwoResourceGenerators(TwoResourceGenerator generatorType, double resourceOne, double resourceTwo){
        int generatorSpace = generatorType.unitsOfSpace;

        int maxUsageOne = getMaxUsageForResource(resourceOne, generatorType.fuelUsagePerBurnTime);
        int maxUsageTwo = getMaxUsageForResource(resourceTwo, generatorType.otherFuelUsagePerBurnTime);

        int maxUsage = Math.min(maxUsageOne, maxUsageTwo); // max usage cannot be higher than the other max usage

        int maxPotentialGeneratorsSpace = maxUsage * generatorSpace;

        while (maxUnitsOfSpace < maxPotentialGeneratorsSpace){
            maxUsage--;
            maxPotentialGeneratorsSpace -= generatorSpace;
        }

        maxUnitsOfSpace -= maxPotentialGeneratorsSpace;

        return maxUsage;
    }

    /**
     * Gets the maximum amount of generators you can use with the given resources. Ignores space requirements.
     * @param amountOfResources The given resources.
     * @param resourceRequirement The resources a single generator uses.
     * @return The maximum amount of generators you can use with the given amountOfResources.
     */
    public static int getMaxUsageForResource(double amountOfResources, double resourceRequirement){
        return (int) (amountOfResources / resourceRequirement);
    }

    public static void addArrayToGeneratorList(LinkedList<PowerGenerator> generators, PowerGenerator[] addingGenerators){
        generators.addAll(Arrays.asList(addingGenerators));
    }
}