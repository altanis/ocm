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


public interface IFormula {
	
	Integer getId();
	String getName();                                                                                                                                                          
	String getInternalName();
	String getSoftVer();
	Double threshold();
	String getSource();
	Date getInsertDate();
	Date getStartDate();
	Date getEndDate();
	String getFormula();         
}
