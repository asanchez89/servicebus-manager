package service_manager.providers.label_providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import service_manager.Activator;
import service_manager.types.BusinessService;

public class BusinessListLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof BusinessService) {
			return ((BusinessService) element).getName() ;
		}
		return null;
	}

}