package com.github.ocm.mapping;

import org.junit.Test;

import com.github.ocm.exceptions.ClassValidationException;
import com.github.ocm.exceptions.ParsingException;
import com.github.ocm.mapping.Configuration;
import com.github.ocm.mapping.OCMEngine;

public class ConfigurationTest {

	@Test
	public void testAutomaticClassAndFileDiscovery() throws ClassValidationException, ParsingException {
		Configuration config = new Configuration.Builder("./testRes").setClassAutoDiscovery(true).build();
		OCMEngine.INSTANCE.Init(config);

		if (OCMEngine.INSTANCE.filesToBeParsed.isEmpty()) {
			throw new AssertionError();
		}

		// if(OCMEngine.INSTANCE.mappingEngine.classMapping.isEmpty()) {
		// throw new AssertionError();
		// }

	}

}
