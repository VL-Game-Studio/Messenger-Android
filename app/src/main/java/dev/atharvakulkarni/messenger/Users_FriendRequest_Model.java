package dev.atharvakulkarni.messenger;

public class Users_FriendRequest_Model
{
    public static final int USERS_TYPE=0;
    public static final int FRIENDREQUEST_TYPE=1;

    public int type;
    public String names,photo,userid;

    public Users_FriendRequest_Model(int type, String names, String photo, String userid)
    {
        this.type = type;
        this.names = names;
        this.photo = photo;
        this.userid = userid;
    }
}