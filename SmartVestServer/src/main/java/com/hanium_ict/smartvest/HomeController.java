package com.hanium_ict.smartvest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanium_ict.smartvest.controller.FieldWorkController;
import com.hanium_ict.smartvest.service.FieldWorkService;
import com.hanium_ict.smartvest.vo.FieldWorkVO;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends HttpServlet{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private SqlSession sqlSession;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//	
//	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/home.jsp");
//		rd.forward(req, resp);
//	}
//	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		
		return "home";
	}
//	private static final Logger Logger = LoggerFactory.getLogger(FieldWorkController.class);
//	
//	@Inject
//	FieldWorkService service;
//	
//	@RequestMapping(value = "/fieldwork/writeView", method = RequestMethod.GET)
//	public void writeView() throws Exception {
//		Logger.info("writeView");
//	}
//	
//	@RequestMapping(value = "/fieldwork/write", method = RequestMethod.POST)
//	public String write(FieldWorkVO fieldworkVO) throws Exception {
//		Logger.info("write");
//		
//		service.write(fieldworkVO);
//		
//		return "redirect:/";
//	}
//	@RequestMapping("/vision")
//	@ResponseBody
//	public Map<String, String> androidTestWithRequest(HttpServletRequest request){
//        System.out.println(request.getParameter("title"));
//        System.out.println(request.getParameter("memo"));
//        
//        Map<String, String> result = new HashMap<String, String>();
//        result.put("data1", "메모에요");
//        result.put("data2", "두번째 메모입니다.");
//        
//        return result;
//
//    }


}
