package backend;

/**
 * A Notes object class that stores the name of the note and the contents of the note
 * as strings.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
public class Notes {
	private String fileName;
	private String contents;

	public Notes(String fileName, String contents) {
		this.fileName = fileName;
		this.contents = contents;
	}
	
	/**
	 * Placeholder constructor.
	 */
	public Notes() {

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
