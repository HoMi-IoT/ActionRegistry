package org.homi.plugin.ar;

import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.commander.CommanderBuilder;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.ar.actions.defenition.ActionDefenitionVisitor;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.ARSpec;
import org.homi.plugins.ar.specification.actions.IAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractBasicPlugin{
	
	public static Queue<IAction> actions = new ConcurrentLinkedQueue<>();		
	public static ActionDefenitionVisitor adv = new ActionDefenitionVisitor();
	private IPluginRegistryListener pluginRegistryListener = new PluginRegistryListener();
	
	@Override
	public void setup() {
		
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		Commander<ARSpec> commander = cb.onCommandEquals(ARSpec.CALL, (args)->{return null;}).build();
		this.addCommander(ARSpec.class, commander);
		this.getPluginProvider().addPluginRegistryListener(this.pluginRegistryListener);
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}



}
