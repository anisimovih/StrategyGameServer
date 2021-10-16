package atlas.Handlers.Game;

import atlas.Player;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class MoveHandler extends BaseClientRequestHandler
{
    private static final String CMD_MOVE = "move";
    private AtlasExtension gameExt;

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        gameExt = (AtlasExtension) getParentExtension();

        if (user.isPlayer()) {
            int playerId = user.getPlayerId();

            gameExt.trace(ExtensionLogLevel.WARN, "Handling data from player %s", playerId);
            Player player = gameExt.playersDict.get(playerId);

            //ToDo: проверка валидности

            player.updateUserData(params);

            //ToDo: обработка
            if (checkAllPlayersDataRecived()) {
                send(CMD_MOVE, getAllUsersData(), gameExt.getGameRoom().getUserList());
                gameExt.trace("Send data users.");
                clearAllUsersData();
            }
        }

    }

    private ISFSObject getAllUsersData()
    {
        ISFSObject data = new SFSObject();

        for (Player player : gameExt.playersDict.values())
        {
            data.putSFSObject(Integer.toString(player.user.getPlayerId() - 1), player.getAllUserData());
        }
        return data;
    }

    private void clearAllUsersData()
    {
        for (Player player : gameExt.playersDict.values())
        {
           player.clearAllUserData();
        }
    }

    private boolean checkAllPlayersDataRecived()
    {
        int num = 0;

        for (Player player : gameExt.playersDict.values())
        {
            if (player.checkDataReceived())
                num++;
        }
        return num == gameExt.getGameRoom().getSize().getUserCount();
    }


}
