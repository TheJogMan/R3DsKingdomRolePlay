package jogLib.inventoryHandler;

import org.bukkit.util.Vector;

import jogLib.ByteConverter;

public class CVector
{
	double x;
	double y;
	double z;
	
	public CVector(Vector vector)
	{
		x = vector.getX();
		y = vector.getY();
		z = vector.getZ();
	}
	
	public CVector(byte[] data)
	{
		double[] array = ByteConverter.toDoubleArray(data);
		x = array[0];
		y = array[1];
		z = array[2];
	}
	
	public byte[] export()
	{
		return ByteConverter.fromDoubleArray(new double[] {x, y, z});
	}
	
	public Vector getVector()
	{
		Vector vector = new Vector();
		vector.setX(x);
		vector.setY(y);
		vector.setZ(z);
		return vector;
	}
}