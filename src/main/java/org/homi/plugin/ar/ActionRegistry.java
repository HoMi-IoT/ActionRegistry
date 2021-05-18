package org.homi.plugin.ar;
import org.homi.plugin.api.*;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.ARspec.ARSpec;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractPlugin implements IPluginRegistryListener{
	private Map<AbstractPlugin, List<Class<? extends ISpecification>>> abstractPluginToSpecMappings = new HashMap<>();
	private Map<String, List<Class<? extends ISpecification>>> pluginIdToSpecMappings = new HashMap<>();
	
		
		
	private PluginParser pluginParser = new PluginParser();
	@Override
	public void setup() {	
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		
		this.addCommander(ARSpec.class, cb.onCommandEquals(ARSpec.CALL, this::call).build());
		
		this.getPluginProvider().addPluginRegistryListener(this);
	}
	
	public Object call(Object... objects) {
		AbstractPlugin p = findPluginForSpec((String)objects[0]);
		System.out.println("Requested spec is: " + (String)objects[0]);
		System.out.println("Current specs are: ");
		for(Map.Entry<AbstractPlugin, List<Class<? extends ISpecification>>> entry : abstractPluginToSpecMappings.entrySet()) {
			List<Class<? extends ISpecification>> specs = entry.getValue();
			for(Class<? extends ISpecification> spec : specs) {
				System.out.println(spec.getAnnotation(SpecificationID.class).id());
			}
		}
		if(p != null) {
			System.out.println("Making call to plugin");
			return sendCommandToPlugin(p, (String)objects[0], (String)objects[1], (Object[])objects[2]);
		}
		System.out.println("No plugin for spec was found");
		return null;
	}

	public void addPluginInternal(AbstractPlugin plugin) {
		
		abstractPluginToSpecMappings.put(plugin, plugin.getSpecifications());
		pluginIdToSpecMappings.put(plugin.getClass().getAnnotation(PluginID.class).id(), plugin.getSpecifications());
		
	}

	public <T extends Enum<?> & ISpecification> Object sendCommandToPlugin(AbstractPlugin plugin, String specID, String command, Object... args) {

		List<Class<? extends ISpecification>> specs = abstractPluginToSpecMappings.get(plugin);
		Class<? extends ISpecification> spec = getSpecByName(specs, specID);
		T cmd = getCommandByName(command, List.of(spec.getEnumConstants()));
		
		
		
		/*
		 * for(Object arg : args) {
		 * 
		 * }
		 */
		
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

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlugin(IPlugin arg0) {
		
		this.addPluginInternal((AbstractPlugin)arg0);
		
	}

	@Override
	public void removePlugin(IPlugin arg0) {
		// TODO Auto-generated method stub
		
	}

}
