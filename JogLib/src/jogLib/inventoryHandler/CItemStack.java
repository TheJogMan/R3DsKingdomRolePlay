package jogLib.inventoryHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.customItemMeta.CItemMeta;

public class CItemStack
{
	boolean unbreakable;
	byte itemFlags;
	int type;
	int amount;
	int damage;
	int repairCost;
	String name;
	String localizedName;
	CItemMeta itemMeta;
	CEnchantment[] enchantments;
	String[] lore;
	
	public CItemStack(byte[] data)
	{
		load(data);
	}
	
	public CItemStack(ItemStack item)
	{
		if (item != null)
		{
			Material[] materials = Material.values();
			for (int index = 0; index < materials.length; index++)
			{
				if (materials[index].equals(item.getType()))
				{
					type = index;
					break;
				}
			}
			amount = item.getAmount();
			ItemMeta meta = item.getItemMeta();
			if (meta instanceof Damageable)
			{
				damage = ((Damageable)meta).getDamage();
			}
			if (meta instanceof Repairable)
			{
				repairCost = ((Repairable)meta).getRepairCost();
			}
			unbreakable = meta.isUnbreakable();
			Map<Enchantment, Integer> enchantmentsMap = meta.getEnchants();
			enchantments = new CEnchantment[enchantmentsMap.size()];
			Set<Enchantment> keySet = enchantmentsMap.keySet();
			Iterator<Enchantment> enchantmentIterator = keySet.iterator();
			for (int index = 0; index < enchantments.length; index++)
			{
				Enchantment enchantment = enchantmentIterator.next();
				enchantments[index] = new CEnchantment(enchantment, enchantmentsMap.get(enchantment).intValue());
			}
			name = meta.getDisplayName();
			localizedName = meta.getLocalizedName();
			List<String> loreList = meta.getLore();
			if (loreList != null)
			{
				lore = new String[loreList.size()];
				Iterator<String> loreIterator = loreList.iterator();
				for (int index = 0; index < lore.length; index++)
				{
					lore[index] = loreIterator.next();
				}
			}
			else
			{
				lore = new String[0];
			}
			itemMeta = CItemMeta.getCItemMeta(meta, item);
			Set<ItemFlag> flags = meta.getItemFlags();
			itemFlags = 0;
			if (flags.contains(ItemFlag.HIDE_ATTRIBUTES))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 0, true);
			}
			if (flags.contains(ItemFlag.HIDE_DESTROYS))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 1, true);
			}
			if (flags.contains(ItemFlag.HIDE_ENCHANTS))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 2, true);
			}
			if (flags.contains(ItemFlag.HIDE_PLACED_ON))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 3, true);
			}
			if (flags.contains(ItemFlag.HIDE_POTION_EFFECTS))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 4, true);
			}
			if (flags.contains(ItemFlag.HIDE_UNBREAKABLE))
			{
				itemFlags = ByteConverter.setBit(itemFlags, 5, true);
			}
		}
		else
		{
			type = -1;
			unbreakable = true;
			itemFlags = 0;
			amount = 0;
			damage = 0;
			repairCost = 0;
			name = "Null";
			localizedName = "Null";
			itemMeta = null;
			enchantments = new CEnchantment[0];
			lore = new String[0];
		}
	}
	
	public ItemStack getItemStack()
	{
		if (type != -1)
		{
			ItemStack item = new ItemStack(Material.values()[type]);
			item.setAmount(amount);
			item.setItemMeta(CItemMeta.getItemMetaObject(item.getItemMeta(), itemMeta));
			ItemMeta meta = item.getItemMeta();
			if (meta instanceof Damageable)
			{
				((Damageable)meta).setDamage(damage);
			}
			if (meta instanceof Repairable)
			{
				((Repairable)meta).setRepairCost(repairCost);
			}
			meta.setDisplayName(name);
			meta.setLocalizedName(localizedName);
			meta.setUnbreakable(unbreakable);
			for (int index = 0; index < enchantments.length; index++)
			{
				meta.addEnchant(Enchantment.values()[enchantments[index].type], enchantments[index].level, true);
			}
			List<String> loreList = new ArrayList<String>();
			for (int index = 0; index < lore.length; index++)
			{
				loreList.add(lore[index]);
			}
			meta.setLore(loreList);
			if (ByteConverter.getBit(itemFlags, 0))
			{
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			}
			if (ByteConverter.getBit(itemFlags, 1))
			{
				meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			}
			if (ByteConverter.getBit(itemFlags, 2))
			{
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			if (ByteConverter.getBit(itemFlags, 3))
			{
				meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
			}
			if (ByteConverter.getBit(itemFlags, 4))
			{
				meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			}
			if (ByteConverter.getBit(itemFlags, 5))
			{
				meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			}
			item.setItemMeta(meta);
			item = CItemMeta.itemFinalization(itemMeta, item);
			return item;
		}
		else
		{
			return null;
		}
	}
	
	public void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		itemFlags = array[0][0];
		unbreakable = ByteConverter.toBoolean(array[0][1]);
		type = ByteConverter.toInteger(array[1]);
		amount = ByteConverter.toInteger(array[2]);
		damage = ByteConverter.toInteger(array[3]);
		name = ByteConverter.toString(array[4]);
		localizedName = ByteConverter.toString(array[5]);
		itemMeta = CItemMeta.loadData(array[6]);
		lore = ByteConverter.toStringArray(array[7]);
		byte[][] enchantmentData = ByteConverter.to2DByteArray(array[8]);
		enchantments = new CEnchantment[enchantmentData.length];
		for (int index = 0; index < enchantments.length; index++)
		{
			enchantments[index] = new CEnchantment(enchantmentData[index]);
		}
		repairCost = ByteConverter.toInteger(array[9]);
	}
	
	public byte[] export()
	{
		byte[][] data = new byte[10][];
		data[0] = new byte[] {itemFlags, ByteConverter.fromBoolean(unbreakable)};
		data[1] = ByteConverter.fromInteger(type);
		data[2] = ByteConverter.fromInteger(amount);
		data[3] = ByteConverter.fromInteger(damage);
		data[4] = ByteConverter.fromString(name);
		data[5] = ByteConverter.fromString(localizedName);
		data[6] = CItemMeta.getBytes(itemMeta);
		data[7] = ByteConverter.fromStringArray(lore);
		byte[][] enchantmentData = new byte[enchantments.length][];
		for (int index = 0; index < enchantments.length; index++)
		{
			enchantmentData[index] = enchantments[index].export();
		}
		data[8] = ByteConverter.from2DByteArray(enchantmentData);
		data[9] = ByteConverter.fromInteger(repairCost);
		return ByteConverter.from2DByteArray(data);
	}
}