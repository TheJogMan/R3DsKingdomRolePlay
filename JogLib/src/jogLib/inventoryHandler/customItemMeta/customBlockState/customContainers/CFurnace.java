package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Container;
import org.bukkit.block.Furnace;

import jogLib.ByteConverter;

public class CFurnace extends CContainerType
{
	int burnTime;
	int cookTime;
	
	@Override
	CContainerType getNew()
	{
		return new CFurnace();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof Furnace);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[8];
		byte[] burnData = ByteConverter.fromInteger(burnTime);
		byte[] cookData = ByteConverter.fromInteger(cookTime);
		data[0] = burnData[0];
		data[1] = burnData[1];
		data[2] = burnData[2];
		data[3] = burnData[3];
		data[4] = cookData[0];
		data[5] = cookData[1];
		data[6] = cookData[2];
		data[7] = cookData[3];
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		burnTime = ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]});
		cookTime = ByteConverter.toInteger(new byte[] {data[4], data[5], data[6], data[7]});
	}
	
	@Override
	void create(Container container)
	{
		Furnace furnace = (Furnace)container;
		burnTime = furnace.getBurnTime();
		cookTime = furnace.getCookTime();
	}
	
	@Override
	Container getContainer(Container container)
	{
		Furnace furnace = (Furnace)container;
		furnace.setBurnTime((short)burnTime);
		furnace.setCookTime((short)cookTime);
		return furnace;
	}
}