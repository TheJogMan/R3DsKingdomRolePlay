package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;

import jogLib.ByteConverter;

public class CJukebox extends CBlockState
{
	int record;
	
	@Override
	CBlockState getNew()
	{
		return new CJukebox();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Jukebox);
	}
	
	@Override
	byte[] export()
	{
		return ByteConverter.fromInteger(record);
	}
	
	@Override
	void load(byte[] data)
	{
		record = ByteConverter.toInteger(data);
	}
	
	@Override
	void create(BlockState blockState)
	{
		Jukebox jukebox = (Jukebox)blockState;
		Material[] materials = Material.values();
		for (int index = 0; index < materials.length; index++)
		{
			if (materials[index].equals(jukebox.getPlaying()))
			{
				record = index;
				break;
			}
		}
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Jukebox jukebox = (Jukebox)blockState;
		jukebox.setPlaying(Material.values()[record]);
		return jukebox;
	}
}