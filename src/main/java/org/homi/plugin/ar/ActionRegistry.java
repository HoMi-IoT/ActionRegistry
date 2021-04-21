package org.homi.plugin.ar;
import org.homi.plugin.api.*;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.ARspec.ARSpec;
import org.homi.plugin.BLEspec.BLESpec;


@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractPlugin{
	private Map<AbstractPlugin, List<Class<? extends ISpecification>>> abstractPluginToSpecMappings = new HashMap<>();
	private Map<String, List<Class<? extends ISpecification>>> pluginIdToSpecMappings = new HashMap<>();
	
		
		
	private PluginParser pluginParser = new PluginParser();
	@Override
	public void setup() {	
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		cb.onCommandEquals(ARSpec.CALL, this::call).build();
	}
	
	public Object call(Object... objects) {
		AbstractPlugin p = findPluginForSpec((String)objects[0]);
		if(p != null) {
			return sendCommandToPlugin(p, (String)objects[0], (String)objects[1], objects[2]);
		}
		return null;
	}

	public void addPlugin(AbstractPlugin plugin) {
		
		abstractPluginToSpecMappings.put(plugin, plugin.getSpecifications());
		pluginIdToSpecMappings.put(plugin.getClass().getAnnotation(PluginID.class).id(), plugin.getSpecifications());
		
	}

	public <T extends Enum<?> & ISpecification> Object sendCommandToPlugin(AbstractPlugin plugin, String specID, String command, Object... args) {
		
		List<Class<? extends ISpecification>> specs = abstractPluginToSpecMappings.get(plugin);
		Class<? extends ISpecification> spec = getSpecByName(specs, specID);
		T cmd = getCommandByName(command, List.of(spec.getEnumConstants()));
		Commander<?> c = plugin.getCommander(spec);
		return c.execute(cmd, args);
		
		
	}
	
	public <T extends Enum<?> & ISpecification> T getCommandByName(String cmd, List<? extends ISpecification> cmds){
		for (ISpecification c : cmds) {
			if(cmd.equals(c.name())) {
				return (T)c;
			}
		}
		return null;
	}
	
	public Class<? extends ISpecification> getSpecByName(List<Class<? extends ISpecification>> spec, String specID){
		for(Class<? extends ISpecification> s : spec) {
			if(s.getAnnotationsByType(SpecificationID.class)[0].id().equals(specID)) {
				return s;
			}
		}
		return null;
	}

	public AbstractPlugin findPluginForSpec(String specID) {
		
		for(Map.Entry<AbstractPlugin, List<Class<? extends ISpecification>>> entry : abstractPluginToSpecMappings.entrySet()) {
			List<Class<? extends ISpecification>> specs = entry.getValue();
			for(Class<? extends ISpecification> spec : specs) {
				if(spec.getAnnotation(SpecificationID.class).id().equals(specID)) {
					return entry.getKey();
				}
			}
		}
		
		return null;
		
		
	}

}
