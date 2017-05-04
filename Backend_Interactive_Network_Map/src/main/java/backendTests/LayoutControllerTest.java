package backendTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import backend.LayoutController;
import backend.Device;

public class LayoutControllerTest {

	private LayoutController lc;
	private Device device;

	@Before
	public void setUp() throws Exception {
		lc = new LayoutController();

	}

	@Test
	public void testGetSpecificLayout() {
		String fileName = "testLayout-Floor1";
		String badFile = "badFile";
		String nullFileName = "null";
		String nullFile = null;
		
		String contents = "[{\"lat\":-199,\"lng\":337.5,\"icon\":\"router\",\"alt\":\"10.52.30.16\"}]";
		PrintWriter writer;
		
		try {
			writer = new PrintWriter("Layouts/" + fileName + ".txt", "UTF-8");
			writer.println(contents);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// We create this file
		device = lc.getSpecificLayout(fileName);
		assertEquals(contents, device.getContent());

		// File does not exist
		device = lc.getSpecificLayout(badFile);
		assertEquals(null, device);		

		// File name is called "null"
		device = lc.getSpecificLayout(nullFileName);
		assertEquals(null, device);
		 
		// File name string is null
		device = lc.getSpecificLayout(nullFile);
		assertEquals(null, device);		
		
		// The file created above will be deleted below, to ensure that tests are repeatable.
	}

	@Test
	public void testGetDeviceList() {
		List<String[]> devices;
		
		String fileName = "testDeviceInfo.txt";
		String badFile = "badFile.txt";
		
		String contents = "Switch,10.52.30.39,255.255.255.0,SBx3112,v2,1,online,6.18.0.1-00,NMS_C4,Floor-1";
		String[] contentArray = contents.split(",|\\n");
		
		PrintWriter writer;
		
		try {
			writer = new PrintWriter("Devices/" + fileName, "UTF-8");
			writer.println(contents);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// We create this file
		devices= lc.getDeviceList(fileName);
		
		for(int i = 0; i < devices.size(); i++) {
			for(int j = 0; j < devices.get(i).length; j++) {
				assertEquals(contentArray[j], devices.get(i)[j]);
			}
		}
		
		// File does not exist
		devices = lc.getDeviceList(badFile);
		assertEquals(null, devices);
		
		// Delete file after testing
		File file = new File("Devices/" + fileName);
		file.delete();
	}

	@Test
	public void testGetLayoutName() {
		Scanner fileScan;
		String contents = "[{\"lat\":-50,\"lng\":80,\"icon\":\"switch\",\"alt\":\"10.52.30.39\"}]";
		
		String existingFileName = "testLayout-Floor1";
		String newFileName = "badLayout-Floor1";
		
		String goodData = "Name:" + existingFileName + " " + contents;
		String badData = "Name:" + newFileName + " " + contents;
		
		try {

			// Assert that file exists
			File existingFile = new File("Layouts/" + existingFileName + ".txt");
			assertTrue(existingFile.exists());
			
			lc.getLayoutName(goodData);
			
			// Assert that the contents of the file is the same as what we passed
			fileScan = new Scanner(existingFile);
			assertEquals(contents, fileScan.nextLine());
			fileScan.close();

			
			// Assert that file does not exist
			File newFile = new File("Layouts/" + newFileName + ".txt");
			// Delete the file so that test is repeatable
			if(newFile.exists()) {
				newFile.delete();
			}
			
			assertFalse(newFile.exists());
			
			lc.getLayoutName(badData);
			
			// Assert that the contents of the file is the same as what we passed
			fileScan = new Scanner(newFile);
			assertEquals(contents, fileScan.nextLine());
			fileScan.close();
			
			// To test an empty marker array (no contents)
			lc.getLayoutName("Name:" + existingFileName + " ");
			Scanner scan = new Scanner(existingFile);
			assertEquals("[]", scan.nextLine());
			scan.close();
			
			// Delete files after testing
			existingFile.delete();
			newFile.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
