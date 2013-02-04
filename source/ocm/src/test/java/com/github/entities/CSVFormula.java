/*******************************************************
 *	Filename:
 *	Copyright (c) 2011 Alcatel-Lucent.
 *	THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Alcatel-Lucent.
 *	The copyright notice above does not evidence any actual
 *	or intended publication of such source code.
 ********************************************************/

/**
 * <p>
 * Change History
 * --------------
 * Name		 Date		What?
 * ---------------------------------------------------------
 * slaskawi	Jan 13, 2011	EXAMPLE
 *
 * </p>
 */

package com.github.entities;

import java.util.Date;

import com.github.ocm.annotations.CSVEntity;
import com.github.ocm.annotations.CSVField;
import com.github.ocm.converters.DefaultDateConverterYYYYMMDD;

@CSVEntity
public class CSVFormula implements IFormula {

	@CSVField(csvFieldName="FORMULA")
	String formula;        
	
	@CSVField(csvFieldName="FORMULA_ID")
	Integer formulaId;
	
	@CSVField(csvFieldName="NAME")
	String name;
	
	@CSVField(csvFieldName="INTERNAL_NAME")
	String internalName;
	
	@CSVField(csvFieldName="SOFT_VER")
	String softVer;
	
	@CSVField(csvFieldName="TRESHOLD")
	Double threshold;
	
	@CSVField(csvFieldName="SOURCE")
	String source;
	
	@CSVField(csvFieldName="INSERT_DATE", converter=DefaultDateConverterYYYYMMDD.class)
	Date insertDate;
	
	@CSVField(csvFieldName="START_DATE", converter=DefaultDateConverterYYYYMMDD.class)
	Date startDate;
	
	@CSVField(csvFieldName="END_DATE", converter=DefaultDateConverterYYYYMMDD.class)
	Date endDate;
	
	
	
	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getEndDate()
	 */
	@Override
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getFormula()
	 */
	@Override
	public String getFormula() {
		return formula;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getId()
	 */
	@Override
	public Integer getId() {
		return formulaId;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getInsertDate()
	 */
	@Override
	public Date getInsertDate() {
		return insertDate;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getInternalName()
	 */
	@Override
	public String getInternalName() {
		return internalName;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getSoftVer()
	 */
	@Override
	public String getSoftVer() {
		return softVer;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getSource()
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#getStartDate()
	 */
	@Override
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Overriden method. 
	 * @see com.alu.formulaparser.formuladata.IFormula#threshold()
	 */
	@Override
	public Double threshold() {
		return threshold;
	}

	public Integer getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(Integer formulaId) {
		this.formulaId = formulaId;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public void setSoftVer(String softVer) {
		this.softVer = softVer;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((formulaId == null) ? 0 : formulaId.hashCode());
		result = prime * result + ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result + ((internalName == null) ? 0 : internalName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((softVer == null) ? 0 : softVer.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((threshold == null) ? 0 : threshold.hashCode());
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
		CSVFormula other = (CSVFormula) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (internalName == null) {
			if (other.internalName != null)
				return false;
		} else if (!internalName.equals(other.internalName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (softVer == null) {
			if (other.softVer != null)
				return false;
		} else if (!softVer.equals(other.softVer))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (threshold == null) {
			if (other.threshold != null)
				return false;
		} else if (!threshold.equals(other.threshold))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CSVFormula [endDate=" + endDate + ", formula=" + formula + ", formulaId=" + formulaId + ", insertDate=" + insertDate + ", internalName=" + internalName + ", name=" + name + ", softVer=" + softVer + ", source=" + source + ", startDate=" + startDate + ", threshold=" + threshold + "]";
	}

}
