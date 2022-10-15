/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This Planner class implements an abstract data type for an array
* of courses as well as operations on such an array.
* 
**/

public class Planner {

    private static final int MAX_COURSES = 50;
    private Course[] coursePlanner;
    private int numberItems;

    /**
     * Creates a new Planner object with no Course objects in it.
     * 
     * @postcondition
     *  This Planner has been initialized to an empty array of Courses.
     */
    public Planner() {

        coursePlanner = new Course[MAX_COURSES + 1];
        numberItems = 0;

    }

    // Getter method for coursePlanner.
    public Course[] getCoursePlanner() {

        return coursePlanner;

    }

    /**
     * Determines the number of courses currently in the planner. 
     * 
     * @precondition
     *   This planner has been instatiated. 
     * @return
     *   The number of Courses in this planner.
     */
    public int size() {

        return numberItems;

    }

    /**
     * Adds a course to this planner. 
     * 
     * @param newCourse
     *   The new course to add to the planner.
     * @param position
     *   The position of this course in the planner.
     * @precondition
     *   The Planner object is instantiated and 1 <= position <= size()+1.
     *   The number of Course objects in this Planner is less than MAX_COURSES.
     * @postcondition
     *   The new Course is listed in the correct position in the array and all
     *   Courses previously in that position and later are moved back 1
     *   position.
     * @throws FullPlannerException
     *   Thrown if the number of items in the array equals MAX_COURSES before
     *   attempting to add another course.
     * @throws IllegalArgumentException
     *   Thrown if position is not in the range 1 <= position <= size()+1. 
     */
    public void addCourse(Course newCourse, int position) {

        // Tests if the planner is full.
        try {

            if (size() == MAX_COURSES) {
                throw new FullPlannerException();
            }

            // Tests if position is a valid value.
            try {

                if (!(position >= 1 && position <= size() + 1)) {
                    throw new IllegalArgumentException();
                }

                // Tests if newCourse is already in the planner.
                try {

                    if (exists(newCourse)) {
                        throw new AlreadyExistsException();
                    }

                    // Swapping algorithmn.
                    for (int i = position; i < size() + 1; i++) {
                        Course j = coursePlanner[i];
                        coursePlanner[i] = newCourse;
                        newCourse = j;
                    }

                    // Adds new course to the end of the planner
                    coursePlanner[size() + 1] = newCourse;
                    numberItems += 1; // Adds 1 to the items in the planner

                }

                // Catches if newCourse is already in the planner.
                catch (AlreadyExistsException e) {

                    System.out.println("\nAlready Exists Exception: Course "
                      + "already exists in the planner.");

                }

            }

            // Catches if position is not a valid value.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Position " 
                  + "cannot be less than 1 or more than the # of courses "
                  + "+ 1.");

            }

        }

        // Catches if planner is full.
        catch (FullPlannerException e) {

            System.out.println("\nFull Planner Exception: Planner is full.");

        }

    }

    /**
     * Adds a course to the end of this planner. 
     * 
     * @param newCourse
     *   The new course to add to the planner.
     * @precondition
     *   The Planner object is instantiated.
     *   The number of Course objects in this Planner is less than MAX_COURSES.
     * @postcondition
     *   The new Course is listed in the next opened position in the array.
     * @throws FullPlannerException
     *   Thrown if the number of items in the array equals MAX_COURSES before
     *   attempting to add another course.
     * @throws IllegalArgumentException
     *   Thrown if position is not in the range 1 <= position <= size()+1. 
     */
    public void addCourse(Course newCourse) {

        // Calls addCourse on the next opened position.
        addCourse(newCourse, size() + 1);

    }

    /**
     * Removes a course in this planner. 
     * 
     * @param position
     *   The position in the planner where the Course would be removed from.
     * @precondition
     *   The Planner object is instantiated and 1 <= position <= size().
     * @postcondition
     *   The Course at specified position is removed and all Courses previously
     *   in a greater position is moved back by 1 position.
     * @throws IllegalArgumentException
     *   Thrown if position is not in the range 1 <= position <= size(). 
     */
    public void removeCourse(int position) {

        // Tests if position is a valid value. 
        try {

            if (!(position >= 1 && position <= size())) {
                throw new IllegalArgumentException();
            }

            // Shifts all courses back 1 position.
            for (int i = position; i < size(); i++)
                coursePlanner[i] = coursePlanner[i + 1];

            // Removes the last course in the planner.
	    coursePlanner[size()] = null;
            numberItems -= 1; // Subtracts 1 from the items in the planner.

        }

        // Catches if position is not a valid value.
        catch (IllegalArgumentException e) {

            System.out.println("\nIllegal Argument Exception: Position cannot "
              + "be less than 1 or more than the # of courses.\n");

        }

    }

    /**
     * Retrieves a course in the planner. 
     * 
     * @param position
     *   The position of the course to retrieve.
     * @precondition
     *   The Planner object is instantiated and 1 <= position <= size().
     * @return
     *   The course at the specified position.
     * @throws IllegalArgumentException
     *   Thrown if position is not in the range 1 <= position <= size(). 
     */
    public Course getCourse(int position) {

        // Tests if position is a valid value.
        try {

            if (!(position >= 1 && position <= size())) {
                throw new IllegalArgumentException();
            }

        }

        // Catches if position is not a valid value.
        catch (IllegalArgumentException e) {

            System.out.println("\nIllegal Argument Exception: Position cannot "
              + "be less than 1 or more than the # of courses.\n");

        }

        return coursePlanner[position];

    }

    /**
     * Checks whether a course in already in the Planner.
     * 
     * @param course
     *   The course we are looking for.
     * @precondition
     *   The Planner and Course are both instantiated.
     * @return
     *   True if the Planner contains this Course, false otherwise.
     */
    public boolean exists(Course course) {

        // Checks if one of the courses in the planner is the same as course.
        for (int i = 1; i < size() + 1; i++)
            if (coursePlanner[i].equals(course))
                return true;

        return false;

    }

    /**
     * Creates and returns a copy of this Planner object.
     *
     * @precondition
     *  The Planner object has been instantiated.
     * @return
     *  A new Object which is a copy of this Planner object.
     */
    public Object clone() {

        Planner plannerClone = new Planner();

        // Copies each course in coursePlanner to plannerClone's course array.
        for (int i = 1; i < size() + 1; i++) {
            plannerClone.coursePlanner[i] = (Course) coursePlanner[i].clone();
            plannerClone.numberItems += 1;
        }

        return plannerClone;

    }

    /**
     * Prints a formatted table of each item in the planner with its
     * position number.
     * 
     * @precondition
     *  The planner has been instantiated.
     * @postcondition
     *  Displays a formatted table of each course in the Planner.
     */
    public void printAllCourses() {

        System.out.print(toString());

    }

    /**
     * Gets the String represntation of this Planner object in a formatted
     * table of each Course in the Planner on its own line with its
     * position number
     *
     * @return
     *   The String representation of this Planner object.
     */
    public String toString() {

        String heading = String.format("%4s%-26s%-11s%-5s%-8s%-10s%n", "No. ",
          "Course Name", "Department ", "Code ", "Section ", "Instructor");
        String line = 
          "----------------------------------------------------------------\n";
        String body = "";

        // Adds a row in to the body for each course in the planner.
        for (int i = 1; i < size() + 1; i++) {

            String name = coursePlanner[i].getName();
            String department = coursePlanner[i].getDepartment();
            int code = coursePlanner[i].getCode();
            byte section = coursePlanner[i].getSection();
            String instructor = coursePlanner[i].getInstructor();

            // Prepends 0 to section if section is less than 10.
            String stringSection = "";

            if (("" + section).length() == 1) {

                stringSection = "0" + section;

            }

            // Changes section to a string.
            else {

                stringSection = "" + section;

            } 

            body += String.format("%3d%-27s%-12s%-8d%3s%-25s%n", i, " "
              + name, department, code, stringSection, " " + instructor);

        }

        return heading + line + body;

    }

    /**
     * Tests if this Planner object is equal to the supplied parameter
     * by testing each course within the array against each other.
     *
     * @param obj
     *   Object being compared to this Planner.
     * @return
     *   True if the two objects are equal, false if they aren't.
     */
    public boolean equals(Object obj) {

        // Checks if obj is a Planner
        if (obj instanceof Planner) {

            Planner p = (Planner) obj; // Type casts obj to Planner.

            // Checks if this Planner and Planner p have the same
            // number of courses.
            if (p.size() == this.size()) {

                // Checks if each each course in this Planner and p.
                for (int i = 1; i < size() + 1; i++) {

                    if (!coursePlanner[i].equals(p.getCoursePlanner()[i]))
                        return false;

                }

                return true;

            }

        }

        return false;

    }

    /**
     * Prints all Courses that are within a specified department.
     *
     * @param planner
     *   The planner to search the department in.
     * @param department
     *   The 3 letter department code for a Course to search for.
     * @precondition
     *   This Planner object has been instantiated.
     * @postcondition
     *   Displays a formatted table of each course filtered from the planner.
     */
    public static void filter(Planner planner, String department) {

        String heading = String.format("%4s%-26s%-11s%-5s%-8s%-10s%n", "No. ",
          "Course Name", "Department ", "Code ", "Section ", "Instructor");
        String line = 
          "----------------------------------------------------------------\n";
        String body = "";

        for (int i = 1; i < planner.size() + 1; i++) {

            String courseDepartment = 
              planner.getCoursePlanner()[i].getDepartment();

            // Adds course i to the body if it has the target department.
            if (courseDepartment.equals(department)) {

                String name = planner.coursePlanner[i].getName();
                int code = planner.coursePlanner[i].getCode();
                byte section = planner.coursePlanner[i].getSection();
                String instructor = planner.coursePlanner[i].getInstructor();

                // Prepends 0 to section if section is less than 10.
                String stringSection = "";

                if (("" + section).length() == 1) {

                    stringSection = "0" + section;

                }

                // Changes section to a string.
                else {

                    stringSection = "" + section;

                } 

                body += String.format("%3d%-27s%-12s%-8d%3s%-25s%n", i, " "
                  + name, department, code, stringSection, " " + instructor);

            }

        }

        System.out.print("\n" + heading + line + body);

    }

}

// Exception for when planner is full.
class FullPlannerException extends Exception {

    public FullPlannerException() {   
    }

}

// Exception for when trying to add a duplicate course.
class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {
    }

}