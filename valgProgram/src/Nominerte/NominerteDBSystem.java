package Nominerte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.DatabaseConnection;
import student.Student;
import student.StudentDBSystem;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class NominerteDBSystem {
	private static final DatabaseConnection database = new DatabaseConnection("localhost", "3306",
			"voteDB",
			"root",
			"Arsenals12"
	);

	static {
		try (Connection connection = database.getConnection()) {
			String createNomineeTableQuery = "CREATE TABLE IF NOT EXISTS Nominees ("
					+ "id INT PRIMARY KEY AUTO_INCREMENT,"
					+ "student_id INT,"
					+ "vote_count INT"
					+ ")";

			PreparedStatement statement = connection.prepareStatement(createNomineeTableQuery);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Nominerte> getAllNominees() {
		List<Nominerte> nominertes = new ArrayList<>();
		StudentDBSystem studentDBSystem =new StudentDBSystem();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Nominees";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int studentId = resultSet.getInt("student_id");
				int voteCount = resultSet.getInt("vote_count");
				Student student = studentDBSystem.getStudentById(studentId);

				Nominerte nominerte = new Nominerte();
				nominerte.setId(id);
				nominerte.setStudentId(studentId);
				nominerte.setVoteCount(voteCount);
				nominerte.setProgramId(student.getProgramId());
				nominerte.setStudentName(student.getStudentName());

				nominertes.add(nominerte);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nominertes;
	}
	public void insertNewNominee(int studentId) {
		try (Connection connection = database.getConnection()) {
			String insertQuery = "INSERT INTO Nominees (student_id, vote_count) VALUES (?, 0)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateVoteCount(int nomineeId) {
		try (Connection connection = database.getConnection()) {
			String updateQuery = "UPDATE Nominees SET vote_count = vote_count + 1 WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setInt(1, nomineeId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Nominerte getStudentNominee(int studentId) {
		Nominerte nominerte = null;
		StudentDBSystem studentDBSystem = new StudentDBSystem();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Nominees where student_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("student_id");
				int voteCount = resultSet.getInt("vote_count");
				Student student = studentDBSystem.getStudentById(studentId);

				nominerte = new Nominerte();
				nominerte.setId(id);
				nominerte.setStudentId(studentId);
				nominerte.setVoteCount(voteCount);
				nominerte.setProgramId(student.getProgramId());
				nominerte.setStudentName(student.getStudentName());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nominerte;
	}
	public boolean isStudentNominee( int studentId) {

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Nominees where student_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Nominerte getNomineeByStudentId(int nomineeId) {

		StudentDBSystem studentDBSystem =new StudentDBSystem();
		Nominerte nominerte =null;
		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Nominees where id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, nomineeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");

				int voteCount = resultSet.getInt("vote_count");
				int stu_id = resultSet.getInt("student_id");

				Student student = studentDBSystem.getStudentById(stu_id);

				nominerte = new Nominerte();
				nominerte.setId(id);
				nominerte.setStudentId(stu_id);
				nominerte.setVoteCount(voteCount);
				nominerte.setProgramId(student.getProgramId());
				nominerte.setStudentName(student.getStudentName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nominerte;
	}

	public Nominerte getTopNominee() {
		Nominerte topNominerte = null;
		StudentDBSystem studentDBSystem = new StudentDBSystem();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Nominees ORDER BY vote_count DESC LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int nomineeId = resultSet.getInt("id");
				int studentId = resultSet.getInt("student_id");
				int voteCount = resultSet.getInt("vote_count");

				// Get student details
				Student student = studentDBSystem.getStudentById(studentId);

				topNominerte = new Nominerte(studentId, student.getStudentName(), student.getProgramId(), voteCount);
				topNominerte.setId(nomineeId);
				topNominerte.setVoteCount(voteCount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return topNominerte;
	}


}