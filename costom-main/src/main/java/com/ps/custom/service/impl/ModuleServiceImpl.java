package com.ps.custom.service.impl;

import com.ps.custom.dao.ModuleDao;
import com.ps.custom.entity.main.Module;
import com.ps.custom.service.ModuleService;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module get(Long id) {
        return moduleDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(Module module) {
        moduleDao.save(module);
    }

    @Override
    public void delete(Long id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findAll(Page page) {
        org.springframework.data.domain.Page<Module> springDataPage = moduleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Module> findByExample(Specification<Module> specification, Page page) {
        org.springframework.data.domain.Page<Module> springDataPage = moduleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    /**
     * 判断是否是根模块.
     */
    private boolean isRoot(Long id) {
        return id == 1;
    }

    @Override
    public Module getTree() {
        List<Module> list = moduleDao.findAllWithCache();
        List<Module> rootList = makeTree(list);
        return rootList.get(0);
    }

    private List<Module> makeTree(List<Module> list) {
        List<Module> parent = new ArrayList<Module>();
        // find parent node
        for (Module m : list) {
            if (m.getParent() == null) {
                m.setChildren(new ArrayList<Module>());
                parent.add(m);
            }
        }
        // remove parent node then the object in list is all children
        list.removeAll(parent);

        makeChildren(parent, list);
        return  parent;
    }

    private void makeChildren(List<Module> parent, List<Module> children) {
        if(children.isEmpty()) {
            return;
        }
        List<Module> temp = new ArrayList<Module>();
        for (Module m1 : parent) {
            for (Module m2 : children) {
                m2.setChildren(new ArrayList<Module>());
                if (m1.getId().equals(m2.getParent().getId())) {
                    m1.getChildren().add(m2);
                    temp.add(m2);
                }
            }
        }
        children.removeAll(temp);
        makeChildren(temp, children);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }
}
