package backend;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that creates a stand-alone server application. Dependent directories
 * will be automatically created at startup if they do not exist.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
@SpringBootApplication
public class Application {

	/**
	 * Starts up the server and checks if the Layouts, Blueprints, Buildings,
	 * and Notes directories exist. If not, creates them.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		// Create the Layouts directory on startup.
		File layoutsDirectory = new File("Layouts");
		if (!layoutsDirectory.exists()) {
			System.out.println("Creating the Layouts directory");
			try {
				layoutsDirectory.mkdir(); // create the directory!
			} catch (SecurityException e) {
				System.out.println("Encountered a security exception: ");
				e.printStackTrace();
			}
		}

		// Create the Blueprint directory on startup
		File blueprintDirectory = new File("Blueprints");
		if (!blueprintDirectory.exists()) {
			System.out.println("Creating the Blueprints directory");
			try {
				blueprintDirectory.mkdir(); // create the directory!
			} catch (SecurityException e) {
				System.out.println("Encountered a security exception: ");
				e.printStackTrace();
			}
		}

		// Create the Buildings directory on startup
		File buildingDirectory = new File("Buildings");
		if (!buildingDirectory.exists()) {
			System.out.println("Creating the Buildings directory");
			try {
				buildingDirectory.mkdir(); // create the directory!
			} catch (SecurityException e) {
				System.out.println("Encountered a security exception: ");
				e.printStackTrace();
			}
		}

		// Create the Notes directory on startup
		File notesDirectory = new File("Notes");
		if (!notesDirectory.exists()) {
			System.out.println("Creating the Notes directory");
			try {
				notesDirectory.mkdir(); // create the directory!
			} catch (SecurityException e) {
				System.out.println("Encountered a security exception: ");
				e.printStackTrace();
			}
		}

		// Run the application
		SpringApplication.run(Application.class, args);
	}
}
