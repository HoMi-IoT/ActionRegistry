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

public class PluginRegistryListener implements IPluginRegistryListener {

	@Override
	public void addPlugin(IPlugin addedPlugin) {
		String pluginID = addedPlugin.id();
		List<Class<? extends ISpecification>> specs;
		try {
			specs = ((IBasicPlugin) addedPlugin).getSpecifications();
			for(Class<? extends ISpecification> s : specs) {
				buildSpecificationActions(pluginID, s);
			}
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}

	private void buildSpecificationActions(String pluginID, Class<? extends ISpecification> s) {
		String specificationID = s.getAnnotation(SpecificationID.class).id();
		for(ISpecification a : s.getEnumConstants()) {
			buildAction(pluginID, specificationID, a);
		}
	}

	private void buildAction(String pluginID, String specificationID, ISpecification a) {
		String cmdName = a.name();
		List<TypeDef<?>> parameterTypes = a.getParameterTypes();
		List<VariableArgument<?>> ap = new ArrayList<VariableArgument<?>>();
		BuildVariableArguments(parameterTypes, ap);
		SpecificationActionDefinition sad = new SpecificationActionDefinition(specificationID, cmdName);
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
