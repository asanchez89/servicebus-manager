package service_manager.providers.label_providers;

import org.eclipse.jface.viewers.LabelProvider;

import service_manager.types.ServiceBus;

public class ServiceBusComboLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof ServiceBus) {
			return ((ServiceBus) element).getName();
		}
		return null;
	}

}