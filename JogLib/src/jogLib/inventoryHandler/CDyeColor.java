package jogLib.inventoryHandler;

import org.bukkit.DyeColor;

public class CDyeColor
{
	static CColor[] colors = CColor.values();
	
	public static byte getID(DyeColor color)
	{
		for (int index = 0; index < colors.length; index++)
		{
			if (colors[index].getColor().equals(color))
			{
				return colors[index].getID();
			}
		}
		return 0;
	}
	
	public static DyeColor getColor(byte id)
	{
		for (int index = 0; index < colors.length; index++)
		{
			if (colors[index].getID() == id)
			{
				return colors[index].getColor();
			}
		}
		return DyeColor.BLACK;
	}
	
	static enum CColor
	{
		CBLACK(DyeColor.BLACK, 0),
		CBLUE(DyeColor.BLUE, 1),
		CBROWN(DyeColor.BROWN, 2),
		CCYAN(DyeColor.CYAN, 3),
		CGRAY(DyeColor.GRAY, 4),
		CGREEN(DyeColor.GREEN, 5),
		CLIGHT_BLUE(DyeColor.LIGHT_BLUE, 6),
		CLIGHT_GRAY(DyeColor.LIGHT_GRAY, 7),
		CLIME(DyeColor.LIME, 8),
		CMAGENTA(DyeColor.MAGENTA, 9),
		CORANGE(DyeColor.ORANGE, 10),
		CPINK(DyeColor.PINK, 11),
		CPURPLE(DyeColor.PURPLE, 12),
		CRED(DyeColor.RED, 13),
		CWHITE(DyeColor.WHITE, 14),
		CYELLOW(DyeColor.YELLOW, 15);
		
		DyeColor color;
		byte id;
		
		CColor(DyeColor color, int id)
		{
			this.color = color;
			this.id = (byte)id;
		}
		
		DyeColor getColor()
		{
			return color;
		}
		
		byte getID()
		{
			return id;
		}
	}
}
