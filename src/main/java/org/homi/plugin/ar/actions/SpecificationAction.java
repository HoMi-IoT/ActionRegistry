package org.homi.plugin.ar.actions;

import java.util.List;

import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.ar.ActionRegistry;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecificationAction<T extends Enum<T> & ISpecification> extends AbstractAction {

	private static final long serialVersionUID = -4882060310691045794L;
	private static Logger logger = LoggerFactory.getLogger(SpecificationAction.class);

	private String pluginID;
	private String specificationID;
	private String command;
	
	public SpecificationAction(Class<T> specification, List<String> parameters, Commander<T> commander, T command, IPlugin plugin) {
		super(parameters);
		
		this.pluginID = plugin.id();
		this.specificationID = command.id();
		this.command = command.name();
				
		this.setInvocationUnit(
				(arguments, classLoader)->{
					Object[] args = new Object[arguments.size()];
					for(var arg: arguments.entrySet()) {
						args[Integer.parseInt(arg.getKey())] = arg.getValue(); 
					}
					try {
						logger.trace("::::::::::::::::::::::::: passed classloader: "+ classLoader + "\n command: " + command.name() + " loader: " + command.getClass().getClassLoader());
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
//		logger.trace("matching with "+ this.pluginID+this.specificationID+this.command);
		if(aq.getType() == ActionQuery.TYPE.SPECIFICATION
				&& (aq.getPluginID() == null || aq.getPluginID().equals(this.pluginID))
				&& (aq.getSpecificationID() == null || aq.getSpecificationID().equals(this.specificationID))
				&& (aq.getCommand() == null || aq.getCommand().equals(this.command))) {
//			logger.trace("matched!!!!!!!!!!! ");
			return true;
		}
//		logger.trace("not matched :(");
		return false;
	}

}
