package org.homi.plugin.ar;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.api.*;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;


public class PluginParser {

	public List<String> getSpecs(AbstractPlugin plugin) {
		List<Class<? extends ISpecification>> specs = plugin.getSpecifications();
		List<String> specIds = new ArrayList<>();
		for(Class<?> spec : specs) {
			SpecificationID[] a = spec.getAnnotationsByType(SpecificationID.class);
			String specId = a[0].id();
			specIds.add(specId);
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
