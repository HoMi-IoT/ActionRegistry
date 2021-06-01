

module org.homi.plugins.actionRegistry.plugin {
	requires org.homi.plugin.api;
	requires org.homi.plugin.specification;
	requires org.homi.plugins.actionRegistry.specification;
	provides org.homi.plugin.api.basicplugin.IBasicPlugin 
		with org.homi.plugin.ar.ActionRegistry;
}