package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;

import jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers.CContainerType;

public abstract class CBlockState
{
	abstract byte[] export();
	abstract void load(byte[] data);
	abstract void create(BlockState meta);
	abstract BlockState getBlockState(BlockState blockState);
	abstract CBlockState getNew();
	abstract boolean check(BlockState blockState);
	
	byte id;
	
	protected static CBlockState[] blockStateObjects = new CBlockState[] {new CBanner(), new CCommandBlock(), new CComparator(), new CConduit(), new CContainer(), new CCreatureSpawner(), new CDaylightDetector(),
																		  new CEnchantingTable(), new CEnderChest(), new CEndGateway(), new CJukebox(), new CSign(), new CSkull(), new CStructure()};
	
	public byte getID()
	{
		return id;
	}
	
	void setID(byte id)
	{
		this.id = id;
	}
	
	public static void init()
	{
		for (byte index = 0; index < blockStateObjects.length; index++)
		{
			blockStateObjects[index].setID(index);
		}
		CContainerType.init();
	}
	
	public static BlockState getBlockStateObject(BlockState blockState, CBlockState cBlockState)
	{
		if (cBlockState != null)
		{
			return cBlockState.getBlockState(blockState);
		}
		return blockState;
	}
	
	public static CBlockState loadData(byte[] fullData)
	{
		byte[] blockStateData = new byte[fullData.length - 1];
		byte type = fullData[0];
		for (int index = 0; index < blockStateData.length; index++)
		{
			blockStateData[index] = fullData[index + 1];
		}
		
		CBlockState cBlockState = null;
		for (int index = 0; index < blockStateObjects.length; index++)
		{
			if (type == blockStateObjects[index].getID())
			{
				cBlockState = blockStateObjects[index].getNew();
				cBlockState.setID(blockStateObjects[index].getID());
				cBlockState.load(blockStateData);
			}
		}
		return cBlockState;
	}
	
	public static CBlockState getCBlockState(BlockState blockState)
	{
		CBlockState cBlockState = null;
		for (int index = 0; index < blockStateObjects.length; index++)
		{
			if (blockStateObjects[index].check(blockState))
			{
				cBlockState = blockStateObjects[index].getNew();
				cBlockState.setID(blockStateObjects[index].getID());
				cBlockState.create(blockState);
			}
		}
		return cBlockState;
	}
	
	public static byte[] getBytes(CBlockState cBlockState)
	{
		if (cBlockState != null)
		{
			byte[] blockStateData = cBlockState.export();
			byte[] data = new byte[blockStateData.length + 1];
			data[0] = cBlockState.getID();
			for (int index = 0; index < blockStateData.length; index++)
			{
				data[index + 1] = blockStateData[index];
			}
			return data;
		}
		else
		{
			return new byte[] {-1, 0};
		}
	}
}