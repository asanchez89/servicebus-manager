package service_manager.views;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import service_manager.Activator;
import service_manager.managers.ServiceManager;
import service_manager.providers.content_providers.ServiceBusComboContentProvider;
import service_manager.providers.label_providers.ServiceBusComboLabelProvider;
import service_manager.types.ServiceBus;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ServiceBusSelectorDialog extends Dialog {

	private static final String LABEL_CANCEL = "Cancelar";
	private static final String LABEL_OK = "Aceptar";
	protected String result;
	protected Shell shell;
	private ComboViewer comboViewer;
	private ServiceManager serviceManager;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ServiceBusSelectorDialog(Shell parent, int style) {
		super(parent, style);
		setText("Exportar a Excel");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public String open() {
		serviceManager = Activator.getDefault().serviceManager;
		
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(330, 120);
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, false));
		
		Label lblSeleccioneUnService = new Label(shell, SWT.NONE);
		lblSeleccioneUnService.setText("Seleccione un Service Bus:");
		comboViewer = new ComboViewer(shell, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		comboViewer.setContentProvider(new ServiceBusComboContentProvider());
		comboViewer.setLabelProvider(new ServiceBusComboLabelProvider());
		setCombo(comboViewer);
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = ((ServiceBus) ((IStructuredSelection) comboViewer
						.getSelection()).getFirstElement()).getId();
				shell.close();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 75;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText(LABEL_OK);
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = null;
				shell.close();
			}
		});
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 75;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText(LABEL_CANCEL);
		
				shell.setDefaultButton(btnNewButton);
	}
	
	public void setCombo(ComboViewer comboViewer) {
		comboViewer.setInput(serviceManager.getServicebusList());
	}

	public String getResult() {
		return result;
	}
	
	
}
