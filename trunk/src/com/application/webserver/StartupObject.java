package com.application.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.application.webserver.parameters.ParameterXmlReader;
import com.dao.core.IbatisGenericDao;

import com.web.form.administration.Dictionary;
import com.web.form.administration.Module;
import com.web.form.administration.Unit;
import com.web.form.administration.UnitLevel;
import com.web.form.administration.UserLevel;
import com.web.service.communication.DatagramConfig;
import com.web.service.communication.DatagramField;
import com.web.service.communication.DatagramField.Enum_format;
import com.web.service.communication.DatagramField.Enum_pending_dir;

public class StartupObject implements IStartupObject {
	protected IbatisGenericDao     sqlDao_i;

	public void setSqlDao_i(IbatisGenericDao sqlDao_i) {
		this.sqlDao_i = sqlDao_i;
	}
	
	private static final String DICTIONARYRESOURCE = "messages-dictionary";
	private String debitCashFilePathL2 = Configure.getConfig("createFile.L2.QUARTZ");
	private String debitCashFilePathCenter = Configure.getConfig("createFile.CENTER.QUARTZ");
	
	public void initializeContextVariables(ServletContext context)
	{
		//get unit level configuration from xml file
		Document unitlevel_doc = ParameterXmlReader.readParameterFromXml("com/application/webserver/parameters/unitlevel.xml");
		if(unitlevel_doc != null){
			UnitLevelDocumentParser parser = new UnitLevelDocumentParser();
			List<UnitLevel> unitLevelList = parser.parse(unitlevel_doc);
			context.setAttribute(ApplicationConstants.UNITLEVEL, unitLevelList);
		}
		
		Document userlevel_doc = ParameterXmlReader.readParameterFromXml("com/application/webserver/parameters/userlevel.xml");
		if(userlevel_doc != null){
			UserLevelDocumentParser parser = new UserLevelDocumentParser();
			List<UserLevel> userLevelList = parser.parse(userlevel_doc);
			context.setAttribute(ApplicationConstants.USERLEVEL, userLevelList);
		}
		
		//get all unit info from database
		List<Unit> unitList = this.sqlDao_i.getRecordList("SystemUnit.selectAllUnits", 0);
		Unit unitTree = new Unit();
		for(Unit unit : unitList){
			if(unit.getNodelevel().equals(0)){//root unit
				unitTree.setNodeid(unit.getNodeid());
				unitTree.setParentnodeid(unit.getParentnodeid());
				unitTree.setNodelevel(unit.getNodelevel());
				unitTree.setNodename(unit.getNodename());
			}else{//children
				Unit temp_unit = unitTree.findNodeById(unit.getParentnodeid());
				temp_unit.addChildNode(unit);
			}
		}
		context.setAttribute(ApplicationConstants.SYSTEMUNITTREE, unitTree);

//		List<Bimsgsndrulttb> rule_list = sqlDao_i.getRecordList(
//				"BiMsgSendRule.selectSystemMsgRulesAll", (Object) null);
//		context.setAttribute(ApplicationConstants.MESSAGESENDRULE, rule_list);
		
		//get system dictionary from xml file
		Document dictionary_doc = ParameterXmlReader.readParameterFromXml("com/application/webserver/parameters/systemdictionary.xml");
		if(dictionary_doc != null){
			DictionaryDocumentParser parser = new DictionaryDocumentParser();
			List<Dictionary> dictionaryList = parser.parse(dictionary_doc);
			if(dictionaryList != null && dictionaryList.size() != 0){
				context.setAttribute(ApplicationConstants.SYSTEMDICTIONARYLIST, dictionaryList);
				String defaultType = "00000";
				Map<String,Map<String,String>> nodeMap = new LinkedHashMap<String, Map<String,String>>();
				Map<String,String> leafMap = null;
				
				for(Dictionary temp_dictitem : dictionaryList){
					if(!temp_dictitem.getDictType().equals(defaultType)){
						if(!defaultType.equals("00000")){
							nodeMap.put(defaultType, leafMap);
						}
						defaultType = temp_dictitem.getDictType();
						leafMap = new LinkedHashMap<String,String>();
						leafMap.put(temp_dictitem.getDictValue(), temp_dictitem.getDictValueDesc());
					}else{
						leafMap.put(temp_dictitem.getDictValue(), temp_dictitem.getDictValueDesc());
					}
				}
				if(leafMap != null){
					nodeMap.put(defaultType, leafMap);
				}
				context.setAttribute(ApplicationConstants.SYSTEMDICTIONARY, nodeMap);
			}
		}
		
		//get system common privileges from xml file
		Document rootprivilege_doc = ParameterXmlReader.readParameterFromXml("com/application/webserver/parameters/rootprivilege.xml");
		if(rootprivilege_doc != null){
			PrivilegeDocumentParser parser = new PrivilegeDocumentParser();
			List<Module> privilegeList = parser.parse(rootprivilege_doc);
			context.setAttribute(ApplicationConstants.ROOTPRIVILEGE, privilegeList);
		}
		
		// 报文解析对象
				List<DatagramConfig> datagram_config_list = new ArrayList<DatagramConfig>();
				//Map<String, BillPrintConfig> billprint_config_map = new HashMap<String, BillPrintConfig>();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				URL url = loader.getResource("com/application/webserver/parameters");
				try {
					File directory = new File(url.toURI());
					File[] files = directory.listFiles();
//					Comparator<BillPrintItems> comp=new Comparator<BillPrintItems>(){
//						@Override
//						public int compare(BillPrintItems o1, BillPrintItems o2) {
//							// TODO Auto-generated method stub
//							float x1=o1.getX();
//							float y1=o1.getY();
//							float x2=o2.getX();
//							float y2=o2.getY();
//							if(y1<y2){
//								return -1;
//							}else if(y1>y2){
//								return 1;
//							}else{
//								if(x1<x2){
//									return -1;
//								}else if(x1>x2){
//									return 1;
//								}else{
//									return 0;
//								}
//							}
//						}};
					for (File f : files) {
						String filename = f.getName();
						if (filename.matches("datagram.*\\.xml")) {
							Document datagram_doc = ParameterXmlReader
									.readParameterFromXml("com/application/webserver/parameters/"
											+ filename);
							DatagramDocumentParser parser = new DatagramDocumentParser();
							DatagramConfig config = parser.parse(datagram_doc);
							datagram_config_list.add(config);
						}
//						if (filename.matches("billprint.*\\.xml")) {
//							System.out.println(filename);
//							Document billprint_doc = ParameterXmlReader
//									.readParameterFromXml("com/application/webserver/parameters/"
//											+ filename);
//							BillPrintDocumentParser parser = new BillPrintDocumentParser();
//							BillPrintConfig config = parser.parse(billprint_doc); 
//							Collections.sort(config.getItems(), comp);
//							billprint_config_map.put(config.getName(), config);
//						}
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				context.setAttribute(ApplicationConstants.DATAGRAMCONFIG,
						datagram_config_list);
		
		//删除定时器下面的文件
		File debitCashQuartzFileL2 = new File(debitCashFilePathL2+ "/DEBITCASHQUARTZ");
		if(debitCashQuartzFileL2.exists()){
			debitCashQuartzFileL2.delete();
		}
		
		File cardOverServerQuartzFileL2 = new File(debitCashFilePathL2+ "/CARDOVERSERVERQUARTZ");
		if(cardOverServerQuartzFileL2.exists()){
			cardOverServerQuartzFileL2.delete();
		}
		
		File busSocketFileL2 = new File(debitCashFilePathL2+"/BUSSOCKETQUARTZ");
		if(busSocketFileL2.exists()){
			busSocketFileL2.delete();
		}
		
		File debitCashQuartzFileCenter = new File(debitCashFilePathCenter+ "/DEBITCASHQUARTZ");
		if(debitCashQuartzFileCenter.exists()){
			debitCashQuartzFileCenter.delete();
		}
		
		File cardOverServerQuartzFileCenter = new File(debitCashFilePathCenter+ "/CARDOVERSERVERQUARTZ");
		if(cardOverServerQuartzFileCenter.exists()){
			cardOverServerQuartzFileCenter.delete();
		}
		
		File busSocketFileCenter = new File(debitCashFilePathCenter+"/BUSSOCKETQUARTZ");
		if(busSocketFileCenter.exists()){
			busSocketFileCenter.delete();
		}
		
		File busUncollectQuartzFileL2 = new File(debitCashFilePathL2+ "/BUSUNCOLLECT");
		if(busUncollectQuartzFileL2.exists()){
			busUncollectQuartzFileL2.delete();
		}
		
		File batTradeDtlFloatFile = new File(debitCashFilePathCenter+"/BATTRADEDTLFLOATQUARTZ");
		if(batTradeDtlFloatFile.exists()){
			batTradeDtlFloatFile.delete();
		}
		
		String insuranceDzpfProcessFilePath = debitCashFilePathCenter + "/INSURANCEDZPFPROCESS";
		File debitCashQuartzFile = new File(insuranceDzpfProcessFilePath);
		if (debitCashQuartzFile.exists()) {
			debitCashQuartzFile.delete();
		}
		
	}
	
	public class UnitLevelDocumentParser extends DocumentParser{
		private List<UnitLevel> unitlevel_list = new ArrayList<UnitLevel>();
		
		public UnitLevelDocumentParser(){
			this.addNodeLet("/unit-level-config/unit-level", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node, null);
					UnitLevel level = new UnitLevel();
					level.setId(props.getProperty("id"));
					level.setDesc(props.getProperty("desc"));
					unitlevel_list.add(level);
					return level;
				}
			});
			this.addNodeLet("/unit-level-config/unit-level/level", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					UnitLevel level = (UnitLevel)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							level.setLevel(Integer.parseInt(item_node.getNodeValue()));
							break;
						}
					}
					return level;
				}
			});
			this.addNodeLet("/unit-level-config/unit-level/attribute-name", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					UnitLevel level = (UnitLevel)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							level.setAttributename(item_node.getNodeValue());
							break;
						}
					}
					return null;
				}
			});
		}
		
		public List<UnitLevel> parse(Document doc){
			Node node = doc.getLastChild();
			parse(node, new Path("//"), (Object[])null);
			return this.unitlevel_list;
		}
		
		private Object parse(Node node, Path path, Object...objects) {
			// Element
			if(node instanceof Element){
				String elementName = node.getNodeName();
				path.add(elementName);
				Object obj = this.processNodelet(node, path.toString(), objects);
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					parse(children.item(i), path, obj);
				}
				path.remove();
			}
			return null;
		}
	}
	
	
	public class UserLevelDocumentParser extends DocumentParser{
		private List<UserLevel> userlevel_list = new ArrayList<UserLevel>();
		
		public UserLevelDocumentParser(){
			this.addNodeLet("/user-level-config/user-level", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node, null);
					UserLevel level = new UserLevel();
					level.setId(props.getProperty("id"));
					level.setDesc(props.getProperty("desc"));
					userlevel_list.add(level);
					return level;
				}
			});
			this.addNodeLet("/user-level-config/user-level/level", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					UserLevel level = (UserLevel)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							level.setLevel(Integer.parseInt(item_node.getNodeValue()));
							break;
						}
					}
					return level;
				}
			});
			this.addNodeLet("/user-level-config/user-level/attribute-name", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					UserLevel level = (UserLevel)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							level.setAttributename(item_node.getNodeValue());
							break;
						}
					}
					return null;
				}
			});
		}
		
		public List<UserLevel> parse(Document doc){
			Node node = doc.getLastChild();
			parse(node, new Path("//"), (Object[])null);
			return this.userlevel_list;
		}
		
		private Object parse(Node node, Path path, Object...objects) {
			// Element
			if(node instanceof Element){
				String elementName = node.getNodeName();
				path.add(elementName);
				Object obj = this.processNodelet(node, path.toString(), objects);
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					parse(children.item(i), path, obj);
				}
				path.remove();
			}
			return null;
		}
	}
	
	
	public class DictionaryDocumentParser extends DocumentParser{
		private List<Dictionary> dict_list = new ArrayList<Dictionary>();
		
		public DictionaryDocumentParser(){
			this.addNodeLet("/system-dictionary-config/dictionary-item", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node, null);
					Dictionary dict = new Dictionary();
					dict.setDictType(props.getProperty("id"));
					dict.setDictTypeDesc(props.getProperty("desc"));
					//dict_list.add(dict);
					return dict;
				}
			});
			this.addNodeLet("/system-dictionary-config/dictionary-item/item", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Dictionary tmp_dict = (Dictionary)objects[0];
					Dictionary dict = new Dictionary();
					dict.setDictType(tmp_dict.getDictType());
					dict.setDictTypeDesc(tmp_dict.getDictTypeDesc());
					dict_list.add(dict);
					return dict;
				}
			});
			this.addNodeLet("/system-dictionary-config/dictionary-item/item/value", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Dictionary dict = (Dictionary)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							dict.setDictValue((item_node.getNodeValue()));
							break;
						}
					}
					return dict;
				}
			});
			this.addNodeLet("/system-dictionary-config/dictionary-item/item/value-description", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Dictionary dict = (Dictionary)objects[0];
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							dict.setDictValueDesc((item_node.getNodeValue()));
							break;
						}
					}
					return dict;
				}
			});
		}
		
		public List<Dictionary> parse(Document doc){
			Node node = doc.getLastChild();
			parse(node, new Path("//"), (Object[])null);
			return this.dict_list;
		}
		
		private Object parse(Node node, Path path, Object...objects) {
			// Element
			if(node instanceof Element){
				String elementName = node.getNodeName();
				path.add(elementName);
				Object obj = this.processNodelet(node, path.toString(), objects);
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					parse(children.item(i), path, obj);
				}
				path.remove();
			}
			return null;
		}
	}	
	
	public class PrivilegeDocumentParser extends DocumentParser{
		private List<Module> module_list = new ArrayList<Module>();
		
		public PrivilegeDocumentParser(){
			this.addNodeLet("/root-privilege-config/privilege", new INodeParser(){
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node, null);
					Module module = new Module();
					module.setModulename(props.getProperty("name"));
					String logging = props.getProperty("logging");
					if(logging.equals("true")){
						module.setLogflag((short)1);
					}else if(logging.equals("false")){
						module.setLogflag((short)0);
					}
					NodeList itemNodeList = node.getChildNodes();
					for(int j=0;j<itemNodeList.getLength();j++){
						Node item_node = itemNodeList.item(j);
						if(item_node.getNodeValue() != null){
							module.setModuleaction((item_node.getNodeValue()));
							break;
						}
					}
					module_list.add(module);
					return null;
				}
			});
		}
		public List<Module> parse(Document doc){
			Node node = doc.getLastChild();
			parse(node, new Path("//"), (Object[])null);
			return this.module_list;
		}
		
		private Object parse(Node node, Path path, Object...objects) {
			// Element
			if(node instanceof Element){
				String elementName = node.getNodeName();
				path.add(elementName);
				this.processNodelet(node, path.toString(), objects);
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					parse(children.item(i), path, (Object[])null);
				}
				path.remove();
			}
			return null;
		}
	}

	private static class Path {

		private List<String> nodeList = new ArrayList<String>();

		@SuppressWarnings("unused")
		public Path() {
		}

		public Path(String path) {
			StringTokenizer parser = new StringTokenizer(path, "/", false);
			while (parser.hasMoreTokens()) {
				nodeList.add(parser.nextToken());
			}
		}

		public void add(String node) {
			nodeList.add(node);
		}

		public void remove() {
			nodeList.remove(nodeList.size() - 1);
		}

		public String toString() {
			StringBuffer buffer = new StringBuffer("/");
			for (int i = 0; i < nodeList.size(); i++) {
				buffer.append(nodeList.get(i));
				if (i < nodeList.size() - 1) {
					buffer.append("/");
				}
			}
			return buffer.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initializeSystemDictionary_itransc(ServletContext context) {
		// TODO Auto-generated method stub
	
		String realPath = context.getRealPath("/WEB-INF/classes/com/application/messages/" + DICTIONARYRESOURCE);
		List<Dictionary> dict_list = (List<Dictionary>)context.getAttribute(ApplicationConstants.SYSTEMDICTIONARYLIST);
		Locale[] available_locales = Locale.getAvailableLocales();
		List<Properties> property_list = new ArrayList<Properties>();
		List<Locale> locale_list = new ArrayList<Locale>();
		for(Locale locale : available_locales){
			File file = new File(realPath + "_" + locale.toString() + ".properties");
			if(file.exists()){
				locale_list.add(locale);
				try {
					FileInputStream fileStream = new FileInputStream(file);
					Properties property = new Properties();
					property.load(fileStream);
					property_list.add(property);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		sqlDao_i.deleteRecord("SystemDictionary.deleteAllDictionary", null);
		
		for(Dictionary dict : dict_list){
			String dictValueDesc = dict.getDictValueDesc();
			int index = 0;
			for(Locale locale : locale_list){
				dict.setDictValueDesc(property_list.get(index).getProperty(dictValueDesc));
				dict.setLocale(locale.toString());
				sqlDao_i.insertRecord("SystemDictionary.insertSystemDictionary", dict);
				index++;
			}
		}
	}
	
	public class DatagramDocumentParser extends DocumentParser {
		private DatagramConfig config = new DatagramConfig();

		public DatagramDocumentParser() {
			this.addNodeLet("/datagram-config", new INodeParser() {
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node,
							null);
					config.setName(props.getProperty("name"));
					config.setClassname(props.getProperty("classname"));
					config.setFields(new ArrayList<DatagramField>());
					return null;
				}
			});
			this.addNodeLet("/datagram-config/field", new INodeParser() {
				public Object parse(Node node, Object... objects) {
					// TODO Auto-generated method stub
					Properties props = DocumentParser.parseAttributes(node,
							null);
					DatagramField field = new DatagramField();
					String type = props.getProperty("type");
					if (type.equals("B")) {
						field.setType(DatagramField.Enum_type.B);
					} else if (type.equals("A")) {
						field.setType(DatagramField.Enum_type.A);
					} else if (type.equals("N")) {
						field.setType(DatagramField.Enum_type.N);
					} else if (type.equals("S")) {
						field.setType(DatagramField.Enum_type.S);
					} else if (type.equals("AN")) {
						field.setType(DatagramField.Enum_type.AN);
					} else if (type.equals("ANS")) {
						field.setType(DatagramField.Enum_type.ANS);
					} else if (type.equals("MMDD")) {
						field.setType(DatagramField.Enum_type.MMDD);
					} else if (type.equals("YYYYMMDD")) {
						field.setType(DatagramField.Enum_type.YYYYMMDD);
					} else if (type.equals("HHMMSS")) {
						field.setType(DatagramField.Enum_type.HHMMSS);
					} else if (type.equals("YYYYMMDDHHMMSS")) {
						field.setType(DatagramField.Enum_type.YYYYMMDDHHMMSS);
					} else if (type.equals("ALLVAR")) {
						field.setType(DatagramField.Enum_type.ALLVAR);
					} else if (type.equals("NLLVAR")) {
						field.setType(DatagramField.Enum_type.NLLVAR);
					} else if (type.equals("ANLLVAR")) {
						field.setType(DatagramField.Enum_type.ANLLVAR);
					} else if (type.equals("ANSLLVAR")) {
						field.setType(DatagramField.Enum_type.ANSLLVAR);
					} else if (type.equals("BLLVAR")) {
						field.setType(DatagramField.Enum_type.BLLVAR);
					} else if (type.equals("ALLLVAR")) {
						field.setType(DatagramField.Enum_type.ALLLVAR);
					} else if (type.equals("NLLLVAR")) {
						field.setType(DatagramField.Enum_type.NLLLVAR);
					} else if (type.equals("ANLLLVAR")) {
						field.setType(DatagramField.Enum_type.ANLLLVAR);
					} else if (type.equals("ANSLLLVAR")) {
						field.setType(DatagramField.Enum_type.ANSLLLVAR);
					} else if (type.equals("BLLLVAR")) {
						field.setType(DatagramField.Enum_type.BLLLVAR);
					} else if (type.equals("CHINESE")) {
						field.setType(DatagramField.Enum_type.CHINESE);
					} else if (type.equals("CHINESELLVAR")) {
						field.setType(DatagramField.Enum_type.CHINESELLVAR);
					} else if (type.equals("CHINESELLLVAR")) {
						field.setType(DatagramField.Enum_type.CHINESELLLVAR);
					} else if (type.equals("ANSLLVAROTHER")) {
						field.setType(DatagramField.Enum_type.ANSLLVAROTHER);
					} else if (type.equals("ANSLLLVAROTHER")) {
						field.setType(DatagramField.Enum_type.ANSLLLVAROTHER);
					} else if (type.equals("ANSOTHER")) {
						field.setType(DatagramField.Enum_type.ANSOTHER);
					}
					String pendingdir = props.getProperty("pendingdir");
					if (pendingdir.equals("LEADING")) {
						field.setPendingdir(Enum_pending_dir.LEADING);
					} else if (pendingdir.equals("TRAILING")) {
						field.setPendingdir(Enum_pending_dir.TRAILING);
					}
					String pendingchar = props.getProperty("pendingchar");
					if (pendingchar.equals("ZERO")) {
						field.setPendingchar('0');
					} else if (pendingchar.equals("SPACE")) {
						field.setPendingchar(' ');
					}
					String format = props.getProperty("format");
					if (format.equals("BCD")) {
						field.setFormat(Enum_format.BCD);
					} else if (format.equals("BINARY")) {
						field.setFormat(Enum_format.BINARY);
					} else if (format.equals("ASCII")) {
						field.setFormat(Enum_format.ASCII);
					} else if (format.equals("HEX")) {
						field.setFormat(Enum_format.HEX);
					} else if (format.equals("ASCIIOTHER")) {
						field.setFormat(Enum_format.ASCIIOTHER);
					}
					field.setLength(props.getProperty("length"));
					field.setProperty(props.getProperty("property"));
					config.getFields().add(field);
					return field;
				}
			});
			this.addNodeLet("/datagram-config/field/default-value",
					new INodeParser() {
						public Object parse(Node node, Object... objects) {
							// TODO Auto-generated method stub
							DatagramField field = (DatagramField) objects[0];
							NodeList itemNodeList = node.getChildNodes();
							for (int j = 0; j < itemNodeList.getLength(); j++) {
								Node item_node = itemNodeList.item(j);
								if (item_node.getNodeValue() != null) {
									field.setDefaultvalue(item_node
											.getNodeValue());
									// config.getFields().add(field);
									break;
								}
							}
							return null;
						}
					});
		}

		public DatagramConfig parse(Document doc) {
			Node node = doc.getLastChild();
			parse(node, new Path("//"), (Object[]) null);
			return this.config;
		}

		private Object parse(Node node, Path path, Object... objects) {
			// Element
			if (node instanceof Element) {
				String elementName = node.getNodeName();
				path.add(elementName);
				Object obj = this
						.processNodelet(node, path.toString(), objects);
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					if (obj instanceof DatagramField) {
						parse(children.item(i), path, obj);
					} else {
						parse(children.item(i), path, (Object[]) null);
					}
				}
				path.remove();
			}
			return null;
		}
	}
	
	public void initializeSocket(ServletContext servletContext){
		
//		//Socket服务启动
//		try {
//			new MultiThreadServer(sqlDao_i).service();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
