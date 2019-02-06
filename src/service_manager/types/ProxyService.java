package service_manager.types;

import java.util.ArrayList;

import service_manager.Activator;

public class ProxyService extends Service {

	private String location;
	private String uri;
	private String wsdl;
	private String protocol;
	private ArrayList<BusinessService> businessServices;
	private ServiceBus serviceBus;

	public ProxyService() {
		super();
		setId(Activator.getDefault().serviceManager.setIdNewProxy(String
				.valueOf((int) (Math.random() * 10000))));
		this.location = "";
		this.uri = "";
		this.wsdl = "";
		this.protocol = "";
		this.businessServices = new ArrayList<BusinessService>();
		this.serviceBus = null;
	}

	public ProxyService(String id, String name, String location, String uri,
			String protocol, String wsdl,
			ArrayList<BusinessService> businessServices, String description,
			ServiceBus serviceBus) {
		super(id, name, description);
		this.location = location;
		this.uri = uri;
		this.wsdl = wsdl;
		this.protocol = protocol;
		this.businessServices = businessServices;
		this.serviceBus = serviceBus;
	}

	public ProxyService(ProxyService proxyService) {
		super(proxyService.getId(), proxyService.getName(), proxyService
				.getDescription());
		this.location = proxyService.getLocation();
		this.uri = proxyService.getUri();
		this.wsdl = proxyService.getWsdl();
		this.protocol = proxyService.getProtocol();
		this.businessServices = proxyService.getBusinessServices();
		this.serviceBus = proxyService.getServiceBus();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public ArrayList<BusinessService> getBusinessServices() {
		return businessServices;
	}

	public void setBusinessServices(ArrayList<BusinessService> businessServices) {
		this.businessServices = businessServices;
	}

	public ServiceBus getServiceBus() {
		return serviceBus;
	}

	public void setServiceBus(ServiceBus serviceBus) {
		this.serviceBus = serviceBus;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProxyService)
			return ((ProxyService) obj).getId().equals(this.getId());
		return super.equals(obj);
	}
	
	public String getNameAndService(){
		return this.getName() + (this.getServiceBus()!=null?" ("+this.getServiceBus().getName()+")":"");
	}

}
