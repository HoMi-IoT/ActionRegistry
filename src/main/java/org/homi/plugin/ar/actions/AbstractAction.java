package org.homi.plugin.ar.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractAction implements IAction {

	private static final long serialVersionUID = -7133010062896916619L;

	private BiFunction< Map<String, Serializable>, ClassLoader, Object> invocationUnit;
	private Map<String, Serializable> arguments;
	private List<String> parameters;
	
	public AbstractAction(List<String> parameters) {
		this.parameters = List.copyOf(parameters);
		arguments = new HashMap<>();
		for(String param: this.parameters) {
			arguments.putIfAbsent(param, null);
		}
	}
	
	protected <T extends BiFunction<Map<String, Serializable>, ClassLoader, Object> & Serializable> void setInvocationUnit(T iu) {
		this.invocationUnit = iu;
	}
	
	@Override
	public BiFunction< Map<String, Serializable>, ClassLoader, Object> getInvocationUnit(){
		return this.invocationUnit;
	}
	
	protected Map<String, ? extends Serializable> getArguments(){
		return this.arguments;
	}

	protected List<String> getParameters(){
		return this.parameters;
	}
	
	@Override
	public <T extends Serializable> void SetArgument(String parameter, T argument) {
		System.out.println("setting argument "+parameter+" to value "+ argument);
		arguments.put(parameter, argument);
	}

	@Override
	public void SetArguments(Map<String, Serializable> args) {
		arguments.putAll(args);
	}

	@Override
	public final <R> R run() {
//		System.out.println("running");
//		return (R) this.invocationUnit.apply(arguments);
		throw new UnsupportedOperationException();
	};

	@Override
	public final <R> R run(Serializable... args) {
		System.out.println("running with args");
		for(int i=0; i< this.parameters.size(); i++) {
			this.arguments.put(this.parameters.get(i), args[i]);
		}
		return run();
	};
}
