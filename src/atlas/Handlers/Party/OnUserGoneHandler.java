package atlas.Handlers.Party;

import atlas.AtlasExtension;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class OnUserGoneHandler extends BaseServerEventHandler
{
	private AtlasExtension gameExt;

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException
	{
		gameExt = (AtlasExtension) getParentExtension();
		Room gameRoom = gameExt.getGameRoom();

		User user = (User) event.getParameter(SFSEventParam.USER);

		if (gameRoom.isGame()) {
			deletePlayer(user);
		}
	}

	void deletePlayer(User user)
	{
		int playerId = user.getPlayerId();

		trace(ExtensionLogLevel.WARN, "Remove player with userId " + playerId);
		gameExt.playersDict.remove(playerId);
	}
}
