/**
 * CS3810 - Principles Database Systems - Fall 2019
 * Driver Class for implementing Employee CRUD methods.
 *
 * @author Justin Strelka
 * @date Aug-23-2019
 */

// Import Libraries

import java.io.FileInputStream;
import java.io.*;
import java.util.Scanner;

// EmployeeFMSDriver class
public class EmployeeFMSDriver implements EmployeeCRUD {

    // Create CONSTANTS for .csv file names.
    static final String EMPLOYEE_FILENAME = "employee.csv";
    static final String EMPLOYEE_FILENAME_TEMP = "employeeTemp.csv";

    /**
     * Writes and employee to file in .csv format
     * method will ensure a unique employee id prior
     * to writing new employee to file.
     * @param employee
     * @return
     * */
    public void create(final Employee employee) {
        // Initialize local variables
        boolean found = false;
        try {
            // Create file scanner to read file
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            // Loop lines of file until EOF reached.
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                int id = Integer.parseInt(data[0]);
                /**
                 * Ensure new employee ID is unique.
                 * Break loop if ID is found in file.
                 * */
                if (id == employee.getId()) {
                    found = true;
                    break;
                }
            }
            // close file
            in.close();
        } catch (FileNotFoundException ex) {
            //ignoring
        }
        // Notify user if ID already exists.
        if (found)
            System.out.println("Employee with same ID already exists!");
        else {
            try {
                // Create file I/O output to append employee to end of .csv file.
                PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME, true));
                // Write employee to file
                out.println(employee);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the employee if found
     *
     * @param id the id of the employee to be returned
     * @return the employee object (null if not found)
     */
    public Employee read(int id) {
        try {
            // Create Scanner for file input
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            // Loop lines of file until EOF
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                int key = Integer.parseInt(data[0]);
                // Check for employee id match
                if (id == key) {
                    String name = data[1];
                    String dep = data[2];
                    Employee employee = new Employee(id, name, dep);
                    in.close();
                    return employee;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            // ignoring...
        }
        // return null if no match found
        return null;
    }

    /**
     * Updates the employee information
     *
     * @param id       the id of the employee to be updated
     * @param employee the employee object
     */
    public void update(int id, final Employee employee) {
        // Initialize local variables
        boolean found = false;
        int lineId;
        // Create file objects for file name manipulation.
        File inFile = new File(EMPLOYEE_FILENAME);
        File outFile = new File(EMPLOYEE_FILENAME_TEMP);
        try {
            // Create scanner for file input and output
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME_TEMP, true));
            // Loop file until EOF
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                lineId = Integer.parseInt(data[0]);
                // Check for ID match
                if (lineId == id) {
                    found = true;
                    out.println(employee);
                } else {
                    out.println(line);
                }
            }
            in.close();
            out.close();
            // Notify user if employee ID does not exist
            if (found == false) {
                System.out.println("The employee id entered to update does not exist.");
            }
            // Delete original file and rename Temp file to match original file name.
            inFile.delete();
            outFile.renameTo(inFile);
        } catch (FileNotFoundException ex) {
            //ignoring
        }
    }

    /**
     * Removes the employee specified by id
     *
     * @param id the id of the employee to be deleted
     */
    public void delete(int id) {
        // Initialize local variables
        int lineId;
        // Create File objects for file manipulation
        File inFile = new File(EMPLOYEE_FILENAME);
        File outFile = new File(EMPLOYEE_FILENAME_TEMP);
        try {
            // Create Scanner for file input and output (append)
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME_TEMP, true));
            // Loop file until EOF
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                lineId = Integer.parseInt(data[0]);
                // Check for ID match
                if (lineId == id) {
                    continue;
                } else {
                    out.println(line);
                }
            }
            in.close();
            out.close();
            inFile.delete();
            outFile.renameTo(inFile);
        } catch (FileNotFoundException ex) {
            //ignoring
        }
    }

    /**
     * Main driver method for EmployeeCRUD interface.
     * */
    public static void main(String args[]) {
        // Initialize local variables
        int usrInput;
        boolean sentinel = true;
        EmployeeFMSDriver impl = new EmployeeFMSDriver();
        Scanner scnUsrIn = new Scanner(System.in);
        // Greet User
        System.out.println("Hello welcome to the .csv FMS");
        // Start User Interface
        while (sentinel) {
            // Print options menu
            System.out.println("\nWhat would you like to do?\nEnter 1 to CREATE employee.\n" +
                    "Enter 2 to READ employee info.\nEnter 3 to UPDATE employee info.\nEnter 4 to DELETE employee.\n" +
                    "Enter 5 to QUIT.\n");
            // Prompt user input
            System.out.print("Your input: ");
            usrInput = scnUsrIn.nextInt();

            // Check user input for flow control
            // CREATE
            if (usrInput == 1) {
                // Initialize local variables
                int id;
                String empName, empDep;
                // Get User input for employee info
                System.out.print("\nEnter unique integer employee ID: ");
                id = scnUsrIn.nextInt();
                System.out.print("Enter employee NAME: ");
                empName = scnUsrIn.next();
                System.out.print("Enter employee DEPARTMENT: ");
                empDep = scnUsrIn.next();
                //Create and write new employee
                Employee newEmp = new Employee(id, empName, empDep);
                impl.create(newEmp);
            }
            // READ
            else if (usrInput == 2) {
                // Initialize local variables
                int id;
                String empStr;
                // Prompt user input for ID
                System.out.print("\nEnter the employee ID number to read: ");
                id = scnUsrIn.nextInt();
                // Print employee info to user if found
                System.out.println("\nEmployee read info: " + impl.read(id) + "\n");
            }
            // UPDATE
            else if (usrInput == 3) {
                // Initialize local variables
                int id;
                String empName, empDep;
                // Prompt user for employee info
                System.out.print("\nEnter the employee ID number to update: ");
                id = scnUsrIn.nextInt();
                System.out.print("Enter the new employee NAME: ");
                empName = scnUsrIn.next();
                System.out.print("Enter the new employee DEPARTMENT: ");
                empDep = scnUsrIn.next();
                // Create and update new employee info
                Employee newEmp = new Employee(id, empName, empDep);
                impl.update(id, newEmp);
            }
            // DELETE
            else if (usrInput == 4) {
                // Initialize local variables
                int id;
                // Prompt user for ID
                System.out.print("\nEnter employee ID to delete: ");
                id = scnUsrIn.nextInt();
                // Delete employee
                impl.delete(id);
            }
            // QUIT
            else if (usrInput == 5) {
                sentinel = false;
                break;
            }
            // INVALID INPUT
            else {
                System.out.println("Please enter an integer from 1 to 5.");
            }
        }
    }
}
