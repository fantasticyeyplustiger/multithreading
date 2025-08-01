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
}
