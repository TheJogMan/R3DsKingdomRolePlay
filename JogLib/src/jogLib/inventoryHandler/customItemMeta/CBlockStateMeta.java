package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.customItemMeta.customBlockState.CBlockState;

public class CBlockStateMeta extends CItemMeta
{
	int type;
	String data;
	CBlockState blockState;
	
	@Override
	CItemMeta getNew()
	{
		return new CBlockStateMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof BlockStateMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		BlockStateMeta blockStateMeta = (BlockStateMeta)meta;
		BlockState blockState = blockStateMeta.getBlockState();
		Material[] materials = Material.values();
		for (int index = 0; index < materials.length; index++)
		{
			if (materials[index].equals(blockState.getType()))
			{
				type = index;
				break;
			}
		}
		data = blockState.getBlockData().getAsString();
		this.blockState = CBlockState.getCBlockState(blockState);
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		BlockStateMeta blockStateMeta = (BlockStateMeta)meta;
		blockStateMeta.setBlockState(CBlockState.getBlockStateObject(blockStateMeta.getBlockState(), blockState));
		BlockState blockState = blockStateMeta.getBlockState();
		blockState.setType(Material.values()[type]);
		blockState.setBlockData(Bukkit.getServer().createBlockData(data));
		blockStateMeta.setBlockState(blockState);
		return blockStateMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		type = ByteConverter.toInteger(array[0]);
		this.data = ByteConverter.toString(array[1]);
		blockState = CBlockState.loadData(array[2]);
	}
	
	@Override
	byte[] export()
	{
		byte[][] data = new byte[3][];
		data[0] = ByteConverter.fromInteger(type);
		data[1] = ByteConverter.fromString(this.data);
		data[2] = CBlockState.getBytes(blockState);
		return ByteConverter.from2DByteArray(data);
	}
}