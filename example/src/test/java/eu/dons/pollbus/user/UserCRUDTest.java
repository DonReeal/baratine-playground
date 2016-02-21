package eu.dons.pollbus.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import javax.net.ssl.ExtendedSSLSession;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.data.Entity;
import eu.dons.pollbus.base.data.immutable.EntityBase;
import eu.dons.pollbus.base.validation.BeanValidatorService;
import eu.dons.pollbus.user.control.UsersEndpoint;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Lookup;
import io.baratine.core.ServiceExceptionIllegalArgument;
import io.baratine.core.ServiceManager;


@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidatorService.class, UsersEndpoint.class})
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
		
		User in =  new User(new UserId("123"), "DonReeal", "password");		
		String userKey = userResource123.create(in);
		assertThat(userKey, is("123"));
		
		User persistedUser = userResource123.get();
		assertThat(persistedUser.isEmpty(), is(false));		
		assertThat(persistedUser, is(in));
		assertTrue(persistedUser.contentEquals(in));
	}

	@Test
	public void creatingUserShouldFailWhenTheCallerUsesWrongIdentityValue() throws AppException {		
		
		failure.expectCause(org.hamcrest.CoreMatchers.instanceOf(ServiceExceptionIllegalArgument.class));
		failure.expectMessage("identity");
		
		UserId userId = new UserId("this-is-the-wrong-key!");		
		userResource123.create(new User(userId, "indifferentName", "indifferentPassword"));		
	}

}
