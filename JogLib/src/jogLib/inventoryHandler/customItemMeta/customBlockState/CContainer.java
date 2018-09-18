package jogLib.inventoryHandler.customItemMeta.customBlockState;

import java.util.Iterator;

import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CItemStack;
import jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers.CContainerType;

public class CContainer extends CBlockState
{
	CContainerType container;
	CItemStack[] inventory;
	
	@Override
	CBlockState getNew()
	{
		return new CContainer();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Container);
	}
	
	@Override
	byte[] export()
	{
		byte[][] data = new byte[2][];
		data[0] = CContainerType.getBytes(container);
		byte[][] itemData = new byte[inventory.length][];
		for (int index = 0; index < inventory.length; index++)
		{
			itemData[index] = inventory[index].export();
		}
		data[1] = ByteConverter.from2DByteArray(itemData);
		return ByteConverter.from2DByteArray(data);
	}
	
	@Override
	void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		container = CContainerType.loadData(array[0]);
		byte[][] itemData = ByteConverter.to2DByteArray(array[1]);
		inventory = new CItemStack[itemData.length];
		for (int index = 0; index < inventory.length; index++)
		{
			inventory[index] = new CItemStack(itemData[index]);
		}
	}
	
	@Override
	void create(BlockState blockState)
	{
		Container container = (Container)blockState;
		this.container = CContainerType.getCContainerType(container);
		inventory = new CItemStack[container.getInventory().getSize()];
		Iterator<ItemStack> iterator = container.getInventory().iterator();
		for (int index = 0; index < inventory.length; index++)
		{
			inventory[index] = new CItemStack(iterator.next());
		}
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Container container = (Container)blockState;
		container = CContainerType.getContainerObject(container, this.container);
		ItemStack[] items = new ItemStack[inventory.length];
		for (int index = 0; index < inventory.length; index++)
		{
			items[index] = inventory[index].getItemStack();
		}
		container.getInventory().setContents(items);
		return container;
	}
}