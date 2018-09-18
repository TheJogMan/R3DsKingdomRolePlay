package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class CSpawnEggMeta extends CItemMeta
{
	@Override
	CItemMeta getNew()
	{
		return new CSpawnEggMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof SpawnEggMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		return meta;
	}
	
	@Override
	void load(byte[] data)
	{
		
	}
	
	@Override
	byte[] export()
	{
		return new byte[] {0};
	}
}