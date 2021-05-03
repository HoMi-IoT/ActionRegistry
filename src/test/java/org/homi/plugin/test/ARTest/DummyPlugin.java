package org.homi.plugin.test.ARTest;

import org.homi.plugin.BLEspec.BLESpec;
import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.api.Commander;
import org.homi.plugin.api.CommanderBuilder;
import org.homi.plugin.api.PluginID;

@PluginID(id = "DummyPlugin")
public class DummyPlugin extends AbstractPlugin{

	@Override
	public void setup() {
		
		CommanderBuilder<BLESpec> cb = new CommanderBuilder<>(BLESpec.class) ;
		
		
		
		Commander<BLESpec> c = cb.onCommandEquals(BLESpec.CONNECT, this::connect).
		onCommandEquals(BLESpec.DISCONNECT, this::disconnect).
		onCommandEquals(BLESpec.PAIR, this::pair).
		onCommandEquals(BLESpec.WRITE, this::write).
		onCommandEquals(BLESpec.READ, this::read).
		build();
		
		addCommander(BLESpec.class, c);
		
	}
	
	private boolean connect(Object...args) {
		return true;
	}
	
	private boolean disconnect(Object ...args) {
		return true;
	}
	
	private boolean pair(Object...args) {
		return true;
	}
	
	private boolean write(Object ...args) {
		return true;
	}
	
	private byte[] read(Object ...args) {
		return new byte[] {0x01};
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}
	


}
