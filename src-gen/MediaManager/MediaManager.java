package MediaManager;

import repository.MediaStore;
import repository.AudioDB;
import repository.Sound;
import repository.Helper;
        
public class MediaManager implements MediaStore {
	
	AudioDB audioDB;
	Sound sound;
	
	public void setAudioDB(AudioDB audioDB){ 
		Helper.assertNull(this.audioDB);
		this.audioDB = audioDB;
	}
	
	public void setSound(Sound sound){ 
		Helper.assertNull(this.sound);
		this.sound = sound;
	}
	
	// Implementing upload from interface MediaStore
	@Override
	public void upload() {
		Helper.assertNotNull(this.audioDB);
		Helper.assertNotNull(this.sound);
		// TODO: Insert code here
	}
	
	// Implementing download from interface MediaStore
	@Override
	public void download() {
		Helper.assertNotNull(this.audioDB);
		Helper.assertNotNull(this.sound);
		// TODO: Insert code here
	}
	
}
