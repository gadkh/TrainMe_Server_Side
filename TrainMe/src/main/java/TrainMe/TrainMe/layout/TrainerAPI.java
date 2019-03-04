package TrainMe.TrainMe.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import TrainMe.TrainMe.layout.TO.TrainerTO;
import TrainMe.TrainMe.logic.TrainerService;
import TraineMe.TrainMe.logic.entity.TrainerEntity;

@RestController
public class TrainerAPI {
	private TrainerService trainerService;
	
	@Autowired
	public void setTrainerService(TrainerService trainerService)
	{
		this.trainerService=trainerService;
	}
	
	@RequestMapping(value = "/ex/foos", method = RequestMethod.GET)
	public String getFoosBySimplePath() {
	    return "Get some Foos";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/trainme/trainners", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public TrainerTO addTrainer(@RequestBody TrainerTO trainerTO)
	{
		TrainerEntity trainerEntity=trainerTO.toEntity();
		this.trainerService.storeTrainer(trainerEntity);
		return new TrainerTO(trainerEntity);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeTrainer(@RequestBody TrainerTO trainerTO)
	{
		TrainerEntity trainerToRemove=trainerTO.toEntity();
		this.trainerService.deleteByTrainer(trainerToRemove);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/trainme/removeTrainer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeTrainerById(@PathVariable("id") String id)
	{
		
	}
}
