package TrainMe.TrainMe.logic;

import TrainMe.TrainMe.logic.entity.CourseEntity;

public interface CourseService {
	public CourseEntity saveCourse(CourseEntity courseEntity);
	public boolean checkCourseIsFull(String courseId);
}
