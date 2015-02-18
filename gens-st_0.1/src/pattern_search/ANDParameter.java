package pattern_search;

import java.util.ArrayList;
import java.util.List;

import model.Storable;

public class ANDParameter extends Parameter {
	
	private List<Parameter> params;
	
	public ANDParameter(Parameter param1, Parameter param2) {
		this.params = new ArrayList<Parameter>(2);
		if (param1 != null && this != param1) {
			this.params.add(param1);
		}
		
		if (param2 != null && this != param2 && !param1.equals(param2)) {
			this.params.add(param2);
		}
	}

	public ANDParameter(List<Parameter> params) {
		this.params = new ArrayList<Parameter>(params.size());
		for (Parameter parameter : params) {
			if (parameter != null && this != parameter && !params.contains(parameter)) {
				this.params.add(parameter);
			}
		}
	}

	@Override
	public boolean eval(Storable target) {
		for (Parameter parameter : params) {
			if (!parameter.eval(target)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((params == null) ? 0 : params.hashCode());
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
		ANDParameter other = (ANDParameter) obj;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}
	
}
