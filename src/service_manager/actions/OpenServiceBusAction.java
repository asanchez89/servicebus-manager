package service_manager.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import service_manager.editors.ServiceBusEditor;
import service_manager.editors.inputeditor.ServiceBusInputEditor;
import service_manager.types.ServiceBus;

public class OpenServiceBusAction extends Action implements IWorkbenchAction {

	private TreeViewer treeViewer;

	public OpenServiceBusAction(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public void run() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ITreeSelection selected = (ITreeSelection) treeViewer.getSelection();
		if (selected instanceof ITreeSelection) {
			ServiceBus serviceBus = (ServiceBus) ((ITreeSelection) selected).getFirstElement();
				try {
					ServiceBusInputEditor input = new ServiceBusInputEditor(serviceBus,false);
					page.openEditor(input, ServiceBusEditor.ID);
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
