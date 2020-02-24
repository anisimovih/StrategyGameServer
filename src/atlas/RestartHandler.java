package atlas;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class RestartHandler extends BaseClientRequestHandler
{
	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		TrisExtension gameExt = (TrisExtension) getParentExtension();

		gameExt.tryStartGame(gameExt.getGameRoom());
	}
}
