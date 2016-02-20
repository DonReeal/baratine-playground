package eu.dons.pollbus.user.control;

import javax.inject.Inject;

import eu.dons.baratine.persistence.StoredVal;
import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.boundary.IUsers;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Lookup;
import io.baratine.core.OnLookup;
import io.baratine.core.Result;
import io.baratine.core.Service;
import io.baratine.core.ServiceRef;
import io.baratine.store.Store;

@Service("/users")
public class UsersEndpoint implements IUsers {
	
	private long userIdCounter = 0L;
	
	@Inject @Lookup("store:///users")
	private Store<User> store;	
	public void setStore(Store<User> store) {
		this.store = store;
	}
	

	@Override
	public void createUser(String login, String password, Result<String> result) throws AppException {
		String allocId = Long.toString(++ userIdCounter);		
		IUser newUser = ServiceRef.current().lookup("/" + allocId).as(IUser.class);
		User data = new User(new UserId(allocId), login);
		newUser.create(data, result);
	}
	

	@OnLookup
	public IUser onLookup(String path) {
		
		String key = path.substring(1);	
	// 	new UserBean().setValidator(validator);
		return UserBean.builder()
				.userId(new UserId(key))
				.db(new StoredVal<>(store, key))
				.build();		
		
	}	

}
