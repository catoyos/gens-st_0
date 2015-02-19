package pattern_search;

import model.Storable;

public abstract class AbstractParameter {
	
	public abstract boolean eval(Storable target);
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);
	
}
