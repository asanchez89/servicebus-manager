package service_manager.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import service_manager.editors.BusinessEditor;
import service_manager.editors.inputeditor.BusinessInputEditor;
import service_manager.types.BusinessService;

public class OpenBusinessAction extends Action implements IWorkbenchAction {

	private TreeViewer treeViewer;

	public OpenBusinessAction(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public void run() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ITreeSelection selected = (ITreeSelection) treeViewer.getSelection();
		if (selected instanceof ITreeSelection) {
			BusinessService businessService = (BusinessService) ((ITreeSelection) selected).getFirstElement();
				try {
					BusinessInputEditor input = new BusinessInputEditor(businessService,false);
					page.openEditor(input, BusinessEditor.ID);
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
