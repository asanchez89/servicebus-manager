package service_manager.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import service_manager.editors.ProxyEditor;
import service_manager.editors.inputeditor.ProxyInputEditor;
import service_manager.types.ProxyService;

public class OpenProxyAction extends Action implements IWorkbenchAction {

	private TreeViewer treeViewer;

	public OpenProxyAction(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public void run() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ITreeSelection selected = (ITreeSelection) treeViewer.getSelection();
		if (selected instanceof ITreeSelection) {
			ProxyService proxyService = (ProxyService) ((ITreeSelection) selected).getFirstElement();
				try {
					ProxyInputEditor input = new ProxyInputEditor(proxyService, false);
					page.openEditor(input, ProxyEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}

		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
