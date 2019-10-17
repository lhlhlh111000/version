package com.ping.web.version.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "app_join_version")
public class AppJoinVersionDO implements Serializable {

    @Id
    @Column(name = "app_id", nullable = false)
    private long appId;

    @Column(name = "version_id", nullable = false)
    private long versionId;
}