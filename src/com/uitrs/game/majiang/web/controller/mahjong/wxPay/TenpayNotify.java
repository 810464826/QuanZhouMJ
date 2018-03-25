package com.uitrs.game.majiang.web.controller.mahjong.wxPay;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.jfinal.aop.Clear;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.controller.FrontController;
import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.GamerOnline;
import com.uitrs.game.majiang.web.model.Profit;
import com.uitrs.game.majiang.web.model.WxPayResult;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;
import com.uitrs.game.majiang.web.service.gamerOnline.GamerOnlieService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

@ControllerPath(controllerKey = "/wxpayxml")
public class TenpayNotify extends FrontController {
	private final Logger log = LoggerFactory.getLogger(TenpayNotify.class);

	@Clear({ SessionInterceptor.class })
	public void wxreturn() throws Exception {

		// 把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
		System.err.println("微信支付回调数据开始");
		log.info("微信支付回调数据开始");
		// 示例报文
		// String xml =
		// "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = getRequest().getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			getRequest().getReader().close();
		} catch (Exception e) {
		}

		Map<String, String> m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		log.info("---->" + m.get("appid").toString());

		wpr.setBankType(m.get("bank_type").toString());
		log.info("-1 ---->" + m.get("bank_type").toString());

		wpr.setCashFee(m.get("cash_fee").toString());
		log.info("-0 ---->" + m.get("bank_type").toString());

		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		log.info("0 ---->" + m.get("is_subscribe").toString());

		wpr.setMchId(m.get("mch_id").toString());
		log.info("1 ---->" + m.get("mch_id").toString());

		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		String openId = m.get("openid").toString();
		log.info("2 ---->" + m.get("openid").toString());

		// out_trade_no：系统本身订单ID
		String out_trade_no = m.get("out_trade_no").toString();
		log.info("微信支付回调" + out_trade_no);

		wpr.setOutTradeNo(out_trade_no);
		wpr.setResultCode(m.get("result_code").toString());
		log.info("微信支付回调result_code" + m.get("result_code").toString());

		wpr.setReturnCode(m.get("return_code").toString());
		log.info("微信支付回调result_code" + m.get("return_code").toString());

		wpr.setSign(m.get("sign").toString());
		log.info("微信支付回调sign" + m.get("sign").toString());

		wpr.setTimeEnd(m.get("time_end").toString());
		log.info("微信支付回调time_end" + m.get("time_end").toString());

		wpr.setTotalFee(m.get("total_fee").toString());
		log.info("微信支付回调total_fee" + m.get("total_fee").toString());

		wpr.setTradeType(m.get("trade_type").toString());
		log.info("微信支付回调trade_type" + m.get("trade_type").toString());

		// //transaction_id：微信支付系统订单ID，有微信支付系统生成
		wpr.setTransactionId(m.get("transaction_id").toString());
		// String re = "";
		if ("SUCCESS".equals(wpr.getResultCode())) {
			// 成功后返回指定页面，处理数据，已支付
			dealPayResultOk(openId);
			// 支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
					+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			log.info("------------------resXml:" + resXml);
			log.info("------------------返回OK!!!");
			// 这里支付成功了
			redirect("/wxPay/payOk");
		} else {
			// re = delPayFailure(openId);
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			log.info("------------------resXml:" + resXml);
			log.info("------------------返回failure!!!");
			// 这里支付失败 返回充值页面
			redirect("/wxPay");
		}
		/*
		 * if (re.equals("success")) { log.info("------------------返回OK!!!");
		 * log.info(re + "!"); //这里支付成功了 redirect("/wxPay/payOk"); } else {
		 * log.info("------------------返回failure!!!"); //这里支付失败 返回充值页面
		 * redirect("/wxPay"); }
		 */
	}

	/**
	 * 根据并系统订单ID处理支付成功后的业务逻辑 这里玩家支付成功过后，处理该玩家的房卡数
	 * 
	 * @param out_trade_no
	 * @throws Exception
	 */
	public String dealPayResultOk(String openId) {
		log.info("------------------充值成功了，开始处理充值的房卡数量！！！ ");
		// 根据openId查找玩家
		GamerInfo gamerInfo = GamerInfoService.getService().getGammer(openId);
		log.info("------------------gamerInfo: " + gamerInfo);
		int gamerId = gamerInfo.getId();
		log.info("------------------gamerId: " + gamerId);
		// 根据玩家id查找在线充值的记录 这里是没有充值成功的记录 该玩家最后一次充值的记录
		GamerOnline gamerOnline = GamerOnlieService.getService().getOnlinePayByGamerId(gamerId);
		log.info("------------------gamerOnline: " + gamerOnline);
		if (gamerOnline != null) {
			Double payMoney = gamerOnline.getPayMoney();
			String payType = gamerOnline.getPayType();

			CardType cardType = CardTypeService.getService().getPayCardsByType(payType).get(0);
			double cardMoney = cardType.getCardMoney();
			int cardNum = cardType.getCardNum();
			// 计算充值的数量
			int payCardsNum = (int) (cardNum / cardMoney * payMoney);
			log.info("------------------玩家以前的房卡数: " + gamerInfo.getSurplusCards());
			log.info("------------------payCardsNum: " + payCardsNum);
			boolean update = gamerInfo.set("totalCards", gamerInfo.getTotalCards() + payCardsNum)
					.set("surplusCards", gamerInfo.getSurplusCards() + payCardsNum).set("id", gamerInfo.getId())
					.update();
			log.info("------------------玩家更新后的房卡数: " + gamerInfo.getSurplusCards());
			log.info("------------------玩家房卡数量更新成功！ ");
			if (update) {
				gamerOnline.set("id", gamerOnline.getId()).set("state", 1).set("payTime", Tools.date2String(new Date()))
						.update();
				log.info("------------------修改充值记录的状态成功！ ");
				/**
				 * 这里给他的父级玩家添加盈利
				 */
				int parentId = gamerInfo.getParentId();
				log.info("------------------充值玩家的父级parentId:" + parentId);
				//这里先获取代理比例
				Double agent = AdminInfoService.getService().getAgent();
				log.info("------------------代理比例agent:" + agent);
				if(parentId!=0){
					Profit profit2 = AdminInfoService.getService().getProfit(parentId).get(0);
					log.info("------------------以前的盈利profit2:" + profit2);
					if(profit2!=null){
						//修改获利就可以了
						profit2.set("id", profit2.getId())
						        .set("profit",payMoney*agent+profit2.getProfit())
						        .update();
					}else{
						Profit profit=new Profit();
						profit.set("winnerId", parentId)
							  .set("profit",payMoney*agent)
						      .save();
						}
				}
			}
		}
		return "success";
	}

	/**
	 * 充值失败 将该条充值记录删掉 Failure
	 *//*
		 * public String delPayFailure(String openId){ //根据openId查找玩家 GamerInfo
		 * gamerInfo = GamerInfoService.getService().getGammer(openId); int
		 * gamerId = gamerInfo.getId(); //根据玩家id查找在线充值的记录 这里是没有充值成功的记录
		 * GamerOnline gamerOnline =
		 * GamerOnlieService.getService().getOnlinePayByGamerId(gamerId);
		 * GamerOnlieService.getService().deleteOnlinePayById(gamerOnline.getId(
		 * )); return "failure"; }
		 */

	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author
	 * @see
	 */
	private Map<String, String> parseXmlToList2(String xml) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			@SuppressWarnings("unchecked")
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
		}
		return retMap;
	}

}
