package de.craften.craftenlauncher;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.craftlauncher.launcher.Config;
import com.tngtech.configbuilder.ConfigBuilder;

/**
 * Simple contract test for config builder lib.
 */
public class ConfigTest {

	private String[] args = {"--server=10.0.0.0", "--fullscreen"};

	@Test
	public void testGetArgumentConfigParameter() {
		Config config = ConfigBuilder.on(Config.class).withCommandLineArgs(args).build();

		assertEquals("10.0.0.0", config.getServer());
	}

	@Test
	public void testGetSwitchConfigParameters() {
		Config config = ConfigBuilder.on(Config.class).withCommandLineArgs(args).build();

		assertEquals(true, config.isFullscreen());
	}
}
