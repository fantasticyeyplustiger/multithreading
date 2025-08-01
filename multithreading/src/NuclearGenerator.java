public class NuclearGenerator extends TwoResourceGenerator {

    NuclearGenerator(){
        this.defaultBurnTime = 5;
        this.defaultPowerGeneration = 5000;
        this.unitsOfSpace = 40;
        this.fuelUsagePerBurnTime = 10;
        this.otherFuelUsagePerBurnTime = 150;
        this.fuelType = "Uranium";
        this.otherFuelType = "Water";
        NuclearGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }

    /**
     * Gets Nuclear Generators and stores them in an array.
     * @param amount The amount of Nuclear Generators in the array.
     * @return The array with all the Nuclear Generators.
     */
    public static NuclearGenerator[] getGenerators(int amount) {

        NuclearGenerator[] generators = new NuclearGenerator[amount];

        for (int i = 0; i < amount; i++) {
            generators[i] = new NuclearGenerator();
        }

        return generators;
    }
}
