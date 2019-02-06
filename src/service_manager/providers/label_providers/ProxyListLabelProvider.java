package service_manager.providers.label_providers;

import org.eclipse.jface.viewers.LabelProvider;

import service_manager.types.ProxyService;

public class ProxyListLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof ProxyService) {
			return ((ProxyService) element).getName();
		}
		return null;
	}

}