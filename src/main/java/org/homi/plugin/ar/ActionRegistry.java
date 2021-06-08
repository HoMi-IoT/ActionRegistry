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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractBasicPlugin{
	
	public static Queue<IAction> actions = new ConcurrentLinkedQueue<>();		
	public static ActionDefenitionVisitor adv = new ActionDefenitionVisitor();
	private IPluginRegistryListener pluginRegistryListener = new PluginRegistryListener();
	private Logger logger = LoggerFactory.getLogger(ActionRegistry.class);
	
	@Override
	public void setup() {
		logger.trace("ActionRegistry starting setup");
		logger.trace("Core has loader {}", this.getClass().getClassLoader());
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		Commander<ARSpec> commander = cb
				.onCommandEquals(ARSpec.DEFINE, (args)->{
					IActionDefenition actionDefenition = (IActionDefenition) args[0];
					actionDefenition.accept(adv);
					return null;
					})
				.onCommandEquals(ARSpec.GET_ACTION, (args)->{
					ActionQuery aq = (ActionQuery) args[0];
					for(var action: ActionRegistry.actions) {
						if(action.match(aq)) {
							return action.getInvocationUnit();
						}
					}
					return null;})
				.onCommandEquals(ARSpec.TEST, (args)->{
					logger.trace("Test received arg {}", args[0]);
					return Boolean.FALSE;})
//				.onCommandEquals(ARSpec.TEST2, (args)->{
//					logger.trace("Test2 received arg {}", ((Custom)args[0]));
//					return Boolean.TRUE;})
//				.onCommandEquals(ARSpec.TEST3, (args)->{
//					logger.trace("Test3 received");
//					return new Custom("Mason");})
				.build();
		this.addCommander(ARSpec.class, commander);
					
		this.getPluginProvider().addPluginRegistryListener(this.pluginRegistryListener);
		logger.trace("ActionRegistry finished setup");
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}


}
