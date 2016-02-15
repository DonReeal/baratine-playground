package eu.dons.baratine.persistence;

public interface AggregateRoot<GLOBALLY_UNIQUE_KEY_TYPE> {
    public GLOBALLY_UNIQUE_KEY_TYPE getId();
}
