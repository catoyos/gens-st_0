package pattern_search;

import java.util.LinkedList;
import java.util.List;

import model.Storable;

public class Pattern<T extends Storable> {
	private List<Parameter> params;
	private List<String> roles;
	
	public Pattern(List<Parameter> params, List<String> roles) {
		this.params = params;
		this.roles = roles;
	}

	public Result<T> eval(T object){
		Result<T> res = new Result<T> (this, object);
		boolean pos = true;
		for (Parameter parameter : params) {
			pos &= parameter.eval(object);
		}
		res.setPositive(pos);
		return res;
	}
	
	public List<Result<T>> eval(List<T> objects){
		List<Result<T>> res = new LinkedList<Result<T>> ();
		Result<T> aux = null;
		for (T obj : objects) {
			aux = eval(obj);
			if (aux.isPositive()) {
				res.add(aux);
			}
		}
		return res;
	}

	public List<Parameter> getParams() {
		return params;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean addParameter(Parameter e) {
		return params.add(e);
	}

	public boolean hasParameters() {
		return !params.isEmpty();
	}

	public Parameter removeParameter(int index) {
		return params.remove(index);
	}

	public int getNParameters() {
		return params.size();
	}

	public boolean addRole(String e) {
		return roles.add(e);
	}

	public boolean hasRoles() {
		return !roles.isEmpty();
	}

	public String removeRole(int index) {
		return roles.remove(index);
	}

	public int getNRoles() {
		return roles.size();
	}
	
	
}
