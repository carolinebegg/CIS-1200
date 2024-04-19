package org.cis1200;

import java.util.TreeMap;

public class Channel {
    private final int ownerId;
    private String ownerUsername;
    private final boolean privacy;
    private final TreeMap<Integer, String> members;

    public Channel(int ownerId, String ownerUsername, boolean privacy) {
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
        this.privacy = privacy;
        members = new TreeMap<>();
    }

    /*
     * Input: int userId, String name
     * Output: void
     * Description: add a member (userId, name) to the Members TreeMap
     */
    public void addMember(int userId, String name) {
        members.put(userId, name);
    }

    /*
     * Input: int userId
     * Output: void
     * Description: remove a member (userId, name) from the Members TreeMap
     */
    public void removeMember(int userId) {
        members.remove(userId);
    }

    /*** Getter Functions ***/

    /*
     * Input: None
     * Output: int
     * Description: getter function for private variable "ownerID"
     */
    public int getOwnerID() {
        return ownerId;
    }

    /*
     * Input: None
     * Output: String
     * Description: getter function for private variable "ownerUsername"
     */
    public String getOwnerName() {
        return ownerUsername;
    }

    /*
     * Input: None
     * Output: boolean
     * Description: getter function for private variable "privacy"
     */
    public boolean getPrivacy() {
        return privacy;
    }

    /*
     * Input: None
     * Output: TreeMap
     * Description: getter function for private variable "members"
     */
    public TreeMap<Integer, String> getMembers() {
        return members;
    }

    /*** Setter Functions ***/

    /*
     * Input: String
     * Output: None
     * Description: setter function for private variable "ownerUsername"
     */
    public void setOwnerName(String ownName) {
        ownerUsername = ownName;
    }
}
