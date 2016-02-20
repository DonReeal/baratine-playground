package eu.dons.pollbus.core.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.pure4j.collections.PersistentList;

import com.caucho.junit.RunnerBaratine;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.core.validation.test.Aggregate;
import eu.dons.pollbus.core.validation.test.AggregateId;
import eu.dons.pollbus.core.validation.test.Leaf;
import io.baratine.core.ServiceExceptionExecution;
import io.baratine.core.ServiceManager;

@RunWith(RunnerBaratine.class)
public class ValidationTest {
    
    private static Validator validatorImplementation;    
    @Inject ServiceManager _sm;
    private BeanValidatorSync validatorService;
    @Rule public ExpectedException expectedException = ExpectedException.none();
    
    
    @BeforeClass public static void setUp() {
        ValidatorFactory f = Validation.buildDefaultValidatorFactory();
        validatorImplementation = f.getValidator(); 
    }
    
    @Before
    public void initImpl() {
        BeanValidator v = new BeanValidator();
        v.setValidatorImplementation(validatorImplementation);
        validatorService = _sm.newService().service(v).build().as(BeanValidatorSync.class);        
    }
    
    
    @Test
    public void test() throws AppException {
    	
        AggregateId aggregateId = new AggregateId("");
        PersistentList<Leaf> leafs = new PersistentList<Leaf>(
                new Leaf(1, "Leaf#1", -1),
                new Leaf(2, "Leaf#2", 2));        
        Aggregate a = new Aggregate(aggregateId, 
        		"a", // too short name -> exception
        		"this is a test aggregate", leafs);
        
        expectedException.expect(new MyExceptionMatcher("size must be between", "must be greater than"));    
        validatorService.validate(a);
    }
    
    static final class MyExceptionMatcher extends BaseMatcher<AppException> {
        public MyExceptionMatcher(String ... expectedSubStringsContained) {
            super();
            this.expectedSubStringsContained = Arrays.asList(expectedSubStringsContained);
        }
        
        final List<String> expectedSubStringsContained;
        
        @Override
        public boolean matches(Object item) {   
        	if(item instanceof ServiceExceptionExecution) {
        		return textMatchesExpectation((AppException) ((Exception) item).getCause());
        	}
        	return false;             
        }

        private boolean textMatchesExpectation(AppException iae) {
            String message = iae.getMessage();
            System.out.println(message);
            Map<String, Boolean> res = expectedSubStringsContained.stream()
                .collect(Collectors.toMap(
                        s -> s,
                        s -> message.contains(s)));
            
            if(res.values().contains(Boolean.FALSE)) {
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValueList("Expected to contain the following text parts:\n", ",", "!", expectedSubStringsContained);            
        }
        
    }

}
