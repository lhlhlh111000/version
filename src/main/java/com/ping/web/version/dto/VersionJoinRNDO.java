package com.ping.web.version.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Title: RN bundle 关联版本
 *
 * @author 二师兄
 * Create Time: 2019/9/29
 */
@Getter
@Setter
@Entity
@Table(name = "version_join_rn")
public class VersionJoinRNDO implements Serializable {

    @Id
    @Column(name = "version_id", nullable = false)
    private long versionId;

    @Column(name = "rn_id", nullable = false)
    private long rnId;
}