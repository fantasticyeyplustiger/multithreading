public class BiomassGenerator extends OneResourceGenerator {

    BiomassGenerator(){
        this.defaultBurnTime = 1;
        this.defaultPowerGeneration = 30;
        this.unitsOfSpace = 1;
        this.fuelUsagePerBurnTime = 5;
        this.fuelType = "Biomass";
        BiomassGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }

    /**
     * Gets Biomass Generators and stores them in an array.
     * @param amount The amount of Biomass Generators in the array.
     * @return The array with all the Biomass Generators.
     */
    public static BiomassGenerator[] getGenerators(int amount) {

        BiomassGenerator[] generators = new BiomassGenerator[amount];

        for (int i = 0; i < amount; i++) {
            generators[i] = new BiomassGenerator();
        }

        return generators;
    }
}
