package TrainMe.TrainMe.FireBase.logic.JPA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import TrainMe.TrainMe.FireBase.logic.IFireBase;
import TrainMe.TrainMe.logic.entity.CourseEntity;
import TrainMe.TrainMe.logic.entity.GeneralCourseEntity;
import TrainMe.TrainMe.logic.entity.TrainerEntity;
import TrainMe.TrainMe.logic.entity.UsersEntity;

@Service
public class FireBaseMethods implements IFireBase {
	private FirebaseOptions options;
	private FileInputStream serviceAccount;
	private final String fileName = "D://Final Project Server/train-e0fc2-firebase-adminsdk-zm1mf-f441dd4bd4.json";
	private FirebaseDatabase database;
	private DatabaseReference ref;
	private DatabaseReference databaseReference;
	private DatabaseReference childReference;
	private FirebaseAuth firebaseAuth;
	private int currentNumOfUsers;
	private int maxNumOfUsers;

	@PostConstruct
	public void configure() {
		try {
			this.serviceAccount = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://train-e0fc2.firebaseio.com").build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FirebaseApp.initializeApp(options);
		this.database = FirebaseDatabase.getInstance();
		this.firebaseAuth.getInstance();
		this.ref = database.getReference("server/saving-data/fireblog");
		this.databaseReference = database.getReference("/");
	}

	@Override
	public TrainerEntity storeTrainer(TrainerEntity newTrainer) {
		this.childReference = databaseReference.child("Trainers").child(newTrainer.getId());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(newTrainer, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return newTrainer;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public void deleteByTrainer(TrainerEntity trainetToDelete) {
		this.childReference = databaseReference.child("Trainers").child(trainetToDelete.getId());
		childReference.removeValueAsync();
	}

	@Override
	public void deleteByTrainerId(String trainertId) {
		this.childReference = databaseReference.child("Trainers").child(trainertId);
		childReference.removeValueAsync();
	}

	@Override
	public GeneralCourseEntity addCourse(GeneralCourseEntity generalCourseEntity) {
		this.childReference = databaseReference.child("GeneralCourses").child(generalCourseEntity.getName());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(generalCourseEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return generalCourseEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByGeneralCourseName(String generalCourseName) {
		this.childReference = databaseReference.child("GeneralCourses").child(generalCourseName);
		childReference.removeValueAsync();
	}

	@Override
	public UsersEntity addUser(UsersEntity userEntity) {
		this.childReference = databaseReference.child("Users").child(userEntity.getUserId());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		childReference.setValue(userEntity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Record saved!");
				// decrement countDownLatch value and application will be continues its
				// execution.
				countDownLatch.countDown();
			}
		});

		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return userEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByUserId(String userId) {
		this.childReference = databaseReference.child("Users").child(userId);
		childReference.removeValueAsync();
	}

	@Override
	public CourseEntity addCourse(CourseEntity courseEntity) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.child("GeneralCourses").child(courseEntity.getCourseName()).exists()) {
					if (snapshot.child("Trainers").child((courseEntity.getTrainerId())).child("name").getValue()
							.toString().equals(courseEntity.getTrainerName())) {

						snapshot.child("Courses").child(courseEntity.getCourseId()).getRef().setValue(courseEntity,
								new CompletionListener() {
									@Override
									public void onComplete(DatabaseError error, DatabaseReference ref) {
										System.out.println("Record saved!");
										// decrement countDownLatch value and application will be continues its
										// execution.
										countDownLatch.countDown();
									}
								});
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return courseEntity;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean checkCourseIsFull(String courseId) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				currentNumOfUsers=Integer.parseInt(snapshot.child("Courses").child(courseId).child("currentNumOfUsersInCourse").getValue().toString());
				maxNumOfUsers=Integer.parseInt(snapshot.child("Courses").child(courseId).child("maxNumOfUsersInCourse").getValue().toString());
				countDownLatch.countDown();
			}
			

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		try {
			// wait for firebase to saves record.
			countDownLatch.await();
			return currentNumOfUsers==maxNumOfUsers?true:false;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}