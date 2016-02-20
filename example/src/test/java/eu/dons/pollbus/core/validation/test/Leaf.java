package eu.dons.pollbus.core.validation.test;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.pure4j.annotations.immutable.ImmutableValue;
import org.pure4j.immutable.AbstractImmutableValue;

@ImmutableValue
public class Leaf extends AbstractImmutableValue<Leaf> {
    
    private final int id;
    
    @Size(min=1, max=255)
    private final String name;
    
    @Min(value=0)
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
    
    public Leaf(int id, String name, int sortKey) {
        this.id = id;
        this.name = name;
        this.sortKey = sortKey;
    }

    @Override
    protected void fields(org.pure4j.immutable.AbstractImmutableValue.Visitor v, Leaf other) {
        v.visit(this.id, other.id);        
    }

}
