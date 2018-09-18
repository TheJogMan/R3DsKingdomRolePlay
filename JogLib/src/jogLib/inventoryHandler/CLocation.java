package jogLib.inventoryHandler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import jogLib.ByteConverter;

public class CLocation
{
	CVector direction;
	float pitch;
	float yaw;
	double x;
	double y;
	double z;
	boolean isNull;
	UUID worldID;
	
	public CLocation(Location location)
	{
		if (location != null)
		{
			direction = new CVector(location.getDirection());
			pitch = location.getPitch();
			yaw = location.getYaw();
			x = location.getX();
			y = location.getY();
			z = location.getZ();
			worldID = location.getWorld().getUID();
			isNull = false;
		}
		else
		{
			isNull = true;
			direction = new CVector(new Vector(0, 0, 0));
			pitch = 0;
			yaw = 0;
			x = 0;
			y = 0;
			z = 0;
			worldID = null;
		}
	}
	
	public CLocation(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		if (array[0].length == 1)
		{
			isNull = true;
			worldID = null;
		}
		else
		{
			worldID = ByteConverter.toUUID(array[0]);
		}
		direction = new CVector(array[1]);
		float[] floatArray = ByteConverter.toFloatArray(array[2]);
		pitch = floatArray[0];
		yaw = floatArray[1];
		double[] doubleArray = ByteConverter.toDoubleArray(array[3]);
		x = doubleArray[0];
		y = doubleArray[1];
		z = doubleArray[2];
	}
	
	public byte[] export()
	{
		byte[][] data = new byte[4][];
		if (worldID != null)
		{
			data[0] = ByteConverter.fromUUID(worldID);
		}
		else
		{
			data[0] = new byte[] {0};
		}
		data[1] = direction.export();
		data[2] = ByteConverter.fromFloatArray(new float[] {pitch, yaw});
		data[3] = ByteConverter.fromDoubleArray(new double[] {x, y, z});
		return ByteConverter.from2DByteArray(data);
	}
	
	public Location getLocation()
	{
		if (!isNull)
		{
			World world = Bukkit.getWorld(worldID);
			Location location = new Location(world, x, y, z);
			location.setDirection(direction.getVector());
			location.setPitch(pitch);
			location.setYaw(yaw);
			return location;
		}
		else
		{
			return null;
		}
	}
}