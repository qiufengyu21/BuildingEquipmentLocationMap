package backend;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import backend.Building.Floor;
import backend.Building.Floor.Device;

/**
 * This class is the controller for the Building Class. It is responsible for
 * gathering all information about a building object and return the object back
 * to the frontend. This class also uses a JSON parser library called Jackson to 
 * map the JSON string to building object.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
@RestController
public class BuildingController {

	/**
	 * Used to gather current building information for the sidebar to display
	 * the current building-floor-device hierarchy on the web application.
	 * 
	 * @return a list of building objects.
	 */
	@CrossOrigin
	@RequestMapping(value = "/buildingList", method = RequestMethod.GET)
	public List<Building> getBuildingList() {

		// Build device list
		LayoutController lc = new LayoutController();
		String deviceFileName = "deviceInfo.txt";
		List<String[]> deviceList = lc.getDeviceList(deviceFileName);

		// Read through the entire building file
		File buildingsTable = new File("Buildings/" + "BuildingsTable.txt");

		List<String> buildingNames = new ArrayList<String>();
		List<Building> buildingObjects = new ArrayList<Building>();
		int fileLength = 0;

		try {
			Scanner fileScan = new Scanner(buildingsTable);
			while (fileScan.hasNextLine()) {
				fileLength++;
				buildingNames.add(fileScan.nextLine());
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("BuildingsTable file not found.");
			e.printStackTrace();
		}

		// Outer loop to iterate all entries until newLine/EOF
		for (int i = 0; i < fileLength; i++) {
			boolean buildingStatus = true;

			// Find the corresponding building file and open the layouts 
			try {
				File buildingFile = new File("Buildings/" + buildingNames.get(i) + ".txt");

				ObjectMapper objectMapper = new ObjectMapper();

				Building building = objectMapper.readValue(buildingFile, Building.class);

				// On the layout, loop through the devices and set the building status
				for (int j = 0; j < building.getNumFloors(); j++) {
					boolean floorStatus = true;

					List<Floor> floorObjects = building.getFloors();
					Floor floor = floorObjects.get(j);
					String layoutName = floor.getLayoutName();
					File layoutFile = new File("Layouts/" + layoutName + ".txt");

					Layout[] layouts = objectMapper.readValue(layoutFile, Layout[].class);
					int numDevices = layouts.length;
					floor.setNumDevices(numDevices);
					building.getFloors().get(j).setNumDevices(numDevices);

					for (int k = 0; k < layouts.length; k++) {
						String alt = layouts[k].getAlt();
						building.getFloors().get(j).getDevices().add(new Device());

						for (int l = 0; l < deviceList.size(); l++) {
							if (alt.equals(deviceList.get(l)[5])) {
								String status = deviceList.get(l)[6];
								String nickname = deviceList.get(l)[9];
								if (status.equalsIgnoreCase("offline")) {
									floorStatus = false;
								}

								building.getFloors().get(j).getDevices().get(k).setStatus(status);
								building.getFloors().get(j).getDevices().get(k).setName(alt);
								building.getFloors().get(j).getDevices().get(k).setNickname(nickname);
								break;
							}
						}
					}
					if (floorStatus == false) {
						buildingStatus = false;
						building.getFloors().get(j).setStatus("offline");
					} else {
						building.getFloors().get(j).setStatus("online");
					}
				}

				if (buildingStatus == false) {
					building.setBuilding_status("offline");
				} else {
					building.setBuilding_status("online");
				}

				buildingObjects.add(building);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return buildingObjects;
	}

	/**
	 * Used to retrieve information for a specific building.
	 * 
	 * @param name
	 *            the name of the building
	 * @return an building object
	 */
	@CrossOrigin
	@RequestMapping(value = "/building/{name}", method = RequestMethod.GET)
	public Building getBuilding(@PathVariable String name) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("Buildings/" + name + ".txt");

			Building building = objectMapper.readValue(file, Building.class);

			return building;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

	}

	/**
	 * Used to return a list of building names from the local
	 * Buildings/BuildingsTable.txt file. Program will automatically create a
	 * blank BuildingsTable.txt file if it's not in the directory. Otherwise, it
	 * opens up the file, scans each line and returns a list of building names
	 * back to the web application.
	 * 
	 * @return a list of building names as a string
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(value = "/buildingListName", method = RequestMethod.GET)
	public String getBuildingNames() throws Exception {
		// The directory is created if it doesn't exist, on startup.
		// This file would exist when a layout is saved through the front-end.
		File f = new File("Buildings/BuildingsTable.txt");
		if (!f.exists()) {
			System.out.println("BuildingsTable hasn't been created, creating now...");
			f.createNewFile();
			return null;
		}

		Scanner scan = new Scanner(f);
		String names = "";
		while (scan.hasNextLine()) {
			names += scan.nextLine() + ",";
		}
		scan.close();

		if (names.length() > 2) {
			// Code to remove the last comma and the space that is appended by
			// the code above.
			names = names.substring(0, names.length() - 1);
			return names;
		}
		return null;

	}

	/**
	 * Used to create a Building object. It receives the data as
	 * a JSON string from the frontend, maps the JSON string to a
	 * Building object, and creates a local building file to store all the
	 * information about that building. It also updates the BuildingsTable.txt
	 * file.
	 * 
	 * @param data
	 *            a JSON string containing information about a building created
	 *            by the user.
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(value = "/building", method = RequestMethod.POST)
	public void createBuilding(@RequestParam(value = "data", defaultValue = "") String data) throws Exception {
		String name = "";
		String buildingString = "";
		boolean fileExists = false;

		try {
			// Create an Object Mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Convert json string to Building object
			Building building = objectMapper.readValue(data, Building.class);
			name = building.getName();

			// Check if the building file already exists. 
			File buildingFile = new File("Buildings/" + name + ".txt");
			if (buildingFile.exists()) {
				fileExists = true;

			}

			// Write building information to the file. 
			try (PrintWriter out = new PrintWriter("Buildings/" + name + ".txt")) {
				buildingString = objectMapper.writeValueAsString(building);
				out.println(buildingString);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (! fileExists) {
				File f = new File("Buildings/BuildingsTable.txt");
				if (f.exists()) {
					PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(f, true)));
					output.println(name);
					output.close();
				} else {
					System.out.println("BuildingsTable hasn't been created, creating now...");
					f.createNewFile();
					PrintStream output = new PrintStream(f);
					output.println(name);
					output.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
