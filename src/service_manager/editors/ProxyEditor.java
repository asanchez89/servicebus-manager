package service_manager.editors;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import service_manager.providers.content_providers.BusinessListContentProvider;
import service_manager.providers.content_providers.ServiceBusComboContentProvider;
import service_manager.providers.label_providers.BusinessListLabelProvider;
import service_manager.providers.label_providers.ServiceBusComboLabelProvider;
import service_manager.types.BusinessService;
import service_manager.types.ProxyService;
import service_manager.types.ServiceBus;
import service_manager.views.ProxyView;
import org.eclipse.wb.swt.SWTResourceManager;

public class ProxyEditor extends EditorPart implements EditorUpdatable {

	private static final int FIELD_WDTH = 400;
	public static final String ID = "service_manager.editors.ProxyEditor"; //$NON-NLS-1$
	private static final String LABEL_BTN_RESET = "Reestablecer";
	private static final String MSGDIALOG_SAVE = "Guardar";
	private static final String LABEL_PROXY_INFO = "Informaci\u00F3n Proxy";
	private static final String LABEL_NOMBRE = "Nombre:";
	private static final String LABEL_UBICACION = "Location:";
	private static final String LABEL_URI = "URI:";
	private static final String LABEL_WSDL = "WSDL:";
	private static final String LABEL_DESCRIPCION = "Descripci\u00F3n:";
	private static final String LABEL_BTN_ASIGNAR = "<-";
	private static final String LABEL_BTN_DESASIGNAR = "->";
	private static final String LABEL_BTN_SAVE = MSGDIALOG_SAVE;
	private Text txtNombre;
	private Text txtUbicacion;
	private Text txtURI;
	private Text txtWSDL;
	private Text txtDescripcion;
	private ProxyService proxyService;
	private ProxyService resetProxyService;
	private ArrayList<BusinessService> businessAsignados;
	private ArrayList<BusinessService> businessDisponibles;
	private ListViewer lstDisponiblesViewer;
	private ListViewer lstAsignadosViewer;
	private Button btnDesasignar;
	private Button btnAsignar;
	private Button btnReset;
	private Button btnGuardar;
	private ServiceManager serviceManager;
	private ComboViewer comboViewer;
	private Text txtProtocol;

	public ProxyEditor() {
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

		Group grpServicioProxy = new Group(composite, SWT.NONE | SWT.V_SCROLL);
		grpServicioProxy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		grpServicioProxy.setText(LABEL_PROXY_INFO);
		grpServicioProxy.setLayout(new GridLayout(2, false));

		Label lblNombre = new Label(grpServicioProxy, SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNombre.setText(LABEL_NOMBRE);

		txtNombre = new Text(grpServicioProxy, SWT.BORDER);
		GridData gd_txtNombre = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtNombre.widthHint = FIELD_WDTH;
		txtNombre.setLayoutData(gd_txtNombre);

		Label lblUbicacion = new Label(grpServicioProxy, SWT.NONE);
		lblUbicacion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUbicacion.setText(LABEL_UBICACION);

		txtUbicacion = new Text(grpServicioProxy, SWT.BORDER);
		GridData gd_txtUbicacion = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtUbicacion.widthHint = FIELD_WDTH;
		txtUbicacion.setLayoutData(gd_txtUbicacion);

		Label lblURI = new Label(grpServicioProxy, SWT.NONE);
		lblURI.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblURI.setText(LABEL_URI);

		txtURI = new Text(grpServicioProxy, SWT.BORDER);
		GridData gd_txtURI = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtURI.widthHint = FIELD_WDTH;
		txtURI.setLayoutData(gd_txtURI);

		Label lblProtocolo = new Label(grpServicioProxy, SWT.NONE);
		lblProtocolo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblProtocolo.setText("Protocolo:");

		txtProtocol = new Text(grpServicioProxy, SWT.BORDER);
		GridData gd_txtProtocol = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtProtocol.widthHint = FIELD_WDTH;
		txtProtocol.setLayoutData(gd_txtProtocol);

		Label lblWSDL = new Label(grpServicioProxy, SWT.NONE);
		lblWSDL.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblWSDL.setText(LABEL_WSDL);

		txtWSDL = new Text(grpServicioProxy, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL);
		txtWSDL.setFont(SWTResourceManager.getFont("Consolas", 9, SWT.NORMAL));
		GridData gd_txtWSDL = new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 3);
		gd_txtWSDL.widthHint = FIELD_WDTH;
		gd_txtWSDL.heightHint = 75;
		txtWSDL.setLayoutData(gd_txtWSDL);
		new Label(grpServicioProxy, SWT.NONE);
		new Label(grpServicioProxy, SWT.NONE);

		Label lblServiceBus = new Label(grpServicioProxy, SWT.NONE);
		lblServiceBus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblServiceBus.setText("Service Bus:");

		comboViewer = new ComboViewer(grpServicioProxy, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		comboViewer.setContentProvider(new ServiceBusComboContentProvider());
		comboViewer.setLabelProvider(new ServiceBusComboLabelProvider());
		setCombo(comboViewer);
		comboViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent arg0) {
						IStructuredSelection selected = (IStructuredSelection) comboViewer
								.getSelection();
						if (!selected.isEmpty()) {
							ServiceBus selectedServiceBus = (ServiceBus) selected
									.getFirstElement();
							initLists(
									serviceManager
											.getBusinessListFromProxyServiceByServiceBus(
													proxyService,
													selectedServiceBus),
									serviceManager
											.getBusinessListByServiceBus(selectedServiceBus));
							updateList();
						}

					}
				});
		Combo combo = comboViewer.getCombo();
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1);
		gd_combo.widthHint = FIELD_WDTH;
		combo.setLayoutData(gd_combo);

		Label lblDescripcion = new Label(grpServicioProxy, SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescripcion.setText(LABEL_DESCRIPCION);

		txtDescripcion = new Text(grpServicioProxy, SWT.BORDER | SWT.MULTI);
		GridData gd_txtDescripcion = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 2);
		gd_txtDescripcion.widthHint = FIELD_WDTH;
		txtDescripcion.setLayoutData(gd_txtDescripcion);

		new Label(grpServicioProxy, SWT.NONE);
		if (proxyService.getServiceBus() != null) {
			StructuredSelection selection = new StructuredSelection(
					proxyService.getServiceBus());
			comboViewer.setSelection(selection);
		}

		Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group.setLayout(new GridLayout(3, false));

		Label lblBusinessUtilizados = new Label(group, SWT.NONE);
		lblBusinessUtilizados.setText("Business Utilizados");
		new Label(group, SWT.NONE);

		Label lblBusinessDisponibles = new Label(group, SWT.NONE);
		lblBusinessDisponibles.setText("Business Disponibles");

		lstAsignadosViewer = new ListViewer(group, SWT.BORDER);
		lstAsignadosViewer
				.setContentProvider(new BusinessListContentProvider());
		lstAsignadosViewer.setLabelProvider(new BusinessListLabelProvider());
		setAssignedList(lstAsignadosViewer);
		List lstAsignados = lstAsignadosViewer.getList();
		lstAsignados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEditorFormListViewer(lstAsignadosViewer);
			}
		});
		lstAsignados.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 2));

		btnDesasignar = new Button(group, SWT.NONE);
		btnDesasignar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selected = (IStructuredSelection) lstAsignadosViewer
						.getSelection();
				if (!selected.isEmpty()) {
					ArrayList<BusinessService> businessSelected = new ArrayList<BusinessService>(
							0);
					businessSelected.addAll(selected.toList());
					for (BusinessService businessService : businessSelected) {
						businessAsignados.remove(businessService);
					}
					setAssignedList(lstAsignadosViewer);
					setAvailableList(lstDisponiblesViewer);
				}
			}
		});
		btnDesasignar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
				false, 1, 1));
		btnDesasignar.setText(LABEL_BTN_DESASIGNAR);

		lstDisponiblesViewer = new ListViewer(group, SWT.BORDER);
		lstDisponiblesViewer
				.setContentProvider(new BusinessListContentProvider());
		lstDisponiblesViewer.setLabelProvider(new BusinessListLabelProvider());
		setAvailableList(lstDisponiblesViewer);

		List lstDisponibles = lstDisponiblesViewer.getList();
		lstDisponibles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEditorFormListViewer(lstDisponiblesViewer);
			}
		});
		lstDisponibles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 2));

		btnAsignar = new Button(group, SWT.NONE);
		btnAsignar.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selected = (IStructuredSelection) lstDisponiblesViewer
						.getSelection();
				if (!selected.isEmpty()) {
					@SuppressWarnings("unchecked")
					ArrayList<BusinessService> businessSelected = new ArrayList<BusinessService>(
							0);
					businessSelected.addAll(selected.toList());
					for (BusinessService businessService : businessSelected) {
						businessAsignados.add(businessService);
					}
					setAssignedList(lstAsignadosViewer);
					setAvailableList(lstDisponiblesViewer);
				}
			}
		});
		btnAsignar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false,
				1, 1));
		btnAsignar.setText(LABEL_BTN_ASIGNAR);

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
		reset();
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
		if (!(input instanceof ProxyInputEditor)) {
			throw new RuntimeException("Wrong input");
		}

		ProxyInputEditor pie = (ProxyInputEditor) input;
		setSite(site);
		setInput(input);
		serviceManager = Activator.getDefault().serviceManager;
		proxyService = pie.getProxyService();
		resetProxyService = new ProxyService(proxyService);
		if (proxyService.getServiceBus() != null)
			initLists(proxyService.getBusinessServices(),
					serviceManager.getBusinessListByServiceBus(proxyService
							.getServiceBus()));
		setPartName(proxyService.getName());
	}

	public void initLists(ArrayList<BusinessService> asignados,
			ArrayList<BusinessService> disponibles) {
		businessAsignados = new ArrayList<BusinessService>(0);
		businessAsignados.addAll(asignados);
		businessDisponibles = new ArrayList<BusinessService>(0);
		businessDisponibles.addAll(disponibles);

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
		proxyService.setName(txtNombre.getText());
		proxyService.setLocation(txtUbicacion.getText());
		proxyService.setUri(txtURI.getText());
		proxyService.setWsdl(txtWSDL.getText());
		proxyService.setDescription(txtDescripcion.getText());
		proxyService.setProtocol(txtProtocol.getText());
		if (businessAsignados != null) {
			proxyService.getBusinessServices().clear();
			proxyService.getBusinessServices().addAll(businessAsignados);
		} else {
			proxyService.setBusinessServices(new ArrayList<BusinessService>(0));
		}
		proxyService
				.setServiceBus((ServiceBus) ((IStructuredSelection) comboViewer
						.getSelection()).getFirstElement());
		serviceManager.addProxy(proxyService);
		Integer result = serviceManager.setDataToXml();
		if (result == 0) {
			resetProxyService = new ProxyService(proxyService);
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			((ProxyView) page.findView(ProxyView.ID)).updateTree();
		}
		return result;
	}

	public void updateList() {
		setAssignedList(lstAsignadosViewer);
		setAvailableList(lstDisponiblesViewer);
	}

	public void reset() {
		proxyService = new ProxyService(resetProxyService);
		txtNombre.setText(proxyService.getName() == null ? "" : proxyService
				.getName());
		txtUbicacion.setText(proxyService.getLocation() == null ? ""
				: proxyService.getLocation());
		txtURI.setText(proxyService.getUri() == null ? "" : proxyService
				.getUri());
		txtProtocol.setText(proxyService.getProtocol() == null ? ""
				: proxyService.getProtocol());
		txtWSDL.setText(proxyService.getWsdl() == null ? "" : proxyService
				.getWsdl());
		txtDescripcion.setText(proxyService.getDescription());
		if (proxyService.getServiceBus() != null)
			comboViewer.setSelection(new StructuredSelection(proxyService
					.getServiceBus()));
		if (businessAsignados != null) {
			businessAsignados.clear();
			businessAsignados.addAll(proxyService.getBusinessServices());
			setAssignedList(lstAsignadosViewer);
			setAvailableList(lstDisponiblesViewer);
		}
	}

	private void setAssignedList(ListViewer listViewer) {
		if (listViewer != null)
			listViewer.setInput(businessAsignados);
	}

	private void setAvailableList(ListViewer listViewer) {
		if (businessDisponibles != null && listViewer != null) {
			ArrayList<BusinessService> businessServices = new ArrayList<BusinessService>();
			ArrayList<BusinessService> bSToRemove = new ArrayList<BusinessService>();
			businessServices.addAll(businessDisponibles);
			for (BusinessService businessService : businessDisponibles) {
				for (BusinessService pBService : businessAsignados) {
					if (pBService.equals(businessService)) {
						bSToRemove.add(businessService);
					}
				}
			}
			businessServices.removeAll(bSToRemove);
			listViewer.setInput(businessServices);
		}
	}

	public void openEditorFormListViewer(ListViewer listViewer) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IStructuredSelection selected = (IStructuredSelection) listViewer
				.getSelection();
		if (!selected.isEmpty()) {
			if (selected instanceof IStructuredSelection) {
				try {
					BusinessService businessService = (BusinessService) ((IStructuredSelection) selected)
							.getFirstElement();
					BusinessInputEditor input = new BusinessInputEditor(
							businessService, false);
					page.openEditor(input, BusinessEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void setCombo(ComboViewer comboViewer) {
		comboViewer.setInput(serviceManager.getServicebusList());
	}

	public void updateCombo(ServiceBus pBusRemoved) {
		setCombo(comboViewer);
	}

}
