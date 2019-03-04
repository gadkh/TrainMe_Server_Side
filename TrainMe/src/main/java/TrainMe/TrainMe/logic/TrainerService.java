package TrainMe.TrainMe.logic;

import TraineMe.TrainMe.logic.entity.TrainerEntity;

public interface TrainerService {
	public TrainerEntity storeTrainer(TrainerEntity newTrainer);
	public void deleteByTrainer(TrainerEntity trainetToDelete);
	public void deleteByTrainerId(String trainertId);
}
