package com.github.ocm.mapping;

import static junitx.framework.FileAssert.assertBinaryEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.github.entities.CSVFormula;
import com.github.entities.PrimitiveStrictTypesEntity;
import com.github.ocm.exceptions.ClassValidationException;
import com.github.ocm.exceptions.ConverterException;
import com.github.ocm.exceptions.ParsingException;

public class OCMEngineTest {

	/**
	 * Should not crash when parsing empty directory.
	 */
	@Test
	public void mustNotFailIfScanningEmptyDirectory() throws ClassValidationException, ParsingException, ConverterException {
		Configuration config = new Configuration.Builder("./testRes/empty").build();

		OCMEngine.INSTANCE.Init(config);

		Map<String, List<PrimitiveStrictTypesEntity>> ret = OCMEngine.INSTANCE.getEntities(PrimitiveStrictTypesEntity.class);
		assertEquals(0, ret.size());
	}

	/**
	 * {@link NullPointerException} should be thrown with description what
	 * happened.
	 */
	@Test(expected = NullPointerException.class)
	public void runWithoutInit() throws ClassValidationException, ParsingException, ConverterException {
		OCMEngine.INSTANCE.clearConfiguration();
		OCMEngine.INSTANCE.getEntities(String.class);
	}

	/**
	 * {@link IllegalStateException} should be thrown with description what
	 * happened.
	 */
	@Test(expected = IllegalStateException.class)
	public void badEntityClass() throws ClassValidationException, ParsingException, ConverterException {
		Configuration config = new Configuration.Builder("./testRes").build();

		OCMEngine.INSTANCE.Init(config);

		OCMEngine.INSTANCE.getEntities(String.class);
	}

	@Test
	public void testWithFormulaEntity() throws Exception {
		Configuration config = new Configuration.Builder("./").build();
		OCMEngine.INSTANCE.Init(config);

		Set<CSVFormula> set = new HashSet<CSVFormula>();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");

		CSVFormula formula = new CSVFormula();
		formula.setEndDate(sdf.parse("20110117"));
		formula.setFormula("VS_UpsizingUnsuccess_DchOther+VS_UpsizingUnsuccess_DchHsdpa+VS_UpsizingUnsuccess_DchPsIbLt64+VS_UpsizingUnsuccess_DchPsIb64+VS_UpsizingUnsuccess_DchPsIb128+VS_UpsizingUnsuccess_DchPsIb256+VS_UpsizingUnsuccess_DchPsIb384");
		formula.setFormulaId(1);
		formula.setInsertDate(sdf.parse("20110117"));

		set.add(formula);

		OCMEngine.INSTANCE.writeCSV(set, "test", true);

		assertBinaryEquals(new File("./testRes/formulaTest.csv"), new File("./test.csv"));
	}

}
