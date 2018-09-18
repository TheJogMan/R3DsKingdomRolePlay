package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CDyeColor;

public class CTropicalFishBucketMeta extends CItemMeta
{
	byte color;
	byte patternColor;
	byte pattern;
	boolean hasVariant;
	
	@Override
	CItemMeta getNew()
	{
		return new CTropicalFishBucketMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof TropicalFishBucketMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		TropicalFishBucketMeta tropicalFishBucketMeta = (TropicalFishBucketMeta)meta;
		if (tropicalFishBucketMeta.hasVariant())
		{
			color = CDyeColor.getID(tropicalFishBucketMeta.getBodyColor());
			patternColor = CDyeColor.getID(tropicalFishBucketMeta.getPatternColor());
			pattern = CFishPattern.getID(tropicalFishBucketMeta.getPattern());
			hasVariant = true;
		}
		else
		{
			hasVariant = false;
			color = 0;
			patternColor = 0;
			pattern = 0;
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		TropicalFishBucketMeta tropicalFishBucketMeta = (TropicalFishBucketMeta)meta;
		if (hasVariant)
		{
			tropicalFishBucketMeta.setBodyColor(CDyeColor.getColor(color));
			tropicalFishBucketMeta.setPatternColor(CDyeColor.getColor(patternColor));
			tropicalFishBucketMeta.setPattern(CFishPattern.getPattern(pattern));
		}
		return tropicalFishBucketMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		color = data[0];
		patternColor = data[1];
		pattern = data[2];
		hasVariant = ByteConverter.toBoolean(data[3]);
	}
	
	@Override
	byte[] export()
	{
		return new byte[] {color, patternColor, pattern, ByteConverter.fromBoolean(hasVariant)};
	}
	
	static class CFishPattern
	{
		static Pattern[] patterns = Pattern.values();
		
		public static byte getID(TropicalFish.Pattern pattern)
		{
			for (int index = 0; index < patterns.length; index++)
			{
				if (pattern.equals(patterns[index].getPattern()))
				{
					return patterns[index].getID();
				}
			}
			return 0;
		}
		
		public static TropicalFish.Pattern getPattern(byte id)
		{
			for (int index = 0; index < patterns.length; index++)
			{
				if (id == patterns[index].getID())
				{
					return patterns[index].getPattern();
				}
			}
			return TropicalFish.Pattern.BETTY;
		}
		
		static enum Pattern
		{
			CBETTY(TropicalFish.Pattern.BETTY, 0),
			CBLOCKFISH(TropicalFish.Pattern.BLOCKFISH, 1),
			CBRINELY(TropicalFish.Pattern.BRINELY, 2),
			CCLAYFISH(TropicalFish.Pattern.CLAYFISH, 3),
			CDASHER(TropicalFish.Pattern.DASHER, 4),
			CFLOPPER(TropicalFish.Pattern.FLOPPER, 5),
			CGLITTER(TropicalFish.Pattern.GLITTER, 6),
			CKOB(TropicalFish.Pattern.KOB, 7),
			CSNOOPER(TropicalFish.Pattern.SNOOPER, 8),
			CSPOTTY(TropicalFish.Pattern.SPOTTY, 9),
			CSTRIPEY(TropicalFish.Pattern.STRIPEY, 10),
			CSUNSTREAK(TropicalFish.Pattern.SUNSTREAK, 11);
			
			TropicalFish.Pattern pattern;
			byte id;
			
			Pattern(TropicalFish.Pattern pattern, int id)
			{
				this.pattern = pattern;
				this.id = (byte)id;
			}
			
			TropicalFish.Pattern getPattern()
			{
				return pattern;
			}
			
			byte getID()
			{
				return id;
			}
		}
	}
}