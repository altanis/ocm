package com.github.ocm.mapping;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.entities.PrimitiveStrictTypesEntity;
import com.github.entities.PrimitiveTypesEntity;
import com.github.entities.StringEntity;
import com.github.ocm.exceptions.ClassValidationException;
import com.github.ocm.exceptions.ConverterException;
import com.github.ocm.exceptions.ParsingException;
import com.github.ocm.mapping.Configuration;
import com.github.ocm.mapping.OCMEngine;

public class MappingTest {

	@Test
	public void testPrimitiveTypeEntities() throws ClassValidationException, ParsingException, ConverterException {
		Configuration config = new Configuration.Builder("./testRes").setClassAutoDiscovery(false).addEntityClasses(PrimitiveTypesEntity.class).build();

		PrimitiveTypesEntity equalTest = new PrimitiveTypesEntity();
		equalTest.setStringVal("STRING");
		equalTest.setDoubleVal(1.11);
		equalTest.setFloatVal(1.11f);
		equalTest.setIntegerVal(1);

		OCMEngine.INSTANCE.Init(config);

		Map<String, List<PrimitiveTypesEntity>> ret = OCMEngine.INSTANCE.getEntities(PrimitiveTypesEntity.class);

		PrimitiveTypesEntity result = ret.values().iterator().next().get(0);

		assertEquals(equalTest, result);
	}

	@Test
	public void testStrictPrimitiveTypeEntities() throws ClassValidationException, ParsingException, ConverterException {
		Configuration config = new Configuration.Builder("./testRes").setClassAutoDiscovery(false).addEntityClasses(PrimitiveStrictTypesEntity.class).build();

		PrimitiveStrictTypesEntity equalTest = new PrimitiveStrictTypesEntity();
		equalTest.setDoubleVal(1.11);
		equalTest.setFloatVal(1.11f);
		equalTest.setIntegerVal(1);

		OCMEngine.INSTANCE.Init(config);

		Map<String, List<PrimitiveStrictTypesEntity>> ret = OCMEngine.INSTANCE.getEntities(PrimitiveStrictTypesEntity.class);

		PrimitiveStrictTypesEntity result = ret.values().iterator().next().get(0);

		assertEquals(equalTest, result);
	}

	@Test
	public void testStringEntities() throws ClassValidationException, ParsingException, ConverterException {
		Configuration config = new Configuration.Builder("./testRes").setClassAutoDiscovery(false).addEntityClasses(StringEntity.class).build();

		StringEntity equalTest = new StringEntity();
		equalTest.setSTR1("String1");
		equalTest.setSTR2("String2");
		equalTest.setSTR3("String3");
		equalTest.setSTR4("String4");
		equalTest.setSTR5("String5");

		OCMEngine.INSTANCE.Init(config);

		Map<String, List<StringEntity>> ret = OCMEngine.INSTANCE.getEntities(StringEntity.class);

		StringEntity result = ret.values().iterator().next().get(0);

		assertEquals(equalTest, result);
	}

}
