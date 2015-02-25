package pattern_search;

import java.util.Hashtable;

import model.Storable;

public abstract class AbstractParameter {
	
	protected Hashtable<String, Storable> roles;
	
	public Hashtable<String, Storable> getRoles() {
		return roles;
	}

	public void setRoles(Hashtable<String, Storable> roles) {
		this.roles = roles;
	}

	public abstract boolean eval(Storable target);

	public abstract boolean isComplex();
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);
	
}
