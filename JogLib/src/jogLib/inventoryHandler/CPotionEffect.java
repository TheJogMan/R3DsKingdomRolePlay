package jogLib.inventoryHandler;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import jogLib.ByteConverter;

public class CPotionEffect
{
	int amplifier;
	int duration;
	boolean particles;
	boolean ambient;
	boolean isNull;
	byte type;
	
	public CPotionEffect(PotionEffect potionEffect)
	{
		if (potionEffect != null)
		{
			amplifier = potionEffect.getAmplifier();
			duration = potionEffect.getDuration();
			particles = potionEffect.hasParticles();
			ambient = potionEffect.isAmbient();
			PotionEffectType[] types = PotionEffectType.values();
			for (byte index = 0; index < types.length; index++)
			{
				if (types[index].equals(potionEffect.getType()))
				{
					type = index;
					break;
				}
			}
			isNull = false;
		}
		else
		{
			isNull = true;
			amplifier = 0;
			duration = 0;
			particles = false;
			ambient = false;
			type = 0;
		}
	}
	
	public CPotionEffect(byte[] data)
	{
		byte[][] newData = ByteConverter.to2DByteArray(data);
		type = newData[0][0];
		int[] intArray = ByteConverter.toIntegerArray(newData[1]);
		amplifier = intArray[0];
		duration = intArray[1];
		boolean[] booleanArray = ByteConverter.toBooleanArray(newData[2]);
		particles = booleanArray[0];
		ambient = booleanArray[1];
		isNull = booleanArray[2];
	}
	
	public byte[] export()
	{
		byte[][] data = new byte[3][];
		data[0] = new byte[] {type};
		data[1] = ByteConverter.fromIntegerArray(new int[] {amplifier, duration});
		data[2] = ByteConverter.fromBooleanArray(new boolean[] {particles, ambient, isNull, false, false, false, false, false});
		return ByteConverter.from2DByteArray(data);
	}
	
	public PotionEffect getPotionEffect()
	{
		if (!isNull)
		{
			return new PotionEffect(PotionEffectType.values()[type], duration, amplifier, ambient, particles);
		}
		else
		{
			return null;
		}
	}
}