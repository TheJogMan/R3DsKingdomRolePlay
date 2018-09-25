package r3dRolePlay.rPlayer;

import org.bukkit.entity.Player;

import r3dRolePlay.character.RPCharacter;

public class RPlayer extends ROfflinePlayer
{
	//temporary values (not saved to file)
	boolean disconnected;
	RPCharacter character;
	
	//persistent values (set when the player is created, and can never change)
	Player player;
	
	public RPlayer(Player player)
	{
		super(player, true);
		this.player = player;
		disconnected = false;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void disconnect()
	{
		if (!disconnected)
		{
			
			
			player.kickPlayer("disconnected.");
			save();
			disconnected = true;
		}
	}
	
	public void applyCharacter()
	{
		applyCharacter(getCharacter());
	}
	
	public void softApplyCharacter()
	{
		softApplyCharacter(getCharacter());
	}
	
	void applyCharacter(RPCharacter character)
	{
		if (!inCharacter()) return;
		
		softApplyCharacter(character);
		player.teleport(character.getLocation());
	}
	
	void softApplyCharacter(RPCharacter character)
	{
		if (!inCharacter()) return;
		
		this.character = character;
	}
}