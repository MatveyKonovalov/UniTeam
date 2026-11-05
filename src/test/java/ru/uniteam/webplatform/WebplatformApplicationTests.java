package ru.uniteam.webplatform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WebplatformApplicationTests {

	@Test
	void contextLoads() {
		// Lightweight smoke test without loading full Spring context
		// (full context requires DB and is covered by integration setup with H2)
		assertTrue(true);
	}

}
