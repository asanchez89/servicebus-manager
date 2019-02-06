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
import org.eclipse.ui.part.ViewPart;

import service_manager.Activator;
import service_manager.actions.DeleteBusinessAction;
import service_manager.actions.OpenBusinessAction;
import service_manager.types.BusinessService;

public class BusinessView extends ViewPart {
	private static final String LABEL_SEARCH_TEXT = "Nombre del Business: ";
	private static final String LABEL_SEARCH = "Administración de Business";
	public BusinessView() {
	}

	public static final String ID = "Service_Manager.views.businessView";
	private Text text;
	private TreeViewer treeViewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class BusinessViewContentProvider implements ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ArrayList) {
				return ((ArrayList<BusinessService>) inputElement).toArray();
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

	class BusinessViewLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof BusinessService) {
				return ((BusinessService) element).getNameAndService();
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
				searchBusinessServices(text.getText());
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label label_1 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_1 = new GridData(SWT.FILL, SWT.CENTER, true, false,
				3, 1);
		gd_label_1.widthHint = 98;
		label_1.setLayoutData(gd_label_1);

		treeViewer = new TreeViewer(group, SWT.BORDER);
		treeViewer.setContentProvider(new BusinessViewContentProvider());
		treeViewer.setLabelProvider(new BusinessViewLabelProvider());
		Tree tree = treeViewer.getTree();
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_tree.widthHint = 204;
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
				OpenBusinessAction open = new OpenBusinessAction(treeViewer);
				open.setText("Abrir Business");
				DeleteBusinessAction delete = new DeleteBusinessAction(treeViewer);
				delete.setText("Eliminar Business");
				manager.add(open);
				manager.add(delete);
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
		treeViewer.setInput(Activator.serviceManager.getBusinessList());
	}

	private void searchBusinessServices(String query) {
		Activator.getDefault();
		ArrayList<BusinessService> input = Activator.serviceManager
				.searchBusinessService(query);
		treeViewer.setInput(input);
	}
}