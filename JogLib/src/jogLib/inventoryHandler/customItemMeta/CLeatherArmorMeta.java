package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import jogLib.ByteConverter;

public class CLeatherArmorMeta extends CItemMeta
{
	int color;
	
	@Override
	CItemMeta getNew()
	{
		return new CLeatherArmorMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof LeatherArmorMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)meta;
		color = leatherArmorMeta.getColor().asRGB();
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)meta;
		leatherArmorMeta.setColor(Color.fromRGB(color));
		return leatherArmorMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		color = ByteConverter.toInteger(data);
	}
	
	@Override
	byte[] export()
	{
		return ByteConverter.fromInteger(color);
	}
}