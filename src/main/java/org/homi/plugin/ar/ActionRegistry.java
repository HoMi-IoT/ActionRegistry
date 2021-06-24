package org.homi.plugin.ar;

import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.commander.CommanderBuilder;
import org.homi.plugin.ar.actions.IAction;
import org.homi.plugin.ar.actions.defenition.ActionDefenitionVisitor;
import org.homi.plugins.ar.specification.ARSpec;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.IActionDefenition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiFunction;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractBasicPlugin{
	
	public static Queue<IAction> actions = new ConcurrentLinkedQueue<>();		
	public static ActionDefenitionVisitor adv = new ActionDefenitionVisitor();
	private IPluginRegistryListener pluginRegistryListener = new PluginRegistryListener();
	private static Logger logger = LoggerFactory.getLogger(ActionRegistry.class);
	
	@Override
	public void setup() {
		logger.trace("ActionRegistry starting setup");
		logger.trace("ActionRegistry has classloader {}", this.getClass().getClassLoader());
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		Commander<ARSpec> commander = cb
				.onCommandEquals(ARSpec.DEFINE, ActionRegistry::define)
				.onCommandEquals(ARSpec.GET_ACTION, ActionRegistry::getAction)
				.build();
		this.addCommander(ARSpec.class, commander);
					
		this.getPluginProvider().addPluginRegistryListener(this.pluginRegistryListener);
		logger.trace("ActionRegistry finished setup");
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}
	
	public static BiFunction<Map<String, Serializable>, ClassLoader, Object> getAction(Object...args){
		ActionQuery aq = (ActionQuery) args[0];

		logger.trace("got query " + aq.getPluginID() + " "+aq.getSpecificationID() + " "+aq.getCommand() );
		for(var action: ActionRegistry.actions) {
//			logger.trace("matching " + action );
			if(action.match(aq)) {
				return action.getInvocationUnit();
			}
		}
		return null;
	}
	
	public static Void define(Object...args){
		IActionDefenition actionDefenition = (IActionDefenition) args[0];
		actionDefenition.accept(adv);
		return null;
	}


}
