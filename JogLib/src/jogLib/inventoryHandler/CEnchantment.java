package jogLib.inventoryHandler;

import org.bukkit.enchantments.Enchantment;

import jogLib.ByteConverter;

public class CEnchantment
{
	int type;
	int level;
	
	public CEnchantment(Enchantment enchantment, int level)
	{
		this.level = level;
		Enchantment[] enchantments = Enchantment.values();
		type = -1;
		for (int index = 0; index < enchantments.length; index++)
		{
			if (enchantments[index].equals(enchantment))
			{
				type = index;
				break;
			}
		}
	}
	
	public CEnchantment(byte[] data)
	{
		type = ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]});
		level = ByteConverter.toInteger(new byte[] {data[4], data[5], data[6], data[7]});
	}
	
	public Enchantment getEnchantment()
	{
		return Enchantment.values()[type];
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public byte[] export()
	{
		byte[] data = new byte[8];
		byte[] typeData = ByteConverter.fromInteger(type);
		byte[] levelData = ByteConverter.fromInteger(level);
		data[0] = typeData[0];
		data[1] = typeData[1];
		data[2] = typeData[2];
		data[3] = typeData[3];
		data[4] = levelData[0];
		data[5] = levelData[1];
		data[6] = levelData[2];
		data[7] = levelData[3];
		return data;
	}
}