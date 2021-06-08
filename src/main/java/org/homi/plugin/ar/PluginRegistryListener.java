package org.homi.plugin.ar;

import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.types.TypeDef;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.arguments.IActionArgument;
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
				for(SpecificationActionDefinition<T> sad: buildSpecificationActions(addedPlugin, s)) {
					sad.accept(ActionRegistry.adv);
				}
			}
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}

	private <T extends Enum<T> & ISpecification> List<SpecificationActionDefinition<T>> buildSpecificationActions(IPlugin plugin, Class<T> s) {
		logger.trace("plugin has {} commands in specification {}", s.getEnumConstants().length, s.getAnnotation(SpecificationID.class).id());
		List<SpecificationActionDefinition<T>> specificationActionDefinitions = new ArrayList<>();
		for(T command : s.getEnumConstants()) {
			specificationActionDefinitions.add(buildAction(plugin, command));
		}
		return specificationActionDefinitions;
	}

	private <T extends Enum<T> & ISpecification> SpecificationActionDefinition<T> buildAction(IPlugin plugin, T command) {
		logger.trace("building command {}", command.name());
		SpecificationActionDefinition<T> sad = new SpecificationActionDefinition<>(plugin, command, buildArgumentList(command.getParameterTypes()));
		logger.trace("built SAD plugin {}, command {}", plugin.id(), command.name());
		logger.debug("SAD for command has {} parameters", sad.getParameters().size());
		return sad;
	}

	private List<IActionArgument<?>> buildArgumentList(List<TypeDef<?>> parameterTypes) {
		List<IActionArgument<?>> argumentList = new ArrayList<>();
		for(int i=0; i<parameterTypes.size(); i++) {
			argumentList.add(buildVariableArgument(parameterTypes.get(i).getType(), i));
		}
		return argumentList;
	}
	
	public <T> VariableArgument buildVariableArgument(Class<T> type, int position){
		return new VariableArgument(type, Integer.toString(position));
	}
	
	@Override
	public void removePlugin(IPlugin plugin) {
		ActionQuery aq = new ActionQuery();
		aq.pluginID(plugin.id());
		ActionRegistry.actions.removeIf((action)->{return action.match(aq);});
	}

}
