package service_manager.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import service_manager.managers.ExcelExportManager;
import service_manager.views.ServiceBusSelectorDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ExcelExport extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ExcelExport() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();

		ServiceBusSelectorDialog selectorDialog = new ServiceBusSelectorDialog(
				shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		String serviceBus = selectorDialog.open();
		
		if (serviceBus != null) {
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			dialog.setFilterExtensions(new String[] { "*.xls" });
			dialog.setFilterNames(new String[] { "Archivo Excel (.xls)" });
			String fileSelected = dialog.open();

			if (fileSelected != null) {
				ExcelExportManager exportManager = new ExcelExportManager();
				exportManager.exportToExcel(serviceBus, fileSelected, shell);
				System.out.println("Guardado: " + fileSelected);
			}
		}
		return null;
	}
}
