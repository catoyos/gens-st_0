package main_logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import main_logic.io.InputOutput;
import model.City;
import model.Individual;
import model.Storable;
import model.Storable.StorableType;
import model.World;
import model.Zone;
import model.interfaces.IAIEngine;
import pattern_search.AbstractParameter;
import pattern_search.ORParameter;
import pattern_search.Pattern;
import pattern_search.Pattern.PatternContainsAs;
import pattern_search.SimpleParameter;
import pattern_search.SimpleParameter.ParamType;
import pattern_search.StorableFieldManager.StorableField;
import ai_engine.MyAIEngine;

public class MainSTPLD01 {
	private Properties properties;
	private StoryUniverse uni;
//	private Player player;

	public MainSTPLD01() {
		this("config.properties");
	}
	
	public MainSTPLD01(String configfile) {
		this.properties = new Properties();
		try {
			properties.load(new FileInputStream(configfile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!properties.containsKey("folder")) {
			properties.setProperty("folder", "files");
		}
	}
	
	private void run() {
		System.out.println(properties);
		this.uni = loadUniverse();
		System.out.println(uni);
		

		System.out.println("---------------------------");

		System.out.println(StoryUniverse.worldToString(uni.getWorld()));
		

		Pattern<World> patternInd = buildTestPattern2();
		uni.printResults(patternInd);
	}


	private Pattern<World> buildTestPattern2() {
		List<Pattern<? extends Storable>> aux;
		
		Pattern<Individual> pii01 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii01", StorableType.INDIVIDUAL, null, null);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pii01);
		Pattern<Individual> pii02 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii02", StorableType.INDIVIDUAL, null, null);
		aux.add(pii02);
		
		Pattern<City> pcc01 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc01", StorableType.CITY, null, aux);
		
		
		Pattern<Individual> pii03 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii03", StorableType.INDIVIDUAL, null, null);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pii03);
		Pattern<Individual> pii04 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii04", StorableType.INDIVIDUAL, null, null);
		aux.add(pii04);
		
		Pattern<City> pcc02 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc01", StorableType.CITY, null, aux);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pcc01);
		aux.add(pcc02);
		
		Pattern<Zone> pzz01 = new Pattern<Zone>(PatternContainsAs.WORLD_CHILD_ZONE, "zz01", StorableType.ZONE, null, aux);
		
		Pattern<Individual> pii05 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii05", StorableType.INDIVIDUAL, null, null);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pii05);
		Pattern<Individual> pii06 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii06", StorableType.INDIVIDUAL, null, null);
		aux.add(pii06);
		
		Pattern<City> pcc03 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc03", StorableType.CITY, null, aux);
		
		
		Pattern<Individual> pii07 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii07", StorableType.INDIVIDUAL, null, null);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pii07);
		Pattern<Individual> pii08 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii08", StorableType.INDIVIDUAL, null, null);
		aux.add(pii08);
		
		Pattern<City> pcc04 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc04", StorableType.CITY, null, aux);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pcc03);
		aux.add(pcc04);
		
		Pattern<Zone> pzz02 = new Pattern<Zone>(PatternContainsAs.WORLD_CHILD_ZONE, "zz02", StorableType.ZONE, null, aux);
		aux = new LinkedList<Pattern<?>>();
		aux.add(pzz01);
		aux.add(pzz02);
		
		Pattern<World> pww01 = new Pattern<World>("ww01", StorableType.WORLD, null, aux);
		
		
		return pww01;

	}
	
	@SuppressWarnings("unused")
	private Pattern<World> buildTestPattern1(){
		Pattern<World> pww01;
		Pattern<Zone> pzz01;
		Pattern<Zone> pzz02;
		Pattern<City> pcc01;
		Pattern<City> pcc02;
		Pattern<City> pcc03;
		Pattern<Individual> pii01;
		Pattern<Individual> pii02;
		AbstractParameter p;
		List<AbstractParameter> listP;
		List<Pattern<? extends Storable>> listPatt;
		
		
		listP = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.INDIVIDUAL, StorableField.IND_BEA, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, StorableField.IND_FER, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		pii01 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii01", StorableType.INDIVIDUAL, listP, null);
		
		
		listP = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.INDIVIDUAL, StorableField.IND_CHA, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, StorableField.IND_HOR, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, StorableField.IND_COM, ParamType.EQUAL_OR_LESSER_THAN, 30);
		listP.add(p);
		pii02 = new Pattern<Individual>(PatternContainsAs.CITY_CITIZEN, "ii02", StorableType.INDIVIDUAL, listP, null);

		listP = new LinkedList<AbstractParameter>();
		listPatt = new LinkedList<Pattern<? extends Storable>>();
		listPatt.add(pii01);
		pcc01 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc01", StorableType.CITY, listP, listPatt);

		listP = new LinkedList<AbstractParameter>();
		listPatt = new LinkedList<Pattern<? extends Storable>>();
		pcc02 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc02", StorableType.CITY, listP, listPatt);

		listP = new LinkedList<AbstractParameter>();
		listPatt = new LinkedList<Pattern<? extends Storable>>();
		listPatt.add(pcc01);
		listPatt.add(pcc02);
		pzz01 = new Pattern<Zone>(PatternContainsAs.WORLD_CHILD_ZONE, "zz01", StorableType.ZONE, listP, listPatt);

		listP = new LinkedList<AbstractParameter>();
		listPatt = new LinkedList<Pattern<? extends Storable>>();
		listPatt.add(pii02);
		pcc03 = new Pattern<City>(PatternContainsAs.ZONE_CHILD_CITY, "cc03", StorableType.CITY, listP, listPatt);

		listP = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.ZONE, StorableField.ZONE_NCITIZENS, ParamType.GREATER_THAN, 20);
		listP.add(p);
		listPatt = new LinkedList<Pattern<? extends Storable>>();
		listPatt.add(pcc03);
		pzz02 = new Pattern<Zone>(PatternContainsAs.WORLD_CHILD_ZONE, "zz02", StorableType.ZONE, listP, listPatt);


		listP = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.WORLD, StorableField.WORLD_NZONES, ParamType.EQUAL_OR_GREATER_THAN, 2);
		listP.add(p);
		List<AbstractParameter> listAux = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.WORLD, StorableField.WORLD_NZONES, ParamType.GREATER_THAN, 2);
		listAux.add(p);
		p = new SimpleParameter(StorableType.WORLD, StorableField.WORLD_NCITIZENS, ParamType.EQUAL_OR_GREATER_THAN, 50);
		listAux.add(p);
		p = new ORParameter(listAux);
		listP.add(p);

		listPatt = new LinkedList<Pattern<? extends Storable>>();
		listPatt.add(pzz01);
		listPatt.add(pzz02);
		pww01 = new Pattern<World>("ww01", StorableType.WORLD, listP, listPatt);

		return pww01;
		
	}
	
	private IAIEngine loadAIEngine() {
		String aiestr = properties.getProperty("aieng");
		IAIEngine aieng = null;
		if (aiestr != null) {
			try {
				aieng = (IAIEngine) Class.forName(aiestr).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (aieng == null) {
			aieng = new MyAIEngine();
			properties.setProperty("aieng", aieng.getClass().getName());
		}
		
		return aieng;
	}
	
	private StoryUniverse loadUniverse() {
		IAIEngine aieng = loadAIEngine();
		String rootfolder = properties.getProperty("folder");
		StoryUniverse res = null;
		try {
			if (properties.getProperty("universe") != null) {
				File file = StoryUniverse.getUniverseFile(properties.getProperty("universe"), rootfolder);
				res = StoryUniverse.generateFromString(InputOutput.getFileContent(file), aieng, rootfolder);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (res == null) {
			res = new StoryUniverse(aieng, rootfolder);
			properties.setProperty("universe", res.uniID);
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		String configfile = "config.properties";
		MainSTPLD01 m = new MainSTPLD01(configfile);
		m.run();
		try {
			m.properties.store(new FileOutputStream(configfile), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
