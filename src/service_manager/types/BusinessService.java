package service_manager.types;

import service_manager.Activator;

public class BusinessService extends Service {

	private String location;
	private String serviceType;
	private String localService;
	private String remoteAccess;
	private String localAccess;
	private String localAddress;
	private String remoteAddress;
	private String remoteService;
	private ServiceBus serviceBus;

	public BusinessService() {
		super();
		setId(Activator.getDefault().serviceManager.setIdNewBusiness(String
				.valueOf((int) (Math.random() * 10000))));
		this.location = "";
		this.serviceType = "";
		this.localService = "";
		this.remoteAccess = "";
		this.localAccess = "";
		this.localAddress = "";
		this.remoteAddress = "";
		this.remoteService = "";
		this.serviceBus = null;
	}

	public BusinessService(String id, String name, String location,
			String serviceType, String localService, String remoteAccess,
			String localAccess, String localAddress, String remoteAddress,
			String remoteService, String description, ServiceBus serviceBus) {
		super(id, name, description);
		this.location = location;
		this.serviceType = serviceType;
		this.localService = localService;
		this.remoteAccess = remoteAccess;
		this.localAccess = localAccess;
		this.localAddress = localAddress;
		this.remoteAddress = remoteAddress;
		this.remoteService = remoteService;
		this.serviceBus = serviceBus;
	}

	public BusinessService(BusinessService businessService) {
		super(businessService.getId(), businessService.getName(),
				businessService.getDescription());
		this.location = businessService.getLocation();
		this.serviceType = businessService.getServiceType();
		this.localService = businessService.getLocalService();
		this.remoteAccess = businessService.getRemoteAccess();
		this.localAccess = businessService.getLocalAccess();
		this.localAddress = businessService.getLocalAddress();
		this.remoteAddress = businessService.getRemoteAddress();
		this.remoteService = businessService.getRemoteService();
		this.serviceBus = businessService.getServiceBus();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getLocalService() {
		return localService;
	}

	public void setLocalService(String localService) {
		this.localService = localService;
	}

	public String getRemoteAccess() {
		return remoteAccess;
	}

	public void setRemoteAccess(String remoteAccess) {
		this.remoteAccess = remoteAccess;
	}

	public String getLocalAccess() {
		return localAccess;
	}

	public void setLocalAccess(String localAccess) {
		this.localAccess = localAccess;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(String remoteService) {
		this.remoteService = remoteService;
	}

	public ServiceBus getServiceBus() {
		return serviceBus;
	}

	public void setServiceBus(ServiceBus serviceBus) {
		this.serviceBus = serviceBus;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BusinessService)
			return ((BusinessService) obj).getId().equals(this.getId());
		return super.equals(obj);
	}
	
	public String getNameAndService(){
		System.out.println(this.getName()+"****");
		return this.getName() + (this.getServiceBus()!=null?" ("+this.getServiceBus().getName()+")":"");
	}

}
