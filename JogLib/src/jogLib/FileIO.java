package jogLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO
{
	public static boolean canReadBytes(File file)
	{
		if (file.exists() && file.isFile() && file.canRead())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean canWriteBytes(File file)
	{
		if (file.canWrite())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static byte[] readBytes(File file)
	{
		if (canReadBytes(file))
		{
			try
			{
				FileInputStream reader = new FileInputStream(file);
				byte[] data = new byte[reader.available()];
				reader.read(data);
				reader.close();
				return data;
			}
			catch (IOException e)
			{
				System.out.println("ERROR: Could not read bytes from file! " + file.getAbsolutePath());
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("ERROR: Could not read bytes from file! " + file.getAbsolutePath());
			System.out.println("Path does not exist as a readable file!");
		}
		return new byte[0];
	}
	
	public static boolean writeBytes(File file, byte[] data)
	{
		try
		{
			boolean exists = false;
			if (file.exists() && file.isFile())
			{
				exists = true;
			}
			else
			{
				exists = file.createNewFile();
			}
			
			if (exists && file.canWrite())
			{
				FileOutputStream writer = new FileOutputStream(file);
				writer.write(data);
				writer.close();
				return true;
			}
			else
			{
				System.out.println("ERROR: Could not read bytes from file! " + file.getAbsolutePath());
				System.out.println("Path does not exist as a writable file!");
			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR: Could not write bytes to file " + file.getAbsolutePath());
			e.printStackTrace();
		}
		return false;
	}
}