package pattern_search;

import model.City;
import model.Individual;
import model.Language;
import model.Storable;
import model.Storable.StorableType;
import model.World;
import model.Zone;

public class SimpleParameter extends AbstractParameter {

	public static enum ParamType {
		EQUALS, EQUAL_OR_GREATER_THAN, EQUAL_OR_LESSER_THAN, GREATER_THAN, LESSER_THAN, NOT_EQUALS
	};

	public static enum ParamField {
		WORLD_NZONES, WORLD_NCITIZENS,
		ZONE_NCITIES, ZONE_NCITIZENS,
		CITY_NCITIZENS,
		IND_BIRTH, IND_STR, IND_CON, IND_SPE, IND_INT, IND_WIS, IND_CHA, IND_BEA, IND_FER, IND_HOR, IND_COM
	};

	private StorableType stType;
	private ParamField paramField;
	private ParamType paramType;
	private Object value;//TODO value = [VALOR DE UNA CARACTERISTICA DE UN OBJETO]
	
	public SimpleParameter(StorableType stType, ParamField paramField,
			ParamType paramType, Object value) {
		this.stType = stType;
		this.paramType = paramType;
		this.paramField = paramField;
		this.value = value;
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

	public boolean evalCity(City target) {
		switch (paramField) {
		case CITY_NCITIZENS:
			return evalNumValue(target.getNCitizens());

		default:
			return false;
		}
	}

	public boolean evalLanguage(Language target) {
		// TODO Auto-generated method stub
		return false;
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


	private boolean evalNumValue(int targetVal) {
		int val;
		try {
			val = (Integer) value;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		switch (paramType) {
		case EQUALS: return (targetVal==val);
		case EQUAL_OR_GREATER_THAN: return (targetVal>=val);
		case EQUAL_OR_LESSER_THAN: return (targetVal<=val);
		case GREATER_THAN: return (targetVal>val);
		case LESSER_THAN: return (targetVal<val);
		case NOT_EQUALS: return (targetVal!=val);

		default: return false;
		}
	}

	private boolean evalNumValue(double targetVal) {
		double val;
		try {
			val = (Double) value;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		switch (paramType) {
		case EQUALS: return (targetVal==val);
		case EQUAL_OR_GREATER_THAN: return (targetVal>=val);
		case EQUAL_OR_LESSER_THAN: return (targetVal<=val);
		case GREATER_THAN: return (targetVal>val);
		case LESSER_THAN: return (targetVal<val);
		case NOT_EQUALS: return (targetVal!=val);

		default: return false;
		}
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
