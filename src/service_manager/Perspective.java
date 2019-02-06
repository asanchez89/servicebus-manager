package service_manager;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import service_manager.editors.ServiceBusEditor;
import service_manager.views.BusinessView;
import service_manager.views.ProxyView;
import service_manager.views.ServiceBusView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {

		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		IFolderLayout left = layout.createFolder("left",
				IPageLayout.LEFT, 0.33f, layout.getEditorArea());
		left.addPlaceholder(ProxyView.ID + ":*");
		left.addView(ProxyView.ID);
		left.addView(BusinessView.ID);
		left.addView(ServiceBusView.ID);

	}
}
