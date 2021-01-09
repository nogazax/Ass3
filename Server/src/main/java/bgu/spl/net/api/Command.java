package bgu.spl.net.api;

public class Command extends Message{

    final private short opcode;
    private short course;
    private String userName;
    private String pass;

    public short getOpcode() {
        return opcode;
    }



    public short getCourse() {
        return course;
    }

    public void setCourse(short course) {
        this.course = course;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    public Command(short OP){
        //logout, mycourses
        opcode=OP;
        course=-1;
        userName=null;
        pass=null;

    }
}
