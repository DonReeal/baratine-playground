package eu.dons.pollbus.polldef.impl;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
class Poll {
    
    private final String pollId;
    @Setter(AccessLevel.PACKAGE)
    private String name;
    @Setter(AccessLevel.PACKAGE)
    private String text;
    @Setter(AccessLevel.PACKAGE)
    private List<Answer> answers;
    
}
