package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Chest;
import org.bukkit.block.Container;

public class CChest extends CContainerType
{
	@Override
	CContainerType getNew()
	{
		return new CChest();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof Chest);
	}
	
	@Override
	byte[] export()
	{
		return new byte[] {0};
	}
	
	@Override
	void load(byte[] data)
	{
		
	}
	
	@Override
	void create(Container container)
	{
		
	}
	
	@Override
	Container getContainer(Container container)
	{
		return container;
	}
}