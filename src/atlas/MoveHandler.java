package atlas;

import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSRuntimeException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

import java.sql.Struct;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class MoveHandler extends BaseClientRequestHandler
{
    private static final String CMD_MOVE = "move";
    private TrisExtension gameExt;


    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        gameExt = (TrisExtension) getParentExtension();
        int playerId = user.getPlayerId() - 1;

        gameExt.trace(ExtensionLogLevel.WARN, "Handling data from player %s", playerId);
        Player player = gameExt.getPlayer(playerId);

        //ToDo: проверка валидности
        if (!params.containsKey("moveX") || !params.containsKey("moveZ"))
            throw new SFSRuntimeException("Invalid request, one mandatory param is missing. Required 'moveX' and 'moveZ'");

        player.updateUserData(params);

        if (gameExt.isGameStarted())
        {
            //ToDo: обработка
            if (checkAllPlayersDataRecived())
            {
                send(CMD_MOVE, getAllUsersData(), gameExt.getGameRoom().getUserList());
                gameExt.trace("Send data users.");
                clearAllUsersData();
            }
        }
        else
            gameExt.trace(ExtensionLogLevel.WARN, "Game is on pause!");

    }


    private ISFSObject getAllUsersData()
    {
        ISFSObject data = new SFSObject();

        for (Player player : gameExt.players)
        {
            data.putSFSObject(Integer.toString(player.user.getPlayerId() - 1), player.getAllUserData());
        }
        return data;
    }

    private void clearAllUsersData()
    {
        for (Player player : gameExt.players)
        {
           player.clearAllUserData();
        }
    }

    private boolean checkAllPlayersDataRecived()
    {
        int num = 0;

        for (Player player : gameExt.players)
        {
            if (player.checkDataRecived())
                num++;
        }
        return num == gameExt.getGameRoom().getSize().getUserCount();
    }


}
