package service_manager.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import service_manager.Activator;
import service_manager.editors.BusinessEditor;
import service_manager.editors.ProxyEditor;
import service_manager.managers.ServiceManager;
import service_manager.types.ProxyService;

public class DeleteProxyAction extends Action implements IWorkbenchAction {

	private TreeViewer treeViewer;

	public DeleteProxyAction(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public void run() {
		ServiceManager serviceManager = Activator.getDefault().serviceManager;
		ITreeSelection selected = (ITreeSelection) treeViewer.getSelection();
		if (selected instanceof ITreeSelection) {
			ProxyService proxyService = (ProxyService) ((ITreeSelection) selected)
					.getFirstElement();
			serviceManager.deleteProxy(proxyService);
			serviceManager.setDataToXml();
			treeViewer.setInput(serviceManager.getProxyList());
			IEditorReference[] editors = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getEditorReferences();
			for (int i = 0; i < editors.length; i++) {
				if (editors[i].getEditor(true) instanceof BusinessEditor) {
					((BusinessEditor) editors[i].getEditor(true)).updateList();
				}
			}
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
