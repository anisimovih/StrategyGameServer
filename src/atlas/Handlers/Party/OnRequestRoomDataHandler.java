package atlas.Handlers.Party;

import atlas.Player;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

import java.util.Map;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class OnRequestRoomDataHandler extends BaseClientRequestHandler
{
    private static final String CMD_DATA = "roomData";

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        AtlasExtension gameExt = (AtlasExtension) getParentExtension();

        if (!gameExt.getGameRoom().isGame()) return;

        SFSArray playersData = new SFSArray();
        for (Map.Entry<Integer, Player> entry : gameExt.playersDict.entrySet()) {
            SFSObject playerData = new SFSObject();
            playerData.putInt("id", entry.getKey());
            playerData.putUtfString("characterName", entry.getValue().charracter.getName());
            playerData.putBool("ready", entry.getValue().ready);
            playersData.addSFSObject(playerData);
        }
        SFSObject data = new SFSObject();
        data.putSFSArray("playersData", playersData);
        send(CMD_DATA, data, user);
    }
}
