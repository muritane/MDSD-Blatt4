package WebGUI;

import repository.HTTP;
import repository.MediaStore;
import repository.Helper;
        
public class WebGUI implements HTTP {
	
	MediaStore mediaStore;
	
	public void setMediaStore(MediaStore mediaStore){ 
		Helper.assertNull(this.mediaStore);
		this.mediaStore = mediaStore;
	}
	
	// Implementing HTTPDownload from interface HTTP
	@Override
	public void HTTPDownload() {
		Helper.assertNotNull(this.mediaStore);
		// TODO: Insert code here
	}
	
	// Implementing HTTPUpload from interface HTTP
	@Override
	public void HTTPUpload() {
		Helper.assertNotNull(this.mediaStore);
		// TODO: Insert code here
	}
	
}
