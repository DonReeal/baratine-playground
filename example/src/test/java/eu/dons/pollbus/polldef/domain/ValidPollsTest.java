package eu.dons.pollbus.polldef.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pure4j.collections.PersistentList;

import eu.dons.pollbus.polldef.entity.Choice;
import eu.dons.pollbus.polldef.entity.Poll;
import eu.dons.pollbus.polldef.entity.PollId;

public class ValidPollsTest {
    

    private static Validator validator;
    private Poll poll;
    
    // poll defaults:
    private static final String USERID_DEFAULT = "alice";
    private static final long POLLID_DEFAULT = 1;
    private static final String NAME_DEFAULT = "Valid Testing going on here?";
    private static final String TEXT_DEFAULT = "Summary: we want your feedback, whether you think this test will be useful in future ...";

    // choices defaults:
    private static final Choice CHOICE_A = new Choice(1, "A", 1);
    private static final Choice CHOICE_B = new Choice(2, "B", 2);
    private static final Choice CHOICE_C = new Choice(3, "A", 3);
    
    
    @Test
    public void pollNamesWithLessThan3CharactersShouldBeInvalid() {
        whenPollNameIs("");
        validationYieldsViolation("name", "size must be between");
    }
    
    
    public void pollNameUnsetIsValid() {
        whenPollNameIs(null);
        pollIsValid();
    }
    
    
    private void pollIsValid() {
        Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
        assertTrue("no Violation expected", violations.isEmpty());
        
    }


    @Test
    public void pollNamesLongerThan255ShouldBeInvalid() {
        whenPollNameIs(getStringOfLength(266));   
        validationYieldsViolation("name", "size must be between");
    }
    

    private String getStringOfLength(int i) {
        char[] ar = new char[i];
        for(int c = 0; c < ar.length; c++ ) {
            ar[c] = 'T';
        }
        return new String(ar);
    }


    private void whenPollNameIs(String name) {
        poll = new Poll(new PollId(USERID_DEFAULT, POLLID_DEFAULT), 
                name, 
                TEXT_DEFAULT, 
                new PersistentList<Choice>(CHOICE_A, CHOICE_B, CHOICE_C));
    }


    private void validationYieldsViolation(String violationPropertyPathExpected, String violationMessagePartExpected) {
        
        Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
        
        for (ConstraintViolation<Poll> v : violations) {
            if(violationPropertyPathExpected.equals(v.getPropertyPath().toString())
                && v.getMessage().contains(violationMessagePartExpected)) {
                
                return;
            }
        }
        fail(String.format("expected Violation { path: %s, message: %s }", 
                violationPropertyPathExpected, violationMessagePartExpected));
    }


    
    @BeforeClass
    public static void setUp() {
        ValidatorFactory f = Validation.buildDefaultValidatorFactory();
        validator = f.getValidator();        
        
        defaultValuedPollIsValid();
    }

    
    private static void defaultValuedPollIsValid() {
        Poll p = new Poll(new PollId(USERID_DEFAULT,POLLID_DEFAULT), NAME_DEFAULT, TEXT_DEFAULT, new PersistentList<Choice>());
        Set<ConstraintViolation<Poll>> vvv = validator.validate(p);
        
        if(!vvv.isEmpty()) {    
            vvv.forEach(ValidPollsTest::printViolation);
            throw new IllegalStateException("This test needs refactoring!");
        }
    }

    private static <T> void printViolation(ConstraintViolation<T> v){
        System.out.println(
                String.format("Invalid Bean-Property: %s#%s. Message was: \"%s\" actual value was: \"%s\".", 
                        v.getRootBean().getClass().getName(), v.getPropertyPath(), v.getMessage(), v.getInvalidValue().toString()));
    }
    
}
