package pattern_search;

import java.util.Hashtable;

import model.Storable;

public class EQUIVParameter extends AbstractParameter {

	private AbstractParameter param1;
	private AbstractParameter param2;
	
	public EQUIVParameter(AbstractParameter param1, AbstractParameter param2) {
		this.param1 = param1;
		this.param2 = param2;
	}

	@Override
	public boolean eval(Storable target) {
		if (param1 == param2) return true;
		else if (param1 != null && param1.equals(param2)) return true;
		
		boolean res1 = param1 == null ? false : param1.eval(target);
		boolean res2 = param2 == null ? false : param2.eval(target);
		
		return res1 == res2;
	}

	@Override
	public boolean isComplex() {
		if (param1 != null && param1.isComplex()) return true;
		else if (param2 != null && param2.isComplex()) return true;
		else return false;
	}

	@Override
	public void setRoles(Hashtable<String, Storable> roles) {
		super.setRoles(roles);
		if (param1 != null) param1.setRoles(roles);
		if (param2 != null) param2.setRoles(roles);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((param2 == null) ? 0 : param2.hashCode());
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
		EQUIVParameter other = (EQUIVParameter) obj;
		if (param1 == null) {
			if (other.param1 != null)
				return false;
		} else if (!param1.equals(other.param1))
			return false;
		if (param2 == null) {
			if (other.param2 != null)
				return false;
		} else if (!param2.equals(other.param2))
			return false;
		return true;
	}

}
