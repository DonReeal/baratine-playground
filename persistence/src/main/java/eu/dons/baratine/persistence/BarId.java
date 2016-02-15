package eu.dons.baratine.persistence;

import java.io.Serializable;

public class BarId implements Serializable {

    private static final long serialVersionUID = 421567862337483818L;
    
    public static enum Schema {
       
        LOCAL("local"),
        POD("pod"),
        PUBLIC("public");
        
        private String value;
        private Schema(String value) {
            this.value = value;
        }
        
        @Override
        public String toString(){
            return value;
        }
        
        
    }

    private static final String SCHEMA_SEPARATOR = "://";
    private static final String POD_SEPARATOR = "/";
    private static final String STORAGE = "store";

    private final String schema;
    private final String pod;
    private final String sid;

    private String url;

    public BarId(String schema, String pod, String sid) {
        this.schema = schema;
        this.pod = pod;
        this.sid = sid;
    }

    public final String schema() {
        return this.schema;
    }

    public final String pod() {
        return this.pod;
    }

    public final String serviceId() {
        return this.sid;
    }

    public final String fqURL() {
        if (url == null)
            url = schema + SCHEMA_SEPARATOR + pod + POD_SEPARATOR + sid;
        return url;
    }

    public static BarId toStorage(BarId barURL) {
        return new BarId(STORAGE, barURL.pod, barURL.sid);
    }

    public static String barURL(String schema, String pod, String sid) {
        return new BarId(schema, pod, sid).fqURL();
    }

    public static String barURL(String schema, String sid) {
        return new BarId(schema, "", sid).fqURL();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private BarId barId;

        // ========================================================
        public SchemaBuilt schema(String schema) {
            
            if(schema == null || schema.isEmpty()) {
                schema = Schema.LOCAL.toString();
            }
            
            this.barId = new BarId(schema, null, null);
            return new SchemaBuilt(this);
        }

        class ABuildStep {

            final Builder builder;

            ABuildStep(Builder root) {
                this.builder = root;
            }
        }

        // ========================================================
        public class SchemaBuilt extends ABuildStep {

            SchemaBuilt(Builder root) {
                super(root);
            }

            public PODBuilt pod(String pod) {
                BarId currentBarId = this.builder.barId;
                this.builder.barId = new BarId(currentBarId.schema, pod, null);
                return new PODBuilt(this.builder);
            }
        }

        // ========================================================
        public class PODBuilt extends ABuildStep {

            PODBuilt(Builder root) {
                super(root);
            }

            public SIDBuilt sid(String sid) {

                BarId currentId = this.builder.barId;
                this.builder.barId = new BarId(currentId.schema, currentId.schema, sid);
                return new SIDBuilt(this.builder);

            }
        }

        public class SIDBuilt extends ABuildStep {
            SIDBuilt(Builder root) {
                super(root);
            }

            public BarId build() {
                return this.builder.barId;
            }
        }

    }

}