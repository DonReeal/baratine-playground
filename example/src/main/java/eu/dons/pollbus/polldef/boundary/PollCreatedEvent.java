package eu.dons.pollbus.polldef.boundary;

import eu.dons.pollbus.polldef.entity.Poll;
import lombok.Data;

@Data
public class PollCreatedEvent {
    
    private String creatingUserId;
    private Poll poll;

}
