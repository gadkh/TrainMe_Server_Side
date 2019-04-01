package TrainMe.TrainMe.logic.JPA;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.Plugins.TrainMePlugins;
import TrainMe.TrainMe.logic.ActivityService;
import TrainMe.TrainMe.logic.entity.ActivityEntity;

@Service
public class JPAActivityService implements ActivityService {
	private ConfigurableApplicationContext spring;
	private ObjectMapper jackson;
	
	@Autowired
	public void init(IFireBase firebaseService, ConfigurableApplicationContext spring) {
		this.spring = spring;
		this.jackson = new ObjectMapper();
	}

	@Override
	public Object performActivity(ActivityEntity activityEntity) {
		Map<String, Object> rvMap = null;
		if (activityEntity.getType() != null) {
			try {
				String type = activityEntity.getType();
				String targetClassName = "TrainMe.TrainMe.Plugins." + type + "Plugin";
				Class<?> pluginClass = Class.forName(targetClassName);
				// autowire plugin
				TrainMePlugins plugin = (TrainMePlugins) this.spring.getBean(pluginClass);
				Object rv = plugin.invokeAction(activityEntity);
				System.err.println("Return Value: "+rv.getClass());
				rvMap = this.jackson.readValue(this.jackson.writeValueAsString(rv), Map.class);
				//System.err.println("The Map Value Key :" +rvMap.keySet());
				//activityEntity.getMoreAttributes().putAll(rvMap);
				activityEntity.getMoreAttributes().clear();
				activityEntity.getMoreAttributes().putAll(rvMap);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return rvMap;
	}
	
}
