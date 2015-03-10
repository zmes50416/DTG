package tw.edu.ncu.im.Preprocess;

import java.nio.file.Path;

public interface IODelegater {
	
	public void store(Path filePath);
	public void load(Path filePath);

}
