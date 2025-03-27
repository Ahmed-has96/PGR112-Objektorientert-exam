package Nominerte;

public class Vote {

	private int id;

	private String comment;
	private int student_id;
	private int nomineeId;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(int nomineeId) {
		this.nomineeId = nomineeId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}



}
