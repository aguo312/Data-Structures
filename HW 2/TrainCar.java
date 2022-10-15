/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class represents a train car. A TrainCar has a car length, a car weight,
* and a ProductLoad.  
* 
**/

public class TrainCar {

    private double CAR_LENGTH;
    private double CAR_WEIGHT;
    private ProductLoad load;

    /**
     * Creates a new TrainCar object with the supplied values
     * 
     * @param carWeight
     *  The double to set CAR_WEIGHT to.
     * @param carLength
     *  The double to set CAR_LENGTH to.
     * @postcondition
     *  This TrainCar has been initialized and load is null.
     */
    public TrainCar(double carWeight, double carLength) {

        CAR_WEIGHT = carWeight;
        CAR_LENGTH = carLength;
        load = null;

    }

    // Getter method for CAR_LENGTH.
    public double getCarLength() {

        return CAR_LENGTH;

    }

    // Getter method for CAR_WEIGHT.
    public double getCarWeight() {

        return CAR_WEIGHT;

    }

    // Getter method for load.
    public ProductLoad getLoad() {

        return load;

    }

    /**
     * Sets the load of this TrainCar object.
     *
     * @param load
     *  The ProductLoad to set the load of this TrainCar object to.
     * @postcondition
     *  load is set to the supplied value.
     * @throws AlreadyLoadedException
     *   Thrown if the provided TrainCar is already loaded.
     */
    public void setLoad(ProductLoad load) {

        try {

            if (!isEmpty())
                throw new AlreadyLoadedException();

        }

        catch (AlreadyLoadedException e) {

            System.out.println("Already Loaded Exception: This car is "
              + "already loaded.");

        }

        this.load = load;

    }

    /**
     * Tests if this TrainCar is loaded.
     *
     * @return
     *   True if load is null, false otherwise.
     */
    public boolean isEmpty() {

        return load == null;

    }

}

// Exception for when this TrainCar is already loaded.
class AlreadyLoadedException extends Exception {

    public AlreadyLoadedException() {   
    }

}