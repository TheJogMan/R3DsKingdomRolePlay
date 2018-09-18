package jogLib;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SkinHandler
{
	static List<UUID> uuids;
	static List<SkinEntry> entries;
	static File file = new File(Main.file.getPath() + "/playerSkins");
	
	static void init()
	{
		if (!(file.exists() && file.isDirectory()))
		{
			file.mkdir();
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new EntryManager(), 1200, 1200);
		Bukkit.getPluginManager().registerEvents(new EventListener(), Main.plugin);
		
		uuids = new ArrayList<UUID>();
		entries = new ArrayList<SkinEntry>();
	}
	
	public static SkinEntry getFromName(String name)
	{
		return getFromUUID(OfflineNameTracker.getID(name));
	}
	
	public static SkinEntry getFromUUID(UUID id)
	{
		if (id != null)
		{
			if (uuids.contains(id))
			{
				return entries.get(uuids.indexOf(id));
			}
			else
			{
				return new SkinEntry(id);
			}
		}
		return null;
	}
	
	static boolean handleCommand(CommandSender sender, String command)
	{
		if (command.length() > 6)
		{
			if (command.substring(0, 5).compareTo("/give") == 0)
			{
				String item;
				int start = 6;
				int end = 7;
				while (command.charAt(end) != ' ')
				{
					end++;
				}
				start = end + 1;
				end = start + 1;
				while (command.charAt(end) != ' ')
				{
					end++;
					if (end == command.length())
					{
						break;
					}
				}
				item = command.substring(start, end);
				if (item.length() > 23)
				{
					if (item.substring(0, 22).compareTo("minecraft:player_head{") == 0 && item.charAt(item.length() - 1) == '}')
					{
						sender.sendMessage("Please use the §3/GetHead§r command for retrieving a player head!");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static class EntryManager implements Runnable
	{
		@Override
		public void run()
		{
			List<SkinEntry> outdatedEntries = new ArrayList<SkinEntry>();
			for (Iterator<SkinEntry> iterator = entries.iterator(); iterator.hasNext();)
			{
				SkinEntry entry = iterator.next();
				if (entry.isOutdated())
				{
					outdatedEntries.add(entry);
				}
			}
			for (Iterator<SkinEntry> iterator = outdatedEntries.iterator(); iterator.hasNext();)
			{
				SkinEntry entry = iterator.next();
				entries.remove(entry);
				uuids.remove(entry.getID());
			}
		}
	}
	
	static class EventListener implements Listener
	{
		@EventHandler
		public void onServerCommand(ServerCommandEvent event)
		{
			event.setCancelled(handleCommand(event.getSender(), event.getCommand()));
		}
		
		@EventHandler
		public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
		{
			event.setCancelled(handleCommand((CommandSender)event.getPlayer(), event.getMessage()));
		}
	}
	
	public static class SkinEntry
	{
		String value;
		String signature;
		String name;
		UUID id;
		long fetchTime;
		File entryFile;
		
		public SkinEntry(UUID id)
		{
			entryFile = new File(file.getPath() + "/" + id.toString());
			this.id = id;
			load();
			uuids.add(id);
			entries.add(this);
		}
		
		void update()
		{
			fetchTime = System.currentTimeMillis();
			try
			{
				String stringID = id.toString();
				String idUrl = "";
				for (int index = 0; index < stringID.length(); index++)
				{
					char ch = stringID.charAt(index);
					if (ch != '-')
					{
						idUrl += ch;
					}
				}
				URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + idUrl + "?unsigned=false");
				InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
	            JsonObject profile = new JsonParser().parse(reader_1).getAsJsonObject();
	            name = profile.get("name").getAsString();
	            JsonObject textureProperty = profile.get("properties").getAsJsonArray().get(0).getAsJsonObject();
				value = textureProperty.get("value").getAsString();
				signature = textureProperty.get("signature").getAsString();
				
				if (OfflineNameTracker.uuids.contains(id))
				{
					OfflineNameTracker.names.set(OfflineNameTracker.uuids.indexOf(id), name);
				}
				else
				{
					OfflineNameTracker.uuids.add(id);
					OfflineNameTracker.names.add(name);
				}
				OfflineNameTracker.save();
			}
			catch (Exception e)
			{
				System.out.println("ERROR: Could not update cached skin for " + id.toString());
				e.printStackTrace();
				value = "";
				signature = "";
			}
			save();
		}
		
		void load()
		{
			if (FileIO.canReadBytes(entryFile))
			{
				byte[][] data = ByteConverter.to2DByteArray(FileIO.readBytes(entryFile));
				signature = ByteConverter.toString(data[0]);
				value = ByteConverter.toString(data[1]);
				name = ByteConverter.toString(data[2]);
				fetchTime = ByteConverter.toLong(data[3]);
				
				if (isOutdated())
				{
					update();
				}
			}
			else
			{
				update();
			}
		}
		
		void save()
		{
			byte[][] data = new byte[4][];
			data[0] = ByteConverter.fromString(signature);
			data[1] = ByteConverter.fromString(value);
			data[2] = ByteConverter.fromString(name);
			data[3] = ByteConverter.fromLong(fetchTime);
			byte[] finalData = ByteConverter.from2DByteArray(data);
			FileIO.writeBytes(entryFile, finalData);
		}
		
		public String[] getData()
		{
			return new String[] {signature, value};
		}
		
		public UUID getID()
		{
			return id;
		}
		
		public String getName()
		{
			return name;
		}
		
		public long getFetchTime()
		{
			return fetchTime;
		}
		
		public boolean isOutdated()
		{
			return ((System.currentTimeMillis() - fetchTime) / 600000 >= 1);
		}
		
		public String getSignature()
		{
			return signature;
		}
		
		public String getValue()
		{
			return value;
		}
	}
}