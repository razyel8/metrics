package org.pwr.runners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import static java.lang.Runtime.getRuntime;

public class CommandLineExecutor {


	public static String runScriptAndGetOutput(String command) throws IOException {

		String allLines = "";

		Process p = null;
		try {
			StringBuffer output = new StringBuffer();
			p = Runtime.getRuntime().exec("cmd /c " + command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			System.out.println();
			allLines = output.toString();
			reader.close();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allLines;
	}


	public static void runRScriptFromPath() {

		String rscriptClasspath = findOutRScriptClasspath();
		String rscriptFilepath = findOutRScriptFilePath();

		if (StringUtils.isEmpty(rscriptClasspath) || StringUtils.isEmpty(rscriptFilepath)) {
			throw new IllegalArgumentException(
					"Specified path to specified script in R file and//or path to R classpath cannot be null or empty. Please check application.properties file.");
		}

		try {
			Runtime.getRuntime().exec(rscriptClasspath + rscriptFilepath);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Invalid path to RScript classpath: " + rscriptClasspath + " and//or specified script in R: "
							+ rscriptFilepath + ". Please check application.properties file.");
		}
	}

	public static void runGitCheckout(String commitHash) {

		Process p = null;
		try {
			StringBuffer output = new StringBuffer();
			p = Runtime.getRuntime().exec("cmd /c cd bat_files && start gitCheckout.bat " + commitHash);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			System.out.println();
			reader.close();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String findOutRScriptFilePath() {
		return ResourceBundle.getBundle(CommandLineExecutorConstants.PROPERTIES_FILENAME)
				.getString(CommandLineExecutorConstants.PROPERTIES_RSCRIPT_FILEPATH_PROPERTY_KEY);
	}

	private static String findOutRScriptClasspath() {
		return ResourceBundle.getBundle(CommandLineExecutorConstants.PROPERTIES_FILENAME)
				.getString(CommandLineExecutorConstants.PROPERTIES_RSCRIPT_CLASSPATH_PROPERTY_KEY);
	}

	// public static String getPathToRScriptFromConfig(){
	// return "C:\\Program Files\\R\\R-3.3.1\\bin\\RScript";
	// }
	//
	// public static String runRScriptFromPath(String filePath){
	// if(filePath == null){
	// return "Invalid path to file!";
	// }
	// try {
	// Runtime.getRuntime().exec("C:\\Program Files\\R\\R-3.3.1\\bin\\RScript
	// "+filePath);
	// } catch (IOException e) {
	// e.printStackTrace();
	// return "Invalid path to file "+ filePath +"!";
	// }
	//
	// return "Success! " + filePath + " script executed succesfully";
	// }

	// public static String executeCommand(String command) {
	// try {
	// // Execute command
	// command = "cmd /c start cmd.exe " + command;
	// Process child = getRuntime().exec(command);
	//
	// // Get output stream to write from it
	// OutputStream out = child.getOutputStream();
	//
	// out.write("cd C:/ /r/n".getBytes());
	// out.flush();
	// out.write("dir /r/n".getBytes());
	// out.close();
	// return out.toString();
	// } catch (IOException e) {
	// }
	// return null;
	// }

	// public static String PATH = "";

	// public static String executeCommand2(String command) {
	// StringBuffer output = new StringBuffer();
	//
	// Process p;
	// try {
	// p = getRuntime().exec("cmd /c " + command);
	// p.waitFor();
	// BufferedReader reader =
	// new BufferedReader(new InputStreamReader(p.getInputStream()));
	//
	// String line = "";
	// while ((line = reader.readLine()) != null) {
	// output.append(line + "\n");
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return output.toString();
	// }

	public static void main(String[] args) {

		// INITIAL: 8119659fb1e4ae6fabe8897c42ba7629fda07b21

		// Second: cfdb8e01ebecb92abf87b3b68abd3e4c5dfb4d03

		// c036e4019f2ebfbe6a94cc6b8bb0acff98c7dfa5
		// b447d7dbe644dc095dd1afc99c4d3fd31e9732f3
		// b3e94dc747715b468775312458ac0c9eef6f89f8

		// dc080cb1be4c35398d1d995c3bb8025ccfde6dea

		// goToCommit("cfdb8e01ebecb92abf87b3b68abd3e4c5dfb4d03");
		// goToCommit("b447d7dbe644dc095dd1afc99c4d3fd31e9732f3");
		// goToCommit("b3e94dc747715b468775312458ac0c9eef6f89f8");

		// ResourceBundle bundle = ResourceBundle.getBundle("application");
		// String value = bundle.getString("rscript_filepath");
		// System.out.println(value);
	}

}
