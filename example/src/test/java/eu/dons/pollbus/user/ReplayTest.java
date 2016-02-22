package eu.dons.pollbus.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.validation.BeanValidatorService;
import eu.dons.pollbus.user.control.UsersEndpoint;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Lookup;
import io.baratine.core.ServiceManager;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidatorService.class, UsersEndpoint.class}, journalDelay = 250000)
public class ReplayTest {	
	
	private static final User ALICE = new User(new UserId("1"), "alice", "password");
	private static final User BOB = new User(new UserId("2"), "bob", "password");
	private static final User CARL = new User(new UserId("3"), "carl", "password");
	
	 @Inject 
	 private RunnerBaratine baratine;
	 
	 @Lookup("/users")
	 private IUsersSync users;
	 

	 @Before
	 public void setup() throws InterruptedException, AppException {
		 
		 	
		 	System.out.println("alice: " + users.createUser("alice", "password"));
		 	System.out.println("bob: " + users.createUser("bob","password"));
		 	System.out.println("carl: " + users.createUser("carl","password"));

		    baratine.closeImmediate();
		    baratine.start();
		    Thread.sleep(200);
	 }
	 
	 @Lookup
	 ServiceManager currentManager;
	 
	 @Test
	 public void whenReplayingAllUsersSurvive() {
		 
		 User alice = currentManager.lookup("/users/1").as(IUserSync.class).get();
		 User bob =  currentManager.lookup("/users/2").as(IUserSync.class).get();
		 User carl = currentManager.lookup("/users/3").as(IUserSync.class).get();
		 
		 // ids generated in same sequence 
		 assertThat(alice, is(ALICE));
		 assertThat(bob, is(BOB));
		 assertThat(carl, is(CARL));
		 
		 assertTrue(alice.contentEquals(ALICE));
		 assertTrue(bob.contentEquals(BOB));
		 assertTrue(carl.contentEquals(CARL));

	 }

}
