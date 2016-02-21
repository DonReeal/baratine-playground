package eu.dons.pollbus.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

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
import io.baratine.core.ServiceExceptionIllegalArgument;
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
		assertThat(userKey, notNullValue());
	}
	
	
	// === Resource
	@Test
	public void userResourceWithoutUserCreatedHasEmptyManagedUser() {
		User user = userResource123.get();
		assertTrue(user != null);
		assertTrue(user.isEmpty());
	}
	
	@Test
	public void userResourceCreate() throws AppException {
		
		String userKey = userResource123.create(new User(new UserId("123"), "DonReeal", "password"));
		User user = userResource123.get();
		assertThat(user.isEmpty(), is(false));
		assertThat(userKey, is("123"));

	}
	
	@Test
	public void creatingUserShouldFailWhenTheCallerUsesWrongIdentityValue() throws AppException {		
		
		failure.expectCause(org.hamcrest.CoreMatchers.instanceOf(ServiceExceptionIllegalArgument.class));
		failure.expectMessage("identity");
		
		UserId userId = new UserId("this-is-the-wrong-key!");		
		userResource123.create(new User(userId, "indifferentName", "indifferentPassword"));		
	}

}
