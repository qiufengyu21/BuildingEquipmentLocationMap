package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to receive the note from the web application, store the
 * note on local file, as well as send the note back to the front-end
 * application.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
@RestController
public class NotesController {

	/**
	 * This method is used to retrieve the contents of a specific note from the
	 * back-end server. The device ID is passed from the front-end to the
	 * back-end using a GET request. The server receives the name, opens up
	 * the file with the same name under the Notes directory, and sends the
	 * content in that file back to the web application.
	 * 
	 * @param name
	 * @return the content in the corresponding note file
	 */
	@CrossOrigin
	@RequestMapping(value = "/getNotes/{name}", method = RequestMethod.GET)
	public String getNotes(@PathVariable String name) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("Notes/" + name + ".txt");

			Notes notes = objectMapper.readValue(file, Notes.class);

			return notes.getContents();
			
		} catch (FileNotFoundException e) {
			System.out.println("The notes file " + name + ".txt does not exist.");
			return "";
		} catch (IOException e) {
			System.out.println("Problem with the object mapper: ");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * This method is used to write notes of a device to a file 
	 * on the server.
	 * 
	 * @param notesContents
	 * 				Contents of the notes file
	 */
	@CrossOrigin
	@RequestMapping(value = "/setNotes", method = RequestMethod.POST)
	public void setNotes(@RequestParam(value = "notes", defaultValue = "") String notesContents) {

		String fileName = "";
		String notesString = "";

		try {
			// Create an Object Mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Convert JSON string to Notes object
			Notes notes = objectMapper.readValue(notesContents, Notes.class);
			fileName = notes.getFileName();

			// Create a file inside Notes folder. Serial number is the name of
			// the file.
			// Overwrites if the file already exists.
			PrintWriter writer = new PrintWriter("Notes/" + fileName + ".txt", "utf-8");
			notesString = objectMapper.writeValueAsString(notes);
			writer.print(notesString);
			writer.close();

		} catch (Exception e) {
			System.out.println("Problem with object Mapper: ");
			e.printStackTrace();
		}

	}

}
