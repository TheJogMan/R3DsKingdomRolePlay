package jogLib.inventoryHandler.customItemMeta.customBlockState.customContainers;

import org.bukkit.block.Container;

public abstract class CContainerType
{
	abstract byte[] export();
	abstract void load(byte[] data);
	abstract void create(Container meta);
	abstract Container getContainer(Container container);
	abstract CContainerType getNew();
	abstract boolean check(Container container);
	
	byte id;
	
	protected static CContainerType[] containerTypeObjects = new CContainerType[] {new CBeacon(), new CBrewingStand(), new CChest(), new CDispenser(), new CDropper(), new CFurnace(), new CHopper(), new CShulkerBox()};
	
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
		for (byte index = 0; index < containerTypeObjects.length; index++)
		{
			containerTypeObjects[index].setID(index);
		}
	}
	
	public static Container getContainerObject(Container container, CContainerType cContainer)
	{
		if (cContainer != null)
		{
			return cContainer.getContainer(container);
		}
		return container;
	}
	
	public static CContainerType loadData(byte[] fullData)
	{
		byte[] containerTypeData = new byte[fullData.length - 1];
		byte type = fullData[0];
		for (int index = 0; index < containerTypeData.length; index++)
		{
			containerTypeData[index] = fullData[index + 1];
		}
		
		CContainerType cContainerType = null;
		for (int index = 0; index < containerTypeObjects.length; index++)
		{
			if (type == containerTypeObjects[index].getID())
			{
				cContainerType = containerTypeObjects[index].getNew();
				cContainerType.setID(containerTypeObjects[index].getID());
				cContainerType.load(containerTypeData);
			}
		}
		return cContainerType;
	}
	
	public static CContainerType getCContainerType(Container container)
	{
		CContainerType cContainerType = null;
		for (int index = 0; index < containerTypeObjects.length; index++)
		{
			if (containerTypeObjects[index].check(container))
			{
				cContainerType = containerTypeObjects[index].getNew();
				cContainerType.setID(containerTypeObjects[index].getID());
				cContainerType.create(container);
			}
		}
		return cContainerType;
	}
	
	public static byte[] getBytes(CContainerType cContainer)
	{
		if (cContainer != null)
		{
			byte[] metaData = cContainer.export();
			byte[] data = new byte[metaData.length + 1];
			data[0] = cContainer.getID();
			for (int index = 0; index < metaData.length; index++)
			{
				data[index + 1] = metaData[index];
			}
			return data;
		}
		else
		{
			return new byte[] {-1, 0};
		}
	}
}