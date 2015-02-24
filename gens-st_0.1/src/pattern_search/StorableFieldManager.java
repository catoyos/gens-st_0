package pattern_search;

import model.City;
import model.Individual;
import model.Language;
import model.Storable;
import model.World;
import model.Zone;
import model.Individual.Gender;

public class StorableFieldManager {
/*

WORLD_ID	String
WORLD_NZONES	int
WORLD_NCITIZENS	int
WORLD_CHILD_ZONES	List<Zone>
WORLD_CITIZENS	List<Individual>
WORLD_CHILD_ZONES_ID	List<String>
WORLD_CITIZENS_ID	List<String>
WORLD_MOMENT	float

ZONE_ID	String
ZONE_NCITIES	int
ZONE_NCITIZENS	int
ZONE_PARENT_WORLD	World
ZONE_CHILD_CITIES	List<City>
ZONE_CITIZENS	List<Individual>
ZONE_CHILD_CITIES_ID	List<String>
ZONE_CITIZENS_ID	List<String>
ZONE_LAST_UPDATED	float

CITY_ID	String
CITY_NCITIZENS	int
CITY_PARENT_ZONE	Zone
CITY_ADJ_CITIES	List<City>
CITY_CITIZENS	List<Individual>
CITY_ADJ_CITIES_ID	List<String>
CITY_CITIZENS_ID	List<String>

IND_ID	String
IND_NAME	String
IND_SURNAMES	List<String>
IND_BIRTH	float
IND_DEATH	float
IND_ALIVE	boolean
IND_REP	float
IND_STR	short
IND_CON	short
IND_SPE	short
IND_INT	short
IND_WIS	short
IND_CHA	short
IND_BEA	short
IND_FER	short
IND_HOR	short
IND_COM	short
IND_GENDER	Gender
IND_ATTRACTION_TO_MALE	float
IND_ATTRACTION_TO_FEMALE	float
IND_DESIRABILITY	float
IND_PARENTS	List<Individual>
IND_FATHER	Individual
IND_MOTHER	Individual
IND_PARTNER	Individual
IND_CHILDS	List<Individual>
IND_GRANDPARENTS	List<Individual>
IND_GRANDCHILDS	List<Individual>
IND_SIBLINGS	List<Individual>
IND_ORIGINAL_ZONE	Zone
IND_CURRENT_ZONE	Zone
IND_CURRENT_CITY	City

 */

	public static enum StorableField {
		WORLD, ZONE, LANGUAGE, CITY, INDIVIDUAL, //TODO
		
		WORLD_ID, WORLD_NZONES, WORLD_NCITIZENS, WORLD_CHILD_ZONES, WORLD_CITIZENS, WORLD_CHILD_ZONES_ID, WORLD_CITIZENS_ID, WORLD_MOMENT,

		ZONE_ID, ZONE_NCITIES, ZONE_NCITIZENS, ZONE_PARENT_WORLD, ZONE_CHILD_CITIES, ZONE_CITIZENS, ZONE_CHILD_CITIES_ID, ZONE_CITIZENS_ID, ZONE_LAST_UPDATED,

		CITY_ID, CITY_NCITIZENS, CITY_PARENT_ZONE, CITY_ADJ_CITIES, CITY_CITIZENS, CITY_ADJ_CITIES_ID, CITY_CITIZENS_ID,

		IND_ID, IND_NAME, IND_SURNAMES, IND_BIRTH, IND_DEATH, IND_ALIVE, IND_REP,
		IND_STR, IND_CON, IND_SPE, IND_INT, IND_WIS, IND_CHA, IND_BEA, IND_FER, IND_HOR, IND_COM,
		IND_GENDER, IND_ATTRACTION_TO_MALE, IND_ATTRACTION_TO_FEMALE, IND_DESIRABILITY,
		IND_PARENTS, IND_FATHER, IND_MOTHER, IND_PARTNER, IND_CHILDS, IND_GRANDPARENTS, IND_GRANDCHILDS, IND_SIBLINGS,
		IND_ORIGINAL_ZONE, IND_CURRENT_ZONE, IND_CURRENT_CITY
		
	}
	
	public static Object getFieldValue(Storable target, StorableField field) {
		if (target == null) return null;
		switch (target.TYPE) {
		case WORLD:
			return getFieldValue((World) target, field);
		case ZONE:
			return getFieldValue((Zone) target, field);
		case LANGUAGE:
			return getFieldValue((Language) target, field);
		case CITY:
			return getFieldValue((City) target, field);
		case INDIVIDUAL:
			return getFieldValue((Individual) target, field);

		default:
			return null;
		}
	}
	
	public static Object getFieldValue(World target, StorableField field) {
		if (target == null) return null;
		switch (field) {
		case WORLD_CHILD_ZONES:
			return target.getZones();
		case WORLD_CHILD_ZONES_ID:
			return target.getZoneIDs();
		case WORLD_CITIZENS:
			//TODO
			return null;
		case WORLD_CITIZENS_ID:
			//TODO
			return null;
		case WORLD_ID:
			return target.getWorldID();
		case WORLD_MOMENT:
			return target.getMoment();
		case WORLD_NCITIZENS:
			return target.getNCitizens();
		case WORLD_NZONES:
			return target.getNZones();
		default:
			return null;
		}
	}
	
	public static Object getFieldValue(Zone target, StorableField field) {
		if (target == null) return null;
		switch (field) {
		case ZONE_CHILD_CITIES:
			return target.getCities();
		case ZONE_CHILD_CITIES_ID:
			return target.getCityIDs();
		case ZONE_CITIZENS:
			//TODO
			break;
		case ZONE_CITIZENS_ID:
			//TODO
			break;
		case ZONE_ID:
			return target.getZoneID();
		case ZONE_LAST_UPDATED:
			return target.getLastUpdated();
		case ZONE_NCITIES:
			return target.getNCities();
		case ZONE_NCITIZENS:
			return target.getNCitizens();
		case ZONE_PARENT_WORLD:
			return target.getParentWorld();
		default:
			return null;
		}
		return null;
	}
	
	public static Object getFieldValue(Language target, StorableField field) {
		if (target == null) return null;
		
		return null;
	}
	
	public static Object getFieldValue(City target, StorableField field) {
		switch (field) {
		case CITY_ADJ_CITIES:
			//TODO
			break;
		case CITY_ADJ_CITIES_ID:
			//TODO
			break;
		case CITY_CITIZENS:
			return target.getCitizenInd();
		case CITY_CITIZENS_ID:
			return target.getCitizens();
		case CITY_ID:
			return target.getCityID();
		case CITY_NCITIZENS:
			return target.getNCitizens();
		case CITY_PARENT_ZONE:
			return target.getParentZone();
		default:
			return null;
		}
		return null;
	}
	
	public static Object getFieldValue(Individual target, StorableField field) {
		if (target == null) return null;
		switch (field) {
		case IND_ID:
			return target.getIndividualID();
		case IND_NAME:
			return target.getName();
		case IND_SURNAMES:
			//TODO
			break;
		case IND_BIRTH:
			return target.getBirthDate();
		case IND_DEATH:
			return target.getDeathDate();
		case IND_ALIVE:
			return (target.getDeathDate() == -1f);
		case IND_REP:
			return target.getRep();
		case IND_STR:
			return target.getStrength();
		case IND_CON:
			return target.getConstitution();
		case IND_SPE:
			return target.getSpeed();
		case IND_INT:
			return target.getIntelligence();
		case IND_WIS:
			return target.getWisdom();
		case IND_CHA:
			return target.getCharisma();
		case IND_BEA:
			return target.getBeauty();
		case IND_FER:
			return target.getFertility();
		case IND_HOR:
			return target.getHorniness();
		case IND_COM:
			return target.getComformity();
		case IND_GENDER:
			return target.getGender();
		case IND_ATTRACTION_TO_FEMALE:
			return target.getGenderAttraction(Gender.FEMALE);
		case IND_ATTRACTION_TO_MALE:
			return target.getGenderAttraction(Gender.MALE);
		case IND_DESIRABILITY:
			return target.getDesirability();
		case IND_PARENTS:
			//TODO
			break;
		case IND_FATHER:
			//TODO
			break;
		case IND_MOTHER:
			//TODO
			break;
		case IND_PARTNER:
			return target.getPartner();
		case IND_CHILDS:
			return target.getChildren();
		case IND_GRANDPARENTS:
			break;
		case IND_GRANDCHILDS:
			break;
		case IND_SIBLINGS:
			break;
		case IND_ORIGINAL_ZONE:
			return target.getOriginalZone();
		case IND_CURRENT_ZONE:
			return target.getCurrentZone();
		case IND_CURRENT_CITY:
			return target.getCurrentCity();
		default:
			return null;
		}
		return null;
	}
}
