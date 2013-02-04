package com.github.entities;

import com.github.ocm.annotations.CSVEntity;

@CSVEntity(regexpFilePattern = ".*mappings2.csv")
public class PrimitiveTypesEntity {

	Double	doubleVal;
	Float	floatVal;
	Integer	integerVal;
	String	stringVal;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PrimitiveTypesEntity other = (PrimitiveTypesEntity) obj;
		if (doubleVal == null) {
			if (other.doubleVal != null) {
				return false;
			}
		} else if (!doubleVal.equals(other.doubleVal)) {
			return false;
		}
		if (floatVal == null) {
			if (other.floatVal != null) {
				return false;
			}
		} else if (!floatVal.equals(other.floatVal)) {
			return false;
		}
		if (integerVal == null) {
			if (other.integerVal != null) {
				return false;
			}
		} else if (!integerVal.equals(other.integerVal)) {
			return false;
		}
		if (stringVal == null) {
			if (other.stringVal != null) {
				return false;
			}
		} else if (!stringVal.equals(other.stringVal)) {
			return false;
		}
		return true;
	}

	public Double getDoubleVal() {
		return doubleVal;
	}

	public Float getFloatVal() {
		return floatVal;
	}

	public Integer getIntegerVal() {
		return integerVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doubleVal == null) ? 0 : doubleVal.hashCode());
		result = prime * result + ((floatVal == null) ? 0 : floatVal.hashCode());
		result = prime * result + ((integerVal == null) ? 0 : integerVal.hashCode());
		result = prime * result + ((stringVal == null) ? 0 : stringVal.hashCode());
		return result;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setFloatVal(Float floatVal) {
		this.floatVal = floatVal;
	}

	public void setIntegerVal(Integer integerVal) {
		this.integerVal = integerVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	@Override
	public String toString() {
		return "PrimitiveTypesEntity [stringVal=" + stringVal + ", doubleVal=" + doubleVal + ", floatVal=" + floatVal + ", integerVal=" + integerVal + "]";
	}

}
