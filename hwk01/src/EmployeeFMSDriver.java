import java.io.FileInputStream;
import java.io.*;
import java.util.Scanner;


public class EmployeeFMSDriver implements EmployeeCRUD {

    static final String EMPLOYEE_FILENAME = "employee.csv";
    static final String EMPLOYEE_FILENAME_TEMP = "employeeTemp.csv";

    /**
     * Adds the employee to the system
     *
     * @param employee
     */
    public void create(final Employee employee) {
        boolean found = false;
        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == employee.getId()) {
                    found = true;
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            //ignoring
        }
        if (found)
            System.out.println("Employee wiht same id already exits!");
        else {
            try {
                PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME, true));
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
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                int key = Integer.parseInt(data[0]);
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
        return null;
    }

    /**
     * Updates the employee information
     *
     * @param id       the id of the employee to be updated
     * @param employee the employee object
     */
    public void update(int id, final Employee employee) {
        boolean found = false;
        int lineId;
        File inFile = new File(EMPLOYEE_FILENAME);
        File outFile = new File(EMPLOYEE_FILENAME_TEMP);
        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME_TEMP, true));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                lineId = Integer.parseInt(data[0]);
                if (lineId == id) {
                    found = true;
                    out.println(employee);
                } else {
                    out.println(line);
                }
            }
            in.close();
            out.close();
            if (found == false) {
                System.out.println("The employee id entered to update does not exist.");
            }
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
        int lineId;
        File inFile = new File(EMPLOYEE_FILENAME);
        File outFile = new File(EMPLOYEE_FILENAME_TEMP);
        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME_TEMP, true));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String data[] = line.split(",");
                lineId = Integer.parseInt(data[0]);
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

    public static void main(String args[]) {

        int usrInput;
        boolean sentinel = true;
        EmployeeFMSDriver impl = new EmployeeFMSDriver();
        Scanner scnUsrIn = new Scanner(System.in);

        System.out.println("Hello welcome to the .csv FMS\n");

        while (sentinel) {
            System.out.println("What would you like to do?\nEnter 1 to CREATE employee.\n" +
                    "Enter 2 to READ employee info.\nEnter 3 to UPDATE employee info.\nEnter 4 to DELETE employee.\n" +
                    "Enter 5 to QUIT.\n");
            System.out.print("Your input: ");
            usrInput = scnUsrIn.nextInt();

            if (usrInput == 1) {
                int id;
                String empName, empDep;
                System.out.print("\nEnter unique integer employee ID: ");
                id = scnUsrIn.nextInt();
                System.out.print("Enter employee NAME: ");
                empName = scnUsrIn.next();
                System.out.print("Enter employee DEPARTMENT: ");
                empDep = scnUsrIn.next();
                Employee newEmp = new Employee(id,empName,empDep);
                impl.create(newEmp);
            }
            else if (usrInput == 2){
                int id;
                String empStr;
                System.out.print("\nEnter the employee ID number to read: ");
                id = scnUsrIn.nextInt();
                System.out.println("\n"+impl.read(id)+"\n");
            }
            else if (usrInput == 3){
                int id;
                String empName, empDep;
                System.out.print("\nEnter the employee ID number to update: ");
                id = scnUsrIn.nextInt();
                System.out.print("Enter the new employee NAME: ");
                empName = scnUsrIn.next();
                System.out.print("Enter the new employee DEPARTMENT: ");
                empDep = scnUsrIn.next();
                Employee newEmp = new Employee(id,empName,empDep);
                impl.update(id,newEmp);
            }
            else if (usrInput == 4){
                int id;
                System.out.print("\nEnter employee ID to delete: ");
                id = scnUsrIn.nextInt();
                impl.delete(id);
            }
            else if (usrInput == 5) {
                sentinel = false;
                break;
            } else{
                System.out.println("Please enter an integer from 1 to 5.");
            }
        }
    }
}
