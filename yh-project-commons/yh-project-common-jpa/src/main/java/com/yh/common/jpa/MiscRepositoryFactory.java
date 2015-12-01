package com.yh.common.jpa;
import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
/**
 * 自定义的RepositoryFactory，用于生成MiscRepository
 */
public class MiscRepositoryFactory extends JpaRepositoryFactory {
	private EntityManager entityManager;
	
	public MiscRepositoryFactory(EntityManager entityManager) {
		super(entityManager) ;
		//设置当前类的实体管理器
		this.entityManager = entityManager ;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
		return new MiscRepositoryImpl(entityInformation, entityManager); 
	}
	
	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return MiscRepositoryImpl.class;
	}
}
