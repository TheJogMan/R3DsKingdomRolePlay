package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Container;
import org.bukkit.block.Hopper;

public class CHopper extends CContainerType
{
	@Override
	CContainerType getNew()
	{
		return new CHopper();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof Hopper);
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