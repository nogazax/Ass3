package bgu.spl.net.srv;

import java.util.LinkedList;

public class User {
    final private String userName;
    final private String pass;
    final private boolean isAdmin;
    private boolean isLoggedIn;
    private LinkedList<Integer> regCourses;

    public User(String userName, String pass, boolean isAdmin) {
        this.userName = userName;
        this.pass = pass;
        this.isAdmin = isAdmin;
        this.isLoggedIn = false;
        regCourses = new LinkedList<>();
    }

    public LinkedList<Integer> getRegCourses() {
        return regCourses;
    }

    public String getUserName() {
        return userName;
    }


    public String getPass() {
        return pass;
    }



    public boolean isAdmin() {
        return isAdmin;
    }



    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }



}
