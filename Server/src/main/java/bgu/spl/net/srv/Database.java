package bgu.spl.net.srv;
import java.io.BufferedReader;
import java.util.*;
import java.io.FileReader;

/**
 * Passive object representing the bgu.spl.net.srv.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	Dictionary<String, User> users;
	Dictionary<Integer, Course> courses;

	//to prevent user from creating new bgu.spl.net.srv.Database
	private  static class DBHolder{
		private static Database instance = new Database();
	}

	private Database() {
	users = new Hashtable<>();
	courses = new Hashtable<>();
	initialize("Courses.txt"); //TODO Verify filepath
	}


	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return DBHolder.instance;
	} //Done

	/**
	 * loades the courses from the file path specified
	 * into the bgu.spl.net.srv.Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(coursesFilePath));
			String line = reader.readLine();
			int serial=0;
			while (line != null) {
				String[] info = line.split("\\|"); //num-name-kdams-maxstudents
				if (info.length==4) {
					int courseNum = Integer.parseInt(info[0]);
					info[2] = info[2].substring(1, info[2].length()-1); //cutting []
					LinkedList<Integer> kdamsList;
					if (!info[2].equals("")) {
						String[] kdamstring = info[2].split(","); //each cell is a kdam course in string
						Integer[] kdams = new Integer[kdamstring.length]; //setting integer array to parse the courseNum into
						for (int i = 0; i < kdams.length && !info[2].equals(""); i++) { //parsing courseNums to ints
							kdams[i] = Integer.parseInt(kdamstring[i]);
						}
						List<Integer> listkdams1 = Arrays.asList(kdams); // array into list
						kdamsList = new LinkedList<Integer>(listkdams1); //list into LL
					}
					else {
						kdamsList = new LinkedList<>();
					}
					int maxstuds = Integer.parseInt(info[3]);
					courses.put(courseNum, new Course(courseNum, info[1], kdamsList, maxstuds, serial++));
				}
				line = reader.readLine();
			}
			reader.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	// unified method for studentReg and adminReg
	public boolean register(String userName, String pass, boolean isAdmin){
		// locking on users hashMap,
		synchronized (users){
			//making sure username is available
			if (users.get(userName)!=null)
				return false;
			// inserting new user
			User newUser = new User(userName,pass,isAdmin);
			users.put(userName, newUser);
		}
		return true;

	}

	public boolean login(String userName, String pass){
		User user = users.get(userName);
		if (user!=null){
			synchronized (user){
				if (!user.isLoggedIn()&&checkPass(user, pass)){
					user.setLoggedIn(true);
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkPass(User currentUser, String pass) {
		return currentUser.getPass().equals(pass);
	}

	public boolean logout(User user){
		synchronized (user){
			if (user.isLoggedIn()) {
				user.setLoggedIn(false);
				return true;
			}
		}
		return false;
	}


	//checking if student has all kdams for courseNum by checking if course.Kdams is subset of student.Courses
	private boolean checkKdamForStud(String userName, int courseNum){
		Course desiredCourse = courses.get(courseNum);
		User user = users.get(userName);
		synchronized (user) {
			return user.getRegCourses().containsAll(desiredCourse.getKdams());
		}
	}




	public LinkedList<Integer> kdamCheck(int courseNum){
		Course desiredCourse = courses.get(courseNum);
		if (desiredCourse!=null) { // no need to sync, as this is the only method which uses course.kdams
			return desiredCourse.getKdams();
		}
		return null;
	}

	public LinkedList<Integer> myCourses(User user){
			return user.getRegCourses();
		}

	public Course getCourse(int courseNum){
		return courses.get(courseNum);
	} //if there is no course with this num, return null

	public User getUser(String userName){
		return users.get(userName);
	}

	public boolean isReg(String userName, int courseNum){//
		// checks if the student is regged to this course IN THE STUDENTS COURSE LIST
		User stud = users.get(userName);
		synchronized (stud) { //syncing on the student, so no one can change it's list
			return stud.getRegCourses().contains(courseNum);
		}
	}

	// un-regs student from course
	public boolean unReg(String userName, int courseNum){

		Course desiredCourse = courses.get(courseNum);
		// if no such course OR student not regged to course
		if (desiredCourse==null||!isReg(userName,courseNum))
			return false;
		boolean removedFromCourse = false;
		synchronized (desiredCourse){
			// trying to remove STUDENT from COURSE LIST
			LinkedList regStuds =	desiredCourse.getRegStudents();
				removedFromCourse = regStuds.remove(userName);
			}
			// if removed from course list, try to remove Course from STUDENT LIST
			if (removedFromCourse) {
				User user = users.get(userName);
				// syncing on user so no one can shuffle it's list
				synchronized (user) {
					user.getRegCourses().remove(user.getRegCourses().indexOf(courseNum)); //FIND THE INDEX OF THE COURSE AND REMOVE IT FROM THE LIST

					return true;
				}
			}
		return false;
	}
	public boolean courseReg(String userName, int courseNum) {

		Course desiredCourse = courses.get(courseNum);
		// if no such course OR already registered OR no Kdams ->FALSE
		if (desiredCourse==null||isReg(userName,courseNum)||!checkKdamForStud(userName,courseNum))
			return false;
		// locking on the specific course, checking for availability, and registering if available
		boolean didReg = false;
		synchronized (desiredCourse){
			LinkedList regStuds =	desiredCourse.getRegStudents();
			if (regStuds.size()<desiredCourse.getCapacity()) {
				regStuds.add(userName);
				didReg = true;
			}
		}
		// if we got a chair in the course, writing it to the student's regged courses
		if (didReg){
			User user = users.get(userName);
			synchronized (user){
				if (user.getRegCourses().add(courseNum))
					return true;
			}
		}

		return false;
	}
}
