package atlas.Handlers.Party;

import atlas.Player;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class UserLoadHandler extends BaseClientRequestHandler
{
    private static final String CMD_LOAD = "load";
    private static final String CMD_START = "startGame";
    private AtlasExtension gameExt;

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        gameExt = (AtlasExtension) getParentExtension();

        int playerId = user.getPlayerId();
        int progress = params.getInt("progress");

        gameExt.playersDict.get(playerId).loadProgress = progress;

        if (progress == 100 && isAllLoaded())
        {
            send(CMD_START, new SFSObject(), gameExt.getGameRoom().getUserList());
        }
        else
        {
            SFSObject data = new SFSObject();
            data.putInt("id", playerId);
            data.putInt("progress", progress);
            send(CMD_LOAD, data, gameExt.getGameRoom().getUserList());
        }
    }

    private Boolean isAllLoaded() {
        for (Player player : gameExt.playersDict.values())
            if (player.loadProgress != 100)
                return false;
        return true;
    }
}
