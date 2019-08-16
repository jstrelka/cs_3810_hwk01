/**
 * CS3810 - Principles Database Systems - Fall 2019
 * An interface that defines CRUD operations on Employee objects
 * @author Thyago Mota
 * @date Aug-16-2018
 */
public interface EmployeeCRUD {

    /**
     * Adds the employee to the system
     * @param employee
     */
    void create(final Employee employee);

    /**
     * Returns the employee if found
     * @param id the id of the employee to be returned
     * @return the employee object (null if not found)
     */
    Employee read(int id);

    /**
     * Updates the employee information
     * @param id the id of the employee to be updated
     * @param employee the employee object
     */
    void update(int id, final Employee employee);

    /**
     * Removes the employee specified by id
     * @param id the id of the employee to be deleted
     */
    void delete(int id);

}
