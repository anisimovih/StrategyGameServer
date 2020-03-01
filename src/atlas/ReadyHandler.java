package atlas;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class ReadyHandler extends BaseClientRequestHandler
{
	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		TrisExtension gameExt = (TrisExtension) getParentExtension();

		if (user.isPlayer())
		{
			gameExt.trace(ExtensionLogLevel.WARN, "Player with Id " + user.getPlayerId() + " connected");
			String charracter = params.getText("Hero");
			gameExt.createPlayer(user, charracter);
			gameExt.tryStartGame(gameExt.getGameRoom());
		}
		
		else
		{
			//gameExt.updateSpectator(user);
			
			LastGameEndResponse endResponse = gameExt.getLastGameEndResponse();
			
			// If game has ended send the outcome
			if (endResponse != null)
				send(endResponse.getCmd(), endResponse.getParams(), user);
		}
	}
}
