package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.BrewingStand;
import org.bukkit.block.Container;

import jogLib.ByteConverter;

public class CBrewingStand extends CContainerType
{
	int fuelLevel;
	int brewTime;
	
	@Override
	CContainerType getNew()
	{
		return new CBrewingStand();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof BrewingStand);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[8];
		byte[] fuelData = ByteConverter.fromInteger(fuelLevel);
		byte[] brewData = ByteConverter.fromInteger(brewTime);
		data[0] = fuelData[0];
		data[1] = fuelData[1];
		data[2] = fuelData[2];
		data[3] = fuelData[3];
		data[4] = brewData[0];
		data[5] = brewData[1];
		data[6] = brewData[2];
		data[7] = brewData[3];
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		fuelLevel = ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]});
		brewTime = ByteConverter.toInteger(new byte[] {data[4], data[5], data[6], data[7]});
	}
	
	@Override
	void create(Container container)
	{
		BrewingStand brewingStand = (BrewingStand)container;
		fuelLevel = brewingStand.getFuelLevel();
		brewTime = brewingStand.getBrewingTime();
	}
	
	@Override
	Container getContainer(Container container)
	{
		BrewingStand brewingStand = (BrewingStand)container;
		brewingStand.setFuelLevel(fuelLevel);
		brewingStand.setBrewingTime(brewTime);
		return brewingStand;
	}
}