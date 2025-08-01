public class CoalGenerator extends TwoResourceGenerator {

    CoalGenerator(){
        this.defaultBurnTime = 1;
        this.defaultPowerGeneration = 150;
        this.unitsOfSpace = 5;
        this.fuelUsagePerBurnTime = 12;
        this.otherFuelUsagePerBurnTime = 25;
        this.fuelType = "Coal";
        this.otherFuelType = "Water";
        CoalGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }

    /**
     * Gets Coal Generators and stores them in an array.
     * @param amount The amount of Coal Generators in the array.
     * @return The array with all the Coal Generators.
     */
    public static CoalGenerator[] getGenerators(int amount) {

        CoalGenerator[] generators = new CoalGenerator[amount];

        for (int i = 0; i < amount; i++) {
            generators[i] = new CoalGenerator();
        }

        return generators;
    }
}
