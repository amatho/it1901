package core;

public class User {

    private String username;
    private List<Scorecard> scorecardHistory;
    private int userID = 0;


    public User(String username) {
        this.username = username;
        this.userID += 1;
    }

    @Override
    public String toString() {
        return username;
    }

    public List<Scorecard> getScorecardHistory(){
        return scorecardHistory;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }
}
