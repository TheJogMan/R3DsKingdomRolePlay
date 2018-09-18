package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.inventoryHandler.CFireworkEffect;

public class CFireworkEffectMeta extends CItemMeta
{
	CFireworkEffect effect;
	
	@Override
	CItemMeta getNew()
	{
		return new CFireworkEffectMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof FireworkEffectMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		FireworkEffectMeta fireworkEffectMeta = (FireworkEffectMeta)meta;
		effect = new CFireworkEffect(fireworkEffectMeta.getEffect());
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		FireworkEffectMeta fireworkEffectMeta = (FireworkEffectMeta)meta;
		fireworkEffectMeta.setEffect(effect.getEffect());
		return fireworkEffectMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		effect = new CFireworkEffect(data);
	}
	
	@Override
	byte[] export()
	{
		return effect.export();
	}
}