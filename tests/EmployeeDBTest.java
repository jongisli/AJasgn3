package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import assignment3.Employee;
import assignment3.EmployeeDB;
import assignment3.SimpleEmployeeDB;

/**
 * Generated JUnit test class to test the EmployeeDB interface. It can be used
 * to test both the client and the server implementation.
 * 
 * @author bonii
 * 
 */
public class EmployeeDBTest {
	private EmployeeDB employeeDB = null;

	@Before
	public void setUp() throws Exception {
		employeeDB = SimpleEmployeeDB.getInstance();
		
		// By setting the employeeDB
		// to either the server implementation first, you can write the server
		// implementation and test it. You dont even have to run the Jetty
		// HTTP server, you can just implement SimpleEmployeeDB and test it
		//
		// employeeDB = new EmployeeDBHTTPClient(); Once you have written the
		// client you can test the full system for the same test cases and it
		// should work, welcome to Unit testing :-)
		//
	}

	@Test
	public void testAddEmployee() {
		assertEquals(0, employeeDB.listAllEmployees().size());
		
		Employee employee = new Employee();
		employee.setId(1);
		employee.setDepartment(1);
		employee.setName("Georg Zou Egilsson");
		employee.setSalary(1000000);
		
		employeeDB.addEmployee(employee);
		
		assertEquals(1, employeeDB.listAllEmployees().size());
	}

	@Test
	public void testListAllEmployees() {
		Employee employee1 = new Employee();
		SetEmployeeProperties(employee1, 1, "Jon", 1, 100);
		Employee employee2 = new Employee();
		SetEmployeeProperties(employee2, 2, "Georg", 2, 200);
		Employee employee3 = new Employee();
		SetEmployeeProperties(employee3, 3, "Bin", 1, 100);
		
		employeeDB.addEmployee(employee1);
		employeeDB.addEmployee(employee2);
		employeeDB.addEmployee(employee3);
		
		List<Employee> empList = employeeDB.listAllEmployees();
		
		AssertSameEmployee(employee1, empList.get(0));
		AssertSameEmployee(employee2, empList.get(1));
		AssertSameEmployee(employee3, empList.get(2));
	}

	@Test
	public void testListEmployeesInDept() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrementSalaryOfDepartment() {
		fail("Not yet implemented");
	}
	
	private void SetEmployeeProperties(Employee empl, int id, String name, int department, float salary)
	{
		empl.setId(id);
		empl.setName(name);
		empl.setDepartment(department);
		empl.setSalary(salary);
	}
	
	private void AssertSameEmployee(Employee expected, Employee actual)
	{
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getSalary(), actual.getSalary(), 0.00001);
	}
	

}
