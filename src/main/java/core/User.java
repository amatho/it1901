package core;

import java.util.UUID;

public class User {

    private String username;
//    private List<Scorecard> scorecardHistory;
    private UUID userID;

    public User(String username) {
        this.username = username;
        this.userID = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return username;
    }

//    public List<Scorecard> getScorecardHistory(){
//        return scorecardHistory;
//    }

    public String getUsername() {
        return username;
    }

    public UUID getUserID() {
        return userID;
    }
}
