package org.homi.plugin.ar.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugins.ar.specification.actions.IAction;

public abstract class AbstractAction implements IAction {

	private static final long serialVersionUID = 8375751040389744681L;

	private Map<String, Object> arguments;
	private List<String> parameters;
	
	public AbstractAction(List<String> parameters) {
		this.parameters = List.copyOf(parameters);
		arguments = new HashMap<>();
		for(String param: this.parameters) {
			arguments.putIfAbsent(param, null);
		}
	}
	
	@Override
	public void SetArgument(String parameter, Object argument) {
		arguments.put(parameter, argument);
	}

	@Override
	public void SetArguments(Map<String, ?> args) {
		arguments.putAll(args);
	}

	@Override
	public abstract <R> R run();

	@Override
	public abstract <R> R run(Object... args);
}
