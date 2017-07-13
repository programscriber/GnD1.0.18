package com.indutech.gnd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.BranchBO;
import com.indutech.gnd.bo.CoreFileSummaryBO;
import com.indutech.gnd.bo.DistrictBO;
import com.indutech.gnd.bo.EnumBo;
import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;
import com.indutech.gnd.bo.MasterDcmsBO;
import com.indutech.gnd.bo.ProductBO;
import com.indutech.gnd.bo.StateBO;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.State;
import com.indutech.gnd.enumTypes.AufFormat;
import com.indutech.gnd.enumTypes.BankDetail;
import com.indutech.gnd.enumTypes.CardType;
import com.indutech.gnd.enumTypes.DCMS;
import com.indutech.gnd.enumTypes.NetworkDetail;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.enumTypes.YesNo;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.BranchService;
import com.indutech.gnd.service.MasteDBService;
import com.indutech.gnd.service.MasterAWBServiceImpl;

@RequestMapping(value = "/master")
@Controller("masterDBCont")
public class MasterDBCont {
	
	Logger logger = Logger.getLogger(MasterDBCont.class);
	
	@Autowired
	private MasteDBService MasterDBService;
	@Autowired
	private JasperReportGenerator jasperreportgenerator;
	@Autowired
	private BranchService branchService;
	@Autowired
	private MasterAWBServiceImpl masterAWBService;
	

	public MasterAWBServiceImpl getMasterAWBService() {
		return masterAWBService;
	}

	public void setMasterAWBService(MasterAWBServiceImpl masterAWBService) {
		this.masterAWBService = masterAWBService;
	}

	public BranchService getBranchService() {
		return branchService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	@RequestMapping(value = "/masterdb")
	public ModelAndView masterDB(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("master");
	}

	@RequestMapping(value = "/addBranch")
	public ModelAndView addBranch(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List resList = getMasterDBService().getdistictAndState();
		ModelAndView view = new ModelAndView("addBranch");
		HashMap< Integer, String> hashstaus=new HashMap<Integer, String>();
		for(Status sta:Status.values()){
			try{
			hashstaus.put(Integer.valueOf(sta.getStatus()),String.valueOf(sta));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		HashMap< Integer, String> yesno=new HashMap<Integer, String>();
		for(YesNo sta:YesNo.values()){			
			yesno.put(Integer.valueOf(sta.getValueYesNO()),String.valueOf(sta));
		}
		
		view.addObject("MasBank", (List<BankBO>) resList.get(0));
		view.addObject("state", (List<StateBO>) resList.get(1));
		view.addObject("status",hashstaus);
		view.addObject("yesNo",yesno);
		return view;
	}

	@RequestMapping(value = "/district.htm", produces = "application/json")
	public @ResponseBody List<DistrictBO> district(
			@RequestParam("stateId") Long stateId) {
		
		List<DistrictBO> dist = getMasterDBService().getDistrictervice(stateId);
		return dist;
	}
@RequestMapping(value="/countawb")
public @ResponseBody long countAWB(@RequestParam("serviceproviderName") String id){
	long valueCout=getMasterDBService().getCountAWB(Long.valueOf(id));
	
	return valueCout;
}
	@RequestMapping(value="/addBranchTo",method=RequestMethod.POST)
	public @ResponseBody String addBranchTo(ModelMap model, Principal principal,
			@RequestParam("branchshortcode") String branchshortcode,
			@RequestParam("bank") Long bankID,
			@RequestParam("lcpcCode") String lcpcCode,
			@RequestParam("address1") String address1,
			@RequestParam("addres3") String addres3,
			@RequestParam("state") Long stateCode,
			@RequestParam("pincode") String pincode,
			@RequestParam("branchName") String branchName,
			@RequestParam("status") Long status,
			@RequestParam("lcpcbranch") String lcpcbranch,
			@RequestParam("address2") String address2,
			@RequestParam("addres4") String addres4,
			@RequestParam("district") Long district,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("lhoName") String lhoName, /*cr no 23*/
			@RequestParam("isNonCardIssueBranch") String isNonCardIssueBranch) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return "login";
		}

		ModelAndView view=null;
		boolean result=false;
		String res=null;
		try{
			result=getMasterDBService().addBranchTo(branchshortcode,bankID,lcpcCode,address1,addres3,stateCode,pincode,branchName,status,lcpcbranch,address2,addres4,district,phone,email,lhoName,isNonCardIssueBranch);
			//System.out.println("the result"+result);
			if(result){
			res="Branch sucessfully added";
			}else{
				res="Branch already exists";
			}
		}catch(Exception e){
			e.printStackTrace();
			res="Fail to add the branch";
		}
		return res;
	}
	
	@RequestMapping(value="/editBranchToadd",method=RequestMethod.POST)
	public @ResponseBody String editBranchToadd(ModelMap model, Principal principal,
			@RequestParam("branchshortcode") String branchshortcode,
			@RequestParam("bank") Long bankID,
			@RequestParam("lcpcCode") String lcpcCode,
			@RequestParam("address1") String address1,
			@RequestParam("addres3") String addres3,
			@RequestParam("state") Long stateCode,
			@RequestParam("pincode") String pincode,
			@RequestParam("branchName") String branchName,
			@RequestParam("status") Long status,
			@RequestParam("lcpcbranch") String lcpcbranch,
			@RequestParam("address2") String address2,
			@RequestParam("addres4") String addres4,
			@RequestParam("district") Long district,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("isNonCardIssueBranch") String isNonCardIssueBranch ) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return "login";
		}

		ModelAndView view=null;
		boolean result=false;
		String res=null;
		try{
			result=getMasterDBService().editBranchTo(branchshortcode,bankID,lcpcCode,address1,addres3,stateCode,pincode,branchName,status,lcpcbranch,address2,addres4,district,phone,email,isNonCardIssueBranch);
		//	System.out.println("the result"+result);
			if(result){
			res="Branch updated sucessfully";
			}else{
				res="Failed to update branch";
			}
		}catch(Exception e){
			e.printStackTrace();
			res="Fail to add the branch";
		}
		return res;
	}

	/*@RequestMapping(value="/addBranchTo",produces="application/json",consumes="application/json")
	public @ResponseBody BranchBO addBranchTo(@RequestBody BranchBO branchBO){
		System.out.println(branchBO.getDistrictCode());
		return branchBO;
	}*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public ModelAndView addproductCont(ModelMap model, Principal principal,
			@RequestParam("productCode") String productCode,
			@RequestParam("productName") String productName,
			@RequestParam("bank") String bank, @RequestParam("bin") String bin,
			@RequestParam("cardType") String cardType,
			@RequestParam("network") String network,
			@RequestParam("OperationGnD") String OperationGnD,
			@RequestParam("dcms") String dcms,
			@RequestParam("photoCard") String photoCard,
			@RequestParam("fourthApplicable") String fourthApplicable) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}

		ModelAndView sendObj = new ModelAndView("addproducts");
		Map paramMap = new HashMap();
		paramMap.put("productCode", productCode);
		paramMap.put("productName", productName);
		paramMap.put("bank", bank);
		paramMap.put("bin", bin);
		paramMap.put("cardType", cardType);
		paramMap.put("OperationGnD", OperationGnD);
		paramMap.put("dcms", dcms);
		paramMap.put("network", network);
		paramMap.put("fourthApplicable", fourthApplicable);
		paramMap.put("photoCard", photoCard);
		List res = getMasterDBService().addproductSer(paramMap);
		if(res.size() > 0) {
			sendObj.addObject("result", res.get(0));
			sendObj.addObject("masType", res.get(1));
			sendObj.addObject("critMasBank", res.get(2));
			sendObj.addObject("masDcms", res.get(3));
			sendObj.addObject("network", res.get(4));
		}	
		return sendObj;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/prerequesttoadd")
	public ModelAndView getdetailsToaddproductCont(ModelMap model,
			Principal principal) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List resList = getMasterDBService().getdetailsToaddproductServ();
		ModelAndView sendObj = new ModelAndView("addproducts");
		sendObj.addObject("masType", resList.get(0));
		sendObj.addObject("critMasBank", resList.get(1));
		sendObj.addObject("masDcms", resList.get(2));
		sendObj.addObject("network", resList.get(3));

		return sendObj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/productSearch", method = RequestMethod.POST)
	public ModelAndView productSearchCont(ModelMap model, Principal principal,
			@RequestParam("bank") String bank,
			@RequestParam("cardType") String cardType,
			@RequestParam("dcms") String dcms,
			@RequestParam("productName") String productName) {
		String username = null;
		String productName1 = "%"+productName+"%";
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		Map produSearch = new HashMap();
		produSearch.put("bank", bank);
		produSearch.put("cardType", cardType);
		produSearch.put("dcms", dcms);
		produSearch.put("productName", productName1);
		List result = getMasterDBService().productSearchServ(produSearch);
		ModelAndView view = new ModelAndView("products");
		List productResult=getMasterAWBService().getproductServ();
		view.addObject("masType", productResult.get(0));
		view.addObject("critMasBank", productResult.get(1));
		view.addObject("masDcms", productResult.get(2));
		view.addObject("Product", result);

		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/branchSearch", method = RequestMethod.GET)
	public ModelAndView branchSearchCont(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
			} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List returnResult = getMasterDBService().branchSearch();
		ModelAndView returnRes = new ModelAndView("branch");
//		logger.info(returnResult.size()+"    ///////////////////");
		returnRes.addObject("bankSear", returnResult);
		return returnRes;
	}
	
	@RequestMapping("/bank")
	public  ModelAndView bank(ModelMap model, Principal principal){
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView view=new  ModelAndView("bank");
		view.addObject("bankdetails", getMasterDBService().getBank());
		
		return view;
	}

	@RequestMapping("/getDetailsbank")
	public @ResponseBody  List<BankBO> banksearch(ModelMap model, Principal principal,@RequestParam("bank") String bank){
		/*String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}*/
		List<BankBO>  result=getMasterDBService().getBanklistid(bank);
		
		return result;
	}
	@RequestMapping("/addbank")
	public @ResponseBody ModelAndView getBank(ModelMap model, Principal principal){
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		HashMap< Integer, String> hashstaus=new HashMap<Integer, String>();
		for(Status sta:Status.values()){
			try{
			if((Integer.valueOf(sta.getStatus())==1)||(Integer.valueOf(sta.getStatus())==2)){
			hashstaus.put(Integer.valueOf(sta.getStatus()),String.valueOf(sta));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		HashMap< Integer, String> yesno=new HashMap<Integer, String>();
		for(Status sta:Status.values()){
			try{
			if((Integer.valueOf(sta.getStatus())==0)){
			yesno.put(Integer.valueOf(sta.getStatus()),"YES");
			}
			if((Integer.valueOf(sta.getStatus())==1)){
				yesno.put(Integer.valueOf(sta.getStatus()),"No");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		HashMap< Integer, String> aufformat=new HashMap<Integer, String>();
		for(AufFormat sta:AufFormat.values()){
			try{
				aufformat.put(Integer.valueOf(sta.getFileStatus()),String.valueOf(sta));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		ModelAndView view=new ModelAndView("addBank");
		view.addObject("status", hashstaus);
		view.addObject("yesNo", yesno);
		view.addObject("aufFormat", aufformat);
		return view;
	}

	@RequestMapping(value="/addBankTo",method=RequestMethod.POST)
	public @ResponseBody boolean addBankTo(@RequestParam("bank") String bank,
			@RequestParam("bankName") String bankName,
			@RequestParam("shortcode") String shortcode,
			@RequestParam("status") String status,
			@RequestParam("bankcode") String bankcode,
			@RequestParam("prefix") String prefix,
			@RequestParam("lpawb") String lpawb,
			@RequestParam("aufformat") String aufformat ){
		boolean result=getMasterDBService().getAddBank(bank,bankName,shortcode,status,bankcode,prefix,lpawb,aufformat);
		return result;	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/searchbranch", method = RequestMethod.POST)
	public ModelAndView searchbranchCont(ModelMap model, Principal principal,
			@RequestParam(value = "bankCode") String bankCode,
			@RequestParam(value = "branchName") String branchName,
			@RequestParam(value = "branchCode") String branchCode,
			@RequestParam(value = "ifscCode") String ifscCode) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		Map branchMap = new HashMap();
		branchMap.put("bankCode", bankCode);
		branchMap.put("branchName", branchName);
		branchMap.put("branchCode", branchCode);
		branchMap.put("ifscCode", ifscCode);
		List branchbo = getMasterDBService().searchbranchSer(branchMap);
		String result = null;
		ModelAndView resturnBranch = new ModelAndView("branch");
		resturnBranch.addObject("searchbranch", branchbo.get(0));
		resturnBranch.addObject("bankSear", branchbo.get(1));
//		resturnBranch.addObject("searchbranch", branchbo.get(2));
		return resturnBranch;
	}
	@RequestMapping("/editBranchTo")
	public @ResponseBody List editBranch(@RequestParam("bank") String bank,@RequestParam("branchshortcode") String branchshortcode,@RequestParam("district") String district,@RequestParam("state") String state,@RequestParam("status") String status){
		BranchBO branchBoo=getMasterDBService().getBranch(branchshortcode,bank);
		List resList = getMasterDBService().getdistictAndState(branchBoo.getDistrictCode(),branchBoo.getStateCode(),branchBoo.getStatus());
		List enumarray=new ArrayList();
		List yesNolis=new ArrayList();
		for(YesNo yesNo:YesNo.values()){
			if((int)Integer.valueOf(yesNo.getValueYesNO())==(branchBoo.getIsNonCardIssueBranch()!=null?(int)branchBoo.getIsNonCardIssueBranch():-1)){
				EnumBo enumb=new EnumBo();
				enumb.setKey(yesNo.getValueYesNO());
				enumb.setValue(String.valueOf(yesNo));
				yesNolis.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(yesNo.getValueYesNO());
			en.setValue(String.valueOf(yesNo));
			yesNolis.add(en);
		}

		for(Status sta:Status.values()){
			try{
			if((long)Long.valueOf(sta.getStatus())==(long)branchBoo.getStatus()){
				EnumBo enumb=new EnumBo();
				enumb.setKey(sta.getStatus());
				enumb.setValue(String.valueOf(sta));
				enumarray.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(sta.getStatus());
			en.setValue(String.valueOf(sta));
			enumarray.add(en);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		resList.add(enumarray);
		resList.add(branchBoo);
		resList.add(yesNolis);
		return resList;
	}
	@RequestMapping("/editProductTo")
	public @ResponseBody List editProductTo(@RequestParam("productId") Long productId,@RequestParam("shortCode") String shortCode,
			@RequestParam("productName") String productName,
			@RequestParam("bin") String bin,
			@RequestParam("status") String status,
			@RequestParam("fourthStatus") String fourthStatus,@RequestParam("photoCard") String photoCard){
		System.out.println("Inside editProductTo ()");
		System.out.println("1"+shortCode);
		System.out.println("1"+productName);
		String username = null;
		Long status1 = Long.valueOf(0);
		Long fourthStatus1 = Long.valueOf(0);
		Long photoCard1 = Long.valueOf(0);
		if(status.equalsIgnoreCase("ACTIVE"))
		{
			status1 = Long.valueOf(1);
		}else if(status.equalsIgnoreCase("INACTIVE")){
			status1 = Long.valueOf(2);
		}else if(status.equalsIgnoreCase("BLOCKED")){
			status1 = Long.valueOf(3);
		}else if(status.equalsIgnoreCase("PENDING FOR APPROVAL")){
			status1 = Long.valueOf(0);
		}
		if(fourthStatus.equalsIgnoreCase("yes"))
		{
			fourthStatus1 = Long.valueOf(1);
		}
		if(photoCard.equalsIgnoreCase("yes"))
		{
			photoCard1 = Long.valueOf(1); 
		}
		/*ProductBO product = getMasterDBService().getProduct(productId,shortCode,productName,bin,status1,fourthStatus1,photoCard1);*/
		ProductBO product = getMasterDBService().getProduct(productId);
		MasterDcmsBO dcms = getMasterDBService().getDcms(product.getDcmsId());
		System.out.println(product.getBin());
		List statusList=new ArrayList();
		List yesNolis=new ArrayList();
		List yesNoList = new ArrayList();
		List resList = new ArrayList();
		List cardTypeList = new ArrayList();
		List DCMSList = new ArrayList();
		List bankList = new ArrayList();
		List networkList = new ArrayList();
		for(YesNo yesNo:YesNo.values()){
			if((int)Integer.valueOf(yesNo.getValueYesNO())==(product.getPhotoCard()!=null?product.getPhotoCard():-1)){
				EnumBo enumb=new EnumBo();
				enumb.setKey(yesNo.getValueYesNO());
				enumb.setValue(String.valueOf(yesNo));
				yesNolis.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(yesNo.getValueYesNO());
			en.setValue(String.valueOf(yesNo));
			yesNolis.add(en);
		}
		
		for(YesNo yesNo:YesNo.values()){
			if((int)Integer.valueOf(yesNo.getValueYesNO())==(product.getForthLineRequired()!=null?product.getForthLineRequired():-1)){
				EnumBo enumb=new EnumBo();
				enumb.setKey(yesNo.getValueYesNO());
				enumb.setValue(String.valueOf(yesNo));
				yesNoList.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(yesNo.getValueYesNO());
			en.setValue(String.valueOf(yesNo));
			yesNoList.add(en);
		}


		for(Status sta:Status.values()){
			try{
			if((long)Long.valueOf(sta.getStatus())==(long)product.getStatus()){
				EnumBo enumb=new EnumBo();
				enumb.setKey(sta.getStatus());
				enumb.setValue(String.valueOf(sta));
				statusList.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(sta.getStatus());
			en.setValue(String.valueOf(sta));
			statusList.add(en);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(CardType cardType:CardType.values()){
			try{
			if((long)Long.valueOf(cardType.getCardType())==(long)product.getTypeId()){
				EnumBo enumb=new EnumBo();
				enumb.setKey(cardType.getCardType());
				enumb.setValue(String.valueOf(cardType));
				cardTypeList.add(0, enumb);
				continue;
			}
			EnumBo en=new EnumBo();
			en.setKey(cardType.getCardType());
			en.setValue(String.valueOf(cardType));
			cardTypeList.add(en);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		for(DCMS dcmsValue:DCMS.values()){
			try{
				if((long)Long.valueOf(dcmsValue.getDCMS())==(long)product.getDcmsId()){
					EnumBo enumb=new EnumBo();
					enumb.setKey(dcmsValue.getDCMS());
					enumb.setValue(String.valueOf(dcmsValue));
					DCMSList.add(0, enumb);
					continue;
				}
				EnumBo en=new EnumBo();
				en.setKey(dcmsValue.getDCMS());
				en.setValue(String.valueOf(dcmsValue));
				DCMSList.add(en);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		for(BankDetail bankValue:BankDetail.values()){
			try{
				if((long)Long.valueOf(bankValue.getBank())==(long)product.getBankId()){
					EnumBo enumb=new EnumBo();
					enumb.setKey(bankValue.getBank());
					enumb.setValue(String.valueOf(bankValue));
					bankList.add(0, enumb);
					continue;
				}
				EnumBo en=new EnumBo();
				en.setKey(bankValue.getBank());
				en.setValue(String.valueOf(bankValue));
				bankList.add(en);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		for(NetworkDetail networkValue:NetworkDetail.values()){
			try{
				if((long)Long.valueOf(networkValue.getNetwork())==(long)product.getNetworkId()){
					EnumBo enumb=new EnumBo();
					enumb.setKey(networkValue.getNetwork());
					enumb.setValue(String.valueOf(networkValue));
					networkList.add(0, enumb);
					continue;
				}
				EnumBo en=new EnumBo();
				en.setKey(networkValue.getNetwork());
				en.setValue(String.valueOf(networkValue));
				networkList.add(en);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		resList.add(product);
		resList.add(statusList);
		resList.add(yesNolis);
		resList.add(yesNoList);
		resList.add(cardTypeList);
		resList.add(DCMSList);
		resList.add(bankList);
		resList.add(networkList);

		System.out.println("editProductTo returns to jsp");
		return resList;
	}
	@RequestMapping(value="/addstate")
	public @ResponseBody boolean addStateNameModel(@RequestParam("stateNamemodel") String stateNamemodel){
		//System.out.println(stateNamemodel);
		boolean result=getMasterDBService().addstateser(stateNamemodel);
		return result;
	}
	@RequestMapping(value="/getstatelist")
	public @ResponseBody List<StateBO> getState(){
		List<StateBO> staetlit=getMasterDBService().getState();
		return staetlit;
	}
	@RequestMapping("/adddistrict")
	public @ResponseBody boolean addddistrict(@RequestParam("stateDistrict") String stateDistrict,@RequestParam("districtVal") String districtval){
		boolean result=getMasterDBService().addDistrict(stateDistrict,districtval);
		
		return result;
	}
	@RequestMapping(value="/getServiceproviders",method=RequestMethod.GET)
	public ModelAndView getServiceProvider(ModelMap model, Principal principal){
		
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List<MasterCourierServiceBO> masterCourierService=getMasterDBService().getServiceProvider();
		ModelAndView view=new ModelAndView("exportAWB");
		
		view.addObject("serviceprovider",masterCourierService);
		//view.addObject("serviceproviderCount",masterCourierService.get(1));
		
		return view;
	}
	@RequestMapping("/getlistAWB")
	public @ResponseBody List<MasterAWBBO> getListAWB(ModelMap model, Principal principal,@RequestParam("serviceproviderName") String serviceProviderId,@RequestParam("maxcount") String count,@RequestParam("blockawb") String blockawb){
		List<MasterAWBBO> masterbo=	getMasterDBService().getListAvailAWB(Long.valueOf(serviceProviderId),Integer.valueOf(count),Boolean.valueOf(blockawb));
		return masterbo;
		
	}
	@RequestMapping(value="/savetofile",method=RequestMethod.POST,consumes="application/json")
	public @ResponseBody File savefile(ModelMap model, Principal principal,@RequestBody List<MasterAWBBO> masterbo){
		//System.out.println(masterbo);
		String fullPath =getJasperreportgenerator().getCsv(masterbo);
		File file = new File(fullPath);
		return file;
	}
	@RequestMapping(value = "/awbReport")
	public @ResponseBody void rsnReport(@RequestParam("filepath") String fileloc,HttpServletResponse response){
		File file = new File(fileloc);
		InputStream is;
		try {
			is = new FileInputStream(file);
			// MIME type of the file
			response.setContentType("application/octet-stream");
			// Response header
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ file.getName() + "\"");
			// Read from the file and write into the response
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/email")
	public @ResponseBody ModelAndView email(ModelMap model, Principal principal){
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		 Map<String,String> filenameAndEmail=getMasterDBService().getEmailFiles();
		ModelAndView view= new ModelAndView("emailhelpdesk");
		view.addObject("fileNameAndEmail",filenameAndEmail);
		return view;
	}

	@RequestMapping(value="/sendemail")
	public @ResponseBody boolean sendmail(@RequestParam("unselectemail") String unselectemail){
//		System.out.println("unselected email"+unselectemail);
		getMasterDBService().getSendEmail(unselectemail);
		return false;
	}
	
	
	
	
	public MasteDBService getMasterDBService() {
		return MasterDBService;
	}

	public void setMasterDBService(MasteDBService masterDBService) {
		MasterDBService = masterDBService;
	}
	public JasperReportGenerator getJasperreportgenerator() {
		return jasperreportgenerator;
	}

	public void setJasperreportgenerator(JasperReportGenerator jasperreportgenerator) {
		this.jasperreportgenerator = jasperreportgenerator;
	}
	@RequestMapping(value = "/getBranchDetails", method = RequestMethod.POST)
	public @ResponseBody List<Branch> getBranchDetails(
			@RequestParam(value = "bankCode", required = false) Long bankCode){
//		logger.info(bankCode+"      ::::::::::::");
		List<Branch> listBranch = (List<Branch>) getBranchService().getBankBranchDetails(bankCode);
//		logger.info(listBranch.size());
		return  listBranch;
	}
	@RequestMapping(value = "/getBranchNameDetails", method = RequestMethod.POST)
	public @ResponseBody List<Branch> getBranchNameDetails(
			@RequestParam(value = "branchName", required = false) Long branchNameCode){
//		logger.info(branchNameCode+"      &&&&&&&&&&&&&&&&&&");
		List<Branch> listBranchNames = (List<Branch>)getBranchService().branchNameDetails(branchNameCode);
      
		return  listBranchNames;
	}
	@RequestMapping(value="/editProductToAdd",method=RequestMethod.POST)
	public @ResponseBody String editProductToAdd(ModelMap model, Principal principal,
			@RequestParam("productId") Long productId,
			@RequestParam("shortCode") String shortCode,
			@RequestParam("bin") String bin,
			@RequestParam("productName") String productName,
			@RequestParam("status") Long status ,
			@RequestParam("cardType") Long typeId,
			@RequestParam("dcmsId") Long dcmsId,
			@RequestParam("photoCard") Long photoCard,
			@RequestParam("fourthLineStatus") Long fourthLineStatus,
			@RequestParam("bankId") Long bankId,
			@RequestParam("networkId") Long networkId
			) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return "login";
		}

		ModelAndView view=null;
		boolean result=false;
		String res=null;
		try{
			result=getMasterDBService().editProductTo(productId,shortCode,bin,productName,status,typeId,dcmsId,photoCard,fourthLineStatus,bankId,networkId);
		//	System.out.println("the result"+result);
			if(result){
			res="Product updated sucessfully";
			}else{
				res="Failed to update Product";
			}
		}catch(Exception e){
			e.printStackTrace();
			res="Fail to add the Product";
		}
		return res;
	}
	
	@RequestMapping(value="/deleteProduct",method=RequestMethod.POST)
	public @ResponseBody String editProductToAdd(ModelMap model, Principal principal,
			@RequestParam("productId") Long productId) {
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return "login";
		}

		ModelAndView view=null;
		boolean result=false;
		String res=null;
		try{
			result=getMasterDBService().deleteProduct(productId);
		//	System.out.println("the result"+result);
			if(result){
			res="Product deleted sucessfully";
			}else{
				res="Failed to delete Product";
			}
		}catch(Exception e){
			e.printStackTrace();
			res="Fail to delete the Product";
		}
		return res;
	}

	@RequestMapping(value="/deleteBranch",method=RequestMethod.POST)
	public @ResponseBody String deleteBranch(ModelMap model, Principal principal,
			@RequestParam("branchId") String branchId
			) {
		String username = null;
		Long id = Long.parseLong(branchId);
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return "login";
		}

		ModelAndView view=null;
		boolean result=false;
		String res=null;
		try{
			result=getMasterDBService().deleteBranch(id);
		//	System.out.println("the result"+result);
			if(result){
			res="Branch deleted sucessfully";
			}else{
				res="Failed to delete Branch";
			}
		}catch(Exception e){
			e.printStackTrace();
			res="Fail to delete the branch";
		}
		return res;
	}
	
	
	
	
}