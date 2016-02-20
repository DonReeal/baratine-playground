package eu.dons.pollbus.core.validation.test;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.collections.PersistentList;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Aggregate extends AbstractImmutableValue<Aggregate> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private final AggregateId aggregateId;

    @Size(min = 3, max = 255)
    private final String name;

    @Size(max=1_000)
    private final String text;

    @Valid
    private final PersistentList<Leaf> leafs;

    @Override
    protected void fields(Visitor v, Aggregate other) {
        v.visit(this.aggregateId, other.aggregateId);
    }

    public Aggregate(AggregateId pollId, String name, String text, PersistentList<Leaf> choices) {
        this.aggregateId = pollId;
        this.name = name;
        this.text = text;
        this.leafs = choices;
    }

    public AggregateId getPollId() {
        return aggregateId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<Leaf> getChoices() {
        return leafs;
    }
}
