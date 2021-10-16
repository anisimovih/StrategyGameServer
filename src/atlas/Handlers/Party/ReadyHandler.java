package atlas.Handlers.Party;

import atlas.Player;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class ReadyHandler extends BaseClientRequestHandler
{
	private static final String CMD_READY = "ready";
	private static final String CMD_LOAD = "startLoad";
	private AtlasExtension gameExt;

	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		gameExt = (AtlasExtension) getParentExtension();
		int playerId = user.getPlayerId();
		boolean isReady = params.getBool("ready");

		changeStatus(playerId, isReady);

		if (isReady && user.getLastJoinedRoom().isFull() && isAllReady())
			startGameLoad();
	}

	private void changeStatus(int playerId, Boolean isReady) {
		gameExt.trace(ExtensionLogLevel.WARN, "Player id = " + playerId + " isReady = " + isReady);
		gameExt.playersDict.get(playerId).ready = isReady;
		SFSObject data = new SFSObject();
		data.putInt("id", playerId);
		data.putBool("ready", isReady);
		send(CMD_READY, data, gameExt.getGameRoom().getUserList());
	}

	private Boolean isAllReady() {
		for (Player player : gameExt.playersDict.values())
			if (!player.ready)
				return false;
		return true;
	}

	private void startGameLoad() {
		gameExt.trace(ExtensionLogLevel.WARN, "Game started!");
		// Send START event to client
		send(CMD_LOAD, new SFSObject(), gameExt.getGameRoom().getUserList());
		ISFSObject resObj = new SFSObject();
	}
}
