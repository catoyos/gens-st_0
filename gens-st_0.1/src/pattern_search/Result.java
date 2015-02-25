package pattern_search;

import java.util.Hashtable;

import model.Storable;

public class Result<T extends Storable> {

	private boolean positive;
	private T res;
	private Hashtable<String, Storable> roles;
	
	protected Result(T res, String role) {
		this(res, role, new Hashtable<String, Storable>());
	}
	
	protected Result(T res, String role, Hashtable<String, Storable> roles) {
		this.positive = false;
		this.res = res;
		this.roles = new Hashtable<String, Storable>(roles);
		this.roles.put(role, res);
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

	public Hashtable<String, Storable> getRoles() {
		return roles;
	}

	public void setRoles(Hashtable<String, Storable> roles) {
		this.roles = roles;
	}

	public void putRole(String role, Storable object) {
		this.roles.put(role, object);
	}

	public void putAllRoles(Hashtable<String, Storable> roles) {
		this.roles.putAll(roles);
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
}
