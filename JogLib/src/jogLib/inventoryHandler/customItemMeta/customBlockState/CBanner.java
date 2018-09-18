package jogLib.inventoryHandler.customItemMeta.customBlockState;

import java.util.Iterator;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;

import jogLib.inventoryHandler.CBannerPattern;

public class CBanner extends CBlockState
{
	CBannerPattern[] patterns;
	byte color;
	
	@Override
	CBlockState getNew()
	{
		return new CBanner();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Banner);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[patterns.length * 3 + 1];
		data[0] = color;
		int dataIndex = 1;
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
	
	@Override
	void load(byte[] data)
	{
		color = data[0];
		patterns = new CBannerPattern[(data.length - 1) / 3];
		int dataIndex = 1;
		for (int index = 0; index < patterns.length; index++)
		{
			patterns[index] = new CBannerPattern(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2]});
			dataIndex += 3;
		}
	}
	
	@Override
	void create(BlockState blockState)
	{
		Banner banner = (Banner)blockState;
		List<Pattern> patterns = banner.getPatterns();
		this.patterns = new CBannerPattern[patterns.size()];
		Iterator<Pattern> iterator = patterns.iterator();
		for (int index = 0; index < this.patterns.length; index++)
		{
			this.patterns[index] = new CBannerPattern(iterator.next());
		}
		DyeColor[] colors = DyeColor.values();
		for (byte index = 0; index < colors.length; index++)
		{
			if (colors[index].equals(banner.getBaseColor()))
			{
				color = index;
				break;
			}
		}
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Banner banner = (Banner)blockState;
		for (int index = 0; index < patterns.length; index++)
		{
			banner.addPattern(patterns[index].getPattern());
		}
		banner.setBaseColor(DyeColor.values()[color]);
		return banner;
	}
}