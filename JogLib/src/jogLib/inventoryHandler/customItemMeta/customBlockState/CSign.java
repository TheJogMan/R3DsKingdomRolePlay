package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import jogLib.ByteConverter;

public class CSign extends CBlockState
{
	String[] lines;
	
	@Override
	CBlockState getNew()
	{
		return new CSign();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Sign);
	}
	
	@Override
	byte[] export()
	{
		byte[][] lineData = new byte[lines.length][];
		int lineDataLength = 0;
		for (int index = 0; index < lines.length; index++)
		{
			lineData[index] = ByteConverter.fromString(lines[index]);
			lineDataLength += lineData[index].length;
		}
		byte[] data = new byte[4 * (lineData.length + 1) + lineDataLength];
		byte[] lineCountData = ByteConverter.fromInteger(lineData.length);
		data[0] = lineCountData[0];
		data[1] = lineCountData[1];
		data[2] = lineCountData[2];
		data[3] = lineCountData[3];
		int dataIndex = 4;
		for (int index = 0; index < lineData.length; index++)
		{
			byte[] lineLengthData = ByteConverter.fromInteger(lineData[index].length);
			data[dataIndex] = lineLengthData[0];
			data[dataIndex + 1] = lineLengthData[1];
			data[dataIndex + 2] = lineLengthData[2];
			data[dataIndex + 3] = lineLengthData[3];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < lineData[index].length; subIndex++)
			{
				data[dataIndex + subIndex] = lineData[index][subIndex];
			}
			dataIndex += lineData[index].length;
		}
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		lines = new String[ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]})];
		int dataIndex = 4;
		for (int index = 0; index < lines.length; index++)
		{
			byte[] lineData = new byte[ByteConverter.toInteger(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3]})];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < lineData.length; subIndex++)
			{
				lineData[subIndex] = data[dataIndex + subIndex];
			}
			dataIndex += lineData.length;
			lines[index] = ByteConverter.toString(lineData);
		}
	}
	
	@Override
	void create(BlockState blockState)
	{
		Sign sign = (Sign)blockState;
		lines = sign.getLines();
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Sign sign = (Sign)blockState;
		for (int index = 0; index < lines.length; index++)
		{
			sign.setLine(index, lines[index]);
		}
		return sign;
	}
}