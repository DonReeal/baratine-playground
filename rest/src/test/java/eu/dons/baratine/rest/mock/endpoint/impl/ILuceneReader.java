package eu.dons.baratine.rest.mock.endpoint.impl;

import io.baratine.stream.ResultStreamBuilder;

public interface ILuceneReader {

    public abstract ResultStreamBuilder<String> search(String collection, String query);

}