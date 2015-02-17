package pattern_search;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Storable;


public class WorldEval {
	public static <T extends Storable> Result<T> findOne(Pattern<T> pattern, Collection<T> pool){
		return null;
		
	}
	
	public static <T extends Storable> List<Result<T>> findAll(Pattern<T> pattern, Collection<T> pool){
		List<Result<T>> res = new LinkedList<Result<T>>();
		return res;
		
	}
	
	public static <T extends Storable> List<Result<T>> findN(Pattern<T> pattern, Collection<T> pool, int n){
		return null;
		
	}
}
