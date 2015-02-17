package main_logic;

import java.util.List;

import pattern_search.Pattern;
import pattern_search.Result;
import model.Arch;
import model.City;
import model.City.Direction;
import model.Individual;
import model.World;
import model.Zone;
import model.interfaces.IAIEngine;
import model.utils.StringUtils;
import data_storage.Storage;

public class StoryUniverse {

	final String uniID;
	private World world;
	
	public StoryUniverse(IAIEngine aieng) {
		this(generateId(), aieng, "files");
	}

	public StoryUniverse(IAIEngine aieng, String rootfolder) {
		this(generateId(), aieng, rootfolder);
	}

	public StoryUniverse(String uniID, IAIEngine aieng) {
		this(uniID, aieng, "files");
	}

	public StoryUniverse(String uniID, IAIEngine aie, String rootfolder) {
		this.uniID = uniID;
		this.world = null;
		Arch.setAIEngine(aie);
		Storage.getInitiatedStorage(uniID, rootfolder);
	}

	private static String generateId(){
		return StringUtils.generateId((int)(Math.random() * StringUtils.ID_N_25_3), 3);
	}

	public static StoryUniverse generateFromString(String fileContent,
			IAIEngine aieng, String rootfolder) {
		String[] lines = fileContent.split("\n");
		StoryUniverse uni = null;
		try {
			String nuid = lines[0];
			String cuid = null;
			
			uni = new StoryUniverse(nuid, aieng, rootfolder);
			
			if(lines.length > 2){
				cuid = lines[2];
				if (Arch.worldExists(cuid)) {
					uni.world = Arch.getWorldById(cuid);
				}
			}
			
			if(lines.length > 3){
				String[] nids = lines[3].split(",");
				long[] lnids = {-1, -1, -1, -1};
				for (int i = 0; i < lnids.length && i < nids.length; i++) {
					try {
						lnids[i] = Long.parseLong(nids[i]);
					} catch (NumberFormatException e) {
						lnids[i] = -1;
						e.printStackTrace();
					}
				}
				Arch.setNewIDns(lnids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uni;
	}

	public void printResults(Pattern<Individual> pattern) {
		long t0,t1;
		System.out.println(world.getWorldID());
		List<Zone> zones = world.getZones();
		for (Zone zone : zones) {
			System.out.println(zone.getZoneID());
			List<City> cities = zone.getCities();
			for (City city : cities) {
				System.out.println(city.getCityID());
				t0 = System.currentTimeMillis();
				List<Result<Individual>> res = pattern.eval(city.getCitizenInd());
				t1 = System.currentTimeMillis();
				System.out.print("("+(t1-t0)+"ms.)");
				System.out.println("("+res.size()+"/"+city.getNCitizens()+" = "+100*(res.size()/(float)city.getNCitizens())+"%)");
				for (Result<Individual> result : res) {
					System.out.println(indiToString(result.getRes()));
				}
			}
		}
	}

	public static void worldToString(World w, StringBuilder sb){
		sb.append("ID: ").append(w.getWorldID());
		sb.append(", Parent universe: ").append(w.getParentUni());
		sb.append(", Moment: ").append(w.getMoment());
		sb.append(", Zones: ").append(w.getNZones());
		if (w.getNZones()>0) {
			sb.append("\nZONES:");
			List<Zone> zones = w.getZones();
			for (Zone zone : zones) {
				sb.append("\n");
				zoneToString(zone, sb);
			}
		}
	}

	public static void zoneToString(Zone z, StringBuilder sb){
		sb.append("\tID: ").append(z.getZoneID());
		sb.append(", Parent world: ").append(z.getParentWorldID());
		sb.append(", Last updated: ").append(z.getLastUpdated());
		sb.append(", Cities: ").append(z.getNCities());
		sb.append(", Citizens: ").append(z.getNCitizens());
		if (z.getNCities() > 0) {
			sb.append("\n\tCITIES:");
			List<City> cities = z.getCities();
			for (City city : cities) {
				sb.append("\n");
				cityToString(city, sb);
			}
		}
	}

	public static void cityToString(City c, StringBuilder sb){
		sb.append("\t\tID: ").append(c.getCityID());
		sb.append(", Parent zone: ").append(c.getParentZoneID());
		sb.append(", Adjacent cities: [");
		sb.append(" NORTH=").append(c.getAdjacentCityID(Direction.NORTH));
		sb.append(" EAST=").append(c.getAdjacentCityID(Direction.EAST));
		sb.append(" SOUTH=").append(c.getAdjacentCityID(Direction.SOUTH));
		sb.append(" WEST=").append(c.getAdjacentCityID(Direction.WEST));
		sb.append(" ]");
		sb.append(", Citizens: ").append(c.getNCitizens());
		if (c.getNCitizens() > 0) {
			sb.append("\n\t\tCITIZENS:");
			List<Individual> citizens = c.getCitizenInd();
			for (Individual indi : citizens) {
				sb.append("\n");
				indiToString(indi, sb);
			}
		}
	}

	public static void indiToString(Individual i, StringBuilder sb){
		sb.append("\t\t\tID: ").append(i.getIndividualID());
		short[] car = i.getCharactValues();
		String[] carstr = {"str", "con", "spe", "int", "wis", "cha", "bea", "fer", "hor", "com" };
		for (int j = 0; j < carstr.length; j++) {
			sb.append(",").append(carstr[j]).append(":").append(car[j]);
		}
	}

	public static String worldToString(World w){
		StringBuilder sb = new StringBuilder();
		worldToString(w, sb);
		return sb.toString();
	}

	public static String zoneToString(Zone z){
		StringBuilder sb = new StringBuilder();
		zoneToString(z, sb);
		return sb.toString();
	}

	public static String cityToString(City c){
		StringBuilder sb = new StringBuilder();
		cityToString(c, sb);
		return sb.toString();
	}

	public static String indiToString(Individual i){
		StringBuilder sb = new StringBuilder();
		indiToString(i, sb);
		return sb.toString();
	}
	

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String getUniID() {
		return uniID;
	}
	
	@Override
	public String toString() {
		return "StoryUniverse [uniID=" + uniID + ", world=" + world + "]";
	}

}
