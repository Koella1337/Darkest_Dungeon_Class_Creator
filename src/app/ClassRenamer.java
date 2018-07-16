package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Used to rename all files of an existing Darkest Dungeon class.<br>
 * Also changes the names contained in the first line of all ".atlas" files accordingly.
 * @author Koella
 */
public class ClassRenamer {
	
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	
	public void shutdownThreadPool() {
		threadPool.shutdown();
	}
	
	public boolean isValid(String name) {
		//exclude special cases because of Darkest Dungeons given folders & files
		if (name.length() <= 4 && (name.startsWith("a") || name.startsWith("e") || name.startsWith("f"))) {
			switch (name) {
				case "a":		case "e":		case "f":
				case "an":		case "eq":		case "fx":
				case "ani":		case "eqp":
				case "anim":	
					return false;
			}
		}
		if (name.startsWith("eqp_")) return false;
		if (name.equals("icons_equip")) return false;
		
		//only allow losercase letters with '_' in-between
		return name.matches("([a-z]+(_[a-z]+)?)+");
	}
	
	/** 
	 * Start the renaming process.
	 * @throws IllegalArgumentException if either String is null or invalid or if dir is not a directory.
	 */
	public void start(File dir, String oldName, String newName) throws IllegalArgumentException {
		if (dir == null || oldName == null || newName == null || !dir.isDirectory() || !isValid(oldName) || !isValid(newName))
			throw new IllegalArgumentException();
		
		threadPool.submit(() -> {
			this.renameFilesAndDirectory(dir, oldName, newName);
		});
	}
	
	/**
	 * Renames the given directory and all of it's files.<br>
	 * Submits tasks for renaming sub-directories to the threadPool.<br>
	 * Expects all parameters to be valid.
	 */
	private void renameFilesAndDirectory(File dir, String oldName, String newName) {
		dir = dir.getAbsoluteFile();
		
		if (dir.getName().startsWith(oldName)) {
			try {
				//rename directory
				Path newPath = Paths.get(dir.getParent(), dir.getName().replaceFirst(oldName, newName));
				Files.move(Paths.get(dir.getPath()), newPath);
				dir = newPath.toFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File[] files = dir.listFiles();
		if (files == null || files.length <= 0) return;
		
		for (File f : files) {
			if (f == null) continue;
			
			if (f.isDirectory()) {
				threadPool.submit(() -> {
					this.renameFilesAndDirectory(f, oldName, newName);
				});
			}
			else if (f.getName().startsWith(oldName)) {
				try {
					//rename file
					Path oldPath = Paths.get(f.getPath());
					Path newPath = Paths.get(f.getParent(), f.getName().replaceFirst(oldName, newName));
					Files.move(oldPath, newPath);
					
					if (newPath.toString().endsWith(".atlas")) {
						threadPool.submit(() -> {
							this.processAtlasFile(newPath, oldName, newName);
						});
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Handles a ".atlas"-file:<br>
	 * Finds the first line that starts with oldName and replaces that string with newName.
	 */
	private void processAtlasFile(Path atlasFile, String oldName, String newName) {
		try {
			List<String> lines = Files.readAllLines(atlasFile);
			boolean foundName = false;		//only replace first occurence of oldName
			
			for (int i = 0; i < lines.size(); i++) {
				if (!foundName && lines.get(i).startsWith(oldName)) {
					lines.add(i, lines.remove(i).replaceFirst(oldName, newName));
					foundName = true;
				}
			}
			Files.write(atlasFile, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
