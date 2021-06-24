package org.homi.plugin.ar.actions.defenition;

import java.util.ArrayList;
import java.util.List;

import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.ar.ActionRegistry;
import org.homi.plugin.ar.actions.ScriptAction;
import org.homi.plugin.ar.actions.SpecificationAction;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugins.ar.specification.actions.CustomActionDefenition;
import org.homi.plugins.ar.specification.actions.IActionDefenitionVisitor;
import org.homi.plugins.ar.specification.actions.ScriptActionDefinition;
import org.homi.plugins.ar.specification.actions.plugin.SpecificationActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionDefenitionVisitor implements IActionDefenitionVisitor {

	private Logger logger = LoggerFactory.getLogger(ActionDefenitionVisitor.class);
	@Override
	public void visit(SpecificationActionDefinition sad) {
		logger.trace("buildign Specification action");
		List<String> parameters = new ArrayList<>();
		for(int i=0; i< sad.getParameters().size(); i++) {
			parameters.add(Integer.toString(i));
		}
		try {
			var commander = ((IBasicPlugin)sad.getPlugin()).getCommander((Class<? extends ISpecification>) sad.getCommand().getClass());
			logger.trace("made parameters for action {}",  sad.getCommand());
			SpecificationAction<?> specificationAction = buildSpecificationAction(sad, parameters, commander);

			logger.trace("Specification action built");
			ActionRegistry.actions.add(specificationAction);
		} catch (PluginException e) {
			e.printStackTrace();
		}
		
	}

	private <T extends Enum<T> & ISpecification> SpecificationAction<T> buildSpecificationAction(SpecificationActionDefinition<T> sad, List<String> parameters,	Commander<T> commander) {
		return new SpecificationAction(sad.getCommand().getClass(), parameters, commander, sad.getCommand(), sad.getPlugin());
	}

	@Override
	public void visit(CustomActionDefenition ca) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ScriptActionDefinition sa) {
		logger.trace("Script action built: " + sa.getCommand());
		ActionRegistry.actions.add(new ScriptAction(sa.getCommand(), sa.getTags(), sa.getActionQuery()));
	}

}
