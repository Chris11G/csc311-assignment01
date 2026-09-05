package edu.farmingdale.csc311.fleet;

/**
 * A named group of vehicles stored in a plain array.
 * No ArrayList, no HashMap. Arrays and loops only.
 *
 * @author Christopher Gonzalez
 */
public class Fleet {

    public static final int MAX_VEHICLES = 25;

    /* ------------------------------------------------------------------
     * TODO-08     commit: TODO-08: implement Fleet storage
     *
     * 1. Add three private fields:
     *        name        String, final
     *        vehicles    Vehicle[], final, sized MAX_VEHICLES
     *        count       int, how many slots are actually used
     *
     * 2. The constructor checks name (not null, not blank) and trims it.
     *
     * 3. Methods:
     *
     *    contains(Vehicle v)
     *        loop over the used slots and return true if one equals v.
     *        Use the equals you wrote in TODO-05, not ==.
     *
     *    add(Vehicle v)
     *        null argument           throw IllegalArgumentException
     *        already in the fleet    return false, store nothing
     *        array full              return false
     *        otherwise               store at index count, count++, return true
     *
     *    removeByVin(String vin)
     *        find the slot whose VIN matches, ignoring case. Shift every
     *        later element one place left, null out the old last slot,
     *        count--, return true. Return false when nothing matched or
     *        the vin was null or blank.
     *
     *    findByVin(String vin)
     *        return the matching Vehicle, ignoring case, or null.
     *
     *    size()
     *        return count.
     *
     *    toArray()
     *        return a NEW array of length count holding the vehicles in
     *        insertion order. Returning the internal array lets a caller
     *        overwrite your slots, so copy it.
     * ------------------------------------------------------------------ */

    // Stores the fleet's name. It cannot be changed after construction.
    private final String name;

    // Stores up to MAX_VEHICLES vehicles in a plain array.
    private final Vehicle[] vehicles;

    // Tracks how many positions in the array are currently being used.
    private int count;

    /**
     * Creates a fleet with the given name.
     */
    public Fleet(String name) {

        // The fleet name cannot be null or blank.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "name has invalid value: [" + name + "]");
        }

        // Store the trimmed fleet name.
        this.name = name.trim();

        // Create the fixed-size vehicle array.
        this.vehicles = new Vehicle[MAX_VEHICLES];

        // A new fleet starts with no vehicles.
        this.count = 0;
    }

    /**
     * Returns the fleet's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if an equal vehicle is already stored in the fleet.
     */
    public boolean contains(Vehicle vehicle) {

        // Only check the slots that are currently being used.
        for (int i = 0; i < count; i++) {

            // Use Vehicle.equals() so vehicles are compared by VIN.
            if (vehicles[i].equals(vehicle)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a vehicle to the fleet if it is not already present
     * and there is room in the array.
     */
    public boolean add(Vehicle vehicle) {

        // Null vehicles are not allowed.
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "vehicle has invalid value: [" + vehicle + "]");
        }

        // Do not add a duplicate vehicle.
        if (contains(vehicle)) {
            return false;
        }

        // Do not add anything if the array is already full.
        if (count >= MAX_VEHICLES) {
            return false;
        }

        // Store the new vehicle in the next unused position.
        vehicles[count] = vehicle;

        // Increase the number of used slots.
        count++;

        return true;
    }

    /**
     * Removes the vehicle whose VIN matches the given VIN.
     */
    public boolean removeByVin(String vin) {

        // Null or blank VIN values cannot match anything.
        if (vin == null || vin.isBlank()) {
            return false;
        }

        // Search only the used portion of the array.
        for (int i = 0; i < count; i++) {

            // Compare VINs without considering upper/lower case.
            if (vehicles[i].getVin().equalsIgnoreCase(vin.trim())) {

                // Shift every later vehicle one position to the left.
                for (int j = i; j < count - 1; j++) {
                    vehicles[j] = vehicles[j + 1];
                }

                // Clear the old final used slot.
                vehicles[count - 1] = null;

                // One fewer vehicle is now stored.
                count--;

                return true;
            }
        }

        // No matching VIN was found.
        return false;
    }

    /**
     * Finds and returns a vehicle by VIN.
     * Returns null if there is no match.
     */
    public Vehicle findByVin(String vin) {

        // Null or blank VIN values cannot match anything.
        if (vin == null || vin.isBlank()) {
            return null;
        }

        // Search only the vehicles currently stored.
        for (int i = 0; i < count; i++) {

            // Compare VINs while ignoring capitalization.
            if (vehicles[i].getVin().equalsIgnoreCase(vin.trim())) {
                return vehicles[i];
            }
        }

        // No matching vehicle was found.
        return null;
    }

    /**
     * Returns the number of vehicles currently stored.
     */
    public int size() {
        return count;
    }

    /**
     * Returns a new array containing only the vehicles currently stored.
     */
    public Vehicle[] toArray() {

        // Create a new array with exactly enough space for the used vehicles.
        Vehicle[] copy = new Vehicle[count];

        // Copy the vehicles in their original insertion order.
        for (int i = 0; i < count; i++) {
            copy[i] = vehicles[i];
        }

        // Return the copy so callers cannot modify the internal array.
        return copy;
    }

    /* ------------------------------------------------------------------
     * TODO-09     commit: TODO-09: implement Fleet reports
     *
     * None of these may reorder or change the internal array. Start from
     * toArray() when you need a different order.
     *
     *    sortedByYear()
     *        a new array ordered by year, oldest first. When two years
     *        match, order by make A to Z ignoring case
     *        (String.compareToIgnoreCase). Write the sort yourself:
     *        selection sort or insertion sort, your choice. No Arrays.sort,
     *        no Comparator.
     *
     *    countWithFuelType(FuelType fuel)
     *        how many vehicles use that fuel.
     *
     *    averageEngineSize()
     *        average engine size over the vehicles whose fuel type has an
     *        engine. Electrics are left out, otherwise their 0.0 drags the
     *        number down and it means nothing. Return 0.0 when the count is
     *        zero, and watch the division.
     *
     *    longestRange()
     *        the vehicle with the largest rangeInMiles(), or null when the
     *        fleet is empty. On a tie keep the one added first. Note that
     *        this compares cars against trucks without a single if about
     *        the type: rangeInMiles() already knows which formula to run.
     * ------------------------------------------------------------------ */

    /**
     * Returns a new array sorted by year from oldest to newest.
     * If two vehicles have the same year, their makes are sorted
     * alphabetically while ignoring capitalization.
     */
    public Vehicle[] sortedByYear() {

        // Start with a copy so the fleet's internal array is never reordered.
        Vehicle[] sorted = toArray();

        // Use selection sort as required instead of Arrays.sort().
        for (int i = 0; i < sorted.length - 1; i++) {

            // Assume the current position holds the smallest item.
            int smallestIndex = i;

            // Search the remaining array for an earlier vehicle.
            for (int j = i + 1; j < sorted.length; j++) {

                // A vehicle comes first if its year is smaller.
                boolean earlierYear =
                        sorted[j].getYear() < sorted[smallestIndex].getYear();

                // If the years match, compare the makes alphabetically.
                boolean sameYearEarlierMake =
                        sorted[j].getYear() == sorted[smallestIndex].getYear()
                                && sorted[j].getMake().compareToIgnoreCase(
                                sorted[smallestIndex].getMake()) < 0;

                if (earlierYear || sameYearEarlierMake) {
                    smallestIndex = j;
                }
            }

            // Swap the selected vehicle into the current position.
            Vehicle temp = sorted[i];
            sorted[i] = sorted[smallestIndex];
            sorted[smallestIndex] = temp;
        }

        // Return the sorted copy, leaving the internal array unchanged.
        return sorted;
    }

    /**
     * Returns the number of vehicles that use the given fuel type.
     */
    public int countWithFuelType(FuelType fuel) {

        // Tracks how many vehicles match the requested fuel type.
        int fuelCount = 0;

        // Check each vehicle currently stored in the fleet.
        for (int i = 0; i < count; i++) {

            // Enum values can be compared directly using ==.
            if (vehicles[i].getFuelType() == fuel) {
                fuelCount++;
            }
        }

        return fuelCount;
    }

    /**
     * Returns the average engine size of vehicles that actually have engines.
     * Electric vehicles are excluded from the calculation.
     */
    public double averageEngineSize() {

        // Stores the total engine size of qualifying vehicles.
        double totalEngineSize = 0.0;

        // Tracks how many vehicles actually have an engine.
        int engineCount = 0;

        for (int i = 0; i < count; i++) {

            // Only include vehicles whose fuel type reports having an engine.
            if (vehicles[i].getFuelType().hasEngine()) {
                totalEngineSize += vehicles[i].getEngineSize();
                engineCount++;
            }
        }

        // Avoid dividing by zero when there are no engine-powered vehicles.
        if (engineCount == 0) {
            return 0.0;
        }

        // Divide using a double total so the result keeps decimal precision.
        return totalEngineSize / engineCount;
    }

    /**
     * Returns the vehicle with the longest driving range.
     * Returns null if the fleet is empty.
     */
    public Vehicle longestRange() {

        // An empty fleet has no vehicle with a longest range.
        if (count == 0) {
            return null;
        }

        // Start with the first vehicle as the current longest-range vehicle.
        Vehicle longest = vehicles[0];

        // Start at index 1 because index 0 is already our current best.
        for (int i = 1; i < count; i++) {

            // Only replace the current winner when the new range is larger.
            // Using > instead of >= keeps the vehicle added first on a tie.
            if (vehicles[i].rangeInMiles() > longest.rangeInMiles()) {
                longest = vehicles[i];
            }
        }

        return longest;
    }
}