package pattern_search;

import java.util.Hashtable;

import model.City;
import model.Individual;
import model.Language;
import model.Storable;
import model.Storable.StorableType;
import model.World;
import model.Zone;
import pattern_search.StorableFieldManager.StorableField;

class ComplexValueObject extends ValueObject {
	private String role;
	private StorableField field;
	
	public ComplexValueObject(String role, StorableField field) {
		super(true);
		this.role = role;
		this.field = field;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public StorableField getField() {
		return field;
	}

	public void setField(StorableField field) {
		this.field = field;
	}

	@Override
	public Object getValue(Hashtable<String, Storable> roles) {
		if (role == null) {
			return null;
		} else if (roles == null) {
			return null;
		} else {
			Storable st = roles.get(role);
			switch (st.TYPE) {
			case WORLD:
				return StorableFieldManager.getFieldValue((World) st, field);
			case ZONE:
				return StorableFieldManager.getFieldValue((Zone) st, field);
			case LANGUAGE:
				return StorableFieldManager.getFieldValue((Language) st, field);
			case CITY:
				return StorableFieldManager.getFieldValue((City) st, field);
			case INDIVIDUAL:
				return StorableFieldManager.getFieldValue((Individual) st, field);
			default:
				return null;
			}
		}
	}

	@Override
	public Object getValue() {
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
		super(false);
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
	final boolean complex;
	ValueObject(boolean complex) {
		this.complex = complex;
	}
	public abstract Object getValue();
	public abstract Object getValue(Hashtable<String, Storable> roles);
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);
}

public class SimpleParameter extends AbstractParameter {

	public static enum ParamType {
		EQUALS, EQUAL_OR_GREATER_THAN, EQUAL_OR_LESSER_THAN, GREATER_THAN, LESSER_THAN, NOT_EQUALS,
		EQUALS_IGNORE_CASE, NOT_EQUALS_IGNORE_CASE, STARTS_WITH, ENDS_WITH, MATCHES, IS_MATCHED
	};

	private StorableType stType;
	private StorableField paramField;
	private ParamType paramType;
	private ValueObject value;
	
	public SimpleParameter(StorableType stType, StorableField paramField,
			ParamType paramType, ValueObject value) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = value;
	}
	
	public SimpleParameter(StorableType stType, StorableField paramField,
			ParamType paramType, Object valueObject) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = new SimpleValueObject(valueObject);
	}
	
	public SimpleParameter(StorableType stType, StorableField paramField,
			ParamType paramType, String valueRole, StorableField valueField) {
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

	@Override
	public boolean isComplex() {
		return value.complex;
	}

	public boolean evalWorld(World target) {
		Object tgvalobj = StorableFieldManager.getFieldValue(target, paramField);
		if (tgvalobj != null) {
			if (paramField == StorableField.WORLD_NZONES ||
					paramField == StorableField.WORLD_NCITIZENS) {
				try {
					int tgval = (Integer) tgvalobj;
					return evalNumValue(tgval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean evalZone(Zone target) {
		Object tgvalobj = StorableFieldManager.getFieldValue(target, paramField);
		if (tgvalobj != null) {
			if (paramField == StorableField.ZONE_NCITIES ||
					paramField == StorableField.ZONE_NCITIZENS) {
				try {
					int tgval = (Integer) tgvalobj;
					return evalNumValue(tgval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean evalLanguage(Language target) {
		return false;
	}

	public boolean evalCity(City target) {
		Object tgvalobj = StorableFieldManager.getFieldValue(target, paramField);
		if (tgvalobj != null) {
			if (paramField == StorableField.CITY_NCITIZENS) {
				try {
					int tgval = (Integer) tgvalobj;
					return evalNumValue(tgval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean evalIndividual(Individual target) {
		Object tgvalobj = StorableFieldManager.getFieldValue(target, paramField);
		if (tgvalobj != null) {
			if (paramField == StorableField.IND_BIRTH || paramField == StorableField.IND_DEATH) {
				try {
					float tgval = (Float) tgvalobj;
					return evalNumValue(tgval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (paramField == StorableField.IND_STR || paramField == StorableField.IND_CON
					|| paramField == StorableField.IND_SPE || paramField == StorableField.IND_INT
					|| paramField == StorableField.IND_WIS || paramField == StorableField.IND_BEA
					|| paramField == StorableField.IND_CHA || paramField == StorableField.IND_FER
					|| paramField == StorableField.IND_HOR || paramField == StorableField.IND_COM) {
				try {
					int tgval = (Integer) tgvalobj;
					return evalNumValue(tgval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean evalNumValue(int targetVal) {
		Object obj = null;
		int val;
		try {
			if (value == null) {
				return true;
			} else if (roles == null) {
				obj = value.getValue();
			} else {
				obj = value.getValue(roles);
			}
			if (obj == null) return true;
			val = (Integer) obj;
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

	private boolean evalNumValue(float targetVal) {
		Object obj = null;
		float val;
		try {
			if (value == null) {
				return true;
			} else if (roles == null) {
				obj = value.getValue();
			} else {
				obj = value.getValue(roles);
			}
			if (obj == null) return true;
			val = (Float) obj;
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

	@SuppressWarnings("unused")//TODO
	private boolean evalStringValue(String targetVal) {
		if (targetVal == null) return false;
		Object obj = null;
		String val;
		try {
			if (value == null) {
				return true;
			} else if (roles == null) {
				obj = value.getValue();
			} else {
				obj = value.getValue(roles);
			}
			if (obj == null) return true;
			val = obj.toString();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		switch (paramType) {
		case EQUALS: return (targetVal.equals(val));
		case EQUALS_IGNORE_CASE: return (targetVal.equalsIgnoreCase(val));
		case NOT_EQUALS: return (!targetVal.equals(val));
		case NOT_EQUALS_IGNORE_CASE: return (!targetVal.equalsIgnoreCase(val));
		case STARTS_WITH: return (targetVal.startsWith(val));
		case ENDS_WITH: return (targetVal.endsWith(val));
		case MATCHES: return (targetVal.matches(val));
		case IS_MATCHED: return (val.matches(targetVal));
		
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
