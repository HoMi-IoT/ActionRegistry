package org.homi.plugin.ar.actions;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;

import org.homi.plugins.ar.specification.actions.ActionQuery;

public interface IAction {
	public <T extends Serializable> void SetArgument(String parameter, T argument);
	public void SetArguments(Map<String, Serializable> args);
	public BiFunction< Map<String, Serializable>, ClassLoader, Object> getInvocationUnit();
	public <R> R run();
	public <R> R run(Serializable... args);
	public boolean match(ActionQuery aq);
}