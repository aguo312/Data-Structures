/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class is a program that runs operations prompted by the user
* on a Planner.
* 
**/

import java.util.Scanner;

public class PlannerManager {

    /**
     * Runs a menu that first creates an empty Planner object and allows
     * the user execute an operation from the menu. 
     *
     * @param args
     *  Command line arguments.
     */
    public static void main(String[] args) {

        Planner plannerManager = new Planner();
        Scanner input = new Scanner(System.in);
	Planner plannerBackup = new Planner();

        // Prints the menu in the beginning and when an operation is done.
        while (true) {

            System.out.println("(A) Add Course");
            System.out.println("(G) Get Course");
            System.out.println("(R) Remove Course");
            System.out.println("(P) Print Courses in Planner");
            System.out.println("(F) Filter by Department Code");
            System.out.println("(L) Look For Course");
            System.out.println("(S) Size");
            System.out.println("(B) Backup");
            System.out.println("(PB) Print Courses in Backup");
            System.out.println("(RB) Revert to Backup");
            System.out.println("(Q) Quit\n");
            System.out.print("Enter a selection: ");
            String selection = input.nextLine();

            // Makes input case insensitive.
            selection = selection.toUpperCase();

            // Runs the operation to add a course.
            if (selection.equals("A")) {

                System.out.println();
                System.out.print("Enter course name: ");
                String courseName = input.nextLine();

                System.out.print("Enter department: ");
                String department = verifyDepartment(input.nextLine());

                // Continues operation if department code is verified.
                if (department != null) {

                    System.out.print("Enter course code: ");
                    int courseCode = verifyCourseCode(input.nextLine());

                    // Continues operation if course code is verified. 
                    if (courseCode != -1) {

                        System.out.print("Enter course section: ");
                        byte courseSection =
                          verifyCourseSection(input.nextLine());

                        // Continues operation if course section if verified.
                        if (courseSection != -1) {

                            System.out.print("Enter instructor: ");
                            String instructor = input.nextLine();

                            System.out.print("Enter position: ");
                            int position = Integer.parseInt(input.nextLine());

                            Course newCourse = new Course(courseName,
                              department, courseCode, courseSection,
                              instructor);

                            // Keeps track of if the size of planner changed.
                            int previousSize = plannerManager.size();
                            plannerManager.addCourse(newCourse, position);
                            int currentSize = plannerManager.size();

                            // Prints out message if addCourse is successful.
                            if (previousSize + 1 == currentSize) {

                                System.out.println();

                                // Message for courseSection less than 10
                                if (("" + courseSection).length() == 1) {

                                    System.out.println(department + " "
                                      + courseCode + ".0" + courseSection
                                      + " sucessfully added to planner.");

                                }

                                // Message for other courseSection
                                else {

                                    System.out.println(department + " "
                                      + courseCode + "." + courseSection 
                                      + " sucessfully added to planner.");

                                }

                            }

                        }

                    }

                }

                System.out.println();

            }

            // Runs the operation to get a course.
            if (selection.equals("G")) {

                System.out.println();
                System.out.print("Enter position: ");
                int position = Integer.parseInt(input.nextLine());
                Course selectedCourse = plannerManager.getCourse(position);

                // Continues operation if position does not cause an error.
                if (selectedCourse != null){

                    String name = selectedCourse.getName();
                    String department = selectedCourse.getDepartment();
                    int code = selectedCourse.getCode();
                    byte section = selectedCourse.getSection();
                    String instructor = selectedCourse.getInstructor();
                    System.out.println();

                    String heading = String.format(
                      "%4s%-26s%-11s%-5s%-8s%-10s%n", "No. ", "Course Name",
                      "Department ", "Code ", "Section ", "Instructor");
                    String line = "------------------------------------------"
                      + "----------------------\n";
                    String body = "";

                    // Prepends 0 to section if section is less than 10.
                    String stringSection = "";

                    if (("" + section).length() == 1) {

                        stringSection = "0" + section;

                    }

                    // Changes section to a string.
                    else {

                    stringSection = "" + section;

                    } 

                    body += String.format("%3d%-27s%-12s%-8d%3s%-25s%n", 
                      position, " " + name, department, code,
                      stringSection, " " + instructor);

                    System.out.println(heading + line + body);

                }

            }

            // Runs the operation to remove a course.
            if (selection.equals("R")) {

                System.out.println();
                System.out.print("Enter position: ");
                int position = Integer.parseInt(input.nextLine());
                Course removedCourse = plannerManager.getCourse(position);

                // Continues operation if position does not cause an error.
                if (removedCourse != null) {

                    String department = removedCourse.getDepartment();
                    int courseCode = removedCourse.getCode();
                    byte courseSection = removedCourse.getSection();
                    plannerManager.removeCourse(position);

                    // Message for courseSection less than 10
                    if (("" + courseSection).length() == 1) {

                        System.out.println("\n" + department + " " + courseCode
                          + ".0" + courseSection
                          + " has been successfully removed "
                          + "from the planner.\n");
                    }

                    // Message for other courseSection
                    else {

                        System.out.println("\n" + department + " " + courseCode
                          + "." + courseSection
                          + " has been successfully removed "
                          + "from the planner.\n");

                    }

                }

            }

            // Runs the operation for printing all courses in the planner.
            if (selection.equals("P")) {

                System.out.println();
                System.out.println("Planner:");
                plannerManager.printAllCourses();
                System.out.println();

            }

            // Runs the operation for filtering by department code.
            if (selection.equals("F")) {

                System.out.println();
                System.out.print("Enter department code: ");
                String department = verifyDepartment(input.nextLine());

                // Continues operation if department code is verified.
                if (department != null) {

                    Planner.filter(plannerManager, department);

                }

                System.out.println();

            }

            // Runs the operation for looking for course.
            if (selection.equals("L")) {

                System.out.println();
                System.out.print("Enter course name: ");
                String courseName = input.nextLine();

                System.out.print("Enter department: ");
                String department = verifyDepartment(input.nextLine());

                // Continues operation if department code is verified.
                if (department != null) {

                    System.out.print("Enter course code: ");
                    int courseCode = verifyCourseCode(input.nextLine());

                    // Continues operation if course code is verified.
                    if (courseCode != -1) {

                        System.out.print("Enter course section: ");
                        byte courseSection =
                          verifyCourseSection(input.nextLine());

                        // Continues operation if course section is verified.
                        if (courseSection != -1) {

                            System.out.print("Enter instructor: ");
                            String instructor = input.nextLine();

                            Course newCourse = new Course(courseName,
                              department, courseCode, courseSection,
                              instructor);

                            // Message for course section less than 10
                            System.out.println();
                            if (("" + courseSection).length() == 1) {

                                System.out.print(department + " " + courseCode
                                  + ".0" + courseSection + " is ");

                            }

                            // Message for other course section.
                            else {

                                System.out.print(department + " " + courseCode
                                  + "." + courseSection + " is ");

                            }

                            // Finds position of newCourse in the planner.
                            if (plannerManager.exists(newCourse)) {

                                int index = 0;
                                int s = plannerManager.size() + 1;

                                for (int i = 1; i < s; i++) {

                                    Course c =
                                      plannerManager.getCoursePlanner()[i];

                                    if (c.equals(newCourse)) {

                                        index = i;

                                    }

                                }

                                // Declares message for position of newCourse.
                                System.out.println("found in the planner at "
                                  + "position " + index + ".");

                            }

                            // Message when course is not in the planner.
                            else
                                System.out.println("not found in the planner.");

                        }

                    }

                }

                System.out.println();

            }

            // Runs the operation for size of the planner.
            if (selection.equals("S")) {

                System.out.println();

                // Message for when planner has one course.
                if (plannerManager.size() == 1)
                    System.out.println("There is 1 course in the planner.");

                // Message for when planner does not have one course.
                else
                    System.out.println("There are " + plannerManager.size()
                      + " courses in the planner.");

                System.out.println();

            }

            // Runs the operation for backing up the active planner.
            if (selection.equals("B")) {

                System.out.println();
                plannerBackup = (Planner) plannerManager.clone();
                System.out.println("Created a backup of the current planner.");
                System.out.println();

            }

            // Runs the operation to print all courses in the backup planner.
            if (selection.equals("PB")) {

                System.out.println();
                System.out.println("Planner (Backup):");
                plannerBackup.printAllCourses();
                System.out.println();

            }

            // Runs the operation to revert planner back to the backup planner.
            if (selection.equals("RB")) {

                System.out.println();
                plannerManager = (Planner) plannerBackup.clone();
                System.out.println("Planner successfully reverted to the "
                  + "backup copy.");
                System.out.println();

            }

            // Runs the operation to quit the program.
            if (selection.equals("Q")) {

                System.out.println("\nProgram terminating successfully...");
                break;

            }

        }

    }

    /**
     * Verifies if the supplied parameter is 3 letters.
     *
     * @param s
     *  String that is being verified
     * @return
     *  An uppercase string of s if s is verified, null otherwise.
     * @throws Illegal Argument Exception
     *  Thrown if s is not 3 letters.
     */
    public static String verifyDepartment(String s) {

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            // Tests if s has a length of 3 and if character c is a letter.
            try {

                if (!Character.isLetter(c) || s.length() != 3)
                    throw new IllegalArgumentException();

            }

            // Catches if s does not have a length of 3
            // or if c is not a letter.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Department "
                  + "does not have three letters.");
                return null;

            }

        }

        return s.toUpperCase();

    }

    /**
     * Verifies if the supplied parameter is 3 numbers.
     *
     * @param s
     *  String that is being verified
     * @return
     *  s as an int if s is verified, -1 otherwise.
     * @throws Illegal Argument Exception
     *  Thrown if s is not 3 numbers.
     */
    public static int verifyCourseCode(String s) {

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            // Tests if s has a length of 3 and if character c is a digit.
            try {

                if (!Character.isDigit(c) || s.length() != 3)
                    throw new IllegalArgumentException();

            }

            // Catches if s does not have a length of 3 or if c is not a digit.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Course Code "
                  + "does not have three numbers.");
                return -1;

            }

        }

        int courseCode = Integer.parseInt(s);
        return courseCode;

    }

    /**
     * Verifies if the supplied parameter fits the range of
     * a byte greater than 0 (1 <= s <= 127).
     *
     * @param s
     *  String that is being verified
     * @return
     *  s as a byte if s is verified, -1 as a byte otherwise.
     * @throws Illegal Argument Exception
     *  Thrown if s is not in the range of a byte greater than 0.
     */
    public static byte verifyCourseSection(String s) {
        int value = Integer.parseInt(s);

        // Tests if s is in the range of valid values.
        try {

            if (!(value > 0 && value <= 127))
                throw new IllegalArgumentException();

        }

        // Catches if s is not in the range of valid values.
        catch (IllegalArgumentException e) {

            System.out.println("\nIllegal Argument Exception: Course section "
              + "needs to be between 1 and 127." );
            byte x = Byte.parseByte("-1");
            return x;

        }

        byte y = Byte.parseByte(s);
        return y;

    }

}