

module actionRegistry {
	requires org.homi.plugin.api;
	requires org.homi.plugin.specification;
	requires arSpec;
	requires ble;
	requires bleSpec;
	requires tinyb;
	provides org.homi.plugin.api.IPlugin with org.homi.plugin.ar.ActionRegistry;
}