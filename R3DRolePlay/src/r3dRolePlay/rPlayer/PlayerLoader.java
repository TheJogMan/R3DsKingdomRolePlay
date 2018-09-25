package r3dRolePlay.rPlayer;

import java.io.File;

import jogLib.ByteConverter;
import jogLib.FileIO;
import r3dRolePlay.character.RPCharacter;

public class PlayerLoader
{
	static byte formatVersion = 0;
	
	public static void save(ROfflinePlayer player)
	{
		FileIO.ensureDirectories(new File[] {player.getMainFile(), player.getCharactersFile()});
		for (int index = 0; index < player.characters.length; index++)
		{
			player.characters[index].save();
		}
		
		byte[][] data = new byte[3][];
		data[0] = new byte[] {formatVersion};
		data[1] = ByteConverter.fromString(player.nameOfLastUsedCharacter);
		data[2] = new byte[] {ByteConverter.fromBoolean(player.inCharacter)};
		FileIO.writeBytes(player.getDataFile(), ByteConverter.from2DByteArray(data));
	}
	
	public static void load(ROfflinePlayer player, boolean totalLoad)
	{
		FileIO.ensureDirectories(new File[] {player.getMainFile(), player.getCharactersFile()});
		if (totalLoad)
		{
			File[] characterFiles = player.getCharactersFile().listFiles();
			player.characters = new RPCharacter[characterFiles.length];
			for (int index = 0; index < player.characters.length; index++)
			{
				player.characters[index] = new RPCharacter(characterFiles[index], true);
			}
		}
		
		if (FileIO.canReadBytes(player.getDataFile()))
		{
			byte[][] data = ByteConverter.to2DByteArray(FileIO.readBytes(player.getDataFile()));
			byte format = data[0][0];
			
			if (format == 0)
			{
				player.nameOfLastUsedCharacter = ByteConverter.toString(data[1]);
				player.inCharacter = ByteConverter.toBoolean(data[2][0]);
			}
		}
	}
	
	public static RPCharacter loadCharacter(ROfflinePlayer player, String characterName, boolean totalLoad)
	{
		File file = new File(player.getCharactersFile().getPath() + "/" + characterName);
		if (file.exists())
		{
			return new RPCharacter(file);
		}
		return null;
	}
}