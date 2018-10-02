package com.dw.customermgr.web;

import com.dw.common.pageutil.Page;
import com.dw.common.util.format.DateFormatHelper;
import com.dw.common.util.json.JsonHelper;
import com.dw.customermgr.service.ICustomerService;
import com.dw.customermgr.vo.CustomerModel;
import com.dw.customermgr.vo.CustomerQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService = null;

    @RequestMapping(value = "toadd", method = RequestMethod.GET)
    public String toadd(){
        return "customer/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute("cm") CustomerModel cm){
        cm.setRegisterTime(DateFormatHelper.long2str(System.currentTimeMillis()));
        customerService.create(cm);
        return "customer/success";
    }

    @RequestMapping(value = "toUpdate/{customerUuid}", method = RequestMethod.GET)
    public String toUpdate(Model model, @PathVariable("customerUuid") int customerUuid){
        CustomerModel cm = customerService.getByUuid(customerUuid);
        model.addAttribute("cm", cm);
        return "customer/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("cm") CustomerModel cm){
        customerService.update(cm);
        return "customer/success";
    }

    @RequestMapping(value = "toDelete/{customerUuid}", method = RequestMethod.GET)
    public String toDelete(Model model, @PathVariable("customerUuid") int customerUuid){
        CustomerModel cm = customerService.getByUuid(customerUuid);
        model.addAttribute("cm", cm);
        return "customer/delete";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam("uuid") int customerUuid){
        customerService.delete(customerUuid);
        return "customer/success";
    }

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList(@ModelAttribute("wm")CustomerWebModel wm, Model model){
        CustomerQueryModel cqm = null;
        if(wm.getQueryJsonStr() == null || wm.getQueryJsonStr().trim().length() == 0){
            cqm = new CustomerQueryModel();
        }else{
            cqm = (CustomerQueryModel) JsonHelper.String2obj(wm.getQueryJsonStr(), CustomerQueryModel.class);
        }

        cqm.getPage().setNowPage(wm.getNowPage());
        if(wm.getPageShow() > 0){
            cqm.getPage().setPageShow(wm.getPageShow());
        }

        Page dbpage = customerService.getByConditionPage(cqm);

        model.addAttribute("wm", wm);
        model.addAttribute("page", dbpage);
        return "customer/list";
    }

    @RequestMapping(value = "toQuery", method = RequestMethod.GET)
    public String toQuery(){
        return "customer/query";
    }
}
