package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Beacon;
import org.bukkit.block.Container;

import jogLib.inventoryHandler.CPotionEffect;

public class CBeacon extends CContainerType
{
	CPotionEffect mainEffect;
	CPotionEffect secondaryEffect;
	
	@Override
	CContainerType getNew()
	{
		return new CBeacon();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof Beacon);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[18];
		byte[] mainData = mainEffect.export();
		byte[] secondaryData = secondaryEffect.export();
		for (int index = 0; index < 9; index++)
		{
			data[index] = mainData[index];
		}
		for (int index = 0; index < 9; index++)
		{
			data[9 + index] = secondaryData[index];
		}
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		byte[] mainData = new byte[9];
		byte[] secondaryData = new byte[9];
		for (int index = 0; index < 9; index++)
		{
			mainData[index] = data[index];
		}
		for (int index = 0; index < 9; index++)
		{
			secondaryData[index] = data[9 + index];
		}
		mainEffect = new CPotionEffect(mainData);
		secondaryEffect = new CPotionEffect(secondaryData);
	}
	
	@Override
	void create(Container container)
	{
		Beacon beacon = (Beacon)container;
		mainEffect = new CPotionEffect(beacon.getPrimaryEffect());
		secondaryEffect = new CPotionEffect(beacon.getSecondaryEffect());
	}
	
	@Override
	Container getContainer(Container container)
	{
		Beacon beacon = (Beacon)container;
		beacon.setPrimaryEffect(mainEffect.getPotionEffect().getType());
		beacon.setSecondaryEffect(secondaryEffect.getPotionEffect().getType());
		return beacon;
	}
}