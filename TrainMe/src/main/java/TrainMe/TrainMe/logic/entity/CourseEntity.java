package TrainMe.TrainMe.logic.entity;

public class CourseEntity {
	private String courseName;
    private String trainerName;
    private String trainerId;
    private String time;
    private String currentNumOfUsersInCourse;
    private String courseLocation;
    private String maxNumOfUsersInCourse;
    private String courseId;
    private String date;
    
	public CourseEntity() {
		super();
		this.currentNumOfUsersInCourse="0";
	}

	public CourseEntity(String courseName, String trainerName, String trainerId, String time,
			String currentNumOfUsersInCourse, String courseLocation, String maxNumOfUsersInCourse, String courseId,
			String date) {
		super();
		this.courseName = courseName;
		this.trainerName = trainerName;
		this.trainerId = trainerId;
		this.time = time;
		this.currentNumOfUsersInCourse = currentNumOfUsersInCourse;
		this.courseLocation = courseLocation;
		this.maxNumOfUsersInCourse = maxNumOfUsersInCourse;
		this.courseId = courseId;
		this.date = date;
	}

	public String getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(String trainerId) {
		this.trainerId = trainerId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCurrentNumOfUsersInCourse() {
		return currentNumOfUsersInCourse;
	}

	public void setCurrentNumOfUsersInCourse(String currentNumOfUsersInCourse) {
		this.currentNumOfUsersInCourse = currentNumOfUsersInCourse;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	
	public String getMaxNumOfUsersInCourse() {
		return maxNumOfUsersInCourse;
	}

	public void setMaxNumOfUsersInCourse(String maxNumOfUsersInCourse) {
		this.maxNumOfUsersInCourse = maxNumOfUsersInCourse;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
