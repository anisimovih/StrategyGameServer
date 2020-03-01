package atlas;

import atlas.Charracters.Charracter;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import java.util.ArrayList;
import java.util.Collection;

final class Player {
    User user;
    Charracter charracter;
    private Collection<Integer> movePathX= new ArrayList<>();
    private Collection<Integer> movePathZ = new ArrayList<>();
    private Boolean dataRecived = false;

    Player(User user, Charracter charracter)
    {
        this.user = user;
        this.charracter = charracter;
        dataRecived = false;
    }

    void updateUserData(ISFSObject data)
    {
        //ToDo: validate
        movePathX = data.getIntArray("moveX");
        movePathZ = data.getIntArray("moveZ");
        updateUserDataReceived();
    }

    ISFSObject getAllUserData()
    {
        ISFSObject data = new SFSObject();

        data.putIntArray("moveX", movePathX);
        data.putIntArray("moveZ", movePathZ);
        return data;
    }

    ISFSObject getUserStartData()
    {
        ISFSObject data = new SFSObject();

        data.putUtfString("hero", charracter.getName());
        return data;
    }

    void clearAllUserData()
    {
        movePathX.clear();
        movePathZ.clear();
    }

    private void updateUserDataReceived()
    {
        if (!dataRecived)
        {
            dataRecived = true;
        }
    }

    Boolean checkDataRecived()
    {
        return dataRecived;
    }
}
