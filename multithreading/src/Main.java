import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Arrays;

// Goal: From randomly generated resources, attempt to calculate the most efficient power grid.
// Print the amount of time it takes to calculate.
// Then, run a thread that burns every single generator's fuel until it runs out.
// While that happens, have a thread calculate fibonacci n45 repeatedly and count the amount of times n45 is reached
// While that also happens, have a thread calculate fibonacci n30 repeatedly and count the amount of times n35 is reached
// Finally, when the last generator's fuel runs out, stop all 3 threads and print both n45 and n35 results.

public class Main {

    static HashMap<String, Double> resources = new HashMap<>();

    static int maxUnitsOfSpace;

    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();

        Random random = new Random();

        //region Initialize resources
        /*
        OLD CODE
        resources.put("Biomass", random.nextDouble(50.0, 3000000.0));
        resources.put("Water", random.nextDouble(50.0, 3000000.0));
        resources.put("Coal", random.nextDouble(0.0, 1000000.0));
        resources.put("Fuel", random.nextDouble(0.0, 1000000.0));
        resources.put("Uranium", random.nextDouble(0.0, 500000.0));

        maxUnitsOfSpace = random.nextInt(499999, 500000);
         */

        resources.put("Biomass", 3000000.0);
        resources.put("Water", 3000000.0);
        resources.put("Coal", 1000000.0);
        resources.put("Fuel", 1000000.0);
        resources.put("Uranium", 500000.0);

        maxUnitsOfSpace = 500000;

        int overclockItems = random.nextInt(10, 50);

        // Assign static variables
        NuclearGenerator.totalFuelAvailable = resources.get("Uranium");
        NuclearGenerator.otherTotalFuelAvailable = resources.get("Water");

        FuelGenerator.totalFuelAvailable = resources.get("Fuel");

        CoalGenerator.totalFuelAvailable = resources.get("Coal");
        CoalGenerator.otherTotalFuelAvailable = resources.get("Water");

        BiomassGenerator.totalFuelAvailable = resources.get("Biomass");

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

        GeneratorThread genThreadOne = new GeneratorThread(nuclearGenerators);
        GeneratorThread genThreadTwo = new GeneratorThread(fuelGenerators);
        GeneratorThread genThreadThree = new GeneratorThread(coalGenerators);
        GeneratorThread genThreadFour = new GeneratorThread(biomassGenerators);
        GeneratorThread allGensThread = new GeneratorThread(allGenerators);

        /*
        genThreadOne.startThread();
        genThreadTwo.startThread();
        genThreadThree.startThread();
        genThreadFour.startThread();
         */

        // This thread has the work of the four above.
        // Used to see the difference in time between single threaded and multithreaded.
        allGensThread.startThread();

        stopwatch.printElapsedMilliseconds();
        System.out.println();
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