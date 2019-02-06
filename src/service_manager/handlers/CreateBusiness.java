package service_manager.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import service_manager.editors.BusinessEditor;
import service_manager.editors.ProxyEditor;
import service_manager.editors.inputeditor.BusinessInputEditor;
import service_manager.types.BusinessService;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CreateBusiness extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CreateBusiness() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		BusinessInputEditor input = new BusinessInputEditor(new BusinessService(), true);
		try {
			page.openEditor(input, BusinessEditor.ID);

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
		// }
		return null;
	}
}
