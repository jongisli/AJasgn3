package assignment3;

import java.util.ArrayList;
import java.util.List;

public class SimpleEmployeeDB implements EmployeeDB {
	private static SimpleEmployeeDB instance = null;

	private List<Employee> instanceList = new ArrayList<Employee>();

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
		instanceList.add(emp);
	}

	@Override
	public synchronized List<Employee> listAllEmployees() {

		return instanceList;
	}

	@Override
	public synchronized List<Employee> listEmployeesInDept(
			List<Integer> departmentIds) {

		ArrayList<Employee> employeeListInDep = new ArrayList<Employee>();
		for (int i = 0; i < departmentIds.size(); i++) {
			for (int j = 0; j < instanceList.size(); j++) {
				if (instanceList.get(j).getDepartment() == departmentIds.get(i)) {
					employeeListInDep.add(instanceList.get(j));
				}

			}

		}
		return employeeListInDep;
	}

	@Override
	public synchronized void incrementSalaryOfDepartment(
			List<SalaryIncrement> salaryIncrements)
			throws DepartmentNotFoundException,
			NegativeSalaryIncrementException {

		List<Integer> allDept = new ArrayList<Integer>();
		List<Boolean> checkDep = new ArrayList<Boolean>();
		List<Boolean> checkSal = new ArrayList<Boolean>();

		for (int i = 0; i < instanceList.size(); i++) {
			allDept.add(instanceList.get(i).getDepartment());
		}

		for (int m = 0; m < salaryIncrements.size(); m++) {
			checkDep.add(allDept.contains(salaryIncrements.get(m)
					.getDepartment()));
			checkSal.add(salaryIncrements.get(m).getIncrementBy() >= 0);
		}

		if (checkDep.contains(false)) {
			throw new DepartmentNotFoundException();
		}

		if (checkSal.contains(false)) {
			throw new NegativeSalaryIncrementException();
		}

		for (int i = 0; i < salaryIncrements.size(); i++) {
			for (int j = 0; j < instanceList.size(); j++) {
				instanceList.get(j).setSalary(
						instanceList.get(j).getSalary()
								+ salaryIncrements.get(i).getIncrementBy());
			}

		}
	}

}
