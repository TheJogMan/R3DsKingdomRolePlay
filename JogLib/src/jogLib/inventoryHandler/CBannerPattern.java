package jogLib.inventoryHandler;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

import jogLib.ByteConverter;

public class CBannerPattern
{
	byte type;
	byte color;
	boolean isNull;
	
	public CBannerPattern(Pattern pattern)
	{
		if (pattern != null)
		{
			DyeColor[] colors = DyeColor.values();
			for (byte index = 0; index < colors.length; index++)
			{
				if (colors[index].equals(pattern.getColor()))
				{
					color = index;
					break;
				}
			}
			PatternType[] patterns = PatternType.values();
			for (byte index = 0; index < patterns.length; index++)
			{
				if (patterns[index].equals(pattern.getPattern()))
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
			type = 0;
			color = 0;
		}
	}
	
	public CBannerPattern(byte[] data)
	{
		type = data[0];
		color = data[1];
		isNull = ByteConverter.toBoolean(data[2]);
	}
	
	public Pattern getPattern()
	{
		if (!isNull)
		{
			return new Pattern(DyeColor.values()[color], PatternType.values()[type]);
		}
		else
		{
			return null;
		}
	}
	
	public byte[] export()
	{
		return new byte[] {type, color, ByteConverter.fromBoolean(isNull)};
	}
}