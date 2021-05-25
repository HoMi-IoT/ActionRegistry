package org.homi.plugin.ar;

import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;


public class PluginParser {

	public List<String> getSpecs(IBasicPlugin plugin) {
		List<Class<? extends ISpecification>> specs;
		List<String> specIds = new ArrayList<>();
		try {
			specs = plugin.getSpecifications();
			for(Class<?> spec : specs) {
				SpecificationID[] a = spec.getAnnotationsByType(SpecificationID.class);
				String specId = a[0].id();
				specIds.add(specId);
			}
		} catch (PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return specIds;
		
	}

	public List<String> getCommands(Class<? extends ISpecification> spec) {
	
			List<? extends ISpecification> cmds = List.of(spec.getEnumConstants());
			List<String> strCmds = new ArrayList<>();
			for (ISpecification c : cmds) {
				strCmds.add(c.name());
			}
			
			return strCmds;
		
	}
	
	
	

}
