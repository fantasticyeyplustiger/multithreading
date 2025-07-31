public class BiomassGenerator extends OneResourceGenerator {

    BiomassGenerator(){
        this.defaultBurnTime = 1;
        this.defaultPowerGeneration = 30;
        this.unitsOfSpace = 1;
        this.fuelUsagePerBurnTime = 5;
        this.fuelType = "Biomass";
        BiomassGenerator.totalFuelBeingBurnt += 5;
    }

}
