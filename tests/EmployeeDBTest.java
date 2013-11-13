package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
	private Employee employee1;
	private Employee employee2;
	private Employee employee3;

	@Before
	public void setUp() throws Exception {
		employeeDB = SimpleEmployeeDB.getInstance();
		
		employee1 = new Employee();
		SetEmployeeProperties(employee1, 1, "Jon", 1, 100);
		employee2 = new Employee();
		SetEmployeeProperties(employee2, 2, "Georg", 2, 200);
		employee3 = new Employee();
		SetEmployeeProperties(employee3, 3, "Bin", 1, 100);
		
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
		employeeDB.addEmployee(employee1);
		employeeDB.addEmployee(employee2);
		employeeDB.addEmployee(employee3);
		assertEquals(3, employeeDB.listAllEmployees().size());
	}

	@Test
	public void testListAllEmployees() {
		List<Employee> empList = employeeDB.listAllEmployees();
		AssertSameEmployee(employee1, empList.get(0));
		AssertSameEmployee(employee2, empList.get(1));
		AssertSameEmployee(employee3, empList.get(2));
	}

	@Test
	public void testListEmployeesInDept() {
		List<Integer> dep1 = new ArrayList<Integer>();
		dep1.add(1);
		List<Employee> dep1EmployeeList = employeeDB.listEmployeesInDept(dep1);
		assertEquals(2, dep1EmployeeList.size());
		
		List<Integer> dep2 = new ArrayList<Integer>();
		dep2.add(2);
		List<Employee> dep2EmployeeList = employeeDB.listEmployeesInDept(dep2);
		assertEquals(1, dep2EmployeeList.size());
		
		List<Integer> allDeps = new ArrayList<Integer>();
		allDeps.add(1);
		allDeps.add(2);
		List<Employee> allDepsEmployeeList = employeeDB.listEmployeesInDept(allDeps);
		assertEquals(3, allDepsEmployeeList.size());
		
		List<Integer> emptyDep = new ArrayList<Integer>();
		List<Employee> emptyDepEmployeeList = employeeDB.listEmployeesInDept(emptyDep);
		assertEquals(0, emptyDepEmployeeList.size());
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
