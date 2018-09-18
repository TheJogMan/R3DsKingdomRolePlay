package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;

public class CShulkerBox extends CContainerType
{
	@Override
	CContainerType getNew()
	{
		return new CShulkerBox();
	}
	
	@Override
	boolean check(Container container)
	{
		return (container instanceof ShulkerBox);
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