package com.github.entities;

import com.github.ocm.annotations.CSVEntity;

@CSVEntity(regexpFilePattern = ".*strings.csv")
public class StringEntity {

	String	STR1;
	String	STR2;
	String	STR3;
	String	STR4;
	String	STR5;

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
		StringEntity other = (StringEntity) obj;
		if (STR1 == null) {
			if (other.STR1 != null) {
				return false;
			}
		} else if (!STR1.equals(other.STR1)) {
			return false;
		}
		if (STR2 == null) {
			if (other.STR2 != null) {
				return false;
			}
		} else if (!STR2.equals(other.STR2)) {
			return false;
		}
		if (STR3 == null) {
			if (other.STR3 != null) {
				return false;
			}
		} else if (!STR3.equals(other.STR3)) {
			return false;
		}
		if (STR4 == null) {
			if (other.STR4 != null) {
				return false;
			}
		} else if (!STR4.equals(other.STR4)) {
			return false;
		}
		if (STR5 == null) {
			if (other.STR5 != null) {
				return false;
			}
		} else if (!STR5.equals(other.STR5)) {
			return false;
		}
		return true;
	}

	public String getSTR1() {
		return STR1;
	}

	public String getSTR2() {
		return STR2;
	}

	public String getSTR3() {
		return STR3;
	}

	public String getSTR4() {
		return STR4;
	}

	public String getSTR5() {
		return STR5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((STR1 == null) ? 0 : STR1.hashCode());
		result = prime * result + ((STR2 == null) ? 0 : STR2.hashCode());
		result = prime * result + ((STR3 == null) ? 0 : STR3.hashCode());
		result = prime * result + ((STR4 == null) ? 0 : STR4.hashCode());
		result = prime * result + ((STR5 == null) ? 0 : STR5.hashCode());
		return result;
	}

	public void setSTR1(String sTR1) {
		STR1 = sTR1;
	}

	public void setSTR2(String sTR2) {
		STR2 = sTR2;
	}

	public void setSTR3(String sTR3) {
		STR3 = sTR3;
	}

	public void setSTR4(String sTR4) {
		STR4 = sTR4;
	}

	public void setSTR5(String sTR5) {
		STR5 = sTR5;
	}

}
