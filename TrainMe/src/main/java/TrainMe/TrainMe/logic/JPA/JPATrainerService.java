package TrainMe.TrainMe.logic.JPA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import TrainMe.TrainMe.logic.TrainerService;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import TraineMe.TrainMe.logic.entity.TrainerEntity;

@Service
public class JPATrainerService implements TrainerService {
	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	private FirebaseDatabase database;
	private DatabaseReference ref;
	//private final String fileName="./JasonFiles/train-e0fc2-firebase-adminsdk-zm1mf-f441dd4bd4.json";
	private final String fileName="D://Final Project Server/train-e0fc2-firebase-adminsdk-zm1mf-f441dd4bd4.json";

	@PostConstruct
	public void init() {
		try {
			this.serviceAccount = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://train-e0fc2.firebaseio.com")
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FirebaseApp.initializeApp(options);
		this.database = FirebaseDatabase.getInstance();
		this.ref = database.getReference("server/saving-data/fireblog");

	}
	
	@Override
	public TrainerEntity storeTrainer(TrainerEntity newTrainer) {
		DatabaseReference databaseReference = database.getReference("/");
		 DatabaseReference childReference = databaseReference.child("trainer").child(newTrainer.getId());
		 CountDownLatch countDownLatch = new CountDownLatch(1);
		 childReference.setValue(newTrainer, new CompletionListener() {
			
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				 System.out.println("Record saved!");
                 // decrement countDownLatch value and application will be continues its execution.
                 countDownLatch.countDown();					
			}
		});
		
		  try {
           //wait for firebase to saves record.
           countDownLatch.await();
           return newTrainer;
       } catch (InterruptedException ex) {
           ex.printStackTrace();
           return null;
       }
	}

	@Override
	public void deleteByTrainer(TrainerEntity trainetToDelete) {
		DatabaseReference databaseReference = database.getReference("/");
		 DatabaseReference childReference = databaseReference.child("trainer").child(trainetToDelete.getId());
		 childReference.removeValueAsync();
	}

	@Override
	public void deleteByTrainerId(String trainertId) {
		DatabaseReference databaseReference = database.getReference("/");
		 DatabaseReference childReference = databaseReference.child("trainer").child(trainertId);
		 childReference.removeValueAsync();
	}
}


