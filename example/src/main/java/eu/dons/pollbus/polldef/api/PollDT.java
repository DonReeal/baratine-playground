package eu.dons.pollbus.polldef.api;

import java.util.List;

import lombok.Data;

@Data
public class PollDT {
    
    private final String pollId;
    private String name;
    private String text;
    private List<AnswerDT> answers;

}
