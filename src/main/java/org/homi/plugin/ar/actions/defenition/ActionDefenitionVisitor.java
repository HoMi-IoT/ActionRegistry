package org.homi.plugin.ar.actions.defenition;

import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.ar.ActionRegistry;
import org.homi.plugin.ar.actions.SpecificationAction;
import org.homi.plugins.ar.specification.actions.CustomActionDefenition;
import org.homi.plugins.ar.specification.actions.IActionDefenitionVisitor;
import org.homi.plugins.ar.specification.actions.ScriptActionDefinition;
import org.homi.plugins.ar.specification.actions.plugin.SpecificationActionDefinition;

public class ActionDefenitionVisitor implements IActionDefenitionVisitor {

	@Override
	public void visit(SpecificationActionDefinition sad) {
		List<String> parameters = new ArrayList<>();
		for(int i=0; i< sad.getParameters().size(); i++) {
			parameters.add(Integer.toString(i));
		}
		SpecificationAction specificationAction = new SpecificationAction(null, sad.getPluginID(), sad.getSpecificationID(), sad.getCommand());
		ActionRegistry.actions.add(specificationAction);
	}

	@Override
	public void visit(CustomActionDefenition ca) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ScriptActionDefinition sa) {
		// TODO Auto-generated method stub
	}

}
