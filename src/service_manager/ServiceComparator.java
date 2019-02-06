package service_manager;

import java.util.Comparator;

import service_manager.types.Service;

public class ServiceComparator implements Comparator<Service> {

	@Override
	public int compare(Service o1, Service o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
