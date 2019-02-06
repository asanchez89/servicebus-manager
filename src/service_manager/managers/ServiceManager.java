package service_manager.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import service_manager.ServiceComparator;
import service_manager.types.BusinessService;
import service_manager.types.ProxyService;
import service_manager.types.ServiceBus;

public class ServiceManager {

	private static final String NODENAME_BUSINESS = "business";
	private static final String NODENAME_PROXY = "proxy";
	private static final String NODENAME_SERVICEBUS = "service-bus";
	private static String FILE_LOCATION = "xmlData/xmlData.xml";
//	private static String FILE_LOCATION = "D://workspace//Service Manager//xmlData//xmlData.xml";
	private File xmlFile;
	private Document doc;
	private ArrayList<ProxyService> proxyList;
	private ArrayList<BusinessService> businessList;
	private ArrayList<ServiceBus> servicebusList;

	public ServiceManager() {
		getDataFromXml();
	}

	public ArrayList<ProxyService> getProxyList() {
		return proxyList;
	}

	public ArrayList<BusinessService> getBusinessList() {
		return businessList;
	}

	public ArrayList<ServiceBus> getServicebusList() {
		return servicebusList;
	}

	public void getDataFromXml() {
		try {
//			URL url = this.getClass().getResource(FILE_LOCATION);
			
			this.xmlFile = new File(FILE_LOCATION);
			System.out.println(this.xmlFile.getAbsolutePath());
			if (this.xmlFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

				this.doc = dBuilder.parse(this.xmlFile);
				this.doc.getDocumentElement().normalize();

				NodeList nPList = this.doc.getElementsByTagName(NODENAME_PROXY);
				NodeList nBList = this.doc
						.getElementsByTagName(NODENAME_BUSINESS);
				NodeList nSList = this.doc
						.getElementsByTagName(NODENAME_SERVICEBUS);

				this.businessList = new ArrayList<BusinessService>(0);
				this.proxyList = new ArrayList<ProxyService>(0);

				getServiceBusList(nSList);
				getBusinessList(nBList);
				getProxyList(nPList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer setDataToXml() {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			this.doc = dBuilder.newDocument();
			Element services = this.doc.createElement("services");
			Element proxies = this.doc.createElement("proxies");
			Element businesses = this.doc.createElement("businesses");
			Element servicesBuS = this.doc.createElement("services-bus");

			services.appendChild(proxies);
			services.appendChild(businesses);
			services.appendChild(servicesBuS);
			setBusinessList(businesses);
			setProxyList(proxies);
			setServiceBusList(servicesBuS);

			this.doc.appendChild(services);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(this.doc);
			StreamResult result = new StreamResult(new File(FILE_LOCATION));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);

			System.out.println("File saved!");
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public ArrayList<ProxyService> searchProxyService(String query) {
		ArrayList<ProxyService> result = new ArrayList<ProxyService>(0);
		for (ProxyService proxyService : this.proxyList) {
			if (proxyService.getName().toUpperCase()
					.contains(query.toUpperCase())) {
				result.add(proxyService);
			}

		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public ArrayList<BusinessService> searchBusinessService(String query) {
		ArrayList<BusinessService> result = new ArrayList<BusinessService>(0);
		for (BusinessService businessService : this.businessList) {
			if (businessService.getName().toUpperCase()
					.contains(query.toUpperCase())) {
				result.add(businessService);
			}

		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public ArrayList<ServiceBus> searchServiceBus(String query) {
		ArrayList<ServiceBus> result = new ArrayList<ServiceBus>(0);
		for (ServiceBus serviceBus : this.servicebusList) {
			if (serviceBus.getName().toUpperCase()
					.contains(query.toUpperCase())) {
				result.add(serviceBus);
			}

		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public void addProxy(ProxyService proxyService) {
		if (this.proxyList.size() > 0)
			deleteProxy(proxyService);
		this.proxyList.add(proxyService);
		sortProxyList();
	}

	public void deleteProxy(ProxyService proxyService) {
		this.proxyList.remove(proxyService);
		sortProxyList();
	}

	public ProxyService getProxy(String id) {
		for (ProxyService pService : this.proxyList) {
			if (pService.getId().equals(id))
				return pService;
		}
		return null;
	}

	public ArrayList<ProxyService> getProxyListByServiceBus(
			ServiceBus serviceBus) {
		ArrayList<ProxyService> result = new ArrayList<ProxyService>();
		for (ProxyService pService : this.proxyList) {
			if (pService.getServiceBus().equals(serviceBus)) {
				result.add(pService);
			}
		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public void addBusiness(BusinessService businessService) {
		if (this.businessList.size() > 0)
			deleteBusiness(businessService);
		this.businessList.add(businessService);
		sortBusinessList();
	}

	public BusinessService getBusinessById(String id) {
		for (BusinessService bService : this.businessList) {
			if (bService.getId().equals(id))
				return bService;
		}
		return null;
	}

	public void deleteBusiness(BusinessService businessService) {
		this.businessList.remove(businessService);
		for (ProxyService proxyService : this.proxyList) {
			proxyService.getBusinessServices().remove(businessService);
		}
		sortBusinessList();
	}

	public ServiceBus getServiceBusById(String id) {
		for (ServiceBus serviceBus : this.servicebusList) {
			if (serviceBus.getId().equals(id))
				return serviceBus;
		}
		return null;
	}

	public ArrayList<BusinessService> getBusinessListByServiceBus(
			ServiceBus serviceBus) {
		ArrayList<BusinessService> result = new ArrayList<BusinessService>();
		for (BusinessService bService : this.businessList) {

			if (bService.getServiceBus() != null
					&& bService.getServiceBus().equals(serviceBus)) {
				result.add(bService);
			}
		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public ArrayList<BusinessService> getBusinessListFromProxyServiceByServiceBus(
			ProxyService pService, ServiceBus serviceBus) {
		ArrayList<BusinessService> result = new ArrayList<BusinessService>();
		for (BusinessService bService : pService.getBusinessServices()) {
			if (bService.getServiceBus().equals(serviceBus)) {
				result.add(bService);
			}
		}
		Collections.sort(result, new ServiceComparator());
		return result;
	}

	public void addServiceBus(ServiceBus serviceBus) {
		if (this.servicebusList.size() > 0)
			deleteServiceBus(serviceBus);
		this.servicebusList.add(serviceBus);
		sortServiceList();
	}

	public void deleteServiceBus(ServiceBus serviceBus) {
		this.servicebusList.remove(serviceBus);
		for (ProxyService proxyService : this.proxyList) {
			if (proxyService.getServiceBus().equals(serviceBus))
				proxyService.setServiceBus(new ServiceBus());
		}
		for (BusinessService businessService : this.businessList) {
			if (businessService.getServiceBus().equals(serviceBus))
				businessService.setServiceBus(new ServiceBus());
		}
		sortServiceList();
	}

	public ArrayList<ProxyService> getProxyFromBusiness(
			BusinessService businessService) {
		ArrayList<ProxyService> proxyResult = new ArrayList<ProxyService>(0);
		for (ProxyService proxyService : proxyList) {
			for (BusinessService pBusinessService : proxyService
					.getBusinessServices()) {
				if (businessService.equals(pBusinessService)) {
					proxyResult.add(proxyService);
					break;
				}

			}
		}
		Collections.sort(proxyResult, new ServiceComparator());
		return proxyResult;
	}

	private void getBusinessList(NodeList nBList) {
		this.businessList = new ArrayList<BusinessService>(0);
		for (int i = 0; i < nBList.getLength(); i++) {
			Node businessNode = nBList.item(i);
			if (businessNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) businessNode;

				String pId = eElement.getAttribute("id");
				String pName = eElement.getAttribute("name");
				String pLocation = eElement.getAttribute("location");
				String pServiceType = eElement.getAttribute("serviceType");
				String pLocalService = eElement.getAttribute("localService");
				String pRemoteAccess = eElement.getAttribute("remoteAccess");
				String pRemoteAddress = eElement.getAttribute("remoteAddress");
				String pRemoteService = eElement.getAttribute("remoteService");
				String pLocalAccess = eElement.getAttribute("localAccess");
				String pLocalAddress = eElement.getAttribute("localAddress");
				String pServiceBus = eElement.getAttribute("service-bus-id");
				String pDesc = eElement.getElementsByTagName("description")
						.item(0).getTextContent().trim();

				this.businessList.add(new BusinessService(pId, pName,
						pLocation, pServiceType, pLocalService, pRemoteAccess,
						pLocalAccess, pLocalAddress, pRemoteAddress,
						pRemoteService, pDesc, getServiceBusById(pServiceBus)));
			}
		}
		sortBusinessList();
	}

	private void setBusinessList(Element businesses) {
		for (int i = 0; i < this.businessList.size(); i++) {
			Element eElement = this.doc.createElement("business");
			BusinessService businessService = this.businessList.get(i);
			eElement.setAttribute("id", businessService.getId());
			eElement.setAttribute("name", businessService.getName());
			eElement.setAttribute("location", businessService.getLocation());
			eElement.setAttribute("serviceType",
					businessService.getServiceType());
			eElement.setAttribute("localService",
					businessService.getLocalService());
			eElement.setAttribute("remoteAccess",
					businessService.getRemoteAccess());
			eElement.setAttribute("remoteAddress",
					businessService.getRemoteAddress());
			eElement.setAttribute("remoteService",
					businessService.getRemoteService());
			eElement.setAttribute("localAccess",
					businessService.getLocalAccess());
			eElement.setAttribute("localAddress",
					businessService.getLocalAddress());
			if (businessService.getServiceBus() != null)
				eElement.setAttribute("service-bus-id", businessService
						.getServiceBus().getId());

			Node descriptionNode = this.doc.createElement("description");
			descriptionNode.setNodeValue(businessService.getDescription());
			eElement.appendChild(descriptionNode);

			businesses.appendChild(eElement);
		}
	}

	private void getProxyList(NodeList nPList) {
		for (int i = 0; i < nPList.getLength(); i++) {
			Node proxyNode = nPList.item(i);
			if (proxyNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) proxyNode;

				String pId = eElement.getAttribute("id");
				String pName = eElement.getAttribute("name");
				String pLocation = eElement.getAttribute("location");
				String pUri = eElement.getAttribute("uri");
				String pWsdl = eElement.getAttribute("wsdl");
				String pProtocol = eElement.getAttribute("protocol");
				String pServiceBus = eElement.getAttribute("service-bus-id");
				String pDesc = eElement.getElementsByTagName("description")
						.item(0).getTextContent().trim();
				ArrayList<BusinessService> bServiceList = new ArrayList<BusinessService>(
						0);

				NodeList bPList = eElement.getChildNodes();
				for (int j = 0; j < bPList.getLength(); j++) {
					Node businessNode = bPList.item(j);
					if (businessNode.getNodeType() == Node.ELEMENT_NODE
							&& businessNode.getNodeName().equals(
									"business-proxy")) {
						Element businessElement = (Element) businessNode;
						String bName = businessElement.getAttribute("id");
						if (this.businessList != null
								|| this.businessList.size() > 0) {
							for (BusinessService bService : this.businessList) {
								if (bService.getId().equals(bName)) {
									bServiceList.add(bService);
								}

							}
							Collections.sort(bServiceList,
									new ServiceComparator());
						}
					}
				}

				this.proxyList.add(new ProxyService(pId, pName, pLocation,
						pUri, pProtocol, pWsdl, bServiceList, pDesc,
						getServiceBusById(pServiceBus)));
			}
		}
		sortProxyList();
	}

	private void setProxyList(Element proxies) {
		for (int i = 0; i < this.proxyList.size(); i++) {

			Element eElement = (Element) this.doc.createElement("proxy");
			ProxyService proxyService = this.proxyList.get(i);
			eElement.setAttribute("id", proxyService.getId());
			eElement.setAttribute("name", proxyService.getName());
			eElement.setAttribute("location", proxyService.getLocation());
			eElement.setAttribute("uri", proxyService.getUri());
			eElement.setAttribute("wsdl", proxyService.getWsdl());
			eElement.setAttribute("protocol", proxyService.getProtocol());
			if (proxyService.getServiceBus() != null)
				eElement.setAttribute("service-bus-id", proxyService
						.getServiceBus().getId());

			Element description = this.doc.createElement("description");
			description.setTextContent(proxyService.getDescription());
			eElement.appendChild(description);

			for (BusinessService bServices : proxyService.getBusinessServices()) {
				Element business = this.doc.createElement("business-proxy");
				business.setAttribute("id", bServices.getId());
				eElement.appendChild(business);
			}

			proxies.appendChild(eElement);
		}
	}

	private void getServiceBusList(NodeList nSList) {
		this.servicebusList = new ArrayList<ServiceBus>(0);
		for (int i = 0; i < nSList.getLength(); i++) {
			Node serviceBusNode = nSList.item(i);
			if (serviceBusNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) serviceBusNode;

				String pId = eElement.getAttribute("id");
				String pName = eElement.getAttribute("name");
				String pUrl = eElement.getAttribute("url");
				String pDesc = eElement.getElementsByTagName("description")
						.item(0).getTextContent().trim();

				this.servicebusList
						.add(new ServiceBus(pId, pName, pUrl, pDesc));
			}
		}
		sortServiceList();
	}

	private void setServiceBusList(Element services) {
		for (int i = 0; i < this.servicebusList.size(); i++) {
			Element eElement = this.doc.createElement("service-bus");
			ServiceBus serviceBus = this.servicebusList.get(i);
			eElement.setAttribute("id", serviceBus.getId());
			eElement.setAttribute("name", serviceBus.getName());
			eElement.setAttribute("url", serviceBus.getUrl());

			Node descriptionNode = this.doc.createElement("description");
			descriptionNode.setNodeValue(serviceBus.getDescription());
			eElement.appendChild(descriptionNode);

			services.appendChild(eElement);
		}
	}

	public String setIdNewProxy(String id) {
		for (ProxyService service : this.proxyList) {
			if (service.getId().equals(id)) {
				return setIdNewProxy(String
						.valueOf((int) (Math.random() * 10000)));
			}
		}
		return id;
	}

	public String setIdNewBusiness(String id) {
		for (BusinessService service : this.businessList) {
			if (service.getId().equals(id)) {
				return setIdNewBusiness(String
						.valueOf((int) (Math.random() * 10000)));
			}
		}
		return id;
	}

	public String setIdNewServiceBus(String id) {
		for (ServiceBus service : this.servicebusList) {
			if (service.getId().equals(id)) {
				return setIdNewServiceBus(String
						.valueOf((int) (Math.random() * 10000)));
			}
		}
		return id;
	}

	private void sortBusinessList() {
		Collections.sort(this.businessList, new ServiceComparator());
	}

	private void sortProxyList() {
		Collections.sort(this.proxyList, new ServiceComparator());
	}

	private void sortServiceList() {
		Collections.sort(this.servicebusList, new ServiceComparator());
	}

}
