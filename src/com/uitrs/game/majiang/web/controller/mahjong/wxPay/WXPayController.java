package com.uitrs.game.majiang.web.controller.mahjong.wxPay;

import java.io.StringReader;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.controller.FrontController;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;
import com.uitrs.game.majiang.web.service.gamerOnline.GamerOnlieService;
import com.uitrs.web.common.controller.ControllerPath;

/**
 * 微信支付控制器
 * 
 */
@ControllerPath(controllerKey = "/wxPay")
public class WXPayController extends FrontController {
	private static final String String = null;
	private final Logger log = LoggerFactory.getLogger(WXPayController.class);
	static HashMap<String, Object> result = new HashMap<String, Object>();

	public void index() {
		String device = MType.getType().getDevice();
		List<Object> allTypes = CardTypeService.getService().getAllTypes();
		setAttr("types", allTypes);
		render("/WEB-INF/" + device + "/default/mahjong/pay/addPayInfoForGamer.html");
	}

	@SuppressWarnings("static-access")
	public void payOnline() {
		int gamerId = getParaToInt("gamerId");
		String type = getPara("type");
		String openid = GamerInfoService.getService().getOpenId(gamerId);
		log.info("------------------openid:" + openid);
//		String openid = "o5394wQF76cdHGyLJnaeEYRJo0J4";
		Map<String, String> model = new HashMap<String, String>();
		// 获取prepayid
		Map<String, String> map = new HashMap<String, String>();
		// 随机字符串
		String nonceStr = UUID.randomUUID().toString().substring(0, 32);
		// 前端页面传入的金额
		String payMoney = getPara("money");
		// Double money=(double)payMoney;
		// boolean addOnlinePay =
		// GamerOnlieService.getService().addOnlinePay(gamerId, type, money);
		// System.out.println(addOnlinePay);
		Float f1 = Float.parseFloat(payMoney);
		// 因为单位是分 所以需要乘以100.00f
		Float aa = Tools.Floatmultiply(f1, 100.00f);
		// 这就是最终需要支付的钱
		String total_fee = String.valueOf(aa);
		String[] aaa = total_fee.split("\\.");
		total_fee = aaa[0];
		// 订单号
		String out_trade_no = orderNum();
		// 公众号id
		String appid = PropKit.use("system.properties").get("appId");

		String mch_id = PropKit.use("system.properties").get("partnerId");
		String body = PropKit.use("system.properties").get("token");
		// 成功后的回调地址
		String notify_url = PropKit.use("system.properties").get("tenpayNotifyurl");
		// 密匙
		String key = PropKit.use("system.properties").get("paternerKey");
		long timestamp = System.currentTimeMillis() / 1000;
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonceStr);
		map.put("body", body);
		map.put("out_trade_no", out_trade_no);
		log.info("------------------out_trade_no:" + out_trade_no);
		map.put("total_fee", total_fee);
		map.put("spbill_create_ip", IpKit.getRealIp(getRequest()));
//		map.put("spbill_create_ip", "123.12.12.123");
		map.put("notify_url", notify_url);
		map.put("trade_type", "JSAPI");
		map.put("openid", openid);
		String paySign = null;
		try {
//			paySign = getPayCustomSign(map, key);
			paySign = sign(map, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("sign", paySign);
		String xml = ArrayToXml(map);
		System.out.println("------------------ceshi:xml:" + xml);
		log.info("------------------ceshi:xml:" + xml);
		// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String xmlStr = HttpKit.post(url, xml);
		System.out.println("------------------ceshi:xmlStr:" + xmlStr);
		/*********************** 解析String ****************************/
		StringReader read = new StringReader(xmlStr);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();
			result.put(root.getName(), root.getText());
			parse(root);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		log.info("------------------ceshi:xmlStr:" + xmlStr);
		String prepayid = "";
		String return_code = (java.lang.String) result.get("return_code");
		if (return_code.equals("SUCCESS")) {
			prepayid = (String) result.get("prepay_id");
		}
		/*
		 * // 预付商品id String prepayid = ""; if (xmlStr.indexOf("SUCCESS") != -1)
		 * { Map<String, String> map2 = null; try { map2 = doXMLParse(xmlStr); }
		 * catch (XmlPullParserException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } prepayid = (String)
		 * map2.get("prepay_id"); }
		 */
		System.out.println("prepareid*****************************=" + prepayid);
		log.info("prepareid*****************************=" + prepayid);
		// 封装h5页面调用参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("appId", appid);
		log.info("------------------appId:" + appid);

		signMap.put("timeStamp", timestamp + "");
		log.info("------------------timestamp:" + timestamp);

		signMap.put("package", "prepay_id=" + prepayid);
		log.info("------------------prepayid:" + prepayid);

		signMap.put("signType", "MD5");
		signMap.put("nonceStr", nonceStr);
		log.info("------------------nonceStr:" + nonceStr);

		model.put("paytimestamp", Long.toString(timestamp));
		model.put("paypackage", "prepay_id=" + prepayid);
		model.put("paynonceStr", nonceStr);
		model.put("paysignType", "MD5");
		model.put("appId", appid);
		String paySign2 = null;
		try {
//			paySign2 = getPayCustomSign(signMap, key);
			paySign2 = sign(signMap, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("paySign", paySign2);
		log.info("------------------paySign2:" + paySign2);
		model.put("price", String.valueOf(f1));
		model.put("gamerId", gamerId + "");
		model.put("type", type);
		log.info("------------------price:" + String.valueOf(f1));
		setAttr("datas", model);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/pay/weixinPay.html");
	}

	/**
	 * 这里保存充值的信息
	 */
	public void savePayInfo() {
		int gamerId = getParaToInt("gamerId");
		log.info("------------------gamerId:" + gamerId);
		String type = getPara("type");
		log.info("------------------type:" + type);
		String payMoney = getPara("money");
		log.info("------------------payMoney:" + payMoney);
		Double money = Double.parseDouble(payMoney);
		boolean addOnlinePay = GamerOnlieService.getService().addOnlinePay(gamerId, type, money);
		log.info("------------------addOnlinePay:" + addOnlinePay);
		renderJson("result", addOnlinePay);
	}

	public void payOk() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/ok.html");
	}

	/**
	 * 获取支付所需签名
	 * 
	 * @param ticket
	 * @param timeStamp
	 * @param card_id
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getPayCustomSign(Map<String, String> bizObj, String key) throws Exception {
		String bizString = FormatBizQueryParaMap(bizObj, false);
		return sign(bizString, key);
	}
	
	 public static String sign(Map<String, String> map,String key) { 
		    //排序 
		    String sort=sortParameters(map); 
		    //拼接API秘钥 
		    sort=sort+"&key="+key; 
		    //System.out.println(sort); 
		    //MD5加密 
//		    String sign= MD5.MD5Encode(sort).toUpperCase(); 
		    String sign= MD5(sort).toUpperCase();
		    return sign; 
		  } 
	 
	 private static String sortParameters(Map<String, String> map) { 
		    Set<String> keys = map.keySet(); 
		    List<String> paramsBuf = new ArrayList<String>(); 
		    for (String k : keys) { 
		      paramsBuf.add((k + "=" + getParamString(map, k))); 
		    } 
		    // 对参数排序 
		    Collections.sort(paramsBuf); 
		    String result=""; 
		    int count=paramsBuf.size(); 
		    for(int i=0;i<count;i++){ 
		      if(i<(count-1)){ 
		        result+=paramsBuf.get(i)+"&"; 
		      }else { 
		        result+=paramsBuf.get(i); 
		      } 
		    } 
		    return result; 
		  } 
	 
	  private static String getParamString(@SuppressWarnings("rawtypes") Map map, String key) { 
		    String buf = ""; 
		    if (map.get(key) instanceof String[]) { 
		      buf = ((String[]) map.get(key))[0]; 
		    } else { 
		      buf = (String) map.get(key); 
		    } 
		    return buf; 
	} 

	/**
	 * 字典排序
	 * 
	 * @param paraMap
	 * @param urlencode
	 * @return
	 * @throws Exception
	 */
	public static String FormatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) throws Exception {
		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				// System.out.println(item.getKey());
				if (StrKit.isBlank(item.getKey())) {
					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key + "=" + val + "&";
				}
			}
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return buff;
	}

	public static String sign(String content, String key) throws Exception {
		String signStr = "";
		signStr = content + "&key=" + key;
		return MD5(signStr).toUpperCase();
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 随机生成订单号
	 * 
	 * @return
	 */
	public String orderNum() {
		String chars = "0123456789";
		String order = System.currentTimeMillis() + "";
		String res = "";
		for (int i = 0; i < 19; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		order += res;
		return order;
	}

	/**
	 * XML转map
	 * 
	 * @param arr
	 * @return
	 */
	public static HashMap<String, Object> parse(Element root) {
		@SuppressWarnings("rawtypes")
		List nodes = root.getChildren();
		int len = nodes.size();
		if (len == 0) {
			result.put(root.getName(), root.getText());
		} else {
			for (int i = 0; i < len; i++) {
				Element element = (Element) nodes.get(i);// 循环依次得到子元素
				result.put(element.getName(), element.getText());
				parse(element);
			}
		}
		return result;
	}

	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else
				xml += "<" + key + ">" + "<![CDATA[" + val + "]]>" + "</" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

}
