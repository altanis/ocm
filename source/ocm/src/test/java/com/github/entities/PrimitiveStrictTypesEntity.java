package com.github.entities;

import com.github.ocm.annotations.CSVEntity;

@CSVEntity(regexpFilePattern = ".*strictmappings.csv")
public class PrimitiveStrictTypesEntity {

	double	doubleVal;
	float	floatVal;
	int		integerVal;

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
		PrimitiveStrictTypesEntity other = (PrimitiveStrictTypesEntity) obj;
		if (Double.doubleToLongBits(doubleVal) != Double.doubleToLongBits(other.doubleVal)) {
			return false;
		}
		if (Float.floatToIntBits(floatVal) != Float.floatToIntBits(other.floatVal)) {
			return false;
		}
		if (integerVal != other.integerVal) {
			return false;
		}
		return true;
	}

	public double getDoubleVal() {
		return doubleVal;
	}

	public float getFloatVal() {
		return floatVal;
	}

	public int getIntegerVal() {
		return integerVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(doubleVal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(floatVal);
		result = prime * result + integerVal;
		return result;
	}

	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setFloatVal(float floatVal) {
		this.floatVal = floatVal;
	}

	public void setIntegerVal(int integerVal) {
		this.integerVal = integerVal;
	}

	@Override
	public String toString() {
		return "PrimitiveStrictTypesEntity [doubleVal=" + doubleVal + ", floatVal=" + floatVal + ", integerVal=" + integerVal + "]";
	}

}
