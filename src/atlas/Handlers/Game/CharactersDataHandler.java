package atlas.Handlers.Game;

import atlas.Player;
import atlas.AtlasExtension;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class CharactersDataHandler extends BaseClientRequestHandler
{
    private static final String CMD_DATA = "charactersData";
    private AtlasExtension gameExt;

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        gameExt = (AtlasExtension) getParentExtension();
        SFSObject charactersData = GetCharactersData();
        send(CMD_DATA, charactersData, user);
    }

    // ToDo: change fake with real data
    private SFSObject GetCharactersData() {
        SFSObject fullData = new SFSObject();
        SFSArray redTeam = new SFSArray();
        SFSArray blueTeam = new SFSArray();
        for (Player player : gameExt.playersDict.values()) {
            SFSObject playerData = new SFSObject();
            playerData.putInt("id", player.user.getPlayerId());
            playerData.putUtfString("character", player.charracter.getName());
            // ToDo: check for team
            blueTeam.addSFSObject(playerData);
        }
        fullData.putSFSArray("red", redTeam);
        fullData.putSFSArray("blue", blueTeam);
        return fullData;
    }
}
