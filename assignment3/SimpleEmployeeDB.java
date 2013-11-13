package assignment3;

import java.util.List;

public class SimpleEmployeeDB implements EmployeeDB {
	private static SimpleEmployeeDB instance = null;

	private SimpleEmployeeDB() {

	}

	public synchronized static SimpleEmployeeDB getInstance() {
		if (instance == null) {
			instance = new SimpleEmployeeDB();
		}
		return instance;
	}

	@Override
	public synchronized void addEmployee(Employee emp) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized List<Employee> listAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized List<Employee> listEmployeesInDept(
			List<Integer> departmentIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void incrementSalaryOfDepartment(
			List<SalaryIncrement> salaryIncrements)
			throws DepartmentNotFoundException,
			NegativeSalaryIncrementException {
		// TODO Auto-generated method stub
	}

}
