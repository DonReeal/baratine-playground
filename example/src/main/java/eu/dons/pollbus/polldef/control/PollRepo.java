package eu.dons.pollbus.polldef.control;

import io.baratine.core.Result;
import io.baratine.core.ServiceRef;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.pure4j.collections.PersistentList;

import eu.dons.baratine.persistence.NamedSequencesService;
import eu.dons.pollbus.polldef.boundary.IPollEndpoint;
import eu.dons.pollbus.polldef.entity.Choice;
import eu.dons.pollbus.polldef.entity.Poll;
import eu.dons.pollbus.polldef.entity.PollId;

// @Endpoint(@PartitionedBy({ tenantId }))
// --> @Service("pod://{tenantId}/polls")
public class PollRepo implements IPollEndpoint {

    private NamedSequencesService pollIdsPerCreator;

    private String tenantId = "";
    private static final String SID = "polls";
    
    private ServiceRef _self;

    public void setNamedSequencesService(NamedSequencesService seq) {
        this.pollIdsPerCreator = seq;
    }

    /* (non-Javadoc)
     * @see eu.dons.pollbus.polldef.module.PollEndpoint#createPoll(java.lang.String, java.lang.String, java.lang.String, java.util.List, io.baratine.core.Result)
     */
    @Override
    public void createPoll(    
            String creatorId,
            String name, String text, List<Choice> choices, Result<Poll> result) {        
        
        pollIdsPerCreator.nextValFor(creatorId, count -> {
            
            PollId id = new PollId(name, count);            
            PersistentList<Choice> persistentChoices = new  PersistentList<>(choices);
            Poll poll = new Poll(id, name, text, persistentChoices);
            
            validate(poll);
            result.complete(poll);
            
        });


    }

    private void validate(Poll poll) throws IllegalArgumentException {
        
        ValidatorFactory f = Validation.buildDefaultValidatorFactory();
        Validator validator = f.getValidator();
        Set<ConstraintViolation<Poll>> validationResults = validator.validate(poll);
        
        if(validationResults.isEmpty()) {
           return; 
        }
        else {
            String error = "";
            for(ConstraintViolation<Poll> v : validationResults) {
                error = error + "\n" + v.getMessage();
            }
            throw new IllegalArgumentException(String.format(error)); 
        }
    }

}
