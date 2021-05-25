package org.homi.plugin.test.ARTest;

import org.homi.plugin.ARspec.ARSpec;
import org.homi.plugin.api.Commander;
import org.homi.plugin.ar.ActionRegistry;
import org.homi.plugin.ar.PluginParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionRegistryTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void createActionRegistry() {
		ActionRegistry ar = new ActionRegistry();
	}
	
	@Test
	void setUpActionRegistry() {
		ActionRegistry ar = new ActionRegistry();
		ar.setup();
	}
	
	@Test
	void getCommanderFromSpecID() {
		ActionRegistry ar = new ActionRegistry();
		ar.setup();
		Commander<ARSpec> c = ar.getCommander(ARSpec.class);
	}
	
	@Test
	void pluginParserGetsSpecs() {
		PluginParser pp = new PluginParser();
		DummyPlugin d = new DummyPlugin();
		d.setup();
		pp.getSpecs(d);
	}
	
	@Test
	void pluginParserGetsCommandsFromSpec() {
		PluginParser pp = new PluginParser();
		
		
		
		
		//List<String> bleCommands = pp.getCommands(BLESpec.class);
		//assertEquals(bleCommands.get(0), "CONNECT");
		
		
	}
	
	@Test
	void actionRegistryGetsNotifiedOfNewPlugin(){
		ActionRegistry ar = new ActionRegistry();
		ar.setup();
		DummyPlugin d = new DummyPlugin();
		ar.addPlugin(d);
		
	}
	
	@Test
	void actionRegistryIssuesCommandToPlugin() {
		ActionRegistry ar = new ActionRegistry();
		ar.setup();
		DummyPlugin d = new DummyPlugin();
		d.setup();
		ar.addPlugin(d);
		//Assertions.assertTrue((boolean)ar.sendCommandToPlugin(d, "BLESpec", "CONNECT", "arg"));
	}
	
	@Test
	void actionRegistryFindsPluginThatImplementsCertainSpec() {
		ActionRegistry ar = new ActionRegistry();
		ar.setup();
		DummyPlugin d = new DummyPlugin();
		d.setup();
		ar.addPlugin(d);
		//AbstractPlugin p = ar.findPluginForSpec("BLESpec");
		//Assertions.assertTrue(p.getClass().getAnnotation(PluginID.class).id().equals("DummyPlugin"));
		
//		Assertions.assertTrue(p.getSpecifications().get(0).getAnnotation(SpecificationID.class).id().equals("BLESpec"));
	}
	
	
	
	

}
