package org.homi.plugin.ar;
import org.homi.plugin.api.*;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.ParameterType;
import org.homi.plugin.specification.SpecificationID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.ARspec.*;



@PluginID(id = "ActionRegistry")
public class ActionRegistry extends AbstractPlugin implements IPluginRegistryListener{
	static Map<AbstractPlugin, List<Class<? extends ISpecification>>> abstractPluginToSpecMappings = new HashMap<>();
	static Map<String, List<Class<? extends ISpecification>>> pluginIdToSpecMappings = new HashMap<>();
	
		
		
	private PluginParser pluginParser = new PluginParser();
	private IActionBuilder specBuilder;
	@Override
	public void setup() {	
		specBuilder = new SpecActionBuilder();
		
		CommanderBuilder<ARSpec> cb = new CommanderBuilder<>(ARSpec.class) ;
		
		this.addCommander(ARSpec.class, cb.onCommandEquals(ARSpec.CALL, args -> {
			try {
				return call(args);
			} catch (TypeMismatchException | ClassCastException | IllegalArgumentException | ClassNotFoundException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return args;
		}).build());
		
		this.getPluginProvider().addPluginRegistryListener(this);
	}
	
	public Object call(Object... objects) throws TypeMismatchException, ClassCastException, IllegalArgumentException, ClassNotFoundException, IOException {
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

	/*
	 * public void addPluginInternal(AbstractPlugin plugin) {
	 * 
	 * abstractPluginToSpecMappings.put(plugin, plugin.getSpecifications());
	 * pluginIdToSpecMappings.put(plugin.getClass().getAnnotation(PluginID.class).id
	 * (), plugin.getSpecifications());
	 * 
	 * }
	 */

	public static <T extends Enum<?> & ISpecification> Object sendCommandToPlugin(AbstractPlugin plugin, String specID, String command, Object... args) throws TypeMismatchException, ClassCastException, IllegalArgumentException, ClassNotFoundException, IOException {

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
	
	public static <T extends Enum<?> & ISpecification> T getCommandByName(String cmd, List<? extends ISpecification> cmds){
		for (ISpecification c : cmds) {
			if(cmd.equals(c.name())) {
				return (T)c;
			}
		}
		return null;
	}
	
	public static Class<? extends ISpecification> getSpecByName(List<Class<? extends ISpecification>> spec, String specID){
		for(Class<? extends ISpecification> s : spec) {
			if(s.getAnnotationsByType(SpecificationID.class)[0].id().equals(specID)) {
				return s;
			}
		}
		return null;
	}

	public static AbstractPlugin findPluginForSpec(String specID) {
		
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
		
		AbstractPlugin plugin = (AbstractPlugin)arg0;
		String pluginID = plugin.getClass().getAnnotation(PluginID.class).id();
		List<Class<? extends ISpecification>> specs = plugin.getSpecifications();
		for(Class<? extends ISpecification> s : specs) {
			String specID = s.getAnnotation(SpecificationID.class).id();
			for(ISpecification a : s.getEnumConstants()) {
				String cmdName = a.name();
				ParameterType<?>[] pTypes = a.getParameterTypes();
				List<ActionParameter<?>> ap = new ArrayList<ActionParameter<?>>();
				for(ParameterType<?> p : pTypes) {
					ap.add(new ActionParameter<>(p.getTypeClass()));
				}
				SpecAction sa = new SpecAction(specID, cmdName, );
			}
		}
		
		
	}

	@Override
	public void removePlugin(IPlugin arg0) {
		// TODO Auto-generated method stub
		
	}

}
