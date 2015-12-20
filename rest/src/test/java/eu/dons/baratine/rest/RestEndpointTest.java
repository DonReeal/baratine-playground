package eu.dons.baratine.rest;

import static org.junit.Assert.assertTrue;
import io.baratine.core.Lookup;
import io.baratine.core.ResultStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;

import eu.dons.baratine.rest.test.RestEndpointSync;

@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={ MessagesInMem.class })
public class RestEndpointTest {
	
	@Inject @Lookup("/messages")
	RestEndpointSync<Message> _service;
	
	@Test
	public void test() {
		
		System.out.println(_service.get());
		
		Message m = new Message();
		m.setValue("hello");
		String url = _service.post(m);
		System.out.println(url);		
		System.out.println(_service.post(m));		
		System.out.println(_service.get());
		
		
	}
	
	@Test
	public void testUpdate() throws InterruptedException{
		
		_service.delete();
		
		System.out.println("Cleared content of endpoint - get yields: " + _service.get());
		
		Message m1In = new Message();
		String v1 = "value.1";
		m1In.setValue(v1);
		_service.post(m1In);
		
		Message m2In = new Message();
		String v2 = "value.2";
		m2In.setValue(v2);
		_service.post(m2In);
		
		
		
		List<Message> messages = _service.get();
		
		Message m1 = getByValue(messages, v1);
		System.out.println("Found message one: " + m1);		
		Message m2 = getByValue(messages, v2);
		System.out.println("Found message two: " + m2);
		System.out.println(messages);
		
		String url1 = m1.getUrl();
		String value1Updated = m1.getValue() + m1.getValue(); 
		m1.setValue(value1Updated);
		String url2 = m2.getUrl();
		String value2Updated = m2.getValue() + m2.getValue();
		m2.setValue(value2Updated);
		
		List<Message> updateMessageList = new ArrayList<Message>();
		updateMessageList.add(m1);
		updateMessageList.add(m2);
		
		ResultStream<Message> stream = new ResultStream<Message>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void accept(Message m) {
				System.out.println("m.url=" + m.getUrl() + " processed!");
			}
			@Override
			public void complete(){
				System.out.println("processing complete!");
			}
		};
		
		_service.put(updateMessageList, stream);
		Thread.sleep(5000);
		
		List<Message> updated = _service.get();
		System.out.println(updated);
		Message m1Updated = getByValue(updated, value1Updated);
		assertTrue(m1Updated.getUrl().equals(url1));
		Message m2Updated = getByValue(updated, value2Updated);
		assertTrue(m2Updated.getUrl().equals(url2));
		
	}

	private static Message getByValue(List<Message> messages, String v1) {
		Optional<Message> first = messages.stream().filter(m -> v1.equals(m.getValue())).findFirst();
		assertTrue(first.isPresent());
		return first.get();
	}

}
