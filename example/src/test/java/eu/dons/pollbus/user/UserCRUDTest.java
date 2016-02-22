package eu.dons.pollbus.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.ConfigurationBaratine.Log;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.validation.BeanValidatorService;
import eu.dons.pollbus.user.control.UsersEndpoint;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Lookup;
import io.baratine.core.ServiceExceptionIllegalArgument;


@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidatorService.class, UsersEndpoint.class})
public class UserCRUDTest {
	
	@Rule
	public ExpectedException failure = ExpectedException.none();
	
	// === Endpoint
	@Lookup("/users")
	private IUsersSync sync;
	
	@Test
	public void usersEndpointCreatesNewUsers() throws AppException {
		String userKey = sync.createUser("newUser", "password");
		assertThat(userKey, notNullValue());
	}	
	
	// === Resource
	@Lookup("/users/empty") 
	private IUserSync userResourceEmpty;
	
	@Test
	public void beforeUserCreateAUserResourceHasAnEmptyUser() {
		User user = userResourceEmpty.get();
		assertTrue(user != null);
		assertTrue(user.isEmpty());
	}
	
	@Lookup("/users/to-be-created") 
	private IUserSync userResourceToBeCreated;
	@Test
	public void createdUserShouldBeIdenticalAndContentEqualsWithInput() throws AppException {
		
		User in =  new User(new UserId("to-be-created"), "DonReeal", "password");		
		String userKey = userResourceToBeCreated.create(in);
		assertThat(userKey, is("to-be-created"));
		
		User persistedUser = userResourceToBeCreated.get();
		assertThat(persistedUser.isEmpty(), is(false));		
		assertThat(persistedUser, is(in));
		assertTrue(persistedUser.contentEquals(in));
	}

	
	@Lookup("/users/abc")
	private IUserSync userResourceABC;
	
	@Test
	public void creatingUserShouldFailWhenTheCallerUsesWrongIdentityValue() throws AppException {		
		
		failure.expectCause(org.hamcrest.CoreMatchers.instanceOf(ServiceExceptionIllegalArgument.class));
		failure.expectMessage("identity");
		
		UserId userId = new UserId("this-is-the-wrong-key!");		
		userResourceEmpty.create(new User(userId, "indifferentName", "indifferentPassword"));		
	}

}
