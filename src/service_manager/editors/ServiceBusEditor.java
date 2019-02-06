package service_manager.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import service_manager.Activator;
import service_manager.editors.inputeditor.BusinessInputEditor;
import service_manager.editors.inputeditor.ServiceBusInputEditor;
import service_manager.managers.ServiceManager;
import service_manager.types.ServiceBus;
import service_manager.views.BusinessView;
import service_manager.views.ServiceBusView;

public class ServiceBusEditor extends EditorPart {

	public static final String ID = "service_manager.editors.ServiceBusEditor"; //$NON-NLS-1$
	private static final String LABEL_BTN_RESET = "Reset";
	private static final String MSGDIALOG_SAVE = "Guardar";
	private static final String LABEL_PROXY_INFO = "Informaci\u00F3n ServiceBus";
	private static final String LABEL_NOMBRE = "Nombre:";
	private static final String LABEL_UBICACION = "Location:";
	private static final String LABEL_DESCRIPCION = "Descripci\u00F3n:";
	private static final String LABEL_BTN_SAVE = "Guardar";
	private Text txtNombre;
	private Text txtUbicacion;
	private Text txtDescripcion;
	private ServiceBus serviceBus;
	private Button btnReset;
	private Button btnGuardar;
	private ServiceManager serviceManager;
	private ServiceBus resetServiceBus;

	public ServiceBusEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Group grpServicioBusiness = new Group(composite, SWT.NONE);
		grpServicioBusiness.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, false, 1, 1));
		grpServicioBusiness.setText(LABEL_PROXY_INFO);
		grpServicioBusiness.setLayout(new GridLayout(2, false));

		Label lblNombre = new Label(grpServicioBusiness, SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNombre.setText(LABEL_NOMBRE);

		txtNombre = new Text(grpServicioBusiness, SWT.BORDER);
		txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		txtNombre.setText(serviceBus.getName());

		Label lblUbicacion = new Label(grpServicioBusiness, SWT.NONE);
		lblUbicacion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUbicacion.setText(LABEL_UBICACION);

		txtUbicacion = new Text(grpServicioBusiness, SWT.BORDER);
		txtUbicacion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtUbicacion.setText(serviceBus.getUrl());

		Label lblDescripcion = new Label(grpServicioBusiness, SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescripcion.setText(LABEL_DESCRIPCION);

		txtDescripcion = new Text(grpServicioBusiness, SWT.BORDER);
		txtDescripcion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 2));
		txtDescripcion.setText(serviceBus.getDescription());
		new Label(grpServicioBusiness, SWT.NONE);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		composite_1.setLayout(new GridLayout(2, false));

		btnReset = new Button(composite_1, SWT.NONE);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (MessageDialog.openQuestion(btnGuardar.getShell(),
						LABEL_BTN_RESET,
						"¿Desea omitir los cambios realizados?")) {
					reset();
				}
			}
		});
		GridData gd_btnReset = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnReset.widthHint = 75;
		btnReset.setLayoutData(gd_btnReset);
		btnReset.setSize(40, 25);
		btnReset.setText(LABEL_BTN_RESET);

		btnGuardar = new Button(composite_1, SWT.NONE);
		GridData gd_btnGuardar = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnGuardar.widthHint = 75;
		btnGuardar.setLayoutData(gd_btnGuardar);
		btnGuardar.setSize(54, 25);
		btnGuardar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (MessageDialog.openQuestion(btnGuardar.getShell(),
						MSGDIALOG_SAVE,
						"¿Desea guardar los cambios realizados?")) {
					switch (save()) {
					case 0:
						MessageDialog.openConfirm(btnGuardar.getShell(),
								MSGDIALOG_SAVE,
								"Los cambios fueron guardados exitosamente.");
						break;
					case 1:
						MessageDialog.openWarning(btnGuardar.getShell(),
								MSGDIALOG_SAVE, "Error al guardar los cambios.");
						break;
					default:
						break;
					}
				}
			}
		});
		btnGuardar.setText(LABEL_BTN_SAVE);

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof ServiceBusInputEditor)) {
			throw new RuntimeException("Wrong input");
		}

		ServiceBusInputEditor sie = (ServiceBusInputEditor) input;
		setSite(site);
		setInput(input);
		serviceManager = Activator.getDefault().serviceManager;
		serviceBus = sie.getServiceBus();
		resetServiceBus = new ServiceBus(serviceBus);
		setPartName(serviceBus.getName());
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	private Integer save() {
		serviceBus.setName(txtNombre.getText());
		serviceBus.setUrl(txtUbicacion.getText());
		serviceBus.setDescription(txtDescripcion.getText());
		serviceManager.addServiceBus(serviceBus);
		Integer result = serviceManager.setDataToXml();
		if (result == 0) {
			resetServiceBus = new ServiceBus(serviceBus);
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			ServiceBusView view = (ServiceBusView) page.findView(ServiceBusView.ID);
			view.updateTree();
		}
		IEditorReference[] editors = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			if (editors[i].getEditor(true) instanceof ProxyEditor)
				((ProxyEditor) editors[i].getEditor(true))
						.updateCombo(serviceBus);
			if (editors[i].getEditor(true) instanceof BusinessEditor)
				((BusinessEditor) editors[i].getEditor(true))
						.updateCombo(serviceBus);
		}
		
		return result;
	}

	private void reset() {
		serviceBus = new ServiceBus(resetServiceBus);
		txtNombre.setText(serviceBus.getName());
		txtUbicacion.setText(serviceBus.getUrl());
		txtDescripcion.setText(serviceBus.getDescription());
	}

}
