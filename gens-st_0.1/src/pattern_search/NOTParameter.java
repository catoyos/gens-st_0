package pattern_search;

import model.Storable;

public class NOTParameter extends AbstractParameter {

	private AbstractParameter param;
	
	public NOTParameter(AbstractParameter param) {
		this.param = param;
	}

	@Override
	public boolean eval(Storable target) {
		return param == null || !param.eval(target);
	}

	@Override
	public boolean isComplex() {
		return param == null || param.isComplex();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((param == null) ? 0 : param.hashCode());
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
		NOTParameter other = (NOTParameter) obj;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		return true;
	}

}
