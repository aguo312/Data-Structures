/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class represents a product load. A ProductLoad has a name, a weight,
* a value, and whether or not it is dangerous. 
* 
**/

public class ProductLoad {

    private String name;
    private double weight;
    private double value;
    private boolean isDangerous;

    /**
     * Creates a new ProductLoad object with the supplied values
     *
     * @param name
     *  The String to set name to.
     * @param weight
     *  The double to set weight to.
     * @param value
     *  The dobule to set value to.
     * @param isDangerous
     *  The boolean to set isDangerous to.
     * @postcondition
     *  This ProductLoad has been initialized.
     */
    public ProductLoad(String name, double weight, double value,
      boolean isDangerous) {

        this.name = name;
        this.weight = weight;
        this.value = value;
        this.isDangerous = isDangerous;

    }

    // Getter method for name.
    public String getName() {

        return name;

    }

    /**
     * Sets the name of this ProductLoad object.
     *
     * @param s
     *  The String to set the name of this ProductLoad object to.
     * @postcondition
     *  name is set to the supplied value.
     */
    public void setName(String s) {

        name = s;

    }

    // Getter method for weight.
    public double getWeight() {

        return weight;

    }

    /**
     * Sets the weight of this ProductLoad object.
     *
     * @param weight
     *  The String to set the weight of this ProductLoad object to.
     * @postcondition
     *  weight is set to the supplied value.
     * @throws IllegalArgumentException
     *   Thrown if the provided weight is negative.
     */
    public void setWeight(double weight) {

        // Tests if weight is negative.
        try {

            if (weight < 0)
                throw new IllegalArgumentException();

        }

        // Catches if weight is negative.
        catch (IllegalArgumentException e) {

            System.out.println("Illegal Arugment Exception: Weight cannot be"
              + "negative.");

        }

        this.weight = weight;

    }

    // Getter method for value.
    public double getValue() {

        return value;

    }

    /**
     * Sets the value of this ProductLoad object.
     *
     * @param value
     *  The String to set the value of this ProductLoad object to.
     * @postcondition
     *  value is set to the supplied value.
     * @throws IllegalArgumentException
     *   Thrown if the provided value is negative.
     */
    public void setValue(double value) {

        // Tests if value is negative.
        try {

            if (value < 0)
                throw new IllegalArgumentException();

        }

        // Catches if weight is negative.
        catch (IllegalArgumentException e) {

            System.out.println("Illegal Arugment Exception: Weight cannot be"
              + "negative.");

        }

        this.value = value;

    }

    // Getter method for isDangerous.
    public boolean getIsDangerous() {

        return isDangerous;

    }

    /**
     * Sets the isDangerous of this ProductLoad object.
     *
     * @param isDangerous
     *  The boolean to set the isDangerous of this ProductLoad object to.
     * @postcondition
     *  isDangerous is set to the supplied value.
     */
    public void setIsDangerous(boolean isDangerous) {

        this.isDangerous = isDangerous;

    }

}