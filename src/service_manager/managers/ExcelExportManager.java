package service_manager.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import service_manager.Activator;
import service_manager.types.BusinessService;
import service_manager.types.ProxyService;

public class ExcelExportManager {

	private ServiceManager serviceManager;
	private CellStyle csContent;
	private CellStyle csHeader;
	
	public void exportToExcel(String busId, String fileName, Shell shell) {
		this.serviceManager = Activator.getDefault().serviceManager;
		HSSFWorkbook workbook = new HSSFWorkbook();

		this.csContent = workbook.createCellStyle();
		this.csContent.setWrapText(true);
		headerStyle(workbook);
		 
		proxySheet(workbook, busId);
		businessSheet(workbook, busId);

		try {
			FileOutputStream out = new FileOutputStream(new File(fileName));
			workbook.write(out);
			out.close();
			MessageDialog.openConfirm(shell, "Exportar a Excel",
					"El archivo ha sido exportado correctamente.");

		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(shell, "Error en guardado", e.getMessage());
		}
	}

	private void generateCells(HSSFSheet sheet, Map<String, Object[]> data, Integer colQty) {
		SortedSet<String> keyset = new TreeSet<String>(data.keySet());

		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
				
				if(rownum==1)
					cell.setCellStyle(csHeader);
				if(rownum>1)
					cell.setCellStyle(csContent);
			}
		}
		for (int i = 0; i < colQty; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private void proxySheet(HSSFWorkbook workbook, String busId) {
		HSSFSheet sheet = workbook.createSheet("Proxy Service");
		ArrayList<ProxyService> proxyList = this.serviceManager.getProxyList();
		int i = 1;
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		Object[] header = new Object[] { "Nombre", "Ubicación",
				"URI", "Protocolo", "WSDL", "Descripción",
				"Business Relacionados" };
		data.put(String.valueOf(i), header);
		for (ProxyService proxyService : proxyList) {
			if (proxyService.getServiceBus().getId().equals(busId)) {
				i++;
				data.put(
						String.valueOf(i),
						new Object[] { proxyService.getName(),
								proxyService.getLocation(),
								proxyService.getUri(),
								proxyService.getProtocol(),
								proxyService.getWsdl(),
								proxyService.getDescription(),
								getBusinessFromProxy(proxyService) });
			}
		}
		generateCells(sheet, data, header.length);
	}

	private void businessSheet(HSSFWorkbook workbook, String busId) {
		HSSFSheet sheet = workbook.createSheet("Business Service");
		ArrayList<BusinessService> businessList = this.serviceManager
				.getBusinessList();
		int i = 1;
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		Object[] header = new Object[] { "Nombre", "Ubicación",
				"Tipo de Servicio", "Servicio Local", "Acceso remoto",
				"Acceso local", "Dirección Local", "Dirección Remota",
				"Servicio Remoto invocado", "Descripción",
				"Proxies Relacionados" };
		data.put(String.valueOf(i), header);
		for (BusinessService businessService : businessList) {
			if (businessService.getServiceBus().getId().equals(busId)) {
				i++;
				data.put(
						String.valueOf(i),
						new Object[] { businessService.getName(),
								businessService.getLocation(),
								businessService.getServiceType(),
								businessService.getLocalService(),
								businessService.getRemoteAccess(),
								businessService.getLocalAccess(),
								businessService.getLocalAddress(),
								businessService.getRemoteAddress(),
								businessService.getRemoteService(),
								businessService.getDescription(),
								getProxyFromBusimess(businessService) });
			}
		}
		generateCells(sheet, data, header.length);
	}

	private String getBusinessFromProxy(ProxyService proxyService) {
		StringBuffer buffer = new StringBuffer();
		for (BusinessService businessService : proxyService
				.getBusinessServices()) {
			buffer.append(businessService.getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	private String getProxyFromBusimess(BusinessService businessService) {
		StringBuffer buffer = new StringBuffer();
		ArrayList<ProxyService> proxyList = this.serviceManager
				.getProxyFromBusiness(businessService);
		for (ProxyService proxyService : proxyList) {
			buffer.append(proxyService.getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	private void headerStyle(HSSFWorkbook workbook){
		this.csHeader = workbook.createCellStyle();
		this.csHeader.setWrapText(true);
		Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        this.csHeader.setAlignment(CellStyle.ALIGN_CENTER);
        this.csHeader.setFont(headerFont);
        this.csHeader.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        this.csHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}
}
