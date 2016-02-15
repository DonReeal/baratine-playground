package eu.dons.pollbus.polldef.entity;

import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Choice extends AbstractImmutableValue<Choice> {
    
    private final int id;
    
    @Size(min=1, max=255)
    private final String name;
    
    private final int sortKey;
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSortKey() {
        return sortKey;
    }
    
    public Choice(int id, String name, int sortKey) {
        this.id = id;
        this.name = name;
        this.sortKey = sortKey;
    }

    @Override
    protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, Choice other) {
        v.visit(this.id, other.id);        
    }

}
