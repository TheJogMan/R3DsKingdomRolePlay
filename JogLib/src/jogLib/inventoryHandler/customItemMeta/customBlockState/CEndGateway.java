package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CLocation;

public class CEndGateway extends CBlockState
{
	CLocation exitLocation;
	boolean exactTeleport;
	
	@Override
	CBlockState getNew()
	{
		return new CEndGateway();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof EndGateway);
	}
	
	@Override
	byte[] export()
	{
		byte[] locationData = exitLocation.export();
		byte[] data = new byte[1 + locationData.length];
		data[0] = ByteConverter.fromBoolean(exactTeleport);
		for (int index = 0; index < locationData.length; index++)
		{
			data[1 + index] = locationData[index];
		}
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		exactTeleport = ByteConverter.toBoolean(data[0]);
		byte[] locationData = new byte[data.length - 1];
		for (int index = 0; index < locationData.length; index++)
		{
			locationData[index] = data[1 + index];
		}
		exitLocation = new CLocation(locationData);
	}
	
	@Override
	void create(BlockState blockState)
	{
		EndGateway endGateway = (EndGateway)blockState;
		exitLocation = new CLocation(endGateway.getExitLocation());
		exactTeleport = endGateway.isExactTeleport();
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		EndGateway endGateway = (EndGateway)blockState;
		endGateway.setExitLocation(exitLocation.getLocation());
		endGateway.setExactTeleport(exactTeleport);
		return endGateway;
	}
}