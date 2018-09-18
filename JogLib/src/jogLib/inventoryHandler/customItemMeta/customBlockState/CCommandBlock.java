package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;

import jogLib.ByteConverter;

public class CCommandBlock extends CBlockState
{
	String command;
	String name;
	
	@Override
	CBlockState getNew()
	{
		return new CCommandBlock();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof CommandBlock);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[4 + command.length() * 2 + name.length() * 2];
		byte[] commandLengthData = ByteConverter.fromInteger(command.length() * 2);
		byte[] commandData = ByteConverter.fromString(command);
		byte[] nameData = ByteConverter.fromString(name);
		data[0] = commandLengthData[0];
		data[1] = commandLengthData[1];
		data[2] = commandLengthData[2];
		data[3] = commandLengthData[3];
		for (int index = 0; index < commandData.length; index++)
		{
			data[4 + index] = commandData[index];
		}
		for (int index = 0; index < nameData.length; index++)
		{
			data[4 + commandData.length + index] = nameData[index];
		}
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		byte[] commandData = new byte[ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]})];
		byte[] nameData = new byte[data.length - commandData.length - 4];
		for (int index = 0; index < commandData.length; index++)
		{
			commandData[index] = data[4 + index];
		}
		for (int index = 0; index < nameData.length; index++)
		{
			nameData[index] = data[4 + commandData.length + index];
		}
		command = ByteConverter.toString(commandData);
		name = ByteConverter.toString(nameData);
	}
	
	@Override
	void create(BlockState blockState)
	{
		CommandBlock commandBlock = (CommandBlock)blockState;
		command = commandBlock.getCommand();
		name = commandBlock.getName();
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		CommandBlock commandBlock = (CommandBlock)blockState;
		commandBlock.setCommand(command);
		commandBlock.setName(name);
		return commandBlock;
	}
}