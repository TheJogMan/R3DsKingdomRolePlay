package jogLib.inventoryHandler.customItemMeta;

import java.util.Iterator;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CFireworkEffect;

public class CFireworkMeta extends CItemMeta
{
	CFireworkEffect[] effects;
	int power;
	
	@Override
	CItemMeta getNew()
	{
		return new CFireworkMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof FireworkMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		FireworkMeta fireworkMeta = (FireworkMeta)meta;
		power = fireworkMeta.getPower();
		List<FireworkEffect> fireworkEffects = fireworkMeta.getEffects();
		effects = new CFireworkEffect[fireworkEffects.size()];
		Iterator<FireworkEffect> iterator = fireworkEffects.iterator();
		for (int index = 0; index < effects.length; index++)
		{
			effects[index] = new CFireworkEffect(iterator.next());
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		FireworkMeta fireworkMeta = (FireworkMeta)meta;
		fireworkMeta.setPower(power);
		for (int index = 0; index < effects.length; index++)
		{
			fireworkMeta.addEffect(effects[index].getEffect());
		}
		return fireworkMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		power = ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]});
		effects = new CFireworkEffect[ByteConverter.toInteger(new byte[] {data[4], data[5], data[6], data[7]})];
		int dataIndex = 8;
		for (int index = 0; index < effects.length; index++)
		{
			byte[] effectData = new byte[ByteConverter.toInteger(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3]})];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < effectData.length; subIndex++)
			{
				effectData[subIndex] = data[dataIndex + subIndex];
			}
			effects[index] = new CFireworkEffect(effectData);
			dataIndex += effectData.length;
		}
	}
	
	@Override
	byte[] export()
	{
		byte[][] effectData = new byte[effects.length][];
		int effectDataLength = 0;
		for (int index = 0; index < effects.length; index++)
		{
			effectData[index] = effects[index].export();
			effectDataLength += effectData[index].length;
		}
		byte[] data = new byte[8 + effectDataLength + 4 * effects.length];
		byte[] powerData = ByteConverter.fromInteger(power);
		byte[] effectCountData = ByteConverter.fromInteger(effects.length);
		data[0] = powerData[0];
		data[1] = powerData[1];
		data[2] = powerData[2];
		data[3] = powerData[3];
		data[4] = effectCountData[0];
		data[5] = effectCountData[1];
		data[6] = effectCountData[2];
		data[7] = effectCountData[3];
		int dataIndex = 8;
		for (int index = 0; index < effectData.length; index++)
		{
			byte[] dataLengthData = ByteConverter.fromInteger(effectData[index].length);
			data[dataIndex] = dataLengthData[0];
			data[dataIndex + 1] = dataLengthData[1];
			data[dataIndex + 2] = dataLengthData[2];
			data[dataIndex + 3] = dataLengthData[3];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < effectData[index].length; subIndex++)
			{
				data[dataIndex + subIndex] = effectData[index][subIndex];
			}
			dataIndex += effectData[index].length;
		}
		return data;
	}
}