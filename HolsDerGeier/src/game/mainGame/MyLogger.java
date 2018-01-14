/**
 * 
 */
package game.mainGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

/**
 * @author Sir.MoM
 * @version 1.0
 * @since 25.09.2017
 */
public class MyLogger{
	
	
	/**
	 * Das Verzeichnis das vorhanden sein muss wir ggf. erstellt
	 */
	final public static File DASVERZEICHNIS = new File(System.getProperties().getProperty("user.home") + "\\Documents\\Noah's Bot\\" );
	
	/**
	 * Die benutzte CSV-Datei
	 */
	final public static File GESPIELTEZUEGE = new File(DASVERZEICHNIS.getPath() + "\\gespielteZuege.csv");
	
	/**
	 * Tabellenkopf Vorlage
	 */
	final public static String TABLESTSART = "Punktekarte; Meine Karte; Gergner Karte; Gewonnen ?";

	/**
	 * Tabellenkopf Formvorlage <p>
	 * Punktekarte; Meine Karte; Gergner Karte; Gewonnen
	 */
	final public static String TABLEFORM = "%s; %s; %s; %s";

	
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yy hh_mm_ss");

	private static File loggerFile = new File(DASVERZEICHNIS.getAbsolutePath().toString() + "\\LOG" + LocalDateTime.now().format(df) + ".log");
	
	private static BufferedWriter loggerFileWriter;
	
	private static BufferedWriter kartenCsvFileWriter;
	
	public static void log(String str) {
		try {
			loggerFileWriter.write("[LOG][" + LocalDateTime.now().format(df) + "] " + str + "\n");
			loggerFileWriter.newLine();
			loggerFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static void log(String str, Exception exception) {
		try {
			loggerFileWriter.write("[EXEPTION]" + "[" + LocalDateTime.now().format(df) + "]" + "["+ exception.getMessage() +"]" + str + "\n");
			loggerFileWriter.newLine();
			loggerFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void createLoggingFiles() {

		createDir();
		if(!loggerFile.exists()) {
			try {
				loggerFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {			
			loggerFileWriter = new BufferedWriter(new FileWriter(loggerFile));
			loggerFileWriter.write("Sir.MoM " + LocalDateTime.now().format(df) + " LOG START");
			loggerFileWriter.newLine();
			loggerFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!GESPIELTEZUEGE.exists()) {
			try {
				GESPIELTEZUEGE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {			
			kartenCsvFileWriter = new BufferedWriter(new FileWriter(GESPIELTEZUEGE, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createDir() {
		if(!DASVERZEICHNIS.exists()) {
			DASVERZEICHNIS.mkdir();
		}
	}

	public static void logTable(int punktekarte, int meineKarte, int gegnerKarte, String whoWon) {
		String tempLine = String.format(TABLEFORM, String.valueOf(punktekarte), String.valueOf(meineKarte), String.valueOf(gegnerKarte), whoWon);
		try {
			kartenCsvFileWriter.write(tempLine);
			kartenCsvFileWriter.newLine();
			kartenCsvFileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
