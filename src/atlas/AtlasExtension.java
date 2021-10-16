package atlas;

import atlas.Handlers.Game.*;
import atlas.Handlers.Party.*;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.extensions.SFSExtension;

import java.util.HashMap;
import java.util.Map;

public class AtlasExtension extends SFSExtension
{
	public Map<Integer, Player> playersDict = new HashMap<>();

	private final String version = "0.1.0";
	
	@Override
	public void init()
	{
		trace("Tris game Extension for SFS2X started, rel. " + version);

		// Lobby Handlers
		addRequestHandler("getRoomData", OnRequestRoomDataHandler.class);
		addRequestHandler("characterChange", CharracterChangeHandler.class);
		addRequestHandler("ready", ReadyHandler.class);
		addRequestHandler("load", UserLoadHandler.class);
		// Game Handlers
		addRequestHandler("getCharactersData", CharactersDataHandler.class);
	    addRequestHandler("move", MoveHandler.class);
		addRequestHandler("end", EndTurnHandler.class);

		addEventHandler(SFSEventType.USER_JOIN_ROOM, OnUserJoinHandler.class);
	    addEventHandler(SFSEventType.USER_DISCONNECT, OnUserGoneHandler.class);
	    addEventHandler(SFSEventType.USER_LEAVE_ROOM, OnUserGoneHandler.class);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
		trace("Game destroyed!");
	}
	
	public Room getGameRoom()
	{
		return this.getParentRoom();
	}
}
