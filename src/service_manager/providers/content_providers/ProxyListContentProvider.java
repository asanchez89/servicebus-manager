package service_manager.providers.content_providers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import service_manager.types.ProxyService;

public class ProxyListContentProvider implements IStructuredContentProvider {

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ArrayList) {
			return ((ArrayList<ProxyService>) inputElement).toArray();
		}
		return new Object[0];
	}

}
