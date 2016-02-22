package eu.dons.pollbus.base.data;

public interface Entity<ID> extends Data {
	
	ID identity();
	boolean contentEquals(Object other);
	
}
