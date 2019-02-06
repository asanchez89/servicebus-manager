package service_manager.handlers;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import service_manager.editors.ProxyEditor;
import service_manager.editors.inputeditor.ProxyInputEditor;
import service_manager.types.ProxyService;
import service_manager.views.ProxyView;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenProxy extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public OpenProxy() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		ProxyView view = (ProxyView) page.findView(ProxyView.ID);
		ISelection selection = view.getSite().getSelectionProvider()
				.getSelection();
		if (selection instanceof ITreeSelection) {
			@SuppressWarnings("unchecked")
			ArrayList<ProxyService> list = (ArrayList<ProxyService>) ((ITreeSelection) selection)
					.toList();
			for (ProxyService proxyService : list) {
				try {
					ProxyInputEditor input = new ProxyInputEditor(proxyService,
							false);
					page.openEditor(input, ProxyEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}
}
