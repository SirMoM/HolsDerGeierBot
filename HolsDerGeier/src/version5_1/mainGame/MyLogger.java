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
	
	final String 
	
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yy hh_mm_ss");

	private static File loggerFile = new File(FileUsingClass.DASVERZEICHNIS.getAbsolutePath().toString() + "\\LOG" + LocalDateTime.now().format(df) + ".log");
	
	private static BufferedWriter loggerFileWriter;
	
	
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
			loggerFileWriter.write("["+ exception.getMessage() +"]"+"[" + LocalDateTime.now().format(df) + "] " + str + "\n");
			loggerFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void createLoggingDatei() {

		FileUsingClass.createDir();
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
	}
	
	
	
}
