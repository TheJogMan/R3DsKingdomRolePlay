package jogLib.inventoryHandler.customItemMeta.customBlockState;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import jogLib.ByteConverter;

public class CSkull extends CBlockState
{
	String playerID;
	
	@Override
	CBlockState getNew()
	{
		return new CSkull();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Skull);
	}
	
	@Override
	byte[] export()
	{
		return ByteConverter.fromString(playerID);
	}
	
	@Override
	void load(byte[] data)
	{
		playerID = ByteConverter.toString(data);
	}
	
	@Override
	void create(BlockState blockState)
	{
		Skull skull = (Skull)blockState;
		playerID = skull.getOwningPlayer().getUniqueId().toString();
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Skull skull = (Skull)blockState;
		skull.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(playerID)));
		return skull;
	}
}