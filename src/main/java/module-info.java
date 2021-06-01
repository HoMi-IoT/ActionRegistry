

module org.homi.plugins.actionRegistry.plugin {
	requires org.homi.plugin.api;
	requires org.homi.plugin.specification;
	requires org.homi.plugins.actionRegistry.specification;

	requires org.slf4j;
	
	provides org.homi.plugin.api.basicplugin.IBasicPlugin 
		with org.homi.plugin.ar.ActionRegistry;
}