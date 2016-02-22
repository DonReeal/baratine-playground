package eu.dons.pollbus.user.control;

import eu.dons.pollbus.base.AppException;
import eu.dons.pollbus.base.service.ResourceBase;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;
import io.baratine.core.Modify;
import io.baratine.core.Result;
import lombok.Builder;


@Builder
public class UserBean extends ResourceBase<UserId, User> implements IUser {
	
	private UserId userId;	
	// private StoredVal<User> db;
	private User user;
//	
//	@OnLoad
//	public void onLoad(Result<Boolean> result) {
//		Objects.requireNonNull(db);
//		db.load(User.EMPTY, result.from( u -> {
//			this.user = u;
//			return true;
//		}));
//	}	
//	
//	@OnSave
//	public void save(Result<Boolean> result){
//		db.save(this.user, result);
//	}

	@Override
	public void get(Result<User> result) {
		result.complete(user);		
	}

	@Override
	@Modify
	public void create(User user, Result<String> result) throws AppException {
		
		validateStatePreCreate(user);		
		getValidator().validate(user, result.from(u -> {
			this.user = u;
			return user.identity().getKey();
		}));		
	}

	
	private void validateStatePreCreate(User user) {		
		if(this.user == null )
			throw new IllegalStateException("resources id not initialized!");		
		
		if(!this.user.isEmpty())
			throw new IllegalStateException("user already exists!");		
		
		if(!userId.equals(user.identity()))
				throw new IllegalArgumentException("wrong data passed - user#identity did not match!");
	}
	
	
	@Override
	@Modify
	public void delete(Result<Boolean> result) {
		this.user = User.EMPTY;
	}
	
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + "]";
	}
}
