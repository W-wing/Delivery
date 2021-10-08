package com.kkb.dao;

import com.kkb.bean.Express;
import com.kkb.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {
    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递(总数+新增)
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    List<Map<String, Integer>> console();

    /**
     * 用于查询用户未取件数
     * @return
     */
    List<Map<String, Integer>> lazyConsole(String userphone);

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 根据快递单号查询快递信息
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    Express findByNumber(String number);

    /**
     * 根据取件码查询快递信息
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    Express findByCode(String code);

    /**
     * 根据用户手机号码，查询他的所有快递信息
     * @param userphone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findByUserPhone(String userphone);

    /**
     * 根据用户手机号码跟快递的状态码进行查询
     * @param userphone
     * @param status
     * @return
     */
    List<Express> findByUserPhoneAndStatus(String userphone,int status);
    /**
     * 根据录入人手机号码，查询录入的所有记录
     * @param sysphone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findBySysPhone(String sysphone);

    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * 快递的修改
     * @param id 要修改的快递的id
     * @param newExpress 新的快递对象（number,company,username,userphone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(int id,Express newExpress);

    /**
     * 更改快递的状态为1，表示取件完成
     * @param code 要修改的快递单号
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean updateStatus(String code);

    /**
     * 根据id，删除单个快递信息
     * @param id 要删除快递的id
     * @return 删除的结果，true表示成功，false表示失败
     */
    boolean delete(int id);
}
