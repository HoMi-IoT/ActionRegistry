package org.homi.plugin.ar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.ar.actions.SpecificationAction;
import org.homi.plugin.ar.actions.defenition.ActionDefenitionVisitor;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.TypeDef;
import org.homi.plugins.ar.specification.actions.arguments.VariableArgument;
import org.homi.plugins.ar.specification.actions.plugin.SpecificationActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginRegistryListener implements IPluginRegistryListener {
	
	private Logger logger = LoggerFactory.getLogger(PluginRegistryListener.class);
	
	@Override
	public void addPlugin(IPlugin addedPlugin) {
		addPluginInternal(addedPlugin);
	}
	
	private  <T extends Enum<T> & ISpecification> void addPluginInternal(IPlugin addedPlugin) {
		List<Class<T>> specs;
		logger.trace("plugin {} Added", addedPlugin.id());
		try {
			specs = ((IBasicPlugin) addedPlugin).getSpecifications();
			for(Class<T> s : specs) {
				buildSpecificationActions(addedPlugin, s);
			}
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}

	private <T extends Enum<T> & ISpecification> void buildSpecificationActions(IPlugin plugin, Class<T> s) {
		logger.trace("plugin has {} commands in specification {}", s.getEnumConstants().length, s.getAnnotation(SpecificationID.class).id());
		for(T a : s.getEnumConstants()) {
			logger.trace("building command {}", a.name());
			buildAction(plugin, a);
		}
	}

	private <T extends Enum<T> & ISpecification> void buildAction(IPlugin plugin, T a) {

		String cmdName = a.name();
		List<TypeDef<?>> parameterTypes = a.getParameterTypes();

		List<VariableArgument<?>> ap = new ArrayList<VariableArgument<?>>();
		BuildVariableArguments(parameterTypes, ap);
		SpecificationActionDefinition sad = new SpecificationActionDefinition(plugin, a);
		logger.trace("built plugin {}, command {}", plugin.id(), cmdName);
		sad.accept(ActionRegistry.adv);
	}

	private <T extends Serializable> void BuildVariableArguments(List<TypeDef<?>> parameterTypes, List<VariableArgument<?>> ap) {
		for(int i=0; i< parameterTypes.size(); i++ ) {
			ap.add(new VariableArgument<T>((Class<T>) parameterTypes.get(i).getType(), Integer.toString(i)));
		}
	}

	@Override
	public void removePlugin(IPlugin arg0) {
		// TODO Auto-generated method stub
	}

}
