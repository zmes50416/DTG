package tw.edu.ncu.im.Preprocess;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import tw.edu.ncu.im.Preprocess.graph.*;

public abstract class IODelegater {
	Path filePath;
	
	public IODelegater(String _filePath) {
		this.filePath = Paths.get(_filePath);
	}
	public abstract void store(PreprocessComponent<Node<?>,Edge> component) throws IOException;
	public abstract void load(PreprocessComponent<Node<?>,Edge> component) throws IOException;

}
