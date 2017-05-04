package backendTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import backend.Building;

public class BuildingTest {
	
	private String name;
	private String building_status;
	private int numFloors;
	private ArrayList <Building.Floor> floors = new ArrayList<Building.Floor>();
	private Building building;

	@Before
	public void setUp() throws Exception {
		building = new Building();
		
		name = "Building1";
		building_status = "offline";
		numFloors = 3;
		
		building = new Building (name, building_status, numFloors);
		
		Building.Floor floorOne = new Building.Floor();
		Building.Floor floorTwo = new Building.Floor("Floor Two","offline",1);
		
		Building.Floor.Device deviceOne = new Building.Floor.Device();
		Building.Floor.Device deviceTwo = new Building.Floor.Device("deviceTwo","online");
		Building.Floor.Device deviceThree = new Building.Floor.Device("deviceThree", "offline");
		
		deviceOne.setName("deviceOne");
		deviceOne.setStatus("online");
		
		ArrayList<Building.Floor.Device> floorOneList = new ArrayList<Building.Floor.Device>();
		ArrayList<Building.Floor.Device> floorTwoList = new ArrayList<Building.Floor.Device>();
		
		floorOneList.add(deviceOne);
		floorOneList.add(deviceTwo);
		
		floorTwoList.add(deviceThree);
		
		floorOne.setBlueprint("Blueprint One");
		floorOne.setName("Floor One");
		floorOne.setLayoutName("Floor One Layout One");
		floorOne.setNumDevices(2);
		floorOne.setStatus("online");
		floorOne.setDevices(floorOneList);
		
		floorTwo.setBlueprint("Blueprint Two");
		floorTwo.setLayoutName("Floor Two Layout One");
		floorTwo.setDevices(floorTwoList);
		
		floors.add(floorOne);
		floors.add(floorTwo);
		
		building.setFloors(floors);
		
	}

	@Test
	public void testGetName() {
		
		assertEquals(building.getName(), name);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		String testName = "Building Two";
		assertEquals(building.getName(), name);
		building.setName(testName);
		assertEquals(building.getName(), testName);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetNumFloors() {
		assertEquals(building.getNumFloors(), 3);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetNumFloors() {
		int testFloorNum = 5;
		assertEquals(building.getNumFloors(), 3);
		building.setNumFloors(testFloorNum);
		assertEquals(building.getNumFloors(), testFloorNum);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFloors() {
	
		assertEquals(floors,building.getFloors());
		
		//fail("Not yet implemented");
	}

	@Test
	public void testSetFloors() {
		Building buildingTwo = new Building();
		assertFalse((buildingTwo.getFloors()).equals(floors));
		buildingTwo.setFloors(floors);
		assertTrue((buildingTwo.getFloors()).equals(floors));
		
		
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuilding_status() {
		String testStatus = "offline";
		assertEquals(building.getBuilding_status(),testStatus);
		//fail("Not yet implemented");
	}

	@Test
	public void testSetBuilding_status() {
		String testStatus = "offline";
		assertEquals(building.getBuilding_status(),testStatus);
		building.setBuilding_status("online");
		assertEquals(building.getBuilding_status(),"online");
		//fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		//System.out.println(building.toString());
		assertEquals(building.toString(),building.toString());
		//fail("Not yet implemented");
	}
		
	@Test
	public void testFloorGetStatus() {		
		assertEquals(building.getFloors().get(0).getStatus(),floors.get(0).getStatus());	
	}
	
	@Test
	public void testFloorGetLayoutName() {		
		assertEquals(building.getFloors().get(0).getLayoutName(),floors.get(0).getLayoutName());	
	}
	
	@Test
	public void testFloorGetNumDevices() {		
		assertEquals(building.getFloors().get(0).getNumDevices(),floors.get(0).getNumDevices());	
	}
	
	@Test
	public void testFloorGetDevices() {		
		assertEquals(building.getFloors().get(0).getDevices(),floors.get(0).getDevices());	
	}
	
	@Test
	public void testDevicesGetName(){
		assertEquals(building.getFloors().get(0).getDevices().get(0).getName(),floors.get(0).getDevices().get(0).getName());
	}

	@Test
	public void testDevicesGetStatus(){
		assertEquals(building.getFloors().get(0).getDevices().get(0).getStatus(),floors.get(0).getDevices().get(0).getStatus());
	}


}
