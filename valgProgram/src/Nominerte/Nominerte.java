package Nominerte;

import student.Student;

public class Nominerte extends Student {

	private int id;
	private int voteCount;

	public Nominerte(int id, int voteCount) {
		this.id = id;
		this.voteCount = voteCount;
	}

	public Nominerte(int studentId, String studentName, int programId, int voteCount) {
		super(studentId, studentName, programId);
		this.voteCount = voteCount;
	}

	public Nominerte() {

	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	public int getVoteCount() {
		return voteCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}





}
