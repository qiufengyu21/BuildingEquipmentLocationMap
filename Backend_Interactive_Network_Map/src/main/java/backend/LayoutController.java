package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for the Layout class. It handles the data exchange between the
 * web application and the server.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
@RestController
public class LayoutController {

	/**
	 * Function that returns the marker details as a device object to the
	 * front-end.
	 * 
	 * @param name
	 *            the name of the layout as a string
	 * @return device object
	 */
	@CrossOrigin
	@RequestMapping(value = "/layout/{name}", method = RequestMethod.GET)
	public Device getSpecificLayout(@PathVariable String name) {

		if (name != null && !name.equalsIgnoreCase("null")) {

			File file = new File("Layouts/" + name + ".txt");
			String fileContents = "";
			try {
				Scanner fileScan = new Scanner(file);
				fileContents = fileScan.nextLine();
				fileScan.close();
			} catch (FileNotFoundException e) {
				System.out.println(name + ".txt does not exist in Layouts/");
				return null;
			}

			return new Device(String.format(fileContents));
		}
		return null;
	}

	/**
	 * Used to read the deviceList file and gets its contents. 
	 * This file has all the device information including IP Address
	 * and device status.
	 * 
	 * @param fileName
	 * 				Name of the file containing device information.
	 * @return an ArrayList containing each device in the file.
	 */
	@CrossOrigin
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public List<String[]> getDeviceList(
			@RequestParam(value = "data", defaultValue = "deviceInfo.txt") String fileName) {
		File file = new File("Devices/" + fileName);
		String fileContents = "";
		List<String[]> retList = new ArrayList<String[]>();

		try {
			Scanner fileScan = new Scanner(file);

			while (fileScan.hasNextLine()) {
				fileContents = fileScan.nextLine();
				String[] contentArray = fileContents.split(",|\\n");

				retList.add(contentArray);
			}

			fileScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("The device list file does not exist");
			return null;
		}

		return retList;
	}

	/**
	 * This function gets the name of the layout and the array of markers that
	 * is being sent from the front-end and appends it to the LayoutsTable file.
	 * 
	 * @param data
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(value = "/layout", method = RequestMethod.POST)
	public void getLayoutName(@RequestParam(value = "data", defaultValue = "") String data) throws Exception {

		// Get the name of the layout from the data.
		int firstSpace = data.indexOf(" ", 1) + 1;
		String name = data.substring(0, firstSpace);
		name = name.substring(5, name.length() - 1);

		// Get the array of markers from the data.
		String markerArray = data.substring(firstSpace, data.length());

		// Need this to avoid Jackson JSON object mapping errors
		if (markerArray.equals("")) {
			markerArray = "[]";
		}

		// Write the array of markers to name.txt file, with the name just parsed.
		try (PrintWriter out = new PrintWriter("Layouts/" + name + ".txt")) {
			out.println(markerArray);
		} catch (FileNotFoundException e) {
			System.out.println(name + ".txt does not exist in Layouts/");
			e.printStackTrace();
		}
	}
}
