package atlas;

import atlas.Charracters.Charracter;
import atlas.Charracters.Drone;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;

import java.util.ArrayList;
import java.util.List;

public class TrisExtension extends SFSExtension
{
	Player[] players;
	private TrisGameBoard gameBoard;
	private volatile boolean gameStarted;
	private LastGameEndResponse lastGameEndResponse;


	
	private final String version = "1.0.5";
	
	@Override
	public void init()
	{
		trace("Tris game Extension for SFS2X started, rel. " + version);
		gameBoard = new TrisGameBoard();
		players = new Player[getGameRoom().getMaxUsers()];

	    addRequestHandler("move", MoveHandler.class);
	    addRequestHandler("restart", RestartHandler.class);
	    addRequestHandler("ready", ReadyHandler.class);
	    
	    addEventHandler(SFSEventType.USER_DISCONNECT, OnUserGoneHandler.class);
	    addEventHandler(SFSEventType.USER_LEAVE_ROOM, OnUserGoneHandler.class);
	    addEventHandler(SFSEventType.SPECTATOR_TO_PLAYER, OnSpectatorToPlayerHandler.class);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
		trace("Tris game destroyed!");
	}
		
	TrisGameBoard getGameBoard()
    {
	    return gameBoard;
    }

	boolean isGameStarted()
	{
		return gameStarted;
	}

	// Start the game if the conditions are met.
	void tryStartGame(Room room)
	{
		if (gameStarted)
			throw new IllegalStateException("Game is already started!");

		if (room.getSize().getUserCount() == room.getMaxUsers())
		{
			lastGameEndResponse = null;
			gameStarted = true;
			gameBoard.reset();

			// Send START event to client
			ISFSObject resObj = new SFSObject();
			resObj.putInt("playersNum", room.getMaxUsers());
			for (Player player : players)
			{
				String userId = Integer.toString(player.user.getPlayerId() - 1);
				ISFSObject userData = player.getUserStartData();
				resObj.putSFSObject(userId, userData);
			}

			send("start", resObj, getParentRoom().getUserList());
		}
	}

	void createPlayer(User user, String characterName)
	{
		Charracter charracter;
		if (characterName.equals("Drone"))
			charracter = new Drone();
		else
			charracter = new Drone();
		trace(ExtensionLogLevel.WARN, "get userId " + (user.getPlayerId() - 1) + " out of " + players.length + " players");
		players[user.getPlayerId() - 1] = new Player(user, charracter);
	}

	Player getPlayer(int playerId)
	{
		return players[playerId];
	}

	void stopGame()
	{
		stopGame(false);
	}
	
	void stopGame(boolean resetTurn)
	{
		gameStarted = false;
		//moveCount = 0;
		//whoseTurn = null;
	}
	
	Room getGameRoom()
	{
		return this.getParentRoom();
	}
	
	LastGameEndResponse getLastGameEndResponse()
    {
	    return lastGameEndResponse;
    }
	
	void setLastGameEndResponse(LastGameEndResponse lastGameEndResponse)
    {
	    this.lastGameEndResponse = lastGameEndResponse;
    }
}
