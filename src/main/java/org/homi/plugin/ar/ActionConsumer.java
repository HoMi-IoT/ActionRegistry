package org.homi.plugin.ar;

import org.homi.plugin.ARspec.IAction;

public abstract class ActionConsumer {
	private ActionConsumer next;
	public abstract Object doAction(IAction a);
	public void setNext(ActionConsumer ac) {
		if(this.next == null) {
			this.next = ac;
			return;
		}
		else {
			this.next.setNext(ac);
		}
	}
	
	public ActionConsumer getNext() {
		return this.next;
	}
}
