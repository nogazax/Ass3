package bgu.spl.net.srv;

import java.util.LinkedList;

public class Course {
    private final int courseNum;
    private final String courseName;
    private final int capacity;


    private final int serialNum;
    private LinkedList<Integer> Kdams;
    private LinkedList<String> regStudents;



    public int getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public LinkedList<Integer> getKdams() {
        return Kdams;
    }

    public int getCapacity() {
        return capacity;
    }

    public LinkedList<String> getRegStudents() {
        return regStudents;
    }


    public int getSerialNum() {
        return serialNum;
    }

    public Course(int courseNum, String courseName, LinkedList<Integer> kdams, int capacity, int serialNum) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.Kdams = kdams;
        this.capacity = capacity;
        this.regStudents = new LinkedList<>();
        this.serialNum = serialNum;
    }
}
