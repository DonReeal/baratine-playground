package eu.dons.baratine.rest.mock.endpoint.inmem;

import io.baratine.core.Result;
import io.baratine.core.ResultStream;
import io.baratine.core.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.dons.baratine.rest.RestEndpoint;

@Service("/messages")
public class IM_MessagesEndpoint implements RestEndpoint<Message> {

	public long _count = 0L;
	public HashMap<String, Message> _messages = new HashMap<String, Message>();

	@Override
	public void get(Result<List<Message>> result) {
		result.complete(new ArrayList<Message>(_messages.values()));
	}

	@Override
	public void delete() {
		_messages.clear();
	}

	@Override
	public void post(Message resource, Result<String> result) {

		String url = "/" + ++_count;

		Message m = new Message();
		m.setUrl(url);
		m.setValue(resource.getValue());
		_messages.put(url, m);
		result.complete(url);
	}

	@Override
	public void put(Iterable<Message> messages, ResultStream<Message> result) {
		messages.forEach(r -> {
			_messages.put(r.getUrl(), r);
			result.accept(r);
		});
		result.complete();
	}
}