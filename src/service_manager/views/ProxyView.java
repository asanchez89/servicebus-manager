package service_manager.views;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import service_manager.Activator;
import service_manager.actions.DeleteProxyAction;
import service_manager.actions.OpenProxyAction;
import service_manager.types.ProxyService;

public class ProxyView extends ViewPart {
	private static final String LABEL_SEARCH_TEXT = "Nombre del Proxy: ";
	private static final String LABEL_SEARCH = "Administración de Proxy";

	public ProxyView() {
	}

	public static final String ID = "Service_Manager.views.proxyView";
	private Text text;
	private TreeViewer treeViewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ProxyViewContentProvider implements ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ArrayList) {
				return ((ArrayList<ProxyService>) inputElement).toArray();
			}
			return new Object[0];
		}

		@Override
		public Object[] getChildren(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getParent(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasChildren(Object arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	class ProxyViewLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof ProxyService) {
				return ((ProxyService) element).getNameAndService();
			}
			return null;
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		Group group = new Group(parent, SWT.NONE);
		group.setText(LABEL_SEARCH);
		group.setLayout(new GridLayout(3, false));

		Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label.setText(LABEL_SEARCH_TEXT);

		text = new Text(group, SWT.BORDER);
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				searchProxyServices(text.getText());
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label label_1 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_1 = new GridData(SWT.FILL, SWT.CENTER, true, false,
				3, 1);
		gd_label_1.widthHint = 98;
		label_1.setLayoutData(gd_label_1);

		treeViewer = new TreeViewer(group, SWT.BORDER);
		treeViewer.setContentProvider(new ProxyViewContentProvider());
		treeViewer.setLabelProvider(new ProxyViewLabelProvider());
		Tree tree = treeViewer.getTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				IHandlerService handlerService = (IHandlerService) getSite()
			            .getService(IHandlerService.class);
			        try {
			          handlerService.executeCommand("Service_Manager.commands.openProxy", null);
			        } catch (Exception ex) {
			          throw new RuntimeException("Service_Manager.commands.openProxy not found");
			        }
			}
		});
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_tree.widthHint = 508;
		tree.setLayoutData(gd_tree);
		Activator.getDefault();
		updateTree();
		initContextMenu();
	}

	private void initContextMenu() {
		// initalize the context menu
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				OpenProxyAction openProxy = new OpenProxyAction(treeViewer);
				openProxy.setText("Abrir Proxy");
				DeleteProxyAction deleteProxy = new DeleteProxyAction(treeViewer);
				deleteProxy.setText("Eliminar Proxy");
				
				manager.add(openProxy);
				manager.add(deleteProxy);
			}
		});
		TreeViewer viewer = this.treeViewer;
		Menu menu = menuMgr.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		text.setFocus();
	}
	
	public void updateTree(){
		treeViewer.setInput(Activator.serviceManager.getProxyList());
	}

	private void searchProxyServices(String query) {
		Activator.getDefault();
		ArrayList<ProxyService> input = Activator.serviceManager
				.searchProxyService(query);
		treeViewer.setInput(input);
	}
}