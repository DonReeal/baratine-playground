package eu.dons.baratine.persistence;

public class Module {
    
    public static Sequence createSequence(StoredVal<Long> storage) {
        SequenceServiceImpl impl = new SequenceServiceImpl();
        impl.setStore(storage);
        return impl;
    }

}
