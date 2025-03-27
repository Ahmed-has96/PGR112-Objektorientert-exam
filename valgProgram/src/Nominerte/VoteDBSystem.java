package Nominerte;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDBSystem {
	private static final DatabaseConnection database = new DatabaseConnection("localhost", "3306",
			"voteDB",
			"root",
			"Arsenals12"
	);

	static  {
		try (Connection connection = database.getConnection()) {
			String createVoteTableQuery = "CREATE TABLE IF NOT EXISTS Votes ("
					+ "id INT PRIMARY KEY AUTO_INCREMENT,"
					+ "nominee_id INT,"
					+ "student_id INT,"
					+ "comment VARCHAR(255)"
					+ ")";

			PreparedStatement statement = connection.prepareStatement(createVoteTableQuery);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Vote> getAllVotes() {
		List<Vote> votes = new ArrayList<>();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Votes";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int nomineeId = resultSet.getInt("nominee_id");
				String comment = resultSet.getString("comment");

				Vote vote = new Vote();
				vote.setId(id);
				vote.setNomineeId(nomineeId);
				vote.setComment(comment);

				votes.add(vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return votes;
	}


	public void insertNewVote(int nomineeId, String comment, int studentId) {
		try (Connection connection = database.getConnection()) {
			String insertQuery = "INSERT INTO Votes (nominee_id, comment, student_id) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, nomineeId);
			preparedStatement.setString(2, comment);
			preparedStatement.setInt(3, studentId);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean hasVoted(int studentId) {
		String query = "SELECT COUNT(*) FROM Votes WHERE student_id = ?";

		try (Connection connection = database.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, studentId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}


	public List<Vote> getVotesByNominee(int nomineeId) {
		List<Vote> votes = new ArrayList<>();

		try (Connection connection = database.getConnection()) {
			String selectQuery = "SELECT * FROM Votes WHERE nominee_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, nomineeId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String comment = resultSet.getString("comment");

				Vote vote = new Vote();
				vote.setId(id);
				vote.setNomineeId(nomineeId);
				vote.setComment(comment);

				votes.add(vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return votes;
	}



	public void updateCommentForStudent(int studentId, String newComment) {
		String updateCommentQuery = "UPDATE Votes SET comment = ? WHERE nominee_id = ?";

		try (Connection connection = database.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(updateCommentQuery)) {

			preparedStatement.setString(1, newComment);
			preparedStatement.setInt(2, studentId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Comment updated successfully.");
			} else {
				System.out.println("No records found for the given student ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}