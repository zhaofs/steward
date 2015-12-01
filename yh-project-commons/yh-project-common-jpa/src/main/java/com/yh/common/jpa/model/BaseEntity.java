package com.yh.common.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -779979211709378717L;

	protected String id;

	private String createUserId;

	private Date createDatetime;

	private String lastUpdateUserId;

	private Date lastUpdateDatetime;

	//默认值为0 ，即不删除
	private String isDeleted = "0";

	private String obligateA;

	private String obligateB;

	private String obligateC;

	private String obligateD;

	private String obligateE;

	@Id
	@GeneratedValue(generator = "generatedkey")
	@GenericGenerator(name = "generatedkey", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id == null || id.trim().length() == 0) {
			this.id = null;
		} else {
			this.id = id;
		}
	}

	@Column(name = "createuserid", length = 32, updatable = false)
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdatetime", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "lastupdateuserid", length = 32)
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastupdatedatetime")
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name = "obligatea", length = 60)
	public String getObligateA() {
		return obligateA;
	}

	public void setObligateA(String obligateA) {
		this.obligateA = obligateA;
	}

	@Column(name = "obligateb", length = 60)
	public String getObligateB() {
		return obligateB;
	}

	public void setObligateB(String obligateB) {
		this.obligateB = obligateB;
	}

	@Column(name = "obligatec", length = 60)
	public String getObligateC() {
		return obligateC;
	}

	public void setObligateC(String obligateC) {
		this.obligateC = obligateC;
	}

	@Column(name = "obligated", length = 60)
	public String getObligateD() {
		return obligateD;
	}

	public void setObligateD(String obligateD) {
		this.obligateD = obligateD;
	}

	@Column(name = "obligatee", length = 60)
	public String getObligateE() {
		return obligateE;
	}

	public void setObligateE(String obligateE) {
		this.obligateE = obligateE;
	}

	@Column(name = "isdeleted", nullable = false, columnDefinition = "varchar(1)  default 0", insertable = false)
	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
