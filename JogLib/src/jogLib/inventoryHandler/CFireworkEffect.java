package jogLib.inventoryHandler;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;

import jogLib.ByteConverter;

public class CFireworkEffect
{
	int[] colors;
	int[] fadeColors;
	boolean flicker;
	boolean trail;
	boolean isNull;
	byte type;
	
	public CFireworkEffect(FireworkEffect effect)
	{
		if (effect != null)
		{
			flicker = effect.hasFlicker();
			trail = effect.hasTrail();
			FireworkEffect.Type[] types = FireworkEffect.Type.values();
			for (byte index = 0; index < types.length; index++)
			{
				if (types[index].equals(effect.getType()))
				{
					type = index;
					break;
				}
			}
			List<Color> colorsList = effect.getColors();
			colors = new int[colorsList.size()];
			Iterator<Color> colorIterator = colorsList.iterator();
			for (int index = 0; index < colors.length; index++)
			{
				colors[index] = colorIterator.next().asRGB();
			}
			List<Color> fadeColorsList = effect.getFadeColors();
			fadeColors = new int[fadeColorsList.size()];
			Iterator<Color> fadeColorIterator = fadeColorsList.iterator();
			for (int index = 0; index < fadeColors.length; index++)
			{
				colors[index] = fadeColorIterator.next().asRGB();
			}
			isNull = false;
		}
		else
		{
			isNull = true;
			colors = new int[] {0};
			fadeColors = new int[] {0};
			flicker = false;
			trail = false;
			type = 0;
		}
	}
	
	public CFireworkEffect(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		type = array[0][0];
		colors = ByteConverter.toIntegerArray(array[1]);
		fadeColors = ByteConverter.toIntegerArray(array[2]);
		boolean[] booleanArray = ByteConverter.toBooleanArray(array[3]);
		flicker = booleanArray[0];
		trail = booleanArray[1];
		isNull = booleanArray[2];
	}
	
	public byte[] export()
	{
		byte[][] data = new byte[4][];
		data[0] = new byte[] {type};
		data[1] = ByteConverter.fromIntegerArray(colors);
		data[2] = ByteConverter.fromIntegerArray(fadeColors);
		data[3] = ByteConverter.fromBooleanArray(new boolean[] {flicker, trail, isNull, false, false, false, false, false});
		return ByteConverter.from2DByteArray(data);
	}
	
	public FireworkEffect getEffect()
	{
		if (!isNull)
		{
			FireworkEffect.Builder builder = FireworkEffect.builder();
			builder.flicker(flicker);
			builder.trail(trail);
			builder.with(FireworkEffect.Type.values()[type]);
			for (int index = 0; index < colors.length; index++)
			{
				builder.withColor(Color.fromRGB(colors[index]));
			}
			for (int index = 0; index < fadeColors.length; index++)
			{
				builder.withFade(Color.fromRGB(fadeColors[index]));
			}
			return builder.build();
		}
		else
		{
			return null;
		}
	}
}