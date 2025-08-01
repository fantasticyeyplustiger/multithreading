import java.util.HashMap;
import java.util.Random;

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
        resources.put("Biomass", random.nextDouble(50.0, 3000.0));
        resources.put("Water", random.nextDouble(50.0, 3000.0));
        resources.put("Coal", random.nextDouble(0.0, 1000.0));
        resources.put("Fuel", random.nextDouble(0.0, 1000.0));
        resources.put("Uranium", random.nextDouble(0.0, 500.0));

        maxUnitsOfSpace = random.nextInt(50, 300);

        int overclockItems = random.nextInt(10, 100);

        System.out.println("RESOURCES:\n");

        for (String resource : resources.keySet()) {
            System.out.printf("%s: %f\n", resource, resources.get(resource));
        }

        System.out.printf("Max units of space: %d\n", maxUnitsOfSpace);
        System.out.printf("Overclock items: %d\n", overclockItems);
        //endregion



        stopwatch.stop();
        stopwatch.printElapsedMilliseconds();
    }

    /**
     * Calculates the amount of OneResourceGenerators you can have with the given space and resources.
     * @param generatorType The type of generator, i.e. BiomassGenerator or FuelGenerator.
     * @param resource The amount of resource the generator uses.
     */
    public static int calculateOneResourceGenerators(OneResourceGenerator generatorType, double resource){
        int generatorSpace = generatorType.unitsOfSpace;
        int maxUsage = getMaxUsageForResource(resource, generatorType.fuelUsagePerBurnTime);

        int maxPotentialGeneratorsSpace = maxUsage * generatorSpace;

        while (maxUnitsOfSpace < maxPotentialGeneratorsSpace){
            maxUsage--;
            maxPotentialGeneratorsSpace -= generatorSpace;
        }

        return maxUsage;
    }

    /**
     * Calculates the amount of TwoResourceGenerators you can have with the given space and resources.
     * @param generatorType The type of generator, i.e. CoalGenerator or NuclearGenerator.
     * @param resourceOne The first amount of resource the generator uses.
     * @param resourceTwo The second amount of resource the generator uses.
     */
    public static int calculateTwoResourceGenerators(TwoResourceGenerator generatorType, double resourceOne, double resourceTwo){
        int generatorSpace = generatorType.unitsOfSpace;

        int maxUsageOne = getMaxUsageForResource(resourceOne, generatorType.fuelUsagePerBurnTime);
        int maxUsageTwo = getMaxUsageForResource(resourceTwo, generatorType.otherFuelUsagePerBurnTime);

        int maxUsage = Math.min(maxUsageOne, maxUsageTwo); // max usage cannot be higher than the other max usage

        int maxPotentialGeneratorsSpace = maxUsage * generatorSpace;

        while (maxUnitsOfSpace < maxPotentialGeneratorsSpace){
            maxUsage--;
            maxPotentialGeneratorsSpace -= generatorSpace;
        }

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
}