package ICGFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;

public class FileUtils {
	private static File dataDirectory = null;
	/**
	 * Returns File pointing to Data directory of current project. If Data directory is not found, returns project directory.
	 * @return File object. 
	 */
	public static File getDataDirectory()
	{
		if(dataDirectory == null)
		{
			try
			{
				String path = URLDecoder.decode(MainFrame.class.getProtectionDomain().getCodeSource().getLocation().getFile(), Charset.defaultCharset().toString());
				dataDirectory = new File(path).getParentFile();
			}
			catch (UnsupportedEncodingException e)
			{
				dataDirectory = new File(".");
			}
			if(dataDirectory == null || !dataDirectory.exists()) dataDirectory = new File(".");
			for(File f: Objects.requireNonNull(dataDirectory.listFiles()))
			{
				if(f.isDirectory() && f.getName().endsWith("_Data"))
				{
					dataDirectory = f;
					break;
				}
			}
		}
		return dataDirectory;
	}

	/**
	 * Prompts user for file name to save and returns it
	 * @param parent - parent frame for file selection dialog
	 * @param extension - preferred file extension (example: "txt") 
	 * @param description - description of specified file type (example: "Text files")
	 * @return File specified by user or null if user canceled operation
	 * @see MainFrame.getOpenFileName
	 */
	public static File getSaveFileName(JFrame parent, String extension, String description)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new ExtensionFileFilter(extension, description);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if(fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			if(!f.getName().contains("."))
				f = new File(f.getParent(), f.getName()+"."+extension);
			return f;
		}
		return null;
	}
	
	/**
	 * Prompts user for file name to open and returns it
	 * @param parent - parent frame for file selection dialog
	 * @param extension - preferred file extension (example: "txt") 
	 * @param description - description of specified file type (example: "Text files")
	 * @return File specified by user or null if user canceled operation
	 * @see MainFrame.getSaveFileName
	 */
	public static File getOpenFileName(JFrame parent, String extension, String description)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new ExtensionFileFilter(extension, description);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if(fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			if(!f.getName().contains("."))
				f = new File(f.getParent(), f.getName()+"."+extension);
			return f;
		}
		return null;
	}

	public static File getOpenImageFileName(JFrame parent)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("PNG, JPEG, BMP, GIF", "png", "jpeg", "bmp", "gif");/*new ExtensionFileFilter(extension, description);*/
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if(fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			if(!f.getName().contains("."))
				f = new File(f.getParent(), f.getName() + ".png");
			return f;
		}
		return null;
	}

	public static File getSaveImageFileName(JFrame parent)
	{
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("png", "png");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if(fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			if(!f.getName().contains("."))
				f = new File(f.getParent(), f.getName()+".png");
			return f;
		}
		return null;
	}
}
