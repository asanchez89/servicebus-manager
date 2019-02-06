package service_manager.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import service_manager.editors.ProxyEditor;
import service_manager.editors.inputeditor.ProxyInputEditor;
import service_manager.types.ProxyService;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CreateProxy extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CreateProxy() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("called");
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		ProxyInputEditor input = new ProxyInputEditor(new ProxyService(), true);
		try {
			page.openEditor(input, ProxyEditor.ID);

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
		// }
		return null;
	}
}
