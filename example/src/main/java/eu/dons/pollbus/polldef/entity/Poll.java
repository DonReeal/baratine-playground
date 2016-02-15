package eu.dons.pollbus.polldef.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.collections.PersistentList;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Poll extends AbstractImmutableValue<Poll> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final PollId pollId;

    @Size(min = 3, max = 255)
    private final String name;

    @Size(max=1_000)
    private final String text;

    private final PersistentList<Choice> choices;

    @Override
    protected void fields(Visitor v, Poll other) {
        v.visit(this.pollId, other.pollId);
    }

    public Poll(PollId pollId, String name, String text, PersistentList<Choice> choices) {
        this.pollId = pollId;
        this.name = name;
        this.text = text;
        this.choices = choices;
    }

    public PollId getPollId() {
        return pollId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
