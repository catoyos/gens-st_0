package pattern_search;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import model.City;
import model.Individual;
import model.Language;
import model.Storable;
import model.Storable.StorableType;
import model.World;
import model.Zone;

public class Pattern<T extends Storable> {
	//TODO change to StorableField
	public enum PatternContainsAs {
		WORLD_CHILD_ZONE, WORLD_CITIZEN,
		ZONE_PARENT_WORLD, ZONE_CHILD_CITY, ZONE_CITIZEN,
		CITY_PARENT_ZONE, CITY_ADJ_CITY, CITY_CITIZEN,
		IND_PARENT, IND_FATHER, IND_MOTHER, IND_PARTNER, IND_CHILD,
		IND_GRANDPARENT, IND_GRANDCHILD, IND_SIBLING, 
		IND_ORIGINAL_ZONE, IND_CURRENT_ZONE, IND_CURRENT_CITY
		};

	private final StorableType type;
	private PatternContainsAs as;
	private String mainRole;
	private Hashtable<String, Storable> roles;
	private List<AbstractParameter> paramsS;
	private List<AbstractParameter> paramsC;
	private List<Pattern<? extends Storable>> contains;

	public Pattern(String role, StorableType type) {
		this(null, role, type, null, null);
	}
	
	public Pattern(PatternContainsAs as, String role, StorableType type) {
		this(as, role, type, null, null);
	}
	
	public Pattern(String role, StorableType type, List<AbstractParameter> params) {
		this(null, role, type, params, null);
	}
	
	public Pattern(PatternContainsAs as, String role, StorableType type, List<AbstractParameter> params) {
		this(as, role, type, params, null);
	}
	
	public Pattern(String role, StorableType type, List<AbstractParameter> params, List<Pattern<? extends Storable>> contains) {
		this(null, role, type, params, contains);
	}
	
	public Pattern(PatternContainsAs as, String role, StorableType type, List<AbstractParameter> params, List<Pattern<? extends Storable>> contains) {
		this.as = as;
		this.mainRole = role;
		this.type = type;
		this.paramsS = new LinkedList<AbstractParameter>();
		this.paramsC = new LinkedList<AbstractParameter>();
		this.roles = new Hashtable<String, Storable>();
		
		if (params != null) {
			for (AbstractParameter parameter : params) {
				if (parameter != null) {
					if (parameter.isComplex()) {
						this.paramsC.add(parameter);
					} else {
						this.paramsS.add(parameter);
					}
				}
			}
		}
		
		this.contains = new LinkedList<Pattern<? extends Storable>>();
		if (contains != null) {
			for (Pattern<? extends Storable> pattern : contains) {
				if (pattern != null) {
					this.contains.add(pattern);
				}
			}
		}
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

	@SuppressWarnings("unchecked")
	public Result<T> eval(T object){
		Result<T> res = new Result<T> (object, mainRole, roles);
		if (object == null) return res;
		boolean pos = true;
		for (AbstractParameter parameter : paramsS) {
			pos &= parameter.eval(object);
		}
		
		if (pos) {
			List<? extends Result<? extends Storable>> containsResults = null;
			for (Pattern<? extends Storable> member : contains) {
				member.putAllRoles(res.getRoles());
				switch (member.type) {
				case WORLD:
					containsResults = evalChildWorldPattern(object, (Pattern<World>) member);
					break;
				case ZONE:
					containsResults = evalChildZonePattern(object, (Pattern<Zone>) member);
					break;
				case LANGUAGE:
					containsResults = evalChildLanguagePattern(object, (Pattern<Language>) member);
					break;
				case CITY:
					containsResults = evalChildCityPattern(object, (Pattern<City>) member);
					break;
				case INDIVIDUAL:
					containsResults = evalChildIndividualPattern(object, (Pattern<Individual>) member);
					break;
				default:
					containsResults = null;
					break;
				}
				
				pos &= (containsResults != null && !containsResults.isEmpty());
			}
		}
		
		if (pos) {
			for (AbstractParameter parameter : paramsC) {
				parameter.setRoles(res.getRoles());
				pos &= parameter.eval(object);
			}
		}
		
		res.setPositive(pos);
		return res;
	}

	private List<Result<World>> evalChildWorldPattern(T object, Pattern<World> member) {
		List<Result<World>> res = null;
		List<World> aux = new LinkedList<World>();
		
		switch (member.as) {
		case ZONE_PARENT_WORLD:
			if (object.TYPE != StorableType.ZONE || this.type != StorableType.ZONE) return null;
			aux.add(((Zone) object).getParentWorld());
			break;
		default: return null;
		}

		res = member.eval(aux);
		return res;
	}

	private List<Result<Zone>> evalChildZonePattern(T object, Pattern<Zone> member) {
		List<Result<Zone>> res = null;
		List<Zone> aux = new LinkedList<Zone>();
		
		switch (member.as) {
		case WORLD_CHILD_ZONE:
			if (object.TYPE != StorableType.WORLD || this.type != StorableType.WORLD) return null;
			aux.addAll(((World) object).getZones());
			break;
		case CITY_PARENT_ZONE:
			if (object.TYPE != StorableType.CITY || this.type != StorableType.CITY) return null;
			aux.add(((City) object).getParentZone());
			break;
		case IND_CURRENT_ZONE:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			aux.add(((Individual) object).getCurrentZone());
			break;
		case IND_ORIGINAL_ZONE:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			aux.add(((Individual) object).getOriginalZone());
			break;
		default: return null;
		}
		
		res = member.eval(aux);
		return res;
	}

	private List<Result<Language>> evalChildLanguagePattern(T object, Pattern<Language> member) {
		List<Result<Language>> res = null;
		List<Language> aux = null;

		res = member.eval(aux);
		return res;
	}

	private List<Result<City>> evalChildCityPattern(T object, Pattern<City> member) {
		List<Result<City>> res = null;
		List<City> aux = new LinkedList<City>();
		
		switch (member.as) {
		case ZONE_CHILD_CITY:
			if (object.TYPE != StorableType.ZONE || this.type != StorableType.ZONE) return null;
			aux.addAll(((Zone) object).getCities());
			break;
		case CITY_ADJ_CITY:
			if (object.TYPE != StorableType.CITY || this.type != StorableType.CITY) return null;
			//TODO CITY_ADJ_CITY:
			break;
		case IND_CURRENT_CITY:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			aux.add(((Individual) object).getCurrentCity());
			break;
		default: return null;
		}

		res = member.eval(aux);
		return res;
	}

	private List<Result<Individual>> evalChildIndividualPattern(T object, Pattern<Individual> member) {
		List<Result<Individual>> res = null;
		List<Individual> aux = new LinkedList<Individual>();
		
		switch (member.as) {
		case WORLD_CITIZEN:
			if (object.TYPE != StorableType.WORLD || this.type != StorableType.WORLD) return null;
			//TODO WORLD_CITIZEN:
			break;
		case ZONE_CITIZEN:
			if (object.TYPE != StorableType.ZONE || this.type != StorableType.ZONE) return null;
			//TODO ZONE_CITIZEN:
			break;
		case CITY_CITIZEN:
			if (object.TYPE != StorableType.CITY || this.type != StorableType.CITY) return null;
			aux.addAll(((City) object).getCitizenInd());
			break;
		case IND_CHILD:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			aux.addAll(((Individual) object).getChildren());
			break;
		case IND_FATHER:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_FATHER:
			break;
		case IND_GRANDCHILD:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_GRANDCHILD:
			break;
		case IND_GRANDPARENT:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_GRANDPARENT:
			break;
		case IND_MOTHER:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_MOTHER:
			break;
		case IND_PARENT:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_PARENT:
			break;
		case IND_PARTNER:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			aux.add(((Individual) object).getPartner());
			break;
		case IND_SIBLING:
			if (object.TYPE != StorableType.INDIVIDUAL || this.type != StorableType.INDIVIDUAL) return null;
			//TODO IND_SIBLING:
			break;
		default: return null;
		}

		res = member.eval(aux);
		return res;
	}

	public PatternContainsAs getAs() {
		return as;
	}

	public String getMainRole() {
		return mainRole;
	}

	public StorableType getType() {
		return type;
	}

	public Hashtable<String, Storable> getRoles() {
		return roles;
	}

	public void putRole(String role, Storable object) {
		this.roles.put(role, object);
	}

	public void putAllRoles(Hashtable<String, Storable> roles) {
		this.roles.putAll(roles);
	}
	
	
}
