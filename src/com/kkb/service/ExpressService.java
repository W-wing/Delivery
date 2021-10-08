package com.kkb.service;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.dao.impl.ExpressDaoMysql;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.util.RandomUtil;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();
    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递(总数+新增)
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */

    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询用户未取件数
     *
     * @param userphone
     * @return
     */
    public static List<Map<String, Integer>> lazyConsole(String userphone) {
        return dao.lazyConsole(userphone);
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据快递单号查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据取件码查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */

    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码，查询他的所有快递信息
     *
     * @param userphone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findByUserPhone(String userphone) {
        return dao.findByUserPhone(userphone);
    }

    /**
     * 根据用户手机号码跟快递的状态码进行查询
     *
     * @param userphone
     * @param status
     * @return
     */
    public static List<Express> findByUserPhoneAndStatus(String userphone, int status) {
        return dao.findByUserPhoneAndStatus(userphone,status);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysphone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findBySysPhone(String sysphone) {
        return dao.findBySysPhone(sysphone);
    }

    /**
     * 快递的录入
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */

    public static boolean insert(Express e) {
        e.setCode(RandomUtil.getCode()+"");
        try {
            boolean flag = dao.insert(e);
            if (flag){
                System.out.println(e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(e);
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递的id
     * @param newExpress 新的快递对象（number,company,username,userphone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserphone() != null){
            dao.delete(id);
            return insert(newExpress);
        }else {
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if (newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code@return 修改的结果，true表示成功，false表示失败
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除快递的id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
