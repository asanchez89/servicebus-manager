package service_manager.editors.inputeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import service_manager.types.BusinessService;

public class BusinessInputEditor implements IEditorInput {

	private BusinessService businessService;
	private Boolean newBusiness;

	public BusinessInputEditor(BusinessService businessService,
			Boolean newBusiness) {
		this.businessService = businessService;
		this.newBusiness = newBusiness;
	}

	public BusinessService getBusinessService() {
		return businessService;
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
		BusinessInputEditor other = (BusinessInputEditor) obj;
		if (this.businessService.getId() != other.getBusinessService().getId())
			return false;
		return true;
	}

}
