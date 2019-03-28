package TrainMe.TrainMe.FireBase.logic;

import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

public interface IFireBase {
	/**Trainer Methods**/
	public TrainerEntity storeTrainer(TrainerEntity newTrainer);
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
	
	/**General Course Methods**/
	public GeneralCourseEntity addCourse(GeneralCourseEntity generalCourseEntity);
	public void deleteByGeneralCourseName(String generalCourseName);

	/**User Methods**/
	public UsersEntity addUser(UsersEntity userEntity);
	public void deleteByUserId(String userId);
	
	/**Course Methods**/
	public CourseEntity addCourse(CourseEntity courseEntity);
	public boolean checkCourseIsFull(String courseId);
}
