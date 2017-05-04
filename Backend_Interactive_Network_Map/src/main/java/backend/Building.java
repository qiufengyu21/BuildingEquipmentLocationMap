package backend;

import java.util.ArrayList;

/**
 * Class responsible for storing the building hierarchy. Has Floor and Devices
 * sub-classes to maintain the hierarchy. Each Building object has variables for
 * it's name, status and number of floors.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
public class Building {
	private String name;
	private String building_status;
	private int numFloors;
	private ArrayList<Floor> floors = new ArrayList<Floor>();

	/**
	 * Placeholder constructor.
	 */
	public Building() {

	}

	/**
	 * Constructor to initialize the variables for name, status and the number
	 * of floors.
	 * 
	 * @param name
	 *            Name of the building.
	 * @param building_status
	 *            Status of the building.
	 * @param numfloors
	 *            Number of floors in the building.
	 * 
	 */
	public Building(String name, String building_status, int numfloors) {
		super();
		this.name = name;
		this.setBuilding_status(building_status);
		this.numFloors = numfloors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}

	public ArrayList<Floor> getFloors() {
		return floors;
	}

	public void setFloors(ArrayList<Floor> floors) {
		this.floors = floors;
	}

	public String getBuilding_status() {
		return building_status;
	}

	public void setBuilding_status(String building_status) {
		this.building_status = building_status;
	}

	// Override the existing toString() function to display the appropriate
	// information.
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + getName() + "\n");
		sb.append("Status: " + getBuilding_status() + "\n");
		sb.append("NumFloors: " + getNumFloors() + "\n");
		ArrayList<Floor> floors = getFloors();
		for (int i = 0; i < floors.size(); i++) {
			sb.append(floors.get(i).toString());
		}

		return sb.toString();
	}

	/**
	 * Inner class to maintain the floor hierarchy. Has variables to store the
	 * floor's name, status, number of devices on it, blueprint and layout name,
	 * and a list of devices on the floor.
	 * 
	 * @author Cameron Lanier
	 * @author Qiufeng Yu
	 * @author Balaji Sundaram
	 * @author Harsh Singh
	 *
	 */
	public static class Floor {
		private String name;
		private String status;
		private int numDevices;
		private String blueprint;
		private String layoutName;
		private ArrayList<Device> devices = new ArrayList<Device>();

		/**
		 * Constructor to initialize the variables for the name, status and the
		 * number of devices on the floor.
		 * 
		 * @param name
		 *            Floor name.
		 * @param status
		 *            Floor status.
		 * @param numDevices
		 *            Number of devices on the floor.
		 * 
		 */
		public Floor(String name, String status, int numDevices) {
			super();
			this.name = name;
			this.status = status;
			this.numDevices = numDevices;
		}

		/**
		 * Placeholder constructor.
		 */
		public Floor() {
			// Default Constructor
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getNumDevices() {
			return numDevices;
		}

		public void setNumDevices(int numDevices) {
			this.numDevices = numDevices;
		}

		public String getBlueprint() {
			return blueprint;
		}

		public void setBlueprint(String blueprint) {
			this.blueprint = blueprint;
		}

		public String getLayoutName() {
			return layoutName;
		}

		public void setLayoutName(String layoutName) {
			this.layoutName = layoutName;
		}

		public ArrayList<Device> getDevices() {
			return devices;
		}

		public void setDevices(ArrayList<Device> devices) {
			this.devices = devices;
		}

		// Override the existing toString() function to display the appropriate
		// information.
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Name: " + getName() + "\n");
			sb.append("Name: " + getBlueprint() + "\n");
			return sb.toString();

		}

		/**
		 * Inner class used to keep track of each device on the floor.
		 * 
		 * @author Cameron Lanier
		 * @author Qiufeng Yu
		 * @author Balaji Sundaram
		 * @author Harsh Singh
		 * 
		 */
		public static class Device {
			private String name;
			private String status;
			private String nickname;

			/**
			 * Constructor to set the name and status of a device.
			 * 
			 * @param name
			 * 			Name of the device.
			 * @param status
			 * 			Status of the device.
			 */
			public Device(String name, String status) {
				super();
				this.name = name;
				this.status = status;

			}

			/**
			 * Placeholder constructor.
			 */
			public Device() {

			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}
		}
	}
}
