package jogLib.inventoryHandler.customItemMeta;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import jogLib.ByteConverter;

public class CMapMeta extends CItemMeta
{
	int color;
	String locationName;
	boolean scaling;
	int id;
	
	@Override
	CItemMeta getNew()
	{
		return new CMapMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof MapMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		MapMeta mapMeta = (MapMeta)meta;
		if (mapMeta.hasColor())
		{
			color = mapMeta.getColor().asRGB();
		}
		else
		{
			color = -1;
		}
		locationName = mapMeta.getLocationName();
		if (locationName == null)
		{
			locationName = "";
		}
		scaling = mapMeta.isScaling();
		id = mapMeta.getMapId();
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		MapMeta mapMeta = (MapMeta)meta;
		if (color != -1)
		{
			mapMeta.setColor(Color.fromRGB(color));
		}
		if (locationName.length() > 0)
		{
			mapMeta.setLocationName(locationName);
		}
		mapMeta.setScaling(scaling);
		mapMeta.setMapId(id);
		return mapMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		scaling = ByteConverter.toBoolean(data[0]);
		color = ByteConverter.toInteger(new byte[] {data[1], data[2], data[3], data[4]});
		id = ByteConverter.toInteger(new byte[] {data[5], data[6], data[7], data[8]});
		byte[] nameData = new byte[data.length - 9];
		for (int index = 0; index < nameData.length; index++)
		{
			nameData[index] = data[9 + index];
		}
		locationName = ByteConverter.toString(nameData);
	}
	
	@Override
	byte[] export()
	{
		byte[] data = new byte[9 + locationName.length() * 2];
		byte[] colorData = ByteConverter.fromInteger(color);
		byte[] idData = ByteConverter.fromInteger(id);
		byte[] nameData = ByteConverter.fromString(locationName);
		data[0] = ByteConverter.fromBoolean(scaling);
		data[1] = colorData[0];
		data[2] = colorData[1];
		data[3] = colorData[2];
		data[4] = colorData[3];
		data[5] = idData[0];
		data[6] = idData[1];
		data[7] = idData[2];
		data[8] = idData[3];
		for (int index = 0; index < nameData.length; index++)
		{
			data[9 + index] = nameData[index];
		}
		return data;
	}
}