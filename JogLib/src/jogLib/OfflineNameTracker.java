package jogLib;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jogLib.SkinHandler.SkinEntry;

public class OfflineNameTracker
{
	static List<String> names;
	static List<UUID> uuids;
	static File file = new File(Main.file.getPath() + "/OfflineNames");

	static void init()
	{
		Bukkit.getPluginManager().registerEvents(new EventListener(), Main.plugin);
		names = new ArrayList<String>();
		uuids = new ArrayList<UUID>();
		load();
		for (Iterator<? extends Player> iterator = Bukkit.getOnlinePlayers().iterator(); iterator.hasNext();)
		{
			Player player = iterator.next();
			if (uuids.contains(player.getUniqueId()))
			{
				names.set(uuids.indexOf(player.getUniqueId()), player.getName());
			}
			else
			{
				uuids.add(player.getUniqueId());
				names.add(player.getName());
			}
		}
		save();
	}

	public static String getName(UUID id)
	{
		if (uuids.contains(id))
		{
			return names.get(uuids.indexOf(id));
		}
		else
		{
			SkinEntry skinEntry = SkinHandler.getFromUUID(id);
			if (skinEntry != null)
			{
				String name = skinEntry.getName();
				uuids.add(id);
				names.add(name);
				save();
				return name;
			}
			else
			{
				return null;
			}
		}
	}

	public static UUID getID(String name)
	{
		if (names.contains(name))
		{
			return uuids.get(names.indexOf(name));
		}
		else
		{
			try
			{
				URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
				InputStreamReader reader_1 = new InputStreamReader(url_0.openStream());
				JsonElement element = new JsonParser().parse(reader_1);
				if (!element.isJsonNull())
				{
					JsonObject profile = element.getAsJsonObject();
					String data = profile.get("id").getAsString();
					String idString = data.substring(0, 8) + "-" + data.substring(8, 12) + "-" + data.substring(12, 16)
							+ "-" + data.substring(16, 20) + "-" + data.substring(20, 32);
					UUID id = UUID.fromString(idString);
					names.add(name);
					uuids.add(id);
					save();
					return id;
				}
				else
				{
					return null;
				}
			}
			catch (IOException e)
			{
				System.out.println("ERROR: Could not get UUID for OfflinePlayer with name " + name);
				e.printStackTrace();
				return null;
			}
		}
	}

	static void load()
	{
		byte[] rawData = FileIO.readBytes(file);
		if (rawData.length > 1)
		{
			byte[][] data = ByteConverter.to2DByteArray(rawData);
			names = ListUtils.getList(ByteConverter.toStringArray(data[0]));
			uuids = ListUtils.getList(ByteConverter.toUUIDArray(data[1]));
		}
		else
		{
			names.clear();
			uuids.clear();
		}
	}

	static void save()
	{
		FileIO.writeBytes(file,
				ByteConverter
						.from2DByteArray(new byte[][] { ByteConverter.fromStringArray(ListUtils.getStringArray(names)),
								ByteConverter.fromUUIDArray(ListUtils.getUUIDArray(uuids)) }));
	}

	static class EventListener implements Listener
	{
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event)
		{
			if (uuids.contains(event.getPlayer().getUniqueId()))
			{
				names.set(uuids.indexOf(event.getPlayer().getUniqueId()), event.getPlayer().getName());
			}
			else
			{
				names.add(event.getPlayer().getName());
				uuids.add(event.getPlayer().getUniqueId());
			}
			save();
		}
	}
}