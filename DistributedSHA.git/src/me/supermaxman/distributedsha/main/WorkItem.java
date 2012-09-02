package me.supermaxman.distributedsha.main;

/**
 * User: Benjamin
 * Date: 02/09/12
 * Time: 22:44
 */
public class WorkItem {
    long ID;
    String seedWord;
//    String hash;

    public WorkItem(long ID) {
        this.ID = ID;
    }

    public String getSeedWord() {
        return seedWord;
    }
}
