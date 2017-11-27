/**
 * 
 */
package version5_1.mainGame;

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
	final static File DASVERZEICHNIS = new File(System.getProperties().getProperty("user.home") + "\\Documents\\Noah's Bot\\" );
	
	/**
	 * Die benutzte CSV-Datei
	 */
	final static File KARTENCSV = new File(DASVERZEICHNIS.getPath() + "\\kartencsv.csv");
	
	/**
	 * Tabellenkopf Vorlage
	 */
	final public static String TABLESTSART = "Punktekarte; Meine Karte; Gergner Karte; Gewonnen ? \n";

	/**
	 * Tabellenkopf Formvorlage
	 */
	final public static String TABLEFORM = "%s; %s; %s; %s ? \n";

	
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yy hh_mm_ss");

	private static File loggerFile = new File(DASVERZEICHNIS.getAbsolutePath().toString() + "\\LOG" + LocalDateTime.now().format(df) + ".log");
	
	private static BufferedWriter loggerFileWriter;
	
	private static BufferedWriter kartenCsvFileWriter;
	
	public static void log(String str) {
		try {
			loggerFileWriter.write("[LOG][" + LocalDateTime.now().format(df) + "] " + str + "\n");
			loggerFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static void log(String str, Exception exception) {
		try {
			loggerFileWriter.write("[EXEPTION]" + "[" + LocalDateTime.now().format(df) + "]" + "["+ exception.getMessage() +"]" + str + "\n");
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
		
		if(!KARTENCSV.exists()) {
			try {
				KARTENCSV.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {			
			kartenCsvFileWriter = new BufferedWriter(new FileWriter(KARTENCSV));
			kartenCsvFileWriter.write("Naoh Ruben " + LocalDateTime.now().format(df) + " TABELS START");
			kartenCsvFileWriter.newLine();
			kartenCsvFileWriter.write(TABLESTSART);
			kartenCsvFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createDir() {
		if(!DASVERZEICHNIS.exists()) {
			DASVERZEICHNIS.mkdir();
		}
	}
	
	
}
