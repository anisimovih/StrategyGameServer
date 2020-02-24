package atlas;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class OnSpectatorToPlayerHandler extends BaseServerEventHandler
{
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException
	{
		TrisExtension gameExt = (TrisExtension) getParentExtension();
		System.out.println("Player was switched: " +  event.getParameter(SFSEventParam.USER));
		System.out.println("Room: " + gameExt.getGameRoom() + " => " + gameExt.getGameRoom().getSize());

		gameExt.tryStartGame(gameExt.getGameRoom());
	}
}
