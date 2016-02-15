package eu.dons.baratine.persistence;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.dons.baratine.persistence.BarId.Builder;

public class WhenOmittingValuesOnBuildingBarIds {

    private BarId barId;
    
    @Test
    public void defaultValuesForSchema() {
        
        buildingSchemaWith(null);
            schemaShouldBeSetTo("local");
            fqnShouldStartWithLocal("local:");
        
        buildingSchemaWith("");
            schemaShouldBeSetTo("local");
            fqnShouldStartWithLocal("local:");
    }

    private void schemaShouldBeSetTo(String expectedValueForSchema) {
        assertTrue(barId.schema() != null);
        assertTrue(expectedValueForSchema.equals(barId.schema()));
    }

    private void fqnShouldStartWithLocal(String expectedPrefix) {
        assertTrue(barId.fqURL().startsWith(expectedPrefix));
    }


    private void buildingSchemaWith(String schemaValue) {
        barId = BarId.builder().schema(schemaValue).pod("pod").sid("sid").build();
        
    }

}
