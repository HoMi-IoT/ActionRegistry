package org.homi.plugin.ar;
import org.homi.plugin.api.*;
import org.homi.plugin.specification.ISpecification;
import java.util.List;
import java.util.Map;

import org.homi.plugin.ARspec.ARSpec;


@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractPlugin{
	private Map<AbstractPlugin, List<Class<? extends ISpecification>>> abstractPluginToSpecMappings;
	private Map<String, List<Class<? extends ISpecification>>> pluginIdToSpecMappings;
	
	//private IPluginProvider core ;
	PluginParser pluginParser;
	@Override
	public void setup() {	
		
	}
	
	public Object call(Object...objects) {
		return null;
	}

	public void addPlugin(AbstractPlugin plugin) {
		
		abstractPluginToSpecMappings.put(plugin, plugin.getSpecifications());
		pluginIdToSpecMappings.put(plugin.getClass().getAnnotationsByType(PluginID.class)[0].id(), plugin.getSpecifications());
		
	}

}
