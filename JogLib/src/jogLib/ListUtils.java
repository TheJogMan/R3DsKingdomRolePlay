package jogLib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ListUtils
{
	public static List<Object> getList(Object[] array)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<UUID> getList(UUID[] array)
	{
		List<UUID> list = new ArrayList<UUID>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<String> getList(String[] array)
	{
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Integer> getList(int[] array)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Long> getList(long[] array)
	{
		List<Long> list = new ArrayList<Long>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Short> getList(short[] array)
	{
		List<Short> list = new ArrayList<Short>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Boolean> getList(boolean[] array)
	{
		List<Boolean> list = new ArrayList<Boolean>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Float> getList(float[] array)
	{
		List<Float> list = new ArrayList<Float>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static List<Double> getList(double[] array)
	{
		List<Double> list = new ArrayList<Double>();
		for (int index = 0; index < array.length; index++)
		{
			list.add(array[index]);
		}
		return list;
	}
	
	public static Object[] getArray(List<Object> list)
	{
		Object[] array = new Object[list.size()];
		Iterator<Object> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static UUID[] getUUIDArray(List<UUID> list)
	{
		UUID[] array = new UUID[list.size()];
		Iterator<UUID> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static String[] getStringArray(List<String> list)
	{
		String[] array = new String[list.size()];
		Iterator<String> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static int[] getIntegerArray(List<Integer> list)
	{
		int[] array = new int[list.size()];
		Iterator<Integer> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static long[] getLongArray(List<Long> list)
	{
		long[] array = new long[list.size()];
		Iterator<Long> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static float[] getFloatArray(List<Float> list)
	{
		float[] array = new float[list.size()];
		Iterator<Float> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static double[] getDoubleArray(List<Double> list)
	{
		double[] array = new double[list.size()];
		Iterator<Double> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static boolean[] getBooleanArray(List<Boolean> list)
	{
		boolean[] array = new boolean[list.size()];
		Iterator<Boolean> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static short[] getShortArray(List<Short> list)
	{
		short[] array = new short[list.size()];
		Iterator<Short> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
	
	public static byte[] getByteArray(List<Byte> list)
	{
		byte[] array = new byte[list.size()];
		Iterator<Byte> iterator = list.iterator();
		for (int index = 0; index < array.length; index++)
		{
			array[index] = iterator.next();
		}
		return array;
	}
}