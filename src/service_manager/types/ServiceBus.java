package service_manager.types;

import service_manager.Activator;

public class ServiceBus extends Service {

	private String url;

	public ServiceBus() {
		super();
		setId(Activator.getDefault().serviceManager.setIdNewServiceBus(String
				.valueOf((int) (Math.random() * 10000))));
		this.url = "";
	}

	public ServiceBus(ServiceBus bus) {
		super(bus.getId(), bus.getName(), bus.getDescription());
		this.url = bus.getUrl();
	}

	public ServiceBus(String id, String name, String url, String description) {
		super(id, name, description);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServiceBus)
			return ((ServiceBus) obj).getId().equals(this.getId());
		return super.equals(obj);
	}

}
