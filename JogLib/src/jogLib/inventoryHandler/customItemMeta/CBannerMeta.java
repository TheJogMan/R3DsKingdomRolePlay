package jogLib.inventoryHandler.customItemMeta;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.inventoryHandler.CBannerPattern;

public class CBannerMeta extends CItemMeta
{
	CBannerPattern[] patterns;
	
	@Override
	CItemMeta getNew()
	{
		return new CBannerMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof BannerMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		BannerMeta bannerMeta = (BannerMeta)meta;
		List<Pattern> patterns = bannerMeta.getPatterns();
		this.patterns = new CBannerPattern[patterns.size()];
		Iterator<Pattern> iterator = patterns.iterator();
		for (int index = 0; index < this.patterns.length; index++)
		{
			this.patterns[index] = new CBannerPattern(iterator.next());
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		BannerMeta bannerMeta = (BannerMeta)meta;
		for (int index = 0; index < patterns.length; index++)
		{
			bannerMeta.addPattern(patterns[index].getPattern());
		}
		return bannerMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		patterns = new CBannerPattern[data.length / 3];
		int dataIndex = 0;
		for (int index = 0; index < patterns.length; index++)
		{
			patterns[index] = new CBannerPattern(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2]});
			dataIndex += 3;
		}
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[patterns.length * 3];
		int dataIndex = 0;
		for (int index = 0; index < patterns.length; index++)
		{
			byte[] patternData = patterns[index].export();
			data[dataIndex] = patternData[0];
			data[dataIndex + 1] = patternData[1];
			data[dataIndex + 2] = patternData[2];
			dataIndex += 3;
		}
		return data;
	}
}