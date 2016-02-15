package eu.dons.baratine.rest.mock.endpoint.impl;

import io.baratine.core.ResultStream;
import io.baratine.core.Service;
import io.baratine.stream.ResultStreamBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.omg.CORBA.SystemException;

import com.caucho.junit.ConfigurationBaratine.Log;

import lombok.Builder;
import lombok.Singular;

// @Service("/lucene/lucene-reader")
@Builder
public class LuceneReaderMock implements ILuceneReader {
    
    @Singular
    private Map<String, List<String>> _indexedValues = new HashMap<>();
    
    /* (non-Javadoc)
     * @see eu.dons.baratine.rest.mock.endpoint.impl.ILuceneReader#search(java.lang.String, java.lang.String)
     */
    @Override
    public ResultStreamBuilder<String> search(String collection, String query) {
        throw new UnsupportedOperationException("well java lang limits API freedom for a bit - baratine hack");
    }
    
    public void search(String collection, String query, ResultStream<String> result) {
        if(collection.equals("all")) {
            throw new UnsupportedOperationException("WTF");
//            for(String col : _indexedValues.keySet()) {
//                List<String> collectionValues = _indexedValues.get(col);
//                if(collectionValues != null)
//                    collectionValues.forEach(result::accept);                
//            }
        }
        else {
            List<String> collectionValues = _indexedValues.get(collection);
            if(collectionValues != null)
                collectionValues.forEach(result::accept);
        }
        result.complete();
    }
    
}
