/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class represents a course. A course has a name, a department,
* a course code, a course section, and an instructor. 
* 
**/

public class Course {
    
    private String name;
    private String department;
    private int code;
    private byte section;
    private String instructor;


    /**
     * Creates a new Course object with the supplied values
     * 
     * @param n
     *  The String to set name to.
     * @param d
     *  The String to set department to.
     * @param c
     *  The int to set code to.
     * @param s
     *  The byte to set section to.
     * @param i
     *  The String to set instructor to.
     * @postcondition
     *  This Course has been initialized.
     */
    public Course(String n, String d, int c, byte s, String i) {

        name = n;
        department = d;
        code = c;
        section = s;
        instructor = i;

    }

    // Getter method for name.
    public String getName() {

        return name;

    }

    // Getter method for department.
    public String getDepartment() {

        return department;

    }

    // Getter method for code.
    public int getCode() {

        return code;

    }

    // Getter method for section.
    public byte getSection() {

        return section;

    }

    // Getter method for instructor.
    public String getInstructor() {

        return instructor;

    }

    /**
     * Creates and returns a copy of this Course object.
     *
     * @precondition
     *  The Course object has been instantiated.
     * @return
     *  A new Object which is a copy of this Course object.
     */
    public Object clone() {

        Course courseClone = new Course(this.name, this.department,
          this.code, this.section, this.instructor);

        return courseClone;

    }

    /**
     * Tests if this Course object is equal to the supplied parameter
     * by testing the corresponding fields of each against each other.
     *
     * @param obj
     *   Object being compared to this Course.
     * @return
     *   True if the two objects are equal, false if they aren't.
     */
    public boolean equals(Object obj) {

        // Checks if obj is a Course.
	if (obj instanceof Course) {

            Course c = (Course) obj; // Type casts obj to Course.

            // Checks each field of this Course and c.
            if (this.getName().equals(c.getName()) &&
              this.getDepartment().equals(c.getDepartment()) &&
              this.getCode() == c.getCode() &&
              this.getSection() == c.getSection() &&
              this.getInstructor().equals(c.getInstructor()))
                return true;

        }

        return false;

    }

}