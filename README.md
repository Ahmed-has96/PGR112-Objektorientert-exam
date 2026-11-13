# Studentvalg · Java og MySQL

Et konsollprogram der studenter logger inn med student-ID, nominerer en medstudent og stemmer på en kandidat. Programmet lagrer stemmer og kommentarer i MySQL ved hjelp av JDBC. Prosjektet er fra faget objektorientert programmering ved Høyskolen Kristiania.

## Les koden

- [Main.java](valgProgram/src/Main.java) viser menyene og flyten fra innlogging til avstemning.
- [NominerteDBSystem.java](valgProgram/src/Nominerte/NominerteDBSystem.java) henter kandidater og oppdaterer stemmetall.
- [VoteDBSystem.java](valgProgram/src/Nominerte/VoteDBSystem.java) registrerer stemmer og sjekker om en student allerede har stemt.

## Kjør lokalt

1. Bruk en lokal MySQL-instans for utprøving. Opprett en egen databasebruker som kan opprette og bruke databasene `studentDB` og `voteDB`.
2. Åpne `valgProgram` i en Java-IDE med JDK. Sett [src](valgProgram/src) som kildemappe og legg MySQL Connector/J på kjørebanen (classpath).
3. Sett `VOTE_DB_USER` og `VOTE_DB_PASSWORD` i den lokale kjørekonfigurasjonen. `VOTE_DB_HOST` og `VOTE_DB_PORT` er valgfrie; standard er `localhost` og `3306`.
4. Kjør [Main.java](valgProgram/src/Main.java). Programmet oppretter databaser og tabeller og legger inn eksempelstudenter ved første oppstart. Velg **3** i hovedmenyen for å se hvilke student-ID-er du kan bruke.
