package org.homi.plugin.ar.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.ar.ActionRegistry;
import org.homi.plugins.ar.specification.actions.ActionQuery;

public class ScriptAction extends AbstractAction {

	private String command;
	private List<String> tags;
	
	public ScriptAction(String command, List<String> tags, ActionQuery actionQuery) {
//		super(List.of());
		this.tags = tags;
		this.command = command;
		var action = ActionRegistry.getAction(actionQuery);
		this.setInvocationUnit(
				(arguments, classLoader)->{
					System.out.println("calling script action: " + command);
					try {
//						Map<String,Serializable> args = new HashMap<String,Serializable>();
//						args.put("0", command);
//						args.put("1", (Serializable) arguments);
						return action.apply(Map.of("0", command, "1", (Serializable)arguments), classLoader);
//						return command.getReturnType().process(ret, classLoader);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			);
	}

	@Override
	public boolean match(ActionQuery aq) {
		if(aq.getType() == ActionQuery.TYPE.SCRIPT
				&& (aq.getCommand() == null || aq.getCommand().equals(this.command))
				&& (this.tags.containsAll(aq.getTags())))
			return true;
		return false;
	}

}