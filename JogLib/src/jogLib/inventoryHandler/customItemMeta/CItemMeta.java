package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.inventoryHandler.customItemMeta.customBlockState.CBlockState;

public abstract class CItemMeta
{
	abstract byte[] export();
	abstract void load(byte[] data);
	abstract void create(ItemMeta meta, ItemStack item);
	abstract CItemMeta getNew();
	abstract boolean check(ItemMeta meta);
	abstract ItemMeta getItemMeta(ItemMeta meta);
	abstract ItemStack itemFinalization(ItemStack item);
	
	byte id;
	
	protected static CItemMeta[] itemMetaObjects = {new CBannerMeta(), new CBlockStateMeta(), new CBookMeta(), new CEnchantmentStorageMeta(), new CFireworkEffectMeta(), new CFireworkMeta(), new CKnowledgeBookMeta(),
													new CLeatherArmorMeta(), new CMapMeta(), new CPotionMeta(), new CSkullMeta(), new CSpawnEggMeta(), new CTropicalFishBucketMeta()};
	
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
		for (byte index = 0; index < itemMetaObjects.length; index++)
		{
			itemMetaObjects[index].setID(index);
		}
		CBlockState.init();
	}
	
	public static ItemStack itemFinalization(CItemMeta cMeta, ItemStack item)
	{
		if (cMeta != null)
		{
			return cMeta.itemFinalization(item);
		}
		return item;
	}
	
	public static ItemMeta getItemMetaObject(ItemMeta meta, CItemMeta cMeta)
	{
		if (cMeta != null)
		{
			return cMeta.getItemMeta(meta);
		}
		return meta;
	}
	
	public static CItemMeta loadData(byte[] fullData)
	{
		byte[] metaData = new byte[fullData.length - 1];
		byte type = fullData[0];
		for (int index = 0; index < metaData.length; index++)
		{
			metaData[index] = fullData[index + 1];
		}
		
		CItemMeta cMeta = null;
		for (int index = 0; index < itemMetaObjects.length; index++)
		{
			if (type == itemMetaObjects[index].getID())
			{
				cMeta = itemMetaObjects[index].getNew();
				cMeta.setID(itemMetaObjects[index].getID());
				cMeta.load(metaData);
				break;
			}
		}
		return cMeta;
	}
	
	public static CItemMeta getCItemMeta(ItemMeta meta, ItemStack item)
	{
		CItemMeta cMeta = null;
		for (int index = 0; index < itemMetaObjects.length; index++)
		{
			if (itemMetaObjects[index].check(meta))
			{
				cMeta = itemMetaObjects[index].getNew();
				cMeta.setID(itemMetaObjects[index].getID());
				cMeta.create(meta, item);
				break;
			}
		}
		return cMeta;
	}
	
	public static byte[] getBytes(CItemMeta cMeta)
	{
		if (cMeta != null)
		{
			byte[] cMetaData = cMeta.export();
			byte[] data = new byte[cMetaData.length + 1];
			data[0] = cMeta.getID();
			for (int index = 0; index < cMetaData.length; index++)
			{
				data[index + 1] = cMetaData[index];
			}
			return data;
		}
		else
		{
			return new byte[] {-1, 0};
		}
	}
}