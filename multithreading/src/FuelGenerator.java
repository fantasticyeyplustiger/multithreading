public class FuelGenerator extends OneResourceGenerator {

    FuelGenerator(){
        this.defaultBurnTime = 2;
        this.defaultPowerGeneration = 450;
        this.unitsOfSpace = 12;
        this.fuelUsagePerBurnTime = 20;
        this.fuelType = "Fuel";
        FuelGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }

    /**
     * Gets Fuel Generators and stores them in an array.
     * @param amount The amount of Fuel Generators in the array.
     * @return The array with all the Fuel Generators.
     */
    public static FuelGenerator[] getGenerators(int amount) {

        FuelGenerator[] generators = new FuelGenerator[amount];

        for (int i = 0; i < amount; i++) {
            generators[i] = new FuelGenerator();
        }

        return generators;
    }
}
