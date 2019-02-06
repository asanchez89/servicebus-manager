package service_manager.editors.inputeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import service_manager.types.ServiceBus;

public class ServiceBusInputEditor implements IEditorInput {

	private ServiceBus serviceBus;
	private Boolean newBusiness;

	public ServiceBusInputEditor(ServiceBus serviceBus,
			Boolean newBusiness) {
		this.serviceBus = serviceBus;
		this.newBusiness = newBusiness;
	}

	public ServiceBus getServiceBus() {
		return serviceBus;
	}

	public Boolean isNewBusiness() {
		return newBusiness;
	}


	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceBusInputEditor other = (ServiceBusInputEditor) obj;
		if (this.serviceBus.getId() != other.getServiceBus().getId())
			return false;
		return true;
	}

}
