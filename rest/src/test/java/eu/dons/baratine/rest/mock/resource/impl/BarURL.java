package eu.dons.baratine.rest.mock.resource.impl;

public class BarURL {
    
    private static final String SCHEMA_SEPARATOR  ="://";
    private static final String POD_SEPARATOR  = "/";
    private static final String STORAGE = "store";
    
    private final String schema;
    private final String pod;
    private final String sid;
    
    private String url;
    
    public BarURL(String schema, String pod, String sid) {
        this.schema = schema;
        this.pod = pod;
        this.sid = sid;
    }
    
    public final String val(){
        if(url == null)
            url = schema + SCHEMA_SEPARATOR + pod + POD_SEPARATOR + sid;
        return url;
    }
    
    public static BarURL toStorage(BarURL barURL) {
        return new BarURL(STORAGE, barURL.pod, barURL.sid);
    }
    
    public static String barURL(String schema, String pod, String sid) {
        return new BarURL(schema, pod, sid).val();
    }
    
    public static String barURL(String schema, String sid) {
        return new BarURL(schema, "", sid).val();
    }
}