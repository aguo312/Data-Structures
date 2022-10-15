/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents the complexity level. A Complexity has an n_power,
 * and a log_power.
 *
 **/

public class Complexity {

    private int n_power;
    private int log_power;

    /**
     * Creates a new Complexity object with the supplied values
     *
     * @param n_power
     *  The int to set n_power to.
     * @param log_power
     *  The int to set log_power to.
     * @postcondition
     *  This Complexity has been initialized.
     */
    public Complexity(int n_power, int log_power) {

        this.n_power = n_power;
        this.log_power = log_power;

    }

    // Getter method for n_power
    public int getN_power() {

        return n_power;

    }

    /**
     * Sets the n_power of this Complexity object.
     *
     * @param n_power
     *  The int to set the n_power of this Complexity object to.
     * @postcondition
     *  n_power is set to the supplied value.
     */
    public void setN_power(int n_power) {

        this.n_power = n_power;

    }

    // Getter method for log_power.
    public int getLog_power() {

        return log_power;

    }

    /**
     * Sets the log_power of this Complexity object.
     * @param log_power
     *  The int to set log_power of this Complexity object to.
     * @postcondition
     *  log_power is set to the supplied value.
     */
    public void setLog_power(int log_power) {

        this.log_power = log_power;

    }

    /**
     * Puts this Complexity object in Big-Oh notation as a string
     * @return
     *  The String representation of this Complexity object.
     */
    public String toString() {

        String s = "O(";

        if (n_power == 0 && log_power == 0) {

            return "O(1)";

        }

        if (n_power == 0) {

            s += "";

        }

        if (n_power == 1) {

            s += "n";

        }

        if (n_power > 1) {

            s += "n^" + n_power;

        }

        if (log_power == 0) {

            s += ")";

        }

        if (n_power > 0 && log_power > 0) {

            s += " * ";

        }

        if (log_power == 1) {

            s += "log(n))";

        }

        if (log_power > 1) {

            s += "log(n)^" + log_power + ")";

        }

        return s;

    }

}
