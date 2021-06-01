package org.homi.plugin.ar.actions;

import java.util.List;

import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.AbstractAction;

public class SpecificationAction<T extends Enum<T> & ISpecification> extends AbstractAction {

	private static final long serialVersionUID = -4882060310691045794L;

	public <T extends Enum<T> & ISpecification> SpecificationAction(Class<T> specification ,List<String> parameters, Commander<T> commander, T command) {
		super(parameters);
		this.setInvocationUnit(
				(arguments)->{
					Object[] args = new Object[parameters.size()];
					for(var arg: this.getArguments().entrySet()) {
						args[Integer.parseInt(arg.getKey())] = arg.getValue(); 
					}
					try {
						return commander.execute(command, args);
					} catch (InvalidArgumentException | ArgumentLengthException | InternalPluginException e) {
						throw new RuntimeException(e);
					}
				}
				);
	}

}
