package atlas.Handlers.Party;

import atlas.Charracters.Charracter;
import atlas.Charracters.Filler;
import atlas.Charracters.Soldier;
import atlas.Charracters.Warrior;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class CharracterChangeHandler extends BaseClientRequestHandler
{
	private static final String CMD_CHANGE = "characterChange";
	private AtlasExtension gameExt;

	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		gameExt = (AtlasExtension) getParentExtension();
		String characterName = params.getText("characterName");
		int playerId = user.getPlayerId();

		UpdatePlayer(user, characterName);

		SFSObject data = new SFSObject();
		data.putInt("id", playerId);
		data.putUtfString("characterName", characterName);
		send(CMD_CHANGE, data, gameExt.getGameRoom().getUserList());
	}


	void UpdatePlayer(User user, String characterName)
	{
		int playerId = user.getPlayerId();

		trace(ExtensionLogLevel.WARN, "Create player with userId " + playerId);
		gameExt.playersDict.get(user.getPlayerId()).charracter = createCharacter(characterName);
	}

	// ToDo: add another characters
	Charracter createCharacter(String characterName)
	{
		if (characterName.equals("warrior"))
			return new Warrior();
		else if (characterName.equals("soldier"))
			return new Soldier();
		else
			return new Filler();
	}
}
