package DBCache;

import repository.AudioDB;
import repository.AudioDB;
import repository.Helper;
        
public class DBCache implements AudioDB {
	
	AudioDB audioDB;
	
	public void setAudioDB(AudioDB audioDB){ 
		Helper.assertNull(this.audioDB);
		this.audioDB = audioDB;
	}
	
	// Implementing addFile from interface AudioDB
	@Override
	public void addFile() {
		Helper.assertNotNull(this.audioDB);
		// TODO: Insert code here
	}
	
	// Implementing queryDB from interface AudioDB
	@Override
	public void queryDB() {
		Helper.assertNotNull(this.audioDB);
		// TODO: Insert code here
	}
	
}
