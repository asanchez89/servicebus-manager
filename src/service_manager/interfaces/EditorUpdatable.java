package service_manager.interfaces;

import org.eclipse.jface.viewers.ListViewer;

import service_manager.types.ServiceBus;

public interface EditorUpdatable {
	
	public Integer save();
	public void reset();
	public void openEditorFormListViewer(ListViewer listViewer);
	public void updateList();
	public void updateCombo(ServiceBus serviceBus);

}
