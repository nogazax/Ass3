package bgu.spl.net.api;

import bgu.spl.net.srv.Course;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.User;

import java.util.Comparator;
import java.util.LinkedList;

public class MessagingProtocolImpl<T> implements MessagingProtocol<Message> {
    private boolean shouldStop;
    private User user;
    private Database db;

    public MessagingProtocolImpl() {
        shouldStop = false;
        user = null;
        db = Database.getInstance();

    }

    @Override
    public Message process(Message m) {
        Command cmd = (Command) m;
        switch (((Command) m).getOpcode()) {
            case 1:
                return adminReg(cmd);
            case 2:
                return studentReg(cmd);
            case 3:
                return login(cmd);
            case 4:
                return logout(cmd);
            case 5:
                return courseReg(cmd);
            case 6:
                return kdamCheck(cmd);
            case 7:
                return courseStat(cmd);
            case 8:
                return studentStat(cmd);
            case 9:
                return isReg(cmd);
            case 10:
                return unReg(cmd);
            case 11:
                return myCourses(cmd);
        }
        return null; // we will never get here, as opcode is always ok (by assumption)
    }


    private String getUserName(Command cmd) {
        return cmd.getUserName();
    }

    private String getUserPass(Command cmd) {
        return cmd.getPass();
    }

    private boolean logged() {
        return user != null;
    }

    private Response adminReg(Command cmd) {

        if (!logged() && db.register(cmd.getUserName(), cmd.getPass(), true))
            return new Response(true, cmd.getOpcode());
        return new Response(false, cmd.getOpcode());

    }


    private Response studentReg(Command cmd) {
        if (!logged() && db.register(cmd.getUserName(), cmd.getPass(), false))
            return new Response(true, cmd.getOpcode());
        return new Response(false, cmd.getOpcode());
    }

    private Response login(Command cmd) {

        //check that client is not logged in already
        String userName = cmd.getUserName();
        if (!logged() && db.login(userName, cmd.getPass())) {
            user = db.getUser(userName);
            return new Response(true, cmd.getOpcode());
        }
        return new Response(false, cmd.getOpcode());

    }

    //check that client is  logged in
    private Response logout(Command cmd) {
        if (logged() && db.logout(user)) {
            shouldStop = true; //======sure is that needed? TPC?
            return new Response(true, cmd.getOpcode());
        }
        return new Response(false, cmd.getOpcode());
    }

    private Response courseReg(Command cmd) {
        //check that client is logged in already, and is a student (and not admin)
        if (logged() && !user.isAdmin()) {
            int courseNum = cmd.getCourse();
            if (db.courseReg(user.getUserName(), courseNum))//may recieve false if reg not successful
                return new Response(true, cmd.getOpcode());
        }
        return new Response(false, cmd.getOpcode());
    }

    private Response kdamCheck(Command cmd) {
        //check that client is logged in already, and is a student (and not admin)
        if (logged() && !user.isAdmin()) {
            LinkedList<Integer> ans = db.kdamCheck(cmd.getCourse());
            if (ans != null) {// we get null if the course does not exist
                String output = ans.toString().replaceAll(" ","");
                return new Response(cmd.getOpcode(), output);
            }
        }
        return new Response(false, cmd.getOpcode());

    }

    private Response courseStat(Command cmd) {
        //check that client is  logged in already, and is an admin
        if (logged() && user.isAdmin()) {
            Course course = db.getCourse(cmd.getCourse());
            // if course exists
            if (course != null) {
                // sort list alphabetically, and send it
                // when we sort it, locking so no one is registering to this course
                String ans;
                synchronized (course) {
                    int capacity = course.getCapacity();
                    int seatsRemaining = capacity - course.getRegStudents().size();
                    String studentsRegged = course.getRegStudents().toString();
                    studentsRegged = studentsRegged.replaceAll(" ", "");
                    java.util.Collections.sort(course.getRegStudents());
                    ans = "Course: (" + course.getCourseNum() + ") " + course.getCourseName() + "\n" +
                            "Seats Available: " + seatsRemaining + " / " + capacity + "\n" +
                            "Students Registered: " + studentsRegged;
                }
                return new Response(cmd.getOpcode(), ans);

            }
        }
        return new Response(false, cmd.getOpcode());

    }

    private Response studentStat(Command cmd) {
        //check that client is  logged in already, and is an admin
        if (logged() && user.isAdmin()) {
            User requestedUser = db.getUser(cmd.getUserName());
            // check if exists such student, and is not an admin
            if (requestedUser != null && !requestedUser.isAdmin()) {
                // locking on a student, so it cannot register to courses while printing it's data
                String ans;
                synchronized (requestedUser) {
                    // sorting student's reged courses according to their order of appearance in the course file (serial num)
                    java.util.Collections.sort(requestedUser.getRegCourses(), new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            int ser1 = db.getCourse(o1).getSerialNum();
                            int ser2 = db.getCourse(o2).getSerialNum();
                            return Integer.compare(ser1, ser2);
                        }
                    });
                    ans = "Student: " + requestedUser.getUserName() + "\n" +
                            "Courses: " + requestedUser.getRegCourses().toString().replaceAll(" ","");
                    return new Response(cmd.getOpcode(), ans);
                }
            }
        }
        return new Response(false, cmd.getOpcode());
    }

    private Response isReg(Command cmd) {

        // check if stud exists and not admin
        if (logged() && !user.isAdmin()) {
            // if reg
            if (db.isReg(user.getUserName(), cmd.getCourse()))
                return new Response(cmd.getOpcode(), "REGISTERED");
            // if not reg
            return new Response(cmd.getOpcode(), "NOT REGISTERED");
        }
        return new Response(false, cmd.getOpcode());
    }

    private Response unReg(Command cmd) {

        if (logged() && !user.isAdmin()) {
            // try to unreg
            if (db.unReg(user.getUserName(), cmd.getCourse()))
                return new Response(true, cmd.getOpcode());
        }
        return new Response(false, cmd.getOpcode());
    }

    private Response myCourses(Command cmd) {
        if (logged() && !user.isAdmin()) {
            synchronized (user) { // so no admin looks to sort the student's list
                // ans = students reged courses
                LinkedList ans = db.myCourses(user);
                return new Response(cmd.getOpcode(), ans.toString().replaceAll(" ",""));
            }
        }
        return new Response(false, cmd.getOpcode());
    }


    @Override
    public boolean shouldTerminate() {
        return shouldStop;
    }
}
