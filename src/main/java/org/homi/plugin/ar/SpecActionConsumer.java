package org.homi.plugin.ar;

import java.util.List;
import java.util.Map;

import org.homi.plugin.ARspec.IAction;
import org.homi.plugin.ARspec.SpecAction;
import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public class SpecActionConsumer extends ActionConsumer {

	@Override
	public Object doAction(IAction a) {
		if(a.getKind() == SpecAction.class) {
			SpecAction sa = (SpecAction)a;
			String spec = sa.getSpecTarget();
			String call = sa.getAPICall();
			List<Object> parameters = sa.getParams();
			
			AbstractPlugin p = ActionRegistry.findPluginForSpec(spec);
			System.out.println("Requested spec is: " + spec);
			System.out.println("Current specs are: ");
			for(Map.Entry<AbstractPlugin, List<Class<? extends ISpecification>>> entry : ActionRegistry.abstractPluginToSpecMappings.entrySet()) {
				List<Class<? extends ISpecification>> specs = entry.getValue();
				for(Class<? extends ISpecification> sp : specs) {
					System.out.println(sp.getAnnotation(SpecificationID.class).id());
				}
			}
			if(p != null) {
				System.out.println("Making call to plugin");
				return ActionRegistry.sendCommandToPlugin(p, spec, call, parameters.toArray());
			}
			System.out.println("No plugin for spec was found");
			return null;
			
			
			
			
			
			
		}
		else {
			if(this.getNext() != null) {
				return this.getNext().doAction(a);
			}
		}
		return null;

	}

}
