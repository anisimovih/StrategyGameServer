package atlas;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class EndTurnHandler extends BaseClientRequestHandler
{
    private static final String CMD_NEXT = "next";
    private TrisExtension gameExt;

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        gameExt = (TrisExtension) getParentExtension();

        if (user.isPlayer())
        {
            int playerId = user.getPlayerId();
            Player player = gameExt.getPlayer(playerId);

            gameExt.trace(ExtensionLogLevel.WARN, "Player with Id " + user.getPlayerId() + " end turn");
            player.endTurn();
            if (checkAllPlayersDataRecived())
            {
                send(CMD_NEXT, new SFSObject(), gameExt.getGameRoom().getUserList());
                gameExt.trace("Send new turn to users.");
                clearAllUsersTurnEnd();
            }
        }
    }

    private boolean checkAllPlayersDataRecived()
    {
        int num = 0;

        for (Player player : gameExt.players)
        {
            if (player.checkTurnEnd())
                num++;
        }
        return num == gameExt.getGameRoom().getSize().getUserCount();
    }

    private void clearAllUsersTurnEnd()
    {
        for (Player player : gameExt.players)
        {
            player.newTurn();
        }
    }
}
