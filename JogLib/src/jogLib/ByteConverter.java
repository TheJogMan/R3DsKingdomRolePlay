package jogLib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ByteConverter
{	
	public static UUID[] toUUIDArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		UUID[] newArray = new UUID[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = toUUID(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromUUIDArray(UUID[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = fromUUID(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static boolean[] toBooleanArray(byte[] data)
	{
		boolean[] array = new boolean[data.length * 8];
		int arrayIndex = 0;
		for (int index = 0; index < data.length; index++)
		{
			array[arrayIndex] = getBit(data[index], 0);
			array[arrayIndex + 1] = getBit(data[index], 1);
			array[arrayIndex + 2] = getBit(data[index], 2);
			array[arrayIndex + 3] = getBit(data[index], 3);
			array[arrayIndex + 4] = getBit(data[index], 4);
			array[arrayIndex + 5] = getBit(data[index], 5);
			array[arrayIndex + 6] = getBit(data[index], 6);
			array[arrayIndex + 7] = getBit(data[index], 7);
			arrayIndex += 8;
		}
		return array;
	}
	
	public static byte[] fromBooleanArray(boolean[] array)
	{
		if (array.length % 8 == 0)
		{
			byte[] data = new byte[array.length / 8];
			int arrayIndex = 0;
			for (int index = 0; index < data.length; index++)
			{
				byte value = 0;
				value = setBit(value, 0, array[arrayIndex]);
				value = setBit(value, 1, array[arrayIndex + 1]);
				value = setBit(value, 2, array[arrayIndex + 2]);
				value = setBit(value, 3, array[arrayIndex + 3]);
				value = setBit(value, 4, array[arrayIndex + 4]);
				value = setBit(value, 5, array[arrayIndex + 5]);
				value = setBit(value, 6, array[arrayIndex + 6]);
				value = setBit(value, 7, array[arrayIndex + 7]);
				data[index] = value;
				arrayIndex += 8;
			}
			return data;
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - fromBooleanArray - Array length must be multiple of 8, array length is " + array.length);
		}
	}
	
	public static double[] toDoubleArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		double[] newArray = new double[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.toDoubleC(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromDoubleArray(double[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.fromDoubleC(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static float[] toFloatArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		float[] newArray = new float[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.toFloatC(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromFloatArray(float[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.fromFloatC(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static long[] toLongArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		long[] newArray = new long[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.toLong(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromLongArray(long[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.fromLong(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static int[] toIntegerArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		int[] newArray = new int[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.toInteger(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromIntegerArray(int[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.fromInteger(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static String[] toStringArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		String[] newArray = new String[array.length];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.toString(array[index]);
		}
		return newArray;
	}
	
	public static byte[] fromStringArray(String[] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = ByteConverter.fromString(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static byte[][][] to3DByteArray(byte[] data)
	{
		byte[][] array = to2DByteArray(data);
		byte[][][] newArray = new byte[array.length][][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = to2DByteArray(array[index]);
		}
		return newArray;
	}
	
	public static byte[] from3DByteArray(byte[][][] array)
	{
		byte[][] newArray = new byte[array.length][];
		for (int index = 0; index < array.length; index++)
		{
			newArray[index] = from2DByteArray(array[index]);
		}
		return from2DByteArray(newArray);
	}
	
	public static byte[][] to2DByteArray(byte[] data)
	{
		if (data.length > 0)
		{
			if (data[0] == 0)
			{
				byte[][] array = new byte[1][data.length - 1];
				for (int index = 0; index < data.length - 1; index++)
				{
					array[0][index] = data[index + 1];
				}
				return array;
			}
			else
			{
				byte[][] array;
				boolean uniformLength = false;;
				int amount = 0;
				int dataIndex = 0;
				if (data[0] == 1)
				{
					uniformLength = true;
				}
				else if (data[0] == 2)
				{
					uniformLength = false;
				}
				if (data[1] == Byte.MAX_VALUE)
				{
					amount = convertUp(data[2]);
					dataIndex = 3;
				}
				else if (data[1] == Byte.MIN_VALUE)
				{
					amount = toInteger(new byte[] {data[2], data[3], data[4], data[5]});
					dataIndex = 6;
				}
				
				if (uniformLength)
				{
					int length = 0;
					if (data[dataIndex] == Byte.MAX_VALUE)
					{
						length = convertUp(data[dataIndex + 1]);
						dataIndex += 2;
					}
					else if (data[dataIndex] == Byte.MIN_VALUE)
					{
						length = toInteger(new byte[] {data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3], data[dataIndex + 4]});
						dataIndex += 5;
					}
					array = new byte[amount][length];
					for (int index = 0; index < array.length; index++)
					{
						for (int subIndex = 0; subIndex < length; subIndex++)
						{
							array[index][subIndex] = data[dataIndex + subIndex];
						}
						dataIndex += length;
					}
				}
				else
				{
					array = new byte[amount][];
					for (int index = 0; index < array.length; index++)
					{
						int length = 0;
						if (data[dataIndex] == Byte.MAX_VALUE)
						{
							length = convertUp(data[dataIndex + 1]);
							dataIndex += 2;
						}
						else if (data[dataIndex] == Byte.MIN_VALUE)
						{
							length = toInteger(new byte[] {data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3], data[dataIndex + 4]});
							dataIndex += 5;
						}
						array[index] = new byte[length];
						for (int subIndex = 0; subIndex < length; subIndex++)
						{
							array[index][subIndex] = data[dataIndex + subIndex];
						}
						dataIndex += length;
					}
				}
				
				return array;
			}
		}
		return new byte[0][];
	}
	
	public static byte[] from2DByteArray(byte[][] array)
	{
		if (array.length == 0)
		{
			return new byte[0];
		}
		else if (array.length == 1)
		{
			byte[] data = new byte[array[0].length + 1];
			data[0] = 0;
			for (int index = 0; index < array[0].length; index++)
			{
				data[index + 1] = array[0][index];
			}
			return data;
		}
		else
		{
			boolean uniformLength = true;
			byte[] arrayData;
			int firstLength = array[0].length;
			for (int index = 1; index < array.length; index++)
			{
				if (array[index].length != firstLength)
				{
					uniformLength = false;
					break;
				}
			}
			if (uniformLength)
			{
				int dataIndex;
				if (firstLength < 256)
				{
					arrayData = new byte[2 + firstLength * array.length];
					arrayData[0] = Byte.MAX_VALUE;
					arrayData[1] = convertDown(firstLength);
					dataIndex = 2;
				}
				else
				{
					arrayData = new byte[5 + firstLength * array.length];
					arrayData[0] = Byte.MIN_VALUE;
					byte[] lengthData = fromInteger(firstLength);
					arrayData[1] = lengthData[0];
					arrayData[2] = lengthData[1];
					arrayData[3] = lengthData[2];
					arrayData[4] = lengthData[3];
					dataIndex = 5;
				}
				for (int index = 0; index < array.length; index++)
				{
					for (int subIndex = 0; subIndex < firstLength; subIndex++)
					{
						arrayData[dataIndex + subIndex] = array[index][subIndex];
					}
					dataIndex += firstLength;
				}
			}
			else
			{
				List<Byte> byteList = new ArrayList<Byte>();
				for (int index = 0; index < array.length; index++)
				{
					if (array[index].length < 256)
					{
						byteList.add(Byte.MAX_VALUE);
						byteList.add(convertDown(array[index].length));
					}
					else
					{
						byteList.add(Byte.MIN_VALUE);
						byte[] lengthData = fromInteger(array[index].length);
						byteList.add(lengthData[0]);
						byteList.add(lengthData[1]);
						byteList.add(lengthData[2]);
						byteList.add(lengthData[3]);
					}
					for (int subIndex = 0; subIndex < array[index].length; subIndex++)
					{
						byteList.add(array[index][subIndex]);
					}
				}
				arrayData = new byte[byteList.size()];
				Iterator<Byte> iterator = byteList.iterator();
				for (int index = 0; index < arrayData.length; index++)
				{
					arrayData[index] = iterator.next().byteValue();
				}
			}
			
			byte[] data;
			if (array.length < 256)
			{
				data = new byte[arrayData.length + 3];
				data[1] = Byte.MAX_VALUE;
				data[2] = convertDown(array.length);
			}
			else
			{
				data = new byte[arrayData.length + 6];
				data[1] = Byte.MIN_VALUE;
				byte[] lengthData = fromInteger(array.length);
				data[2] = lengthData[0];
				data[3] = lengthData[1];
				data[4] = lengthData[2];
				data[5] = lengthData[3];
			}
			if (uniformLength)
			{
				data[0] = 1;
			}
			else
			{
				data[0] = 2;
			}
			for (int index = 0; index < arrayData.length; index++)
			{
				data[data.length - arrayData.length + index] = arrayData[index];
			}
			return data;
		}
	}
	
	public static byte toggleBit(byte value, int index)
	{
		if (index >= 0 && index <= 7)
		{
			return setBit(value, index, !getBit(value, index));
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toggleBit - Index not in valid range: Expected 0-7, got " + index);
		}
	}
	
	public static byte setBit(byte value, int index, boolean state)
	{
		if (index >= 0 && index <= 7)
		{
			if (index == 0)
			{
				value = (byte)((value >>> 1) << 1);
				if (state)
				{
					value = (byte)(value | 1);
				}
			}
			else if (index == 7)
			{
				byte tempValue = (byte)(value >>> 7);
				value = (byte)(value - tempValue);
				if (state)
				{
					value = (byte)(value | (1 << 7));
				}
			}
			else
			{
				byte tempVal1 = (byte)((value >>> index + 1) << index + 1);
				byte tempVal2 = (byte)((value >>> index) << index);
				value = (byte)(tempVal1 | tempVal2);
				if (state)
				{
					value = (byte)(value | (1 << index - 1));
				}
			}
			return value;
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - setBit - Index not in valid range: Expected 0-7, got " + index);
		}
	}
	
	public static boolean getBit(byte value, int index)
	{
		if (index >= 0 && index <= 7)
		{
			if (index == 0)
			{
				byte tempVal = (byte)(value >>> 1);
				value -= (tempVal << 1);
			}
			else if (index == 7)
			{
				value = (byte)(value >>> 7);
			}
			else
			{
				byte tempVal1 = (byte)((value >>> index + 1) << index + 1);
				byte tempVal2 = (byte)((value >>> index) << index);
				value = (byte) (value - tempVal1);
				value = (byte) (value - tempVal2);
				value = (byte)(value >>> index - 1);
			}
			return (value == 1 ? true : false);
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - getBit - Index not in valid range: Expected 0-7, got " + index);
		}
	}
	
	public static int convertUp(byte value)
	{
		int newValue = (int)value;
		newValue += 128;
		return newValue;
	}
	
	public static byte convertDown(int value)
	{
		if (value >= 0 && value <= 255)
		{
			value -= 128;
			return (byte)value;
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - convertDown - Value not within unsigned byte range: Expected 0-255, got " + value);
		}
	}
	
	public static UUID toUUID(byte[] data)
	{
		return UUID.fromString(toString(data));
	}
	
	public static byte[] fromUUID(UUID id)
	{
		return fromString(id.toString());
	}
	
	public static byte[] fromInteger(int value)
	{
		byte[] data = new byte[4];
		data[0] = convertDown(value >>> 24);
		value -= convertUp(data[0]) << 24;
		data[1] = convertDown(value >> 16);
		value -= convertUp(data[1]) << 16;
		data[2] = convertDown(value >> 8);
		value -= convertUp(data[2]) << 8;
		data[3] = convertDown(value);
		return data;
	}
	
	public static int toInteger(byte[] data)
	{
		if (data.length == 4)
		{
			return ((convertUp(data[0]) << 24) | (convertUp(data[1]) << 16) | (convertUp(data[2]) << 8) | convertUp(data[3]));
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toInteger - Invalid byte array size: Expected 4, got " + data.length);
		}
	}
	
	public static byte[] fromLong(long value)
	{
		byte[] data = new byte[8];
		data[0] = convertDown((int)(value >>> 56));
		value -= (long)convertUp(data[0]) << 56;
		data[1] = convertDown((int)(value >> 48));
		value -= (long)convertUp(data[1]) << 48;
		data[2] = convertDown((int)(value >> 40));
		value -= (long)convertUp(data[2]) << 40;
		data[3] = convertDown((int)(value >>> 32));
		value -= (long)convertUp(data[3]) << 32;
		data[4] = convertDown((int)(value >>> 24));
		value -= (long)convertUp(data[4]) << 24;
		data[5] = convertDown((int)(value >> 16));
		value -= (long)convertUp(data[5]) << 16;
		data[6] = convertDown((int)(value >> 8));
		value -= (long)convertUp(data[6]) << 8;
		data[7] = convertDown((int)value);
		return data;
	}
	
	public static long toLong(byte[] data)
	{
		if (data.length == 8)
		{
			return (((long)convertUp(data[0]) << 56) | ((long)convertUp(data[1]) << 48) | ((long)convertUp(data[2]) << 40) | ((long)convertUp(data[3]) << 32) | ((long)convertUp(data[4]) << 24) | ((long)convertUp(data[5]) << 16) | ((long)convertUp(data[6]) << 8) | (long)convertUp(data[7]));
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toLong - Invalid byte array size: Expected 8, got " + data.length);
		}
	}
	
	public static byte[] fromString(String string)
	{
		byte[] data = new byte[string.length() * 2];
		int dataIndex = 0;
		for (int index = 0; index < string.length(); index++)
		{
			int character = (int)string.charAt(index);
			byte[] charData = fromInteger(character);
			data[dataIndex] = charData[2];
			data[dataIndex + 1] = charData[3];
			dataIndex += 2;
		}
		return data;
	}
	
	public static String toString(byte[] data)
	{
		if (data.length % 2 == 0)
		{
			String string = "";
			for (int index = 0; index < data.length; index += 2)
			{
				byte[] charData = {0, 0, data[index], data[index + 1]};
				string += (char)(toInteger(charData));
			}
			return string;
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toString - Invalid byte array size: Expected multiple of 2, got " + data.length);
		}
	}
	
	public static byte fromBoolean(boolean value)
	{
		if (value)
		{
			return Byte.MAX_VALUE;
		}
		return Byte.MIN_VALUE;
	}
	
	public static boolean toBoolean(byte data)
	{
		if (data == Byte.MAX_VALUE)
		{
			return true;
		}
		return false;
	}
	
	public static byte[] fromFloatC(float value)
	{
		return fromInteger(Float.floatToIntBits(value));
	}
	
	public static byte[] fromFloat(float value)
	{
		byte[] data = new byte[128];
		String strValue = "" + value;
		long integerNumber = Long.parseLong(strValue.substring(0, strValue.indexOf('.')));
		long decimalNumber = Long.parseLong(strValue.substring(strValue.indexOf('.') + 1, strValue.length()));
		byte[] integerData = fromLong(integerNumber);
		byte[] decimalData = fromLong(decimalNumber);
		for (int index = 0; index < 64; index++)
		{
			data[index] = integerData[index];
		}
		for (int index = 0; index < 64; index++)
		{
			data[64 + index] = decimalData[index];
		}
		return data;
	}
	
	public static float toFloatC(byte[] data)
	{
		return Float.intBitsToFloat(toInteger(data));
	}
	
	public static float toFloat(byte[] data)
	{
		if (data.length == 128)
		{
			byte[] integerData = new byte[64];
			byte[] decimalData = new byte[64];
			for (int index = 0; index < 64; index++)
			{
				integerData[index] = data[index];
			}
			for (int index = 0; index < 64; index++)
			{
				decimalData[index] = data[64 + index];
			}
			return Float.parseFloat(toLong(integerData) + "." + toLong(decimalData));
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toFloat - Invalid byte array size: Expected 128, got " + data.length);
		}
	}
	
	public static byte[] fromDoubleC(double value)
	{
		return fromLong(Double.doubleToLongBits(value));
	}
	
	public static byte[] fromDouble(double value)
	{
		byte[] data = new byte[128];
		String strValue = "" + value;
		long integerNumber = Long.parseLong(strValue.substring(0, strValue.indexOf('.')));
		long decimalNumber = Long.parseLong(strValue.substring(strValue.indexOf('.') + 1, strValue.length()));
		byte[] integerData = fromLong(integerNumber);
		byte[] decimalData = fromLong(decimalNumber);
		for (int index = 0; index < 64; index++)
		{
			data[index] = integerData[index];
		}
		for (int index = 0; index < 64; index++)
		{
			data[64 + index] = decimalData[index];
		}
		return data;
	}
	
	public static double toDoubleC(byte[] data)
	{
		return Double.doubleToLongBits(toLong(data));
	}
	
	public static double toDouble(byte[] data)
	{
		if (data.length == 128)
		{
			byte[] integerData = new byte[64];
			byte[] decimalData = new byte[64];
			for (int index = 0; index < 64; index++)
			{
				integerData[index] = data[index];
			}
			for (int index = 0; index < 64; index++)
			{
				decimalData[index] = data[64 + index];
			}
			return Double.parseDouble(toLong(integerData) + "." + toLong(decimalData));
		}
		else
		{
			throw new IllegalStateException("BYTE CONVERTER - toFloat - Invalid byte array size: Expected 128, got " + data.length);
		}
	}
}