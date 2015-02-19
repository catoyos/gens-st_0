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
import model.Individual;
import model.Storable.StorableType;
import model.interfaces.IAIEngine;
import pattern_search.AbstractParameter;
import pattern_search.Pattern;
import pattern_search.SimpleParameter;
import pattern_search.SimpleParameter.ParamField;
import pattern_search.SimpleParameter.ParamType;
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
		
		AbstractParameter p;
		List<AbstractParameter> listP = new LinkedList<AbstractParameter>();
		p = new SimpleParameter(StorableType.INDIVIDUAL, ParamField.IND_BEA, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, ParamField.IND_FER, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, ParamField.IND_CHA, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, ParamField.IND_HOR, ParamType.EQUAL_OR_GREATER_THAN, 60);
		listP.add(p);
		p = new SimpleParameter(StorableType.INDIVIDUAL, ParamField.IND_COM, ParamType.EQUAL_OR_LESSER_THAN, 30);
		listP.add(p);
		Pattern<Individual> patternInd = new Pattern<Individual>("ii01", listP);
		
		
		uni.printResults(patternInd);
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
