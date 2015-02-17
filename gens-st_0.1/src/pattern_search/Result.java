package pattern_search;

import java.util.HashMap;
import java.util.List;

import model.Storable;

public class Result<T extends Storable> {

	private boolean positive;
	private final Pattern<T> pattern;
	private T res;
	private HashMap<String, Storable> roles;
	
	protected Result(Pattern<T> pattern, T res) {
		this.positive = false;
		this.pattern = pattern;
		this.res = res;
		this.roles = new HashMap<String, Storable>(2 * pattern.getNRoles());
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	public T getRes() {
		return res;
	}

	public void setRes(T res) {
		this.res = res;
	}

	public HashMap<String, Storable> getRoles() {
		return roles;
	}

	public void setRoles(HashMap<String, Storable> roles) {
		this.roles = roles;
	}

	public Pattern<T> getPattern() {
		return pattern;
	}

	public boolean containsRole(String key) {
		return roles.containsKey(key);
	}

	public boolean containsObject(Storable value) {
		return roles.containsValue(value);
	}

	public Storable getRoleObject(String key) {
		return roles.get(key);
	}

	public int getNRoles() {
		return roles.size();
	}
		
	public boolean containsAllPatternRoles(){
		if (roles.size() != pattern.getNRoles()) {
			return false;
		}
		List<String> ptRoles = pattern.getRoles();
		for (String role : ptRoles) {
			if (!roles.containsKey(role)) {
				return false;
			}
		}
		return true;
	}
}
