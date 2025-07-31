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

    public static void main(String[] args) {

        Random random = new Random();

        //region Initialize resources
        resources.put("Biomass", random.nextDouble(50.0, 3000.0));
        resources.put("Water", random.nextDouble(50.0, 3000.0));
        resources.put("Coal", random.nextDouble(0.0, 1000.0));
        resources.put("Fuel", random.nextDouble(0.0, 1000.0));
        resources.put("Uranium", random.nextDouble(0.0, 500));

        int maxUnitsOfSpace = random.nextInt(50, 300);
        int overclockItems = random.nextInt(10, 100);

        System.out.println("RESOURCES:\n");

        for (String resource : resources.keySet()) {
            System.out.printf("%s: %f\n", resource, resources.get(resource));
        }

        System.out.printf("Max units of space: %d\n", maxUnitsOfSpace);
        System.out.printf("Overclock items: %d\n", overclockItems);
        //endregion



    }
}