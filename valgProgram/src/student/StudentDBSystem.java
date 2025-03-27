package student;



import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentDBSystem {

	private static final DatabaseConnection database = new DatabaseConnection("localhost", "3306",
			"studentDB",
			"root",
			"Arsenals12"
	);
	static {
		String createStudentsTableQuery =
				"CREATE TABLE IF NOT EXISTS Students ("
				+ "student_id INT PRIMARY KEY,"
				+ "student_name VARCHAR(100),"
				+ "program_id INT,"
				+ "is_voted boolean"
				+ ")";

		try (Connection connection = database.getConnection()) {
			Statement statement = connection.createStatement();
			statement.execute(createStudentsTableQuery);
			if(!isAlreadyInserted()) {
				String insertStudentQuery = "INSERT INTO Students (student_id, student_name, program_id, is_voted) VALUES (?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(insertStudentQuery);

				insertStudent(preparedStatement, 1, "amina", 1);
				insertStudent(preparedStatement, 2, "maria", 2);
				insertStudent(preparedStatement, 3, "kristian", 1);
				insertStudent(preparedStatement, 4, "katrine", 2);
				insertStudent(preparedStatement, 5, "petter", 1);
				insertStudent(preparedStatement, 6, "hans", 2);
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}



	}


	private static void insertStudent(PreparedStatement preparedStatement, int studentId, String studentName, int programId) throws SQLException {
		preparedStatement.setInt(1, studentId);
		preparedStatement.setString(2, studentName);
		preparedStatement.setInt(3, programId);
		preparedStatement.setBoolean(4, false);
		preparedStatement.executeUpdate();
	}

	private static boolean isAlreadyInserted(){
		String countStudentsQuery = "SELECT COUNT(*) FROM Students";

		try (Connection connection = database.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(countStudentsQuery)) {

			if (resultSet.next()) {
				int studentCount = resultSet.getInt(1);
				if(studentCount>0)
					return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<Student> getAllStudent() {
		List<Student> students = new ArrayList<>();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Students";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("student_id");
				String studentName = resultSet.getString("student_name");
				int programId = resultSet.getInt("program_id");
				Student student = new Student(id, studentName, programId);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public Student getStudentById(int studentId) {

		Student student=null;
		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Students WHERE student_Id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, studentId);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("student_id");
				String studentName = resultSet.getString("student_name");
				int programId = resultSet.getInt("program_id");
				student = new Student(studentId, studentName, programId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}
}
