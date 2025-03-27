import database.DatabaseCreation;
import student.Student;
import student.StudentDBSystem;
import Nominerte.Nominerte;
import Nominerte.NominerteDBSystem;
import Nominerte.Vote;
import Nominerte.VoteDBSystem;

import java.util.List;
import java.util.Scanner;

public class Main {


	private VoteDBSystem voteDBSystem = new VoteDBSystem();
	private DatabaseCreation databaseCreation = new DatabaseCreation();
	private NominerteDBSystem nominerteDBSystem = new NominerteDBSystem();

	private StudentDBSystem studentDBSystem = new StudentDBSystem();
	private Student currentUser = null;

	public static void main(String[] args) {
		Main main = new Main();
		main.run();

	}

	public void run() {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			if (currentUser == null) {
				System.out.println("Velkommen! Her er valgene dine:");
				System.out.println("1. Logg inn");
				System.out.println("2. Se høyest nominert");
				System.out.println("3. Skriv ut alle studenter");

				System.out.println("4. Avslutt");

				int option = getIntInput(scanner, 1, 3);
				switch (option) {
					case 1:
						signIn(scanner);
						break;
					case 2:
						seeTopNominee();
						break;
					case 3:
						printAllStudents();
						break;
					case 4:
						exit = true;
						System.out.println("Hade!");
						break;
				}
			} else if (!voteDBSystem.hasVoted(currentUser.getStudentId())) {
				System.out.println("Velkommen, " + currentUser.getStudentName() + "! Du har ikke stemt ennå. Her er valgene dine:");
				System.out.println("1. Se alle nominerte");
				System.out.println("2. Velg en du vil nominere");
				System.out.println("3. Stem og legg til kommentar på en nominert");
				System.out.println("4. Print alle studenter");
				System.out.println("5. Gå tilbake til hovedmenyen.");

				int option = getIntInput(scanner, 1, 5);
				switch (option) {
					case 1:
						seeAllNominees();
						break;
					case 2:
						proposeNewNominee(scanner);
						break;
					case 3:
						voteAndAddComment(scanner);
						break;
					case 4:
						printAllStudents();
						break;
					case 5:
						currentUser = null;
						break;
				}
			} else {
				System.out.println("Velkommen, " + currentUser.getStudentName() + "! Du har stemt. Her er valgene dine:");
				System.out.println("1. Se alle nominerte");
				System.out.println("2. Velg ny nominert");
				System.out.println("3. Forandre kommentaren din til nominerte");
				System.out.println("4. Se nominerte med høyest antall stemmer");
				System.out.println("5. Se stemme antallet for alle nominerte");
				System.out.println("6. Gå tilbake til hovedmeny");

				int option = getIntInput(scanner, 1, 6);
				switch (option) {
					case 1:
						seeAllNominees();
						break;
					case 2:
						proposeNewNominee(scanner);
						break;
					case 3:
						changeCommentForNominee(scanner);
						break;
					case 4:
						seeTopNominee();
						break;
					case 5:
						seeVotesForAllNominees();
						break;
					case 6:
						System.out.println("Går tilbake til hovedmeny...");
						currentUser = null;
						break;
					default:
						System.out.println("Ugyldig valg. Prøv igjen.");
				}
			}
		}
	}

	private void printAllStudents() {
		List<Student> students = studentDBSystem.getAllStudent();
		for (Student student : students) {
			System.out.println("Id: " + student.getStudentId() + "  Navn: " + student.getStudentName() + "  Program id: " + student.getProgramId());
		}
	}

	private void seeVotesForAllNominees() {
		List<Nominerte> allNominertes = nominerteDBSystem.getAllNominees();

		if (allNominertes.isEmpty()) {
			System.out.println("Ingen nominerte funnet.");
			return;
		}

		System.out.println("Stemmer og kommentarer for nominerte så langt:");

		for (Nominerte nominerte : allNominertes) {
			System.out.println(nominerte.getStudentName() + " - Stemmer: " + nominerte.getVoteCount());
		}
	}

	private void changeCommentForNominee(Scanner scanner) {

		System.out.println("Skriv ny kommentar");
		String changeCommentChoice = scanner.next().trim().toLowerCase();
		voteDBSystem.updateCommentForStudent(currentUser.getStudentId(), changeCommentChoice);
	}

	private void signIn(Scanner scanner) {
		System.out.print("Student id: ");
		int studentId = getIntInput(scanner, 1, Integer.MAX_VALUE);

		currentUser = studentDBSystem.getStudentById(studentId);
		if (currentUser == null) {
			System.out.println("Bruker ikke funnet. Prøv igjen");
		} else {
			System.out.println("Velkommen, " + currentUser.getStudentName() + "!");
		}
	}

	private void seeAllNominees() {
		if (nominerteDBSystem.getAllNominees().size() == 0) {
			System.out.println("Nominer noen først:");
			return;
		}
		System.out.println("Nominerte er:");
		int position = 1;
		for (Nominerte nominerte : nominerteDBSystem.getAllNominees()) {
			System.out.println(position + ". " + nominerte.getStudentName());
			position++;
		}
	}

	private void proposeNewNominee(Scanner scanner) {
		if (currentUser == null) {
			System.out.println("Logg inn først.");
			return;
		}


		System.out.print("Utvelger ny nominert. Skriv inn student id: ");
		int nomineeStudentId = getIntInput(scanner, 1, Integer.MAX_VALUE);

		if (studentDBSystem.getStudentById(currentUser.getStudentId()).getProgramId() != studentDBSystem.getStudentById(nomineeStudentId).getProgramId()) {
			System.out.println("Du kan ikke utvelge en nominert fra et annet program.");
			return;

		}

		Student nomineeStudent = studentDBSystem.getStudentById(nomineeStudentId);
		if (nomineeStudent.getStudentId() == (currentUser.getStudentId())) {
			System.out.println("Du kan ikke nominere deg selv.");
		} else if (nomineeStudent == null) {
			System.out.println("Student med id " + nomineeStudentId + " ikke funnet.");
		} else if (nominerteDBSystem.isStudentNominee(nomineeStudentId)) {
			System.out.println(nomineeStudent.getStudentName() + " er allerede nominert.");
		} else {
			nominerteDBSystem.insertNewNominee(nomineeStudentId);
			System.out.println("Du har nominert " + nomineeStudent.getStudentName());
		}
	}	private int getIntInput(Scanner scanner, int min, int max) {
		while (true) {
			try {
				int input = Integer.parseInt(scanner.next());
				if (input >= min && input <= max) {
					return input;
				}
				System.out.println("Takk og farvel :)");
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig. Skriv et tall.");
			}
		}
	}

	private void seeTopNominee() {
		Nominerte topNominerte = nominerteDBSystem.getTopNominee();
		System.out.println("Nominerte med høyest antall stemmer er:");
		System.out.println("---");
		System.out.println(topNominerte.getStudentName() + " - " + topNominerte.getVoteCount());
		System.out.println("Kommentarer:");

		for (Vote vote : voteDBSystem.getVotesByNominee(topNominerte.getId())) {
			System.out.println("\"" + vote.getComment() + "\"");
		}
		System.out.println("---");
	}

	private void voteAndAddComment(Scanner scanner) {
		if (currentUser == null) {
			System.out.println("Logg inn først.");
			return;
		}


		if (nominerteDBSystem.getAllNominees().size() == 0) {
			System.out.println("Nominer noen først:");
			return;

		}
		System.out.println("Nominerte er:");
		int position = 1;
		for (Nominerte nominerte : nominerteDBSystem.getAllNominees()) {
			System.out.println(position + ". " + nominerte.getStudentName());
			position++;
		}

		System.out.print("Hvem vil du stemme på? (Velg nummer): ");
		int selectedNominee = getIntInput(scanner, 1, nominerteDBSystem.getAllNominees().size());

		if (studentDBSystem.getStudentById(currentUser.getStudentId()).getProgramId() != studentDBSystem.getStudentById(nominerteDBSystem.getNomineeByStudentId(selectedNominee).getStudentId()).getProgramId()) {
			System.out.println("Du kan ikke velge nominerte som tilhører et annet program.");
			return;

		}
		Nominerte nominerte = nominerteDBSystem.getAllNominees().get(selectedNominee - 1);
		nominerteDBSystem.updateVoteCount(nominerte.getId());
		System.out.print("Vil du legge til en kommentar (j/n): ");
		String addComment = scanner.next();

		if (addComment.equalsIgnoreCase("j")) {
			System.out.print("Legg til kommentar: ");
			scanner.nextLine();
			String comment = scanner.nextLine();
			voteDBSystem.insertNewVote(nominerte.getId(), comment, currentUser.getStudentId());
		}
		else {voteDBSystem.insertNewVote(nominerte.getId(),"" , currentUser.getStudentId());}

		System.out.println("Velkommen, " + currentUser.getStudentName() + "! Du har stemt.");
	}

}
