public class BiomassGenerator extends OneResourceGenerator {

    /* Initialize all default values */
    BiomassGenerator(){
        this.defaultBurnTime = 1;
        this.defaultPowerGeneration = 30;
        this.unitsOfSpace = 1;
        this.fuelUsagePerBurnTime = 5;
        BiomassGenerator.totalFuelBeingBurnt += 5;
    }

}
