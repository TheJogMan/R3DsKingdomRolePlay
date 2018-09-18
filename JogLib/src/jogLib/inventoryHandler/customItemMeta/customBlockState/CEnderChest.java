package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.EnderChest;

public class CEnderChest extends CBlockState
{
	@Override
	CBlockState getNew()
	{
		return new CEnderChest();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof EnderChest);
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
	void create(BlockState blockState)
	{
		
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		return blockState;
	}
}