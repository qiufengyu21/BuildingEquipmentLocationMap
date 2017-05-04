package backendTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import backend.Blueprint;
import backend.BlueprintController;

public class BlueprintControllerTest {
	private BlueprintController bc;

	@Before
	public void setUp() throws Exception {
		bc = new BlueprintController();
	}

	@Test
	public void testGetBlueprint() {
		try {
			bc.getBlueprint("TestName,100,100,TheActualData:abcdefg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f = new File("Blueprints/TestName.txt");
		boolean flag = f.exists();
		assertTrue(flag);

		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = scan.nextLine();
		assertEquals("TestName,100,100,TheActualData:abcdefg", data);

		File table = new File("Blueprints/BlueprintsTable.txt");
		Scanner scan1 = null;
		try {
			scan1 = new Scanner(table);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = scan1.nextLine();
		assertEquals("TestName", data);
	}

	@Test
	public void testSendListOfBlueprint() {
		String name = "";
		try {
			bc.getBlueprint("TestName2,100,100,TheActualData:abfg");
			name = bc.sendListOfBlueprint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name);
		assertEquals("TestName,TestName2", name);

	}

	@Test
	public void testSendBlueprint() {
		Blueprint b;
		b = bc.sendBlueprint("TestName");
		assertEquals("TheActualData:abcdefg", b.getContent());
		assertEquals(100, b.getHeight());
		assertEquals(100, b.getWidth());
	}

}
