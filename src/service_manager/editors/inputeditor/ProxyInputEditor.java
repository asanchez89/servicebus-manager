package service_manager.editors.inputeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import service_manager.types.ProxyService;

public class ProxyInputEditor implements IEditorInput {

	private ProxyService proxyService;
	private Boolean newProxy;

	public ProxyInputEditor(ProxyService proxyService, Boolean newProxy) {
		this.proxyService = proxyService;
		this.newProxy = newProxy;
	}

	public Boolean isNewProxy() {
		return newProxy;
	}

	public ProxyService getProxyService() {
		return proxyService;
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
		ProxyInputEditor other = (ProxyInputEditor) obj;
		if (this.proxyService.getId() != other.getProxyService().getId())
			return false;
		return true;
	}

}
