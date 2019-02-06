package service_manager.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import service_manager.Activator;
import service_manager.editors.inputeditor.BusinessInputEditor;
import service_manager.editors.inputeditor.ProxyInputEditor;
import service_manager.interfaces.EditorUpdatable;
import service_manager.managers.ServiceManager;
import service_manager.providers.content_providers.ProxyListContentProvider;
import service_manager.providers.content_providers.ServiceBusComboContentProvider;
import service_manager.providers.label_providers.ProxyListLabelProvider;
import service_manager.providers.label_providers.ServiceBusComboLabelProvider;
import service_manager.types.BusinessService;
import service_manager.types.ProxyService;
import service_manager.types.ServiceBus;
import service_manager.views.BusinessView;

public class BusinessEditor extends EditorPart implements EditorUpdatable {

	public static final String ID = "service_manager.editors.BusinessEditor"; //$NON-NLS-1$
	private static final String LABEL_BTN_RESET = "Reestablecer";
	private static final String MSGDIALOG_SAVE = "Guardar";
	private static final String LABEL_REMOTE_SERVICE = "Servicio Remoto:";
	private static final String LOCAL_REMOTE_ADDRESS = "Dirección Remota:";
	private static final String LABEL_LOCAL_ADDRESS = "Dirección Local:";
	private static final String LABEL_LOCAL_ACCESS = "Acceso Local:";
	private static final String LABEL_REMOTE_ACCESS = "Acceso Remoto:";
	private static final String LABEL_PROXY_INFO = "Informaci\u00F3n Proxy";
	private static final String LABEL_NOMBRE = "Nombre:";
	private static final String LABEL_UBICACION = "Ubicación:";
	private static final String LABEL_SERVICE_TYPE = "Tipo de Servicio:";
	private static final String LABEL_LOCAL_SERVICE = "Servicio Local:";
	private static final String LABEL_DESCRIPCION = "Descripci\u00F3n:";
	private static final String LABEL_BTN_SAVE = "Guardar";
	private Text txtNombre;
	private Text txtUbicacion;
	private Text txtServiceType;
	private Text txtLocalService;
	private Text txtDescripcion;
	private BusinessService businessService;
	private ListViewer lstAsignadosViewer;
	private Text txtRemoteAccess;
	private Text txtLocalAccess;
	private Text txtLocalAddress;
	private Text txtRemoteAddress;
	private Text txtRemoteService;
	private Button btnReset;
	private Button btnGuardar;
	private ServiceManager serviceManager;
	private BusinessService resetBusinessService;
	private ComboViewer comboViewer;

	public BusinessEditor() {
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
		txtNombre.setText(businessService.getName());

		Label lblUbicacion = new Label(grpServicioBusiness, SWT.NONE);
		lblUbicacion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUbicacion.setText(LABEL_UBICACION);

		txtUbicacion = new Text(grpServicioBusiness, SWT.BORDER);
		txtUbicacion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtUbicacion.setText(businessService.getLocation());

		Label lblLocalService = new Label(grpServicioBusiness, SWT.NONE);
		lblLocalService.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblLocalService.setText(LABEL_SERVICE_TYPE);

		txtServiceType = new Text(grpServicioBusiness, SWT.BORDER);
		txtServiceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtServiceType.setText(businessService.getServiceType());

		Label lblRemoteAccess = new Label(grpServicioBusiness, SWT.NONE);
		lblRemoteAccess.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblRemoteAccess.setText(LABEL_LOCAL_SERVICE);

		txtLocalService = new Text(grpServicioBusiness, SWT.BORDER);
		txtLocalService.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtLocalService.setText(businessService.getLocalService());

		Label lblNewLabel = new Label(grpServicioBusiness, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText(LABEL_REMOTE_ACCESS);

		txtRemoteAccess = new Text(grpServicioBusiness, SWT.BORDER);
		txtRemoteAccess.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtRemoteAccess.setText(businessService.getRemoteAccess());

		Label lblLocalAccess = new Label(grpServicioBusiness, SWT.NONE);
		lblLocalAccess.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblLocalAccess.setText(LABEL_LOCAL_ACCESS);

		txtLocalAccess = new Text(grpServicioBusiness, SWT.BORDER | SWT.MULTI);
		txtLocalAccess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 2));
		txtLocalAccess.setText(businessService.getLocalAccess());
		new Label(grpServicioBusiness, SWT.NONE);

		Label lblLocalAddress = new Label(grpServicioBusiness, SWT.NONE);
		lblLocalAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblLocalAddress.setText(LABEL_LOCAL_ADDRESS);

		txtLocalAddress = new Text(grpServicioBusiness, SWT.BORDER | SWT.MULTI);
		txtLocalAddress.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 2));
		txtLocalAddress.setText(businessService.getLocalAddress());
		new Label(grpServicioBusiness, SWT.NONE);

		Label lblRemoteAddress = new Label(grpServicioBusiness, SWT.NONE);
		lblRemoteAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblRemoteAddress.setText(LOCAL_REMOTE_ADDRESS);

		txtRemoteAddress = new Text(grpServicioBusiness, SWT.BORDER);
		txtRemoteAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtRemoteAddress.setText(businessService.getRemoteAddress());

		Label lblRemoteService = new Label(grpServicioBusiness, SWT.NONE);
		lblRemoteService.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblRemoteService.setText(LABEL_REMOTE_SERVICE);

		txtRemoteService = new Text(grpServicioBusiness, SWT.BORDER);
		txtRemoteService.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtRemoteService.setText(businessService.getRemoteService());

		Label lblServiceBus = new Label(grpServicioBusiness, SWT.NONE);
		lblServiceBus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblServiceBus.setText("Service Bus:");

		comboViewer = new ComboViewer(grpServicioBusiness, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		comboViewer.setContentProvider(new ServiceBusComboContentProvider());
		comboViewer.setLabelProvider(new ServiceBusComboLabelProvider());
		setCombo(comboViewer);
		if (businessService.getServiceBus() != null) {
			StructuredSelection selection = new StructuredSelection(
					businessService.getServiceBus());
			comboViewer.setSelection(selection);
		}
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Label lblDescripcion = new Label(grpServicioBusiness, SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescripcion.setText(LABEL_DESCRIPCION);

		txtDescripcion = new Text(grpServicioBusiness, SWT.BORDER | SWT.MULTI);
		txtDescripcion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 2));
		txtDescripcion.setText(businessService.getDescription());
		new Label(grpServicioBusiness, SWT.NONE);

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label lblBusinessUtilizados = new Label(group, SWT.NONE);
		lblBusinessUtilizados.setLayoutData(new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 1, 1));
		lblBusinessUtilizados.setAlignment(SWT.CENTER);
		lblBusinessUtilizados.setText("Proxies");

		lstAsignadosViewer = new ListViewer(group, SWT.BORDER);
		lstAsignadosViewer.setContentProvider(new ProxyListContentProvider());
		lstAsignadosViewer.setLabelProvider(new ProxyListLabelProvider());
		setList(lstAsignadosViewer);
		List lstAsignados = lstAsignadosViewer.getList();
		lstAsignados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEditorFormListViewer(lstAsignadosViewer);
			}
		});
		GridData gd_lstAsignados = new GridData(SWT.CENTER, SWT.FILL, true,
				true, 1, 2);
		gd_lstAsignados.widthHint = 300;
		lstAsignados.setLayoutData(gd_lstAsignados);

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
		if (!(input instanceof BusinessInputEditor)) {
			throw new RuntimeException("Wrong input");
		}

		BusinessInputEditor bie = (BusinessInputEditor) input;
		setSite(site);
		setInput(input);
		serviceManager = Activator.getDefault().serviceManager;
		businessService = bie.getBusinessService();
		resetBusinessService = new BusinessService(businessService);
		setPartName(businessService.getName());
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public Integer save() {
		businessService.setName(txtNombre.getText());
		businessService.setLocation(txtUbicacion.getText());
		businessService.setLocalAccess(txtLocalAccess.getText());
		businessService.setLocalAddress(txtLocalAddress.getText());
		businessService.setLocalService(txtLocalService.getText());
		businessService.setRemoteAccess(txtRemoteAccess.getText());
		businessService.setRemoteAddress(txtRemoteAddress.getText());
		businessService.setRemoteService(txtRemoteService.getText());
		businessService.setServiceType(txtServiceType.getText());
		businessService.setDescription(txtDescripcion.getText());
		businessService
				.setServiceBus((ServiceBus) ((IStructuredSelection) comboViewer
						.getSelection()).getFirstElement());
		serviceManager.addBusiness(businessService);
		Integer result = serviceManager.setDataToXml();
		if (result == 0) {
			resetBusinessService = new BusinessService(businessService);
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			BusinessView view = (BusinessView) page.findView(BusinessView.ID);
			view.updateTree();
		}
		return result;
	}

	public void reset() {
		businessService = new BusinessService(resetBusinessService);
		txtNombre.setText(businessService.getName());
		txtUbicacion.setText(businessService.getLocation());
		txtLocalAccess.setText(businessService.getLocalAccess());
		txtLocalAddress.setText(businessService.getLocalAddress());
		txtRemoteAccess.setText(businessService.getRemoteAccess());
		txtRemoteAddress.setText(businessService.getRemoteAddress());
		txtRemoteService.setText(businessService.getRemoteService());
		txtServiceType.setText(businessService.getServiceType());
		txtDescripcion.setText(businessService.getDescription());
		comboViewer.setSelection(new StructuredSelection(businessService
				.getServiceBus()));
		setList(lstAsignadosViewer);
	}

	private void setList(ListViewer listViewer) {
		listViewer.setInput(serviceManager
				.getProxyFromBusiness(businessService));
	}

	public void openEditorFormListViewer(ListViewer listViewer) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IStructuredSelection selected = (IStructuredSelection) listViewer
				.getSelection();
		if (!selected.isEmpty()) {
			if (selected instanceof IStructuredSelection) {
				ProxyService proxyService = (ProxyService) ((IStructuredSelection) selected)
						.getFirstElement();
				try {
					ProxyInputEditor input = new ProxyInputEditor(proxyService,
							false);
					page.openEditor(input, ProxyEditor.ID);
				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		}
	}

	public void updateList() {
		setList(lstAsignadosViewer);
	}

	public void setCombo(ComboViewer comboViewer) {
		comboViewer.setInput(serviceManager.getServicebusList());
	}

	public void updateCombo(ServiceBus pBusRemoved) {
		setCombo(comboViewer);
	}
}
