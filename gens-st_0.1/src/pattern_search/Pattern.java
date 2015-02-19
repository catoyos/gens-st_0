package pattern_search;

import java.util.LinkedList;
import java.util.List;

import model.Storable;

public class Pattern<T extends Storable> {
	public enum PatternContainsAs {
		WORLD_CHILD_ZONE, WORLD_CITIZEN,
		ZONE_PARENT_WORLD, ZONE_CHILD_CITY, ZONE_CITIZEN,
		CITY_PARENT_ZONE, CITY_ADJ_CITY, CITY_CITIZEN,
		IND_PARENT, IND_FATHER, IND_MOTHER, IND_PARTNER, IND_CHILD,
		IND_GRANDPARENT, IND_GRANDCHILD, IND_SIBLING, 
		IND_ORIGINAL_ZONE, IND_CURRENT_ZONE, IND_CURRENT_CITY
		};
	
	private PatternContainsAs as;
	private String role;
	private List<AbstractParameter> params;
	@SuppressWarnings("rawtypes")
	private List<Pattern> contains;

	public Pattern(String role) {
		this(null, role, null, null);
	}
	
	public Pattern(PatternContainsAs as, String role) {
		this(as, role, null, null);
	}
	
	public Pattern(String role, List<AbstractParameter> params) {
		this(null, role, params, null);
	}
	
	@SuppressWarnings("rawtypes")
	public Pattern(String role, List<AbstractParameter> params, List<Pattern> contains) {
		this(null, role, params, contains);
	}
	
	@SuppressWarnings("rawtypes")
	public Pattern(PatternContainsAs as, String role, List<AbstractParameter> params, List<Pattern> contains) {
		this.as = as;
		this.role = role;
		this.params = new LinkedList<AbstractParameter>();
		if (params != null) {
			for (AbstractParameter parameter : params) {
				if (parameter != null) {
					this.params.add(parameter);
				}
			}
		}
		this.contains = new LinkedList<Pattern>();
		if (contains != null) {
			for (Pattern pattern : contains) {
				if (pattern != null) {
					this.contains.add(pattern);
				}
			}
		}
	}

	public Result<T> eval(T object){
		Result<T> res = new Result<T> (object, role);
		boolean pos = true;
		for (AbstractParameter abstractParameter : params) {
			pos &= abstractParameter.eval(object);
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

	public PatternContainsAs getAs() {
		return as;
	}

	public String getRole() {
		return role;
	}
	
	
}
