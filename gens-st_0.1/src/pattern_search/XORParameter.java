package pattern_search;

import model.Storable;

public class XORParameter extends AbstractParameter {

	private AbstractParameter param1;
	private AbstractParameter param2;
	
	public XORParameter(AbstractParameter param1, AbstractParameter param2) {
		//TODO CONTROLAR NULLS, THIS==PARAM...
		this.param1 = param1;
		this.param2 = param2;
	}

	@Override
	public boolean eval(Storable target) {
		boolean res = param1.eval(target);		
		return res != param2.eval(target);
	}

	@Override
	public boolean isComplex() {
		return param1.isComplex()||param2.isComplex();
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
		XORParameter other = (XORParameter) obj;
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
