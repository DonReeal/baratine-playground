package eu.dons.pollbus.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.core.validation.BeanValidator;
import eu.dons.pollbus.user.control.UsersEndpoint;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Lookup;
import io.baratine.core.ServiceManager;


@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidator.class, UsersEndpoint.class})
public class UserCRUDTest {
	
	@Rule
	public ExpectedException failure = ExpectedException.none();
	
	
	@Lookup
	private ServiceManager sm;
	
	@Lookup("/users")
	private IUsersSync sync;
	
	
	private IUserSync userResource123;
	
	@Before 
	public void setup() {
		userResource123 = sm.lookup("/users/123").as(IUserSync.class);
	}
	

	
	
	// === Endpoint
	@Test
	public void usersEndpointCreatesNewUsers() throws AppException {
		String userKey = sync.createUser("don", "password");
		assertTrue(userKey != null);
	}
	
	
	// === Resource
	@Test
	public void userResourceWithoutUserCreatedHasEmptyManagedUser(){
		User user = userResource123.get();
		assertTrue(user != null);
		assertTrue(user.isEmpty());
	}
	
	@Test
	public void userResourceCreate() throws AppException {	
		
		String id = userResource123.create(new User(new UserId("123"), "DonReeal"));
		User user = userResource123.get();
		assertFalse(user.isEmpty());
		assertTrue("123".equals(id));

	}
	
	@Test()
	public void creatingUserShouldFailIfTheCallerUsesWrongIdentityValue() throws AppException {
		
		
		failure.expectCause(org.hamcrest.CoreMatchers.instanceOf(AppException.class));
		failure.expectMessage("identity");
		
		userResource123.create(new User(new UserId("someWrongKey"), "indifferentName"));
		
		
	}

}
