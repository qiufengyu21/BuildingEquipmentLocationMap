package backendTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import backend.Building;
import backend.BuildingController;

public class BuildingControllerTest {

	private BuildingController bc;
	private Building building;

	@Before
	public void setUp() throws Exception {
		bc = new BuildingController();
	}

	@Test
	public void testGetBuildingList() {

		String buildingName = "BuildingOneTest";
		String buildingString = "{\"name\":\"BuildingOneTest\",\"building_status\":\"\",\"numFloors\":2,\"floors\":[{\"name\":\"Floor 2\",\"status\":null,\"numDevices\":0,\"blueprint\":\"webappdesign\",\"layoutName\":\"BuildingOne-Floor2\",\"devices\":[]},{\"name\":\"Floor 1\",\"status\":null,\"numDevices\":0,\"blueprint\":\"project2\",\"layoutName\":\"BuildingOne-Floor1\",\"devices\":[]}]}";

		String floor1 = "[{\"lat\":-50,\"lng\":120,\"icon\":\"router\",\"alt\":\"G1AS8600Y\"},{\"lat\":-50,\"lng\":80,\"icon\":\"switch\",\"alt\":\"3\"},{\"lat\":-50,\"lng\":120,\"icon\":\"router\",\"alt\":\"4\"}]";
		String floor2 = "[{\"lat\":-50,\"lng\":80,\"icon\":\"switch\",\"alt\":\"1\"},{\"lat\":-50,\"lng\":120,\"icon\":\"router\",\"alt\":\"2\"},{\"lat\":-50,\"lng\":150,\"icon\":\"computer\",\"alt\":\"58793416\"}]";
		PrintWriter writer;

		try {
			writer = new PrintWriter("Layouts/BuildingOne-Floor1.txt", "UTF-8");
			writer.println(floor1);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			writer = new PrintWriter("Layouts/BuildingOne-Floor2.txt", "UTF-8");
			writer.println(floor2);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			bc.createBuilding(buildingString);
			assertTrue(bc.getBuildingList().size() > 0);

			System.out.println("Building List to String");

			// System.out.println(bc.getBuildingList()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// fail("Not yet implemented");
	}

	@Test
	public void testGetBuilding() {
		String buildingName = "BuildingOneTest";
		String buildingString = "{\"name\":\"BuildingOne\",\"building_status\":\"\",\"numFloors\":2,\"floors\":[{\"name\":\"Floor 2\",\"status\":null,\"numDevices\":0,\"blueprint\":\"webappdesign\",\"layoutName\":\"BuildingOne-Floor2\",\"devices\":[]},{\"name\":\"Floor 1\",\"status\":null,\"numDevices\":0,\"blueprint\":\"project2\",\"layoutName\":\"BuildingOne-Floor1\",\"devices\":[]}]}";

		String badBuildingString = "{\"namer\":\"BuildingOne\",\"building_status\":\"\",\"numFloors\":2,\"floors\":[{\"name\":\"Floor 2\",\"status\":null,\"numDevices\":0,\"blueprint\":\"webappdesign\",\"layoutName\":\"BuildingOne-Floor2\",\"devices\":[]},{\"name\":\"Floor 1\",\"status\":null,\"numDevices\":0,\"blueprint\":\"project2\",\"layoutName\":\"BuildingOne-Floor1\",\"devices\":[]}]}";

		// create test building
		try (PrintWriter out = new PrintWriter("Buildings/" + buildingName + ".txt")) {
			out.println(buildingString);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Pass parsing building
		building = bc.getBuilding(buildingName);
		assertEquals(building.getName(), "BuildingOne");

		// create test building
		try (PrintWriter out = new PrintWriter("Buildings/" + buildingName + ".txt")) {
			out.println(badBuildingString);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		building = bc.getBuilding(buildingName);
		assertEquals(building, null);
		// assertFail(building.getName(),null);

		// delete after testing
		File file = new File("Buildings/" + buildingName + ".txt");
		file.delete();
		// fail("Not yet implemented");
	}

	@Test
	public void testGetBuildingNames() {
		String buildingNameList = "";

		// fail("Not yet implemented");
	}

	@Test
	public void testCreateBuilding() {
		// TODO Need to remove building name from list
		String buildingName = "BuildingOneTest";
		String buildingString = "{\"name\":\"BuildingOneTest\",\"building_status\":\"\",\"numFloors\":2,\"floors\":[{\"name\":\"Floor 2\",\"status\":null,\"numDevices\":0,\"blueprint\":\"webappdesign\",\"layoutName\":\"BuildingOne-Floor2\",\"devices\":[]},{\"name\":\"Floor 1\",\"status\":null,\"numDevices\":0,\"blueprint\":\"project2\",\"layoutName\":\"BuildingOne-Floor1\",\"devices\":[]}]}";

		String buildingNameTwo = "BuildingTwoTest";
		String buildingStringTwo = "{\"namer\":\"BuildingTwoTest\",\"building_status\":\"\",\"numFloors\":2,\"floors\":[{\"name\":\"Floor 2\",\"status\":null,\"numDevices\":0,\"blueprint\":\"webappdesign\",\"layoutName\":\"BuildingOne-Floor2\",\"devices\":[]},{\"name\":\"Floor 1\",\"status\":null,\"numDevices\":0,\"blueprint\":\"project2\",\"layoutName\":\"BuildingOne-Floor1\",\"devices\":[]}]}";

		try {
			bc.createBuilding(buildingString);
			bc.createBuilding(buildingStringTwo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertTrue((bc.getBuildingNames()).contains(buildingName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// fail("Not yet implemented");
	}

}
