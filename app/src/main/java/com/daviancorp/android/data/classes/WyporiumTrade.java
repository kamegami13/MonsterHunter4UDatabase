package com.daviancorp.android.data.classes;

public class WyporiumTrade {

    private long id;                    // Trade id
    private long itemInId;              // Item in id
    private String itemInName;          // Item in name
    private String itemInIconName;      // Item in icon name
    private long itemOutId;             // Item out id
    private String itemOutName;         // Item out name
    private String itemOutIconName;     // Item out icon name
    private long unlockQuestId;         // Unlock quest id
    private String unlockQuestName;     // Unlock quest name

    /* Default Constructor */
    public WyporiumTrade() {
        this.id = -1;
        this.itemInId = -1;
        this.itemInName = "";
        this.itemInIconName = "";
        this.itemOutId = -1;
        this.itemOutName = "";
        this.itemOutIconName = "";
        this.unlockQuestId = -1;
        this.unlockQuestName = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemInId() {
        return itemInId;
    }

    public void setItemInId(long itemInId) {
        this.itemInId = itemInId;
    }

    public String getItemInName() {
        return itemInName;
    }

    public void setItemInName(String itemInName) {
        this.itemInName = itemInName;
    }

    public String getItemInIconName() {
        return itemInIconName;
    }

    public void setItemInIconName(String itemInIconName) {
        this.itemInIconName = itemInIconName;
    }

    public long getItemOutId() {
        return itemOutId;
    }

    public void setItemOutId(long itemOutId) {
        this.itemOutId = itemOutId;
    }

    public String getItemOutName() {
        return itemOutName;
    }

    public void setItemOutName(String itemOutName) {
        this.itemOutName = itemOutName;
    }

    public String getItemOutIconName() {
        return itemOutIconName;
    }

    public void setItemOutIconName(String itemOutIconName) {
        this.itemOutIconName = itemOutIconName;
    }

    public long getUnlockQuestId() {
        return unlockQuestId;
    }

    public void setUnlockQuestId(long unlockQuestId) {
        this.unlockQuestId = unlockQuestId;
    }

    public String getUnlockQuestName() {
        return unlockQuestName;
    }

    public void setUnlockQuestName(String unlockQuestName) {
        this.unlockQuestName = unlockQuestName;
    }
}
