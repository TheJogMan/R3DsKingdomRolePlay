package jogLib.inventoryHandler.customItemMeta;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CPotionEffect;

public class CPotionMeta extends CItemMeta
{
	int color;
	byte type;
	boolean extended;
	boolean upgraded;
	CPotionEffect[] effects;
	
	@Override
	CItemMeta getNew()
	{
		return new CPotionMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof PotionMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		PotionMeta potionMeta = (PotionMeta)meta;
		PotionType[] types = PotionType.values();
		for (byte index = 0; index < types.length; index++)
		{
			if (types[index].equals(potionMeta.getBasePotionData().getType()))
			{
				type = index;
				break;
			}
		}
		extended = potionMeta.getBasePotionData().isExtended();
		upgraded = potionMeta.getBasePotionData().isUpgraded();
		if (potionMeta.hasColor())
		{
			color = potionMeta.getColor().asRGB();
		}
		else
		{
			color = -1;
		}
		List<PotionEffect> effects = potionMeta.getCustomEffects();
		this.effects = new CPotionEffect[effects.size()];
		Iterator<PotionEffect> iterator = effects.iterator();
		for (int index = 0; index < this.effects.length; index++)
		{
			this.effects[index] = new CPotionEffect(iterator.next());
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		PotionMeta potionMeta = (PotionMeta)meta;
		if (color != -1)
		{
			potionMeta.setColor(Color.fromRGB(color));
		}
		PotionType potionType = PotionType.values()[type];
		boolean isUpgraded = upgraded;
		boolean isExtended = extended;
		if (!potionType.isExtendable()) isExtended = false;
		if (!potionType.isUpgradeable()) isUpgraded = false;
		potionMeta.setBasePotionData(new PotionData(potionType, isExtended, isUpgraded));
		for (int index = 0; index < effects.length; index++)
		{
			potionMeta.addCustomEffect(effects[index].getPotionEffect(), true);
		}
		return potionMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		type = array[0][0];
		extended = ByteConverter.toBoolean(array[0][1]);
		upgraded = ByteConverter.toBoolean(array[0][2]);
		color = ByteConverter.toInteger(array[1]);
		byte[][] effectData = ByteConverter.to2DByteArray(array[2]);
		effects = new CPotionEffect[effectData.length];
		for (int index = 0; index < effects.length; index++)
		{
			effects[index] = new CPotionEffect(effectData[index]);
		}
	}
	
	@Override
	byte[] export()
	{
		byte[][] data = new byte[3][];
		data[0] = new byte[] {type, ByteConverter.fromBoolean(extended), ByteConverter.fromBoolean(upgraded)};
		data[1] = ByteConverter.fromInteger(color);
		byte[][] effectData = new byte[effects.length][];
		for (int index = 0; index < effects.length; index++)
		{
			effectData[index] = effects[index].export();
		}
		data[2] = ByteConverter.from2DByteArray(effectData);
		return ByteConverter.from2DByteArray(data);
	}
}