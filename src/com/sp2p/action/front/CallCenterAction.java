package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fp2p.helper.infusion.SqlInfusionHelper;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HelpAndServicerService;

public class CallCenterAction extends BaseFrontAction {
	
	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;	
	private HelpAndServicerService callCenterService;
	
	private Map<String,String> helpAnswer;
	private String question;
	
	

	public HelpAndServicerService getCallCenterService() {
		return callCenterService;
	}

	public void setCallCenterService(HelpAndServicerService callCenterService) {
		this.callCenterService = callCenterService;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setHelpAnswer(Map<String, String> helpAnswer) {
		this.helpAnswer = helpAnswer;
	}

	public String callCenterInit() throws Exception{
		String pageNum =SqlInfusionHelper.filteSqlInfusion(request().getParameter("curPage"));
		String cid =SqlInfusionHelper.filteSqlInfusion(request().getParameter("cid")) == null?"1":request().getParameter("cid");
		request().setAttribute("cid", cid);
		
		long typeId = Convert.strToLong(cid, -1);
		try{	
			//查询左边菜单
			pageBean = new PageBean();
			callCenterService.queryHelpTypes(pageBean);
			List<Map<String, Object>> types = pageBean.getPage();
			
			Map<String,String> values = null;
			String curDes = null;
			if(request().getParameter("type")!= null){
				values = callCenterService.getDescribeById(typeId);
				curDes = values.get("helpDescribe");//获得帮助类型描述
			}
			
			//右边问题		
			pageBean = new PageBean();
			pageBean.setPageSize(5);
			if(StringUtils.isNotBlank(pageNum)){
				pageBean.setPageNum(pageNum);
			}
			
			callCenterService.queryHelpQuestions(pageBean,typeId);
			List<Map<String, Object>> questions = pageBean.getPage();
			if(pageNum!=null&&pageNum!=""){
				if(Integer.parseInt(pageNum)>pageBean.getTotalPageNum()){
					pageBean.setPageNum(pageBean.getTotalPageNum());
					callCenterService.queryHelpQuestions(pageBean,typeId);
					questions = pageBean.getPage();
				}
			}
			List<Map<String, Object>> lists;//客服列表
			lists = callCenterService.queryKefuLimit4();
			request().setAttribute("types", types);
			request().setAttribute("curDes", curDes);
			request().setAttribute("typeId", typeId);
			request().setAttribute("lists", lists);
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示客服列表
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public String callKfsInit() throws Exception{
		try{
			List<Map<String,Object>> lists;
			//查询左边菜单
			pageBean = new PageBean();
			callCenterService.queryHelpTypes(pageBean);
			List<Map<String, Object>> types = pageBean.getPage();
			lists = callCenterService.queryKefuList();
			request().setAttribute("types", types);
			request().setAttribute("lists", lists);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String queryCallCenterInit() throws Exception{
		String pageNum = SqlInfusionHelper.filteSqlInfusion(request().getParameter("curPage"));
		long typeId = Convert.strToLong(SqlInfusionHelper.filteSqlInfusion(request().getParameter("cid")), -1);
		//前台显示列表类型
		if(StringUtils.isNotBlank(pageNum)){
			pageBean.setPageNum(pageNum);
		}
		
		pageBean.setPageSize(4);
		try{
			callCenterService.queryAllAnswerLists(IConstants.SORT_TYPE_DESC, pageBean,typeId);
			request().setAttribute("cid", typeId);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String queryCallCenterAnswer() throws Exception{
		String id =SqlInfusionHelper.filteSqlInfusion(request("id"));
		
		//添加浏览量
		try{
			helpAnswer = callCenterService.queryAnswer(Convert.strToLong(id, -1));
			callCenterService.updateQuestionBrowse(Convert.strToLong(id, -1));
		}catch(SQLException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		getOut().print(helpAnswer.get("anwer"));//获得对应问题的回答值
		return null;
	}
}
