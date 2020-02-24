package atlas;

import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSRuntimeException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class MoveHandler extends BaseClientRequestHandler
{
    //private static final String CMD_WIN = "win";
    //private static final String CMD_TIE = "tie";
    private static final String CMD_MOVE = "move";

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        // Check params
        if (!params.containsKey("moveX") || !params.containsKey("moveY"))
            throw new SFSRuntimeException("Invalid request, one mandatory param is missing. Required 'x' and 'y'");

        TrisExtension gameExt = (TrisExtension) getParentExtension();
        //tris.TrisGameBoard board = gameExt.getGameBoard();

        ISFSArray moveX = params.getSFSArray("moveX");
        ISFSArray moveY = params.getSFSArray("moveY");

        gameExt.trace(String.format("Handling move from player %s. (%s, %s)", user.getPlayerId(), moveX, moveY));

        if (gameExt.isGameStarted())
        {
            gameExt.addUserData(user.getPlayerId(), moveX, moveY);
            gameExt.trace(String.format("Update data from user %s.", user.getPlayerId()));
            if (gameExt.getUserDataRecvCount() == gameExt.getGameRoom().getSize().getUserCount())
            {
                send(CMD_MOVE, gameExt.getUserData(), gameExt.getGameRoom().getUserList());
                gameExt.trace(String.format("Send data to users %s.", gameExt.getGameRoom().getUserList()));
                gameExt.clearUserData();
            }
        }
        else
            gameExt.trace(ExtensionLogLevel.WARN, "Game is not started yet!!!");

    }
}
