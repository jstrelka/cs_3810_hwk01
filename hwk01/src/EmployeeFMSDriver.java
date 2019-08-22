public class EmployeeFMSDriver implements EmployeeCRUD {
    public void create ( final Employee employee){}

    /**
     * Returns the employee if found
     * @param id the id of the employee to be returned
     * @return the employee object (null if not found)
     */
    public Employee read( int id){
        Employee mrJava2 = new Employee(5,"me", "hi");
        return mrJava2;
    }

    /**
     * Updates the employee information
     * @param id the id of the employee to be updated
     * @param employee the employee object
     */
    public void update ( int id, final Employee employee){}

    /**
     * Removes the employee specified by id
     * @param id the id of the employee to be deleted
     */

    public void delete(int id) {
        System.out.println("hello World!!\n");
    }

    public static void main(String args[]) {
        delete(5);
        Employee mrJava = new Employee(6, "mrJava", "school");

    }
}
