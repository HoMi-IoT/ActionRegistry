package org.homi.plugin.ar.actions;

import java.util.List;

import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.ActionQuery;

public class SpecificationAction<T extends Enum<T> & ISpecification> extends AbstractAction {

	private static final long serialVersionUID = -4882060310691045794L;

	private String pluginID;
	private String specificationID;
	private String command;
	
	public SpecificationAction(Class<T> specification, List<String> parameters, Commander<T> commander, T command, IPlugin plugin) {
		super(parameters);
		
		this.pluginID = plugin.id();
		this.specificationID = command.id();
		this.command = command.name();
		
		System.out.println("NUMBER OF PARAMERTERS IS: " + parameters.size());
		
		this.setInvocationUnit(
				(arguments, classLoader)->{
					Object[] args = new Object[parameters.size()];
					System.out.println("_____________1___________________");
					System.out.println("arguments size: " + arguments.size());
					for(var arg: arguments.entrySet()) {
						args[Integer.parseInt(arg.getKey())] = arg.getValue(); 
					}
					System.out.println("NUMBER OF PARAMERTERS IN INVOCATION UNIT IS: " + parameters.size());
					System.out.println("_____________2___________________");
					try {
						var ret = commander.execute(command, args);
						return command.getReturnType().process(ret, classLoader);
					} catch (InvalidArgumentException | ArgumentLengthException | InternalPluginException e) {
						throw new RuntimeException(e);
					}
				}
				);
	}

	@Override
	public boolean match(ActionQuery aq) {
		if(aq.getType() == ActionQuery.TYPE.SPECIFICATION
				&& (aq.getSpecificationID() == null || aq.getSpecificationID().equals(this.specificationID))
				&& (aq.getPluginID() == null || aq.getPluginID().equals(this.pluginID))
				&& (aq.getCommand() == null || aq.getCommand().equals(this.command)))
			return true;
		return false;
	}

}
