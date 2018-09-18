package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.Conduit;

public class CConduit extends CBlockState
{
	@Override
	CBlockState getNew()
	{
		return new CConduit();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Conduit);
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