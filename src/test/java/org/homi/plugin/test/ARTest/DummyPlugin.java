package org.homi.plugin.test.ARTest;


import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.commander.CommanderBuilder;

@PluginID(id = "DummyPlugin")
public class DummyPlugin extends AbstractBasicPlugin{

	@Override
	public void setup() {
//		CommanderBuilder<BLESpec> cb = new CommanderBuilder<>(BLESpec.class) ;		
//		Commander<BLESpec> c = cb.onCommandEquals(BLESpec.CONNECT, this::connect).
//		onCommandEquals(BLESpec.DISCONNECT, this::disconnect).
//		onCommandEquals(BLESpec.PAIR, this::pair).
//		onCommandEquals(BLESpec.WRITE, this::write).
//		onCommandEquals(BLESpec.READ, this::read).
//		build();
//		addCommander(BLESpec.class, c);
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
