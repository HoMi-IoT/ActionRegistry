package org.homi.plugin.ar;

import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.commander.CommanderBuilder;
import org.homi.plugin.ar.actions.defenition.ActionDefenitionVisitor;
import org.homi.plugins.ar.specification.ARSpec;
import org.homi.plugins.ar.specification.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractBasicPlugin{
//	public static Map<String, IBasicPlugin> plugins = new ConcurrentHashMap<>();
//	public static 
	
	public static Queue<IAction> actions = new ConcurrentLinkedQueue<>();		
	public static ActionDefenitionVisitor adv = new ActionDefenitionVisitor();
	private IPluginRegistryListener pluginRegistryListener = new PluginRegistryListener();
	private Logger logger = LoggerFactory.getLogger(ActionRegistry.class);
	
	@Override
	public void setup() {
		logger.trace("ActionRegistry starting setup");
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		Commander<ARSpec> commander = cb.onCommandEquals(ARSpec.CALL, (args)->{return null;}).build();
		this.addCommander(ARSpec.class, commander);
		this.getPluginProvider().addPluginRegistryListener(this.pluginRegistryListener);
		logger.trace("ActionRegistry finished setup");
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}



}
