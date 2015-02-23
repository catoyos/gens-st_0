package pattern_search;

import java.util.Hashtable;

import model.City;
import model.Individual;
import model.Language;
import model.Storable;
import model.Storable.StorableType;
import model.World;
import model.Zone;
import pattern_search.SimpleParameter.ParamField;

class ComplexValueObject extends ValueObject {
	private String role;
	private ParamField field;
	
	public ComplexValueObject(String role, ParamField field) {
		this.role = role;
		this.field = field;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ParamField getField() {
		return field;
	}

	public void setField(ParamField field) {
		this.field = field;
	}

	@Override
	public Object getValue(Hashtable<String, Storable> roles) {
		Storable st = roles.get(role);
		if (role == null) {
			return null;
		} else {
			switch (st.TYPE) {
			case WORLD:
				return getWorldValue((World) st);
			case ZONE:
				return getZoneValue((Zone) st);
			case LANGUAGE:
				return getLanguageValue((Language) st);
			case CITY:
				return getCityValue((City) st);
			case INDIVIDUAL:
				return getIndividualValue((Individual) st);
			default:
				return null;
			}
		}
	}

	private Object getWorldValue(World st) {
		// TODO
		switch (field) {
		case WORLD_NCITIZENS:
			break;
		case WORLD_NZONES:
			break;
		default:
			return null;
		}
		return null;
	}

	private Object getZoneValue(Zone st) {
		// TODO
		switch (field) {
		case ZONE_NCITIES:
			break;
		case ZONE_NCITIZENS:
			break;
		default:
			return null;
		}
		return null;
	}

	private Object getLanguageValue(Language st) {
		// TODO
		switch (field) {
		default:
			return null;
		}
		// return null;
	}

	private Object getCityValue(City st) {
		// TODO
		switch (field) {
		case CITY_NCITIZENS:
			break;
		default:
			return null;
		}
		return null;
	}

	private Object getIndividualValue(Individual st) {
		// TODO
		switch (field) {
		case IND_BEA:
			break;
		case IND_BIRTH:
			break;
		case IND_CHA:
			break;
		case IND_COM:
			break;
		case IND_CON:
			break;
		case IND_FER:
			break;
		case IND_HOR:
			break;
		case IND_INT:
			break;
		case IND_SPE:
			break;
		case IND_STR:
			break;
		case IND_WIS:
			break;
		default:
			return null;
		}
		return null;
	}
	
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexValueObject other = (ComplexValueObject) obj;
		if (field != other.field)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	
}

class SimpleValueObject extends ValueObject {
	private Object value;

	public SimpleValueObject(Object value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Object getValue(Hashtable<String, Storable> roles) {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleValueObject other = (SimpleValueObject) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}

abstract class ValueObject {
	public abstract Object getValue();
	public abstract Object getValue(Hashtable<String, Storable> roles);
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);
}

public class SimpleParameter extends AbstractParameter {

	public static enum ParamType {
		EQUALS, EQUAL_OR_GREATER_THAN, EQUAL_OR_LESSER_THAN, GREATER_THAN, LESSER_THAN, NOT_EQUALS
	};

	public static enum ParamField {
		WORLD_NZONES, WORLD_NCITIZENS,
		ZONE_NCITIES, ZONE_NCITIZENS,
		CITY_NCITIZENS,
		IND_BIRTH, IND_STR, IND_CON, IND_SPE,
		IND_INT, IND_WIS, IND_CHA, IND_BEA,
		IND_FER, IND_HOR, IND_COM
	};

	private StorableType stType;
	private ParamField paramField;
	private ParamType paramType;
	private ValueObject value;
	
	private Hashtable<String, Storable> roles;
	
	public SimpleParameter(StorableType stType, ParamField paramField,
			ParamType paramType, ValueObject value) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = value;
	}
	
	public SimpleParameter(StorableType stType, ParamField paramField,
			ParamType paramType, Object valueObject) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = new SimpleValueObject(valueObject);
	}
	
	public SimpleParameter(StorableType stType, ParamField paramField,
			ParamType paramType, String valueRole, ParamField valueField) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = new ComplexValueObject(valueRole, valueField);
	}
	
	public boolean eval(Storable target){
		if (target.TYPE != stType) {
			return false;
		}
		switch (stType) {
		case WORLD:
			return evalWorld((World) target);
		case ZONE:
			return evalZone((Zone) target);
		case LANGUAGE:
			return evalLanguage((Language) target);
		case CITY:
			return evalCity((City) target);
		case INDIVIDUAL:
			return evalIndividual((Individual) target);

		default:
			return false;
		}
	}

	public boolean evalWorld(World target) {
		switch (paramField) {
		case WORLD_NZONES:
			return evalNumValue(target.getNZones());
		case WORLD_NCITIZENS:
			return evalNumValue(target.getNCitizens());

		default:
			return false;
		}
	}

	public boolean evalZone(Zone target) {
		switch (paramField) {
		case ZONE_NCITIES:
			return evalNumValue(target.getNCities());
		case ZONE_NCITIZENS:
			return evalNumValue(target.getNCitizens());

		default:
			return false;
		}
	}

	public boolean evalLanguage(Language target) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean evalCity(City target) {
		switch (paramField) {
		case CITY_NCITIZENS:
			return evalNumValue(target.getNCitizens());

		default:
			return false;
		}
	}

	private boolean evalNumValue(int targetVal) {
		int val;
		try {
			if (value == null) {
				return false;
			} else if (roles == null) {
				val = (Integer) value.getValue();
			} else {
				val = (Integer) value.getValue(roles);
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		switch (paramType) {
		case EQUALS: return (targetVal == val);
		case EQUAL_OR_GREATER_THAN: return (targetVal >= val);
		case EQUAL_OR_LESSER_THAN: return (targetVal <= val);
		case GREATER_THAN: return (targetVal > val);
		case LESSER_THAN: return (targetVal < val);
		case NOT_EQUALS: return (targetVal != val);

		default: return false;
		}
	}

	public boolean evalIndividual(Individual target) {
		switch (paramField) {
		case IND_BIRTH:
			return evalNumValue(target.getBirthDate());
		case IND_STR:
			return evalNumValue(target.getStrength());
		case IND_CON:
			return evalNumValue(target.getConstitution());
		case IND_SPE:
			return evalNumValue(target.getSpeed());
		case IND_INT:
			return evalNumValue(target.getIntelligence());
		case IND_WIS:
			return evalNumValue(target.getWisdom());
		case IND_BEA:
			return evalNumValue(target.getBeauty());
		case IND_CHA:
			return evalNumValue(target.getCharisma());
		case IND_FER:
			return evalNumValue(target.getFertility());
		case IND_HOR:
			return evalNumValue(target.getHorniness());
		case IND_COM:
			return evalNumValue(target.getComformity());

		default:
			return false;
		}
	}

	private boolean evalNumValue(double targetVal) {
		double val;
		try {
			if (value == null) {
				return false;
			} else if (roles == null) {
				val = (Double) value.getValue();
			} else {
				val = (Double) value.getValue(roles);
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		switch (paramType) {
		case EQUALS: return (targetVal == val);
		case EQUAL_OR_GREATER_THAN: return (targetVal >= val);
		case EQUAL_OR_LESSER_THAN: return (targetVal <= val);
		case GREATER_THAN: return (targetVal > val);
		case LESSER_THAN: return (targetVal < val);
		case NOT_EQUALS: return (targetVal != val);

		default: return false;
		}
	}
	
	public Hashtable<String, Storable> getRoles() {
		return roles;
	}

	public void setRoles(Hashtable<String, Storable> roles) {
		this.roles = roles;
	}

	public void putRole(String key, Storable role) {
		this.roles.put(key, role);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paramField == null) ? 0 : paramField.hashCode());
		result = prime * result + ((paramType == null) ? 0 : paramType.hashCode());
		result = prime * result + ((stType == null) ? 0 : stType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleParameter other = (SimpleParameter) obj;
		if (paramField != other.paramField)
			return false;
		if (paramType != other.paramType)
			return false;
		if (stType != other.stType)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
