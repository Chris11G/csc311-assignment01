package edu.farmingdale.csc311.fleet;

/**
 * A work truck. Its range drops as its payload rating goes up, so it
 * computes range differently from a Car.
 *
 * @author Christopher Gonzalez
 */
public class Truck extends Vehicle {

    /* ------------------------------------------------------------------
     * TODO-07     commit: TODO-07: implement Truck
     *
     * 1. Add a private double field payloadKg.
     * 2. After the super(...) call, check payloadKg (above 0.0, at most
     *    20000.0) and store it.
     * 3. Fill in getPayloadKg() and setPayloadKg(double) with the same check.
     * 4. category()     returns "Truck"
     *    hornSound()    returns "HOOOONK!"
     *    honk()         a truck answers one honk() with two blasts.
     *                   Override it and call honk(2). Do not copy the
     *                   printing code from Vehicle.
     *    rangeInMiles()
     *        loadFactor = 1.0 - Math.min(0.35, payloadKg / 20000.0)
     *        range      = getFuelCapacity() * getFuelType().getMilesPerUnit() * loadFactor
     * 5. toString() returns:
     *
     *      Truck -> <what Vehicle.toString() gives>, payload=3500.0 kg, range=1122.0 mi
     *
     *    Same rules as Car: super.toString(), category(), one decimal.
     * ------------------------------------------------------------------ */

    // Stores the truck's payload capacity in kilograms.
    private double payloadKg;

    public Truck(String vin, String make, String model, int year, String color,
                 int wheels, double engineSize, FuelType fuelType, double fuelCapacity, double payloadKg) {

        // Calls the Vehicle constructor to initialize all inherited fields.
        // This must remain the first statement in the constructor.
        super(vin, make, model, year, color, wheels, engineSize, fuelType, fuelCapacity);

        // Use the setter so the payload validation only has to be written once.
        setPayloadKg(payloadKg);
    }

    /**
     * Returns the truck's payload capacity in kilograms.
     */
    public double getPayloadKg() {
        return payloadKg;
    }

    /**
     * Changes the truck's payload capacity.
     * The payload must be above 0.0 kg and no more than 20000.0 kg.
     */
    public void setPayloadKg(double payloadKg) {

        // Reject payload values outside the allowed range.
        if (!(payloadKg > 0.0 && payloadKg <= 20000.0)) {
            throw new IllegalArgumentException(
                    "payloadKg has invalid value: [" + payloadKg + "]");
        }

        // Store the valid payload value.
        this.payloadKg = payloadKg;
    }

    /**
     * Returns the type/category of this vehicle.
     */
    @Override
    public String category() {
        return "Truck";
    }

    /**
     * Returns the horn sound used by a Truck.
     */
    @Override
    public String hornSound() {
        return "HOOOONK!";
    }

    /**
     * A truck honks twice when honk() is called once.
     */
    @Override
    public void honk() {

        // Reuse Vehicle's honk(int) method instead of repeating its printing code.
        honk(2);
    }

    /**
     * Calculates the truck's driving range.
     * The range decreases as the payload rating increases.
     */
    @Override
    public double rangeInMiles() {

        // Reduce the range by up to 35% based on the truck's payload.
        double loadFactor = 1.0 - Math.min(0.35, payloadKg / 20000.0);

        // Range = capacity * miles per unit * payload load factor.
        return getFuelCapacity()
                * getFuelType().getMilesPerUnit()
                * loadFactor;
    }

    /**
     * Returns the Vehicle information along with the truck-specific
     * payload capacity and driving range.
     */
    @Override
    public String toString() {

        // Reuse Vehicle's toString() and add the Truck-specific information.
        // Payload and range are both displayed with one decimal place.
        return String.format(
                "%s -> %s, payload=%.1f kg, range=%.1f mi",
                category(),
                super.toString(),
                payloadKg,
                rangeInMiles()
        );
    }
}