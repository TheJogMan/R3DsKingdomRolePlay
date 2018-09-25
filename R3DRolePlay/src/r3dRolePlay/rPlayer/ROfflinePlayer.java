package r3dRolePlay.rPlayer;

import java.io.File;

import org.bukkit.OfflinePlayer;

import r3dRolePlay.Main;
import r3dRolePlay.character.RPCharacter;

public class ROfflinePlayer
{
	//persistent values (saved to file
	String nameOfLastUsedCharacter;
	boolean inCharacter;
	
	//temporary values (not saved to file)
	RPCharacter[] characters;
	
	//immutable values (set when the player is created, and can never change)
	OfflinePlayer player;
	File file;
	
	public ROfflinePlayer(OfflinePlayer player)
	{
		load(player, false);
	}
	
	public ROfflinePlayer(OfflinePlayer player, boolean totalLoad)
	{
		load(player, totalLoad);
	}
	
	private void load(OfflinePlayer player, boolean totalLoad)
	{
		this.player = player;
		defaults();
		file = new File(Main.getPlayersFile().getPath() + "/" + player.getUniqueId().toString());
		PlayerLoader.load(this, totalLoad);
	}
	
	public void save()
	{
		PlayerLoader.save(this);
	}
	
	void defaults()
	{
		nameOfLastUsedCharacter = "";
		inCharacter = false;
		characters = new RPCharacter[0];
	}
	
	public void setCharacter(String name)
	{
		RPCharacter character = getCharacter(name);
		if (character != null)
		{
			nameOfLastUsedCharacter = character.getName();
			if (this instanceof RPlayer)
			{
				((RPlayer)this).applyCharacter(character);
				character.setPlayer((RPlayer)this);
			}
			save();
		}
	}
	
	public boolean inCharacter()
	{
		return inCharacter;
	}
	
	public RPCharacter getCharacter()
	{
		if (inCharacter)
		{
			return getCharacter(nameOfLastUsedCharacter);
		}
		return null;	
	}
	
	public RPCharacter getCharacter(String name)
	{
		if (characters.length > 0)
		{
			for (int index = 0; index < characters.length; index++)
			{
				if (characters[index].getName().compareTo(name) == 0)
				{
					return characters[index];
				}
			}
		}
		
		RPCharacter character = PlayerLoader.loadCharacter(this, name, false);
		if (character != null)
		{
			RPCharacter[] newCharacters = new RPCharacter[characters.length + 1];
			for (int index = 0; index < characters.length; index++)
			{
				newCharacters[index] = characters[index];
			}
			newCharacters[characters.length] = character;
			characters = newCharacters;
		}
		return character;
	}
	
	public OfflinePlayer getOfflinePlayer()
	{
		return player;
	}
	
	//File Getters
	public File getMainFile()		{return file;}
	public File getCharactersFile()	{return new File(file.getPath() + "/characters");}
	public File getDataFile()		{return new File(file.getPath() + "/data");}
}