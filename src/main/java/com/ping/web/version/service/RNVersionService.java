package com.ping.web.version.service;

import com.ping.web.version.dao.RNVersionDAO;
import com.ping.web.version.dto.RNVersionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RNVersionService {

    @Autowired
    private RNVersionDAO rnVersionDAO;

    /**
     * 查询所有rn版本信息
     * @return rn版本信息列表
     */
    public List<RNVersionDO> queryAllRNVersion() {
        return rnVersionDAO.findAll();
    }

    /**
     * 根据版本id查询RN版本列表
     * @param versionId 版本id
     * @return RN版本列表
     */
    public List<RNVersionDO> queryRNVersionListByVersionId(Long versionId) {
        return rnVersionDAO.findByVersionIdOrderByVersionDesc(versionId);
    }

    /**
     * 根据id删除RN版本
     * @param id RN版本id
     */
    public void deleteRNVersion(Long id) {
        rnVersionDAO.deleteById(id);
    }

    /**
     * 创建rn版本
     * @param rnVersionDO rn 版本信息
     * @return 创建结果
     */
    public RNVersionDO createRNVersion(RNVersionDO rnVersionDO) {
        return rnVersionDAO.saveAndFlush(rnVersionDO);
    }
}