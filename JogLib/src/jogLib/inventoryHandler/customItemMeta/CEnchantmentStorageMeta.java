package jogLib.inventoryHandler.customItemMeta;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.inventoryHandler.CEnchantment;

public class CEnchantmentStorageMeta extends CItemMeta
{
	CEnchantment[] enchantments;
	
	@Override
	CItemMeta getNew()
	{
		return new CEnchantmentStorageMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof EnchantmentStorageMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta)meta;
		Set<Enchantment> enchants = enchantmentStorageMeta.getEnchants().keySet();
		enchantments = new CEnchantment[enchants.size()];
		Iterator<Enchantment> iterator = enchants.iterator();
		for (int index = 0; index < enchantments.length; index++)
		{
			Enchantment enchantment = iterator.next();
			enchantments[index] = new CEnchantment(enchantment, enchantmentStorageMeta.getEnchantLevel(enchantment));
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta)meta;
		for (int index = 0; index < enchantments.length; index++)
		{
			enchantmentStorageMeta.addStoredEnchant(enchantments[index].getEnchantment(), enchantments[index].getLevel(), true);
		}
		return enchantmentStorageMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		enchantments = new CEnchantment[data.length / 8];
		int dataIndex = 0;
		for (int index = 0; index < enchantments.length; index++)
		{
			byte[] enchantmentData = new byte[8];
			for (int subIndex = 0; subIndex < 8; subIndex++)
			{
				enchantmentData[subIndex] = data[dataIndex + subIndex];
			}
			enchantments[index] = new CEnchantment(enchantmentData);
			dataIndex += 8;
		}
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[enchantments.length * 8];
		int dataIndex = 0;
		for (int index = 0; index < enchantments.length; index++)
		{
			byte[] enchantmentData = enchantments[index].export();
			for (int subIndex = 0; subIndex < 8; subIndex++)
			{
				data[dataIndex + subIndex] = enchantmentData[subIndex];
			}
			dataIndex += 8;
		}
		return data;
	}
}