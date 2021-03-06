package TrainMe.TrainMe.Plugins;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.ActivityEntity;
import TrainMe.TrainMe.logic.entity.CourseEntity;

@Component
public class RateCoursePlugin implements TrainMePlugins {

	private ObjectMapper jackson;
	private IFireBase firebaseService;
	
	@Autowired
	public RateCoursePlugin(IFireBase firebaseService) {
		this.firebaseService = firebaseService;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public Object invokeAction(ActivityEntity activityEntity) {
		RateCourse rateCourse=new RateCourse();
		try {
			rateCourse = this.jackson.readValue(activityEntity.getAttributesJson(), RateCourse.class);
			this.firebaseService.rateCourse(rateCourse.getCourseId(), rateCourse.getUserId(), rateCourse.getRate());
			return rateCourse;
		} catch (IOException e) {
			throw new RuntimeException(e);		
			}
	}
	

}
