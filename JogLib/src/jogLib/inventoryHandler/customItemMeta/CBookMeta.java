package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import jogLib.ByteConverter;

public class CBookMeta extends CItemMeta
{
	String author;
	String title;
	String[] pages;
	byte generation;
	
	@Override
	CItemMeta getNew()
	{
		return new CBookMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof BookMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		BookMeta bookMeta = (BookMeta)meta;
		if (bookMeta.hasAuthor())
		{
			author = bookMeta.getAuthor();
			title = bookMeta.getTitle();
			BookMeta.Generation[] generations = BookMeta.Generation.values();
			for (byte index = 0; index < generations.length; index++)
			{
				if (generations[index].equals(bookMeta.getGeneration()))
				{
					generation = index;
				}
			}
		}
		else
		{
			author = "";
			title = "";
			generation = -1;
		}
		pages = new String[bookMeta.getPageCount()];
		for (int index = 0; index < pages.length; index++)
		{
			pages[index] = bookMeta.getPage(index + 1);
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		BookMeta bookMeta = (BookMeta)meta;
		if (generation != -1)
		{
			bookMeta.setAuthor(author);
			bookMeta.setTitle(title);
			bookMeta.setGeneration(BookMeta.Generation.values()[generation]);
		}
		for (int index = 0; index < pages.length; index++)
		{
			bookMeta.addPage(pages[index]);
		}
		return bookMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		generation = data[0];
		String[] pageData = new String[ByteConverter.toInteger(new byte[] {data[1], data[2], data[3], data[4]})];
		int dataIndex = 5;
		for (int index = 0; index < pageData.length; index++)
		{
			byte[] pageSubData = new byte[ByteConverter.toInteger(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3]})];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < pageSubData.length; subIndex++)
			{
				pageSubData[subIndex] = data[dataIndex + subIndex];
			}
			pageData[index] = ByteConverter.toString(pageSubData);
			dataIndex += pageSubData.length;
		}
		pages = new String[pageData.length - 2];
		author = pageData[0];
		title = pageData[1];
		for (int index = 0; index < pages.length; index++)
		{
			pages[index] = pageData[index + 2];
		}
	}
	
	@Override
	byte[] export()
	{
		int pageDataLength = 0;
		byte[][] pageData = new byte[pages.length + 2][];
		pageData[0] = ByteConverter.fromString(author);
		pageData[1] = ByteConverter.fromString(title);
		pageDataLength += pageData[0].length + pageData[1].length;
		for (int index = 0; index < pages.length; index++)
		{
			pageData[index + 2] = ByteConverter.fromString(pages[index]);
			pageDataLength += pageData[index + 2].length;
		}
		byte[] data = new byte[5 + pageDataLength + 4 * pageData.length];
		data[0] = generation;
		byte[] pageCountData = ByteConverter.fromInteger(pageData.length);
		data[1] = pageCountData[0];
		data[2] = pageCountData[1];
		data[3] = pageCountData[2];
		data[4] = pageCountData[3];
		int dataIndex = 5;
		for (int index = 0; index < pageData.length; index++)
		{
			byte[] pageLengthData = ByteConverter.fromInteger(pageData[index].length);
			data[dataIndex] = pageLengthData[0];
			data[dataIndex + 1] = pageLengthData[1];
			data[dataIndex + 2] = pageLengthData[2];
			data[dataIndex + 3] = pageLengthData[3];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < pageData[index].length; subIndex++)
			{
				data[dataIndex + subIndex] = pageData[index][subIndex];
			}
			dataIndex += pageData[index].length;
		}
		return data;
	}
}