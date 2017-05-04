package backend;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The BlueprintController class handles the interactions between the frontend
 * web application and the server at the backend.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
@RestController
public class BlueprintController {
	/**
	 * Receives the converted base64 string representation of the blueprint
	 * image from the frontend. The string sent by the web application contains
	 * the name of the blueprint, the height, width, and the actual base64
	 * representation of the image separated by commas. The base64
	 * representation is stored in a text file in the Blueprints directory, and
	 * the name of the blueprint is also updated in the BlueprintsTable.txt
	 * file.
	 * 
	 * @param data
	 *            A long string containing the name, height, width, and the
	 *            base64 representation of the blueprint image.
	 * @throws Exception
	 *             FileNotFound Exception
	 */
	@CrossOrigin
	@RequestMapping(value = "/blueprint", method = RequestMethod.POST)
	public void getBlueprint(@RequestParam(value = "data", defaultValue = "") String data) throws Exception {
		String[] blueprint = data.split(","); // Split each field of the string
												// by comma
		String name = blueprint[0]; // Get the name of the blueprint

		File blueprintFile = new File("Blueprints/" + name + ".txt");

		if (!blueprintFile.exists()) {
			File f = new File("Blueprints/BlueprintsTable.txt");
			if (f.exists()) { // Update the BlueprintsTable file if it exists
				PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(f, true)));
				output.println(blueprint[0]);
				output.close();
			} else { // If it doesn't exist, create and write the current
						// blueprint's name to it
				System.out.println("BlueprintsTable hasn't been created, creating now...");
				f.createNewFile();
				PrintStream output = new PrintStream(f);
				output.println(blueprint[0]);
				output.close();
			}
		}

		// Create and write the current blueprint's data to it's text file
		try (PrintWriter out = new PrintWriter("Blueprints/" + name + ".txt")) {
			out.println(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the list of saved blueprint names.
	 * 
	 * @return Names of blueprints, separated by commas.
	 * @throws Exception
	 *             IO Exception
	 */
	@CrossOrigin
	@RequestMapping(value = "/blueprinttable", method = RequestMethod.GET)
	public String sendListOfBlueprint() throws Exception {
		// Check if the file exists and if not, create it
		File f = new File("Blueprints/BlueprintsTable.txt");

		if (!f.exists()) {
			System.out.println("BlueprintsTable hasn't been created, creating now...");
			f.createNewFile();
		}

		Scanner scan = new Scanner(f);
		String names = "";
		while (scan.hasNextLine()) {
			names += scan.nextLine() + ","; // Add names of blueprints to the
											// string variable
		}
		scan.close();

		if (names.length() > 2) {
			// Remove the last comma and space appended by the loop above.
			names = names.substring(0, names.length() - 1);
			return names;
		}
		return null;
	}

	/**
	 * Sends the contents of the blueprint image.
	 * 
	 * @param name
	 *            Name of the blueprint used for opening the file.
	 * @return A blueprint object.
	 */
	@CrossOrigin
	@RequestMapping(value = "/blueprint/{name}", method = RequestMethod.GET)
	public Blueprint sendBlueprint(@PathVariable String name) {
		File file = new File("Blueprints/" + name + ".txt");
		String fileContents = "";
		try {
			Scanner fileScan = new Scanner(file);
			fileContents = fileScan.nextLine();
			fileScan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] s = fileContents.split(",");
		String image = "";
		for (int i = 3; i < s.length; i++) {
			image += s[i];
			image += ",";
		}
		// This may cut out an "=" sign
		image = image.substring(0, image.length() - 1);

		return new Blueprint(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]), image);
	}

}