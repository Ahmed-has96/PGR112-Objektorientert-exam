package student;

public class Student {
	private int studentId;
	private String studentName;
	private int programId;

	public Student() {

	}

	public Student(int studentId, String studentName, int programId) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.programId = programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getProgramId() {
		return programId;
	}
}


