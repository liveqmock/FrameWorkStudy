package com.ps.custom.controller.system;

import com.ps.custom.entity.main.Dictionary;
import com.ps.custom.log.Log;
import com.ps.custom.log.LogMessageObject;
import com.ps.custom.log.impl.LogUtils;
import com.ps.custom.service.DictionaryService;
import com.ps.custom.util.dwz.AjaxObject;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.persistence.DynamicSpecifications;
import com.ps.custom.util.persistence.SearchFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package com.ps.custom.controller.system
 * @Description
 * @Date 14-3-12
 * @USER saxisuer
 */
@Controller
@RequestMapping(value = "/management/system/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    private static final String CREATE = "management/system/dictionary/create";
    private static final String UPDATE = "management/system/dictionary/update";
    private static final String LIST = "management/system/dictionary/list";

    @RequiresPermissions("Dictionary:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了id={0}数据字典。")
    @RequiresPermissions("Dictionary:save")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@Valid Dictionary dictionary) {
        dictionaryService.saveOrUpdate(dictionary);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{dictionary.getId()}));
        return AjaxObject.newOk("数据字典添加成功！").toString();
    }

    @ModelAttribute(value = "preloadDictionary")
    public Dictionary preload(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            Dictionary dictionary = dictionaryService.get(id);
            return dictionary;
        }
        return null;
    }

    @RequiresPermissions("Dictionary:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preupdate(@PathVariable Long id, Map<String, Object> map) {
        Dictionary dictionary = dictionaryService.get(id);
        map.put("dictionary", dictionary);
        return UPDATE;
    }

    @Log(message = "修改了id={0}数据字典的信息。")
    @RequiresPermissions("Dictionary:edit")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadDictionary") Dictionary dictionary) {
        dictionaryService.saveOrUpdate(dictionary);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{dictionary.getId()}));
        return AjaxObject.newOk("数据字典修改成功！").toString();
    }

    @Log(message = "批量删除了id={0}数据字典。")
    @RequiresPermissions("Dictionary:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteMany(Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            Dictionary dictionary = dictionaryService.get(ids[i]);
            dictionaryService.delete(dictionary.getId());
        }
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
        return AjaxObject.newOk("数据字典删除成功！").setCallbackType("").toString();
    }

    @RequiresPermissions("Dictionary:view")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Long id, ServletRequest request, Page page, Map<String, Object> map) {
        Specification<Dictionary> specification = null;
        if (id != null) {
            specification = DynamicSpecifications.bySearchFilter(request, Dictionary.class, new SearchFilter("parentId", SearchFilter.Operator.EQ, id));
            map.put("dictionaryType", Dictionary.DictionaryType.ITEM.name());
            map.put("pDictionary", dictionaryService.get(id));
        } else {
            specification = DynamicSpecifications.bySearchFilter(request, Dictionary.class, new SearchFilter("type", SearchFilter.Operator.EQ, Dictionary.DictionaryType.THEME.name()));
            map.put("dictionaryType", Dictionary.DictionaryType.THEME.name());
        }
        List<Dictionary> dictionarys = dictionaryService.findByExample(specification, page);
        map.put("page", page);
        map.put("dictionarys", dictionarys);
        return LIST;
    }
}
