package pattern_search;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import model.Storable;

public class ORParameter extends AbstractParameter {
	
	private List<AbstractParameter> params;
	
	public ORParameter(AbstractParameter param1, AbstractParameter param2) {
		this.params = new ArrayList<AbstractParameter>(2);
		if (param1 != null){
			this.params.add(param1);
		}
		
		if (param2 != null && param2 != param1 && !param2.equals(param1)) {
			this.params.add(param2);
		}
	}

	public ORParameter(List<AbstractParameter> params) {
		this.params = new ArrayList<AbstractParameter>(params.size());
		for (AbstractParameter abstractParameter : params) {
			if (abstractParameter != null && !this.params.contains(abstractParameter)) {
				this.params.add(abstractParameter);
			}
		}
	}

	@Override
	public boolean eval(Storable target) {
		for (AbstractParameter abstractParameter : params) {
			if (abstractParameter != null && abstractParameter.eval(target)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isComplex() {
		for (AbstractParameter abstractParameter : params) {
			if (abstractParameter != null && abstractParameter.isComplex()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setRoles(Hashtable<String, Storable> roles) {
		super.setRoles(roles);
		for (AbstractParameter abstractParameter : params) {
			if (abstractParameter != null) abstractParameter.setRoles(roles);
		}
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
		ORParameter other = (ORParameter) obj;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}

}
