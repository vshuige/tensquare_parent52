package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/10
 */

@Service
@Transactional
public class LabelService {


    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;


    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return  labelDao.findById(id).get();
    }


    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {

            /**
             *
             * @param root      根对象，也就是要把条件封装在对象中 where 类名=label.getId
             * @param criteriaQuery    封装的都是查询的关键字，比如 order by  group by  等
             * @param criteriaBuilder   用来封装条件对象，如果直接返回null，则表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // new一个list集合存放所有条件
                ArrayList<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    // where labelname like '%小明%'
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())){
                    // state = '1'
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                // new 一个数组，作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                // 把list转成数组
                list.toArray(parr);
                return criteriaBuilder.and(parr);  // where labelname like '%小明%' and state = '1'
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        // 封装分页对象
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {


            /**
             *
             * @param root      根对象，也就是要把条件封装在对象中 where 类名=label.getId
             * @param criteriaQuery    封装的都是查询的关键字，比如 order by  group by  等
             * @param criteriaBuilder   用来封装条件对象，如果直接返回null，则表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // new一个list集合存放所有条件
                ArrayList<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    // where labelname like '%小明%'
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())){
                    // state = '1'
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                // new 一个数组，作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                // 把list转成数组
                list.toArray(parr);
                return criteriaBuilder.and(parr);  // where labelname like '%小明%' and state = '1'
            }
        }, pageable);
    }
}
