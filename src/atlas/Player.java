package atlas;

import atlas.Charracters.Charracter;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class Player {
    public User user;
    public Charracter charracter;
    public Boolean ready = false;
    public int loadProgress = 0;

    private Boolean dataReceived;
    private Boolean turnEnd = false;
    private ISFSObject playerData; //ToDo: temporary variable

    public Player(User user, Charracter charracter)
    {
        this.user = user;
        this.charracter = charracter;
        dataReceived = false;
    }

    public void updateUserData(ISFSObject data)
    {
        //ToDo: validate
        playerData = data;
        updateUserDataReceived();
    }

    public ISFSObject getAllUserData()
    {
        return playerData;
    }

    public void clearAllUserData()
    {
        playerData = new SFSObject();
        dataReceived = false;
    }

    private void updateUserDataReceived()
    {
        if (!dataReceived)
        {
            dataReceived = true;
        }
    }

    public void endTurn()
    {
        turnEnd = true;
    }

    public boolean checkTurnEnd()
    {
        return turnEnd;
    }

    public void newTurn()
    {
        turnEnd = false;
    }

    public Boolean checkDataReceived()
    {
        return dataReceived;
    }
}
